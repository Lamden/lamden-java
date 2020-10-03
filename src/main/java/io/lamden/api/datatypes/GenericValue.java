package io.lamden.api.datatypes;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@JsonSerialize(using = GenericValueSerializer.class)
@JsonDeserialize(using = GenericValueDeserializer.class)
public class GenericValue<T> {

    private T value;

    public GenericValue(T value) {
        this.value = value;
    }

    public T getValue() {
        return value;
    }

}
