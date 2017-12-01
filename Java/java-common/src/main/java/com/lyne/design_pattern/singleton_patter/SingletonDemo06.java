package com.lyne.design_pattern.singleton_patter;

/**
 * @author nn_liu
 * @Created 2017-11-28-19:27
 */

public class SingletonDemo06 {

    private static class SingletonHolder {
        private static final Singleton INSTANCE = new Singleton();
    }

    private SingletonDemo06 (){}

    /**
     * 静态内部类Singleton为私有的，《Effective Java》中推荐方法
     * @return
     */
    public static Singleton getInstance() {
        return SingletonHolder.INSTANCE;
    }


}
