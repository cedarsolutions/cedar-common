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
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import org.junit.Test;

import com.cedarsolutions.exception.CedarRuntimeException;
import com.cedarsolutions.exception.context.ExceptionContext;
import com.cedarsolutions.exception.context.RootCause;

/**
 * Unit tests for ExceptionUtils.
 * @author Kenneth J. Pronovici <pronovic@ieee.org>
 */
public class ExceptionUtilsTest {

    /** Test generateStackTrace(). */
    @Test public void testGenerateStackTrace() {
        // Just spot-check, since it's hard to confirm the exact output
        assertEquals(null, ExceptionUtils.generateStackTrace(null));
        assertNotNull(ExceptionUtils.generateStackTrace(new Exception("whatever")));
        assertNotNull(ExceptionUtils.generateStackTrace(new Exception("whatever", new Exception("cause"))));
    }

    /** Test generateRootCause. */
    @Test public void testGenerateRootCause() {
        Throwable nested = new IllegalAccessException("nested");
        Throwable cause1 = new Exception("cause1");
        Throwable cause2 = new Exception("cause2", nested);

        RootCause rootCause = ExceptionUtils.generateRootCause(cause1);
        assertNotNull(rootCause);
        assertEquals(cause1.getClass().getName(), rootCause.getName());
        assertEquals(cause1.getClass().getCanonicalName(), rootCause.getCanonicalName());
        assertEquals(cause1.getClass().getSimpleName(), rootCause.getSimpleName());
        assertEquals(cause1.getMessage(), rootCause.getMessage());
        assertNull(rootCause.getCause());

        rootCause = ExceptionUtils.generateRootCause(cause2);
        assertEquals(cause2.getClass().getName(), rootCause.getName());
        assertEquals(cause2.getClass().getCanonicalName(), rootCause.getCanonicalName());
        assertEquals(cause1.getClass().getSimpleName(), rootCause.getSimpleName());
        assertEquals(cause2.getMessage(), rootCause.getMessage());
        assertNotNull(rootCause.getCause());
        assertEquals(nested.getClass().getName(), rootCause.getCause().getName());
        assertEquals(nested.getClass().getCanonicalName(), rootCause.getCause().getCanonicalName());
        assertEquals(nested.getClass().getSimpleName(), rootCause.getCause().getSimpleName());
        assertEquals(nested.getMessage(), rootCause.getCause().getMessage());
    }

    /** Test generateExceptionContext(). */
    @Test public void testGenerateExceptionContext() {
        // This test is very sensitive to line numbers, so if you edit stuff above (even organize imports), this will fail

        ExceptionContext context;
        String expected;

        context = ExceptionUtils.generateExceptionContext(null, 0);
        assertNull(context);

        Exception exception = new Exception("Hello", null);
        context = ExceptionUtils.generateExceptionContext(exception, 0);
        expected = "com.cedarsolutions.util.ExceptionUtilsTest.testGenerateExceptionContext(ExceptionUtilsTest.java:85)";
        assertEquals(expected, context.getLocation());
        assertNotNull(context.getStackTrace());
        assertNull(context.getRootCause());

        Exception cause = new Exception("I am a cause");
        exception = new Exception("Hello", cause);
        context = ExceptionUtils.generateExceptionContext(exception, 0);
        expected = "com.cedarsolutions.util.ExceptionUtilsTest.testGenerateExceptionContext(ExceptionUtilsTest.java:93)";
        assertEquals(expected, context.getLocation());
        assertNotNull(context.getStackTrace());
        assertEquals(ExceptionUtils.generateRootCause(cause), context.getRootCause());

        // note: line number should be marked as if it came from the line below, not the method call
        exception = createException();
        context = ExceptionUtils.generateExceptionContext(exception, 1);
        expected = "com.cedarsolutions.util.ExceptionUtilsTest.testGenerateExceptionContext(ExceptionUtilsTest.java:101)";
        assertEquals(expected, context.getLocation());
        assertNotNull(context.getStackTrace());
        assertNull(context.getRootCause());
    }

    /** Test addExceptionContext(). */
    @Test public void testAddExceptionContext() {
        Exception cause = new Exception("I am a cause");

        // Just make sure this doesn't blow up
        Exception exception = new Exception("Hello", cause);
        ExceptionUtils.addExceptionContext(exception, 1);

        // Just make sure this doesn't blow up
        CedarRuntimeException cedar = new CedarRuntimeException("Hello", cause);
        assertNull(cedar.getContext());
        ExceptionUtils.addExceptionContext(cedar, 1);
        assertNotNull(cedar.getContext());
    }

    /** Method that creates an exception. */
    private static Exception createException() {
        return new Exception("Hello", null);
    }

}
