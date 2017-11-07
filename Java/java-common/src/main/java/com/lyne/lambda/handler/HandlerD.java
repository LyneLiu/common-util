package com.lyne.lambda.handler;

/**
 * @author nn_liu
 * @Created 2017-09-26-16:46
 */

public class HandlerD implements BaseHandler {

    public void process(BaseBean bean){
        bean.setD("test d");
        System.out.println("D..");
    }

}
