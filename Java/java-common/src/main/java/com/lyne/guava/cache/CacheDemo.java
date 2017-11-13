package com.lyne.guava.cache;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;

import java.util.concurrent.TimeUnit;

/**
 * @author nn_liu
 * @Created 2017-11-13-14:54
 */

public class CacheDemo {

    public void testCache(){
        Cache<String, String> cache =
                CacheBuilder.newBuilder()
                .maximumSize(1024)      //cache中允许的key的个数
                //.maximumWeight(1000)    每个K-V占用不同的内存量，可以通过“weight”来描述这一指标
                .expireAfterAccess(10, TimeUnit.SECONDS)    //当key在被“read/write”操作之后，空闲多久后被删除
                //.expireAfterWrite(10, TimeUnit.SECONDS)     当key被write(put或者reload)操作之后，空闲多久后被删除
                .weakKeys()     //CacheBuilder.weakValues(),CacheBuilder.softValues() 将key存为WeakReference，如果JVM中，此key没有任何被任何“强引用”、“软引用”对象引用它，那么此K-V将会从cache中移除。
                .build();

        String cacheValue1 = cache.getIfPresent("jerry");
        System.out.println("init cache:" + cacheValue1);
        cache.put("jerry", "jerry");    //显示插入缓存对象
        String cacheValue2 = cache.getIfPresent("jerry");
        System.out.println("after put:" + cacheValue2);
        cache.invalidate("jerry");     //显性移除缓存对象，Cache.invalidate(key)、Cache.invalidateAll(keys)、Cache.invalidateAll()
        String cacheValue3 = cache.getIfPresent("jerry");
        System.out.println("after invalidate:" + cacheValue3);
    }

    public static void main(String[] args) {
        CacheDemo demo = new CacheDemo();
        demo.testCache();
    }

}
