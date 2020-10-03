package io.lamden.api.datatypes;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;

class DateTimeValueSerializer extends JsonSerializer<DateTimeValue> {
    @Override
    public void serialize(DateTimeValue dateTimeValue, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {

        int[] dateTimeArray = new int[]{
                dateTimeValue.getValue().getYear(),
                dateTimeValue.getValue().getMonthValue(),
                dateTimeValue.getValue().getDayOfMonth(),
                dateTimeValue.getValue().getHour(),
                dateTimeValue.getValue().getMinute(),
                dateTimeValue.getValue().getSecond()
        };

        jsonGenerator.writeArray(dateTimeArray, 0, dateTimeArray.length);
    }
}
