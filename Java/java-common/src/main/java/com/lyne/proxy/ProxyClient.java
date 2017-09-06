package com.lyne.proxy;

/**
 * @author nn_liu
 * @Created 2017-08-24-18:57
 */

public class ProxyClient {

    public static void main(String[] args) {
        Subject subject = new ProxySubject();
        subject.request();
    }

}
