package io.lamden.api.datatypes;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;

class TimeDeltaValueSerializer extends JsonSerializer<TimeDeltaValue> {

    @Override
    public void serialize(TimeDeltaValue timeDeltaValue, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        TimeDelta timeDelta = timeDeltaValue.getValue();
        int[] timeDeltaArray = new int[]{
                timeDelta.getDays(),
                timeDelta.getSeconds(),
                timeDelta.getMicroseconds(),
                timeDelta.getMilliseconds(),
                timeDelta.getMinutes(),
                timeDelta.getHours(),
                timeDelta.getWeeks()};

        jsonGenerator.writeArray(timeDeltaArray, 0, timeDeltaArray.length);
    }
}
