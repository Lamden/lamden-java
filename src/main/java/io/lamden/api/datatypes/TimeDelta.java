package io.lamden.api.datatypes;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TimeDelta {

    private int weeks;
    private int days;
    private int hours;
    private int minutes;
    private int seconds;
    private int milliseconds;
    private int microseconds;

}
