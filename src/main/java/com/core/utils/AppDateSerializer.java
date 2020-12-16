package com.core.utils;

import java.io.IOException;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

@Component
public class AppDateSerializer extends JsonSerializer<ZonedDateTime> {

    @Value("${app.date.pattern:MM/dd/yyyy HH:mm:ss.SSSSSS}")
    private String pattern;

    @Value("${app.localTimeZone:UTC}")
    private String timeZone;

    @Override
    public void serialize(ZonedDateTime zonedDateTime, JsonGenerator gen, SerializerProvider provider)
            throws IOException {
        if (pattern == null) {
            pattern = "yyyy-MM-dd HH:mm:ss.SSSSSS";
        }
        if (timeZone == null) {
            timeZone = "UTC";
        }
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern).withZone(ZoneId.of(timeZone));
        gen.writeString(zonedDateTime.format(formatter));
    }
}
