package com.lyne.classloader.concurrency.utility.blockingqueue;

import java.util.concurrent.DelayQueue;
import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;

import static java.util.concurrent.TimeUnit.SECONDS;

/**
 *
 * 使用场景：
 * 1、缓存系统的设计：可以用DelayQueue保存缓存元素的有效期，使用一个线程循环查询DelayQueue，一旦能从DelayQueue中获取元素时，表示缓存有效期到了。
 * 2、定时任务调度：使用DelayQueue保存当天将会执行的任务和执行时间，一旦从DelayQueue中获取到任务就开始执行，从比如TimerQueue就是使用DelayQueue实现的。
 * @author nn_liu
 * @Created 2017-11-23-13:57
 */

public class DelayQueueDemo {

    public static void main(String[] args) {

        DelayQueue<DelayedElement>  delayQueue = new DelayQueue<>();
        long currMills = System.currentTimeMillis();

        DelayedElement element01 = new DelayedElement("e01", currMills + 10000);
        DelayedElement element02 = new DelayedElement("e02", currMills + 15000);
        DelayedElement element03 = new DelayedElement("e03", currMills + 15000);

        delayQueue.add(element01);
        delayQueue.add(element02);
        delayQueue.add(element03);

        try {
            SECONDS.sleep(1);
        }catch (InterruptedException e){
            e.printStackTrace();
        }

        while (delayQueue.size() > 0){
            try {
                DelayedElement element = delayQueue.take();
                System.out.println("current time in ms: " + System.currentTimeMillis() + ", element:" + element.name);
            }catch (InterruptedException e){
                e.printStackTrace();
            }
        }

    }

    private static class DelayedElement implements Delayed{

        private long time;

        private String name;

        private DelayedElement(String name, long time) {
            this.time = time;
            this.name = name;
        }

        /**
         * Note: 阻塞过程中使用的是NANOSECONDS时间单位
         * http://standalone.iteye.com/blog/1876771
         * @param unit
         * @return
         */
        @Override public long getDelay(TimeUnit unit) {
            return unit.convert(time - System.currentTimeMillis(), TimeUnit.NANOSECONDS);
            //return time - System.currentTimeMillis();
        }

        @Override public int compareTo(Delayed o) {
            if (this.time < ((DelayedElement)o).time){
                return -1;
            }else if (this.time > ((DelayedElement)o).time){
                return 1;
            }else {
                return 0;
            }
        }
    }
}
