package netty.demo;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import netty.demo.handler.DemoServerHandler;
import netty.demo.handler.PackingServerHandler;
import netty.nio.NIOByteBufferTest;

import java.net.InetSocketAddress;

/**
 * 一个简陋的不符合规范的NettyServer,具体架构查看笔记
 * 因此这里参照官方的user guide
 * Created by chriszhang on 2017/6/24.
 */
public class NettyServer {

    public void bind(int port) {
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workGroup = new NioEventLoopGroup();
        try {
            ServerBootstrap serverBootstrap = new ServerBootstrap();
            serverBootstrap.group(bossGroup, workGroup).channel(NioServerSocketChannel.class).
                    option(ChannelOption.SO_BACKLOG, 1024).childHandler(new ChannelInitializer<SocketChannel>() {
                @Override
                protected void initChannel(SocketChannel ch) throws Exception {
                    ch.pipeline()
                            .addLast(new PackingServerHandler());
//                            .addLast(new DemoServerHandler());
                }
            });

            ChannelFuture f = serverBootstrap.bind(new InetSocketAddress(port)).sync();

            f.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            bossGroup.shutdownGracefully();
            workGroup.shutdownGracefully();
        }

    }

    public static void  main(String[] args){
        NettyServer nettyServer = new NettyServer();
        nettyServer.bind(54321);
    }
}
