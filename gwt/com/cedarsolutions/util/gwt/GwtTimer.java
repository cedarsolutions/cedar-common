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

import java.io.Serializable;
import java.util.Date;

import com.cedarsolutions.exception.CedarRuntimeException;

/**
 * Measures elapsed time.
 *
 * <p>
 * Basic usage is pretty simple:
 * </p>
 *
 * <pre>
 *    GwtTimer timer = new GwtTimer();
 *    timer.start();
 *    // do some stuff
 *    timer.stop();
 *    System.out.println("Elapsed time: " + timer.getElapsedTimeString());
 * </pre>
 *
 * <p>
 * Just keep in mind that the timer must be stopped before
 * you ask for its elapsed time.  It's legal to stop the
 * timer more than once: just call stop() each time you need
 * to see how long the timer has been running.
 * </p>
 *
 * <p>
 * Note that this implementation relies on GWT functionality that
 * is only available in the client runtime.  You can't execute all
 * of these methods from normal Java code, only GWT client code.
 * Normal Java code should use the standard Timer class, which
 * provides similar functionality.
 * </p>
 *
 * @author Kenneth J. Pronovici <pronovic@ieee.org>
 */
public class GwtTimer implements Serializable {

    /** Serialization version number, which can be important to the GAE back-end. */
    private static final long serialVersionUID = 1L;

    /** Start timestamp. */
    private Date start = null;

    /** Stop timestamp. */
    private Date stop = null;

    /** Create a timer. */
    public GwtTimer() {
        this(null, null);
    }

    /** Create a timer with a start timestamp, generally only useful for unit tests. */
    public GwtTimer(Date start) {
        this(start, null);
    }

    /** Create an timer with a start and stop timestamp, generally only useful for unit tests. */
    public GwtTimer(Date start, Date stop) {
        this.start = start;
        this.stop = stop;
    }

    /** Clear the timer, resetting the start and stop timestamp. */
    public synchronized void clear() {
        this.start = null;
        this.stop = null;
    }

    /** Start the timer. */
    public synchronized void start() {
        this.start = GwtDateUtils.getCurrentDate();
    }

    /** Stop the timer. */
    public synchronized void stop() {
        this.stop = GwtDateUtils.getCurrentDate();
    }

    /** Whether the timer has been started. */
    public synchronized boolean isStarted() {
        return this.getStart() != null;
    }

    /** Whether the timer has been stopped. */
    public synchronized boolean isStopped() {
        return this.getStop() != null;
    }

    /** Get the start timestamp. */
    public synchronized Date getStart() {
        return this.start;
    }

    /** Get the stop timestamp. */
    public synchronized Date getStop() {
        return this.stop;
    }

    /**
     * Get the elapsed time as a timestamp.
     * @return Timestamp as from Date.getTime().
     * @throws CedarRuntimeException If the timer has not been properly started and stopped.
     */
    public synchronized long getElapsedTime() {
        try {
            long elapsedTime = this.stop.getTime() - this.start.getTime();
            return elapsedTime < 0 ? 0 : elapsedTime;
        } catch (NullPointerException e) {
            throw new CedarRuntimeException("Timer has not been properly started and stopped.");
        }
    }

    /**
     * Get the elapsed time as a legible string.
     * @return String as from GwtDateUtils.formatElapsedTime().
     * @throws CedarRuntimeException If the timer has not been properly started and stopped.
     */
    public String getElapsedTimeString() {
        long elapsedTime = this.getElapsedTime();
        return GwtDateUtils.formatElapsedTime(elapsedTime);
    }

    /** Set the start timestamp, overriding the start() method, intended for use with unit tests ONLY. */
    public synchronized void setStartForUnitTest(Date start) {
        this.start = start;
    }

    /** Set the stop timestamp, overriding the stop() method, intended for use with for unit tests ONLY. */
    public synchronized void setStopForUnitTest(Date stop) {
        this.stop = stop;
    }

}
