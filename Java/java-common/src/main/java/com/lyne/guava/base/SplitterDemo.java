package com.lyne.guava.base;

/**
 * Created by nn_liu on 2016/10/27.
 */

import com.google.common.base.Splitter;

import java.util.Map;
import java.util.Set;

/**
 * Splitter:作用和Joiner的作用刚好相反，用于切割字符串，返回集合
 */
public class SplitterDemo {

    private static String separator = "|";  // 分隔符

    private static String separatorForMap = "=";    //map分隔符

    private static String string = "leader=luffy|cooker=sanji||swordsman=zoro|||navigator=nami";  //测试字符串

    /*Splitter将字符串分割为字符串集合*/
    public void listWithSplitter(){
        /*omitEmptyStrings方法用于忽略分割后产生的空值*/
        Iterable strings = Splitter.on(separator).omitEmptyStrings().split(string);
        System.out.println(strings);    // 结果：[leader=luffy, cooker=sanji, swordsman=zoro, navigator=nami]
        /*trimResults方法用户处理分割后每一项中的空白符*/
        Iterable string2 = Splitter.on(separator).trimResults().split(string);
        System.out.println(string2);    // 结果：[leader=luffy, cooker=sanji, , swordsman=zoro, , , navigator=nami]
    }

    /*Splitter将字符串分割为Map集合*/
    public void mapWithSplitter(){
        /*omitEmptyStrings方法用于忽略分割后产生的空值*/
        Splitter.MapSplitter mapSplitter = Splitter.on(separator).omitEmptyStrings().withKeyValueSeparator(separatorForMap);
        Map<String,String> map = mapSplitter.split(string);
        Set keySet = map.keySet();
        System.out.println(keySet);     // 结果：[leader, cooker, swordsman, navigator]
    }

    public static void main(String[] args) {
        SplitterDemo splitterDemo = new SplitterDemo();
        splitterDemo.listWithSplitter();
        splitterDemo.mapWithSplitter();
    }
}
