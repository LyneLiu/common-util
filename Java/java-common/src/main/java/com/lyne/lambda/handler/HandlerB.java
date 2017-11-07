package com.lyne.lambda.handler;

/**
 * @author nn_liu
 * @Created 2017-09-26-16:46
 */

public class HandlerB implements BaseHandler {

    public void process(BaseBean bean){
        bean.setB("test b");
        System.out.println("B..");
    }

}
