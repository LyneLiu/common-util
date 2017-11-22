package com.lyne.design_pattern.producer_consumer_pattern;

import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author nn_liu
 * @Created 2017-11-22-11:02
 */

public class WaitNotifyModel implements Model {

    private final Object BUFFER_LOCK = new Object();

    private final Queue<Task> buffer = new LinkedList<>();

    private final int cap;

    private final AtomicInteger increTaskNo = new AtomicInteger(0);

    public WaitNotifyModel(int cap) {
        this.cap = cap;
    }

    @Override public Runnable newRunnableConsumer() {
        return new ConsumerImpl();
    }

    @Override public Runnable newRunnableProducer() {
        return new ProducerImpl();
    }


    private class ConsumerImpl extends AbstractConsumer {
        @Override public void consume() throws InterruptedException {
            synchronized (BUFFER_LOCK){
                while (buffer.isEmpty()){
                    BUFFER_LOCK.wait();
                }
                Task task = buffer.poll();
                assert task != null;
                // 固定时间范围的消费，模拟相对稳定的服务器处理过程
                Thread.sleep(500 + (long) (Math.random() * 500));
                System.out.println("consume: " + task.no);
                BUFFER_LOCK.notifyAll();
            }
        }
    }

    private class ProducerImpl extends AbstractProducer {
        @Override public void produce() throws InterruptedException {
            synchronized (BUFFER_LOCK){
                while (buffer.size() == cap){
                    System.out.println("waiting...");
                    BUFFER_LOCK.wait();
                }
                // 不定期生产，模拟随机的用户请求
                Thread.sleep((long) (Math.random() * 1000));
                Task task = new Task(increTaskNo.getAndIncrement());
                buffer.offer(task);
                System.out.println("produce: " + task.no);
                BUFFER_LOCK.notifyAll();
            }
        }
    }

    public static void main(String[] args) {
        Model model = new WaitNotifyModel(3);

        for (int i = 0; i < 2; i++) {
            new Thread(model.newRunnableConsumer()).start();
        }

        for (int i = 0; i < 5; i++) {
            new Thread(model.newRunnableProducer()).start();
        }
    }
}
