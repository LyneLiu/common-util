package com.lyne.common.data_serialize.json;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.lyne.common.data_serialize.json.deserializer.CustomLocalDateTimeDeserializer;
import com.lyne.common.data_serialize.json.serializer.CustomLocalDateTimeSerializer;
import org.joda.time.LocalDateTime;

/**
 * @author nn_liu
 * @Created 2017-11-02-10:56
 */

public class JsonSerializeDemo {

    public static void main(String[] args) {

        ExampleDomain exampleDomain = new ExampleDomain();

        System.out.println("==============Json 序列化=================");
        System.out.println(JsonUtil.serialize(exampleDomain));

        System.out.println("==============Json 反序列化=================");
        String jsonData = "{\"custom\":{\"date\":\"2017-11-02\",\"time\":\"14:11:15\"}}";
        System.out.println(JsonUtil.deserialize(jsonData, ExampleDomain.class));
    }

}

class ExampleDomain {

    //private LocalDateTime asDefault = LocalDateTime.now();

    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
    private LocalDateTime custom = LocalDateTime.now();

   /* public LocalDateTime getAsDefault() {
        return asDefault;
    }

    public void setAsDefault(LocalDateTime asDefault) {
        this.asDefault = asDefault;
    }*/

    public LocalDateTime getCustom() {
        return custom;
    }

    public void setCustom(LocalDateTime custom) {
        this.custom = custom;
    }
}
