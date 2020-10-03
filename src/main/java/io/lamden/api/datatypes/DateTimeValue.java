package io.lamden.api.datatypes;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.time.LocalDateTime;


@JsonSerialize(using=DateTimeValueSerializer.class)
@JsonDeserialize(using=DateTimeValueDeserializer.class)
public class DateTimeValue extends GenericValue<LocalDateTime> {

    public DateTimeValue(int year, int month, int dayOfMonth, int hour, int minute, int second) {
        super(LocalDateTime.of(year, month, dayOfMonth, hour, minute, second));
    }

}
