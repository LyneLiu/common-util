package com.lyne.classloader.concurrency.utility.countdownlatch.example1;

import java.util.concurrent.CountDownLatch;

/**
 * @author nn_liu
 * @Created 2018-03-08-13:24
 */

public class CacheHealthChecker extends BaseHealthChecker {

    public CacheHealthChecker(CountDownLatch latch)  {
        super("Cache Service", latch);
    }

    @Override
    public void verifyService()
    {
        System.out.println("Checking " + this.getServiceName());
        try
        {
            Thread.sleep(6000);
        }
        catch (InterruptedException e)
        {
            e.printStackTrace();
        }
        System.out.println(this.getServiceName() + " is UP");
    }
}
