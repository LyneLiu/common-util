package com.lyne.classloader.concurrency.utility.countdownlatch;

import java.util.concurrent.CountDownLatch;

/**
 * @author nn_liu
 * @Created 2017-11-24-9:41
 */

public class Decrementer implements Runnable {

    CountDownLatch latch = null;

    public Decrementer(CountDownLatch latch) {
        this.latch = latch;
    }

    public void run() {

        try {
            Thread.sleep(1000);
            System.out.println("countDowning... 1");
            this.latch.countDown();

            Thread.sleep(1000);
            System.out.println("countDowning... 2");
            this.latch.countDown();

            Thread.sleep(1000);
            System.out.println("countDowning... 3");
            this.latch.countDown();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
