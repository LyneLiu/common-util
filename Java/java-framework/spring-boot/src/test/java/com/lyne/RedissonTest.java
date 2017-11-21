package com.lyne;

import org.junit.Before;
import org.junit.Test;
import org.redisson.Redisson;
import org.redisson.api.RBucket;
import org.redisson.api.RedissonClient;
import org.redisson.client.codec.Codec;
import org.redisson.config.Config;
import org.springframework.util.ClassUtils;

/**
 * http://aperise.iteye.com/blog/2396196
 *
 * @author nn_liu
 * @Created 2017-11-21-10:39
 */

public class RedissonTest {

    private RedissonClient redisson;

    @Before
    public void init() throws ClassNotFoundException, IllegalAccessException,
            InstantiationException {
        Config config = new Config();
        config.useSingleServer().setAddress("redis://127.0.0.1:6379").setDatabase(7);
        config.setCodec((Codec) ClassUtils.forName("org.redisson.codec.KryoCodec",ClassUtils.getDefaultClassLoader()).newInstance());
        redisson = Redisson.create(config);
    }

    @Test
    public void testBucket(){

        // redisson将key-value定义为RBucket，key不存在没关系
        RBucket<String> bucket = redisson.getBucket("key_test_01");
        //如果key存在，就设置key的值为新值value
        //如果key不存在，就设置key的值为value
        bucket.set("value_test_01");

        System.out.println(bucket.get());

    }

}
