package com.signal;

import java.util.concurrent.TimeUnit;

/**
 * @author nn_liu
 * @Created 2017-08-18-13:17
 */

public class ShutdownHook implements Runnable
{
    @Override
    public void run() {
        System.out.println("ShutdownHook execute start...");
        System.out.println("Netty NioEventLoopGroup shutdownGracefully...");
        try {
            TimeUnit.SECONDS.sleep(10);//模拟应用进程退出前的处理操作
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("ShutdownHook execute end...");
        System.out.println("Sytem shutdown over, the cost time is 10000MS");
    }
}
