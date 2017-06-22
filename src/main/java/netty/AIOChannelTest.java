package netty;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousServerSocketChannel;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.locks.LockSupport;

/**
 * jdk1.7新增异步IO模型AIO。
 * 将模拟1个客户端发送消息，多个服务器接收并打印
 * Created by chriszhang on 2017/6/21.
 */
public class AIOChannelTest {

    Executor executor = Executors.newCachedThreadPool();

    /**
     * 服务器端
     * 实现CompletionHandler接口，通过该接口的回调函数进行接收操作。
     */
    public class Server implements Runnable {

        AsynchronousServerSocketChannel asynchronousServerSocketChannel;

        public Server(int port) {
            try {
                this.asynchronousServerSocketChannel = AsynchronousServerSocketChannel.open();
                this.asynchronousServerSocketChannel.bind(new InetSocketAddress(port));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void run() {
            //accept有两个方法，一个是无参方法一个是下面带参方法。
            // 两个方法在实现上一样，区别只在于无参方法在回调时会返回Future<AsynchronousSocketChannel>，而带参会执行回调CompletionHandler的回调方法
            asynchronousServerSocketChannel.accept(this, new CompletionHandler<AsynchronousSocketChannel, Server>() {
                /**
                 * 1接收到socket的connect请求
                 * @param result
                 * @param attachment
                 */
                @Override
                public void completed(AsynchronousSocketChannel result, Server attachment) {
                    //递归调用asynchronousServerSocketChannel以便后续的连接请求可以被获取
                    attachment.asynchronousServerSocketChannel.accept(attachment, this);
                    ByteBuffer buffer = ByteBuffer.allocateDirect(100);
                    /**
                     * 2开始接收socket的write数据
                     */
                    result.read(buffer, buffer, new CompletionHandler<Integer, ByteBuffer>() {
                        /**
                         * 3收到socket的发送数据
                         * @param result
                         * @param attachment
                         */
                        @Override
                        public void completed(Integer result, ByteBuffer attachment) {
                            System.out.println("read Socket的操作类型" + result);
                            attachment.flip();
                            if (attachment.hasRemaining()) {
                                byte[] bytes = new byte[attachment.remaining()];
                                attachment.get(bytes);
                                System.out.println(NIOByteBufferTest.toObject(bytes, String.class));
                            }
                        }

                        @Override
                        public void failed(Throwable exc, ByteBuffer attachment) {
                            exc.printStackTrace();
                        }
                    });


                }

                @Override
                public void failed(Throwable exc, Server attachment) {
                    try {
                        if (attachment != null) {
                            attachment.asynchronousServerSocketChannel.close();
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });
            //Future<AsynchronousSocketChannel> future = asynchronousServerSocketChannel.accept();
            LockSupport.park();
        }
    }


    /**
     * 创建客户端请求服务器端
     */
    public class Client implements Runnable {


        public Client() {
        }

        @Override
        public void run() {
            ByteBuffer buffer = ByteBuffer.allocateDirect(100);
            ByteBuffer buffer2 = ByteBuffer.allocateDirect(100);
            NIOByteBufferTest nioByteBufferTest = new NIOByteBufferTest(buffer);
            executor.execute(nioByteBufferTest.new WriteTask());
            executor.execute(nioByteBufferTest.new ReadTask() {
                @Override
                public void processByteBuffer() throws IOException {
                    /**
                     * 每次一请求都会创建一次异步客户端
                     */
                    AsynchronousSocketChannel asynchronousSocketChannel = AsynchronousSocketChannel.open();
                    buffer2.clear();
                    buffer2.put(buffer);
                    buffer.rewind();
                    byte[] bytes = new byte[this.buffer1.remaining()];
                    this.buffer1.get(bytes);
                    String res = NIOByteBufferTest.toObject(bytes,String.class);
                    String port = res.length() > 1 ? res.split(",")[0] : "43211";
                    buffer2.flip();
                    /**
                     *  每次请求连接后都会触发回调函数
                     */
                    asynchronousSocketChannel.connect(new InetSocketAddress(Integer.valueOf(port)), this, new CompletionHandler<Void, NIOByteBufferTest.ReadTask>() {
                        @Override
                        public void completed(Void result, NIOByteBufferTest.ReadTask attachment) {
                            /**
                             * 这里的write方法同样可以选择回调，回调时机是写操作完毕后
                             */
                            asynchronousSocketChannel.write(buffer2);
                            try {
                                asynchronousSocketChannel.close();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }

                        @Override
                        public void failed(Throwable exc, NIOByteBufferTest.ReadTask attachment) {
                            exc.printStackTrace();
                        }
                    });
                }
            });
                LockSupport.park();
        }
    }
    public static void main(String[] args){
        AIOChannelTest aioChannelTest = new AIOChannelTest();
        aioChannelTest.executor.execute(aioChannelTest.new Server(43211));
        aioChannelTest.executor.execute(aioChannelTest.new Server(43212));
        aioChannelTest.executor.execute(aioChannelTest.new Server(43213));
        aioChannelTest.executor.execute(aioChannelTest.new Client());
    }
}
