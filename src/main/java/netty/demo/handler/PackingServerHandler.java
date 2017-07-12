package netty.demo.handler;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import netty.nio.NIOByteBufferTest;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Date;

/**
 * 为了测试粘包数据，设置计数器表示收到请求的次数。
 * 另外在一次请求后去掉发送端加上的换行符来分割请求。
 * Created by chriszhang on 2017/7/8.
 */
public class PackingServerHandler extends ChannelInboundHandlerAdapter {

    private int counter;

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws UnsupportedEncodingException {
        ByteBuf buf = (ByteBuf) msg;
        byte[] bytes = new byte[buf.readableBytes()];
        buf.readBytes(bytes);
        String body = new String(bytes, "utf-8").substring(0, bytes.length - System.getProperty("line.separator").length());
        System.out.println("Server received query :" + body + " , the counter is :" + ++counter);

        // 向客户端发送消息
        String response = "QUERY TIME ORDER".equalsIgnoreCase(body) ? new Date().toString() : "BAD QUERY";
        response = response + System.getProperty("line.separator");
        buf = Unpooled.copiedBuffer(response.getBytes());
        ctx.writeAndFlush(buf);

    }


    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause)
            throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
