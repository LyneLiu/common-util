package com.lyne.common.data_serialize.json;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdScalarSerializer;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import org.joda.time.LocalDateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author nn_liu
 * @Created 2017-09-07-13:39
 */

public class JsonUtil {

    private static Logger logger = LoggerFactory.getLogger(JsonUtil.class);

    public static <T> T deserialize(String value, Class<T> clazz) {
        T obj = null;
        try {
            ObjectMapper mapper = new ObjectMapper().setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
            obj = mapper.readValue(value, clazz);
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
        }
        return obj;
    }

    public static <T> String serialize(T t) {
        String jsonStr = null;
        try {
            ObjectMapper mapper = new ObjectMapper().setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
            mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
            jsonStr = mapper.writeValueAsString(t);
        } catch (JsonProcessingException e) {
            logger.error(e.getMessage(), e);
        }
        return jsonStr;
    }

    public static void main(String[] args) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(LocalDateTime.now());
        System.out.println(json);
    }

}


