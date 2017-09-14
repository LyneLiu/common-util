package com.lyne.common.utils.convert_utils.bean_converter;

import com.lyne.common.data_serialize.json.JsonUtil;
import org.apache.commons.beanutils.BeanUtils;

import java.lang.reflect.InvocationTargetException;

/**
 * @author nn_liu
 * @Created 2017-09-14-13:59
 */

public class BeanConverterUtils extends BeanUtils {

    public static void main(String[] args) {
        ErrorBeanUtilObject from = new ErrorBeanUtilObject();
        ErrorBeanUtilObject to = new ErrorBeanUtilObject();
        //from.setDate(new java.util.Date());
        from.setName("TTTT");
        try {
            // Note:如果from.setDate去掉，此处出现conveter异常的问题在新的版本中已经修复
            BeanUtils.copyProperties(to, from);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        System.out.println(JsonUtil.serialize(from));
        System.out.println(JsonUtil.serialize(to));
    }

}

