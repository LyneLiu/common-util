package com.lyne.classloader.concurrency.utility.countdownlatch.example2;

import java.util.Random;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.CyclicBarrier;

import static java.util.concurrent.TimeUnit.SECONDS;

/**
 * @author nn_liu
 * @Created 2018-03-09-17:18
 */

public class Demo01 {

    /**
     * 两个线程执行
     */
    private static void printThread(){
        Thread aThread = new Thread(new Runnable() {
            @Override public void run() {
                printNumber("Thread-A");
            }
        });

        Thread bThread = new Thread(new Runnable() {
            @Override public void run() {
                printNumber("Thread-B");
            }
        });

        aThread.start();
        bThread.start();
    }

    /**
     * 控制线程执行顺序
     */
    private static void printThreadWithJoin(){
        final Thread aThread = new Thread(new Runnable() {
            @Override public void run() {
                printNumber("Thread-A");
            }
        });

        Thread bThread = new Thread(new Runnable() {
            @Override public void run() {

                System.out.println("B 开始等待 A执行");
                try {
                    aThread.join();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                printNumber("Thread-B");
            }
        });

        aThread.start();
        bThread.start();
    }

    /**
     * 通过wait和notify控制线程执行过程
     */
    private static void printThreadWithWaitAndNotify(){

        final Object lock = new Object();

        final Thread aThread = new Thread(new Runnable() {
            @Override public void run() {

                synchronized (lock) {

                    System.out.println("Thread-A:1");

                    try {
                        System.out.println("Thread-A waiting...");
                        lock.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    System.out.println("Thread-A:2");
                    System.out.println("Thread-A:3");
                }
            }
        });

        Thread bThread = new Thread(new Runnable() {
            @Override public void run() {

                // Note:
                // synchronized同步方法（块）同一时刻只允许一个线程在里面,
                // 所以notify执行后，aThread并没有立即执行
                synchronized (lock) {
                    System.out.println("Thread-B:1");
                    System.out.println("Thread-B:2");

                    try {
                        lock.notify();
                        SECONDS.sleep(10);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    System.out.println("Thread-B:3");

                }
            }
        });

        aThread.start();
        bThread.start();
    }

    /**
     * 四个线程 A B C D，其中 D 要等到 A B C 全执行完毕后才执行，而且 A B C 是同步运行的
     * Note:
     * CountDownLatch是一个倒数计时器，调用await()时会检测计数器是够为0，若不为0保持等待状态；
     * 当执行完三个线程的时候，计数器会被置为0，此刻会触发D的await()运行结束，继续执行下去。
     */
    private static void printThreadWithCountDownLatch(){

        int worker = 3;
        final CountDownLatch countDownLatch = new CountDownLatch(worker);

        Thread dThread = new Thread(new Runnable() {
            @Override public void run() {
                System.out.println("D is waiting for other three threads");
                try {
                    // Note：不要错把await()方法写成wait()方法
                    countDownLatch.await();
                    System.out.println("All done, D starts working");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        dThread.start();

        for (char threadName = 'A'; threadName <= 'C'; threadName ++){
            final String tN = String.valueOf(threadName);
            new Thread(new Runnable() {
                @Override
                public void run() {
                    System.out.println(tN + " is working");
                    try {
                        Thread.sleep(100);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    System.out.println(tN + " finished");
                    countDownLatch.countDown();
                }
            }).start();
        }

    }

    /**
     * 三个运动员各自准备，等到三个人都准备好后，再一起跑
     * Note:
     * CountDownLatch的await()方法适合多个线程执行完成后唤醒单个线程的场景，
     * 如果想多个线程被同时唤醒可以使用CyclicBarrier数据结构。
     */
    private static void printThreadWithCyclicBarrier(){
        int runner = 3;
        final CyclicBarrier cyclicBarrier = new CyclicBarrier(3);

        final Random random = new Random();
        for (char runnerName='A'; runnerName <= 'C'; runnerName++) {
            final String rN = String.valueOf(runnerName);
            new Thread(new Runnable() {
                @Override public void run() {
                    long prepareTime = random.nextInt(1000) + 100;
                    System.out.println(rN + " is preparing for time: " + prepareTime);
                    try {
                        Thread.sleep(prepareTime);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    try {
                        System.out.println(rN + " is prepared, waiting for others——" + System.currentTimeMillis());
                        cyclicBarrier.await();  // wait for others
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } catch (BrokenBarrierException e) {
                        e.printStackTrace();
                    }

                    System.out.println(rN + " starts running——" + System.currentTimeMillis()); // all is ready
                }
            }).start();
        }
    }

    private static void printNumber(String threadName) {
        int i=0;
        while (i++ < 3) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(threadName + " print: " + i);
        }
    }

    public static void main(String[] args) {
        // 两个线程并发执行
        //printThread();

        // B 等待 A线程执行,通过join控制
        //printThreadWithJoin();

        // A B通过wait和notify控制执行流程
        //printThreadWithWaitAndNotify();

        //printThreadWithCountDownLatch();

        printThreadWithCyclicBarrier();
    }

}
