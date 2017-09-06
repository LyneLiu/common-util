package com.lyne.server;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by nn_liu on 2017/6/14.
 */
public class TimeServer {

    public static final Logger logger = LoggerFactory.getLogger(TimeServer.class);

    public static void main(String[] args) {
        ServerSocket server=null;
        try {
            server=new ServerSocket(8089);
            System.out.println("TimeServer Started on 8089...");
            int num = 0;
            while (true){
                num ++;
                Socket client = server.accept();
                //每次接收到一个新的客户端连接，启动一个新的线程来处理
                new Thread(new TimeServerHandler(client),"Server"+num).start();
            }
        } catch (IOException e) {
            logger.error(ExceptionUtils.getStackTrace(e));
        }finally {
            try {
                server.close();
                System.out.println("TimeServer closed.");
            } catch (IOException e) {
                // do nothing
            }
        }
    }

}
