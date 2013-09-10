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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Date;
import java.util.logging.LogRecord;

import org.junit.Test;

import com.cedarsolutions.util.JavaLoggingFormatter.LogInfo;

/**
 * Unit tests for JavaLoggingFormatter.
 * @author Kenneth J. Pronovici <pronovic@ieee.org>
 */
public class JavaLoggingFormatterTest {

    /** Test the LogInfo class. */
    @Test public void testLogInfo() {
        Date now = DateUtils.createDate(2011, 10, 7, 13, 1, 42, 663);
        LogRecord record = mock(LogRecord.class);
        when(record.getMillis()).thenReturn(now.getTime());
        when(record.getLevel()).thenReturn(java.util.logging.Level.INFO);
        when(record.getSourceClassName()).thenReturn("test.class.Name");
        when(record.getMessage()).thenReturn("message");

        LogInfo info = new LogInfo(record);
        assertNotNull(info);
        assertEquals(now, info.getDate());
        assertEquals(org.apache.log4j.Level.INFO, info.getLevel());
        assertEquals("test.class.Name", info.getClassName());
        assertEquals("message", info.getMessage());

        assertEquals("2011-10-07T13:01:42,663 [INFO ] --> [test.class.Name] message" + StringUtils.LINE_ENDING, info.format());
    }

    /** Test LogInfo.deriveLevel(). */
    @Test public void testDeriveLevel() {
        java.util.logging.Level[] input = new java.util.logging.Level[] { java.util.logging.Level.OFF,
                                                                          java.util.logging.Level.SEVERE,
                                                                          java.util.logging.Level.WARNING,
                                                                          java.util.logging.Level.INFO,
                                                                          java.util.logging.Level.CONFIG,
                                                                          java.util.logging.Level.FINE,
                                                                          java.util.logging.Level.FINER,
                                                                          java.util.logging.Level.FINEST,
                                                                          java.util.logging.Level.ALL, };

        org.apache.log4j.Level[] output = new org.apache.log4j.Level[] { org.apache.log4j.Level.OFF,
                                                                       org.apache.log4j.Level.ERROR,
                                                                       org.apache.log4j.Level.WARN,
                                                                       org.apache.log4j.Level.INFO,
                                                                       org.apache.log4j.Level.INFO,
                                                                       org.apache.log4j.Level.DEBUG,
                                                                       org.apache.log4j.Level.DEBUG,
                                                                       org.apache.log4j.Level.TRACE,
                                                                       org.apache.log4j.Level.ALL, };

        for (int i = 0; i < input.length; i++) {
            LogRecord record = mock(LogRecord.class);
            when(record.getLevel()).thenReturn(input[i]);
            LogInfo info = new LogInfo(record);
            assertEquals(output[i], info.getLevel());
        }
    }

}
