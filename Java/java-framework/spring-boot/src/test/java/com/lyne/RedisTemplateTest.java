package com.lyne;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.Serializable;
import java.util.concurrent.TimeUnit;

/**
 * Created by nn_liu on 2017/5/22.
 */

@RunWith(SpringRunner.class)
@SpringBootTest
public class RedisTemplateTest implements Serializable {

    @Autowired
    private RedisTemplate redisTemplate;
    @Test
    public void test() throws Exception {

        City city = new City();
        city.setName("lyne");
        city.setAddr("Shanghai");

        //redisTemplate.setKeySerializer(new StringRedisSerializer());
        //redisTemplate.setValueSerializer(new RedisObjectSerializer());

        redisTemplate.opsForValue().set("ddd", city,10000, TimeUnit.MILLISECONDS);

        System.out.println(redisTemplate.hasKey("ddd"));

        Thread.sleep(5000);
        ValueOperations<String, City> ops = redisTemplate.opsForValue();

        City newCity = ops.get("ddd");
        System.out.println(newCity.getName());
        System.out.println(newCity.getAddr());

    }

}
