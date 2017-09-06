package com.lyne.design_pattern.reactor_patter.main;

import com.lyne.design_pattern.reactor_patter.client.ReactorClient;

import java.io.IOException;

/**
 * Created by nn_liu on 2017/2/17.
 */
public class Client {

    public static void main(String[] args) {
        ReactorClient client = new ReactorClient("10.33.22.1", 9900);
        try {
            client.runClient();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
