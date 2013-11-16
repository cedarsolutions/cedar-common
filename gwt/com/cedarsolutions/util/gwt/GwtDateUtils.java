/* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 *
 *              C E D A R
 *          S O L U T I O N S       "Software done right."
 *           S O F T W A R E
 *
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 *
 * Copyright (c) 2013 Kenneth J. Pronovici.
 * All rights reserved.
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the Apache License, Version 2.0.
 * See LICENSE for more information about the licensing terms.
 *
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 *
 * Author   : Kenneth J. Pronovici <pronovic@ieee.org>
 * Language : Java 6
 * Project  : Common Java Functionality
 *
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */
package com.cedarsolutions.util.gwt;

import java.util.Date;

import com.google.gwt.i18n.client.DateTimeFormat;

/**
 * Date utilities that are translatable to GWT client code.
 *
 * <p>
 * Note that this implementation relies on GWT functionality that
 * is only available in the client runtime.  You can't execute all
 * of these methods from normal Java code, only GWT client code.
 * Normal Java code should use the standard DateUtils class, which
 * provides similar functionality.
 * </p>
 *
 * @author Kenneth J. Pronovici <pronovic@ieee.org>
 */
public class GwtDateUtils {

    /** Format used for date. */
    public static final String DATE_FORMAT = "yyyy-MM-dd";

    /** Format used for date/time. */
    public static final String TIME_FORMAT = "yyyy-MM-dd'T'HH:mm";

    /** Format used for timestamp. */
    public static final String TIMESTAMP_FORMAT = "yyyy-MM-dd'T'HH:mm:ss,SSS";

    /** Number of seconds per minute. */
    public static final long SECONDS_PER_MINUTE = 60;

    /** Number of seconds per hour. */
    public static final long MINUTES_PER_HOUR = 60;

    /** Number of hours per day. */
    public static final long HOURS_PER_DAY = 24;

    /** Number of minutes per day. */
    public static final long MINUTES_PER_DAY = MINUTES_PER_HOUR * HOURS_PER_DAY;

    /** Number of milliseconds per second. */
    public static final long MILLISECONDS_PER_SECOND = 1000;

    /** Number of milliseconds per minute. */
    public static final long MILLISECONDS_PER_MINUTE = MILLISECONDS_PER_SECOND * SECONDS_PER_MINUTE;

    /** Number of milliseconds per hour. */
    public static final long MILLISECONDS_PER_HOUR = MILLISECONDS_PER_MINUTE * MINUTES_PER_HOUR;

    /** Number of milliseconds per day. */
    public static final long MILLISECONDS_PER_DAY = MILLISECONDS_PER_HOUR * HOURS_PER_DAY;

    /** Get a current date. */
    public static Date getCurrentDate() {
        return new Date();
    }

    /** Get the current date with time set to midnight. */
    public static Date getCurrentDateAtMidnight() {
        return getCurrentDateAtTime("00:00");
    }

    /** Get the current date with a specific time set. */
    public static Date getCurrentDateAtTime(String time) {
        return resetTime(getCurrentDate(), time);
    }

    /**
     * Reset a date to a particular time.
     * The result will always have seconds and milliseconds of zero.
     * @param date  Date to reset
     * @param time  Time to set, like "00:00" or "23:59"
     */
    public static Date resetTime(Date date, String time) {
        // This is pretty hokey, but it does seem to work reliably
        return date == null ? date : parseDate(formatDate(date) + "T" + time, TIME_FORMAT);
    }

    /**
     * Reset a date to a particular time.
     * @param date  Date to reset
     * @param time  Time to set, like "00:00:00,000" or "23:59:59,999"
     */
    public static Date resetTimestamp(Date date, String time) {
        // This is pretty hokey, but it does seem to work reliably
        return date == null ? date : parseDate(formatDate(date) + "T" + time, TIMESTAMP_FORMAT);
    }

    /** Get a GWT DateTimeFormat for a particular format string. */
    public static DateTimeFormat getFormat(String format) {
        return DateTimeFormat.getFormat(format);
    }

    /** Get the standard date format. */
    public static DateTimeFormat getDateFormat() {
        return getFormat(DATE_FORMAT);
    }

    /** Get the standard time format. */
    public static DateTimeFormat getTimeFormat() {
        return getFormat(TIME_FORMAT);
    }

    /** Get the standard timestamp format. */
    public static DateTimeFormat getTimestampFormat() {
        return getFormat(TIMESTAMP_FORMAT);
    }

    /**
     * Format a date using the standard format (YYYY-MM-DD).
     * @param date  Java date to format
     * @return String generated based on input date, or null if passed-in date is null.
     */
    public static String formatDate(Date date) {
        return formatDate(date, getDateFormat());
    }

    /**
     * Format a date/time using the standard format (YYYY-MM-DDTHH:MM).
     * @param date  Java date to format
     * @return String generated based on input date, or null if passed-in date is null.
     */
    public static String formatTime(Date date) {
        return formatDate(date, getTimeFormat());
    }

    /**
     * Format a timestamp using the standard format (YYYY-MM-DDTHH:MM:SS,SSS).
     * @param date  Java date to format
     * @return String generated based on input date, or null if passed-in date is null.
     */
    public static String formatTimestamp(Date date) {
        return formatDate(date, getTimestampFormat());
    }

    /**
     * Format a date with a specified format.
     * @param date   Java date to format
     * @param format Date format compatible with GWT DateTimeFormat class
     * @return String generated based on input date, or empty string if passed-in date or format is null or empty.
     */
    public static String formatDate(Date date, String format) {
        return date == null || GwtStringUtils.isEmpty(format) ? "" : formatDate(date, getFormat(format));
    }

    /**
     * Format a date with a specified format.
     * @param date   Java date to format
     * @param format Date format compatible with GWT DateTimeFormat class
     * @return String generated based on input date, or empty string if passed-in date or format is null or empty.
     */
    public static String formatDate(Date date, DateTimeFormat format) {
        return date == null || format == null ? "" : format.format(date);
    }

    /**
     * Format a string to a Java date using the specified format.
     * @param date   Date string to parse, presumed to use the passed-in format
     * @param format Date format compatible with GWT DateTimeFormat class
     * @return Java date parsed from input string, or null if input date or format is null or empty.
     */
    public static Date parseDate(String date, String format) {
        return GwtStringUtils.isEmpty(date) || GwtStringUtils.isEmpty(format) ? null : DateTimeFormat.getFormat(format).parseStrict(date);
    }

    /**
     * Create a date as a copy of another date.
     * @param source   Source date to copy.
     * @return Copy of source date, or null if source is null.
     */
    public static Date createDate(Date source) {
        if (source == null) {
            return null;
        } else {
            return new Date(source.getTime());
        }
    }

    /**
     * Create a date based on input values.
     * @param year        Year, on the range 0-9999
     * @param month       Month, on the range 1-12
     * @param day         Day, on the range 1-31
     * @return Date created based on input values.
     */
    public static Date createDate(int year, int month, int day) {
        return createDate(year, month, day, 0, 0);
    }

    /**
     * Create a date based on input values.
     * @param year        Year, on the range 0-9999
     * @param month       Month, on the range 1-12
     * @param day         Day, on the range 1-31
     * @param hour        Hour, on the range 0-23
     * @param minute      Minute, on the range 0-59
     * @return Date created based on input values.
     */
    public static Date createDate(int year, int month, int day, int hour, int minute) {
        return createDate(year, month, day, hour, minute, 0);
    }

    /**
     * Create a date based on input values.
     * @param year        Year, on the range 0-9999
     * @param month       Month, on the range 1-12
     * @param day         Day, on the range 1-31
     * @param hour        Hour, on the range 0-23
     * @param minute      Minute, on the range 0-59
     * @param second      Second, on the range 0-59
     * @return Date created based on input values.
     */
    public static Date createDate(int year, int month, int day, int hour, int minute, int second) {
        return createDate(year, month, day, hour, minute, second, 0);
    }

    /**
     * Create a date based on input values.
     * @param year        Year, on the range 0-9999
     * @param month       Month, on the range 1-12
     * @param day         Day, on the range 1-31
     * @param hour        Hour, on the range 0-23
     * @param minute      Minute, on the range 0-59
     * @param second      Second, on the range 0-59
     * @param millisecond Millisecond, on the range 0-999
     * @return Date created based on input values.
     */
    public static Date createDate(int year, int month, int day, int hour, int minute, int second, int millisecond) {
        validateDateComponents(year, month, day, hour, minute, second, millisecond);

        String yearString = formatNumber(year, 4);
        String monthString = formatNumber(month, 2);
        String dayString = formatNumber(day, 2);
        String hourString = formatNumber(hour, 2);
        String minuteString = formatNumber(minute, 2);
        String secondString = formatNumber(second, 2);
        String millisecondString = formatNumber(millisecond, 3);

        String date = yearString + "-" + monthString + "-" + dayString + " " +
                      hourString + ":" + minuteString + ":" + secondString + "." + millisecondString;

        return parseDate(date, "yyyy-MM-dd HH:mm:ss.SSS");
    }

    /**
     * Format a timestamp as a string, assuming it is an elapsed time.
     * The formatted string will look like "00:14:32,117" or "1 day 00:14:32,117".
     * @param timestamp  Timestamp to format, as from Date.getTime()
     * @return Timestamp as described above.
     */
    public static String formatElapsedTime(long timestamp) {
        long days = 0L;
        long hours = 0L;
        long minutes = 0L;
        long seconds = 0L;
        long milliseconds = 0L;
        long remainder = 0L;

        remainder = timestamp;

        days = remainder / MILLISECONDS_PER_DAY;
        remainder = remainder % MILLISECONDS_PER_DAY;

        hours = remainder / MILLISECONDS_PER_HOUR;
        remainder = remainder % MILLISECONDS_PER_HOUR;

        minutes = remainder / MILLISECONDS_PER_MINUTE;
        remainder = remainder % MILLISECONDS_PER_MINUTE;

        seconds = remainder / MILLISECONDS_PER_SECOND;
        remainder = remainder % MILLISECONDS_PER_SECOND;

        milliseconds = remainder;

        StringBuffer result = new StringBuffer();

        if (days > 0) {
            result.append(days);
            if (days == 1) {
                result.append(" day ");
            } else {
                result.append(" days ");
            }
        }

        if (hours < 10) {
            result.append("0");
        }

        result.append(hours);
        result.append(":");

        if (minutes < 10) {
            result.append("0");
        }

        result.append(minutes);
        result.append(":");

        if (seconds < 10) {
            result.append("0");
        }

        result.append(seconds);
        result.append(",");

        if (milliseconds < 100) {
            result.append("0");
        }
        if (milliseconds < 10) {
            result.append("0");
        }

        result.append(milliseconds);

        return result.toString();
    }

    /**
     * Format a number as a string, padding with leading zeroes if needed.
     * @param input   Input value to format
     * @param digits  Number of digits to pad to
     * @return Input formatted as a string.
     */
    private static String formatNumber(int input, int digits) {
        String value = String.valueOf(input);

        if (value.length() < digits) {
            int needed = digits - value.length();
            for (int i = 0; i < needed; i++) {
                value = "0" + value;
            }
        }

        return value;
    }

    /** Validate date components. */
    private static void validateDateComponents(int year, int month, int day, int hour, int minute, int second, int millisecond) {
        validateYear(year);
        validateMonth(month);
        validateDay(day);
        validateHour(hour);
        validateMinute(minute);
        validateSecond(second);
        validateMillisecond(millisecond);
    }

    /** Validate a year date component. */
    private static void validateYear(int year) {
        if (year < 0 || year > 9999) {
            throw new IllegalArgumentException("Invalid year: " + year);
        }
    }

    /** Validate a month date component. */
    private static void validateMonth(int month) {
        if (month < 1 || month > 12) {
            throw new IllegalArgumentException("Invalid month: " + month);
        }
    }

    /** Validate a day date component. */
    private static void validateDay(int day) {
        if (day < 1 || day > 31) {
            throw new IllegalArgumentException("Invalid day: " + day);
        }
    }

    /** Validate an hour date component. */
    private static void validateHour(int hour) {
        if (hour < 0 || hour > 23) {
            throw new IllegalArgumentException("Invalid hour: " + hour);
        }
    }

    /** Validate a minute date component. */
    private static void validateMinute(int minute) {
        if (minute < 0 || minute > 59) {
            throw new IllegalArgumentException("Invalid minute: " + minute);
        }
    }

    /** Validate a second date component. */
    private static void validateSecond(int second) {
        if (second < 0 || second > 59) {
            throw new IllegalArgumentException("Invalid second: " + second);
        }
    }

    /** Validate a millisecond date component. */
    private static void validateMillisecond(int millisecond) {
        if (millisecond < 0 || millisecond > 999) {
            throw new IllegalArgumentException("Invalid millisecond: " + millisecond);
        }
    }

}
