package io.lamden.api.datatypes;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Data;

import java.math.BigDecimal;

@Data
@JsonDeserialize(using=FloatValueDeserializer.class)
public class FloatValue {

    @JsonProperty("__fixed__")
    private String stringValue;

    private BigDecimal value;

    public FloatValue(BigDecimal value) {
        this.value = value;
        this.stringValue = this.value.toPlainString();
    }
}
