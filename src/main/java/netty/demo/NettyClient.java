package netty.demo;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import netty.demo.handler.DemoClientHandler;
import netty.nio.NIOByteBufferTest;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;


/**
 * Created by chriszhang on 2017/6/28.
 */
public class NettyClient {

    public void connect(String address, int port) {
        NIOByteBufferTest nioByteBufferTest = new NIOByteBufferTest(ByteBuffer.allocateDirect(100));
        NioEventLoopGroup group = new NioEventLoopGroup();
        final DemoClientHandler clientHandler = new DemoClientHandler();
        try {
            Executor executor = Executors.newCachedThreadPool();
            Bootstrap bootstrap = new Bootstrap();
            bootstrap.group(group).channel(NioSocketChannel.class)
                    .option(ChannelOption.TCP_NODELAY, true)
//                    .option(ChannelOption.SO_KEEPALIVE,true)
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                                ch.pipeline().addLast(clientHandler);
                        }
                    });
            ChannelFuture future = bootstrap.connect(new InetSocketAddress(address, port)).sync();

            executor.execute(nioByteBufferTest.new WriteTask());
            executor.execute(nioByteBufferTest.new ReadTask() {
                @Override
                public void processByteBuffer() throws IOException {
                    ByteBuf buf = Unpooled.copiedBuffer(this.buffer1);
                    this.buffer1.position(this.buffer1.limit());
                    future.channel().writeAndFlush(buf);
                    System.out.println("client: message has been sent");
                }
            });
            future.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }finally {
            group.shutdownGracefully();
        }
    }

    public static void main(String[] args){
//        new Thread(() ->{
//            NettyServer server = new NettyServer();
//            server.bind(54321);
//        });
        NettyClient client = new NettyClient();
        client.connect("localhost",54321);
    }
}
