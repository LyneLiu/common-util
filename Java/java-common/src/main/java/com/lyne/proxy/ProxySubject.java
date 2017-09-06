package com.lyne.proxy;

/**
 * @author nn_liu
 * @Created 2017-08-24-18:55
 */


/**
 * 代理主题
 */
public class ProxySubject implements Subject {

    /**
     * 被代理对象
     */
    private RealSubject realSubject;

    @Override public void request() {
        System.out.println("proxy start...");
        if (realSubject == null){
            realSubject = new RealSubject();
        }

        realSubject.request();
        System.out.println("proxy end...");
    }

}
