package com.lyne.guava.base;

import com.google.common.base.Charsets;
import com.google.common.base.Strings;

import java.io.UnsupportedEncodingException;

/**
 * Created by nn_liu on 2016/10/31.
 */
public class StringAndCharSetsDemo {

    private final static String testStr = "another sunny day!";

    /*print函数*/
    private void print(Object obj){
        System.out.println(String.valueOf(obj));
    }

    /*CharSets是一个字符集常量类，包含了常用的字符集常量：目前只有UTF_8、ISO_8859_1、US_ASCII、UTF_16、UTF_16BE、UTF_16LE*/
    public void funOfCharSets(){
        print(Charsets.UTF_8);
        print(Charsets.ISO_8859_1);
        print(Charsets.US_ASCII);
        print(Charsets.UTF_16);
        print(Charsets.UTF_16BE);
        print(Charsets.UTF_16LE);

        byte[] bytes ;

        /*常用的获取bytes方法*/
        try{
            testStr.getBytes("UTF-8");
        }catch (UnsupportedEncodingException e){
            e.printStackTrace();
        }

        /*通过CharSets指定编码，Charsets.UTF_8 == Charset.forName("UTF-8");*/
        bytes = testStr.getBytes(Charsets.UTF_8);
    }

    /**
     * Strings：字符串常用处理方法
     */
    public void funOfStrings() {
        //我们需要将测试字符串变成一个长度为6的字符串，位数不够的话填充字符c
        //这样的需求，经常出现在有金额表示的时候，如4.0补充到4.000
        //传统的做法，我们需要循环追加，如下
        StringBuilder sb = new StringBuilder(testStr);
        char c = '!';
        for (int i = 0; i < 20 - testStr.length(); i++) {
            sb.append(c);
        }
        print(sb.toString());   //another sunny day!!!

        //guava中提供了padEnd（param1,length,param2）方法
        //将param1的长度改变为length，长度不足的话向后追加字符param2
        print(Strings.padEnd(testStr, 20, c));  //another sunny day!!!
        //这里如果param1的长度大于length，则默认返回原字符串param1
        print(Strings.padEnd(testStr, 10, c));  //another sunny day!
        //与之类似，也有padStart()方法，向前追加，长度超出默认返回原字符
        print(Strings.padStart(testStr, 22, c));    //!!!!another sunny day!

        //另外，还有几个常用的方法
        //nullToEmpty：将null值转为空字符串
        print(Strings.nullToEmpty(null));   //""
        //emptyToNull：将空字符串转为null
        print(Strings.emptyToNull("")); //null
        //isNullOrEmpty：判断字符串为null或空字符串
        print(Strings.isNullOrEmpty(null)); //true
        //repeat：用于将指定字符串循环拼接多次返回
        print(Strings.repeat(testStr,3));   //another sunny day!another sunny day!another sunny day!

        //另外，有两个方法用来进行字符串的比较
        //commonSuffix：返回两个字符串中相同的后缀部分
        print(Strings.commonSuffix("nihaoma?","nibuhaoma?")); //haoma?
        //commonPrefix：返回两个字符串中相同的前缀部分
        print(Strings.commonPrefix("nihaoma?","nibuhaoma?")); //ni

    }

    public static void main(String[] args) {
        StringAndCharSetsDemo stringAndCharSetsDemo = new StringAndCharSetsDemo();
        stringAndCharSetsDemo.funOfCharSets();
        stringAndCharSetsDemo.funOfStrings();
    }

}
