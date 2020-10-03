package io.lamden.api.datatypes;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@JsonSerialize(using = IntValueSerializer.class)
@JsonDeserialize(using = IntValueDeserializer.class)
public class IntValue extends GenericValue<Integer> {

    public IntValue(Integer value) {
        super(value);
    }
}
