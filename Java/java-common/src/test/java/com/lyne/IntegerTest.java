package com.lyne;

import org.junit.Test;

/**
 * Created by lyne on 2017/2/10.
 */
public class IntegerTest {
    @Test
    public void testInteger(){
        int a = 5,b=-5,c=3,d=-3;
        System.out.println(a/c);
        System.out.println(a/d);
        System.out.println(b/c);
        System.out.println(b/d);

        System.out.println(a%c);
        System.out.println(a%d);
        System.out.println(b%c);
        System.out.println(b%d);
    }
}
