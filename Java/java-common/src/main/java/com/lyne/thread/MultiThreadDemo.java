package com.lyne.thread;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author nn_liu
 * @Created 2018-03-23-10:47
 */

public class MultiThreadDemo {

    private static ExecutorService executorService;

    static{
        executorService = Executors.newFixedThreadPool(5);
    }

    public static void foo1(){
        System.out.println("1");
    }

    public static void foo2(){
        System.out.println("2");
    }

    public static void bar(){
        System.out.println("3");
    }

    public static void processWithCountDownLatch(){

        CountDownLatch countDownLatch = new CountDownLatch(2);

        Thread thread1 = new Thread(() -> {
            foo1();
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            countDownLatch.countDown();
        });

        Thread thread2 = new Thread(() -> {
            foo2();
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            countDownLatch.countDown();
        });

        Thread thread3 = new Thread(() -> {
            try {
                countDownLatch.await();
                bar();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        thread1.start();
        thread2.start();
        thread3.start();
    }

    public static void main(String[] args) {

        processWithCountDownLatch();

    }

}
