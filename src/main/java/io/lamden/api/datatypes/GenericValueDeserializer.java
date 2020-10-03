package io.lamden.api.datatypes;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

class GenericValueDeserializer extends JsonDeserializer<GenericValue<Object>> {

    private ObjectMapper mapper = new ObjectMapper();

    @Override
    public GenericValue<Object> deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JsonProcessingException {
        JsonNode node = jsonParser.getCodec().readTree(jsonParser);
        JsonNode value = node.get("value");

        return new GenericValue<>(mapper.treeToValue(value, Object.class));

    }
}
