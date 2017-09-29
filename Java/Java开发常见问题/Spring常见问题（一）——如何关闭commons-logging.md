&ensp; &ensp;日志是开发中不可或缺的重要部分。Spring中的强依赖日志关系是Jakarta Commons Logging API（JCL），通过maven项目管理工具可以发现commons-logging模块的依赖来自于spring-core的中央模块。

&ensp; &ensp;但是commons-logging目前存在一些问题，更推荐使用Simple Logging Facade for Java（SLF4J）。目前，关闭commons-logging的两种方法：

- 排除Spring-core模块的依赖关系；

- 排除commons-logging依赖（推荐使用）。将一下部分添加至dependencyManagement部分：

		<dependencies>
			<dependency>
				<groupId>org.springframework</groupId>
				<artifactId>spring-core</artifactId>
				<version>5.0.0.M5</version>
				<exclusions>
					<exclusion>
						<groupId>commons-logging</groupId>
						<artifactId>commons-logging</artifactId>
					</exclusion>
				</exclusions>
			</dependency>
		</dependencies>
 

参考链接：

[http://spring4all.com/article/10](http://spring4all.com/article/10)

