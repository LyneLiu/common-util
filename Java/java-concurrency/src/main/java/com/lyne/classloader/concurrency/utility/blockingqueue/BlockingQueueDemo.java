package com.lyne.classloader.concurrency.utility.blockingqueue;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;

/**
 *
 * http://tutorials.jenkov.com/java-util-concurrent/blockingqueue.html
 *
 * @author nn_liu
 * @Created 2017-11-22-14:09
 */
public class BlockingQueueDemo {

    public static void main(String[] args) {

        BlockingQueue<Integer> queue = new LinkedBlockingQueue<>();

        Producer producer = new Producer(queue);
        Consumer consumer = new Consumer(queue);

        for (int i = 0; i < 5; i++) {
            new Thread(producer).start();
        }

        for (int i = 0; i < 3; i++) {
            new Thread(consumer).start();
        }
    }

    private static class Producer implements Runnable{

        private final AtomicInteger increTaskNo = new AtomicInteger(0);

        protected BlockingQueue queue = null;

        public Producer(BlockingQueue queue){
            this.queue = queue;
        }

        public void run() {
            try {
                while (true){
                    queue.put(increTaskNo.incrementAndGet());
                    System.out.println("producing..." + increTaskNo);
                    Thread.sleep(1000);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private static class Consumer implements Runnable{

        protected BlockingQueue queue = null;

        public Consumer(BlockingQueue queue){
            this.queue = queue;
        }

        public void run() {
            try {
                while (true){
                    System.out.println("consuming..." + queue.take());
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

}
