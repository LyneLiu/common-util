package com.lyne.thread;

/**
 *
 * java.lang.IllegalMonitorStateException异常
 * 参考链接：
 * https://leokongwq.github.io/2017/02/24/java-why-wait-notify-called-in-synchronized-block.html
 *
 * @author nn_liu
 * @Created 2018-04-03-17:50
 */

public class WaitNotifyDemo {

    private static final Object lock = new Object();

    public static void funForWaitNotify(){
        Thread thread1 = new Thread(new Runnable() {
            @Override
            public void run() {
                synchronized (lock){
                    try {
                        lock.wait();
                        Thread.sleep(8000);
                        System.out.println(Thread.currentThread().getName());
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        Thread thread2 = new Thread(new Runnable() {
            @Override
            public void run() {
                synchronized (lock){
                    try {
                        Thread.sleep(6000);
                        System.out.println(Thread.currentThread().getName());
                        lock.notify();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        thread1.start();
        thread2.start();
    }


    public static void main(String[] args) {
        funForWaitNotify();
    }

}
