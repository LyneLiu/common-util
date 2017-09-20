
1、 编译并部署至本地repo（跳过enforcer和test unit）

**mvn clean install -Denforcer.skip=true -DskipTests=true**

2、 查看mvn dependency 类包冲突解决

**mvn dependency:tree -Dverbose -Dincludes=commons-collections**

[https://maven.apache.org/plugins/maven-dependency-plugin/examples/resolving-conflicts-using-the-dependency-tree.html](https://maven.apache.org/plugins/maven-dependency-plugin/examples/resolving-conflicts-using-the-dependency-tree.html)

参考链接：
[http://blog.csdn.net/ldds_520/article/details/51860803](http://blog.csdn.net/ldds_520/article/details/51860803)


3、 将jar包导入本地仓库（当pom依赖的jar包不存在的情况下，可以通过下载jar包将其部署至本地仓库）
**mvn install:install-file -Dfile=xxx.jar -DgroupId=xxx -DartifactId=xxx -Dversion=xxx -Dpackaging=xxx**

	Note：
	-Dfile表示要添加的jar包；
	-DgroupId为添加的jar包定义项目组；
	-DartifictId为添加的jar包定义在项目组中的模块名；
	-Dversion为添加的jar包定义版本号；
	-Dpackaging选择jar包在maven仓库中的打包方式。

实例：
*mvn install:install-file -Dfile=pentaho-aggdesigner-algorithm-5.1.5-jhyde.jar -DgroupId=org.pentaho -DartifactId=pentaho-aggdesigner-algorithm -Dversion=5.1.5-jhyde -Dpackaging=jar*
