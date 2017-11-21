spring配置文件web.xml中有两处会实例化bean：

1、Webapplicationcontext作为ROOT application context，初始化service、dao等bean，对整个应用程序共享：
	<context-param>
        <param-name>contextConfigLocation</param-name>
        <param-value>classpath*:/applicationContext.xml</param-value>
    </context-param>

2、Servletcontext加载的为针对Spring Web MVC有效的bean，如controller、handlermapping、handleradapte等web相关组件：
	<servlet>
        <servlet-name>dispatcher</servlet-name>
        <servlet-class>org.springframework.web.servlet.DispatcherServlet</servlet-class>
        <init-param>
            <param-name>contextConfigLocation</param-name>
            <param-value>classpath:applicationContext-mvc.xml</param-value>
        </init-param>
        <load-on-startup>1</load-on-startup>
    </servlet>

这个配置文件中的bean都会被初始化, 如果包扫描范围重复就会初始化两次bean。如果配置文件在两个地方均有配置，会抛出DuplicateListenerException异常，即重复注册listener。

Note：
检查bean的初始化情况,可以使用@PostConstruct注解打log；
spring boot不会存在该问题。

参考链接：
http://jinnianshilongnian.iteye.com/blog/1602617