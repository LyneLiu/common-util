package com.lyne.mapper.builder;

import org.apache.ibatis.jdbc.SQL;

/**
 * @author nn_liu
 * @Created 2017-07-28-18:30
 */

public class UserSqlBuilder {

    public String getUserByName(String userName){
        return new SQL(){{
            SELECT("*");
            FROM("users");
            if (userName != null){
                WHERE("username like #{value} || '%'");
            }
        }}.toString();
    }

}
