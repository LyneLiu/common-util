&ensp; &ensp;Spring Boot项目默认为jar包，但是其目录结构和通常的jar目录结构有区别。
Spring Boot项目的jar包包含BOOT-INF（spring boot依赖模块）、META-INF和org（当前项目的classes等文件）。

&ensp; &ensp;如果想将项目打包为war包，通过tomcat、jetty等容器进行部署。首先添加pom依赖插件：

	<!-- ... -->
	<build>
		<plugins>
			<plugin>
				<groupId>org.springframework.boot</groupId>
				<artifactId>spring-boot-maven-plugin</artifactId>
			</plugin>
		</plugins>
	</build>
	<!-- ... -->

&ensp; &ensp;然后指定项目的打包方式：
	
	<!-- ... -->
    <packaging>war</packaging>
    <!-- ... -->

最后，执行mvn package即可以得到可以自定义部署的war包。