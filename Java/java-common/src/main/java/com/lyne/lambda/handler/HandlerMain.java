package com.lyne.lambda.handler;

import java.util.ArrayList;
import java.util.List;

/**
 * 当使用parallelStream方法对同一个对象并行处理的情况下，是否有助于提高使用性能？
 *
 * @author nn_liu
 * @Created 2017-09-26-16:58
 */

public class HandlerMain {
    public static void main(String[] args) {
        BaseHandler handlerA = new HandlerA();
        BaseHandler handlerB = new HandlerB();
        BaseHandler handlerC = new HandlerC();
        BaseHandler handlerD = new HandlerD();
        BaseHandler handlerE = new HandlerE();

        BaseBean bean = new BaseBean();

        long startParallel = System.currentTimeMillis();

        List<BaseHandler> handlers = new ArrayList<>();
        handlers.add(handlerA);
        handlers.add(handlerB);
        handlers.add(handlerC);
        handlers.add(handlerD);
        handlers.add(handlerE);
        handlers.parallelStream().forEach(p -> p.process(bean));
        long endParallel = System.currentTimeMillis();

        System.out.println(endParallel - startParallel);
        System.out.println("====================================");

        BaseBean bean1 = new BaseBean();
        long start = System.currentTimeMillis();
        handlerA.process(bean1);
        handlerB.process(bean1);
        handlerC.process(bean1);
        handlerD.process(bean1);
        handlerE.process(bean1);
        long end = System.currentTimeMillis();

        System.out.println(end - start);
    }
}
