package com.lyne.callback;

import static java.lang.Thread.sleep;

/**
 * Created by nn_liu on 2016/9/23.
 */

/**
 * 服务器处理数据，并调用回调函数
 */
public class Server {

    public void getMessage(CSCallBack client, String msg) {
        System.out.println("server receive the msg:"+msg);

        /*模拟服务器处理情况*/
        try {
            sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("server status:200.");
        String status = String.valueOf(200);
        client.process(status);
    }
}
