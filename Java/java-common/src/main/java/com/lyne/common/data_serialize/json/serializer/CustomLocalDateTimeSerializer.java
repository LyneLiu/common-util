package com.lyne.common.data_serialize.json.serializer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdScalarSerializer;
import org.joda.time.LocalDateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.io.IOException;

/**
 * 自定义序列化LocalDateTime
 * @author nn_liu
 * @Created 2017-11-02-10:14
 */
public class CustomLocalDateTimeSerializer extends StdScalarSerializer<LocalDateTime> {

    private final static DateTimeFormatter DATE_FORMAT = DateTimeFormat.forPattern("yyyy-MM-dd");
    private final static DateTimeFormatter TIME_FORMAT = DateTimeFormat.forPattern("HH:mm:ss");

    protected CustomLocalDateTimeSerializer() {
        super(LocalDateTime.class);
    }

    protected CustomLocalDateTimeSerializer(Class<LocalDateTime> t) {
        super(t);
    }

    @Override
    public void serialize(LocalDateTime value, JsonGenerator gen, SerializerProvider provider)
            throws IOException {
        gen.writeStartObject();
        gen.writeStringField("date", DATE_FORMAT.print(value));
        gen.writeStringField("time", TIME_FORMAT.print(value));
        gen.writeEndObject();
    }
}
