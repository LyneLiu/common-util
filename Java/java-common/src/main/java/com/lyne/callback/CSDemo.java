package com.lyne.callback;

/**
 * Created by nn_liu on 2016/9/23.
 */
public class CSDemo {
    public static void main(String[] args) {
        Server server = new Server();
        Client client = new Client(server);
        client.sendMsg("call back demo.");
    }
}
