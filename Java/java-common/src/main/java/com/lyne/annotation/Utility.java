package com.lyne.annotation;

/**
 * Created by nn_liu on 2016/10/21.
 */
@Description(value = "这是一个有用的工具类")
public class Utility {

    @Author(name = "haoran_202",group="com.magc")
    public String work()
    {
        return "work over!";
    }
}