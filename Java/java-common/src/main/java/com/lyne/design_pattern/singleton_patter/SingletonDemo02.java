package com.lyne.design_pattern.singleton_patter;

/**
 * @author nn_liu
 * @Created 2017-11-28-16:24
 */

public class SingletonDemo02 {

    private static Singleton instance;

    private SingletonDemo02 (){}

    /**
     * 添加synchronized至getInstance()方法，任何时候只能有一个线程调用改方法，所以该方法并不高效
     * @return
     */
    public synchronized static Singleton getInstance() {
        if (instance == null) {
            instance = new Singleton();
        }
        return instance;
    }

}
