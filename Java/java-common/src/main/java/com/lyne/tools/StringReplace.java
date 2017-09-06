package com.lyne.tools;

import com.google.common.base.Joiner;
import com.google.common.collect.ImmutableMap;

import java.io.*;

/**
 * Created by nn_liu on 2016/10/24.
 */

/**
 * Date:2016/10/24
 * Guava使用：Joiner和Spliter
 * 参考链接：http://www.cnblogs.com/whitewolf/p/4214749.html
 * common-lange使用：StringUtils
 */
public class StringReplace {
    public static void main(String[] args) throws IOException {

        String encoding = "UTF-8"; // 字符编码(可解决中文乱码问题 )
        /*输入文件*/
        File inFile = new File("F:/bella/temp.txt");

        /*输出文件*/
        File outFile = new File("F:/bella/out.txt");
        //outFile.createNewFile();

        /*Joiner Usage*/
        String joinerResult = Joiner.on("&").withKeyValueSeparator("=").join(ImmutableMap.of("id", "123", "name", "green"));
        System.out.println("Joiner Result ===>:"+joinerResult);  // 运行结果：Joiner Result ===>:id=123&name=green

        if (inFile.isFile() && inFile.exists()) {
            InputStreamReader read = new InputStreamReader(
                    new FileInputStream(inFile), encoding);
            BufferedReader bufferedReader = new BufferedReader(read);
            String lineTXT = null;
            while ((lineTXT = bufferedReader.readLine()) != null) {
                String[] strings = lineTXT.toString().trim().split("\\),");
                for (String str:strings) {
                    tackleStr(str);
                }
            }

            read.close();
        } else {
            System.out.println("找不到指定的文件！");
        }

    }

    private static String tackleStr(String str){
        String rawStr = str;
        String tackledStr="";

        /*删除括号*/
        rawStr = rawStr.replace("(","");
        rawStr = rawStr.replace(");","");
        String[] strings = rawStr.split(",");
        if (strings.length == 37 || strings.length == 36){
            for (int i = 0; i < strings.length; i++) {
                if (i == 0)
                    continue;

                if (i == strings.length){
                    tackledStr += strings[i];
                }else {
                    tackledStr += strings[i]+",";
                }

            }
        }else{
            tackledStr = rawStr;
        }

        System.out.print("("+tackledStr+"),");
        return tackledStr;
    }
}
