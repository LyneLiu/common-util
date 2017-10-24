package com.lyne.agent;

import java.io.File;
import java.lang.management.ManagementFactory;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;

public final class Tools {

    private static URLClassLoader jdkToolClassLoader;
    private static String pid;
    private static final Object locker = new Object();
    private static final Object loaderLocker = new Object();

    public static String currentPID(){
        if(pid == null){
            synchronized (locker) {
                if(pid == null) {
                    final String jvmName = ManagementFactory.getRuntimeMXBean().getName();
                    final int index = jvmName.indexOf('@');
                    pid = jvmName.substring(0, index);
                }
            }
        }
        return pid;
    }

    public static Class<?> loadJDKToolClass(String name) throws MalformedURLException, ClassNotFoundException {

        if(jdkToolClassLoader == null) {
            synchronized (loaderLocker) {
                if(jdkToolClassLoader == null) {
                    String javaPath = System.getenv("JAVA_HOME");
                    String path = javaPath + "/lib/tools.jar";
                    URL jarURl = new File(path).toURI().toURL();
                    jdkToolClassLoader = new URLClassLoader(new URL[]{jarURl});
                }
            }
        }
        return jdkToolClassLoader.loadClass(name);
    }

    public static boolean isLegalClass(String className){
        try{
            Class.forName(className);
            return true;
        }catch (Throwable e){
            return false;
        }

    }



}
