package com.lyne.utils;

import org.apache.commons.lang3.StringUtils;

/**
 * MySQL提供了INET_ATON和INET_NTOA（ipV4）、INET6_ATON和INET6_NTOA（ipV6）
 *
 * IP字段在Mysql中的存储使用整型字段
 *
 * @author nn_liu
 * @Created 2018-03-29-11:15
 */

public class IPLongUtil {


    /**
     * 字符串IP转换为Long值
     * @param ipStr
     * @return
     */
    private static Long iP2Long(String ipStr){

        String[] ip = ipStr.split("\\.");

        return  (Long.valueOf(ip[0]) << 24) +
                (Long.valueOf(ip[1]) << 16) +
                (Long.valueOf(ip[2]) << 8) +
                Long.valueOf(ip[3]);
    }

    /**
     * IP的Long值转换为字符串
     * @param ipLong
     * @return
     */
    private static String long2IP(Long ipLong){

        StringBuilder ipBuilder = new StringBuilder();
        ipBuilder.append(ipLong >>> 24).append(".");
        ipBuilder.append((ipLong >>> 16) & 0xFF).append(".");
        ipBuilder.append((ipLong >>> 8) & 0xFF).append(".");
        ipBuilder.append(ipLong & 0xFF);

        return ipBuilder.toString();
    }

    public static void main(String[] args) {
        System.out.println(iP2Long("192.168.0.1"));
        System.out.println(long2IP(3232235521L));
        System.out.println(iP2Long("10.0.0.1"));
    }

}
