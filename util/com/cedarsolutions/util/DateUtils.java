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
package com.cedarsolutions.util;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;

import com.cedarsolutions.exception.CedarRuntimeException;


/**
 * Date-related utilities.
 * @author Kenneth J. Pronovici <pronovic@ieee.org>
 */
public class DateUtils {

    /** Format used for date. */
    public static final String DATE_FORMAT = "yyyy-MM-dd";

    /** Format used for date/time. */
    public static final String TIME_FORMAT = "yyyy-MM-dd'T'HH:mm";

    /** Format used for time only. */
    public static final String TIME_ONLY_FORMAT = "HH:mm:ss";

    /** Format used for timestamp. */
    public static final String TIMESTAMP_FORMAT = "yyyy-MM-dd'T'HH:mm:ss,SSS";

    /** Format used for numeric timestamp. */
    public static final String NUMERIC_TIMESTAMP_FORMAT = "yyyyMMddHHmmssSSS";

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
        return new DateTime().toDate();
    }

    /** Get the current year. */
    public static int getCurrentYear() {
        return new DateTime().getYear();
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
     * @param date  Date to reset
     * @param time  Time to set, like "00:00"
     */
    public static Date resetTime(Date date, String time) {
        // This is pretty hokey, but it does seem to work reliably
        return date == null ? date : parseDate(formatDate(date) + "T" + time, TIME_FORMAT);
    }

    /**
     * Create a date using a standard ISO 8601 date format.
     * @param iso8601Date  ISO 8601 date to use, something like '2011-12-31' or '2011-12-13T14:32'
     * @return Copy of source date, or null if source is null.
     */
    public static Date createDate(String iso8601Date) {
        return new DateTime(iso8601Date).toDate();
    }

    /**
     * Generate a range of years.
     * @param start   Starting year
     * @param end     Ending year
     * @return List of years between start and end (inclusive).
     */
    public static List<Integer> generateYearRange(int start, int end) {
        List<Integer> years = new ArrayList<Integer>();

        if (start == end) {
            years.add(start);
        } else if (end < start) {
            // degenerate case, but let's just handle it
            for (int year = end; year <= start; year++) {
                years.add(year);
            }
        } else {
            for (int year = start; year <= end; year++) {
                years.add(year);
            }
        }

        return years;
    }

    /**
     * Create a date as a copy of another date.
     * @param source   Source date to copy.
     * @return Copy of source date, or null if source is null.
     */
    public static Date createDate(Date source) {
        return source == null ? null : new DateTime(source).toDate();
    }

    /**
     * Create a date based on input values, with time fields set to zero.
     * @param year  Year, on the range 0-9999
     * @param month Month, on the range 1-12
     * @param day   Day, on the range 1-31
     * @return Date created based on input values.
     */
    public static Date createDate(int year, int month, int day) {
        return createDate(year, month, day, 0, 0);
    }

    /**
     * Create a date based on input values, with seconds and milliseconds set to zero.
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
     * Create a date based on input values, with milliseconds set to zero.
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
     * @param year        Year, on the range 1-12
     * @param month       Month, on the range 1-12
     * @param day         Day, on the range 1-31
     * @param hour        Hour, on the range 0-23
     * @param minute      Minute, on the range 0-59
     * @param second      Second, on the range 0-59
     * @param millisecond Millisecond, on the range 0-999
     * @return Date created based on input values.
     */
    public static Date createDate(int year, int month, int day, int hour, int minute, int second, int millisecond) {
        return new DateTime(year, month, day, hour, minute, second, millisecond).toDate();
    }

    /**
     * Format a date using the standard format (YYYY-MM-DD).
     * @param date  Java date to format
     * @return String generated based on input date, or null if passed-in date is null.
     * @throws CedarRuntimeException If the date cannot be formatted.
     */
    public static String formatDate(Date date) {
        return formatDate(date, DATE_FORMAT);
    }

    /**
     * Format a date/time using the standard format (YYYY-MM-DDTHH:MM).
     * @param date  Java date to format
     * @return String generated based on input date, or null if passed-in date is null.
     * @throws CedarRuntimeException If the date cannot be formatted.
     */
    public static String formatTime(Date date) {
        return formatDate(date, TIME_FORMAT);
    }

    /**
     * Format a time only using the standard format (HH:MM:SS).
     * @param date  Java date to format
     * @return String generated based on input date, or null if passed-in date is null.
     * @throws CedarRuntimeException If the date cannot be formatted.
     */
    public static String formatTimeOnly(Date date) {
        return formatDate(date, TIME_ONLY_FORMAT);
    }

    /**
     * Format a timestamp using the standard format (YYYY-MM-DDTHH:MM:SS,SSS).
     * @param date  Java date to format
     * @return String generated based on input date, or null if passed-in date is null.
     * @throws CedarRuntimeException If the date cannot be formatted.
     */
    public static String formatTimestamp(Date date) {
        return formatDate(date, TIMESTAMP_FORMAT);
    }

    /**
     * Format a numeric timestamp using the standard format (YYYYMMDDHHMMSSSSS).
     * @param date  Java date to format
     * @return String generated based on input date, or null if passed-in date is null.
     * @throws CedarRuntimeException If the date cannot be formatted.
     */
    public static String formatNumericTimestamp(Date date) {
        return formatDate(date, NUMERIC_TIMESTAMP_FORMAT);
    }

    /**
     * Format a date with a specified format using the US locale.
     * @param date   Java date to format
     * @param format Date format compatible with Joda time library
     * @return String generated based on input date, or null if passed-in date is null.
     * @throws CedarRuntimeException If the date cannot be formatted.
     */
    public static String formatDate(Date date, String format) {
        return formatDate(date, format, Locale.US);
    }

    /**
     * Format a Joda date with a specified format and locale.
     * The locale only really matters if you include localizable text in your format (i.e. month or day name).
     * @param date   Joda DateTime object to format
     * @param format Date format compatible with Joda time library
     * @param locale Locale to use when applying the format
     * @return String generated based on input date, or null if passed-in date is null.
     * @throws CedarRuntimeException If the date cannot be formatted.
     */
    public static String formatDate(Date date, String format, Locale locale) {
        return date == null ? "" : formatJodaDate(new DateTime(date), format, locale);
    }

    /**
     * Format a Joda date with a specified format using the US locale.
     * @param date   Joda DateTime object to format
     * @param format Date format compatible with Joda time library
     * @return String generated based on input Joda date, or null if passed-in date is null.
     * @throws CedarRuntimeException If the date cannot be formatted.
     */
    public static String formatJodaDate(DateTime date, String format) {
        return formatJodaDate(date, format, Locale.US);
    }

    /**
     * Format a Joda date with a specified format and locale.
     * The locale only really matters if you include localizable text in your format (i.e. month or day name).
     * @param date   Joda time DateTime to format
     * @param format Date format compatible with Joda time library
     * @param locale Locale to use when applying the format
     * @return String generated based on input Joda date, or null if passed-in date is null.
     * @throws CedarRuntimeException If the date cannot be formatted.
     */
    public static String formatJodaDate(DateTime date, String format, Locale locale) {
        try {
            return date == null ? "" : DateTimeFormat.forPattern(format).withLocale(locale).print(date);
        } catch (Exception e) {
            throw new CedarRuntimeException("Unable to format date using format [" + format + "]");
        }
    }

    /**
     * Format a string to a Java date using the specified format.
     * @param date   Date string to parse, presumed to use the passed-in format
     * @param format Date format compatible with Joda time library
     * @return Java date parsed from input string, or null if input date is null.
     * @throws CedarRuntimeException If the date cannot be parsed.
     */
    public static Date parseDate(String date, String format) {
        return date == null ? null : parseJodaDate(date, format).toDate();
    }

    /**
     * Format a string to a Joda date using the specified format.
     * @param date   Date string to parse, presumed to use the passed-in format
     * @param format Date format compatible with Joda time library
     * @return Joda date parsed from input string, or null if input date is null.
     * @throws CedarRuntimeException If the date cannot be parsed.
     */
    public static DateTime parseJodaDate(String date, String format) {
        try {
            return date == null ? null : DateTimeFormat.forPattern(format).parseDateTime(date);
        } catch (Exception e) {
            throw new CedarRuntimeException("Failed to parsed date [" + date + "] using format [" + format + "]");
        }
    }

    /** Indicates whether one date is before another date. */
    public static boolean isBefore(Date date, Date another) {
        DateTime dtDate = new DateTime(date);
        DateTime dtAnother = new DateTime(another);
        return dtDate.equals(dtAnother) || dtDate.isBefore(dtAnother);
    }

    /** Indicates whether one date is strictly before another date. */
    public static boolean isStrictlyBefore(Date date, Date another) {
        DateTime dtDate = new DateTime(date);
        DateTime dtAnother = new DateTime(another);
        return dtDate.isBefore(dtAnother);
    }

    /** Indicates whether one date is after another date. */
    public static boolean isAfter(Date date, Date another) {
        DateTime dtDate = new DateTime(date);
        DateTime dtAnother = new DateTime(another);
        return dtDate.equals(dtAnother) || dtDate.isAfter(dtAnother);
    }

    /** Indicates whether one date is strictly after another date. */
    public static boolean isStrictlyAfter(Date date, Date another) {
        DateTime dtDate = new DateTime(date);
        DateTime dtAnother = new DateTime(another);
        return dtDate.isAfter(dtAnother);
    }

    /** Indicates whether one date is between two other dates. */
    public static boolean isBetween(Date date, Date left, Date right) {
        DateTime dtDate = new DateTime(date);
        DateTime dtLeft = new DateTime(left);
        DateTime dtRight = new DateTime(right);
        return (dtDate.equals(dtLeft) || dtDate.isAfter(dtLeft)) &&
               (dtDate.equals(dtRight) || dtDate.isBefore(dtRight));
    }

    /** Indicates whether one date is strictly between two other dates. */
    public static boolean isStrictlyBetween(Date date, Date left, Date right) {
        DateTime dtDate = new DateTime(date);
        DateTime dtLeft = new DateTime(left);
        DateTime dtRight = new DateTime(right);
        return dtDate.isAfter(dtLeft) && dtDate.isBefore(dtRight);
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
}
