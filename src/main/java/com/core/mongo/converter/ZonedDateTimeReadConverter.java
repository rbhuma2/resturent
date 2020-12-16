package com.core.mongo.converter;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Date;

import org.springframework.core.convert.converter.Converter;

public class ZonedDateTimeReadConverter implements Converter<Date, ZonedDateTime> {

    private String timeZone;

    public ZonedDateTimeReadConverter(String timeZone) {
        super();
        this.timeZone = timeZone;
    }

    @Override
    public ZonedDateTime convert(Date date) {
        if (timeZone == null) {
            timeZone = "UTC";
        }
        return date.toInstant().atZone(ZoneId.of(timeZone));
    }

    public String getTimeZone() {
        return timeZone;
    }

    public void setTimeZone(String timeZone) {
        this.timeZone = timeZone;
    }

}