问题记录：
- 

通过Jedis存储数据，

	for (int i=0;i<10000;i++) {
        data.clear();
        data.put("k_" + i, "v_" + i);
        redis.hmset("key_" + i, data);
    }

可以通过Jedis获取：
	
	redis.hgetAll(key);

但是当通过Redisson访问时，报错：
Unrecognized token 'xxx': was expecting ('true', 'false' or 'null')。


原因描述：
- 

通过Java Code的方式存储数据时，存储的数据为：

	127.0.0.1:6379[4]> hgetall key_0
	1) "\"k_0\""
	2) "\"v_0\""

通过Jedis的方式或者直接通过redis cli的方式存储的数据为：

	127.0.0.1:6379[4]> hgetall key_0
	1) "k_0"
	2) "v_0"


解决办法：
- 

in json all strings should be wrapped by ", so "xxx" should be written as ""xxx""【引】

	for (int i=0;i<10000;i++) {
        data.clear();
        data.put(String.format("\"%s\"","k_" + i), String.format("\"%s\"","v_" + i));
        redis.hmset("key_" + i, data);
    }

参考链接：

https://github.com/redisson/redisson/issues/403

