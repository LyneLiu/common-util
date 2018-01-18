package com.lyne.tools;

import com.google.common.collect.Lists;

import java.lang.*;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;

/**
 * 场景：
 * 当记录log时，当String字符串长度超过32K的情况下，数据会被截断。
 * 通过String分片的方式将其分为特定长度的字符串。
 */
public class Splitter {

    // the default constructor uses this value to initialize maxLen
    private static int defaultMaxLen = 32*1024;

    private static List<String> chunk_split(String original, int length, String separator) throws IOException {
        List<String> stringList = Lists.newArrayList();
        ByteArrayInputStream bis = new ByteArrayInputStream(original.getBytes());
        byte[] buffer = new byte[length];
        while (bis.read(buffer) > 0) {
            String result = new String(buffer, StandardCharsets.UTF_8).trim();
            stringList.add(result);
            Arrays.fill(buffer, (byte) 0);
        }
        return stringList;
    }

    public static void main(String[] argv) throws IOException {
        String original = "abcdefghijklmnopqrstuvwxyz";
        System.out.println(chunk_split(original,5,"\n").get(5).length());
    }
}
