package io.lamden.api.datatypes;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@JsonSerialize(using=TimeDeltaValueSerializer.class)
@JsonDeserialize(using = TimeDeltaValueDeserializer.class)
public class TimeDeltaValue extends GenericValue<TimeDelta> {
    
    public TimeDeltaValue(int weeks, int days, int hours, int minutes, int seconds, int milliseconds, int microseconds) {
        super(new TimeDelta(weeks, days, hours, minutes, seconds, milliseconds, microseconds));
    }

}
