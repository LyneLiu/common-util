package com.lyne.classloader.concurrency.utility.countdownlatch.example;

/**
 * @author nn_liu
 * @Created 2018-03-08-13:27
 *
 * 执行结果：
 * Checking Network Service
 * Checking Database Service
 * Checking Cache Service
 * Cache Service is UP
 * Network Service is UP
 * Database Service is UP
 * External services validation completed !! Result was :: true
 * 
 */

public class Main {

    public static void main(String[] args)
    {
        boolean result = false;
        try {
            result = ApplicationStartupUtil.checkExternalServices();
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("External services validation completed !! Result was :: "+ result);
    }

}
