package io.lamden.api.datatypes;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.util.List;

@JsonDeserialize(using = ListValueDeserializer.class)
@JsonSerialize(using = ListValueSerializer.class)
public class ListValue<T> extends GenericValue<List<T>> {

    public ListValue(List<T> value) {
        super(value);
    }
}
