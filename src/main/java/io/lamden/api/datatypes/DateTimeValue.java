package io.lamden.api.datatypes;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Data;

import java.time.*;

@Data
@JsonSerialize(using=DateTimeValueSerializer.class)
public class DateTimeValue {

    private int[] dateTimeArray;

    public DateTimeValue(int year, int month, int dayOfMonth, int hour, int minute, int second) {
        LocalDateTime ldt = LocalDateTime.of(year, month, dayOfMonth, hour, minute, second);
        dateTimeArray = new int[]{
                ldt.getYear(),
                ldt.getMonthValue(),
                ldt.getDayOfMonth(),
                ldt.getHour(),
                ldt.getMinute(),
                ldt.getSecond()};
    }

}
