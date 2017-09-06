package com.lyne.client;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * Created by nn_liu on 2017/6/14.
 */
public class TimeClient {

    public static final Logger logger = LoggerFactory.getLogger(TimeClient.class);

    public static void main(String[] args) {
        Socket client = null;
        try {

            for (int i = 0; i < 1; i++) {
                client = new Socket("127.0.0.1", 8089);
                new Thread(new TimeClientHandler(client),"ClientThread "+i).start();
            }
        } catch (Exception e) {
            logger.error(ExceptionUtils.getStackTrace(e));
        }
    }

}
