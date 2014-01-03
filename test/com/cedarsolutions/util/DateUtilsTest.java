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

import static com.cedarsolutions.junit.util.Assertions.assertAfter;
import static com.cedarsolutions.junit.util.Assertions.assertBefore;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;

import org.joda.time.DateTime;
import org.junit.Test;

import com.cedarsolutions.exception.CedarRuntimeException;

/**
 * Unit tests for DateUtils.
 * @author Kenneth J. Pronovici <pronovic@ieee.org>
 */
public class DateUtilsTest {

    /** Test getCurrentDate(). */
    @Test public void testGetCurrentDate() {
        Date start = new DateTime().toDate();
        Date current = DateUtils.getCurrentDate();
        Date stop = new DateTime().toDate();
        assertAfter(start, current);
        assertBefore(stop, current);
    }

    /** Test getCurrentYear(). */
    @Test public void testGetCurrentYear() {
        assertEquals(new DateTime().getYear(), DateUtils.getCurrentYear());
    }

    /** Test getCurrentDateAtMidnight(). */
    @Test public void testGetCurrentDateAtMidnight() {
        Date current = DateUtils.getCurrentDate();
        Date time = DateUtils.getCurrentDateAtMidnight();
        assertEquals(DateUtils.formatDate(current), DateUtils.formatDate(time));
        assertEquals("00:00:00", DateUtils.formatDate(time, "HH:mm:ss"));
    }

    /** Test getCurrentDateAtTime(). */
    @Test public void testGetCurrentDateAtTime() {
        Date current = DateUtils.getCurrentDate();
        Date time = DateUtils.getCurrentDateAtTime("14:32");
        assertEquals(DateUtils.formatDate(current), DateUtils.formatDate(time));
        assertEquals("14:32:00", DateUtils.formatDate(time, "HH:mm:ss"));
    }

    /** Test generateYearRange(). */
    @Test public void testGenerateYearRange() {
        List<Integer> years;

        years = DateUtils.generateYearRange(2000, 2000);
        assertEquals(1, years.size());
        assertEquals(new Integer(2000), years.get(0));

        years = DateUtils.generateYearRange(2000, 2002);
        assertEquals(3, years.size());
        assertEquals(new Integer(2000), years.get(0));
        assertEquals(new Integer(2001), years.get(1));
        assertEquals(new Integer(2002), years.get(2));

        // we also handle the degenerate case
        years = DateUtils.generateYearRange(2002, 2000);
        assertEquals(3, years.size());
        assertEquals(new Integer(2000), years.get(0));
        assertEquals(new Integer(2001), years.get(1));
        assertEquals(new Integer(2002), years.get(2));
    }

    /** Test formatDate(). */
    @Test public void testFormatDate() {
        assertEquals("", DateUtils.formatDate(null));
        assertEquals("2011-01-02", DateUtils.formatDate(DateUtils.createDate(2011, 1, 2, 3, 4, 5, 6)));
        assertEquals("2011-11-12", DateUtils.formatDate(DateUtils.createDate(2011, 11, 12, 13, 14, 15, 16)));
        assertEquals("2011-11-12", DateUtils.formatDate(DateUtils.createDate(2011, 11, 12, 13, 14, 15, 167)));
    }

    /** Test formatTime(). */
    @Test public void testFormatTime() {
        assertEquals("", DateUtils.formatTime(null));
        assertEquals("2011-01-02T03:04", DateUtils.formatTime(DateUtils.createDate(2011, 1, 2, 3, 4, 5, 6)));
        assertEquals("2011-11-12T13:14", DateUtils.formatTime(DateUtils.createDate(2011, 11, 12, 13, 14, 15, 16)));
        assertEquals("2011-11-12T13:14", DateUtils.formatTime(DateUtils.createDate(2011, 11, 12, 13, 14, 15, 167)));
    }

    /** Test formatTimeOnly(). */
    @Test public void testFormatTimeOnly() {
        assertEquals("", DateUtils.formatTimeOnly(null));
        assertEquals("03:04:05", DateUtils.formatTimeOnly(DateUtils.createDate(2011, 1, 2, 3, 4, 5, 6)));
        assertEquals("13:14:15", DateUtils.formatTimeOnly(DateUtils.createDate(2011, 11, 12, 13, 14, 15, 16)));
        assertEquals("13:14:15", DateUtils.formatTimeOnly(DateUtils.createDate(2011, 11, 12, 13, 14, 15, 167)));
    }

    /** Test formatTimestamp(). */
    @Test public void testFormatTimestamp() {
        assertEquals("", DateUtils.formatTimestamp(null));
        assertEquals("2011-01-02T03:04:05,006", DateUtils.formatTimestamp(DateUtils.createDate(2011, 1, 2, 3, 4, 5, 6)));
        assertEquals("2011-11-12T13:14:15,016", DateUtils.formatTimestamp(DateUtils.createDate(2011, 11, 12, 13, 14, 15, 16)));
        assertEquals("2011-11-12T13:14:15,167", DateUtils.formatTimestamp(DateUtils.createDate(2011, 11, 12, 13, 14, 15, 167)));
    }

    /** Test formatNumericTimestamp(). */
    @Test public void testFormatNumericTimestamp() {
        assertEquals("", DateUtils.formatNumericTimestamp(null));
        assertEquals("20110102030405006", DateUtils.formatNumericTimestamp(DateUtils.createDate(2011, 1, 2, 3, 4, 5, 6)));
        assertEquals("20111112131415016", DateUtils.formatNumericTimestamp(DateUtils.createDate(2011, 11, 12, 13, 14, 15, 16)));
        assertEquals("20111112131415167", DateUtils.formatNumericTimestamp(DateUtils.createDate(2011, 11, 12, 13, 14, 15, 167)));
    }

    /** Test formatDate() for an arbitrary format. */
    @Test public void testFormatDateArbitraryFormat() {
        try {
            DateUtils.formatDate(DateUtils.createDate(2011, 12, 14), null);
            fail("Expected CedarRuntimeException");
        } catch (CedarRuntimeException e) { }

        try {
            DateUtils.formatDate(DateUtils.createDate(2011, 12, 14), "");
            fail("Expected CedarRuntimeException");
        } catch (CedarRuntimeException e) { }

        try {
            DateUtils.formatDate(DateUtils.createDate(2011, 12, 14), "blech");
            fail("Expected CedarRuntimeException");
        } catch (CedarRuntimeException e) { }

        assertEquals("", DateUtils.formatDate(null, null));
        assertEquals("", DateUtils.formatDate(null, "yyyy-MM-dd"));
        assertEquals("", DateUtils.formatDate(null, "yyyy-MMM-dd"));
        assertEquals("", DateUtils.formatDate(null, "yyyy-MM-dd", Locale.US));
        assertEquals("", DateUtils.formatDate(null, "yyyy-MMM-dd", Locale.US));
        assertEquals("2011-12-14", DateUtils.formatDate(DateUtils.createDate(2011, 12, 14), "yyyy-MM-dd"));
        assertEquals("2011-Dec-14", DateUtils.formatDate(DateUtils.createDate(2011, 12, 14), "yyyy-MMM-dd"));
        assertEquals("2011-Dec-14", DateUtils.formatDate(DateUtils.createDate(2011, 12, 14), "yyyy-MMM-dd", Locale.US));
    }

    /** Test formatJodaDate(). */
    @Test public void testFormatJodaData() {
        try {
            DateUtils.formatJodaDate(new DateTime("2011-12-14"), null);
            fail("Expected CedarRuntimeException");
        } catch (CedarRuntimeException e) { }

        try {
            DateUtils.formatJodaDate(new DateTime("2011-12-14"), "");
            fail("Expected CedarRuntimeException");
        } catch (CedarRuntimeException e) { }

        try {
            DateUtils.formatJodaDate(new DateTime("2011-12-14"), "blech");
            fail("Expected CedarRuntimeException");
        } catch (CedarRuntimeException e) { }

        assertEquals("", DateUtils.formatJodaDate(null, null));
        assertEquals("", DateUtils.formatJodaDate(null, "yyyy-MM-dd"));
        assertEquals("", DateUtils.formatJodaDate(null, "yyyy-MMM-dd"));
        assertEquals("", DateUtils.formatJodaDate(null, "yyyy-MM-dd", Locale.US));
        assertEquals("", DateUtils.formatJodaDate(null, "yyyy-MMM-dd", Locale.US));
        assertEquals("2011-12-14", DateUtils.formatJodaDate(new DateTime("2011-12-14"), "yyyy-MM-dd"));
        assertEquals("2011-Dec-14", DateUtils.formatJodaDate(new DateTime("2011-12-14"), "yyyy-MMM-dd"));
        assertEquals("2011-Dec-14", DateUtils.formatJodaDate(new DateTime("2011-12-14"), "yyyy-MMM-dd", Locale.US));
    }

    /** Test parseDate(). */
    @Test public void testParseDate() {
        try {
            DateUtils.parseDate("2011-12-14", null);
            fail("Expected CedarRuntimeException");
        } catch (CedarRuntimeException e) { }

        try {
            DateUtils.parseDate("2011-12-14", "");
            fail("Expected CedarRuntimeException");
        } catch (CedarRuntimeException e) { }

        try {
            DateUtils.parseDate("2011-12-14", "blech");
            fail("Expected CedarRuntimeException");
        } catch (CedarRuntimeException e) { }

        try {
            DateUtils.parseDate("", "yyyy-MM-dd");
            fail("Expected CedarRuntimeException");
        } catch (CedarRuntimeException e) { }

        assertEquals(null, DateUtils.parseDate(null, null));
        assertEquals(null, DateUtils.parseDate(null, "yyyy-MM-dd"));
        assertEquals(DateUtils.createDate(2011, 12, 14), DateUtils.parseDate("2011-12-14", "yyyy-MM-dd"));
        assertEquals(DateUtils.createDate(2011, 12, 14), DateUtils.parseDate("2011-Dec-14", "yyyy-MMM-dd"));
    }

    /** Test parseJodaDate(). */
    @Test public void testJodaParseDate() {
        try {
            DateUtils.parseJodaDate("2011-12-14", null);
            fail("Expected CedarRuntimeException");
        } catch (CedarRuntimeException e) { }

        try {
            DateUtils.parseJodaDate("2011-12-14", "");
            fail("Expected CedarRuntimeException");
        } catch (CedarRuntimeException e) { }

        try {
            DateUtils.parseJodaDate("2011-12-14", "blech");
            fail("Expected CedarRuntimeException");
        } catch (CedarRuntimeException e) { }

        try {
            DateUtils.parseJodaDate("", "yyyy-MM-dd");
            fail("Expected CedarRuntimeException");
        } catch (CedarRuntimeException e) { }

        assertEquals(null, DateUtils.parseJodaDate(null, null));
        assertEquals(null, DateUtils.parseJodaDate(null, "yyyy-MM-dd"));
        assertEquals(new DateTime("2011-12-14"), DateUtils.parseJodaDate("2011-12-14", "yyyy-MM-dd"));
        assertEquals(new DateTime("2011-12-14"), DateUtils.parseJodaDate("2011-Dec-14", "yyyy-MMM-dd"));
    }

    /** Test createDate() using the ISO 8601 date format. */
    @Test public void testCreateDateIso8601Format() {
        assertEquals(DateUtils.createDate(2011, 11, 12, 13, 14, 15, 167), DateUtils.createDate("2011-11-12T13:14:15.167"));
    }

    /** Test createDate() using copy from another date. */
    @Test public void testCopyDate() {
        Date date = DateUtils.createDate(2011, 11, 12, 13, 14, 15, 167);
        assertEquals(date, DateUtils.createDate(date));
        assertEquals(null, DateUtils.createDate((Date) null));
    }

    /** Test createDate() using individual fields. */
    @Test public void testCreateDateFields() {
        Date date = DateUtils.createDate(2011, 1, 21);
        assertDateValues(date, 2011, 1, 21, 0, 0, 0, 0);

        date = DateUtils.createDate(2011, 1, 21, 14, 27);
        assertDateValues(date, 2011, 1, 21, 14, 27, 0, 0);

        date = DateUtils.createDate(2011, 1, 21, 14, 27, 54);
        assertDateValues(date, 2011, 1, 21, 14, 27, 54, 0);

        date = DateUtils.createDate(2011, 1, 21, 14, 27, 54, 334);
        assertDateValues(date, 2011, 1, 21, 14, 27, 54, 334);
    }

    /** Test isBefore(). */
    @Test public void testIsBefore() {
        Date date1 = DateUtils.createDate("2011-06-24T11:13:42.001");
        Date date2 = DateUtils.createDate("2011-06-24T11:13:42.002");
        Date date3 = DateUtils.createDate("2011-06-24T11:13:42.003");

        assertTrue(DateUtils.isBefore(date1, date1));
        assertTrue(DateUtils.isBefore(date2, date2));
        assertTrue(DateUtils.isBefore(date2, date2));

        assertTrue(DateUtils.isBefore(date1, date2));
        assertTrue(DateUtils.isBefore(date1, date3));
        assertTrue(DateUtils.isBefore(date2, date3));

        assertFalse(DateUtils.isBefore(date2, date1));
        assertFalse(DateUtils.isBefore(date3, date1));
    }

    /** Test isStrictlyBefore(). */
    @Test public void testIsStrictlyBefore() {
        Date date1 = DateUtils.createDate("2011-06-24T11:13:42.001");
        Date date2 = DateUtils.createDate("2011-06-24T11:13:42.002");
        Date date3 = DateUtils.createDate("2011-06-24T11:13:42.003");

        assertFalse(DateUtils.isStrictlyBefore(date1, date1));
        assertFalse(DateUtils.isStrictlyBefore(date2, date2));
        assertFalse(DateUtils.isStrictlyBefore(date2, date2));

        assertTrue(DateUtils.isBefore(date1, date2));
        assertTrue(DateUtils.isBefore(date1, date3));
        assertTrue(DateUtils.isBefore(date2, date3));

        assertFalse(DateUtils.isBefore(date2, date1));
        assertFalse(DateUtils.isBefore(date3, date1));
    }

    /** Test isAfter(). */
    @Test public void testIsAfter() {
        Date date1 = DateUtils.createDate("2011-06-24T11:13:42.001");
        Date date2 = DateUtils.createDate("2011-06-24T11:13:42.002");
        Date date3 = DateUtils.createDate("2011-06-24T11:13:42.003");

        assertTrue(DateUtils.isAfter(date1, date1));
        assertTrue(DateUtils.isAfter(date2, date2));
        assertTrue(DateUtils.isAfter(date2, date2));

        assertTrue(DateUtils.isAfter(date2, date1));
        assertTrue(DateUtils.isAfter(date3, date1));
        assertTrue(DateUtils.isAfter(date3, date2));

        assertFalse(DateUtils.isAfter(date1, date2));
        assertFalse(DateUtils.isAfter(date1, date3));
    }

    /** Test isStrictlyAfter(). */
    @Test public void testIsStrictlyAfter() {
        Date date1 = DateUtils.createDate("2011-06-24T11:13:42.001");
        Date date2 = DateUtils.createDate("2011-06-24T11:13:42.002");
        Date date3 = DateUtils.createDate("2011-06-24T11:13:42.003");

        assertFalse(DateUtils.isStrictlyAfter(date1, date1));
        assertFalse(DateUtils.isStrictlyAfter(date2, date2));
        assertFalse(DateUtils.isStrictlyAfter(date2, date2));

        assertTrue(DateUtils.isStrictlyAfter(date2, date1));
        assertTrue(DateUtils.isStrictlyAfter(date3, date1));
        assertTrue(DateUtils.isStrictlyAfter(date3, date2));

        assertFalse(DateUtils.isStrictlyAfter(date1, date2));
        assertFalse(DateUtils.isStrictlyAfter(date1, date3));
    }

    /** Test isBetween(). */
    @Test public void testIsBetween() {
        Date date1 = DateUtils.createDate("2011-06-24T11:13:42.001");
        Date date2 = DateUtils.createDate("2011-06-24T11:13:42.002");
        Date date3 = DateUtils.createDate("2011-06-24T11:13:42.003");

        assertTrue(DateUtils.isBetween(date1, date1, date1));
        assertTrue(DateUtils.isBetween(date1, date1, date2));
        assertTrue(DateUtils.isBetween(date1, date1, date3));

        assertTrue(DateUtils.isBetween(date2, date1, date3));
        assertTrue(DateUtils.isBetween(date2, date2, date2));
        assertTrue(DateUtils.isBetween(date2, date2, date3));

        assertTrue(DateUtils.isBetween(date3, date3, date3));
    }

    /** Test isStrictlyBetween(). */
    @Test public void testIsStrictlyBetween() {
        Date date1 = DateUtils.createDate("2011-06-24T11:13:42.001");
        Date date2 = DateUtils.createDate("2011-06-24T11:13:42.002");
        Date date3 = DateUtils.createDate("2011-06-24T11:13:42.003");

        assertFalse(DateUtils.isStrictlyBetween(date1, date1, date1));
        assertFalse(DateUtils.isStrictlyBetween(date1, date1, date2));
        assertFalse(DateUtils.isStrictlyBetween(date1, date1, date3));

        assertTrue(DateUtils.isStrictlyBetween(date2, date1, date3));
        assertFalse(DateUtils.isStrictlyBetween(date2, date2, date2));
        assertFalse(DateUtils.isStrictlyBetween(date2, date2, date3));

        assertFalse(DateUtils.isStrictlyBetween(date3, date3, date3));
    }

    /** Test the formatElapsedTime() method. */
    @Test public void testFormatElapsedTime() {
        long oneSecond = DateUtils.MILLISECONDS_PER_SECOND;
        long twoSeconds = DateUtils.MILLISECONDS_PER_SECOND * 2;
        long oneMinute = DateUtils.MILLISECONDS_PER_MINUTE;
        long tenMinutes = DateUtils.MILLISECONDS_PER_MINUTE * 10;
        long elevenMinutes = DateUtils.MILLISECONDS_PER_MINUTE * 11;
        long oneHour = DateUtils.MILLISECONDS_PER_HOUR;
        long tenHours = DateUtils.MILLISECONDS_PER_HOUR * 10;
        long oneDay = DateUtils.MILLISECONDS_PER_DAY;

        assertEquals("00:00:00,001", DateUtils.formatElapsedTime(1));
        assertEquals("00:00:00,002", DateUtils.formatElapsedTime(2));
        assertEquals("00:00:00,999", DateUtils.formatElapsedTime(oneSecond - 1));
        assertEquals("00:00:01,000", DateUtils.formatElapsedTime(oneSecond));
        assertEquals("00:00:01,001", DateUtils.formatElapsedTime(oneSecond + 1));
        assertEquals("00:00:01,002", DateUtils.formatElapsedTime(oneSecond + 2));
        assertEquals("00:00:02,000", DateUtils.formatElapsedTime(twoSeconds));
        assertEquals("00:00:59,999", DateUtils.formatElapsedTime(oneMinute - 1));
        assertEquals("00:01:00,000", DateUtils.formatElapsedTime(oneMinute));
        assertEquals("00:01:00,001", DateUtils.formatElapsedTime(oneMinute + 1));
        assertEquals("00:01:01,001", DateUtils.formatElapsedTime(oneMinute + oneSecond + 1));
        assertEquals("00:09:59,999", DateUtils.formatElapsedTime(tenMinutes - 1));
        assertEquals("00:10:00,000", DateUtils.formatElapsedTime(tenMinutes));
        assertEquals("00:10:59,999", DateUtils.formatElapsedTime(elevenMinutes - 1));
        assertEquals("00:59:59,999", DateUtils.formatElapsedTime(oneHour - 1));
        assertEquals("01:00:00,000", DateUtils.formatElapsedTime(oneHour));
        assertEquals("01:00:00,001", DateUtils.formatElapsedTime(oneHour + 1));
        assertEquals("09:59:59,999", DateUtils.formatElapsedTime(tenHours - 1));
        assertEquals("10:00:00,000", DateUtils.formatElapsedTime(tenHours));
        assertEquals("10:00:00,001", DateUtils.formatElapsedTime(tenHours + 1));
        assertEquals("23:59:59,999", DateUtils.formatElapsedTime(oneDay - 1));
        assertEquals("1 day 00:00:00,000", DateUtils.formatElapsedTime(oneDay));
        assertEquals("1 day 00:00:00,001", DateUtils.formatElapsedTime(oneDay + 1));
        assertEquals("2 days 00:00:12,222", DateUtils.formatElapsedTime((oneDay * 2) + 12222));
    }

    /** Assert that a date has the passed-in values. */
    private static void assertDateValues(Date date, int year, int month, int day, int hour, int minute, int second, int millisecond) {
        Calendar calendar = GregorianCalendar.getInstance();
        calendar.setTime(date);
        assertEquals(year, calendar.get(Calendar.YEAR));
        assertEquals(month - 1, calendar.get(Calendar.MONTH));
        assertEquals(day, calendar.get(Calendar.DATE));
        assertEquals(hour, calendar.get(Calendar.HOUR_OF_DAY));
        assertEquals(minute, calendar.get(Calendar.MINUTE));
        assertEquals(second, calendar.get(Calendar.SECOND));
        assertEquals(millisecond, calendar.get(Calendar.MILLISECOND));
    }
}

