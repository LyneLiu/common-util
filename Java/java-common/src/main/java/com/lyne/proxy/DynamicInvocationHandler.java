package com.lyne.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * @author nn_liu
 * @Created 2017-08-24-19:04
 */

public class DynamicInvocationHandler implements InvocationHandler {

    private Object obj;

    public DynamicInvocationHandler(Object obj) {
        this.obj = obj;
    }

    @Override public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println("proxy start...");
        method.invoke(obj, args);
        System.out.println("proxy end...");
        return null;
    }

}
