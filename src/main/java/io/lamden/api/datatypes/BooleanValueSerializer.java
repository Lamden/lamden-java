package io.lamden.api.datatypes;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;

class BooleanValueSerializer extends JsonSerializer<BooleanValue> {


    @Override
    public void serialize(BooleanValue booleanValue, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        jsonGenerator.writeBoolean(booleanValue.getValue());
    }
}
