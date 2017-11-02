package com.lyne.common.data_serialize.gson.typeadapter;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author nn_liu
 * @Created 2017-11-02-11:33
 */

public class CustomDateTypeAdapter extends TypeAdapter<Date> {

    /*
     * 注意：
     * beginObject/endObject为key-value对象；
     * beginArray/endArray为数组对象。
     *
     * @param out
     * @param value
     * @throws IOException
     */
    @Override public void write(JsonWriter out, Date value) throws IOException {

        out.jsonValue(new SimpleDateFormat("yyyy-MM-dd").format(value));

    }

    @Override public Date read(JsonReader in) throws IOException {

        String result = "";

        while (in.hasNext()){
            String JSONDateToMilliseconds = "\\/(Date\\((.*?)(\\+.*)?\\))\\/";
            Pattern pattern = Pattern.compile(JSONDateToMilliseconds);
            Matcher matcher = pattern.matcher(in.nextString());
            result = matcher.replaceAll("$2");
        }

        return new Date(new Long(result));
    }
}
