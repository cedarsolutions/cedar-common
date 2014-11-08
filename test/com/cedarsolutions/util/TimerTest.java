/* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 *
 *              C E D A R
 *          S O L U T I O N S       "Software done right."
 *           S O F T W A R E
 *
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 *
 * Copyright (c) 2013-2014 Kenneth J. Pronovici.
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
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.Date;

import org.junit.Test;

import com.cedarsolutions.exception.CedarRuntimeException;

/**
 * Unit tests for Timer.
 * @author Kenneth J. Pronovici <pronovic@ieee.org>
 */
public class TimerTest {

    /** Test the constructor. */
    @Test public void testConstructor() {
        Timer timer = null;
        Date start = DateUtils.getCurrentDate();
        Date stop = DateUtils.getCurrentDate();

        timer = new Timer();
        assertNotNull(timer);
        assertFalse(timer.isStarted());
        assertNull(timer.getStart());
        assertNull(timer.getStop());

        timer = new Timer(start);
        assertNotNull(timer);
        assertTrue(timer.isStarted());
        assertSame(start, timer.getStart());
        assertNull(timer.getStop());

        timer = new Timer(start, stop);
        assertNotNull(timer);
        assertTrue(timer.isStarted());
        assertSame(start, timer.getStart());
        assertSame(stop, timer.getStop());
    }

    /** Test the clear() method. */
    @Test public void testClear() {
        Date start = DateUtils.getCurrentDate();
        Date stop = DateUtils.getCurrentDate();
        Timer timer = new Timer(start, stop);
        timer.clear();
        assertNull(timer.getStart());
        assertNull(timer.getStop());
    }

    /** Test the start() method. */
    @Test public void testStart() {
        Timer timer = new Timer();
        timer.start();
        assertTrue(timer.isStarted());
        assertNotNull(timer.getStart());
    }

    /** Test the stop() method. */
    @Test public void testStop() {
        Timer timer = new Timer();
        assertFalse(timer.isStopped());
        timer.stop();
        assertNotNull(timer.getStop());
        assertTrue(timer.isStopped());
    }

    /** Test the getElapsedTime() method for real start/stop calls. */
    @Test public void testGetElapsedTimeReal() {
        Timer timer = null;

        try {
            timer = new Timer();
            timer.getElapsedTime();
            fail("Expected NullPointerExeption");
        } catch (CedarRuntimeException e) { }

        try {
            timer = new Timer();
            timer.start();
            timer.getElapsedTime();
            fail("Expected NullPointerExeption");
        } catch (CedarRuntimeException e) { }

        try {
            timer = new Timer();
            timer.stop();
            timer.getElapsedTime();
            fail("Expected NullPointerExeption");
        } catch (CedarRuntimeException e) { }

        timer = new Timer();
        timer.start();
        assertTrue(timer.isStarted());
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) { }
        timer.stop();
        long value = timer.getElapsedTime();
        assertTrue(value > 0);
    }

    /** Test the getElapsedTime() method for contrived data. */
    @Test public void testGetElapsedTimeContrived() {
        Timer timer = null;
        Date start = null;
        Date stop = DateUtils.getCurrentDate();

        // Negative times just get reset to zero (sometimes we see elapsed time of -1)
        timer = new Timer();
        start = new Date(stop.getTime() + 1);
        timer.setStartForUnitTest(start);
        timer.setStopForUnitTest(stop);
        assertTrue(timer.isStarted());
        assertEquals(0, timer.getElapsedTime());

        timer = new Timer();
        start = new Date(stop.getTime() - 1);
        timer.setStartForUnitTest(start);
        timer.setStopForUnitTest(stop);
        assertTrue(timer.isStarted());
        assertEquals(1, timer.getElapsedTime());

        timer = new Timer();
        start = new Date(stop.getTime() - 10);
        timer.setStartForUnitTest(start);
        timer.setStopForUnitTest(stop);
        assertTrue(timer.isStarted());
        assertEquals(10, timer.getElapsedTime());

        timer = new Timer();
        start = new Date(stop.getTime() - 1000000);
        timer.setStartForUnitTest(start);
        timer.setStopForUnitTest(stop);
        assertTrue(timer.isStarted());
        assertEquals(1000000, timer.getElapsedTime());

        timer = new Timer();
        start = new Date(0);
        timer.setStartForUnitTest(start);
        timer.setStopForUnitTest(stop);
        assertTrue(timer.isStarted());
        assertEquals(stop.getTime(), timer.getElapsedTime());
    }

    /** Test the getElapsedTimeString() method for real start/stop calls. */
    @Test public void testGetElapsedTimeStringReal() {
        Timer timer = null;

        try {
            timer = new Timer();
            timer.getElapsedTime();
            fail("Expected NullPointerExeption");
        } catch (CedarRuntimeException e) { }

        try {
            timer = new Timer();
            timer.start();
            timer.getElapsedTime();
            fail("Expected NullPointerExeption");
        } catch (CedarRuntimeException e) { }

        try {
            timer = new Timer();
            timer.stop();
            timer.getElapsedTime();
            fail("Expected NullPointerExeption");
        } catch (CedarRuntimeException e) { }

        timer = new Timer();
        timer.start();
        assertTrue(timer.isStarted());
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) { }
        timer.stop();
        String value = timer.getElapsedTimeString();
        assertNotNull(value);
        assertTrue(value.length() > 0);
    }

    /** Spot-check the getElapsedTimeString() method for contrived data. */
    @Test public void testElapsedTimeStringContrived() {
        Timer timer = new Timer();
        Date stop = DateUtils.getCurrentDate();
        Date start = new Date(stop.getTime() - 1);
        timer.setStartForUnitTest(start);
        timer.setStopForUnitTest(stop);
        assertEquals("00:00:00,001", timer.getElapsedTimeString());
    }

}
