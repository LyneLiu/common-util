package com.lyne.server;

import com.lyne.LogUtil;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * @author nn_liu
 * @Created 2017-08-18-16:33
 */

public class EchoInbountHandler1 extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        LogUtil.info(">>>>>EchoInbountHandler1.channelRead: ctx :" + ctx);

        // 通知执行下一个InboundHandler
        ctx.fireChannelRead(msg);
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        LogUtil.info(">>>>>EchoInbountHandler1.channelReadComplete");
        ctx.flush();
    }

}
