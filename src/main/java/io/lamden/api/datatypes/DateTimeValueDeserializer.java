package io.lamden.api.datatypes;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;

import java.io.IOException;
import java.util.Iterator;

class DateTimeValueDeserializer extends JsonDeserializer<DateTimeValue> {

    @Override
    public DateTimeValue deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JsonProcessingException {
        JsonNode node = jsonParser.getCodec().readTree(jsonParser);
        Iterator<JsonNode> valueNode = node.withArray("value").elements();

        int year = valueNode.next().asInt();
        int month = valueNode.next().asInt();
        int day = valueNode.next().asInt();
        int hour = valueNode.next().asInt();
        int minute = valueNode.next().asInt();
        int second = valueNode.next().asInt();

        return new DateTimeValue(year, month, day, hour, minute, second);
    }
}
