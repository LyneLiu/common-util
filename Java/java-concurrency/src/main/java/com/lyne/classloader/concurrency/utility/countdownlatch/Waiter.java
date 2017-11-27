package com.lyne.classloader.concurrency.utility.countdownlatch;

import java.util.concurrent.CountDownLatch;

/**
 * @author nn_liu
 * @Created 2017-11-24-9:40
 */
public class Waiter implements Runnable{

    CountDownLatch latch = null;

    public Waiter(CountDownLatch latch) {
        this.latch = latch;
    }

    public void run() {
        try {
            latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("Waiter Released");
    }
}

