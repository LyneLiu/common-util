package com.lyne.server;

import com.lyne.LogUtil;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelOutboundHandlerAdapter;
import io.netty.channel.ChannelPromise;

/**
 * @author nn_liu
 * @Created 2017-08-18-16:33
 */

public class EchoOutbountHandler2 extends ChannelOutboundHandlerAdapter {

    @Override
    public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
        LogUtil.info(">>>>>EchoOutbountHandler2.write");
        // 执行下一个OutboundHandler
        super.write(ctx, msg, promise);
    }


}
