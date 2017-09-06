package com.lyne.common;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by nn_liu on 2016/9/22.
 */

/**
 * IDEA中JDK源码的调试：Settings --> Build,Execution,Deployment --> Debugger --> Steping
 * 取消Do not step into the classes，便可以进行源码的调试
 */
public class HashMapDemo {

    public static void main(String[] args) {
        Map<String, String> map = new HashMap<String, String>();
        map.put("key", "value");
        System.out.println(map.get("key"));
    }

}
