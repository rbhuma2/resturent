package com.core.mongo.converter;

import java.sql.Timestamp;
import java.util.Date;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.core.convert.converter.Converter;

import com.core.exception.application.InvalidDataException;

public class CustomTimestampReaderConvertor implements Converter<Date, Timestamp> {

    private static final Logger logger = LogManager.getLogger("CustomTimestampReaderConvertor.class");

    @Override
    public Timestamp convert(Date source) {

        Timestamp timestamp = null;
        try {
            timestamp = new Timestamp(source.getTime());
        } catch (Exception ex) {
            logger.error("Invalid Data for the field");
            throw new InvalidDataException("Mongo DB record has invalid data. " + ex.getMessage());
        }
        return timestamp;
    }

}