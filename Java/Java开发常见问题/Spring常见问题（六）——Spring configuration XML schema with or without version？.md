
Spring项目中常用到的XML配置文件指定加载的bean数据信息，是否需要指定spring的xsd版本号呢？？？

以下为两种使用方式：
	
	1、指定版本号的方式
	<?xml version="1.0" encoding="UTF-8"?>
	<beans xmlns="http://www.springframework.org/schema/beans"
		   xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
		   xmlns:context="http://www.springframework.org/schema/context"
		   xsi:schemaLocation="http://www.springframework.org/schema/beans
			http://www.springframework.org/schema/beans/spring-beans-4.0.xsd
			http://www.springframework.org/schema/context
			http://www.springframework.org/schema/context/spring-context-4.0.xsd">

		<context:annotation-config/>

		<context:component-scan base-package="base.package"/>

	</beans>
	
	2、不指定版本号的方式
	<?xml version="1.0" encoding="UTF-8"?>
	<beans xmlns="http://www.springframework.org/schema/beans"
		   xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
		   xmlns:context="http://www.springframework.org/schema/context"
		   xsi:schemaLocation="http://www.springframework.org/schema/beans
			http://www.springframework.org/schema/beans/spring-beans.xsd
			http://www.springframework.org/schema/context
			http://www.springframework.org/schema/context/spring-context.xsd">

		<context:annotation-config/>

		<context:component-scan base-package="base.package"/>

	</beans>
	
推荐使用的为第二种，不定义版本号的方式。不定义版本号的情况下，spring应用启动的时候会从jar包中获取当前spring版本对应的xsd文件。

参考链接：
https://stackoverflow.com/questions/20894695/spring-configuration-xml-schema-with-or-without-version
http://blog.csdn.net/hengyunabc/article/details/22295749