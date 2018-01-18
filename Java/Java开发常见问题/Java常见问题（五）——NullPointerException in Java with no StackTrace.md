
1、异常信息：
java.lang.NullPointerException

通常异常信息都会有stacktrace，提供给开发者，用来排查问题。但是，上述异常信息只有一行，有些让人摸不着头脑。

2、查看stack trace
添加VM启动参数：-XX:-OmitStackTraceInFastThrow

参考链接：
https://stackoverflow.com/questions/2411487/nullpointerexception-in-java-with-no-stacktrace
http://blog.csdn.net/taotao4/article/details/43918131