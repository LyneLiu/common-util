package com.lyne.lambda.handler;

/**
 * @author nn_liu
 * @Created 2017-09-26-16:46
 */

public class HandlerC implements BaseHandler {

    public void process(BaseBean bean){
        bean.setC("test c");
        System.out.println("C..");
    }

}
