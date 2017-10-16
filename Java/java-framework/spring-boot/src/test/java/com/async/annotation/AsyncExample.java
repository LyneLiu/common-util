package com.async.annotation;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;

/**
 * @Async 注解：
 * 大部分情况下，Java应用通过同步的方式实现交互处理；当应用与第三方系统进行交互的时候，容易造成响应迟缓的情况。
 * 比如，项目需要记录log日志，将请求数据信息记录至hbase，为问题排查做记录。但是数据量大的情况下，
 * 造成client端调用服务超时（socket设置的默认时间为10s）。通过@Async注解异步的方式，启动独立的线程处理记录log、发送消息等操作，
 * 主线程继续执行而不产生停滞等待，这样可以提供应用性能。
 *
 */

public class AsyncExample {

    public static void main (String[] args) {
        AnnotationConfigApplicationContext context =
                  new AnnotationConfigApplicationContext(
                            MyConfig.class);
        MyBean bean = context.getBean(MyBean.class);
        System.out.printf("calling async method from thread: %s%n",
                          Thread.currentThread().getName());
        bean.runTask();
    }

    @EnableAsync
    @Configuration
    public static class MyConfig {
        @Bean
        public MyBean myBean () {
            return new MyBean();
        }
    }

    private static class MyBean {

        @Async
        public void runTask () {
            System.out.printf("Running task  thread: %s%n",
                              Thread.currentThread().getName());
        }
    }
}
