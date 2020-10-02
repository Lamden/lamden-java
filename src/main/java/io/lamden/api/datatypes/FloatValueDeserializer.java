package io.lamden.api.datatypes;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.databind.node.DoubleNode;

import java.io.IOException;
import java.math.BigDecimal;

class FloatValueDeserializer extends JsonDeserializer<FloatValue> {

    @Override
    public FloatValue deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
        JsonNode node = jsonParser.getCodec().readTree(jsonParser);
        BigDecimal value = new BigDecimal(node.get("value").get("__fixed__").textValue());
        return new FloatValue(value);
    }

}
