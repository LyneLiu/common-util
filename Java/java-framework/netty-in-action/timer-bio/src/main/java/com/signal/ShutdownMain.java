package com.signal;

/**
 * @author nn_liu
 * @Created 2017-08-18-13:14
 */

public class ShutdownMain {

    public static void main(String[] args) {
        invokeShutdownHook();
        Runtime.getRuntime().exit(0);
    }

    private static void invokeShutdownHook()
    {
        Thread t = new Thread(new ShutdownHook(), "ShutdownHook-Thread");
        Runtime.getRuntime().addShutdownHook(t);
    }

    /**
     * 获取系统信号量
     * @return
     */
    private String getOSSignalType(){
        return System.getProperties().getProperty("os.name").
                toLowerCase().startsWith("win") ? "INT" : "USR2";
    }

}
