package com.redis;

import com.lyne.utils.SerializeUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.redisson.Redisson;
import org.redisson.api.*;
import org.redisson.config.Config;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.Pipeline;
import redis.clients.jedis.Response;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ExecutionException;

/**
 *
 * 测试redis pipeline（管道）
 * 1、Jedis提供接口相对较灵活，功能更强大些；
 * 2、Spring RedisTemplate对redis提供的接口进行封装，通过管道处理时返回数据为字节数据，需要自定义序列化/反序列化方式；
 * 3、Redisson对redis提供的接口进行封装，提供了对应Java基础类型的接口，方便程序开发，相对较便捷。
 *
 * @author nn_liu
 * @Created 2017-11-20-10:24
 */

@RunWith(SpringRunner.class)
@SpringBootTest
public class RedisPipelineTest {

    /**
     * Note:
     * 需要使用StringRedisTemplate，而不是RedisTemplate。
     * 否则，redisTemplate.keys("*")获取为empty set！
     */
    @Autowired
    private StringRedisTemplate redisTemplate;

    /**
     * Jedis client的方式调用
     */
    @Test
    public void testJedisPipeline(){
        Jedis redis = new Jedis("127.0.0.1", 6379, 400000);
        Map<String,String> data = new HashMap<String,String>();
        redis.select(4);
        redis.flushDB();
        //hmset
        long start = System.currentTimeMillis();
        //直接hmset
        for (int i=0;i<10000;i++) {
            data.clear();
            data.put(String.format("\"%s\"","k_" + i), String.format("\"%s\"","v_" + i));
            redis.hmset("key_" + i, data);
        }
        long end = System.currentTimeMillis();
        System.out.println("dbsize:[" + redis.dbSize() + "] .. ");
        System.out.println("hmset without pipeline used [" + (end - start) + "] milliseconds ..");
        redis.select(4);
        //redis.flushDB();

        //使用pipeline hmset
        Pipeline p = redis.pipelined();
        start = System.currentTimeMillis();
        for (int i=0;i<10000;i++) {
            data.clear();
            data.put(String.format("\"%s\"","k_" + i), String.format("\"%s\"","v_" + i));
            p.hmset("key_" + i, data);
        }
        p.sync();
        end = System.currentTimeMillis();
        System.out.println("dbsize:[" + redis.dbSize() + "] .. ");
        System.out.println("hmset with pipeline used [" + (end - start) + "] milliseconds ..");

        //hmget
        Set<String> keys = redis.keys("*");
        //直接使用Jedis hgetall
        start = System.currentTimeMillis();
        Map<String,Map<String,String>> result = new HashMap<>();
        for(String key : keys) {
            result.put(key, redis.hgetAll(key));
        }
        end = System.currentTimeMillis();
        System.out.println("result size:[" + result.size() + "] ..");
        System.out.println("hgetAll without pipeline used [" + (end - start) + "] milliseconds ..");

        //使用pipeline hgetall
        Map<String,Response<Map<String,String>>> responses = new HashMap<>(keys.size());
        result.clear();
        start = System.currentTimeMillis();
        for(String key : keys) {
            responses.put(key, p.hgetAll(key));
        }
        p.sync();
        for(String k : responses.keySet()) {
            result.put(k, responses.get(k).get());
        }
        end = System.currentTimeMillis();
        System.out.println("result size:[" + result.size() + "] ..");
        System.out.println("hgetAll with pipeline used [" + (end - start) + "] milliseconds ..");

        redis.disconnect();

    }

    /**
     * RedisTemplate的管道是通过RedisConnection进行操作，底层数据为string或者字节数组，需要进行序列化/反序列化
     *
     */
    @Test
    public void testRedisTemplate(){

        RedisObjectSerializer serializer = new RedisObjectSerializer();

        Map<String, Map<String, String>> result = new HashMap<>();
        Set<String> keys = redisTemplate.keys("*");

        //redisTemplate.getConnectionFactory().getConnection().flushDb();
        long start = System.currentTimeMillis();

        redisTemplate.executePipelined((RedisCallback<Object>) connection -> {
            for(String key : keys) {
                Map<byte[], byte[]> resultValue = connection.hGetAll(serializer.serialize(key));
                result.put(key, SerializeUtil.unserializehmbb2mss(resultValue));
            }

            return null;
        }, serializer);

        long end = System.currentTimeMillis();
        System.out.println("result size:[" + result.size() + "] ..");
        System.out.println("redistemplate with pipeline used [" + (end - start) + "] milliseconds ..");
    }

    /**
     * Redisson
     * https://github.com/redisson/redisson/wiki/Redisson%E9%A1%B9%E7%9B%AE%E4%BB%8B%E7%BB%8D
     */
    @Test
    public void testRedisson() throws ExecutionException, InterruptedException {

        Config config = new Config();
        config.useSingleServer().setAddress("redis://127.0.0.1:6379").setDatabase(4);
        RedissonClient redisson = Redisson.create(config);


        Map<String, Map<String, String>> result = new HashMap<>();
        RKeys keys = redisson.getKeys();
        //keys.flushdb();

         /*1、直接访问*/
        long start = System.currentTimeMillis();
        for (String key: keys.getKeys()) {
            result.put(key, redisson.getMap(key));
        }

        long end = System.currentTimeMillis();
        System.out.println("result size:[" + result.size() + "] ..");
        System.out.println("redisson without pipeline used [" + (end - start) + "] milliseconds ..");


    }

    @Test
    public void testRessionWithPipeline(){

        Config config = new Config();
        config.useSingleServer().setAddress("redis://127.0.0.1:6379").setDatabase(4);
        RedissonClient redisson = Redisson.create(config);


        Map<String, Map<String, String>> result = new HashMap<>();
        Map<String,String> data = new HashMap<String,String>();
        RKeys keys = redisson.getKeys();
        keys.flushdb();

        /*2、通过管道方式访问*/
        RBatch batchPut = redisson.createBatch();

        for (int i=0;i<10000;i++) {
            data.clear();
            data.put("k_" + i, "v_" + i);
            batchPut.getMap("key_" + i).putAllAsync(data);
        }

        batchPut.execute();
    }

    @Test
    public void testRedissonWithPipeline2() throws ExecutionException, InterruptedException {

        Config config = new Config();
        config.useSingleServer().setAddress("redis://127.0.0.1:6379").setDatabase(4);
        RedissonClient redisson = Redisson.create(config);


        Map<String, Map<Object, Object>> result = new HashMap<>();
        RKeys keys = redisson.getKeys();

        /*2、通过管道方式访问*/
        RBatch batch = redisson.createBatch();

        Map<String, RFuture<Map<Object, Object>>> futureMap = new HashMap<>();
        long start = System.currentTimeMillis();
        for (String key: keys.getKeys()) {
            futureMap.put(key, batch.getMap(key).readAllMapAsync());
        }

        batch.execute();

        for (Map.Entry<String, RFuture<Map<Object, Object>>> entry : futureMap.entrySet()) {
            result.put(entry.getKey(), entry.getValue().get());
        }

        long end = System.currentTimeMillis();

        System.out.println("result size:[" + result.size() + "] ..");
        System.out.println("redisson with pipeline used [" + (end - start) + "] milliseconds ..");
    }

    @Test
    public void testBatchRedirect() {

        Config config = new Config();
        config.useSingleServer().setAddress("redis://127.0.0.1:6379").setDatabase(6);
        RedissonClient redisson = Redisson.create(config);

        RBatch batch = redisson.createBatch();
        for (int i = 0; i < 5; i++) {
            batch.getMap("" + i).fastPutAsync("" + i, i);
        }
        batch.execute();

        batch = redisson.createBatch();
        for (int i = 0; i < 1; i++) {
            batch.getMap("" + i).sizeAsync();
            batch.getMap("" + i).containsValueAsync("" + i);
            batch.getMap("" + i).containsValueAsync(i);
        }
        List<?> t = batch.execute();
        System.out.println(t);
    }

}
