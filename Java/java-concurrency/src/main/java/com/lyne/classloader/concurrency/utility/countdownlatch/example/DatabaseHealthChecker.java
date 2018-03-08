package com.lyne.classloader.concurrency.utility.countdownlatch.example;

import java.util.concurrent.CountDownLatch;

/**
 * @author nn_liu
 * @Created 2018-03-08-13:24
 */

public class DatabaseHealthChecker extends BaseHealthChecker {

    public DatabaseHealthChecker (CountDownLatch latch)  {
        super("Database Service", latch);
    }

    @Override
    public void verifyService()
    {
        System.out.println("Checking " + this.getServiceName());
        try
        {
            Thread.sleep(10000);
        }
        catch (InterruptedException e)
        {
            e.printStackTrace();
        }
        System.out.println(this.getServiceName() + " is UP");
    }
}
