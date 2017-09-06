package com.lyne.design_pattern.reactor_patter.main;

import com.lyne.design_pattern.reactor_patter.reactor.Reactor;

/**
 * Created by nn_liu on 2017/2/16.
 */
public class Server {

    public static void main(String[] args) {
        Reactor reactor  = new Reactor(9900);
        new Thread(reactor).start();
    }
}
