package com.lyne.design_pattern.singleton_patter;

/**
 * @author nn_liu
 * @Created 2017-11-28-19:29
 */
public enum EasySingleton {

    INSTANCE;

    private Singleton instance;

    EasySingleton(){
        instance = new Singleton();
    }

    public Singleton getInstance() {
        return instance;
    }

}
