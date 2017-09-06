package com.lyne.thread;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by nn_liu on 2017/1/24.
 */
public class DeamonThreadDemo {

    private final static Logger logger = LoggerFactory.getLogger(DeamonThreadDemo.class);

    public static void main(String[] args) {
        try {
            Thread deamon = new Thread(new CustomeThread(),"deamon_thread");
            deamon.setDaemon(true); // 设置线程为守护线程
            deamon.start();

            Thread.sleep(30000);
            System.out.println("Finishing program!");
        }catch (Exception e){

        }

    }

}
