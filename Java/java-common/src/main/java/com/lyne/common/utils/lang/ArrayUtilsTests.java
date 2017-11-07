package com.lyne.common.utils.lang;

import org.apache.commons.lang3.ArrayUtils;

/**
 * @author nn_liu
 * @Created 2017-11-03-13:49
 */

public class ArrayUtilsTests {

    public static void main(String[] args) {
        String[] emptyStr = null;
        String[] arrayStr01 = new String[]{""};
        String[] arrayStr02 = new String[]{"one", "two", "three"};

        System.out.println(arrayStr01.length);
        System.out.println(ArrayUtils.isNotEmpty(emptyStr));
        System.out.println(ArrayUtils.isNotEmpty(arrayStr01));
        System.out.println(ArrayUtils.isNotEmpty(arrayStr02));
    }

}
