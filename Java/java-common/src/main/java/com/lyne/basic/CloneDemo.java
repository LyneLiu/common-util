package com.lyne.basic;

/**
 * @author nn_liu
 * @Created 2018-03-13-10:45
 */

public class CloneDemo {

    public static void main(String[] args) throws CloneNotSupportedException {
        Person p1 = new Person("zhang", 30);

        Person p2 = p1;

        // clone()方法为浅拷贝
        Person p3 = (Person) p1.clone();

        System.out.println("equal:" + (p2 == p1));
        System.out.println("clone:" + (p3 == p1));
        System.out.println("clone:age-" + (p3.getAge() == p1.getAge()));
        System.out.println("clone:name-" + (p3.getName() == p1.getName()));

    }

}


class Person implements Cloneable {

    private String name;

    private int age;

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

    public Person(String name, int age) {
        this.name = name;
        this.age = age;
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        return (Person) super.clone();
    }

    public Person clone(Object o){
        return (Person) o;
    }

}
