package io.lamden.api.datatypes;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;


@JsonSerialize(using = BooleanValueSerializer.class)
@JsonDeserialize(using = BooleanValueDeserializer.class)
public class BooleanValue extends GenericValue<Boolean> {


    public BooleanValue(boolean value) {
        super(value);
    }

}
