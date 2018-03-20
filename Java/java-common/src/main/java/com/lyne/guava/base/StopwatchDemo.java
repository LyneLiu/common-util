package com.lyne.guava.base;

import com.google.common.base.Stopwatch;
import org.springframework.util.StopWatch;

import java.util.concurrent.TimeUnit;

/**
 * @author nn_liu
 * @Created 2018-03-20-15:39
 */

public class StopwatchDemo {

    public static void guavaStopwatch() {
        Stopwatch stopwatch = Stopwatch.createStarted();

        for (int i = 0; i < 10; i++) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        stopwatch.stop();
        long mills = stopwatch.elapsed(TimeUnit.MILLISECONDS);

        System.out.println(mills);

    }

    public static void springStopwatch() {

        try {
            StopWatch sw = new StopWatch();

            sw.start("起床");
            Thread.sleep(1000);
            sw.stop();

            sw.start("洗漱");
            Thread.sleep(2000);
            sw.stop();


            sw.start("锁门");
            Thread.sleep(500);
            sw.stop();


            System.out.println(sw.prettyPrint());
            System.out.println(sw.getTotalTimeMillis());;
            System.out.println(sw.getTaskCount());

        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    public static void main(String[] args) {
        // guavaStopwatch();
        springStopwatch();
    }

}
