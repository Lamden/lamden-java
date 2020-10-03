package io.lamden.api.datatypes;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@JsonDeserialize(using = StringValueDeserializer.class)
@JsonSerialize(using = StringValueSerializer.class)
public class StringValue extends GenericValue<String> {

    public StringValue(String value) {
        super(value);
    }
}
