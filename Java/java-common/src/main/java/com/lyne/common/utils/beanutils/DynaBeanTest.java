package com.lyne.common.utils.beanutils;

import org.apache.commons.beanutils.BasicDynaBean;
import org.apache.commons.beanutils.BasicDynaClass;
import org.apache.commons.beanutils.DynaBean;
import org.apache.commons.beanutils.DynaProperty;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by nn_liu on 2016/9/27.
 */

/**
 * DynaBean动态Bean，不需要创建VO，缺点是不支持JSTL中的<c:outvalue="{bean.name}">这样的语法
 */
public class DynaBeanTest {

    public static void main(String[] args) throws InstantiationException, IllegalAccessException, ParseException {
        /* 添加属性 */
        DynaProperty[] props = new DynaProperty[]{
            new DynaProperty("name",String.class),
            new DynaProperty("age",Integer.class),
            new DynaProperty("birth",Date.class)
        };

        /* 构造DynaClass类，管理所有属性 */
        BasicDynaClass dynaClass = new BasicDynaClass("user", BasicDynaBean.class,props);

        /* 实例化DynaBean */
        DynaBean dynaBean = dynaClass.newInstance();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        dynaBean.set("name","luffy");
        dynaBean.set("age",20);
        dynaBean.set("birth",formatter.parse("1989-02-15"));
        System.out.println(dynaBean.get("name"));
        System.out.println(dynaBean.get("age"));
        System.out.println(dynaBean.get("birth"));
    }

}
