package com.lyne.common.data_serialize.gson;

import com.alibaba.dubbo.common.json.ParseException;
import com.fasterxml.jackson.core.JsonProcessingException;

import java.util.Calendar;
import java.util.Date;

/**
 *
 * @author nn_liu
 * @Created 2017-10-31-19:34
 */

public class GsonSerializeDemo {

    public static void main(String[] args) throws JsonProcessingException, ParseException {

        PubPO demoPO = new PubPO();
        demoPO.setCol("测试数据");
        demoPO.setDateTime(Calendar.getInstance().getTime());

        System.out.println("==============Gson 序列化=================");
        System.out.println(GsonUtil.toJson(demoPO));

        //String jsonData = "{\"col\":\"测试数据\",\"dateTime\":\"/Date(1509724800000)/\"}";
        String jsonData = "{\"col\":\"测试数据\",\"dateTime\":\"1509724800000\"}";

        System.out.println("==============Gson 反序列化=================");
        PubPO gsonPubPO = GsonUtil.fromJson(jsonData, PubPO.class);
        System.out.println(gsonPubPO.getDateTime());  // com.google.gson.JsonSyntaxException: /Date(1509724800000)/

    }
}


class PubPO{

    private String col;

    private Date dateTime;

    public String getCol() {
        return col;
    }

    public void setCol(String col) {
        this.col = col;
    }

    public Date getDateTime() {
        return dateTime;
    }

    public void setDateTime(Date dateTime) {
        this.dateTime = dateTime;
    }
}
