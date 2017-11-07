package com.lyne.common.data_serialize.json.deserializer;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdScalarDeserializer;
import org.apache.commons.lang3.StringUtils;
import org.joda.time.LocalDateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.io.IOException;

/**
 * @author nn_liu
 * @Created 2017-11-02-10:14
 */

public class CustomLocalDateTimeDeserializer extends StdScalarDeserializer<LocalDateTime>{

    private final static DateTimeFormatter DATETIME_FORMAT = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss");

    protected CustomLocalDateTimeDeserializer() {
        super(LocalDateTime.class);
    }

    protected CustomLocalDateTimeDeserializer(Class<?> vc) {
        super(vc);
    }

    @Override
    public LocalDateTime deserialize(JsonParser jp, DeserializationContext ctxt)
            throws IOException, JsonProcessingException {

        String dateStr = null;
        String timeStr = null;
        String fieldName = null;

        while (jp.hasCurrentToken()) {
            JsonToken token = jp.nextToken();
            if (token == JsonToken.FIELD_NAME) {
                fieldName = jp.getCurrentName();
            } else if (token == JsonToken.VALUE_STRING) {
                if (StringUtils.equals(fieldName, "date")) {
                    dateStr = jp.getValueAsString();
                } else if (StringUtils.equals(fieldName, "time")) {
                    timeStr = jp.getValueAsString();
                } else {
                    throw new JsonParseException("Unexpected field name", jp.getTokenLocation());
                }
            } else if (token == JsonToken.END_OBJECT) {
                break;
            }
        }
        if (dateStr != null && timeStr != null) {
            LocalDateTime dateTime = LocalDateTime.parse(dateStr + " " + timeStr, DATETIME_FORMAT);
            return dateTime;
        }
        return null;
    }
}
