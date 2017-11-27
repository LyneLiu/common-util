

- SQL查询报警：
- 

	详细SQL :SELECT * FROM Product_xxx WHERE 1=1 LIMIT 265000, 5000
	执行次数 :8 /min 
	平均执行时间 :1014 毫秒/次


- 优化建议：
- 

	使用limt 起始，步长， 这种取数方式虽然在最开始的时候，取数效率高，但是起始值越高，性能消耗越大，而且都是重复重复无意义的扫描消耗，
	这种取数请改为按主键范围，偏移来取数。


- SQL优化：
-

	[SQL]SELECT * FROM prd_product_0 WHERE 1=1 LIMIT 10000, 800
	
	受影响的行: 0
	时间: 0.187s

Product_xxx表的productID是自增主键（主键索引），使用limit查询时，offset查询是遍历查询，可以通过以下sql进行优化：
	
	[SQL]select * from Product_xxx where productID >
	(
		select productid FROM Product_xxx WHERE 1=1 LIMIT 1000, 1
	)
	limit 800
	
	受影响的行: 0
	时间: 0.123s


参考链接：
https://my.oschina.net/No5stranger/blog/158202


