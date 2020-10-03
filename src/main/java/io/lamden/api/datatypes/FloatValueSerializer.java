package io.lamden.api.datatypes;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.*;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Collections;

class FloatValueSerializer extends JsonSerializer<FloatValue> {

    @Override
    public void serialize(FloatValue floatValue, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        jsonGenerator.writeObject(Collections.singletonMap("__fixed__", floatValue.getValue().toPlainString()));
    }
}
