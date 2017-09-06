package com.lyne.guava.collect;

import com.google.common.base.Function;
import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimaps;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by nn_liu on 2017/6/6.
 */
public class ImmutableMultiMapDemo {

    public static void main(String[] args) {

        /* guava map处理 */
        List<PrdBean> prdBeans = new ArrayList<>();
        PrdBean prdBean01 = new PrdBean();
        prdBean01.setAddr("shanghai");
        prdBean01.setName("lyne");
        PrdBean prdBean02 = new PrdBean();
        prdBean02.setAddr("shanghai");
        prdBean02.setName("luffy");
        prdBeans.add(prdBean01);
        prdBeans.add(prdBean02);

        ImmutableMultimap<String, PrdBean> opsMap = Multimaps.index(prdBeans,indexPrdBean());

        System.out.println("=======================================");
        for (Map.Entry<String,PrdBean> entry: opsMap.entries()) {
            System.out.println(entry.getKey());
            System.out.println(entry.getValue());
        }

    }

    private static Function<? super PrdBean, String> indexPrdBean() {
        return new Function<PrdBean, String>() {
            @Override
            public String apply(PrdBean prdBean) {
                return prdBean.getAddr();
            }
        };
    }
}
