package com.lyne.annotation;

import java.lang.annotation.*;

/**
 * Created by nn_liu on 2016/10/21.
 */

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Documented
public @interface Description {

    String value();

}
