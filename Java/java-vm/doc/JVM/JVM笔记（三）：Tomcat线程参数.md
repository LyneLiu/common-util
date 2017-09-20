1、Tomcat配置文件server.xml中，线程连接数相关的参数：

> maxThreads： Tomcat使用线程来处理接收的每个请求。这个值表示Tomcat可创建的最大的线程数。默认值150。

> acceptCount： 指定当所有可以使用的处理请求的线程数都被使用时，可以放到处理队列中的请求数，超过这个数的请求将不予处理。默认值10。

> minSpareThreads： Tomcat初始化时创建的线程数。默认值25。

> maxSpareThreads： 一旦创建的线程超过这个值，Tomcat就会关闭不再需要的socket线程。默认值75。

> enableLookups： 是否反查域名，默认值为true。为了提高处理能力，应设置为false

> connnectionTimeout： 网络连接超时，默认值60000，单位：毫秒。设置为0表示永不超时，这样设置有隐患的。通常可设置为30000毫秒。

> maxKeepAliveRequests： 保持请求数量，默认值100。 bufferSize： 输入流缓冲大小，默认值2048 bytes。

> compression： 压缩传输，取值on/off/force，默认值off。 

其中和最大连接数相关的参数为maxThreads和acceptCount。如果要加大并发连接数，应同时加大这两个参数。

2、关于中文乱码的解决方法：

JVM和class文件是基于unicode的。

在Tomcat的配置文件中添加配置参数的方法：
 <Connector port="8080" maxThreads="150" minSpareThreads="25" maxSpareThreads="75"
 enableLookups="false" redirectPort="8443" acceptCount="100"
 connectionTimeout="20000" disableUploadTimeout="true" **URIEncoding='GBK'** />

参考链接：
https://wiki.apache.org/tomcat/FAQ/CharacterEncoding#Q8