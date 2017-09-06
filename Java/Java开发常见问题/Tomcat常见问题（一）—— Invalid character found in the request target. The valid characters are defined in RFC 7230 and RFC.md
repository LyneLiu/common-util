- 异常信息：

java.lang.IllegalArgumentException: Invalid character found in the request target. The valid characters are defined in RFC 7230 and RFC 3986
at org.apache.coyote.http11.InternalInputBuffer.parseRequestLine(InternalInputBuffer.java:189)
at org.apache.coyote.http11.AbstractHttp11Processor.process(AbstractHttp11Processor.java:1000)
at org.apache.coyote.AbstractProtocol$AbstractConnectionHandler.process(AbstractProtocol.java:637)
at org.apache.tomcat.util.net.JIoEndpoint$SocketProcessor.run(JIoEndpoint.java:318)
at java.util.concurrent.ThreadPoolExecutor.runWorker(ThreadPoolExecutor.java:1142)
at java.util.concurrent.ThreadPoolExecutor$Worker.run(ThreadPoolExecutor.java:617)
at org.apache.tomcat.util.threads.TaskThread$WrappingRunnable.run(TaskThread.java:61)
at java.lang.Thread.run(Thread.java:745)

- 问题原因：

Tomcat按照RFC 3986规范对访问进行解析，而RFC 3986规范定义了Url中只允许包含英文字母（a-zA-Z）、数字（0-9）、-_.~4个特殊字符以及所有保留字符(RFC3986中指定了以下字符为保留字符：! * ’ ( ) ; : @ & = + $ , / ? # [ ])。当请求中包含特殊的保留字符，Tomcat解析请求参数失败。


- 解决方案：

1、降低Tomcat版本；

2、在conf/catalina.properties中最后添加一行：

org.apache.tomcat.util.buf.UDecoder.ALLOW_ENCODED_SLASH=true
重启服务器后，解决问题。

官方指南地址：http://tomcat.apache.org/tomcat-8.5-doc/config/systemprops.html

官方说明：
org.apache.tomcat.util.buf.UDecoder.ALLOW_ENCODED_SLASH

If this is true ‘%2F’ and ‘%5C’ will be permitted as path.delimiters.If not specified, the default value of false will be used.