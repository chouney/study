package mq.dmq;

import java.io.FileNotFoundException;
import java.io.RandomAccessFile;
import java.nio.channels.FileChannel;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 目前是单文件持久化队列
 *
 * 实际提交时，请维持包名和类名不变，把方法实现修改为自己的内容；
 */
public class DefaultQueueStoreImpl extends QueueStore {
    public static Collection<byte[]> EMPTY = new ArrayList<byte[]>();
    private Map<String, Queue> queueMap = new ConcurrentHashMap<String, Queue>();
    public static final String dir = DefaultQueueStoreImpl.class.getResource("").getPath();

    //单文件通道
    private FileChannel fileChannel;

    public DefaultQueueStoreImpl() {
        try {
            RandomAccessFile file = new RandomAccessFile(dir + "test.data", "rw");
            this.fileChannel = file.getChannel();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public synchronized void put(String queueName, byte[] message) {
        if (!queueMap.containsKey(queueName)) {
            queueMap.put(queueName, new Queue(this.fileChannel));
        }
        queueMap.get(queueName).put(message);
    }

    public synchronized Collection<byte[]> get(String queueName, long offset, long num) {
        if (!queueMap.containsKey(queueName)) {
            return EMPTY;
        }
        return queueMap.get(queueName).get(offset,num);
    }

}
