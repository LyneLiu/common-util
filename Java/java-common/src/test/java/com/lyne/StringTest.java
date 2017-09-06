package com.lyne;

import org.junit.Test;

/**
 * Created by lyne on 2017/2/10.
 */
public class StringTest {

    @Test
    public void testString(){
        String s = "abcde";

        StringBuffer s1 = new StringBuffer("abcde");

        if (s.equals(s1)){
            s1 = null;
        }

        if (s1.equals(s)){
            s = null;
        }
    }
}
