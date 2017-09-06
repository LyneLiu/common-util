package com.lyne.server;

import com.lyne.LogUtil;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * @author nn_liu
 * @Created 2017-08-18-16:33
 */

public class EchoInbountHandler2 extends ChannelInboundHandlerAdapter {

    @Override
    // 读取Client发送的信息，并打印出来
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        LogUtil.info(">>>>>EchoInbountHandler2.channelRead: ctx :" + ctx);
        ByteBuf result = (ByteBuf) msg;
        byte[] result1 = new byte[result.readableBytes()];
        result.readBytes(result1);
        String resultStr = new String(result1);
        System.out.println("Echo Server receive:" + resultStr);
        result.release();

        ctx.write(msg);
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        LogUtil.info(">>>>>EchoInbountHandler2.channelReadComplete");
        ctx.flush();
    }

}
