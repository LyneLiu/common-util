package com.lyne.callback;

/**
 * Created by nn_liu on 2016/9/23.
 */

/**
 * 客户端：实现回调函数
 */
public class Client implements CSCallBack {

    private Server server;

    public Client(Server server) {
        this.server = server;
    }

    public void sendMsg(String msg) {
        System.out.println("client send message...");
        new Thread(new Runnable() {
            @Override
            public void run() {
                server.getMessage(Client.this, msg);
            }
        }).start();
        System.out.println("msg has been sent to server...");
    }

    @Override
    public void process(String status) {
        System.out.println("client status:" + status);
    }
}
