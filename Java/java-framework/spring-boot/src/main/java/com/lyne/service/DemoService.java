package com.lyne.service;

import com.google.common.base.Function;
import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimaps;
import com.lyne.bo.PrdBean;
import com.lyne.common.RedisCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;

/**
 * Created by nn_liu on 2017/6/26.
 */

@Service
public class DemoService {

    @Autowired
    private CacheManager cacheManager;

    public void calendarDemo() {
        /* 日期处理 */
        Calendar calendar = Calendar.getInstance();
        Date currDate = calendar.getTime();
        calendar.add(Calendar.MONDAY, 3);
        Date futureDate = calendar.getTime();
        System.out.println(currDate);
        System.out.println(futureDate);

    }

    public void bigDecimalDemo() {
         /* BigDecimal处理 */
        BigDecimal big1 = new BigDecimal(400.000);
        BigDecimal big2 = new BigDecimal(400.000);
        System.out.println(big1 == big2);
        System.out.println(big1.compareTo(big2));
    }


    public void guavaMapDemo(List<PrdBean> prdBeans) {
        /* guava map处理 */
        ImmutableMultimap<String, PrdBean> opsMap = Multimaps.index(prdBeans, indexPrdBean());

        //ImmutableMap<String, PrdBean> opsMapUnique = Maps.uniqueIndex(prdBeans,indexPrdBean());

        System.out.println("=======================================");
        for (Map.Entry<String, PrdBean> entry : opsMap.entries()) {
            System.out.println(entry.getKey());
            System.out.println(entry.getValue());
        }
        /*System.out.println("=======================================");
        for (Map.Entry<String, PrdBean> entry: opsMapUnique.entrySet()) {
            System.out.println(entry.getKey());
            System.out.println(entry.getValue());
        }*/

        System.out.println(opsMap.get("shanghai").asList().size());
        System.out.println(opsMap.get("test").isEmpty());

    }

    public void handle(PrdBean prdBean){
        System.out.println(prdBean.toString());
    }

    private Function<? super PrdBean, String> indexPrdBean() {
        return new Function<PrdBean, String>() {
            @Override public String apply(PrdBean prdBean) {
                return prdBean.getAddr();
            }
        };
    }

    /**
     * 通过@Cacheable注解将数据写入redisCache缓存
     * @param name
     * @return
     */
    @Cacheable(value = "redisCache",key = "#name")
    public PrdBean prdInfo(String name){
        PrdBean prdBean = new PrdBean();
        prdBean.setName(name);
        prdBean.setAddr("shanghai");
        return prdBean;
    }

    /**
     * 获取redisCache缓存中存储的数据
     * @param name
     * @return
     */
    public Object getPrdInfo(String name) {
        return ((RedisCache)cacheManager.getCache("redisCache").getNativeCache()).get(name,PrdBean.class);
    }
}
