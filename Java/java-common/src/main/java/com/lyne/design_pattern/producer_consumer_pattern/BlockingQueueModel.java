package com.lyne.design_pattern.producer_consumer_pattern;

import lombok.Getter;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 关于Object的wait()、notify()、notifyall()详解：
 * http://blog.csdn.net/ns_code/article/details/17225469
 *
 * @author nn_liu
 * @Created 2017-11-21-18:34
 */
public class BlockingQueueModel implements Model {

    private final BlockingQueue<Task> queue;

    private final AtomicInteger increTaskNo = new AtomicInteger(0);

    public BlockingQueueModel(int cap) {
        // LinkedBlockingQueue 的队列是 lazy-init 的，但 ArrayBlockingQueue 在创建时就已经 init
        this.queue = new LinkedBlockingQueue<>(cap);
    }

    @Override public Runnable newRunnableConsumer() {
        return new ConsumerImpl();
    }

    @Override public Runnable newRunnableProducer() {
        return new ProducerImpl();
    }

    private class ConsumerImpl extends AbstractConsumer {

        @Override public void consume() throws InterruptedException {
            Task task = queue.take();
            // 固定时间范围的消费，模拟相对稳定的服务器处理过程
            Thread.sleep(500 + (long) (Math.random() * 500));
            System.out.println("consume: " + task.no);
        }
    }


    private class ProducerImpl extends AbstractProducer {

        @Override public void produce() throws InterruptedException {
            // 不定期生产，模拟随机的用户请求
            Thread.sleep((long) (Math.random() * 1000));
            Task task = new Task(increTaskNo.getAndIncrement());
            queue.put(task);
            System.out.println("produce: " + task.no);
        }
    }

    public static void main(String[] args) {

        Model model = new BlockingQueueModel(5);
        for (int i = 0; i < 1; i++) {
            new Thread(model.newRunnableConsumer()).start();
        }

        for (int i = 0; i < 1; i++) {
            new Thread(model.newRunnableProducer()).start();
        }
    }
}
