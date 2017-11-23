package com.lyne.classloader.concurrency.utility.blockingqueue;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

/**
 * ArrayBlockingQueue：存储数据方式为FIFO，维护定长数据，无法真正并行运行。
 *
 * http://tutorials.jenkov.com/java-util-concurrent/arrayblockingqueue.html
 *
 * @author nn_liu
 * @Created 2017-11-23-11:05
 */
public class ArrayBlockingQueueDemo {

    public static void main(String[] args) throws InterruptedException {
        BlockingQueue<String> queue = new ArrayBlockingQueue<String>(1024);

        // throw exception
        queue.add("1");
        System.out.println("queue remove:" + queue.remove("2"));
        System.out.println("queue element:" + queue.element());
        queue.remove("1");
        System.out.println("current queue size:" + queue.size());

        // special value
        queue.offer("1");
        System.out.println("queue poll:" + queue.poll());
        System.out.println("queue peek:" + queue.peek());
        System.out.println("current queue size:" + queue.size());

        // blocks
        queue.put("1");
        System.out.println("queue take:" + queue.take());
        System.out.println("current queue size:" + queue.size());

        // times out
        queue.offer("1", 1000, TimeUnit.MILLISECONDS);
        System.out.println("queue poll with timeout:" + queue.poll(1000, TimeUnit.MILLISECONDS));
        System.out.println("current queue size:" + queue.size());


    }

}
