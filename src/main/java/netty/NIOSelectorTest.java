// Copyright (C) 2017 Meituan
// All rights reserved
package netty;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.regex.Pattern;

/**
 * 选择器练习
 *
 * @author manatea
 * @version 1.0
 * @created 2017/6/18 PM3:42
 **/
public class NIOSelectorTest {
    Executor executor = Executors.newCachedThreadPool();
    NIOByteBufferTest nioByteBufferTest = new NIOByteBufferTest(ByteBuffer.allocateDirect(100));

    /**
     * 测试构建一个基于3个serverSocket通道的就绪选择器，并根据发送不同socket通道测试选择器功能
     */
    public void testSelectable() throws IOException {

        executor.execute(() -> {
            //构建选择器并注册三个serversocket通道
            try (Selector selector = Selector.open();
                 ServerSocketChannel serverSocketChannel1 = ServerSocketChannel.open();
                 ServerSocketChannel serverSocketChannel2 = ServerSocketChannel.open();
                 ServerSocketChannel serverSocketChannel3 = ServerSocketChannel.open()) {
                serverSocketChannel1.bind(new InetSocketAddress("localhost",43210));
                serverSocketChannel1.configureBlocking(false);
                //注册时操作必须是通道所支持的操作的子集，可以调用通道的validOps获得通道支持的操作
                serverSocketChannel1.register(selector, serverSocketChannel1.validOps());
                serverSocketChannel2.bind(new InetSocketAddress("localhost",43211));
                serverSocketChannel2.configureBlocking(false);
                serverSocketChannel2.register(selector, serverSocketChannel2.validOps());
                serverSocketChannel3.bind(new InetSocketAddress("localhost",43212));
                serverSocketChannel3.configureBlocking(false);
                serverSocketChannel3.register(selector, serverSocketChannel3.validOps());

                while(true) {
                    /**
                     * 选择器的用法通常是调用一次select方法，然后获取他的selectKey()，遍历去除后删除
                     */
                    //select方法只能返回上次select到这个select前的就绪通道
                    int count = selector.select();
                    Set<SelectionKey> selectionKeys = selector.selectedKeys();
                    Iterator<SelectionKey> iterator = selectionKeys.iterator();
                    while (iterator.hasNext()) {
                        SelectionKey selectionKey = iterator.next();
                        //如果就绪的是ServerSocketChannel,它绑定的兴趣集是Accept,则将连接的SocketChannel也注册到选择器中
                        if (selectionKey.isAcceptable()) {
                            ServerSocketChannel channel = (ServerSocketChannel) selectionKey.channel();
                            SocketChannel socketChannel = channel.accept();
                            socketChannel.configureBlocking(false);
                            socketChannel.register(selector, SelectionKey.OP_READ);
                        }
                        if (selectionKey.isReadable()) {
                            SocketChannel socketChannel = (SocketChannel) selectionKey.channel();
                            ByteBuffer buffer = ByteBuffer.allocateDirect(100);
                            socketChannel.read(buffer);
                            buffer.flip();
                            byte[] bytes = new byte[buffer.remaining()];
                            buffer.get(bytes);
                            System.out.println(socketChannel.getRemoteAddress()+ " "+ socketChannel.getLocalAddress() + " , serverSocket,:" + NIOByteBufferTest.toObject(bytes, String.class));
                            //一次请求后需要释放通道，否则选择器会认为该通道一直就绪会一直取数据
                            socketChannel.close();
                        }
                        //就绪键集合需要手动删除，因为防止未处理的通道被一下轮就绪选择覆盖。
                        iterator.remove();
                    }
                }

            } catch (ClosedChannelException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });


        //发送不同的socket通道
        executor.execute(nioByteBufferTest.new WriteTask());

        executor.execute(nioByteBufferTest.new ReadTask() {
            @Override
            public void processByteBuffer() {
                try (SocketChannel socketChannel = SocketChannel.open()) {
                    byte[] bytes = new byte[this.buffer1.remaining()];
                    this.buffer1.get(bytes);
                    String str = NIOByteBufferTest.toObject(bytes, String.class);
                    String[] arrayStr = str.split(",");
                    int port = arrayStr.length > 1 ? Integer.valueOf(arrayStr[0]) : 0;
                    socketChannel.connect(new InetSocketAddress("localhost",port));
                    while (!socketChannel.finishConnect()) {
                        System.out.println(Thread.currentThread().getName() + ": connecting");
                    }
                    this.buffer1.rewind();
                    socketChannel.write(this.buffer1);
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        });
    }

    public static void main(String[] args) throws IOException {
        NIOSelectorTest nioSelectorTest = new NIOSelectorTest();
        nioSelectorTest.testSelectable();
    }
}