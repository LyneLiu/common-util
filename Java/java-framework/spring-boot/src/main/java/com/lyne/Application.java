package com.lyne;

import com.lyne.servlet.RegisteredServlet;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.BeansException;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

/**
 * 1、MapperScan为Mybatis扫描注解；
 * 2、通过ServletRegistrationBean添加自定义Servlet Bean；
 * 3、通过@WebServlet注解添加自定义Servlet Bean，该方法需要在Application启动的地方添加@ServletComponentScan注解；
 */
@SpringBootApplication
@EnableAutoConfiguration
//@ServletComponentScan
@MapperScan("com.lyne.mapper")
public class Application implements ApplicationContextAware {

	private static ApplicationContext applicationContext;

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	public static ApplicationContext getContext(){
		return applicationContext;
	}

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		this.applicationContext = applicationContext;
	}
}
