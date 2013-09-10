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

/**
 * Exception utilities that are translatable to GWT client code.
 *
 * <p>
 * This class provides functionality similar to ExceptionUtils. We can't use
 * ExceptionUtils in GWT client code because a lot of the required classes (i.e.
 * ByteArrayOutputStream) are not available on the client side.
 * </p>
 *
 * <p>
 * What you see below is a mostly-equivalent implementation, except the output
 * isn't as pretty. For instance, when there are nested causes or duplicated
 * stack traces, the standard Java behavior gives you something like
 * "...19 more" rather than repeating the duplicates. The code below is not
 * that smart.
 * </p>
 *
 * @author Kenneth J. Pronovici <pronovic@ieee.org>
 */
public class GwtExceptionUtils {

    /**
     * Generate a stack trace for an exception.
     * @param exception  Exception to generate the stack trace for
     * @return Stack trace as a string.
     */
    public static String generateStackTrace(Throwable exception) {
        if (exception == null) {
            return "null";
        } else {
            StringBuffer buffer = new StringBuffer();

            buffer.append(exception.getClass().getName());
            buffer.append(": ");
            buffer.append(exception.getMessage());
            buffer.append("\n");
            buffer.append(generateStackTrace(exception.getStackTrace()));

            if (exception.getCause() != null) {
                buffer.append("Caused by: ");
                buffer.append(generateStackTrace(exception.getCause()));
            }

            return buffer.toString();
        }
    }

    /**
     * Format a string based on Exception.getStackTrace().
     * @param stackTrace  Stack trace array
     * @return Formatted string.
     */
    private static String generateStackTrace(Object[] stackTrace) {
        StringBuffer buffer = new StringBuffer();

        for (Object line : stackTrace) {
            buffer.append("  at ");
            buffer.append(line);
            buffer.append("\n");
        }

        return buffer.toString();
    }

}
