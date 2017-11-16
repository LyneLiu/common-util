package com.lyne.domain;

/**
 *
 * http://zhuyuehua.iteye.com/blog/1717525
 * https://my.oschina.net/linuxred/blog/38802
 *
 * Created by nn_liu on 2016/8/17.
 */
public class User {
    private int id;
    private String name;
    private int age;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

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


    @Override
    public String toString() {
        return "User [id=" + id + ", name=" + name + ", age=" + age + "]";
    }
}
