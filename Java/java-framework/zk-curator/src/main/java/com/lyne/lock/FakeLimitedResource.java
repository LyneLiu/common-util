package com.lyne.lock;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

/**
 * @author nn_liu
 * @Created 2017-11-30-20:40
 */
@Slf4j
public class FakeLimitedResource {

    private final AtomicBoolean inUse = new AtomicBoolean(false);

    private final AtomicReference<Integer> num = new AtomicReference<Integer>(0);

    public void use() throws InterruptedException {

        /*
         * 真实环境中我们会在这里访问/维护一个共享的资源
         * 这个例子在使用锁的情况下不会非法并发异常IllegalStateException
         * 但是在无锁的情况由于sleep了一段时间，很容易抛出异常
         */

        Integer currNum = num.get();
        log.info(String.format("%s resource status:%d", Thread.currentThread().getName(), currNum));

        if (!num.compareAndSet(0, currNum + 10)){
            throw new IllegalStateException("Needs to be used by one client at a time");
        }

        try {
            long time = (long) (300 * Math.random());
            System.out.println(String.format("Sleeping for %d...", time));
            Thread.sleep(time);
        }finally {
            Integer resultNum = num.get();
            log.info(String.format("recovering the num:%d", resultNum));
            num.set(resultNum - 10);
        }

    }

}
