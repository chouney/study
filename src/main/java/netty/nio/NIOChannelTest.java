package netty.nio;


import java.io.*;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;

/**
 * 结合缓冲区练习设计不同的通道
 * Created by manatea on 2017/5/24.
 */
public class NIOChannelTest {
    ExecutorService pool = Executors.newCachedThreadPool(new ThreadFactory() {
        int count = 0;

        public Thread newThread(Runnable r) {
            return new Thread(r, "ZQX_THREAD" + count++);
        }
    });
    NIOByteBufferTest byteBufferTest = new NIOByteBufferTest(ByteBuffer.allocateDirect(100));

    public void testFileChannel() throws IOException {
        //创建了一个可随机读写的通道
        final RandomAccessFile raf = new RandomAccessFile(new File("./fileChannelTest"), "rw");
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
                    System.out.println(NIOByteBufferTest.toObject(bytes, String.class));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public void testSocketChannel() {
        pool.execute(byteBufferTest.new WriteTask());
        pool.execute(byteBufferTest.new ReadTask() {
            @Override
            public void processByteBuffer() {
                try(SocketChannel socketChannel = SocketChannel.open()) {
                    //非阻塞模式
                    socketChannel.configureBlocking(false);
                    socketChannel.connect(new InetSocketAddress("localhost", 8087));
                    while (!socketChannel.finishConnect()) {
                        System.out.println("connectting");
                    }
                    System.out.println("connecting finished");
                    socketChannel.write(this.buffer1);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        pool.execute(new Runnable() {
            public void run() {
                try(ServerSocketChannel serverSocketChannel = ServerSocketChannel.open()) {
                    serverSocketChannel.bind(new InetSocketAddress(8087));
                    //非阻塞模式
                    serverSocketChannel.configureBlocking(false);
                    //阻塞模式
                    //serverSocketChannel.configureBlocking(true);
                    ByteBuffer in = ByteBuffer.allocateDirect(100);
                    System.out.println("waiting for connection");
                    while (true) {
                        SocketChannel socketChannel = serverSocketChannel.accept();
                        if (socketChannel == null) {
                            try {
                                Thread.sleep(1000);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        } else {
                            socketChannel.read(in);
                            in.rewind();
                            byte[] bytes = new byte[in.remaining()];
                            in.get(bytes);
                            System.out.println(NIOByteBufferTest.toObject(bytes, String.class));
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

    //数据包通道测试UDP/IP
    public void testDatagramChannel() {
        pool.execute(byteBufferTest.new WriteTask());
        pool.execute(byteBufferTest.new ReadTask() {
            @Override
            public void processByteBuffer() {
                //只需要绑定端口，发送时指定IP即可
                try (DatagramChannel datagramChannel = DatagramChannel.open()) {
                    datagramChannel.bind(new InetSocketAddress(10087));
                    datagramChannel.configureBlocking(false);
                    System.out.println("客户端线程已发送");
                    datagramChannel.send(this.buffer1, new InetSocketAddress("127.0.0.1", 10086));
                    SocketAddress address = null;
                    this.buffer1.clear();
                    while ((address = datagramChannel.receive(this.buffer1)) == null) ;
                    this.buffer1.flip();
                    byte[] bytes = new byte[this.buffer1.remaining()];
                    this.buffer1.get(bytes);
                    System.out.println("客户端线程已接收：" + NIOByteBufferTest.toObject(bytes, String.class));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        pool.execute(new Runnable() {
            @Override
            public void run() {
                try (DatagramChannel datagramChannel = DatagramChannel.open()) {
                    datagramChannel.bind(new InetSocketAddress(10086));
                    datagramChannel.configureBlocking(false);
                    ByteBuffer byteBuffer = ByteBuffer.allocateDirect(100);
                    while (true) {
                        byteBuffer.clear();
                        SocketAddress address = datagramChannel.receive(byteBuffer);
                        if (address != null) {
                            byteBuffer.flip();
                            byte[] bytes = new byte[byteBuffer.remaining()];
                            byteBuffer.get(bytes);
                            System.out.println("监听线程接收：" + NIOByteBufferTest.toObject(bytes, String.class));
                            byteBuffer.clear();
                            byteBuffer.put(NIOByteBufferTest.toByte("监听线程已收到"));
                            byteBuffer.flip();
                            datagramChannel.send(byteBuffer, address);
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    //管道
    public void testPipe(){
        pool.execute(byteBufferTest.new WriteTask());
        pool.execute(byteBufferTest.new ReadTask() {
            @Override
            public void processByteBuffer() {
//                Pipe pipe = Pipe.open();
//                pipe.
            }
        });
    }

    public static void main(String[] args) throws IOException {
        NIOChannelTest n = new NIOChannelTest();
        n.testDatagramChannel();
    }
}
