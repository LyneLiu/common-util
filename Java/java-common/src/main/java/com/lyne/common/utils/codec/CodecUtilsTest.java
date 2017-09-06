package com.lyne.common.utils.codec;

import com.lyne.common.utils.UtilsBaseTest;
import org.apache.commons.codec.CharEncoding;
import org.apache.commons.codec.binary.Base64;

import java.io.UnsupportedEncodingException;


/**
 * Created by nn_liu on 2016/9/26.
 */

/**
 * 编码工具集，codec
 */
public class CodecUtilsTest extends UtilsBaseTest {

    /* base64编码 */
    public static String encodeStr(String str){
        Base64 base64 = new Base64();
        try {
            str = base64.encodeToString(str.getBytes(CharEncoding.UTF_8));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return str;
    }

    /* base64解码 */
    public static String decodeStr(String str){
        String decodeStr = new String(Base64.decodeBase64(str));
        return decodeStr;
    }

    public static void main(String[] args) {
        String str = "this is a code demo.";
        String encodeStr = encodeStr(str);
        String decodeStr = decodeStr(encodeStr);
        System.out.println(encodeStr);
        System.out.println(decodeStr);
    }

}
