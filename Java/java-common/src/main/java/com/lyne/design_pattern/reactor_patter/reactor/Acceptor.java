package com.lyne.design_pattern.reactor_patter.reactor;

import com.lyne.design_pattern.reactor_patter.handler.SocketReadHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.channels.SocketChannel;

/**
 * Created by nn_liu on 2017/2/16.
 */
public class Acceptor implements Runnable {

    private static final Logger logger = LoggerFactory.getLogger(Acceptor.class);

    private Reactor reactor;

    public Acceptor(Reactor reactor) {
        this.reactor = reactor;
    }


    @Override
    public void run() {
        try {
            SocketChannel socketChannel = reactor.serverSocketChannel.accept();
            if (socketChannel != null)
                new SocketReadHandler(reactor.selector, socketChannel); // 调用Handler来处理channel，唤醒selector
        } catch (IOException e) {
            logger.error("init handler errror:",e);
        }
    }
}
