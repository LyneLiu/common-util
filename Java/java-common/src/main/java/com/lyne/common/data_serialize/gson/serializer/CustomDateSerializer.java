package com.lyne.common.data_serialize.gson.serializer;

import com.google.gson.*;

import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author nn_liu
 * @Created 2017-11-02-10:28
 */

public class CustomDateSerializer implements JsonSerializer<Date> {

    @Override
    public JsonElement serialize(Date src, Type typeOfSrc, JsonSerializationContext context) {
        final JsonPrimitive jsonPrimitive = new JsonPrimitive(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(src));
        return jsonPrimitive;
    }
}
