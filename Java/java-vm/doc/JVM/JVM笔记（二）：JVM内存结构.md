JVM内存结构：

> 1、堆内存

>> 年轻代；
>>> Eden空间（80%）；

>>> From Survivor空间（10%）；

>>> To Survivor空间（10%）；

>> 老年代；

> 2、方法区（别名 Non-Heap）：存储类信息、常量、静态变量等数据，是线程共享的区域；

> 3、栈  

>> Java虚拟机栈；

>> 本地方法栈：主要用于方法的执行。

![](http://images2015.cnblogs.com/blog/331425/201606/331425-20160623115841031-564040608.png)

> 控制参数：
 
>>-Xms设置堆的最小空间大小。

>>-Xmx设置堆的最大空间大小。

>>-XX:NewSize设置新生代最小空间大小。

>>-XX:MaxNewSize设置新生代最大空间大小。

>>-XX:PermSize设置永久代最小空间大小。

>>-XX:MaxPermSize设置永久代最大空间大小。

>>-Xss设置每个线程的堆栈大小

参考链接：
http://www.cnblogs.com/ityouknow/p/5610232.html
