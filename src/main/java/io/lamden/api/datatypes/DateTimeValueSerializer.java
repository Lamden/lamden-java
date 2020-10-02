package io.lamden.api.datatypes;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;

class DateTimeValueSerializer extends JsonSerializer<DateTimeValue> {
    @Override
    public void serialize(DateTimeValue dateTimeValue, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        jsonGenerator.writeArray(dateTimeValue.getDateTimeArray(), 0, dateTimeValue.getDateTimeArray().length);
    }
}
