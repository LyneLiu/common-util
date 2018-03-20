package com.lyne.classloader.concurrency.utility.atomic;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.CyclicBarrier;

/**
 * @author nn_liu
 * @Created 2018-03-19-16:53
 */

public class SimulateCAS {

    private int value;

    public SimulateCAS(int value) {
        this.value = value;
    }

    public synchronized int getValue(){
        return value;
    }

    public boolean compareAndSet(int expect, int newValue){
        synchronized (this){
            if (value == expect){
                value = newValue;
                return true;
            }
        }

        return false;
    }

    public static void multiThread(final SimulateCAS simulateCAS, final CountDownLatch countDownLatch){

        for (int i = 0; i < 100 ; i++) {
            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    int value = simulateCAS.getValue();
                    System.out.println(Thread.currentThread().getName() + " init value:" + value);
                    boolean result = simulateCAS.compareAndSet(value, value + 1);
                    System.out.println(Thread.currentThread().getName() + " compare and set result:" + result + " value:" + simulateCAS.getValue());
                    countDownLatch.countDown();
                }
            });
            thread.start();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        CountDownLatch countDownLatch = new CountDownLatch(100);

        SimulateCAS simulateCAS = new SimulateCAS(1);
        multiThread(simulateCAS, countDownLatch);

        countDownLatch.await();
        System.out.println("CAS result:" + simulateCAS.getValue());
    }

}
