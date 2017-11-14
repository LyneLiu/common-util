Java项目开发中通常需要配置maven服务器地址。

1、pom.xml文件

	......
	<repositories>...</repositories>
	......
	<distributionManagement>...</distributionManagement>


	说明：    
	<repositories>...</repositories> : 配置同setting.xml中的开发库, 项目中依赖的jar包会从指定服务器获取。
	<distributionManagement>...</distributionManagement> : 用于配置分发管理，配置相应的产品发布信息,主要用于发布，在执行mvn deploy后表示要发布的位置。


2、setting.xml文件

	<proxies>
	    <proxy>
			<id>optional</id>
			<active>true</active>
			<protocol>http</protocol>
			<username>proxyuser</username>
			<password>proxypass</password>
			<host>proxy.host.net</host>
			<port>80</port>
			<nonProxyHosts>local.net|some.host.com</nonProxyHosts>
	    </proxy>
	</proxies>
	说明：proxy表示Maven的代理，需要proxy是因为很多时候你所在的公司基于安全因素考虑，要求你使用通过安全认证的代理访问因特网。这种情况下，就需要为Maven配置HTTP代理，才能让它正常访问外部仓库，以下载所需要的资源。proxies下可以配置多个proxy元素，如果声明了多个proxy元素，则默认情况下第一个被激活的proxy会生效。active为true表示激活该代理，protocol表示使用的代理协议，当然最重要的是指定正确的主机名（host）和端口（port），如果代理服务器需要认证则配置username和password，nonProxyHost元素表示指定哪些主机名不需要代理，可以用”|”分隔多个主机名，也支持通配符”*”。

----------

	<!--这里的profile元素是pom.xml的profile元素的一个裁剪版本-->
	<profiles xmlns="">
		<profile>
			<id>nexus</id>
			<repositories>
				<repository>
				  <id>nexus-releases</id>
				  <name>all repoes</name>
				  <url>http://maven.release.com/nexus/content/groups/public</url>
				  <releases>
					<enabled>true</enabled>
					<checksumPolicy>warn</checksumPolicy>
				  </releases>
				  <snapshots>
					 <enabled>true</enabled>
					 <updatePolicy>always</updatePolicy>
					 <checksumPolicy>fail</checksumPolicy>
				  </snapshots>
				</repository>
			</repositories>
			<pluginRepositories>
				<pluginRepository>
					<id>nexus-plugin-releases</id>
					<url>http://maven.release.com/nexus/content/groups/public</url>
					<releases>
						<enabled>true</enabled>
						<checksumPolicy>warn</checksumPolicy>
					</releases>
					<snapshots>
						<enabled>true</enabled>
						<updatePolicy>always</updatePolicy>
						<checksumPolicy>fail</checksumPolicy>
					</snapshots>
				</pluginRepository>
			</pluginRepositories>
		</profile>
	</profiles>
	<!--一直启用id为nexus的profile配置-->
  	<activeProfiles>
		<activeProfile>nexus</activeProfile>
	</activeProfiles>

	说明：通过Profile执行仓库环境，类似于开发的fat、uat、prod环境。repository表示Maven的中央仓库，可以声明多个repository，但是id必须是唯一的。需要注意的是Maven自带的中央仓库的id为central，如果其他仓库声明也用该id，就会覆盖中央仓库的配置。releases和snapshots比较重要，前者表示开启仓库的发布版本下载支持，后者表示开启仓库的快照版本下载支持。

----------

	<servers>
		<server>
	    	<id>nexus-releases</id>
	    	<username>deployment</username>
	    	<password>deployment</password>
		</server>
	</servers>

	说明：大部分远程仓库无需认证就可以访问，但是通常出于安全方面的考虑，需要提供认证信息才能访问一些远程仓库，server部分为认证元素。需要注意的是，id必须与需要认证的repository元素的id完全一致，访问repository时需要用到对应的认证信息。

----------

	<!--
	 localRepository是设置本地仓库地址的。默认位置为: ${user.home}/.m2/repository/ 
	 Windows 参考格式：<localRepository>d:/repository</localRepository> 
	 Linux 参考格式：<localRepository>$HOME/repository</localRepository> 
	 -->
	<localRepository>${user.home}\.m2\repository\</localRepository>

----------

	<mirror>
	    <id>nexus</id>
	    <name>internal nexus repository</name>
	    <url>http://192.168.1.6:8081/nexus/content/groups/public</url>
    	<mirrorOf>*</mirrorOf>
	</mirror>
	
	说明：如果仓库A可以提供仓库B存储的所有内容，那么就可以认为仓库A是仓库B的一个镜像（mirror）举个例子，”http://maven.net.cn/content/groups/public/”是中央仓库”http://repo1.maven.org/maven2/”在中国的镜像，由于地理位置的因素，该镜像往往能够提供比中央仓库更快的服务，这就是为什么要使用mirror的原因。在上述示例中，mirrorOf为*，表示该配置为所有中央仓库的镜像，任何对于中央仓库的请求都会转至该镜像。另外三个元素id、name、url与一般仓库配置无异。如果需要认证，也可以基于该id配置仓库认证。

----------

参考链接：

	http://www.blogjava.net/zyl/archive/2006/12/30/91055.html
	http://www.importnew.com/22779.html