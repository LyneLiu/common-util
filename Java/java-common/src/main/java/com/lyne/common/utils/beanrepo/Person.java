package com.lyne.common.utils.beanrepo;

/**
 * Created by nn_liu on 2016/9/23.
 */

/**
 * Person POJO
 */
public class Person {

    private String name;
    private String email;
    private int age;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String toString() {
        return "PersonBean[name='" + name + "',email='" + email + "',age='" + age + "']";
    }
}
