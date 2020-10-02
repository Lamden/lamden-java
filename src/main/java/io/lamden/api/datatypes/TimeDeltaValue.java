package io.lamden.api.datatypes;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@JsonSerialize(using=TimeDeltaValueSerializer.class)
public class TimeDeltaValue {

    private int[] timeDeltaArray;

    public TimeDeltaValue(int weeks, int days, int hours, int minutes, int seconds, int milliseconds, int microseconds) {
        this.timeDeltaArray = new int[]{days, seconds, microseconds, milliseconds, minutes, hours, weeks};
    }

}
