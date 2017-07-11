package netty.nio;



import java.io.*;
import java.nio.ByteBuffer;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 缓冲区的读写练习，涉及到生产者消费者模型、序列化反序列化，为其他练习提供服务
 * Created by manatea on 2017/5/24.
 */
public class NIOByteBufferTest {
    public ByteBuffer buffer;
    private ReentrantLock lock = new ReentrantLock();
    private Condition canRead = lock.newCondition();
    private Condition canWrite = lock.newCondition();
    private Scanner in = new Scanner(System.in);

    public NIOByteBufferTest(ByteBuffer buffer) {
        this.buffer = buffer;
        buffer.flip();
    }

    public abstract class ReadTask implements Runnable {
        public ByteBuffer buffer1;

        public ReadTask() {
            this.buffer1 = buffer;
        }

        public abstract void processByteBuffer() throws IOException;
        public void run() {
            while (true) {
                try {
                    lock.lock();
                    while (!buffer1.hasRemaining()) {
                        canRead.await();
                    }
                    processByteBuffer();
                    canWrite.signalAll();
                    lock.unlock();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public class WriteTask implements Runnable {
        public void run() {
            while (true) {
                try {
                    lock.lock();
                    while (buffer.hasRemaining()) {
                        canWrite.await();
                    }
                    buffer.clear();
                    System.out.println("请输入通讯：");
                    String s = "";
                    if (in.hasNextLine()) {
                        s = in.nextLine();
                    }
                    try {
                        buffer.put(toByte(s));
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                    }
                    buffer.flip();
                    canRead.signalAll();
                    lock.unlock();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void main(String[] args) {
        ExecutorService pool = Executors.newFixedThreadPool(2, new ThreadFactory() {
            int count = 0;

            public Thread newThread(Runnable r) {
                return new Thread(r, "ZQX_Thread" + count++);
            }
        });
        NIOByteBufferTest t = new NIOByteBufferTest(ByteBuffer.allocateDirect(100));
        pool.execute(t.new WriteTask());
        pool.execute(t.new ReadTask() {
            @Override
            public void processByteBuffer() {
                int count = this.buffer1.remaining();
                byte[] read = new byte[count];
                this.buffer1.get(read);
                String s = toObject(read,String.class);
                System.out.println("线程2的输出任务："+s);
            }
        });
    }

    //序列化和反序列化
    public static <T> T toObject(byte[] bytes, Class<T> clazz) {
        if (bytes.length == 0) {
            return null;
        }
        ByteArrayInputStream byteArrayInputStream = null;
        ObjectInputStream inputStream = null;
        try {
            byteArrayInputStream = new ByteArrayInputStream(bytes);
            inputStream = new ObjectInputStream(byteArrayInputStream);
            return (T) inputStream.readObject();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (byteArrayInputStream != null) {
                try {
                    byteArrayInputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }

    public static byte[] toByte(Object obj) throws IOException, ClassNotFoundException {
        if(obj == null){
            return new byte[0];
        }
        ByteArrayOutputStream bout = null;
        ObjectOutputStream oout = null;
        try {
            bout = new ByteArrayOutputStream();
            oout = new ObjectOutputStream(bout);
            oout.writeObject(obj);
            return bout.toByteArray();
        }finally {
            if(bout != null){
                bout.close();
            }
            if(oout != null){
                oout.close();
            }
        }
    }
}
