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

import com.cedarsolutions.client.gwt.junit.ClientTestCase;

/**
 * Client-side unit tests for GwtTimer.
 * @author Kenneth J. Pronovici <pronovic@ieee.org>
 */
public class GwtTimerClientTest extends ClientTestCase {

    /** Test the constructor. */
    public void testConstructor() {
        GwtTimer timer = null;
        Date start = GwtDateUtils.getCurrentDate();
        Date stop = GwtDateUtils.getCurrentDate();

        timer = new GwtTimer();
        assertNotNull(timer);
        assertFalse(timer.isStarted());
        assertNull(timer.getStart());
        assertNull(timer.getStop());

        timer = new GwtTimer(start);
        assertNotNull(timer);
        assertTrue(timer.isStarted());
        assertSame(start, timer.getStart());
        assertNull(timer.getStop());

        timer = new GwtTimer(start, stop);
        assertNotNull(timer);
        assertTrue(timer.isStarted());
        assertSame(start, timer.getStart());
        assertSame(stop, timer.getStop());
    }

    /** Test the clear() method. */
    public void testClear() {
        Date start = GwtDateUtils.getCurrentDate();
        Date stop = GwtDateUtils.getCurrentDate();
        GwtTimer timer = new GwtTimer(start, stop);
        timer.clear();
        assertNull(timer.getStart());
        assertNull(timer.getStop());
    }

    /** Test the start() method. */
    public void testStart() {
        GwtTimer timer = new GwtTimer();
        timer.start();
        assertTrue(timer.isStarted());
        assertNotNull(timer.getStart());
    }

    /** Test the stop() method. */
    public void testStop() {
        GwtTimer timer = new GwtTimer();
        assertFalse(timer.isStopped());
        timer.stop();
        assertNotNull(timer.getStop());
        assertTrue(timer.isStopped());
    }

    /** Test the getElapsedTime() method for contrived data. */
    public void testGetElapsedTimeContrived() {
        GwtTimer timer = null;
        Date start = null;
        Date stop = GwtDateUtils.getCurrentDate();

        // Negative times just get reset to zero (sometimes we see elapsed time of -1)
        timer = new GwtTimer();
        start = new Date(stop.getTime() + 1);
        timer.setStartForUnitTest(start);
        timer.setStopForUnitTest(stop);
        assertTrue(timer.isStarted());
        assertEquals(0, timer.getElapsedTime());

        timer = new GwtTimer();
        start = new Date(stop.getTime() - 1);
        timer.setStartForUnitTest(start);
        timer.setStopForUnitTest(stop);
        assertTrue(timer.isStarted());
        assertEquals(1, timer.getElapsedTime());

        timer = new GwtTimer();
        start = new Date(stop.getTime() - 10);
        timer.setStartForUnitTest(start);
        timer.setStopForUnitTest(stop);
        assertTrue(timer.isStarted());
        assertEquals(10, timer.getElapsedTime());

        timer = new GwtTimer();
        start = new Date(stop.getTime() - 1000000);
        timer.setStartForUnitTest(start);
        timer.setStopForUnitTest(stop);
        assertTrue(timer.isStarted());
        assertEquals(1000000, timer.getElapsedTime());

        timer = new GwtTimer();
        start = new Date(0);
        timer.setStartForUnitTest(start);
        timer.setStopForUnitTest(stop);
        assertTrue(timer.isStarted());
        assertEquals(stop.getTime(), timer.getElapsedTime());
    }

    /** Spot-check the getElapsedTimeString() method for contrived data. */
    public void testElapsedTimeStringContrived() {
        GwtTimer timer = new GwtTimer();
        Date stop = GwtDateUtils.getCurrentDate();
        Date start = new Date(stop.getTime() - 1);
        timer.setStartForUnitTest(start);
        timer.setStopForUnitTest(stop);
        assertEquals("00:00:00,001", timer.getElapsedTimeString());
    }

}
