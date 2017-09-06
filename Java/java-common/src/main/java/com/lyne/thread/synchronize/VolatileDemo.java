package com.lyne.thread.synchronize;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by nn_liu on 2016/10/31.
 */


public class VolatileDemo {
    private static final Logger LOGGER = LoggerFactory.getLogger(VolatileDemo.class);

    /* volatile关键字定义的变量，用来确保将变量的更新操作通知到其它线程
     * Note：volatile适合先读取内存数据，再进行计算的场景，如果多个线程或进程同时读取并对数据进行计算的场景，数据有可能会出错
     * 参考链接：
     * http://lucumt.info/posts/java-volatile-keyword/
     * http://tutorials.jenkov.com/java-concurrency/volatile.html
     */
    private static volatile int MY_INT = 0;

    public static void main(String[] args) {
        new ChangeListener().start();
        new ChangeMaker().start();
    }

    static class ChangeListener extends Thread {
        @Override
        public void run() {
            /*监控MY_INT值*/
            int local_value = MY_INT;
            while ( local_value < 5){
                if( local_value!= MY_INT){
                    LOGGER.info("Got Change for MY_INT :"+ MY_INT);
                    local_value= MY_INT;
                }
            }
        }
    }

    static class ChangeMaker extends Thread{
        @Override
        public void run() {
            /*更改MY_INT值*/
            int local_value = MY_INT;
            while (MY_INT <5){
                LOGGER.info("Incrementing MY_INT to :"+ (local_value+1));
                MY_INT = ++local_value;
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) { e.printStackTrace(); }
            }
        }
    }
}
