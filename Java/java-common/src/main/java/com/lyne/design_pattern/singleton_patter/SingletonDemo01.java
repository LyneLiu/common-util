package com.lyne.design_pattern.singleton_patter;

/**
 * @author nn_liu
 * @Created 2017-11-28-16:14
 */

public class SingletonDemo01 {

    private static Singleton instance;

    private SingletonDemo01 (){}

    /**
     * 当有多个线程并行调用getInstance()的时候，会创建多个实例，线程不安全
     * @return
     */
    public static Singleton getInstance() {
        if (instance == null) {
            instance = new Singleton();
        }
        return instance;
    }

}
