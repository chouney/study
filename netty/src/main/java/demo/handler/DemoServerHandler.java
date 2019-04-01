package demo.handler;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import nio.NIOByteBufferTest;

import java.io.IOException;

/**
 * server端的handler处理器
 * Created by chriszhang on 2017/7/8.
 */
public class DemoServerHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("client:" + ctx.channel().remoteAddress() + " connected !");
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("client:" + ctx.channel().remoteAddress() + " disconnected ! ");
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        /**
         * 这里注释的demo告诉我们，默认使用ByteBuf作为通讯单元，如果不适用则接收方就无法触发Read事件
         * （应该是发送端无法encode，也就没法发送）
         */
//        ByteBuf result = (ByteBuf) msg;
//        byte[] result1 = new byte[result.readableBytes()];
//        // msg中存储的是ByteBuf类型的数据，把数据读取到byte[]中
//        result.readBytes(result1);
//        String resultStr = new String(result1);
//        // 接收并打印客户端的信息
//        System.out.println("Client said:" + resultStr);
//        // 释放资源，这行很关键
//        result.release();
//
//        // 向客户端发送消息
//        String response = "I am ok!";
//        // 在当前场景下，发送的数据必须转换成ByteBuf数组
//        ByteBuf encoded = ctx.alloc().buffer(4 * response.length());
//        encoded.writeBytes(response.getBytes());
//        ctx.write(encoded);
//        ctx.flush();

        ByteBuf buf = (ByteBuf) msg;
        byte[] bytes = new byte[buf.readableBytes()];
        buf.readBytes(bytes);
        String res = NIOByteBufferTest.toObject(bytes, String.class);
        System.out.println(res);
        buf.release();

        // 向客户端发送消息
        String response = "server : has received " + res;
        // 在当前场景下，发送的数据必须转换成ByteBuf数组
        ByteBuf encoded = ctx.alloc().buffer(4 * response.length());
        try {
            encoded.writeBytes(NIOByteBufferTest.toByte(response));
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        ctx.write(encoded);
        ctx.flush();
    }


    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause)
            throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
