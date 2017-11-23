package com.lyne.classloader.concurrency.utility.blockingqueue;

import java.util.concurrent.BlockingDeque;
import java.util.concurrent.LinkedBlockingDeque;

/**
 *
 * 双端链表阻塞队列（BlockingDeque）：实现类为LinkedBlockingDeque。
 * Note：LinkedBlockingQueue和LinkedBlockingDeque的区别是双端阻塞队列使用全局锁对Node进行操作。
 * @author nn_liu
 * @Created 2017-11-23-15:23
 */

public class BlockingDequeDemo {

    public static void main(String[] args) throws InterruptedException {
        BlockingDeque<String> deque = new LinkedBlockingDeque<String>();

        deque.addFirst("first");
        deque.addLast("last");

        System.out.println(deque.takeFirst());
        System.out.println(deque.takeLast());
    }

}
