package com.lyne.lambda.handler;

/**
 * @author nn_liu
 * @Created 2017-09-26-16:46
 */

public class HandlerE implements BaseHandler {

    public void process(BaseBean bean){
        bean.setE("test e");
        System.out.println("E..");
    }

}
