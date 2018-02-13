
1、异常信息：

Caused by: java.sql.SQLException: Value '0000-00-00 00:00:00' can not be represented as java.sql.Timestamp
	at com.mysql.jdbc.SQLError.createSQLException(SQLError.java:964)
	at com.mysql.jdbc.SQLError.createSQLException(SQLError.java:897)
	at com.mysql.jdbc.SQLError.createSQLException(SQLError.java:886)
	at com.mysql.jdbc.SQLError.createSQLException(SQLError.java:860)
	at com.mysql.jdbc.ResultSetRow.getTimestampFast(ResultSetRow.java:937)
	at com.mysql.jdbc.ByteArrayRow.getTimestampFast(ByteArrayRow.java:130)
	at com.mysql.jdbc.ResultSetImpl.getTimestampInternal(ResultSetImpl.java:5934)
	at com.mysql.jdbc.ResultSetImpl.getTimestamp(ResultSetImpl.java:5604)
	at com.mysql.jdbc.ResultSetImpl.getObject(ResultSetImpl.java:4557)
	at com.mysql.jdbc.ResultSetImpl.getObject(ResultSetImpl.java:4703)
	at com.ctrip.platform.dal.dao.helper.DalDefaultJpaMapper.setValue(DalDefaultJpaMapper.java:58)
	at com.ctrip.platform.dal.dao.helper.DalDefaultJpaMapper.map(DalDefaultJpaMapper.java:44)
	... 46 more
	

2、解决办法

出现0000-00-00 属于一个无效日期，设置zeroDateTimeBehavior 属性，当遇到DATETIME值完全由0组成时，最终的有效值可以设置为，异常(exception)，一个近似值(round)，或将这个值转换为null(convertToNull)。

jdbc:mysql://dbserver:3306/yourdatabase?zeroDateTimeBehavior=convertToNull

参考链接：
http://blog.csdn.net/revivedsun/article/details/52972407
https://stackoverflow.com/questions/11133759/0000-00-00-000000-can-not-be-represented-as-java-sql-timestamp-error