package io.lamden.api.datatypes;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.util.Map;

@JsonDeserialize(using = MapValueDeserializer.class)
@JsonSerialize(using = MapValueSerializer.class)
public class MapValue extends GenericValue<Map<String, Object>> {

    public MapValue(Map<String, Object> value) {
        super(value);
    }

}
