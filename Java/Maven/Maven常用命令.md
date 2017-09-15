
1、 编译并部署至本地repo（跳过enforcer和test unit）

**mvn clean install -Denforcer.skip=true -DskipTests=true**

2、 查看mvn dependency 类包冲突解决

**mvn dependency:tree -Dverbose -Dincludes=commons-collections**

[https://maven.apache.org/plugins/maven-dependency-plugin/examples/resolving-conflicts-using-the-dependency-tree.html](https://maven.apache.org/plugins/maven-dependency-plugin/examples/resolving-conflicts-using-the-dependency-tree.html)

参考链接：
[http://blog.csdn.net/ldds_520/article/details/51860803](http://blog.csdn.net/ldds_520/article/details/51860803)