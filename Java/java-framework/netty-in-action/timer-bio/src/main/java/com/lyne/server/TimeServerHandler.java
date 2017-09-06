package com.lyne.server;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.time.LocalDateTime;

/**
 * Created by nn_liu on 2017/6/14.
 */
public class TimeServerHandler implements Runnable {

    public static final Logger logger = LoggerFactory.getLogger(TimeServerHandler.class);

    private Socket clientProxy;

    public TimeServerHandler(Socket clientProxy){
        System.out.println("Create a new ServerThread...");
        this.clientProxy = clientProxy;
    }

    public void run() {
        BufferedReader reader = null;
        PrintWriter writer = null;

        try {
            clientProxy.setTcpNoDelay(true);
            reader = new BufferedReader(new InputStreamReader(clientProxy.getInputStream()));
            writer = new PrintWriter(clientProxy.getOutputStream());

            while (true){
                String request = reader.readLine();
                if (request == null || !request.equalsIgnoreCase("GET CURRENT TIME")){
                    writer.println("BAD REQUEST!\n");
                }else {
                    writer.print(LocalDateTime.now().toString()+"\n");
                }
                writer.flush();
            }

        } catch (Exception e) {
            logger.error(ExceptionUtils.getStackTrace(e));
        }finally {
            try {
                writer.close();
                reader.close();
                clientProxy.close();
            }catch (Exception e){
                // do nothing
            }
        }
    }
}
