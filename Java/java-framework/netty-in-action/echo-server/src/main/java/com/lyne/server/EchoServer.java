package com.lyne.server;

import com.lyne.LogUtil;
import io.netty.bootstrap.Bootstrap;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.util.concurrent.Future;

import java.net.InetSocketAddress;

/**
 * Created by nn_liu on 2017/6/12.
 */
public class EchoServer {

    private final int port;

    public EchoServer(int port) {
        this.port = port;
    }

    public static void main(String[] args) {
        if (args.length != 1){
            System.out.println("Usage: "+EchoServer.class.getSimpleName()+" <port>");
        }

        int port = Integer.parseInt(args[0]);
        EchoServer server = new EchoServer(port);
        server.start();

        // 阻塞线程，所以无法执行到stop操作
        server.stop();
    }

    /**
     * 引导过程的大致步骤：
     * 1、创建一个ServerBootstrap的实例以引导和绑定服务器；
     * 2、创建并分配一个NioEventLoopGroup实例以进行事件的处理，如接受新连接以及读/写数据；
     * 3、指定服务器绑定的本地的InetSocketAddress；
     * 4、使用一个EchoServerHandler的实例初始化每一个新的Channel；
     * 5、调用ServerBootstrap.bind()方法绑定服务器
     *
     */
    private void start() {
        final EchoServerHandler serverHandler = new EchoServerHandler();
        /* 线程池的默认线程个数为当前服务器CPU核心数目的2倍 */
        EventLoopGroup bossGroup = new NioEventLoopGroup(1);
        EventLoopGroup workerGroup = new NioEventLoopGroup(3);
        try {
            ServerBootstrap bootstrap = new ServerBootstrap();
            bootstrap.group(bossGroup,workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .localAddress(new InetSocketAddress(port))
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        protected void initChannel(SocketChannel ch) throws Exception {
                            /*
                             * =============================================================
                             * echo server handler 使用示例
                             * =============================================================
                             */
                            //ch.pipeline().addLast(serverHandler);


                            /*
                             * =============================================================
                             * multi in and out client handler 使用示例
                             * 注意：OutboundHandler在注册的时候需要放在最后一个InboundHandler之前，否则将无法传递到OutboundHandler！
                             * =============================================================
                             */
                            // 注册两个OutboundHandler，执行顺序为注册顺序的逆序，所以应该是OutboundHandler2 OutboundHandler1
                            ch.pipeline().addLast(new EchoOutbountHandler1());
                            ch.pipeline().addLast(new EchoOutbountHandler2());
                            // 注册两个InboundHandler，执行顺序为注册顺序，所以应该是InboundHandler1 InboundHandler2
                            ch.pipeline().addLast(new EchoInbountHandler1());
                            ch.pipeline().addLast(new EchoInbountHandler2());
                        }
                    })
                    .option(ChannelOption.SO_BACKLOG, 128)
                    .childOption(ChannelOption.SO_KEEPALIVE, true); ;
            ChannelFuture future = bootstrap.bind().sync();
            // Wait until the server socket is closed.
            // In this example, this does not happen, but you can do that to gracefully
            // shut down your server.
            future.channel().closeFuture().awaitUninterruptibly();
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            try {
                LogUtil.info("Closing echo server...");
                bossGroup.shutdownGracefully().sync();
                workerGroup.shutdownGracefully().sync();
            } catch (InterruptedException e) {
                // do nothing
            }
        }
    }

    public void stop(){
        EventLoopGroup group = new NioEventLoopGroup(); //1
        Bootstrap bootstrap = new Bootstrap(); //2
        bootstrap.group(group)
                .channel(NioSocketChannel.class);
        Future<?> future = group.shutdownGracefully(); //3
        // block until the group has shutdown
        try {
            future.sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
