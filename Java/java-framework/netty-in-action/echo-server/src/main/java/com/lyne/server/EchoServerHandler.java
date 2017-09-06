package com.lyne.server;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.CharsetUtil;

/**
 * @Sharable标识一个ChannelHandler可以被多个Channel安全地共享
 *
 * Created by nn_liu on 2017/6/12.
 */

@ChannelHandler.Sharable
public class EchoServerHandler extends ChannelInboundHandlerAdapter{

    /**
     * 获取Channel中的消息
     * @param ctx
     * @param msg
     */
    public void channelRead(ChannelHandlerContext ctx, Object msg){
        ByteBuf in = (ByteBuf) msg;
        System.out.println("Server received msg:"+in.toString(CharsetUtil.UTF_8));
        ctx.write(in);  // 将接收到的消息写给发送者，并且不冲刷出站消息
    }

    public void channelReadComplete(ChannelHandlerContext ctx){
        ctx.writeAndFlush(Unpooled.EMPTY_BUFFER)
                /*
                 * =============================================================
                 * 执行该操作，会关闭server和client建立的channel，即使client端采取阻塞的方式
                 * =============================================================
                 */
                .addListener(ChannelFutureListener.CLOSE);  //将消息冲刷至远程节点，并关闭该channel，该操作会关闭当前的channel

        /*
         * =============================================================
         * close the parent channel (the one attached to the bind)
         * =============================================================
         */
        // ctx.channel().parent().close();
    }

    /**
     * 处理异常信息，可以执行连接重试等操作
     * @param ctx
     * @param cause
     */
    public void exceptionCaught(ChannelHandlerContext ctx,Throwable cause){
        cause.printStackTrace();
        ctx.close();
    }



}
