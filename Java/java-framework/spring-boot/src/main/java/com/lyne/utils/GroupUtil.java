package com.lyne.utils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * List分组
 *
 * @author nn_liu
 * @Created 2017-07-18-20:54
 */

public class GroupUtil {

    /**
     * 当有几十万数据的时候，可以将数据进行分组处理
     * 注：数据量大的情况下，sql优化、DB分表分库是解决问题的常用方法
     * @param list
     * @param <T>
     * @return
     */
    private static <T> Map<String, List<T>> buildGroupMap(List<T> list) {

        int listSize=list.size();
        int toIndex=100;    // 每100个元素分为一组
        Map<String, List<T>> map = new HashMap<>();
        int keyToken = 0;
        for(int i = 0;i<list.size();i+=100){
            if(i+100 > listSize){
                toIndex=listSize-i;
            }
            List<T> newList = list.subList(i,i+toIndex);
            map.put("keyName"+keyToken, newList);
            keyToken++;
        }

        return map;
    }

}
