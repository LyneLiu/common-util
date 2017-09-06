package com.lyne.common.utils.lang;

import com.lyne.common.utils.UtilsBaseTest;
import org.apache.commons.lang3.StringUtils;

/**
 * Created by nn_liu on 2016/9/27.
 */

/**
 * Apache Common Lang对java lang工具包的扩展
 */
public class StringUtilsTest extends UtilsBaseTest {

    public static void main(String[] args) {
        /* StringBuilder用来创建频繁操作的String，比如sql语句组装等情况，空间换时间？减少性能消耗 */
        StringBuilder builder = new StringBuilder();
        /* StringUtils工具对string数据类型的数据执行相关操作 */
        System.out.println(StringUtils.isEmpty(builder.toString()));

    }

}
