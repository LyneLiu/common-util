package com.lyne.guava.cache;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;

/**
 * @author nn_liu
 * @Created 2017-11-13-14:19
 */

public class CallableDemo {


    public void testCallable() throws ExecutionException {
        Cache<String, String> localCacheProvider
                = CacheBuilder.newBuilder().build();

        String cacheValue1 = localCacheProvider.getIfPresent("jerry");
        String cacheValue2 = localCacheProvider.get("jerry", new Callable<String>() {
            @Override
            public String call() throws Exception {
                // return redisClient.get("jerry")
                // 与CacheLoader的方式类似，返回结果不能为null
                return "jerry";
            }
        });

        System.out.println("jerry result: " +cacheValue1);
        System.out.println("jerry result: " +cacheValue2);
    }

    public static void main(String[] args) throws ExecutionException {
        CallableDemo demo = new CallableDemo();
        demo.testCallable();
    }

}
