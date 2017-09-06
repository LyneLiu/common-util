package com.lyne.annotation;

import java.lang.reflect.Method;

/**
 * Created by nn_liu on 2016/10/21.
 */

/**
 * 注解的作用：
 * 1、生成文档；
 * 2、跟踪代码依赖性，事项替代配置文件的功能；
 * 3、在编译时进行格式检查。
 * Note：包 java.lang.annotation中包含所有定义自定义注解所需用到的原注解和接口。
 * spring-core提供了注解工具AnnotationUtils，通过该工具可以实现注解的相关操作。
 */
public class AnalysisAnnotation {
    /**
     * 在运行时分析处理annotation类型的信息
     *
     *
     */
    public static void main(String[] args) {
        try {
            //通过运行时反射API获得annotation信息
            Class rt_class = Class.forName("com.lyne.annotation.Utility");
            Method[] methods = rt_class.getMethods();

            boolean flag = rt_class.isAnnotationPresent(Description.class);

            if(flag)
            {
                Description description = (Description)rt_class.getAnnotation(Description.class);
                System.out.println("Utility's Description--->"+description.value());
                for (Method method : methods) {
                    if(method.isAnnotationPresent(Author.class))
                    {
                        Author author = (Author)method.getAnnotation(Author.class);
                        System.out.println("Utility's Author--->"+author.name()+" from "+author.group());

                    }
                }
            }


        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

}