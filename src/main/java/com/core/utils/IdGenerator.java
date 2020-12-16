package com.core.utils;

import org.apache.commons.lang3.RandomStringUtils;

public class IdGenerator {

    public static final int ID_LENGTH = 36;

    private IdGenerator() {
        super();
    }

    public static String generateUniqueId() {
        return RandomStringUtils.randomAlphanumeric(ID_LENGTH);
    }

    public static String generateUniqueIdUpperCase() {
        return generateUniqueId().toUpperCase();
    }

    public static String generateUniqueId(int length) {
        return RandomStringUtils.randomAlphanumeric(length);
    }

    public static String generateUniqueIdUpperCase(int length) {
        return generateUniqueId(length).toUpperCase();
    }

    public static String generateUniqueStringUpperCase(int length) {
        return RandomStringUtils.randomAlphabetic(length).toUpperCase();
    }

    public static String generateUniqueNumber(int length) {
        return RandomStringUtils.randomNumeric(length);
    }
}