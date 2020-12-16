package com.core.utils;

import java.io.IOException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

@Component
public class CustomDateTimeDeSerializer extends StdDeserializer<ZonedDateTime> {

    private static final long serialVersionUID = 1L;

    public CustomDateTimeDeSerializer() {
        this(null);
    }

    protected CustomDateTimeDeSerializer(Class<ZonedDateTime> t) {
        super(t);
    }

    @Value("${app.localTimeZone:UTC}")
    private String timeZone;

    @Override
    public ZonedDateTime deserialize(JsonParser paramJsonParser, DeserializationContext paramDeserializationContext)
            throws IOException, JsonProcessingException {
        if (timeZone == null) {
            timeZone = "UTC";
        }
        String dateString = paramJsonParser.getText().trim();
        if (dateString.isEmpty()) {
            dateString = "9999-12-31";
        }
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate date = LocalDate.parse(dateString, formatter);
        return date.atStartOfDay(ZoneId.of(timeZone));

    }

}
