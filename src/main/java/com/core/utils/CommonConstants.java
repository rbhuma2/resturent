package com.core.utils;

import java.math.BigDecimal;

public class CommonConstants {
    public static final String SEPARATOR_BLANK = " ";
    public static final int GENERATED_ID_LENGTH_4 = 4;
    public static final int GENERATED_ID_LENGTH_8 = 8;
    public static final int GENERATED_ID_LENGTH_20 = 20;
    public static final int GENERATED_ID_LENGTH_32 = 32;
    public static final int GENERATED_ID_LENGTH_10 = 10;
    public static final int GENERATED_ID_LENGTH_17 = 17;
    public static final int DEFAULT_COMMIT_INTERVAL = 1000;
    public static final int NUM_PARAM_ALLOWED = 2000;
    public static final int MIN_DEFER_SECONDS = 120;
    public static final String BLANK_STRING = "";
    public static final String CASH_SUSPENSE_REFERENCE_ID = "BCMBSUS";
    public static final short SHORT_ZERO = 0;
    public static final short SHORT_ONE = 1;
    public static final short PRIORITY_LEVEL_100 = 100;
    public static final double DECIMAL_ZERO = 0.00;
    public static final char BLANK_CHAR = ' ';
    public static final int INVALID_INT_VAL = Integer.MIN_VALUE;
    public static final short INVALID_SHORT_VAL = Short.MIN_VALUE;
    public static final double INVALID_DOUBLE_VAL = Double.MIN_VALUE;
    public static final char CHAR_HYPEN = '-';
    public static final String COMMA_STRING = ", ";
    public static final String SPACE_REGEX = "\\s";
    public static final String FLASH_STRING = "/";
    public static final String GENERIC_LOCALE_ERROR = "An internal system error occurred , could not find the error code specific to locale, please contact System administrator.";
    public static final String HOST_DB = "HOST_DB";
    public static final String ZEROS_10 = "0000000000";
    public static final String ZEROS_05 = "00000";
    public static final String ZEROS_04 = "0000";
    public static final String SYSTEM_USER_ID = "SYSTEM";
    public static final String SYSTEMIW_USER_ID = "SYSTEMIW";
    public static final char CHAR_A = 'A';
    public static final char CHAR_B = 'B';
    public static final char CHAR_C = 'C';
    public static final char CHAR_D = 'D';
    public static final char CHAR_E = 'E';
    public static final char CHAR_F = 'F';
    public static final char CHAR_G = 'G';
    public static final char CHAR_H = 'H';
    public static final char CHAR_I = 'I';
    public static final char CHAR_J = 'J';
    public static final char CHAR_K = 'K';
    public static final char CHAR_L = 'L';
    public static final char CHAR_M = 'M';
    public static final char CHAR_N = 'N';
    public static final char CHAR_O = 'O';
    public static final char CHAR_P = 'P';
    public static final char CHAR_Q = 'Q';
    public static final char CHAR_R = 'R';
    public static final char CHAR_S = 'S';
    public static final char CHAR_T = 'T';
    public static final char CHAR_U = 'U';
    public static final char CHAR_V = 'V';
    public static final char CHAR_W = 'W';
    public static final char CHAR_X = 'X';
    public static final char CHAR_Y = 'Y';
    public static final char CHAR_Z = 'Z';
    public static final char CHAR_0 = '0';
    public static final char CHAR_1 = '1';
    public static final char CHAR_2 = '2';
    public static final char CHAR_3 = '3';
    public static final char CHAR_4 = '4';
    public static final char CHAR_9 = '9';
    public static final char CHAR_5 = '5';
    public static final char CHAR_6 = '6';
    public static final char CHAR_7 = '7';
    public static final char CHAR_8 = '8';
    public static final String NEWLINE_STRING = "\r\n";
    public static final String ZEROS_02 = "00";
    public static final String DATE_SPACES = "        ";
    public static final String DATE_HIGH = "9999-12-31";
    public static final String DATE_LOW = "1900-01-01";
    public static final char CHAR_ZERO = '0';
    public static final char CHAR_COLON = ':';
    public static final char CHAR_COMMA = ',';
    public static final BigDecimal BIGDECIMAL_ZERO = BigDecimal.ZERO;
    public static final String ISSUE_NODE = "BATCHAPI";
    public static final char ISSUE_PLATFORM = CHAR_B;
    public static final String APP_VERSION_ID = BLANK_STRING;
    public static final String IAP_VERSION_ID = "V80";
    public static final String USER_ID = BLANK_STRING;
    public static final String PRIOR_ACY_ID = BLANK_STRING;
    public static final BigDecimal BIGDECIMAL_HUNDRED = BigDecimal.valueOf(100.00);
    public static final String CASH_JOURNAL_REFERENCE_ID = "BCMBCHJ";
    public static final String CASH_JOURNAL_SUMMARY_REFERENCE_ID = "BCMBCJS";
    public static final String STRING_ZERO = "0";
    public static final String HOLIDAY_8 = "H8";
    public static final String HOLIDAY_6 = "H6";
    public static final String HOLIDAY_5 = "H5";
    public static final String HOLIDAY_3 = "H3";
    public static final String HOLIDAY_1 = "H1";
    
    public enum Indicator {
        Y("Yes"), N("No");

        private final String type;

        private Indicator(String type) {
            this.type = type;
        }

        public boolean hasValue(String value) {
            return value == null ? Boolean.FALSE : type.equals(value);
        }

        @Override
        public String toString() {
            return this.type;
        }

    }

    public enum BusCodeTranslationType {
        APP("APP"), ACTION("ATN"), OBJECT("OBJ");
        private final String type;

        private BusCodeTranslationType(String type) {
            this.type = type;
        }

        public String getValue() {
            return type;
        }
    }

    public enum BusCodeTranslationParentCode {
        ACTIONCD("ATNCD"), APPCD("APPCD"), OBJECTCD("OBJCD");
        private final String type;

        private BusCodeTranslationParentCode(String type) {
            this.type = type;
        }

        public String getValue() {
            return type;
        }
    }

}
