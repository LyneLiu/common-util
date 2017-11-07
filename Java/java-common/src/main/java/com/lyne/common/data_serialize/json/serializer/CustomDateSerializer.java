package com.lyne.common.data_serialize.json.serializer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdScalarSerializer;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import java.io.IOException;
import java.util.Date;

/**
 * @author nn_liu
 * @Created 2017-11-02-10:12
 */
public class CustomDateSerializer extends StdScalarSerializer<Date> {

    public CustomDateSerializer(Class<Date> t) {
        super(t);
    }

    @Override
    public void serialize(Date value, JsonGenerator gen, SerializerProvider provider)
            throws IOException {

    }
}
