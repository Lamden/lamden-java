package io.lamden.api.datatypes;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.math.BigDecimal;

@JsonDeserialize(using=FloatValueDeserializer.class)
@JsonSerialize(using=FloatValueSerializer.class)
public class FloatValue extends GenericValue<BigDecimal> {

    public FloatValue(BigDecimal value) {
        super(value);
    }
}
