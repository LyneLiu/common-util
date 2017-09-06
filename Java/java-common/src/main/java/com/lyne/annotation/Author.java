package com.lyne.annotation;

import java.lang.annotation.*;

/**
 * Created by nn_liu on 2016/10/21.
 */

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Author {

    String name();
    String group();
}
