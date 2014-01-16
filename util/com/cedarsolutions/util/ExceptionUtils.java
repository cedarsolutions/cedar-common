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

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;

import com.cedarsolutions.exception.context.ExceptionContext;
import com.cedarsolutions.exception.context.IHasExceptionContext;
import com.cedarsolutions.exception.context.RootCause;

/**
 * Server-side exception utilities.
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

        if (stackTrace == null) {
            return null;
        } else {
            stackTrace = stackTrace.replaceAll("\tat", "  at");  // shrink leading indentation
            return stackTrace;
        }
    }

    /**
     * Add exception context to an exception which supports it.
     * @param exception  Exception to modify
     * @param level      Number of stack levels above the exception instantiation level to take the line number from
     * @return The same exception, for convenience.
     */
    @SuppressWarnings("unchecked")
    public static <T> T addExceptionContext(Throwable exception, int level) {
        if (exception instanceof IHasExceptionContext) {
            IHasExceptionContext hasContext = (IHasExceptionContext) exception;
            ExceptionContext context = generateExceptionContext(exception, level);
            hasContext.setContext(context);
        }

        return (T) exception;
    }

    /** Generate the exception context for an exception. */
    public static ExceptionContext generateExceptionContext(Throwable exception, int level) {
        if (exception == null) {
            return null;
        } else {
            ExceptionContext context = new ExceptionContext();

            context.setLocation(getLocation(exception, level));
            context.setStackTrace(generateStackTrace(exception));
            context.setRootCause(generateRootCause(exception.getCause()));

            return context;
        }
    }

    /**
     * Create a root cause based on a Throwable exception.
     * @param exception  Exception to use as source
     * @return ExceptionCause generated from the input cause.
     */
    public static RootCause generateRootCause(Throwable exception) {
        if (exception == null) {
            return null;
        } else {
            String name = exception.getClass().getName();
            String canonicalName = exception.getClass().getCanonicalName();
            String simpleName = exception.getClass().getSimpleName();
            String message = exception.getMessage();
            String location = getLocation(exception);
            RootCause cause = generateRootCause(exception.getCause());
            return new RootCause(name, canonicalName, simpleName, message, location, cause);
        }
    }

    /** Create a location string describing where the exception was instantiated. */
    private static String getLocation(Throwable exception) {
        return getLocation(exception, 0);
    }

    /**
     * Create a location string describing where the exception was instantated.
     * @param level  Number of levels back to look, zero being the location, 1 being the caller, etc.
     * @return A string like "com.cedarsolutions.whatever.MyClass.myMethod(MyClass.java:14)"
     */
    private static String getLocation(Throwable exception, int level) {
        try {
            return getLocation(exception.getStackTrace()[level]);
        } catch (IndexOutOfBoundsException e) {
            return null;
        }
    }

    /** Create a location string like "com.cedarsolutions.whatever.MyClass.myMethod(MyClass.java:14)". */
    private static String getLocation(StackTraceElement element) {
        String fileName = element.getFileName();
        String lineNumber = String.valueOf(element.getLineNumber());
        String className = element.getClassName();
        String methodName = element.getMethodName();
        return className + "." + methodName + "(" + fileName + ":" + lineNumber + ")";
    }

}

