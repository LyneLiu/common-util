package com.lyne.common.data_serialize.gson;

import com.google.common.reflect.TypeToken;
import com.google.gson.*;
import com.lyne.common.data_serialize.gson.deserializer.CustomDateDeserializer;
import com.lyne.common.data_serialize.gson.serializer.CustomDateSerializer;
import com.lyne.common.data_serialize.gson.typeadapter.CustomDateTypeAdapter;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * https://stackoverflow.com/questions/9127529/how-to-parse-net-datetime-received-as-json-string-into-javas-date-object
 *
 * @author nn_liu
 * @Created 2017-09-07-13:37
 */

public class GsonUtil {

    private static Gson gson;

    static {
        if (gson == null){
            synchronized (GsonUtil.class){
                GsonBuilder gsonb = new GsonBuilder();
                /*通过JsonSerializer/JsonDeserializer自定义序列化或者反序列化*/
                /*CustomDateSerializer se = new CustomDateSerializer();
                CustomDateDeserializer ds = new CustomDateDeserializer();
                gsonb.registerTypeAdapter(Date.class, se);
                gsonb.registerTypeAdapter(Date.class, ds);*/
                CustomDateTypeAdapter da = new CustomDateTypeAdapter();
                gsonb.registerTypeAdapter(Date.class, da);
                gson = gsonb.create();
            }
        }
    }

    /**
     * 转成Json字符串
     */
    public static String toJson(Object object) {
        return gson.toJson(object);
    }

    /**
     * Json转Java对象
     */
    public static <T> T fromJson(String json, Class<T> clz) {

        return gson.fromJson(json, clz);

    }

    /**
     * Json转List集合
     */
    public static <T> List<T> jsonToList(String json, Class<T> clz) {
        Type type = new TypeToken<List<T>>() {
        }.getType();
        return gson.fromJson(json, type);
    }

    /**
     * Json转List集合,遇到解析不了的，就使用这个
     */
    public static <T> List<T> fromJsonList(String json, Class<T> cls) {
        List<T> mList = new ArrayList<T>();
        JsonArray array = new JsonParser().parse(json).getAsJsonArray();
        Gson mGson = new Gson();
        for (final JsonElement elem : array) {
            mList.add(mGson.fromJson(elem, cls));
        }
        return mList;
    }

    /**
     * Json转换成Map的List集合对象
     */
    public static <T> List<Map<String, T>> toListMap(String json, Class<T> clz) {
        Type type = new TypeToken<List<Map<String, T>>>() {
        }.getType();
        return gson.fromJson(json, type);
    }

    /**
     * Json转Map对象
     */
    public static <T> Map<String, T> toMap(String json, Class<T> clz) {
        Type type = new TypeToken<Map<String, T>>() {
        }.getType();
        return gson.fromJson(json, type);
    }

}


