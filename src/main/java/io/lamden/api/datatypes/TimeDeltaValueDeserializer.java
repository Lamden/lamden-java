package io.lamden.api.datatypes;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;

import java.io.IOException;
import java.util.Iterator;

class TimeDeltaValueDeserializer extends JsonDeserializer<TimeDeltaValue> {

    @Override
    public TimeDeltaValue deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JsonProcessingException {
        JsonNode node = jsonParser.getCodec().readTree(jsonParser);
        Iterator<JsonNode> valueNode = node.withArray("value").elements();

        int days = valueNode.next().asInt();
        int seconds = valueNode.next().asInt();
        int microseconds = valueNode.next().asInt();
        int milliseconds = valueNode.next().asInt();
        int minutes = valueNode.next().asInt();
        int hours = valueNode.next().asInt();
        int weeks = valueNode.next().asInt();

        return new TimeDeltaValue(weeks, days, hours, minutes, seconds, microseconds, milliseconds);
    }
}
