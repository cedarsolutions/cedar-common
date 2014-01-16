/* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 *
 *              C E D A R
 *          S O L U T I O N S       "Software done right."
 *           S O F T W A R E
 *
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 *
 * Copyright (c) 2014 Kenneth J. Pronovici.
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
package com.cedarsolutions.exception.context;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

/**
 * Unit tests for ExceptionContext.
 * @author Kenneth J. Pronovici <pronovic@ieee.org>
 */
public class ExceptionContextTest {

    /** Test the constructors. */
    @Test public void testConstructors() {
        ExceptionContext context = new ExceptionContext();
        assertNull(context.getLocation());
        assertNull(context.getRootCause());
        assertNull(context.getStackTrace());
    }

    /** Test equals(). */
    @Test public void testEquals() {
        ExceptionContext context1;
        ExceptionContext context2;

        context1 = createExceptionContext();
        context2 = createExceptionContext();
        assertTrue(context1.equals(context2));
        assertTrue(context2.equals(context1));

        try {
            context1 = createExceptionContext();
            context2 = null;
            context1.equals(context2);
            fail("Expected NullPointerException");
        } catch (NullPointerException e) { }

        try {
            context1 = createExceptionContext();
            context1.equals("blech");
            fail("Expected ClassCastException");
        } catch (ClassCastException e) { }

        context1 = createExceptionContext();
        context2 = createExceptionContext();
        context2.setLocation("X");
        assertFalse(context1.equals(context2));
        assertFalse(context2.equals(context1));

        context1 = createExceptionContext();
        context2 = createExceptionContext();
        context2.setRootCause(null);
        assertFalse(context1.equals(context2));
        assertFalse(context2.equals(context1));

        context1 = createExceptionContext();
        context2 = createExceptionContext();
        context2.setStackTrace("X");
        assertFalse(context1.equals(context2));
        assertFalse(context2.equals(context1));
    }

    /** Test hashCode(). */
    @Test public void testHashCode() {
        ExceptionContext context1 = createExceptionContext();
        context1.setLocation("X");

        ExceptionContext context2 = createExceptionContext();
        context2.setRootCause(null);

        ExceptionContext context3 = createExceptionContext();
        context3.setStackTrace("X");

        ExceptionContext context4 = createExceptionContext();
        context4.setLocation("X");  // same as context1

        Map<ExceptionContext, String> map = new HashMap<ExceptionContext, String>();
        map.put(context1, "ONE");
        map.put(context2, "TWO");
        map.put(context3, "THREE");

        assertEquals("ONE", map.get(context1));
        assertEquals("TWO", map.get(context2));
        assertEquals("THREE", map.get(context3));
        assertEquals("ONE", map.get(context4));
    }

    /** Create an exception context for testing. */
    private static ExceptionContext createExceptionContext() {
        ExceptionContext context = new ExceptionContext();
        context.setLocation("file");
        context.setRootCause(new RootCause());
        context.setStackTrace("stack");
        return context;
    }

}
