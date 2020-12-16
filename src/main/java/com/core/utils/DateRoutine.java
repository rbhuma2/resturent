package com.core.utils;

import static com.core.utils.CommonConstants.CHAR_HYPEN;
import static com.core.utils.CommonConstants.SHORT_ZERO;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAdjusters;
import java.util.Date;
import java.util.TimeZone;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class DateRoutine {

	private static String timeZone;
	private static String defaultSinceDate;
	private static final char PLUS_SIGN = '+';
	private static final char MINUS_SIGN = '-';
	private static final String ANNUAL = "AN";
	private static final String MONTHLY = "MO";
	private static final String BI_MONTHLY = "BM";
	private static final String QUARTERLY = "QT";
	private static final String SEMI_ANNUAL = "SA";
	private static final String WEEKLY = "WK";
	private static final String BI_WEEKLY = "BW";
	private static final String SEMI_MONTHLY = "SM";
	private static final char NON_WKN_BEFORE = '1';
	private static final char NON_WKN_AFTER = '2';
	private static final char NON_WKN_BEFORE_SAT = '3';
	private static final char NON_WKN_AFTER_SUN = '4';
	private static final char NON_WKN_SAT_SUN_SPLIT = '5';
	private static final char NON_HOL_BEFORE = '6';
	private static final char NON_HOL_AFTER = '7';
	private static final char NON_HOL_WKN_BEFORE = '8';
	private static final char NON_HOL_WKN_AFTER = '9';

	private static final String DEFUALT_DATE_FORMAT = "yyyy-MM-dd";
	private static final Logger LOGGER = LogManager.getLogger(DateRoutine.class);

	@Value("${app.localTimeZone}")
	public void setTimeZone(String zoneName) {
		timeZone = zoneName;
	}

	@Value("${app.sinceDateTime}")
	public void setDefaultDate(String defaultSinceDateTime) {
		defaultSinceDate = defaultSinceDateTime;
	}

	private DateRoutine() {
		super();
	}

	public static String currentDateAsStr() {
		ZonedDateTime now = ZonedDateTime.now();
		String pattern = DEFUALT_DATE_FORMAT;
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern).withZone(ZoneId.of(timeZone));
		return now.format(formatter);
	}

	public static String currentDateTimeAsStr() {
		ZonedDateTime now = ZonedDateTime.now();
		String pattern = "yyyy-MM-dd HH:mm:ss";
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern).withZone(ZoneId.of(timeZone));
		return now.format(formatter);
	}

	public static ZonedDateTime currentTimestamp() {
		return ZonedDateTime.now();
	}

	public static ZonedDateTime systemDate() {
		LocalDate date = LocalDate.parse(currentDateAsStr());
		return date.atStartOfDay(ZoneId.of(timeZone));
	}

	public static String dateTimeAsYYYYMMDDString(ZonedDateTime datetime) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DEFUALT_DATE_FORMAT).withZone(ZoneId.of(timeZone));
		return datetime.format(formatter);
	}

	public static ZonedDateTime dateTimeAsYYYYMMDD(String dateString) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DEFUALT_DATE_FORMAT);
		LocalDate date = LocalDate.parse(dateString, formatter);
		return date.atStartOfDay(ZoneId.of(timeZone));
	}

	public static ZonedDateTime defaultStartDateTime() {
		String dateString = "1900-01-01";
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DEFUALT_DATE_FORMAT);
		LocalDate date = LocalDate.parse(dateString, formatter);
		return date.atStartOfDay(ZoneId.of(timeZone));
	}

	public static ZonedDateTime defaultStartDateTimestamp() {
		return ZonedDateTime.of(1900, 01, 01, 00, 00, 00, 000001, ZoneId.of(timeZone));
	}

	public static ZonedDateTime defaultDateTime() {
		String dateString = "9999-12-31";
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DEFUALT_DATE_FORMAT);
		LocalDate date = LocalDate.parse(dateString, formatter);
		return date.atStartOfDay(ZoneId.of(timeZone));
	}

	public static ZonedDateTime defaultDateTimestamp() {
		return ZonedDateTime.of(9999, 12, 31, 00, 00, 00, 000001, ZoneId.of(timeZone));
	}

	public static ZonedDateTime dateTimeAsYYYYMM(String dateString) {
		LocalDate localDate = LocalDate.parse(dateString);
		ZonedDateTime dateTime = localDate.atStartOfDay(ZoneId.of(timeZone));
		return ZonedDateTime.of(dateTime.getYear(), dateTime.getMonthValue(), 1, 0, 0, 0, 0, ZoneId.of(timeZone));
	}

	public static ZonedDateTime se3DateTimeAsYYYYMM(ZonedDateTime dateTime) {
		return ZonedDateTime.of(dateTime.getYear(), dateTime.getMonthValue(), 1, 0, 0, 0, 0, ZoneId.of(timeZone));
	}

	public static ZonedDateTime getAdjustedDate(ZonedDateTime dateTime, boolean excludeWeekend, int daysToAdd,
			ZonedDateTime processDate) {
		if (daysToAdd == 0 && excludeWeekend) {
			if (processDate == null || dateTime.compareTo(processDate) != 0) {
				if (dateTime.getDayOfWeek() == DayOfWeek.SATURDAY) {
					return dateTime.minusDays(1);
				}

				if (dateTime.getDayOfWeek() == DayOfWeek.SUNDAY) {
					return dateTime.plusDays(1);
				}
			}

		}
		if (daysToAdd != 0 && !excludeWeekend) {
			return dateTime.plusDays(daysToAdd);
		}
		if (daysToAdd != 0 && excludeWeekend) {
			ZonedDateTime newDateTime = dateTime.plusDays(daysToAdd);
			if (processDate == null || newDateTime.compareTo(processDate) != 0) {
				if (newDateTime.getDayOfWeek() == DayOfWeek.SATURDAY) {
					return newDateTime.minusDays(1);
				}
				if (newDateTime.getDayOfWeek() == DayOfWeek.SUNDAY) {
					return newDateTime.plusDays(1);
				}
			}

			return newDateTime;
		}

		return dateTime;
	}

	public static ZonedDateTime getAdjustedDateByWeek(ZonedDateTime dateTime, boolean excludeWeekend, int weeksToAdd) {

		if (weeksToAdd == 0 && excludeWeekend) {
			if (dateTime.getDayOfWeek() == DayOfWeek.SATURDAY) {
				return dateTime.minusDays(1);
			} else if (dateTime.getDayOfWeek() == DayOfWeek.SUNDAY) {
				return dateTime.plusDays(1);
			}
		}

		if (weeksToAdd != 0) {
			if (!excludeWeekend) {
				return dateTime.plusWeeks(weeksToAdd);
			} else {
				ZonedDateTime newDateTime = dateTime.plusWeeks(weeksToAdd);
				if (newDateTime.getDayOfWeek() == DayOfWeek.SATURDAY) {
					return newDateTime.minusDays(1);
				}
				if (newDateTime.getDayOfWeek() == DayOfWeek.SUNDAY) {
					return newDateTime.plusDays(1);
				}
				return newDateTime;
			}
		}

		return dateTime;
	}

	public static ZonedDateTime getAdjustedDateByMonth(ZonedDateTime dateTime, boolean excludeWeekend,
			int monthsToAdd) {

		if (monthsToAdd == 0 && excludeWeekend) {
			if (dateTime.getDayOfWeek() == DayOfWeek.SATURDAY) {
				return dateTime.minusDays(1);
			} else if (dateTime.getDayOfWeek() == DayOfWeek.SUNDAY) {
				return dateTime.plusDays(1);
			}
		}

		if (monthsToAdd != 0) {
			if (!excludeWeekend) {
				return dateTime.plusMonths(monthsToAdd);
			} else {
				ZonedDateTime newDateTime = dateTime.plusMonths(monthsToAdd);
				if (newDateTime.getDayOfWeek() == DayOfWeek.SATURDAY) {
					return newDateTime.minusDays(1);
				}
				if (newDateTime.getDayOfWeek() == DayOfWeek.SUNDAY) {
					return newDateTime.plusDays(1);
				}
				return newDateTime;
			}
		}

		return dateTime;
	}

	public static ZonedDateTime getAdjustedDateByYear(ZonedDateTime dateTime, boolean excludeWeekend, int yearsToAdd) {

		if (yearsToAdd == 0 && excludeWeekend) {
			if (dateTime.getDayOfWeek() == DayOfWeek.SATURDAY) {
				return dateTime.minusDays(1);
			} else if (dateTime.getDayOfWeek() == DayOfWeek.SUNDAY) {
				return dateTime.plusDays(1);
			}
		}

		if (yearsToAdd != 0) {
			if (!excludeWeekend) {
				return dateTime.plusYears(yearsToAdd);
			} else {
				ZonedDateTime newDateTime = dateTime.plusYears(yearsToAdd);
				if (newDateTime.getDayOfWeek() == DayOfWeek.SATURDAY) {
					return newDateTime.minusDays(1);
				}
				if (newDateTime.getDayOfWeek() == DayOfWeek.SUNDAY) {
					return newDateTime.plusDays(1);
				}
				return newDateTime;
			}
		}

		return dateTime;
	}

	public static ZonedDateTime toDateTimeSince(String date) {
		ZonedDateTime dateTime = null;
		final String SINCE_TIME = " 00:00:00";
		if (date == null) {
			dateTime = DateRoutine.defaultSinceDateTime();
		} else {
			date = date.concat(SINCE_TIME);
			dateTime = dateTimeAsYYYYMMDDHHhhmmss(date);
		}
		return dateTime;
	}

	public static ZonedDateTime toDateTimeUntil(String date) {
		ZonedDateTime dateTime = null;
		final String UNTIL_TIME = " 23:59:59";
		if (date == null) {
			dateTime = DateRoutine.defaultDateTime();
		} else {
			date = date.concat(UNTIL_TIME);
			dateTime = dateTimeAsYYYYMMDDHHhhmmss(date);
		}
		return dateTime;
	}

	public static ZonedDateTime defaultSinceDateTime() {
		String dateString = defaultSinceDate;
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DEFUALT_DATE_FORMAT);
		LocalDate localDate = LocalDate.parse(dateString, formatter);
		return localDate.atStartOfDay(ZoneId.of(timeZone));
	}

	public static String dateTimeAsYYYYMMDDHHmmssSSSString(ZonedDateTime datetime) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd-HH.mm.ss.SSSSSS")
				.withZone(ZoneId.of(timeZone));
		return datetime.format(formatter);
	}

	public static ZonedDateTime dateTimeAsYYYYMMDDHHmmssSSS(String dateString) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd-HH.mm.ss.SSSSSS");
		LocalDateTime localDateTime = LocalDateTime.parse(dateString, formatter);
		return localDateTime.atZone(ZoneId.of(timeZone));
	}

	public static ZonedDateTime dateTimeAsYYYYMMDDHHhhmmss(String dateString) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		LocalDateTime localDateTime = LocalDateTime.parse(dateString, formatter);
		return localDateTime.atZone(ZoneId.of(timeZone));
	}

	public static ZonedDateTime systemDateTime() {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSSSSS");
		String dateString = formatter.format(LocalDateTime.now());
		LocalDateTime localDateTime = LocalDateTime.parse(dateString, formatter);
		return localDateTime.atZone(ZoneId.of(timeZone));
	}

	public static String dateTimeAsMMDDYYYYAsString(ZonedDateTime datetime) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy").withZone(ZoneId.of(timeZone));
		return datetime.format(formatter);
	}

	public static ZonedDateTime adjustDateWithOffset(ZonedDateTime dateTime, boolean excludeWeekend, int offsetDays,
			Character offset, ZonedDateTime processDate) {
		switch (offset) {
		case PLUS_SIGN:
			if (offsetDays == 0 && excludeWeekend) {
				if (processDate == null || dateTime.compareTo(processDate) != 0) {
					if (dateTime.getDayOfWeek() == DayOfWeek.SATURDAY) {
						return dateTime.minusDays(1);
					} else if (dateTime.getDayOfWeek() == DayOfWeek.SUNDAY) {
						return dateTime.plusDays(1);
					}
				}

			} else if (offsetDays != 0 && !excludeWeekend) {
				return dateTime.plusDays(offsetDays);
			} else if (offsetDays != 0 && excludeWeekend) {
				ZonedDateTime newDateTime = dateTime.plusDays(offsetDays);
				if (processDate == null || newDateTime.compareTo(processDate) != 0) {
					if (newDateTime.getDayOfWeek() == DayOfWeek.SATURDAY) {
						return newDateTime.minusDays(1);
					}
					if (newDateTime.getDayOfWeek() == DayOfWeek.SUNDAY) {
						return newDateTime.plusDays(1);
					}
				}
				return newDateTime;
			}
			return dateTime;
		case MINUS_SIGN:
			if (offsetDays == 0 && excludeWeekend) {

				if (processDate == null || dateTime.compareTo(processDate) != 0) {
					if (dateTime.getDayOfWeek() == DayOfWeek.SATURDAY) {
						return dateTime.minusDays(1);
					}
					if (dateTime.getDayOfWeek() == DayOfWeek.SUNDAY) {
						return dateTime.plusDays(1);
					}
				}

			}
			if (offsetDays != 0 && !excludeWeekend) {
				return dateTime.minusDays(offsetDays);
			}
			if (offsetDays != 0 && excludeWeekend) {
				ZonedDateTime newDateTime = dateTime.minusDays(offsetDays);
				if (processDate == null || newDateTime.compareTo(processDate) != 0) {
					if (newDateTime.getDayOfWeek() == DayOfWeek.SATURDAY) {
						return newDateTime.minusDays(1);
					}
					if (newDateTime.getDayOfWeek() == DayOfWeek.SUNDAY) {
						return newDateTime.plusDays(1);
					}
				}
				return newDateTime;
			}
			return dateTime;
		}
		return dateTime;
	}

	public static ZonedDateTime calculateDateTimeByFrequency(String frequency, ZonedDateTime dateTime) {
		int months = 0;
		int days = 0;
		switch (frequency) {
		case ANNUAL:
			months = 12;
			break;
		case MONTHLY:
			months = 1;
			break;
		case BI_MONTHLY:
			months = 2;
			break;
		case QUARTERLY:
			months = 3;
			break;
		case SEMI_ANNUAL:
			months = 6;
			break;
		case WEEKLY:
			days = 7;
			break;
		case BI_WEEKLY:
			days = 14;
			break;
		case SEMI_MONTHLY:
			Month month = dateTime.getMonth();
			if (dateTime.getDayOfMonth() == 1) {
				days = 14;
			} else if (dateTime.getDayOfMonth() == 29 && month.equals(Month.FEBRUARY)) {
				dateTime = dateTime.with(TemporalAdjusters.firstDayOfMonth()).toLocalDate()
						.atStartOfDay(ZoneId.of(timeZone)).plusDays(15);
				if (dateTime.getMonthValue() < 12) {
					months = 1;
				} else {
					dateTime = dateTime.withMonth(Month.JANUARY.getValue()).toLocalDate()
							.atStartOfDay(ZoneId.of(timeZone));
					dateTime = dateTime.plusYears(1);
				}
			} else if (dateTime.getDayOfMonth() < 15) {
				dateTime = dateTime.plusMonths(months).plusDays(15);
				if (month.equals(Month.FEBRUARY) && dateTime.getDayOfMonth() == 29) {
					days = dateTime.getDayOfMonth() - 1;
				}
			} else if (dateTime.getDayOfMonth() == 15) {
				dateTime = dateTime.with(TemporalAdjusters.firstDayOfMonth()).toLocalDate()
						.atStartOfDay(ZoneId.of(timeZone));
				if (dateTime.getMonthValue() < 12) {
					months = 1;
				} else {
					dateTime = dateTime.withMonth(Month.JANUARY.getValue()).toLocalDate()
							.atStartOfDay(ZoneId.of(timeZone));
					dateTime = dateTime.plusYears(1);
				}
			} else if (dateTime.getDayOfMonth() == 16) {
				dateTime = dateTime.with(TemporalAdjusters.firstDayOfMonth()).toLocalDate()
						.atStartOfDay(ZoneId.of(timeZone)).plusDays(1);
				if (dateTime.getMonthValue() < 12) {
					months = 1;
				} else {
					dateTime = dateTime.withMonth(Month.JANUARY.getValue()).toLocalDate()
							.atStartOfDay(ZoneId.of(timeZone));
					dateTime = dateTime.plusYears(1);
				}
			} else if (dateTime.getDayOfMonth() > 16) {
				dateTime = dateTime.with(TemporalAdjusters.firstDayOfMonth()).toLocalDate()
						.atStartOfDay(ZoneId.of(timeZone)).plusDays(dateTime.getDayOfMonth() - 16);
				if (dateTime.getMonthValue() < 12) {
					months = 1;
				} else {
					dateTime = dateTime.withMonth(Month.JANUARY.getValue()).toLocalDate()
							.atStartOfDay(ZoneId.of(timeZone));
					dateTime = dateTime.plusYears(1);
				}
			}
			break;
		default:
			break;

		}
		return dateTime.plusMonths(months).plusDays(days);
	}

	public static ZonedDateTime dateTimeWithHolidayProcess(ZonedDateTime dateTime, char holidayValue,
			boolean isHoliday) {
		DayOfWeek dayOfWeekend = dateTime.getDayOfWeek();
		switch (holidayValue) {
		case NON_WKN_BEFORE:
			if (dayOfWeekend.equals(DayOfWeek.SATURDAY)) {
				return dateTime.minusDays(1);
			} else if (dayOfWeekend.equals(DayOfWeek.SUNDAY)) {
				return dateTime.minusDays(2);
			}
			break;
		case NON_WKN_AFTER:
			if (dayOfWeekend.equals(DayOfWeek.SATURDAY)) {
				return dateTime.plusDays(2);
			} else if (dayOfWeekend.equals(DayOfWeek.SUNDAY)) {
				return dateTime.plusDays(1);
			}
			break;
		case NON_WKN_BEFORE_SAT:
			if (dayOfWeekend.equals(DayOfWeek.SATURDAY)) {
				return dateTime.minusDays(1);
			}
			break;
		case NON_WKN_AFTER_SUN:
			if (dayOfWeekend.equals(DayOfWeek.SUNDAY)) {
				return dateTime.plusDays(1);
			}
			break;
		case NON_WKN_SAT_SUN_SPLIT:
			if (dayOfWeekend.equals(DayOfWeek.SATURDAY)) {
				return dateTime.minusDays(1);
			} else if (dayOfWeekend.equals(DayOfWeek.SUNDAY)) {
				return dateTime.plusDays(1);
			}
			break;
		case NON_HOL_BEFORE:
			if (isHoliday) {
				dateTime = dateTime.minusDays(1);
			}
			break;
		case NON_HOL_AFTER:
			if (isHoliday) {
				dateTime = dateTime.plusDays(1);
			}
			break;
		case NON_HOL_WKN_BEFORE:
			if (isHoliday) {
				dateTime = dateTime.minusDays(1);
			}
			dayOfWeekend = dateTime.getDayOfWeek();
			if (dayOfWeekend.equals(DayOfWeek.SATURDAY)) {
				return dateTime.minusDays(1);
			} else if (dayOfWeekend.equals(DayOfWeek.SUNDAY)) {
				return dateTime.minusDays(2);
			}
			break;
		case NON_HOL_WKN_AFTER:
			if (isHoliday) {
				dateTime = dateTime.minusDays(1);
			}
			dayOfWeekend = dateTime.getDayOfWeek();
			if (dayOfWeekend.equals(DayOfWeek.SATURDAY)) {
				return dateTime.plusDays(2);

			} else if (dayOfWeekend.equals(DayOfWeek.SUNDAY)) {
				return dateTime.plusDays(1);
			}
			break;
		}
		return dateTime;
	}

	public static ZonedDateTime calculateDateTimeBySubtractFrequency(String frequency, ZonedDateTime dateTime) {
		int months = 0;
		int days = 0;
		switch (frequency) {
		case ANNUAL:
			months = 12;
			break;
		case MONTHLY:
			months = 1;
			break;
		case BI_MONTHLY:
			months = 2;
			break;
		case QUARTERLY:
			months = 3;
			break;
		case SEMI_ANNUAL:
			months = 6;
			break;
		case WEEKLY:
			days = 7;
			break;
		case BI_WEEKLY:
			days = 14;
			break;
		case SEMI_MONTHLY:
			Month month = dateTime.getMonth();
			if (dateTime.getDayOfMonth() == 1) {
				days = 14;
			} else if (dateTime.getDayOfMonth() == 29 && month.equals(Month.FEBRUARY)) {
				dateTime = dateTime.with(TemporalAdjusters.firstDayOfMonth()).toLocalDate()
						.atStartOfDay(ZoneId.of(timeZone)).plusDays(15);
				if (dateTime.getMonthValue() < 12) {
					months = 1;
				} else {
					dateTime = dateTime.withMonth(Month.JANUARY.getValue()).toLocalDate()
							.atStartOfDay(ZoneId.of(timeZone));
					dateTime = dateTime.plusYears(1);
				}
			} else if (dateTime.getDayOfMonth() < 15) {
				dateTime = dateTime.plusMonths(months).plusDays(15);
				if (month.equals(Month.FEBRUARY) && dateTime.getDayOfMonth() == 29) {
					days = dateTime.getDayOfMonth() - 1;
				}
			} else if (dateTime.getDayOfMonth() == 15) {
				dateTime = dateTime.with(TemporalAdjusters.firstDayOfMonth()).toLocalDate()
						.atStartOfDay(ZoneId.of(timeZone));
				if (dateTime.getMonthValue() < 12) {
					months = 1;
				} else {
					dateTime = dateTime.withMonth(Month.JANUARY.getValue()).toLocalDate()
							.atStartOfDay(ZoneId.of(timeZone));
					dateTime = dateTime.plusYears(1);
				}
			} else if (dateTime.getDayOfMonth() == 16) {
				dateTime = dateTime.with(TemporalAdjusters.firstDayOfMonth()).toLocalDate()
						.atStartOfDay(ZoneId.of(timeZone)).plusDays(1);
				if (dateTime.getMonthValue() < 12) {
					months = 1;
				} else {
					dateTime = dateTime.withMonth(Month.JANUARY.getValue()).toLocalDate()
							.atStartOfDay(ZoneId.of(timeZone));
					dateTime = dateTime.plusYears(1);
				}
			} else if (dateTime.getDayOfMonth() > 16) {
				dateTime = dateTime.with(TemporalAdjusters.firstDayOfMonth()).toLocalDate()
						.atStartOfDay(ZoneId.of(timeZone)).plusDays(dateTime.getDayOfMonth() - 16);
				if (dateTime.getMonthValue() < 12) {
					months = 1;
				} else {
					dateTime = dateTime.withMonth(Month.JANUARY.getValue()).toLocalDate()
							.atStartOfDay(ZoneId.of(timeZone));
					dateTime = dateTime.plusYears(1);
				}
			}
			break;
		default:
			break;

		}
		return dateTime.minusMonths(months).minusDays(days);
	}

	public static ZonedDateTime dateTimeAsStartOfDay(ZonedDateTime dateTime) {
		dateTime = dateTime.toLocalDate().atStartOfDay(ZoneId.of(timeZone));
		return dateTime;
	}

	public static ZonedDateTime dateTimeAsStartOfMonth(ZonedDateTime dateTime) {
		dateTime = dateTime.with(TemporalAdjusters.firstDayOfMonth()).toLocalDate().atStartOfDay(ZoneId.of(timeZone));
		return dateTime;
	}

	public static ZonedDateTime timeStampToZonedDateTime(Timestamp timestamp) {
		return ZonedDateTime.ofInstant(timestamp.toInstant(), ZoneId.of(timeZone));
	}

	public static ZonedDateTime dateTimeAsYYYYMMDDHHmmssSSS(ZonedDateTime dateTime) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSSSSS");
		LocalDateTime localDateTime = LocalDateTime.parse(formatter.format(dateTime), formatter);
		return localDateTime.atZone(ZoneId.of(timeZone));
	}

	public static Date dateAsYYYYMMDD(ZonedDateTime dateTime) {
		Date date = null;
		if (dateTime != null) {
			date = Date.from(ZonedDateTime.ofInstant(dateTime.toInstant(), ZoneId.of(timeZone)).toInstant());
		}
		return date;
	}

	public static ZonedDateTime dateTimeAsYYYYMMDD(long time) {
		String dateString = "1900-01-01";
		Date initialDate = dateAsYYYYMMDD(dateString);
		long actualTime = time + initialDate.getTime();
		return ZonedDateTime.ofInstant(Instant.ofEpochMilli(actualTime), ZoneId.of(timeZone));
	}

	public static Date dateAsYYYYMMDD(String dateString) {
		Date defaultDate = null;
		SimpleDateFormat formatter = new SimpleDateFormat(DEFUALT_DATE_FORMAT);
		try {
			defaultDate = formatter.parse(dateString);
		} catch (ParseException e) {
			LOGGER.error(e.getMessage());
		}
		return defaultDate;
	}

	public static ZonedDateTime sqlDateToZonedDateTimeAsYYYYMMDD(Object dateTime) {
		return ZonedDateTime.of(((java.sql.Date) dateTime).toLocalDate().atStartOfDay(), ZoneId.of(timeZone));
	}

	public static ZonedDateTime convertDateToZonedDateTime(Date date) {
		return date.toInstant().atZone(ZoneId.of(timeZone));
	}

	public static ZonedDateTime getDateofZonedDateTime(int year, int month, int dayOfMonth) {
		return ZonedDateTime.of(year, month, dayOfMonth, 0, 0, 0, 0, ZoneId.of(timeZone));
	}

	public static String getFormatedYYYYMMDDDateString(String dateString) {
		return dateString.substring(SHORT_ZERO, 4) + CHAR_HYPEN + dateString.substring(4, 6) + CHAR_HYPEN
				+ dateString.substring(6, 8);
	}

	public static String dateTimeAsYYYYMMDDHHhhmmssSSSString(ZonedDateTime datetime) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSSSSS")
				.withZone(ZoneId.of(timeZone));
		return datetime.format(formatter);
	}

	public static String dateAsMMDDYYYYString(Date date) {
		SimpleDateFormat format = new SimpleDateFormat("MM/dd/YYYY");
		format.setTimeZone(TimeZone.getTimeZone(timeZone));
		return format.format(date);
	}

	public static String dateTimeAsMMDDYYYYHHmmssSSS(ZonedDateTime datetime) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/YYYY HH.mm.ss.SSSSSS")
				.withZone(ZoneId.of(timeZone));
		return datetime.format(formatter);
	}

	public static String dateAsDDMONString(Date date) {
		SimpleDateFormat format = new SimpleDateFormat("dd-MMM");
		format.setTimeZone(TimeZone.getTimeZone(timeZone));
		return format.format(date);
	}

	public static ZonedDateTime[] getBeginEndDates(String beginDateString, String endDateString, String noOfMonths,
			ZonedDateTime applicationDate) {
		ZonedDateTime beginDate = null;
		ZonedDateTime endDate = null;

		if (!(noOfMonths == null || noOfMonths.trim().isEmpty())) {
			endDate = applicationDate;
			int numMonths = 0;
			try {
				numMonths = Integer.parseInt(noOfMonths);
			} catch (Exception e) {
				LOGGER.error(e.getMessage());
				throw e;
			}
			beginDate = endDate.minusMonths(numMonths).plusDays(1);
			return new ZonedDateTime[] { beginDate, endDate };
		}
		if (!(beginDateString == null || beginDateString.trim().isEmpty())
				&& !(endDateString == null || endDateString.trim().isEmpty())) {
			try {
				beginDate = dateTimeAsYYYYMMDD(beginDateString);
			} catch (Exception e) {
				LOGGER.error(e.getMessage());
				throw e;
			}
			try {
				endDate = dateTimeAsYYYYMMDD(endDateString);
			} catch (Exception e) {
				LOGGER.error(e.getMessage());
				throw e;
			}
			return new ZonedDateTime[] { beginDate, endDate };
		}
		if (!(beginDateString == null || beginDateString.trim().isEmpty())) {
			try {
				beginDate = dateTimeAsYYYYMMDD(beginDateString);
			} catch (Exception e) {
				LOGGER.error(e.getMessage());
				throw e;
			}
			endDate = beginDate.plusYears(1).minusDays(1);
			if (endDate.compareTo(applicationDate) > 0) {
				endDate = applicationDate;
			}
			return new ZonedDateTime[] { beginDate, endDate };
		}
		if (!(endDateString == null || endDateString.trim().isEmpty())) {
			try {
				endDate = dateTimeAsYYYYMMDD(endDateString);
			} catch (Exception e) {
				LOGGER.error(e.getMessage());
				throw e;
			}
			beginDate = endDate.minusYears(1).plusDays(1);
			return new ZonedDateTime[] { beginDate, endDate };
		}
		return new ZonedDateTime[] { beginDate, endDate };
	}

	public static Date defaultDate() {
		String dateString = "9999-12-31";
		Date defaultDate = null;
		SimpleDateFormat formatter = new SimpleDateFormat(DEFUALT_DATE_FORMAT);
		try {
			defaultDate = formatter.parse(dateString);
		} catch (ParseException e) {
			LOGGER.error(e);
		}
		return defaultDate;
	}

	public static String dateTimeAsMMYYYYAsString(ZonedDateTime datetime) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/yyyy").withZone(ZoneId.of(timeZone));
		return datetime.format(formatter);
	}

	public static ZonedDateTime convertSqlDateTime(ZonedDateTime date) {
		return dateTimeAsYYYYMMDD(dateTimeAsYYYYMMDDString(date));
	}

	public static int getPendingDays(ZonedDateTime startDate, ZonedDateTime endDate) {
		int pendingDays = SHORT_ZERO;

		ZonedDateTime startDt = startDate.toLocalDate().atStartOfDay(ZoneId.of(timeZone));
		ZonedDateTime endDt = endDate.toLocalDate().atStartOfDay(ZoneId.of(timeZone));

		if (endDt.isAfter(startDt)) {
			pendingDays = (int) ChronoUnit.DAYS.between(startDt, endDt);
		}
		return pendingDays;
	}

	public static String dateTimeAsMMDDYYYYString(ZonedDateTime datetime) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM-dd-YYYY").withZone(ZoneId.of(timeZone));
		return datetime.format(formatter);
	}

	public static String dateAsYYYYMMDDString(Date date) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		format.setTimeZone(TimeZone.getTimeZone(timeZone));
		return format.format(date);
	}

	public static String timestampAsYYYYMMDDHHmmssSSSSSSString(Timestamp timestamp) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd-HH.mm.ss.SSSSSS");
		return format.format(timestamp);
	}

	public static String timestampAsYYYYMMDDString(Timestamp timestamp) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		format.setTimeZone(TimeZone.getTimeZone(timeZone));
		return format.format(timestamp);
	}

	public static ZonedDateTime defaultDateTimeAsZero() {
		String dateString = "0001-01-01";
		return toDateTimeSince(dateString);
	}

	public static Timestamp defaultTimestampAsZeroValue() {
		String initialDateTime = dateTimeAsYYYYMMDDHHhhmmssSSSString(defaultDateTimeAsZero());
		return Timestamp.valueOf(Formatter.convertFormatTimestamp(initialDateTime));
	}

	public static boolean validateDate(Date date) {
		try {
			LocalDateTime.from(date.toInstant().atZone(ZoneId.of(timeZone)));
		} catch (Exception e) {
			LOGGER.info("error", e);
			return false;
		}
		return true;

	}

	public static ZonedDateTime dateTimeAsYYYYMMDDHHmmssSSSSSS(String dateString) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd-HH.mm.ss.SSSSSS");
		LocalDateTime localDateTime = LocalDateTime.parse(dateString, formatter);
		return localDateTime.atZone(ZoneId.of(timeZone));
	}

}