&ensp; &ensp;spring boot项目默认会以启动类的package为root package，自动扫描当前包及子包的所有组件。

##### 问题一：多模块扫描路径 #####
&ensp; &ensp;但是通常项目是由多个模块组成的，通过maven进行管理。比如demo-core作为核心module、demo-service模块提供服务，demo-web提供展示给用户。Spring boot项目启动的类在demo-web模块，如何将demo-core、demo-service模块中的组件也加载至web context中呢？？？

&ensp; &ensp;@Componentscan注解提供了自定义扫描路径的配置功能，规范话管理的情况下demo-core模块的base package为com.demo.core；demo-service模块的base package为com.demo.service；demo-web模块的base package为com.demo.web。
	
	@SpringBootApplication
	@EnableAutoConfiguration
	@ComponentScan(basePackages = {"com.demo.core","com.demo.service","com.demo.web"})
	public class DemoApplication {
	
	    public static void main(String[] args) {
	        SpringApplication.run(DemoApplication.class, args);
	    }
	}

&ensp; &ensp;相对简洁的使用方法是：@ComponentScan(basePackages = "com.demo.*")

##### 问题二：*@ComponentScan* vs *@EnableJpaRepositories* vs  *@EntityScan*的*basePackages*区别#####

@ComponentScan表示Spring容器将basePackages下的所有标记注解@Component, @Service, @Controller, @RestController, @Repository, ...的类进行实例化，并将其通过@Autowired实现自动注入。

@EnableJpaRepositories和@EntityScan标识数据持久化相关的类，并且**<span style="color:red">如果JPA Repositories和Entities不在Application.java启动类sub package的情况下，需要定义basePackages扫描路径</span>**。



参考链接：

[https://stackoverflow.com/questions/30587377/springboot-componentscan-issue-with-multi-module-project](https://stackoverflow.com/questions/30587377/springboot-componentscan-issue-with-multi-module-project)
[https://stackoverflow.com/questions/38896414/difference-between-entityscan-and-componentscan](https://stackoverflow.com/questions/38896414/difference-between-entityscan-and-componentscan)
