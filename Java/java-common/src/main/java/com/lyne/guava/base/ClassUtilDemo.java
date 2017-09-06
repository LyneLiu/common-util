package com.lyne.guava.base;

import com.google.common.primitives.Primitives;
import org.apache.commons.lang3.ClassUtils;

import java.util.ArrayList;
import java.util.Date;

/**
 * @author nn_liu
 * @Created 2017-08-29-20:01
 */

public class ClassUtilDemo {

    public static void main(String[] args) {

        System.out.println("=====================Apache Common========================");
        System.out.println("Integer:" + ClassUtils.isPrimitiveOrWrapper(Integer.class));
        System.out.println("String:" + ClassUtils.isPrimitiveOrWrapper(String.class));
        System.out.println("Long:" + ClassUtils.isPrimitiveOrWrapper(Long.class));
        System.out.println("Double:" + ClassUtils.isPrimitiveOrWrapper(Double.class));
        System.out.println("ArrayList:" + ClassUtils.isPrimitiveOrWrapper(ArrayList.class));
        System.out.println("Date:" + ClassUtils.isPrimitiveOrWrapper(Date.class));

        System.out.println("=====================Guava Common========================");
        System.out.println("Integer:" + Primitives.isWrapperType(Integer.class));
        System.out.println("String:" + Primitives.isWrapperType(String.class));
        System.out.println("Long:" + Primitives.isWrapperType(Long.class));
        System.out.println("Double:" + Primitives.isWrapperType(Double.class));
        System.out.println("ArrayList:" + Primitives.isWrapperType(ArrayList.class));
        System.out.println("Date:" + Primitives.isWrapperType(Date.class));
    }

}
