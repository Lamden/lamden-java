package io.lamden.api.datatypes;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@JsonSerialize(using=TimeDeltaValueSerializer.class)
@JsonDeserialize(using = TimeDeltaValueDeserializer.class)
public class TimeDeltaValue {

    private int weeks;
    private int days;
    private int hours;
    private int minutes;
    private int seconds;
    private int milliseconds;
    private int microseconds;

    public TimeDeltaValue(int weeks, int days, int hours, int minutes, int seconds, int milliseconds, int microseconds) {
        this.weeks = weeks;
        this.days = days;
        this.hours = hours;
        this.minutes = minutes;
        this.seconds = seconds;
        this.milliseconds = milliseconds;
        this.microseconds = microseconds;
    }

}
