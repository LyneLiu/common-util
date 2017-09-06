package com.lyne.server;

import com.lyne.LogUtil;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelOutboundHandlerAdapter;
import io.netty.channel.ChannelPromise;

/**
 * @author nn_liu
 * @Created 2017-08-18-16:33
 */

public class EchoOutbountHandler1 extends ChannelOutboundHandlerAdapter {

    /**
     * 向client发送消息
     * @param ctx
     * @param msg
     * @param promise
     * @throws Exception
     */
    @Override
    public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
        LogUtil.info(">>>>>EchoOutbountHandler1.write");
        String response = "Hello, Netty World!";
        ByteBuf encoded = ctx.alloc().buffer(4 * response.length());
        encoded.writeBytes(response.getBytes());
        ctx.write(encoded);
        ctx.flush();
    }

}
