package com.lyne.common;

import com.lyne.utils.SerializeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.support.SimpleValueWrapper;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

/**
 * Cache为Spring提供的核心缓存接口
 * Created by nn_liu on 2017/6/28.
 */

@Component
public class RedisCache implements Cache{

    /**
     * Redis
     */
    @Autowired
    private RedisTemplate redisTemplate;

    @Resource(name = "cachedThreadPool")
    private ExecutorService cachedThreadPool;

    /**
     * 缓存名称
     */
    private String name;

    /**
     * 超时时间
     */
    private long timeout;

    /*
     * (non-Javadoc)
     * @see org.springframework.cache.Cache#getName()
     */
    @Override
    public String getName() {
        return this.name;
    }

    /*
     * (non-Javadoc)
     * @see org.springframework.cache.Cache#getNativeCache()
     */
    @Override
    public Object getNativeCache() {
        // TODO Auto-generated method stub
        return this.redisTemplate;
    }

    /*
     * (non-Javadoc)
     * @see org.springframework.cache.Cache#get(java.lang.Object)
     */
    @Override
    public ValueWrapper get(Object key) {
        if (StringUtils.isEmpty(key)) {
            return null;
        } else {
            final String finalKey;
            if (key instanceof String) {
                finalKey = (String) key;
            } else {
                finalKey = key.toString();
            }
            Object object = null;
            object = redisTemplate.execute(new RedisCallback<Object>() {
                public Object doInRedis(RedisConnection connection) throws DataAccessException {
                    byte[] key = finalKey.getBytes();
                    byte[] value = connection.get(key);
                    if (value == null) {
                        return null;
                    }
                    return SerializeUtil.unserialize(value);
                }
            });
            return (object != null ? new SimpleValueWrapper(object) : null);
        }
    }

    /*
     * (non-Javadoc)
     * @see org.springframework.cache.Cache#get(java.lang.Object, java.lang.Class)
     */
    @SuppressWarnings("unchecked")
    @Override
    public <T> T get(Object key, Class<T> type) {
        if (StringUtils.isEmpty(key) || null == type) {
            return null;
        } else {
            final String finalKey;
            final Class<T> finalType = type;
            if (key instanceof String) {
                finalKey = (String) key;
            } else {
                finalKey = key.toString();
            }
            final Object object = redisTemplate.execute(new RedisCallback<Object>() {
                public Object doInRedis(RedisConnection connection) throws DataAccessException {
                    byte[] key = finalKey.getBytes();
                    byte[] value = connection.get(key);
                    if (value == null) {
                        return null;
                    }
                    return SerializeUtil.unserialize(value);
                }
            });
            if (finalType != null && finalType.isInstance(object) && null != object) {
                return (T) object;
            } else {
                return null;
            }
        }
    }

    @Override public <T> T get(Object key, Callable<T> callable) {
        ValueWrapper val = get(key);
        if (val != null) {
            return (T) val.get();
        } else {
            Future<T> future = cachedThreadPool.submit(callable);
            try {
                T value = future.get();
                this.put(key, value);
                return value;
            } catch (Exception e) {
                return null;
            }
        }
    }

    /*
     * (non-Javadoc)
     * @see org.springframework.cache.Cache#put(java.lang.Object, java.lang.Object)
     */
    @Override
    public void put(final Object key, final Object value) {
        if (StringUtils.isEmpty(key) || StringUtils.isEmpty(value)) {
            return;
        } else {
            final String finalKey;
            if (key instanceof String) {
                finalKey = (String) key;
            } else {
                finalKey = key.toString();
            }
            if (!StringUtils.isEmpty(finalKey)) {
                final Object finalValue = value;
                redisTemplate.execute(new RedisCallback<Boolean>() {
                    @Override
                    public Boolean doInRedis(RedisConnection connection) {
                        connection.set(finalKey.getBytes(), SerializeUtil.serialize(finalValue));
                        // 设置超时间
                        connection.expire(finalKey.getBytes(), timeout);
                        return true;
                    }
                });
            }
        }
    }

    @Override public ValueWrapper putIfAbsent(Object key, Object value) {
        ValueWrapper oldValue = this.get(key.toString());
        if (oldValue == null){
            this.put(key,value);
        }
        return  oldValue;
    }

    /*
     * 根据Key 删除缓存
     */
    @Override
    public void evict(Object key) {
        if (null != key) {
            final String finalKey;
            if (key instanceof String) {
                finalKey = (String) key;
            } else {
                finalKey = key.toString();
            }
            if (!StringUtils.isEmpty(finalKey)) {
                redisTemplate.execute(new RedisCallback<Long>() {
                    public Long doInRedis(RedisConnection connection) throws DataAccessException {
                        return connection.del(finalKey.getBytes());
                    }
                });
            }
        }
    }

    /*
     * 清楚系统缓存
     */
    @Override
    public void clear() {
        // TODO Auto-generated method stub
        // redisTemplate.execute(new RedisCallback<String>() {
        // public String doInRedis(RedisConnection connection) throws DataAccessException {
        // connection.flushDb();
        // return "ok";
        // }
        // });
    }

    public RedisTemplate<String, Object> getRedisTemplate() {
        return redisTemplate;
    }

    public void setRedisTemplate(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getTimeout() {
        return timeout;
    }

    public void setTimeout(long timeout) {
        this.timeout = timeout;
    }

}
