package com.lyne.proxy.cglib;

import net.sf.cglib.proxy.CallbackFilter;
import net.sf.cglib.proxy.Enhancer;

import java.lang.reflect.Method;

/**
 * @author nn_liu
 * @Created 2018-03-22-10:58
 */

public class AuthProxyFilter implements CallbackFilter {

    /**
     * 过滤方法
     * @param method
     * @return
     */

    /**
     * @param method
     * @return the index into the array of callbacks (as specified by {@link Enhancer#setCallbacks}) to use for the method,
     */
    @Override
    public int accept(Method method) {

        // 执行CallBack AuthProxy
        if (!"query".equalsIgnoreCase(method.getName())){
            return 0;
        }

        // 执行NoOp.INSTANCE
        return 1;
    }
}
