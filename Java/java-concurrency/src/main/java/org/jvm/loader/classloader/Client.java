package org.jvm.loader.classloader;

import com.lyne.agent.AgentTool;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.net.URISyntaxException;

/**
 * Created by nn_liu on 2017/5/23.
 */
public class Client {
    public static void main(String[] args) throws URISyntaxException {

        //MyClassLoader的父类加载器为系统默认的加载器AppClassLoader
        MyClassLoader myCLoader = new MyClassLoader("MyClassLoader");
        //指定MyClassLoader的父类加载器为ExtClassLoader
        //MyClassLoader myCLoader = new MyClassLoader(ClassLoader.getSystemClassLoader().getParent(),"MyClassLoader");
        myCLoader.setPath(Client.class.getProtectionDomain().getCodeSource().getLocation().toURI().toString());
        Class<?> clazz;
        try {
            clazz = myCLoader.loadClass(Client.class.getName());
            Field[] filed = clazz.getFields();   //获取加载类的属性字段
            Method[] methods = clazz.getMethods();   //获取加载类的方法字段
            System.out.println("该类的类加载器为：" + clazz.getClassLoader());
            System.out.println("该类的类加载器的父类为:" + clazz.getClassLoader().getParent());
            System.out.println("该类的名称为：" + clazz.getName());
            System.out.println(clazz.getClassLoader().toString());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
