package com.lyne.lambda.handler;

/**
 * @author nn_liu
 * @Created 2017-09-26-16:46
 */

public class HandlerA implements BaseHandler{

    public void process(BaseBean bean){
        bean.setA("test a");
        System.out.println("A..");
    }

}
