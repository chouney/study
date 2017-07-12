package netty.demo.handler;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import netty.nio.NIOByteBufferTest;

/**
 * 粘包测试
 * 连续发送100个数据
 * 结果将看到100条数据会最终封装为2个数据报文，即粘包
 * Created by chriszhang on 2017/7/12.
 */
public class PackingClientHandler extends ChannelInboundHandlerAdapter {

    private int counter;

    private byte[] bytes;

    public PackingClientHandler() {
        counter = 0;
        bytes = ("QUERY TIME ORDER" + System.getProperty("line.separator")).getBytes();
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        ByteBuf buf = null;
        for (int i = 0; i < 100; i++) {
            buf = Unpooled.buffer(bytes.length);
            buf.writeBytes(bytes);
            ctx.writeAndFlush(buf);
        }
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ByteBuf res = (ByteBuf) msg;
        byte[] bytes = new byte[res.readableBytes()];
        res.readBytes(bytes);
        String message = new String(bytes, "utf-8");
        System.out.println("now is " + message + " , count: " + ++counter);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause)
            throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
