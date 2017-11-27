package com.lyne.classloader.concurrency.utility.countdownlatch;

import java.util.concurrent.CountDownLatch;

/**
 * @author nn_liu
 * @Created 2017-11-24-9:39
 */

public class CountDownLatchDemo {

    public static void main(String[] args) throws InterruptedException {

        CountDownLatch latch = new CountDownLatch(6);

        Waiter      waiter      = new Waiter(latch);
        Decrementer decrementer = new Decrementer(latch);

        new Thread(waiter)     .start();
        new Thread(decrementer).start();

        Thread.sleep(4000);

    }

}
