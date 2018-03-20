package com.lyne.common.utils.beanutils;

import java.io.*;

/**
 *
 * 通过序列化方式对对象深度拷贝
 * @author nn_liu
 * @Created 2018-03-14-15:18
 */

public class SerializableCloneUtils {

    public static <T extends Serializable> T clone(T obj){
        T cloneObj = null;
        ByteArrayOutputStream outputStream = null;
        ByteArrayInputStream inputStream = null;
        try {
            // 写入字节流
            outputStream = new ByteArrayOutputStream();
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream);
            objectOutputStream.writeObject(obj);
            objectOutputStream.close();

            // 分配内存，写入原始对象，生成新对象
            inputStream = new ByteArrayInputStream(outputStream.toByteArray());
            ObjectInputStream objectInputStream = new ObjectInputStream(inputStream);
            cloneObj = (T) objectInputStream.readObject();
            objectInputStream.close();

        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            if (outputStream != null){
                try {
                    outputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (inputStream != null){
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return cloneObj;
    }

    public static void main(String[] args) {
        // 写封邮件
        Email email = new Email("请参加会议","请与今天12:30到二会议室参加会议...");

        Person person1 =  new Person("张三",email);

        Person person2 =  SerializableCloneUtils.clone(person1);
        person2.setName("李四");
        Person person3 =  SerializableCloneUtils.clone(person1);
        person3.setName("王五");
        person1.getEmail().setContent("请与今天12:00到二会议室参加会议...");

        System.out.println(person1.getName() + "的邮件内容是：" + person1.getEmail().getContent());
        System.out.println(person2.getName() + "的邮件内容是：" + person2.getEmail().getContent());
        System.out.println(person3.getName() + "的邮件内容是：" + person3.getEmail().getContent());

        System.out.println(String.format("%s%s%s", "A", "B"));
    }

}


class Person implements Serializable{

    private static final long serialVersionUID = 2631590509760908280L;
    /** 姓名 **/
    private String name;

    /** 电子邮件 **/
    private Email email;

    public Person(String name, Email email) {
        this.name = name;
        this.email = email;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Email getEmail() {
        return email;
    }

    public void setEmail(Email email) {
        this.email = email;
    }
}

class Email implements Serializable{

    private static final long serialVersionUID = 1267293988171991494L;

    private String title;

    private String content;

    public Email(String title, String content) {
        this.title = title;
        this.content = content;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
