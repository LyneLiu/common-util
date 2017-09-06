package com.lyne.common.utils.convert_utils;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.beanutils.Converter;
import org.apache.commons.beanutils.converters.BooleanConverter;
import org.apache.commons.beanutils.locale.converters.DateLocaleConverter;
import org.junit.Test;

import java.util.Date;

/**
 * Created by nn_liu on 2016/11/7.
 */
public class TestMyBean {
    /**
     * 首先测试的是基本的数据类型
     *
     * @throws Exception
     */
    @Test
    public void test1() throws Exception {
        String name = "Mark";
        int age = 20;
        boolean isAlive = true;

        MyBean myBean = new MyBean();
        BeanUtils.setProperty(myBean, "name", name);
        BeanUtils.setProperty(myBean, "age", age);
        // 对于boolean貌似不能进行转换
        BeanUtils.setProperty(myBean, "isAlive", isAlive);
        System.out.println("Print the result by BeanUtils");
        System.out.println("Name:" + myBean.getName());
        System.out.println("Age:" + myBean.getAge());
        System.out.println("IsAlive:" + myBean.getIsAlive());
    }

    /**
     * 对Date进行转换测试
     *
     * @throws Exception
     */
    @Test
    public void test2() throws Exception {
        String date = "2016-07-05";
        ConvertUtils.register(new DateLocaleConverter(), Date.class);
        MyBean myBean = new MyBean();
        BeanUtils.setProperty(myBean, "birthday", date);
        System.out.println("Birthday:" + myBean.getBirthday());

    }

    /**
     * 测试boolean类型的数据:由于使用IDE提供的getter和setter自动生成没有符合apache common的规范，导致属性无法赋值……
     * isAlive生成的getter和setter分别为：isAlice和setAlive……，修改后可以实现赋值。
     *
     * @throws Exception
     */
    @Test
    public void test3() throws Exception {

        boolean isalive = true;
        ConvertUtils.register(new BooleanConverter(), Boolean.class);
        MyBean myBean = new MyBean();
        BeanUtils.setProperty(myBean, "isAlive", isalive);
        System.out.println("IsAlive:" + myBean.getIsAlive());
    }

    /**
     * 测试自定义的转换类:BeanUtils.setProperties(bean,name,value)
     * Note:name区分大小写，坑啊……调了半天 ！！！
     * @throws Exception
     */
    @Test
    public void test4() throws Exception {
        MyClass myclass = new MyClass();
        myclass.setClassname("this is customer converter！" +
                "                  \n" +
                "  _____           \n" +
                " |  ___|   _ _ __  \n" +
                " | |_ | | | | '_ \\ \n" +
                " |  _|| |_| | | | |\n" +
                " |_|   \\__,_|_| |_|");
        ConvertUtils.register(new MyConverter(), MyClass.class);
        MyBean myBean = new MyBean();
        BeanUtils.setProperty(myBean, "ownClass", myclass);
        System.out.println("Customer Converter result:" + myBean.getOwnClass().getClassname());

    }
}
