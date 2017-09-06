package com.lyne.design_pattern.reactor_patter.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;

/**
 * Created by nn_liu on 2017/2/16.
 */
public class SocketReadHandler implements Runnable{

    private static final Logger logger = LoggerFactory.getLogger(SocketReadHandler.class);

    private static final int READING = 0;
    private static final int SENDING = 1;
    private int state = READING;
    private String clientMsg = "";
    private ByteBuffer input = ByteBuffer.allocate(1024);

    private SocketChannel socketChannel;
    private SelectionKey selectionKey;

    public SocketReadHandler(Selector selector, SocketChannel socketChannel) {
        this.socketChannel = socketChannel;
        try {
            socketChannel.configureBlocking(false);
            selectionKey = socketChannel.register(selector,0);
            selectionKey.attach(this);

            selectionKey.interestOps(SelectionKey.OP_READ);
            selector.wakeup();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        try {
            if (state == READING) {
                read();
            } else if (state == SENDING) {
                send();
            }
        } catch (IOException ex) {
            logger.error("connect to client error:",ex);
        }
    }

    void read() {
        int readCount = 0;
        try {
            readCount = socketChannel.read(input);
            if (readCount > 0) {
                readProcess(readCount);
            }
            state = SENDING;
            // Interested in writing
            if(selectionKey.interestOps()==1){
                selectionKey.interestOps(selectionKey.interestOps() | (SelectionKey.OP_WRITE));

            }
        } catch (IOException e) {
            //socket channel is not yet connected, so close the channel
            try {
                socketChannel.close();
            } catch (IOException closeException) {
                logger.error("finish channel error:",closeException);
            }
            logger.error("read channel input error:",e);
        }

    }

    /**
     * Processing of the read message. This only prints the message to stdOut.
     *
     * @param readCount
     */
    synchronized void readProcess(int readCount) {
        StringBuilder sb = new StringBuilder();
        input.flip();
        byte[] subStringBytes = new byte[readCount];
        byte[] array = input.array();
        System.arraycopy(array, 0, subStringBytes, 0, readCount);
        // Assuming ASCII (bad assumption but simplifies the example)
        sb.append(new String(subStringBytes));
        input.clear();
        clientMsg = sb.toString().trim();
    }

    void send() throws IOException {
        System.out.println("Saying hello to " + clientMsg);
        ByteBuffer output = ByteBuffer.wrap(("Hello " + clientMsg + "\n").getBytes());
        socketChannel.write(output);
        selectionKey.interestOps(selectionKey.interestOps() & (~SelectionKey.OP_WRITE));
        state = READING;
    }

}
