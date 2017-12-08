
1、通过以下命令获取mysql db的系统参数：
SHOW VARIABLES WHERE Variable_name LIKE 'character\_set\_%' OR Variable_name LIKE 'collation%';

utf8mb4, MySQL在5.5.3之后增加了这个utf8mb4的编码，mb4就是most bytes 4的意思，专门用来兼容四字节的unicode字符。

2、修改方法：
	
	修改配置文件my.ini，然后重启mysql：
	[client] 
	default-character-set = utf8mb4 
	[mysql] 
	default-character-set = utf8mb4 
	[mysqld] 
	character-set-client-handshake = FALSE 
	character-set-server = utf8mb4 
	collation-server = utf8mb4_unicode_ci 
	init_connect='SET NAMES utf8mb4'
	
	通过命令行修改table的编码：
	alter table TABLE_NAME convert to character set utf8mb4 collate utf8mb4_bin; 
	
	
	

参考链接：
http://blog.csdn.net/u012758088/article/details/54138877
https://stackoverflow.com/questions/20411440/incorrect-string-value-xf0-x9f-x8e-xb6-xf0-x9f-mysql
http://www.cnblogs.com/chrischennx/p/6623610.html
