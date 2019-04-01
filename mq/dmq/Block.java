package mq.dmq;

/**
 * @author chriszhang
 * @version 1.0
 * @date 2018/10/11
 */
public class Block {

    //块内消息数量
    public int messageCount;

    //块大小
    public int blockSize;

    //块在文件中的偏移量
    public long position;

    //消息在该块中的第一个编号
    public int messageIndex;

}
