/* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 *
 *              C E D A R
 *          S O L U T I O N S       "Software done right."
 *           S O F T W A R E
 *
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 *
 * Copyright (c) 2012-2013 Kenneth J. Pronovici.
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
package com.cedarsolutions.client.gwt.widget;

import com.cedarsolutions.util.gwt.GwtDateUtils;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Window;
import com.mvp4g.client.event.Mvp4gLogger;

/**
 * Custom Mvp4g logger with a couple of different modes.
 *
 * <p>
 * The logger can either write to the GWT log, or can pop an alert
 * for each message.
 * </p>
 *
 * @author Kenneth J. Pronovici <pronovic@ieee.org>
 */
public abstract class GwtCustomLogger implements Mvp4gLogger {

    /** Tabstop to use when logging messages. */
    public static final String TABSTOP = "    ";

    /** Disables logging. */
    public static final String DISABLED = "disabled";

    /** Configures alert logging mode. */
    public static final String ALERT_MODE = "alert";

    /** Configures GWT logging mode. */
    public static final String GWT_MODE = "gwt";

    /** Configured alert mode. */
    private String loggingMode;

    /** Configured logging prefix. */
    private String loggingPrefix;

    /** Log a message. */
    @Override
    public void log(String message, int depth) {
        if (ALERT_MODE.equals(this.loggingMode)) {
            Window.alert(message);
        } else if (GWT_MODE.equals(this.loggingMode)) {
            GWT.log(getTimestamp() + " --> " + this.loggingPrefix + generatePadding(depth) + message);
        }
    }

    /** Generate padding based on a depth. */
    protected static String generatePadding(int depth) {
        StringBuffer buffer = new StringBuffer();

        for (int i = 0; i < depth; i++) {
            buffer.append(TABSTOP);
        }

        return buffer.toString();
    }

    /** Get a timestamp. */
    protected static String getTimestamp() {
        return GwtDateUtils.formatTimestamp(GwtDateUtils.getCurrentDate());
    }

    /** Set the configured logging mode. */
    public void setLoggingMode(String loggingMode) {
        this.loggingMode = loggingMode;
    }

    /** Get the configured logging mode. */
    public String getLoggingMode() {
        return this.loggingMode;
    }

    /** Set the configured logging prefix. */
    public void setLoggingPrefix(String loggingPrefix) {
        this.loggingPrefix = loggingPrefix;
    }

    /** Get the configured logging prefix. */
    public String getLoggingPrefix() {
        return this.loggingPrefix;
    }

}
