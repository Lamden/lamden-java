package io.lamden.api.datatypes;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.*;

import java.io.IOException;

class GenericValueSerializer extends JsonSerializer<GenericValue<Object>> {

    @Override
    public void serialize(GenericValue<Object> objectGenericValue, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        jsonGenerator.writeObject(objectGenericValue.getValue());
    }
}
