


1. 问题异常：
	
		Could not resolve placeholder 'Hatake' in value "${Hatake}"

2. 解决办法：

		<bean id="propertyConfigurer" class="org.springframework.beans.factory.config.PropertyPlaceholderConfigurer">
	        <!--指定配置加载顺序-->
	        <property name="order" value="2" />
	        <!--是否忽略未匹配的占位符${...}-->
	        <property name="ignoreUnresolvablePlaceholders" value="true" />
	        <property name="locations">
	            <list>
	                <!--
	                    正确使用方式：classpath:context-*.properties
	                -->
	                <value>classpath:context-*.properties</value>
	            </list>
	        </property>
	    </bean>

参考链接：

	http://seraph115.iteye.com/blog/435165
	https://docs.spring.io/spring/docs/current/javadoc-api/org/springframework/beans/factory/config/PlaceholderConfigurerSupport.html#setPlaceholderPrefix-java.lang.String-