package mq.dmq;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

// TODO: 2018/11/6 可以维护成多文件
// TODO: 2018/11/6 1. 尝试从pageCache、锁粒度细化角度优化。（目前TPS200W）2.从工程的角度，可以以此为基准做扩展。需要解决的问题有：同步写操作、保证读写数据一致性、保证高可用性。

/**
 * 考察点：磁盘块读写，读写缓冲，顺序读写与随机读写，稀疏索引，队列存储设计等
 */

/**
 * @author chriszhang
 * @version 1.0
 * @date 2018/10/9
 */
public class Queue {
    // 首先确定文件IO方式
    // - 每个队列需要写缓冲区、一个刷盘方法、一个读盘方法。
    // 其次确定存储结构
    // - 以队列为单位消息按块存储，单个文件存储

    //1.测试数据2000w条，10000队列的运行。则每个队列平均存储2000条。
    //2.每条数据50byte左右，阿里云的SSD读写在16kb-64kb吞吐量最优，因此每个队列块缓冲区大小尽可能的靠近这个区域。
    //3.机器配置堆内堆外均为4g,则每个队列最多均需4k缓冲区。因此块缓冲区设置为4k。
    //4.每个缓冲区存储大概70条左右数据。队列偏移量size存储大小为2000/70+30=200左右
    private static final int MESSAGE_HEAD_SIZE = 2;

    private static final int SINGLE_MESSAGE_SIZE = 58 + MESSAGE_HEAD_SIZE;

    private static final int BLOCK_SAVE_MESSAGE_NUM = 70;

    //块缓冲区大小
    private static final int BLOCK_SIZE = 50 * 70;

    //总共的块大小
    private static final int BLOCK_NUM = 2000 / BLOCK_SAVE_MESSAGE_NUM  + 30;

    private FileChannel fileChannel;

    //读写缓冲区
    private ByteBuffer writeBuffer ;
//    private ByteBuffer readBuffer ;

    private boolean firstGet = false;

    //块记录
    private static Block[] blocks = new Block[BLOCK_NUM];
    //块记录偏移量
    private AtomicInteger lastBlockOffset = new AtomicInteger(0);
    private AtomicLong lastMessageOffset = new AtomicLong(0);
    private volatile Block currentBlock;

    public Queue(FileChannel fileChannel) {
        this.fileChannel = fileChannel;
        this.writeBuffer = ByteBuffer.allocateDirect(BLOCK_SIZE);
        //不用读缓存是因为题目保证了先写后读的顺序性。
//        this.readBuffer = ByteBuffer.allocateDirect(BLOCK_SIZE);
    }

    /**
     * 评测程序保证了在 queue 级别的写入是同步的，所以对于同一个队列，我们无需担心同步问题
     * @param message
     */
    //先实现同步非异步刷盘
    public void put(byte[] message){
        //创建新的块
        if(Objects.isNull(currentBlock)){
            currentBlock = new Block();
        }
        //1.放入缓冲区
        //1.1判断是否超出缓冲区，如果超过，则刷盘。
        if(writeBuffer.remaining() < message.length + MESSAGE_HEAD_SIZE){
            flush();
        }
        writeBuffer.putShort((short)message.length).put(message);
        currentBlock.blockSize += MESSAGE_HEAD_SIZE + message.length;
        currentBlock.messageCount++;
    }

    /**
     * 对统一读缓冲区进行读，因此有并发问题
     * @param offset
     * @param num
     * @return
     */
    public synchronized Collection<byte[]> get(long offset, long num){
        if(Objects.isNull(currentBlock) || blocks.length == 0){
            return DefaultQueueStoreImpl.EMPTY;
        }
        if(!firstGet){
            flushForGet();
            firstGet = true;
        }
        Block lastBlock = blocks[lastBlockOffset.get()-1];
        int maxMessageIndex = lastBlock.messageIndex+lastBlock.messageCount;
        if(maxMessageIndex < offset){
            return DefaultQueueStoreImpl.EMPTY;
        }

        //二分查找到消息所在的块
        Integer startBlock = binarySearch(blocks,offset);
        Integer endBlock = binarySearch(blocks,offset+num);
        if(Objects.isNull(endBlock)){
            endBlock = lastBlockOffset.get()-1;
        }
        if(Objects.isNull(startBlock) || Objects.isNull(endBlock)){
            throw new RuntimeException("未找到消息所在的块");
        }
        List<byte[]> result = new ArrayList<>();
        //取出块中消息的具体所在位置。
        for(int i = startBlock;i<=endBlock;i++) {
            Block block = blocks[i];
            //执行读取操作
//            readBuffer.clear();
            writeBuffer.clear();
            try {
//                fileChannel.read(readBuffer, block.position);
                fileChannel.read(writeBuffer, block.position);
            } catch (IOException e) {
                e.printStackTrace();
            }
//            readBuffer.flip();
            writeBuffer.flip();
            for(int j = 0;j<block.messageCount;j++){
//                int len = readBuffer.getShort();
                int len = writeBuffer.getShort();
                byte[] message = new byte[len];
//                readBuffer.get(message);
                writeBuffer.get(message);
                if(j+block.messageIndex >= offset && j+block.messageIndex < offset+num){
                    result.add(message);
                }
            }
        }
        return result;
    }


    private Integer binarySearch(Block[] blocks,long mOffset){
        int si = 0,ei = lastBlockOffset.get()-1;
        while(si <= ei){
            int mid = si+((ei - si) >> 1);
            Block curBlock = blocks[mid];
            if(mOffset < curBlock.messageIndex){
                ei = mid - 1;
            }else if(mOffset > curBlock.messageIndex+curBlock.messageCount){
                si = mid + 1;
            }else {
                return mid;
            }
        }
        return null;
    }


    private void flush(){
        //刷盘
        writeBuffer.flip();
        try {
            fileChannel.write(writeBuffer,lastMessageOffset.getAndAdd(currentBlock.blockSize));
        } catch (IOException e) {
            e.printStackTrace();
        }
        writeBuffer.clear();
        //形成新的Block，offset偏移量+1
        blocks[lastBlockOffset.getAndAdd(1)] = currentBlock;
        Block newBlock = new Block();
        //记录新Block的消息偏移量和存储地址偏移量
        newBlock.messageIndex = currentBlock.messageIndex+currentBlock.messageCount;
        newBlock.position = currentBlock.position + currentBlock.blockSize;
        currentBlock = newBlock;
    }

    /**
     * 为了读取时将缓冲区的数据放入文件里（只针对题目进行设计，并不是最优化）
     */
    private void flushForGet(){
        //刷盘
        writeBuffer.flip();
        try {
            fileChannel.write(writeBuffer,lastMessageOffset.getAndAdd(currentBlock.blockSize));
        } catch (IOException e) {
            e.printStackTrace();
        }
        writeBuffer.clear();
        //形成新的Block，offset偏移量+1
        blocks[lastBlockOffset.getAndAdd(1)] = currentBlock;

    }

}
