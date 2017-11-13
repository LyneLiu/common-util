package com.lyne.guava.cache;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import lombok.extern.slf4j.Slf4j;

/**
 * CacheLoader的方式适用于通过默认的方法来记载或计算与键关联的值
 * <p>
 * 参考链接：
 * http://www.jianshu.com/p/d2fb0f5ccdcd
 *
 * @author nn_liu
 * @Created 2017-11-09-10:38
 */

@Slf4j
public class CacheLoaderDemo {

    public void testLoadingCache() throws Exception {
        LoadingCache<String, String> cahceBuilder =
                CacheBuilder.newBuilder().build(new CacheLoader<String, String>() {
                    @Override public String load(String key) throws Exception {
                        String strProValue = "hello " + key + "!";
                        log.debug("loading key..." + key + ":" + strProValue);
                        return strProValue;
                    }

                });

        System.out.println("jerry value:" + cahceBuilder.getUnchecked("jerry"));
        System.out.println("jerry value:" + cahceBuilder.get("jerry"));
        System.out.println("peida value:" + cahceBuilder.get("peida"));
        System.out.println("peida value:" + cahceBuilder.getUnchecked("peida"));
        System.out.println("lisa value:" + cahceBuilder.getUnchecked("lisa"));
        cahceBuilder.put("harry", "ssdded");
        System.out.println("harry value:" + cahceBuilder.get("harry"));
    }

    public static void main(String[] args) throws Exception {
        CacheLoaderDemo demo = new CacheLoaderDemo();
        demo.testLoadingCache();
    }

}
