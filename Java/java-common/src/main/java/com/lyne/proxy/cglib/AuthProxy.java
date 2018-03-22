package com.lyne.proxy.cglib;

import com.google.common.collect.Lists;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;
import java.util.List;

/**
 *
 * 参考链接：
 * http://llying.iteye.com/blog/220452
 *
 * @author nn_liu
 * @Created 2018-03-22-10:16
 */

public class AuthProxy implements MethodInterceptor {

    private String userName;

    private List<String> authList = Lists.newArrayList("a", "b", "c", "d");

    public AuthProxy(String userName) {
        this.userName = userName;
    }

    /**
     *
     * @param o
     * @param method
     * @param objects
     * @param methodProxy
     * @return
     * @throws Throwable
     */
    @Override
    public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy)
            throws Throwable {

        if (!authList.contains(userName)){
            System.out.println("No Permission!");
            return null;
        }

        return methodProxy.invokeSuper(o, objects);
    }
}
