


- 异常信息：
- 

	Failed to close coordinator
	java.lang.OutOfMemoryError: Metaspace
		at java.lang.ClassLoader.defineClass1(Native Method)
		at java.lang.ClassLoader.defineClass(ClassLoader.java:760)
		at java.security.SecureClassLoader.defineClass(SecureClassLoader.java:142)
		at org.apache.catalina.loader.WebappClassLoaderBase.findClassInternal(WebappClassLoaderBase.java:3196)
		at org.apache.catalina.loader.WebappClassLoaderBase.findClass(WebappClassLoaderBase.java:1373)
		at org.apache.catalina.loader.WebappClassLoaderBase.loadClass(WebappClassLoaderBase.java:1861)
		at org.apache.catalina.loader.WebappClassLoaderBase.loadClass(WebappClassLoaderBase.java:1735)
		at org.apache.kafka.clients.consumer.internals.AbstractCoordinator.sendLeaveGroupRequest(AbstractCoordinator.java:561)
		at org.apache.kafka.clients.consumer.internals.AbstractCoordinator.maybeLeaveGroup(AbstractCoordinator.java:552)
		at org.apache.kafka.clients.consumer.internals.AbstractCoordinator.close(AbstractCoordinator.java:541)
		at org.apache.kafka.clients.consumer.internals.ConsumerCoordinator.close(ConsumerCoordinator.java:321)
		at org.apache.kafka.clients.ClientUtils.closeQuietly(ClientUtils.java:63)
		at org.apache.kafka.clients.consumer.KafkaConsumer.close(KafkaConsumer.java:1277)
		at org.apache.kafka.clients.consumer.KafkaConsumer.close(KafkaConsumer.java:1258)
		at com.ctrip.hermes.kafka.engine.HermesKafkaConsumer.close(HermesKafkaConsumer.java:215)
		at com.ctrip.hermes.kafka.engine.HermesKafkaConsumerTask.run(HermesKafkaConsumerTask.java:175)
		at java.lang.Thread.run(Thread.java:745)




- 理论基础：
- 

Java 8移除了Java 7中的永久代（Permanent Generation (PermGen)），永久代的替代者为元空间（Metaspace）。

根据参考链接中的描述信息可知，该问题是由于Java程序不断增加的类元数据的内存占用造成的。解决问题的办法是设置JVM参数，目前的JVM参数信息如下：

	-Djava.util.logging.config.file=/opt/tomcat/conf/logging.properties 
	-Djava.util.logging.manager=org.apache.juli.ClassLoaderLogManager 
	-XX:ParallelGCThreads=1 -Xmx4916m -Xms4916m -Xss256k 
	-XX:MetaspaceSize=128m -XX:MaxMetaspaceSize=256m
	-XX:SoftRefLRUPolicyMSPerMB=0 -XX:MaxGCPauseMillis=200 
	-XX:+UseG1GC -XX:-OmitStackTraceInFastThrow 
	-Xmx4096m -Xms4096m 
	-XX:+PrintGC -XX:+PrintGCDetails -XX:+PrintGCDateStamps 
	-XX:+HeapDumpOnOutOfMemoryError -XX:HeapDumpPath=/opt/logs/ 
	-Dport.http.server=8080 -Dlog.server=/opt/logs/ 
	-Dport.shutdown.server=8081 -Ddocbase.server=/opt/app 
	-Dvdir.server=/package-product-metaservice 
	-Djava.security.egd=file:/dev/./urandom -Dcom.sun.management.jmxremote.authenticate=false 
	-Dcom.sun.management.jmxremote.ssl=false 
	-Djava.rmi.server.hostname=10.0.108.15 
	-Dcom.sun.management.jmxremote -Dcom.sun.management.jmxremote.port=8780 
	-Dcom.sun.management.jmxremote.local.only=false 
	-Xloggc:/opt/logs/gc-20171128161553.log 
	-DAPPLOGDIR=/opt/logs/applog -Djdk.tls.ephemeralDHKeySize=2048 
	-Djava.endorsed.dirs=/opt/tomcat/endorsed -Dcatalina.base=/opt/tomcat 
	-Dcatalina.home=/opt/tomcat -Djava.io.tmpdir=/opt/tomcat/temp



*Note：*

**Java 元空间为无限（默认值）**！



参考链接：
http://blog.csdn.net/qq_33157775/article/details/51142090
http://caoyaojun1988-163-com.iteye.com/blog/1969853




----------


JVM垃圾收集器－对比Serial、Parallel、CMS和G1

By Evan Wu, 电商与大数据@TAZHI, jvm
发表于2015年4月18日22时3分2秒

4个Java垃圾收集器，错误的选择会对性能影响很大

现在很多开发者仍然搞不清垃圾收集器。这一块在Java 8版本的改动也比较大，特别是去掉了PermGen永久代和带来一些新的让人激动的优化。

提到垃圾收集，大多数人在每天的编程工作中都会用到并知道这个概念。其中最大的一个误解是一位JVM只有一个垃圾收集器，事实上它有4个，每个都有自己的优点和缺点。JVM并不会自动帮你选择，决定权在你手里，不同的选择会导致应用的吞吐量（throughtput）和停顿（pause）有巨大的不同。

但这4个垃圾收集器的共同点是它们都是分代式的，这是说它们基于大多数对象的都比较短命需要快速回收的假设把堆分成不同年龄区域。很多人应该也知道这一点。这4个垃圾收集器各有什么优缺点呢？

1. 串行收集器Seiral Collector

串行收集器是最简单的，它设计为在单核的环境下工作（32位或者windows），你几乎不会使用到它。它在工作的时候会暂停整个应用的运行，因此在所有服务器环境下都不可能被使用。

使用方法：-XX:+UseSerialGC

2. 并行／吞吐优先收集器Parallel/Throughput Collector

这是JVM默认的收集器，跟它名字显示的一样，它最大的优点是使用多个线程来扫描和压缩堆。缺点是在minor和full GC的时候都会暂停应用的运行。并行收集器最适合用在可以容忍程序停滞的环境使用，它占用较低的CPU因而能提高应用的吞吐（throughput）。

使用方法：-XX:+UseParallelGC

3. CMS收集器CMS Collector

接下来是CMS收集器，CMS是Concurrent-Mark-Sweep的缩写，并发的标记与清除。这个算法使用多个线程并发地（concurrent）扫描堆，标记不使用的对象，然后清除它们回收内存。在两种情况下会使应用暂停（Stop the World, STW）：1. 当初次开始标记根对象时。2. 当在并行收集时应用又改变了堆的状态时，需要它从头再确认一次标记了正确的对象。

这个收集器最大的问题是在年轻代与老年代收集时会出现的一种竞争情况（race condition），称为提升失败promotion failure。对象从年轻代复制到老年代称为提升promotion，但有时侯老年代需要清理出足够空间来放这些对象，这需要一定的时间，它收集的速度可能赶不上不断产生的要提升的年轻代对象的速度，这时就需要做STW的收集。STW正是CMS想避免的问题。为了避免这个问题，需要增加老年代的空间大小或者增加更多的线程来做老年代的收集以赶上从年轻代复制对象的速度。

对比并行收集器它的一个坏处是需要占用比较多的CPU。对于大多数长期运行的服务器应用来说，这通常是值得的，因为它不会导致应用长时间的停滞。但是它不是JVM的默认的收集器。

使用方法：-XX：+UseConcMarkSweepGC，此时可同时使用-XX:+UseParNewGC将并行收集作用于年轻代，新的JVM自动打开这一配置

4. G1收集器Garbage First Collector

如果你的堆内存大于4G的话，那么G1会是要考虑使用的收集器。它是为了更好支持大于4G堆内存在JDK 7 u4引入的。G1收集器把堆分成多个区域，大小从1MB到32MB，并使用多个后台线程来扫描这些区域，优先会扫描最多垃圾的区域，这就是它名称的由来，垃圾优先Garbage First。

如果在后台线程完成扫描之前堆空间耗光的话，才会进行STW收集。它另外一个优点是它在处理的同时会整理压缩堆空间，相比CMS只会在完全STW收集的时候才会这么做。

使用过大的堆内存在过去几年是存在争议的，很多开发者从单个JVM分解成使用多个JVM的微服务（micro-service）和基于组件的架构。其他一些因素像分离程序组件、简化部署和避免重新加载类到内存的考虑也促进了这样的分离。

除了这些因素，最大的因素当然是避免在STW收集时JVM用户线程停滞时间过长，如果你使用了很大的堆内存的话就可能出现这种情况。另外，像Docker那样的容器技术让你可以在一台物理机器上轻松部署多个应用也加速了这种趋势。

使用方法：－XX:+UseG1GC

Java 8和G1收集器

G1收集器在Java 8 u20上最漂亮的优化是String去重（String Deduplication）。String对象和它内部使用的char[]数组会占用比较多的内存，因此优化过的G1收集器会把重复的String对象指向同一个char[]数组，避免多个副本存在在堆里。可以使用-XX:+UseStringDeduplication参数来打开这一功能。

Java 8和永久代PermGen

Java 8最大的改变之一是去掉了永久代。永久代需要开发者仔细调节它的大小，过去多年这是产生OutOfMemory异常的重要原因。现在JVM可以自己管理这块区域了。

翻译自：

http://blog.takipi.com/garbage-collectors-serial-vs-parallel-vs-cms-vs-the-g1-and-whats-new-in-java-8/?utm_source=blog&utm_medium=in-post&utm_content=jvmtuning&utm_campaign=java

© 2016 www.tazhi.com.


注：网页无法打开，故将文本信息保存下来。