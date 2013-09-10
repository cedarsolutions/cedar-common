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

import java.util.Date;
import java.util.logging.Formatter;
import java.util.logging.LogRecord;

import org.apache.log4j.Level;

/**
 * Custom Java Logging (JUL) formatter that matches my preferred Log4j format.
 *
 * <p>
 * The goal here is to produce the equivalent of the Log4j conversion
 * pattern "%d{ISO8601} [%-5p] --> [%c] %m%n".
 * </p>
 *
 * @author Kenneth J. Pronovici <pronovic@ieee.org>
 */
public class JavaLoggingFormatter extends Formatter {

    public JavaLoggingFormatter() {
        super();
    }

    @Override
    public String format(LogRecord record) {
        return new LogInfo(record).format();
    }

    /** Log information derived from a JUL LogRecord. */
    protected static class LogInfo {

        private Date date;
        private Level level;
        private String className;
        private String message;

        public LogInfo(LogRecord record) {
            this.date = deriveDate(record);
            this.level = deriveLevel(record);
            this.className = deriveClassName(record);
            this.message = deriveMessage(record);
        }

        public String format() {
            StringBuffer buffer = new StringBuffer();

            buffer.append(DateUtils.formatTimestamp(this.date));

            buffer.append(" [");
            buffer.append(padded(5, this.level.toString()));
            buffer.append("]");

            buffer.append(" --> ");
            buffer.append("[");
            buffer.append(this.className);
            buffer.append("]");

            buffer.append(" ");
            buffer.append(this.message);
            buffer.append(StringUtils.LINE_ENDING);

            return buffer.toString();
        }

        public Date getDate() {
            return this.date;
        }

        public Level getLevel() {
            return this.level;
        }

        public String getClassName() {
            return this.className;
        }

        public String getMessage() {
            return this.message;
        }

        private static Date deriveDate(LogRecord record) {
            return new Date(record.getMillis());
        }

        private static String deriveClassName(LogRecord record) {
            return record.getSourceClassName();
        }

        private static String deriveMessage(LogRecord record) {
            return record.getMessage();
        }

        protected static Level deriveLevel(LogRecord record) {
            if (java.util.logging.Level.OFF.equals(record.getLevel())) {
                return Level.OFF;
            } else if (java.util.logging.Level.SEVERE.equals(record.getLevel())) {
                return Level.ERROR;
            } else if (java.util.logging.Level.WARNING.equals(record.getLevel())) {
                return Level.WARN;
            } else if (java.util.logging.Level.INFO.equals(record.getLevel())) {
                return Level.INFO;
            } else if (java.util.logging.Level.CONFIG.equals(record.getLevel())) {
                return Level.INFO;
            } else if (java.util.logging.Level.FINE.equals(record.getLevel())) {
                return Level.DEBUG;
            } else if (java.util.logging.Level.FINER.equals(record.getLevel())) {
                return Level.DEBUG;
            } else if (java.util.logging.Level.FINEST.equals(record.getLevel())) {
                return Level.TRACE;
            } else if (java.util.logging.Level.ALL.equals(record.getLevel())) {
                return Level.ALL;
            } else {
                return Level.INFO;  // eh, give up and return something marginally sensible
            }
        }

        private static String padded(int length, String data) {
            StringBuffer buffer = new StringBuffer();

            data = data == null ? "" : data;
            data = data.length() > length ? data.substring(0, length) : data;
            buffer.append(data);

            int remaining = length - data.length();
            for (int i = 0; i < remaining; i++) {
                buffer.append(" ");
            }

            return buffer.toString();
        }
    }
}
