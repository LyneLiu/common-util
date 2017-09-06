package com.lyne.common.utils.convert_utils;

import java.util.Date;

/**
 * Created by nn_liu on 2016/11/7.
 */

/**
 * 自定义Bean，并且包含自定义的类MyClass
 */
public class MyBean {
    private String name;
    private int age;
    private Date birthday;
    private boolean isAlive;
    private MyClass ownClass;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public boolean getIsAlive() {
        return isAlive;
    }

    public void setIsAlive(boolean alive) {
        isAlive = alive;
    }

    public MyClass getOwnClass() {
        return ownClass;
    }

    public void setOwnClass(MyClass ownClass) {
        this.ownClass = ownClass;
    }
}
