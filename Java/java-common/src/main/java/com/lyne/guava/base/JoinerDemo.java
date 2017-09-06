package com.lyne.guava.base;

/**
 * Created by nn_liu on 2016/10/27.
 */

import com.google.common.base.Joiner;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * List字符串集合转字符串，通过分隔符separator进行分割
 */
public class JoinerDemo {

    private static String separator = "|";  //分割符

    private static List<String> list = initData();  //创建List字符串集合，用于测试

    /*初始化map数据集合，用于测试*/
    private static Map<String,String> dataMap = new HashMap<String,String>(){
            private static final long serialVersionUID = 1L;
            {
                put("leader","luffy");
                put("swordsman","zoro");
                put("cooker","sanji");
            }
    };

    /**
     * 初始化测试数据
     * @return
     */
    private static List initData(){
        List<String> list = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            if (i%5 == 0){
                list.add(null);
            }else {
                list.add("init_data_"+i);
            }
        }
        return list;
    }

    /**
     * 传统方法通过循环处理字符串集合
     */
    public void buildStringWithLoop(){
        StringBuilder stringBuilder = new StringBuilder();
        for (String str: list) {
            if (str != null)
                stringBuilder.append(str).append(separator);
        }
        stringBuilder.setLength(stringBuilder.length() - separator.length());
        System.out.println(stringBuilder.toString());
    }

    /**
     * 通过guava库提供的Joiner类处理字符串集合
     */
    public void buildStringWithJoiner(){

        /*skipNulls用于过滤集合中的null值*/
        String str1 = Joiner.on(separator).skipNulls().join(list);
        System.out.println(str1);
        String str2 = Joiner.on(separator).useForNull("*_*").join(list);
        System.out.println(str2);
    }

    /**
     * 使用Joiner类处理StringBuilder
     */
    public void tackleStringBuilderWithJoiner(){
        StringBuilder stringBuilder = new StringBuilder();
        Joiner joiner = Joiner.on(separator).skipNulls();
        joiner.appendTo(stringBuilder,"one","two","three");
        System.out.println(stringBuilder.toString());
    }

    /**
     * 使用Joiner类处理Map集合
     */
    public void tackleMapWithJoiner(){
        String str = Joiner.on(separator).withKeyValueSeparator("=").join(dataMap);
        System.out.println(str);
    }

    public static void main(String[] args) {
        StringBuilder builder = new StringBuilder();
        builder.append(String.format("{\"productId\":%d,", 1));
        builder.append(String.format("\"syncStatus\":\"%s\",", "F"));
        builder.append(String.format("\"modifyUser\":\"%s\",", "test"));
        builder.append(String.format("\"maintainSource\":\"%s\",", "6381"));
        builder.append("\"errorCode\":null,");
        builder.append("\"errorMessage\":null}");
        System.out.println(builder.toString());
    }

}
