package ru.wondering.swtest.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;

import java.util.Locale;

import static org.junit.jupiter.api.Assertions.assertEquals;

class LocalizedDeserializationContextTest {
    @Test
    void testDeserializationOfNumberUS() throws JsonProcessingException {
        String serialized = "\"123,456\"";
        ObjectMapper om = new ObjectMapper(null, null, new LocalizedDeserializationContext(Locale.US));
        Long longData = om.readValue(serialized, Long.class);
        assertEquals(123456L, longData);

        Number number = om.readValue(serialized, Number.class);
        assertEquals(123456L, number.longValue());
    }

}