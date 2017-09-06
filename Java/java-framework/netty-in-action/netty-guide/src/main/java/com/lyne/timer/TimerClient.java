package com.lyne.timer;

import com.lyne.LogUtil;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author nn_liu
 * @Created 2017-08-23-19:04
 */

public class TimerClient {

    private static volatile int count = 0;

    public static void main(String[] args) {

        String host = "localhost";
        int port = Integer.parseInt("8080");
        EventLoopGroup workerGroup = new NioEventLoopGroup();

        // 使用newFixedThreadPool防止栈溢出，newCachedThreadPool创建线程会产生栈溢出
        ExecutorService threadPoolExecutor = Executors.newFixedThreadPool(10);

        try {

            for (int i = 0; i < 100; i++) {
                threadPoolExecutor.submit(() -> {
                    try {
                        Bootstrap b = new Bootstrap(); // (1)
                        b.group(workerGroup); // (2)
                        b.channel(NioSocketChannel.class); // (3)
                        b.option(ChannelOption.SO_KEEPALIVE, true); // (4)
                        b.handler(new ChannelInitializer<SocketChannel>() {
                            @Override public void initChannel(SocketChannel ch)
                                    throws Exception {
                                ch.pipeline().addLast(new TimeClientHandler());
                            }
                        });

                        // 启动客户端
                        ChannelFuture f = b.connect(host, port).sync(); // (5)

                        // 等待连接关闭
                        f.channel().closeFuture().sync();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } finally {
                        count++;
                        LogUtil.warn("exec num:" + count);
                        workerGroup.shutdownGracefully();
                    }
                });
            }

        } catch (Exception e) {
            // do nothing
        } finally {
            threadPoolExecutor.shutdown();
        }
    }

}
