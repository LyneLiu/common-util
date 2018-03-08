package com.lyne.classloader.concurrency.utility.countdownlatch.example;

import java.util.concurrent.CountDownLatch;

/**
 * @author nn_liu
 * @Created 2018-03-08-13:23
 */

public class NetworkHealthChecker extends BaseHealthChecker
{
    public NetworkHealthChecker (CountDownLatch latch)  {
        super("Network Service", latch);
    }

    @Override
    public void verifyService()
    {
        System.out.println("Checking " + this.getServiceName());
        try
        {
            Thread.sleep(7000);
        }
        catch (InterruptedException e)
        {
            e.printStackTrace();
        }
        System.out.println(this.getServiceName() + " is UP");
    }
}
