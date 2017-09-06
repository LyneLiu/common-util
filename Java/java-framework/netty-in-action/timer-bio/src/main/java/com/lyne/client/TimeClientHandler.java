package com.lyne.client;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * Created by nn_liu on 2017/6/14.
 */
public class TimeClientHandler implements Runnable {

    public static final Logger logger = LoggerFactory.getLogger(TimeClientHandler.class);

    private Socket client = null;

    public TimeClientHandler(Socket client){
        this.client = client;
    }

    public void run() {
        PrintWriter writer = null;
        BufferedReader reader = null;
        try {
            client.setTcpNoDelay(true);
            writer = new PrintWriter(client.getOutputStream());
            reader = new BufferedReader(new InputStreamReader(client.getInputStream()));

            /* Note：需要添加换行符\n符号数据才能flush至server端 */
            while (true){
                writer.print("GET CURRENT TIME\n");
                writer.flush();
                String response = reader.readLine();
                logger.info("Current Time:" + response);
                Thread.sleep(5000);
            }

        } catch (Exception e) {
            logger.error(ExceptionUtils.getStackTrace(e));
        }finally {
            try {
                writer.close();
                reader.close();
                client.close();
            }catch (Exception e){
                // do nothing
            }
        }
    }
}
