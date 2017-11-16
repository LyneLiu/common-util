Redis提供了密码访问的轻量级的认证方式，可以通过两种方式设置密码：

1、修改配置文件redis.conf：
requirepass test123

2、通过命令行的方式设置密码：
config set requirepass test123  // 设置密码
auth test123  	//认证用户
config get requirepass  // 获取密码

设置密码后，如果client没有指定密码进行认证，建立连接的时候便会报错：

	Caused by: redis.clients.jedis.exceptions.JedisDataException: NOAUTH Authentication required.
		at redis.clients.jedis.Protocol.processError(Protocol.java:127)
		at redis.clients.jedis.Protocol.process(Protocol.java:161)
		at redis.clients.jedis.Protocol.read(Protocol.java:215)
		at redis.clients.jedis.Connection.readProtocolWithCheckingBroken(Connection.java:340)
		at redis.clients.jedis.Connection.getStatusCodeReply(Connection.java:239)
		at redis.clients.jedis.BinaryJedis.psetex(BinaryJedis.java:3275)
		at org.springframework.data.redis.connection.jedis.JedisConnection.pSetEx(JedisConnection.java:1440)


参考链接：

https://www.cnblogs.com/nerrissa/articles/4619027.html