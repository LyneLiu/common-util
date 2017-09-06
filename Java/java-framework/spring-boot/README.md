# spring-boot
1. redis : 


    存储缓存数据；
    
2. spring cache ：
 
 
    通过cache manager管理缓存；
    
3. hibernate validator验证 ： 


    检验Bean属性信息, 启动spring 项目的时候添加注解校验；
    
4. metric：


    监控应用。
    
5. Mybatis：


    ORM数据库使用。
    
    common mapper: 通过mybatis访问db数据。另外一个方案是使用spring boot jpa方式实现数据访问。
    
6. servlet：


    自定义servlet，spring boot的主servlet为DispatcherServlet，其默认的url-patter为"/"。spring boot也提供了两种自定义servlet给开发人员。
    
    >>> 代码注册通过ServletRegistrationBean、 FilterRegistrationBean 和 ServletListenerRegistrationBean 获得控制。也可以通过实现 ServletContextInitializer 接口直接注册；
    >>> 在 SpringBootApplication 上使用@ServletComponentScan 注解后，Servlet、Filter、Listener 可以直接通过 @WebServlet、@WebFilter、@WebListener 注解自动注册，无需其他代码。
