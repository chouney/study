package netty.demo.handler;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import netty.nio.NIOByteBufferTest;

import java.io.IOException;
import java.util.concurrent.Executor;

/**
 * Created by chriszhang on 2017/7/8.
 */
public class DemoClientHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("server " + ctx.channel().remoteAddress().toString() + " connected");
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ByteBuf buf = (ByteBuf) msg;
        byte[] bytes = new byte[buf.readableBytes()];
        buf.readBytes(bytes);
        buf.release();
//        ByteBuf result = (ByteBuf) msg;
//        byte[] result1 = new byte[result.readableBytes()];
//        result.readBytes(result1);
//        System.out.println("Server said:" + new String(result1));
//        result.release();
        System.out.println("client :received from server :" + NIOByteBufferTest.toObject(bytes, String.class));
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause)
            throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
