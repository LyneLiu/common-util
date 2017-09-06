package com.lyne.design_pattern.reactor_patter.reactor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.util.Iterator;
import java.util.Set;

/**
 * 反应堆模式：事件驱动型的，可以处理多个输入（事件），每个特定的事件由特定的handler处理。用于解决多用户访问并发问题。
 * 传统线程池方法：一个线程对应于一个请求。
 * <p>
 * <p>
 * Note：
 * 示例——汽车是乘客访问的主体（Reactor），乘客上车后，到售票员（acceptor）处登记，之后乘客便可以休息睡觉去了，
 * 当到达乘客索的目的地后，售票员将其唤醒即可。
 * <p>
 * <p>
 * 参考链接：http://blog.csdn.net/Mailzyw/article/details/53149360
 * <p>
 * Created by nn_liu on 2017/2/16.
 */

public class Reactor implements Runnable {

    public Selector selector;

    public ServerSocketChannel serverSocketChannel;

    private static final Logger logger = LoggerFactory.getLogger(Reactor.class);

    public Reactor(int port) {
        try {
            //单线程使用一个Selector处理多个channel
            selector = Selector.open();
            serverSocketChannel = ServerSocketChannel.open();
            InetSocketAddress inetSocketAddress = new InetSocketAddress(InetAddress.getLocalHost(), port);
            serverSocketChannel.socket().bind(inetSocketAddress);
            serverSocketChannel.configureBlocking(false);

            //向selector注册channel（OP_READ、OP_WRITE、OP_CONNECT、OP_ACCEPT）
            //将channel和selector配合使用，并确认channel处于非阻塞模式
            SelectionKey selectionKey = serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);

            //利用selectionKey的attache功能绑定Acceptor。如果有事情，触发Acceptor
            selectionKey.attach(new Acceptor(this));

        } catch (IOException ex) {
            logger.error("init reactor error:", ex);
        }

    }


    @Override
    public void run() {
        try {

            while (!Thread.interrupted()) {
                int readyChannels = selector.select();
                if (readyChannels == 0) continue;

                Set<SelectionKey> selectionKeys = selector.selectedKeys();
                Iterator<SelectionKey> iterable = selectionKeys.iterator();

                //Selector如果发现channel有OP_ACCEPT或READ事件发生，便会进行遍历操作
                while (iterable.hasNext()) {

                    SelectionKey selectionKey = iterable.next();
                    dispatch(selectionKey);

                    // 当获取一个 SelectionKey 后, 就要将它删除, 表示我们已经对这个 IO 事件进行了处理.
                    // iterable.remove();
                }

                selectionKeys.clear();
            }
        } catch (IOException e) {
            logger.error("thread run error:", e);
        }

    }

    /**
     * 触发Acceptor，然后调用对应的Handler
     *
     * @param selectionKey
     */
    private void dispatch(SelectionKey selectionKey) {

        Runnable r = (Runnable) selectionKey.attachment();
        if (r != null) {
            r.run();
        }
    }
}
