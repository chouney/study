package netty;

import java.io.*;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;

/**
 * 结合缓冲区练习设计不同的通道
 * Created by manatea on 2017/5/24.
 */
public class NIOChannelTest {
    ExecutorService pool = Executors.newCachedThreadPool(new ThreadFactory() {
        int count=0;
        public Thread newThread(Runnable r) {
            return new Thread(r,"ZQX_THREAD"+count++);
        }
    });
    NIOByteBufferTest byteBufferTest = new NIOByteBufferTest(ByteBuffer.allocateDirect(100));

    public void testFileChannel() throws IOException {
        //创建了一个可随机读写的通道
        final RandomAccessFile raf = new RandomAccessFile(new File("./fileChannelTest"),"rw");
        final FileChannel f = raf.getChannel();
        pool.execute(byteBufferTest.new WriteTask());
        pool.execute(byteBufferTest.new ReadTask() {
            @Override
            public void processByteBuffer() {
                try {
                    //标记通道起始位置,通道读取缓冲区数据，写入通道
                    f.position(0);
                    f.write(this.buffer1);
                    //从通道起始位置
                    ByteBuffer in = ByteBuffer.allocateDirect(100);
                    f.position(0);
                    f.read(in);
                    in.flip();
                    byte[] bytes = new byte[in.remaining()];
                    in.get(bytes);
                    System.out.println(NIOByteBufferTest.toObject(bytes,String.class));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public void testSocketChannel(){
        pool.execute(byteBufferTest.new WriteTask());
        pool.execute(byteBufferTest.new ReadTask() {
            @Override
            public void processByteBuffer() {
                SocketChannel socketChannel = null;
                try {
                    socketChannel = SocketChannel.open();
                    //非阻塞模式
                    socketChannel.configureBlocking(false);
                    socketChannel.connect(new InetSocketAddress("localhost",8087));
                    while(! socketChannel.finishConnect()){
                        System.out.println("connectting");
                    }
                    System.out.println("connecting finished");
                    socketChannel.write(this.buffer1);
                    socketChannel.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        pool.execute(new Runnable() {
            public void run() {
                ServerSocketChannel serverSocketChannel = null;
                try {
                    serverSocketChannel = ServerSocketChannel.open();
                    serverSocketChannel.bind(new InetSocketAddress(8087));
                    //非阻塞模式
                    serverSocketChannel.configureBlocking(false);
                    //阻塞模式
                    //serverSocketChannel.configureBlocking(true);
                    ByteBuffer in = ByteBuffer.allocateDirect(100);
                    System.out.println("waiting for connection");
                    while(true){
                        SocketChannel socketChannel = serverSocketChannel.accept();
                        if(socketChannel == null){
                            try {
                                Thread.sleep(1000);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }else{
                            socketChannel.read(in);
                            in.rewind();
                            byte[] bytes = new byte[in.remaining()];
                            in.get(bytes);
                            System.out.println(NIOByteBufferTest.toObject(bytes,String.class));
                            socketChannel.close();
                            //读完缓冲区必须clear，
                            in.clear();
                        }

                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public static void main(String[] args) throws IOException {
        NIOChannelTest n = new NIOChannelTest();
        n.testSocketChannel();
    }
}
