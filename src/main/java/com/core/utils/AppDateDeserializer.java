package com.core.utils;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

@Component
public class AppDateDeserializer extends JsonDeserializer<ZonedDateTime> {

    @Value("${app.date.pattern:MM/dd/yyyy HH:mm:ss.SSSSSS}")
    private String pattern;

    @Value("${app.localTimeZone:UTC}")
    private String timeZone;

    @Override
    public ZonedDateTime deserialize(JsonParser paramJsonParser, DeserializationContext paramDeserializationContext)
            throws IOException {
        String dateString = paramJsonParser.getText().trim();
        if (pattern == null) {
            pattern = "yyyy-MM-dd HH:mm:ss.SSSSSS";
        }
        if (timeZone == null) {
            timeZone = "UTC";
        }
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
        LocalDateTime localDateTime = LocalDateTime.parse(dateString, formatter);
        return localDateTime.atZone(ZoneId.of(timeZone));

    }
}