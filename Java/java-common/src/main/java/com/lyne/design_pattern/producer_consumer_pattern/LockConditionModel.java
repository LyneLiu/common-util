package com.lyne.design_pattern.producer_consumer_pattern;

import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Lock和synchronize区别：
 * http://hanhailong.com/2016/12/10/Synchronized%E4%B8%8ELock%E9%94%81%E7%9A%84%E5%8C%BA%E5%88%AB/
 *
 * @author nn_liu
 * @Created 2017-11-22-11:26
 */

public class LockConditionModel implements Model {

    private final Lock BUFFER_LOCK = new ReentrantLock();

    private final Condition BUFFER_COND = BUFFER_LOCK.newCondition();

    private final Queue<Task> buffer = new LinkedList<>();

    private final int cap;

    private final AtomicInteger increTaskNo = new AtomicInteger(0);

    public LockConditionModel(int cap) {
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
            BUFFER_LOCK.lockInterruptibly();
            try {
                while (buffer.isEmpty()){
                    BUFFER_COND.await();
                }
                Task task = buffer.poll();
                assert task != null;
                // 固定时间范围的消费，模拟相对稳定的服务器处理过程
                Thread.sleep(500 + (long) (Math.random() * 500));
                System.out.println("consume: " + task.no);

                BUFFER_COND.signalAll();
            }finally {
                BUFFER_LOCK.unlock();
            }
        }
    }


    private class ProducerImpl extends AbstractProducer {
        @Override public void produce() throws InterruptedException {
            // 不定期生产，模拟随机的用户请求
            Thread.sleep((long) (Math.random() * 1000));
            BUFFER_LOCK.lockInterruptibly();
            try {
                while (buffer.size() == cap) {
                    BUFFER_COND.await();
                }
                Task task = new Task(increTaskNo.getAndIncrement());
                buffer.offer(task);
                System.out.println("produce: " + task.no);
                BUFFER_COND.signalAll();
            } finally {
                BUFFER_LOCK.unlock();
            }
        }
    }

    public static void main(String[] args) {
        Model model = new LockConditionModel(3);
        for (int i = 0; i < 2; i++) {
            new Thread(model.newRunnableConsumer()).start();
        }
        for (int i = 0; i < 5; i++) {
            new Thread(model.newRunnableProducer()).start();
        }
    }
}
