package com.lyne.basic;

/**
 * @author nn_liu
 * @Created 2017-12-11-18:45
 */

public class EnumDemo {

    public static void main(String[] args) {
        AppType type = AppType.from("running");
        System.out.println(type.getCode() + ":" + type.getStatus());
    }

}
