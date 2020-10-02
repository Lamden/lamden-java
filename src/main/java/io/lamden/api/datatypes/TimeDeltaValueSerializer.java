package io.lamden.api.datatypes;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;

class TimeDeltaValueSerializer extends JsonSerializer<TimeDeltaValue> {
    @Override
    public void serialize(TimeDeltaValue timeDeltaValue, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        jsonGenerator.writeArray(timeDeltaValue.getTimeDeltaArray(), 0, timeDeltaValue.getTimeDeltaArray().length);
    }
}
