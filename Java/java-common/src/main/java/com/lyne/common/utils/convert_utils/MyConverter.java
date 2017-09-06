package com.lyne.common.utils.convert_utils;

import org.apache.commons.beanutils.Converter;

/**
 * Created by nn_liu on 2016/11/7.
 */

/**  已有的类型转换器
 *   AbstractArrayConverter
 *   AbstractConverter
 *   ArrayConverter
 *   BigDecimalConverter
 *   BooleanArrayConverter
 *   BooleanConverter
 *   ByteArrayConverter
 *   ByteConverter
 *   CalendarConverter
 *   CharacterArrayConverter
 *   CharacterConverter
 *   ClassConverter
 *   ConverterFacade
 *   DateCOnverter
 *   DateTimeConverter
 *   DoubleConverter
 *   FloatArrayConverter
 *   FileConverter
 *   FloatConverter
 *   IntegerArrayConverter
 *   IntegerConverter
 *   LongConverter
 *   LondArrayCOnverter
 *   NumberConverter
 *   ShortArrayConverter
 *   ShortConverter
 *   SqlTimeConverter
 *   SqlTimestampConverter
 *   StringArrayConverter
 *   StringConverter
 *   URLConverter
 */
public class MyConverter implements Converter {

    @Override
    public <T> T convert(Class<T> type, Object value) {
        MyClass myclass = (MyClass) value;
        return (T) myclass;
    }
}
