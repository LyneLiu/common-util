package com.lyne.io;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Created by nn_liu on 2016/10/20.
 */

/**
 * 通过Properties加载配置属性文件，Properties本质上是一个HashTable！
 */
public class PropertiesDemo {

    public static void main(String[] args) {
        Properties prop = new Properties();
        InputStream input = null;

        try {

            input = PropertiesDemo.class.getClassLoader().getResourceAsStream("log4j.properties");

            // load a properties file
            prop.load(input);

            System.out.println(prop.size());


        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
            if (input != null) {
                try {
                    input.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}


