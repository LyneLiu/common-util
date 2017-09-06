package com.lyne.common.utils.beanutils;

import com.lyne.common.utils.UtilsBaseTest;
import com.lyne.common.utils.beanrepo.Person;
import org.apache.commons.beanutils.BeanUtils;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by nn_liu on 2016/9/23.
 */

/**
 * BeanUtils——提供了对JavaBean的各种操作，克隆对象、属性等。
 */
public class BeanUtilsTest extends UtilsBaseTest {

    public static void main(String[] args) {
        /* 克隆Person对象 */
        Person person = new Person();
        person.setAge(20);
        person.setName("luffy");
        person.setEmail("caomao@sea.com");

        Person clonedPerson = null;
        try {
            clonedPerson = (Person) BeanUtils.cloneBean(person);
            System.out.println(clonedPerson.getName()+">>>"+clonedPerson.getEmail()+">>>"+clonedPerson.getAge());
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }

        /* 将Map转换为一个Bean */
        try {
            Map map = new HashMap();
            map.put("name","tom");
            map.put("email","tom@sea.com");
            map.put("age",21);
            Person map2Person = new Person();
            BeanUtils.populate(map2Person,map);
            System.out.println(map2Person.getName()+">>>"+map2Person.getEmail()+">>>"+map2Person.getAge());

        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }

        /*将Bean装换为一个Map对象*/
        try {
            Map person2Map = BeanUtils.describe(person);
            System.out.println(person2Map.size());
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }

    }
}
