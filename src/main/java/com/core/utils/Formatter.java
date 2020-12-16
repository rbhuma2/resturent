package com.core.utils;

import static com.core.utils.CommonConstants.BLANK_CHAR;
import static com.core.utils.CommonConstants.SEPARATOR_BLANK;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Formatter {

    public static final String PHONE_REGEX_1 = "\\d{10}";
    public static final String PHONE_REGEX_2 = "\\d{3}[-\\.\\s]\\d{3}[-\\.\\s]\\d{4}";
    public static final String PHONE_REGEX_3 = "\\d{3}-\\d{3}-\\d{4}\\s(x|(ext))\\d{3,5}";
    public static final String PHONE_REGEX_4 = "\\(\\d{3}\\)-\\d{3}-\\d{4}";
    public static final String PHONE_REGEX_5 = "\\(\\d{3}\\)\\d{3}-\\d{4}";
    public static final String DECIMAL_FORMAT = "#.00";

    private Formatter() {
        super();
    }

    public static String setSpacesIfNull(String field) {
        if (field == null) {
            field = SEPARATOR_BLANK;
        }
        return field;
    }

    public static boolean isValidEmailAddress(String email) {
        String ePattern = "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\])|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))$";
        Pattern p = Pattern.compile(ePattern);
        Matcher m = p.matcher(email);
        return m.matches();
    }

    public static Double formatAmount(Double amount) throws ParseException {
        if (amount == null) {
            return null;
        }
        DecimalFormat df = new DecimalFormat(DECIMAL_FORMAT);
        String formate = df.format(amount);

        return df.parse(formate).doubleValue();

    }

    public static String removeAmountPrecision(Double amount) {
        DecimalFormat df = new DecimalFormat(DECIMAL_FORMAT);
        String formate = df.format(amount);
        return String.valueOf(formate).replaceAll("\\W", "");
    }

    public static String formatAmountAsString(Double amount) {
        DecimalFormat df = new DecimalFormat(DECIMAL_FORMAT);
        return df.format(amount);
    }

    public static Double formatAmountRoudingUp(Double amount) throws ParseException {
        DecimalFormat df = new DecimalFormat(DECIMAL_FORMAT);
        String formate = df.format(amount);
        df.setRoundingMode(RoundingMode.UP);
        return df.parse(formate).doubleValue();
    }

    public static String convertFormatTimestamp(String timeString) {
        if (timeString != null && !timeString.trim().isEmpty()) {
            char[] timeArray = timeString.toCharArray();

            timeArray[10] = BLANK_CHAR;
            timeArray[13] = ':';
            timeArray[16] = ':';

            timeString = String.valueOf(timeArray);
        }
        return timeString;
    }

}
