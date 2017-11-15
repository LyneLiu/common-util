package com.lyne.common;

import org.springframework.cache.Cache;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.support.SimpleCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;

import javax.annotation.Resource;
import java.util.Collection;
import java.util.HashSet;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by nn_liu on 2017/6/23.
 */

@Configuration
@EnableCaching
public class CacheConfig {

    @Resource(name = "redisCache")
    private RedisCache redisCache = null;

    @Bean
    @Resource(name = "caches")
    public SimpleCacheManager cacheManager(Collection<? extends Cache> caches) {
        SimpleCacheManager simpleCacheManager = new SimpleCacheManager();
        simpleCacheManager.setCaches(caches);
        return simpleCacheManager;
    }

    @Bean
    public Collection<? extends Cache> caches() {
        Collection caches = new HashSet<>();
        caches.add(redisCache);
        return caches;
    }

    @Bean
    public RedisCache redisCache() {
        RedisCache redisCache = new RedisCache();
        redisCache.setName("redisCache");
        redisCache.setTimeout(60 * 60  * 24l);
        return redisCache;
    }

    /**
     * Java通过Executors提供四种线程池，分别为：
     * newCachedThreadPool创建一个可缓存线程池，如果线程池长度超过处理需要，可灵活回收空闲线程，若无可回收，则新建线程。
     * newFixedThreadPool 创建一个定长线程池，可控制线程最大并发数，超出的线程会在队列中等待。
     * newScheduledThreadPool 创建一个定长线程池，支持定时及周期性任务执行。
     * newSingleThreadExecutor 创建一个单线程化的线程池，它只会用唯一的工作线程来执行任务，保证所有任务按照指定顺序(FIFO, LIFO, 优先级)执行。
     * @return
     */
    @Bean(name = "cachedThreadPool")
    public ExecutorService cachedThreadPool(){
        return Executors.newCachedThreadPool();
    }

    public static void main(String[] args) {

        // 创建一个ExpressionParser对象，用于解析表达式
        ExpressionParser parser = new SpelExpressionParser();
        // 使用直接量表达式
        Expression exp = parser.parseExpression("'###60'");
        System.out.println(exp.getValue(String.class));
        exp = parser.parseExpression("6.9");
        System.out.println(exp.getValue(Double.class));
    }

}
