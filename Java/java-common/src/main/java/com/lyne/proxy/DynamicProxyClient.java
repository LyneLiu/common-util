package com.lyne.proxy;

import java.lang.reflect.Proxy;

/**
 * @author nn_liu
 * @Created 2017-08-24-19:04
 */

public class DynamicProxyClient {

    public static void main(String[] args) {

        Subject subject = new RealSubject();
        // 使用 Proxy.newProxyInstance 方法生成代理对象
        Subject proxy = (Subject) Proxy.newProxyInstance(subject.getClass().getClassLoader(),
                subject.getClass().getInterfaces(), new DynamicInvocationHandler(subject));
        System.out.println("自动生成代理类：" + proxy.getClass().getName());
        proxy.request();

    }
}
