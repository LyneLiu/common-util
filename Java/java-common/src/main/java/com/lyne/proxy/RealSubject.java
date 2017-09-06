package com.lyne.proxy;

/**
 * @author nn_liu
 * @Created 2017-08-24-18:53
 */


/**
 * 真实主题
 */
public class RealSubject implements Subject {

    /**
     * 实现请求方法
     */
    @Override public void request() {
        System.out.println("RealSubject Request!");
    }

}
