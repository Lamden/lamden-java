package io.lamden.api.datatypes;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;

class TimeDeltaValueSerializer extends JsonSerializer<TimeDeltaValue> {



    @Override
    public void serialize(TimeDeltaValue timeDeltaValue, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        int[] timeDeltaArray = new int[]{
                timeDeltaValue.getDays(),
                timeDeltaValue.getSeconds(),
                timeDeltaValue.getMicroseconds(),
                timeDeltaValue.getMilliseconds(),
                timeDeltaValue.getMinutes(),
                timeDeltaValue.getHours(),
                timeDeltaValue.getWeeks()};

        jsonGenerator.writeArray(timeDeltaArray, 0, timeDeltaArray.length);
    }
}
