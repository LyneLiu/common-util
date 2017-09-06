package com.lyne.client;

import com.lyne.LogUtil;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.net.InetSocketAddress;

/**
 * Created by nn_liu on 2017/6/12.
 */
public class EchoClient {

    private final String host;
    private final int port;

    public EchoClient(String host, int port) {
        this.host = host;
        this.port = port;
    }

    public static void main(String[] args) {
        if (args.length != 2){
            System.out.println("Usage: "+EchoClient.class.getSimpleName()+" <host> <port>");
        }

        final String host = args[0];
        final int port = Integer.parseInt(args[1]);
        for (int i = 0; i < 1; i++) {
            Thread thread = new Thread("thread-"+i){
                public void run(){
                    new EchoClient(host,port).start();
                }
            };
            thread.start();
        }
    }

    private void start() {
        EventLoopGroup group = new NioEventLoopGroup();
        try {
            Bootstrap bootstrap = new Bootstrap();
            bootstrap.group(group)
                    .channel(NioSocketChannel.class)
                    .remoteAddress(new InetSocketAddress(host,port))
                    .handler(new ChannelInitializer<SocketChannel>() {
                        protected void initChannel(SocketChannel ch) throws Exception {
                            /*
                             * =============================================================
                             * echo client handler 使用示例
                             * =============================================================
                             */
                            //ch.pipeline().addLast(new EchoClientHandler());

                            /*
                             * =============================================================
                             * multi in and out client handler 使用示例
                             * =============================================================
                             */
                            ch.pipeline().addLast(new InAndOutClientHandler());
                        }
                    })
                    .option(ChannelOption.SO_KEEPALIVE, true);
            ChannelFuture future = bootstrap.connect().sync();
            future.channel().closeFuture().sync();
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            try {
                LogUtil.info("Closing echo client ...");
                group.shutdownGracefully().sync();
            } catch (InterruptedException e) {
                // do nothing
            }
        }
    }

}
