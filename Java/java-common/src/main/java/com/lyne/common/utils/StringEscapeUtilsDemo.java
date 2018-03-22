package com.lyne.common.utils;

/**
 * Created by nn_liu on 2016/12/14.
 */

import org.apache.commons.lang.StringEscapeUtils;

/**
 * Apache Commons-lang转移工具类：提供防止sql注入、xss注入攻击的功能
 */
public class StringEscapeUtilsDemo {

    /**
     *  测试SQL注入
     */
    public void testSQLInject() {
        String userName = "1 ' or ' 1 '=' 1";
        String password = "123456";
        userName = StringEscapeUtils.escapeSql(userName);
        password = StringEscapeUtils.escapeSql(password);
        String sql = "SELECT COUNT (userId) FROM t_user WHERE userName = '" + userName + "' AND password ='" + password + "'";
        System.out.println(sql);
    }

    /**
     * html转义
     * @param str
     */
    public void testHtml(String str) {
        System.out.println("Html escape: " + StringEscapeUtils.escapeHtml(str));
        System.out.println("Html unescape: " + StringEscapeUtils.unescapeHtml(str));
    }

    /**
     * java转义
     * @param str
     */
    public void testJava(String str) {
        System.out.println("Java escape: " + StringEscapeUtils.escapeJava(str));
        System.out.println("Java unescape: " + StringEscapeUtils.unescapeJava(str));
    }

    /**
     * javascript转义
     * @param str
     */
    public void testJavaScript(String str) {
        System.out.println("JavaScript escape: " + StringEscapeUtils.escapeJavaScript(str));
        System.out.println("JavaScript unescape: " + StringEscapeUtils.unescapeJavaScript(str));
    }

    public static void main(String[] args) {
        StringEscapeUtilsDemo demo = new StringEscapeUtilsDemo();
        demo.testSQLInject();
        String ctrip = "携程-上海";
        demo.testHtml(ctrip);
        demo.testJava(ctrip);
        demo.testJavaScript(ctrip);
    }

}
