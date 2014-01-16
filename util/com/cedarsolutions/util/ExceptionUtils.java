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

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;

import com.cedarsolutions.exception.RootCause;

/**
 * Exception utilities.
 * @author Kenneth J. Pronovici <pronovic@ieee.org>
 */
public class ExceptionUtils {

    /**
     * Generate a stack trace for an exception.
     * @param exception  Exception to generate the stack trace for
     * @return Stack trace as a string.
     */
    public static String generateStackTrace(Throwable exception) {
        String stackTrace = null;

        if (exception != null) {
            OutputStream stream = null;
            PrintWriter writer = null;

            try {
                stream = new ByteArrayOutputStream();
                writer = new PrintWriter(stream);
                exception.printStackTrace(writer);
                writer.flush();
                stream.flush();
                stackTrace = stream.toString();
            } catch (IOException e) {
                // hopefully shouldn't ever happen?
            } finally {
                try {
                    if (writer != null) {
                        writer.close();
                    }
                } catch (Exception e) { }

                try {
                    if (stream != null) {
                        stream.close();
                    }
                } catch (Exception e) { }
            }
        }

        return stackTrace == null ? "null" : stackTrace;
    }

    /**
     * Create a root cause based on a Throwable exception.
     * @param exception  Exception to use as source
     * @return ExceptionCause generated from the input cause.
     */
    public static RootCause createRootCause(Throwable exception) {
        if (exception == null) {
            return null;
        } else {
            String name = exception.getClass().getName();
            String canonicalName = exception.getClass().getCanonicalName();
            String simpleName = exception.getClass().getSimpleName();
            String message = exception.getMessage();
            String location = getLocation(exception);
            String stackTrace = generateStackTrace(exception);
            RootCause cause = createRootCause(exception.getCause());
            return new RootCause(name, canonicalName, simpleName, message, location, stackTrace, cause);
        }
    }

    /** Create a location string like "com.cedarsolutions.whatever.MyClass.myMethod(MyClass.java:14)". */
    private static String getLocation(Throwable exception) {
        String fileName = exception.getStackTrace()[0].getFileName();
        String lineNumber = String.valueOf(exception.getStackTrace()[0].getLineNumber());
        String className = exception.getStackTrace()[0].getClassName();
        String methodName = exception.getStackTrace()[0].getMethodName();
        return className + "." + methodName + "(" + fileName + ":" + lineNumber + ")";
    }

}

