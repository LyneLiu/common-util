package com.lyne.common;

import com.lyne.metric.CustomMetric;
import com.lyne.servlet.RegisteredFilter;
import com.lyne.servlet.RegisteredListener;
import com.lyne.servlet.RegisteredServlet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.autoconfigure.ExportMetricWriter;
import org.springframework.boot.actuate.endpoint.PublicMetrics;
import org.springframework.boot.actuate.metrics.export.MetricExportProperties;
import org.springframework.boot.actuate.metrics.repository.redis.RedisMetricRepository;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletListenerRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;

import java.util.ArrayList;
import java.util.EventListener;
import java.util.List;

/**
 * Common配置中心
 *
 * @author nn_liu
 * @Created 2017-06-30-18:59
 */
@Configuration
public class CommonConfig {


    @Autowired
    private MetricExportProperties export;

    /**
     * 自定义servlet
     * @return
     */
    @Bean
    public ServletRegistrationBean servletRegistrationBean() {

        /*
        RegisteredServlet customServlet = new RegisteredServlet();
        ServletRegistrationBean registrationBean = new ServletRegistrationBean();
        registrationBean.setServlet(customServlet);
        List<String> urlMappings=new ArrayList<String>();
        urlMappings.add("/registeredServlet*//*");////访问，可以添加多个
        registrationBean.setUrlMappings(urlMappings);
        registrationBean.setLoadOnStartup(1);
        return registrationBean;
        */
        return new ServletRegistrationBean(new RegisteredServlet(), "/registeredServlet/*"); 	// ServletName默认值为首字母小写，即registeredServlet
    }

    @Bean
    public FilterRegistrationBean getDemoFilter(){
        RegisteredFilter demoFilter=new RegisteredFilter();
        FilterRegistrationBean registrationBean=new FilterRegistrationBean();
        registrationBean.setFilter(demoFilter);
        List<String> urlPatterns=new ArrayList<>();
        urlPatterns.add("/*");//拦截路径，可以添加多个
        registrationBean.setUrlPatterns(urlPatterns);
        registrationBean.setOrder(1);
        return registrationBean;
    }

    @Bean
    public ServletListenerRegistrationBean<EventListener> getDemoListener(){
        ServletListenerRegistrationBean<EventListener> registrationBean = new ServletListenerRegistrationBean<>();
        registrationBean.setListener(new RegisteredListener());
        registrationBean.setOrder(1);
        return registrationBean;
    }


    /**
     * public metric
     * @return
     */
    @Bean
    public PublicMetrics appMetrics() {
        return new CustomMetric();
    }

    @Bean
    @ExportMetricWriter
    public RedisMetricRepository redisMetricWriter(RedisConnectionFactory connectionFactory) {
        return new RedisMetricRepository(connectionFactory,
                this.export.getRedis().getPrefix(), this.export.getRedis().getKey());
    }
}
