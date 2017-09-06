package com.lyne.dao;

import com.lyne.domain.User;

import java.util.List;

/**
 * Created by nn_liu on 2016/8/17.
 */
public interface UserDao {
    //使用@Insert注解指明add方法要执行的SQL
    //@Insert("insert into User(name, age) values(#{name}, #{age})")
    public int addUser(User user);

    //使用@Delete注解指明deleteById方法要执行的SQL
    //@Delete("delete from User where id=#{id}")
    public int deleteUser(int id);

    //使用@Update注解指明update方法要执行的SQL
    //@Update("update User set name=#{name},age=#{age} where id=#{id}")
    public int updateUser(User user);

    //使用@Select注解指明getById方法要执行的SQL
    //@Select("select * from User where id=#{id}")
    public User getUser(int id);

    //使用@Select注解指明getAll方法要执行的SQL
    //@Select("select * from User")
    public List<User> getAllUsers();

    //@Delete("delete from User where age=#{age}")
    public int deleteUserByage(int age);
}
