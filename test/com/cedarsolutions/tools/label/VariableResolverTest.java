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
package com.cedarsolutions.tools.label;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.Test;

import com.cedarsolutions.exception.InvalidDataException;
import com.cedarsolutions.exception.NotSupportedException;
import com.cedarsolutions.util.DateUtils;


/**
 * Unit tests for VariableResolver.
 * @author Kenneth J. Pronovici <pronovic@ieee.org>
 */
public class VariableResolverTest {

    /** Test the constructors. */
    @Test public void testConstructors() {
        VariableResolver resolver;

        resolver = new VariableResolver(VariableTestResources.class);
        assertNotNull(resolver.constantsWithLookup);
        assertTrue(resolver.constantsWithLookup instanceof VariableTestResources);

        resolver = new VariableResolver("com.cedarsolutions.tools.label.VariableTestResources");
        assertNotNull(resolver.constantsWithLookup);
        assertTrue(resolver.constantsWithLookup instanceof VariableTestResources);

        try {
            new VariableResolver(String.class);
            fail("Expected NotSupportedException");
        } catch (NotSupportedException e) { }

        try {
            new VariableResolver("java.lang.String");
            fail("Expected NotSupportedException");
        } catch (NotSupportedException e) { }

        try {
            new VariableResolver("com.blech.Whatever");
            fail("Expected InvalidDataException");
        } catch (InvalidDataException e) { }
    }

    /** Test expandFormat(), strict=false. */
    @Test public void testExpandFormatStrictFalse() {
        VariableResolver resolver = new VariableResolver(VariableTestResources.class);

        assertEquals(null, resolver.expandFormat(null, false));
        assertEquals("", resolver.expandFormat("", false));
        assertEquals("whatever", resolver.expandFormat("whatever", false));

        assertEquals("whatever_${blech}", resolver.expandFormat("whatever_${blech}", false));
        assertEquals("whatever_${string:blech}", resolver.expandFormat("whatever_${string:blech}", false));
        assertEquals("whatever_${integer:blech}", resolver.expandFormat("whatever_${integer:blech}", false));
        assertEquals("whatever_${boolean:blech}", resolver.expandFormat("whatever_${boolean:blech}", false));

        assertEquals("whatever_string", resolver.expandFormat("whatever_${stringValue}", false));
        assertEquals("whatever_string", resolver.expandFormat("whatever_${string:stringValue}", false));

        assertEquals("whatever_${intValue}", resolver.expandFormat("whatever_${intValue}", false));
        assertEquals("whatever_15", resolver.expandFormat("whatever_${integer:intValue}", false));

        assertEquals("whatever_${booleanValue}", resolver.expandFormat("whatever_${booleanValue}", false));
        assertEquals("whatever_true", resolver.expandFormat("whatever_${boolean:booleanValue}", false));

        String date = DateUtils.formatDate(DateUtils.getCurrentDate());
        assertEquals("whatever_" + date, resolver.expandFormat("whatever_${special:date}", false));

        String time = DateUtils.formatTime(DateUtils.getCurrentDate());
        assertEquals("whatever_" + time, resolver.expandFormat("whatever_${special:time}", false));

        // I don't have any way to know exactly what the timestamp will be, so I'm spot-checking
        // that what I get back looks reasonable.  It should typically start with the same string
        // as the time did (except in rare cases where the time rolls over a minute during execution
        // of this test case).  If that starts to become a problem, I'll just remove these tests.
        assertTrue(resolver.expandFormat("whatever_${special:timestamp}", false).startsWith("whatever_" + time));
        assertTrue(resolver.expandFormat("whatever_${special:ntimestamp}", false)
                   .startsWith(("whatever_" + time).replace("-", "").replace("T", "").replace(":", "")));

        assertEquals("a_string_b_15_c_true", resolver.expandFormat("a_${stringValue}_b_${integer:intValue}_c_${boolean:booleanValue}", false));
    }

    /** Test expandFormat(), strict=true. */
    @Test public void testExpandFormatStrictTrue() {
        VariableResolver resolver = new VariableResolver(VariableTestResources.class);

        assertEquals(null, resolver.expandFormat(null, true));
        assertEquals("", resolver.expandFormat("", true));
        assertEquals("whatever", resolver.expandFormat("whatever", true));

        try {
            resolver.expandFormat("whatever_${blech}", true);
            fail("Expected InvalidDataException");
        } catch (InvalidDataException e) { }

        try {
            resolver.expandFormat("whatever_${string:blech}", true);
            fail("Expected InvalidDataException");
        } catch (InvalidDataException e) { }

        try {
            resolver.expandFormat("whatever_${integer:blech}", true);
            fail("Expected InvalidDataException");
        } catch (InvalidDataException e) { }

        try {
            resolver.expandFormat("whatever_${boolean:blech}", true);
            fail("Expected InvalidDataException");
        } catch (InvalidDataException e) { }

        assertEquals("whatever_string", resolver.expandFormat("whatever_${stringValue}", true));
        assertEquals("whatever_string", resolver.expandFormat("whatever_${string:stringValue}", true));

        try {
            resolver.expandFormat("whatever_${intValue}", true);
            fail("Expected InvalidDataException");
        } catch (InvalidDataException e) { }

        assertEquals("whatever_15", resolver.expandFormat("whatever_${integer:intValue}", true));

        try {
            resolver.expandFormat("whatever_${booleanValue}", true);
            fail("Expected InvalidDataException");
        } catch (InvalidDataException e) { }

        assertEquals("whatever_true", resolver.expandFormat("whatever_${boolean:booleanValue}", true));

        String date = DateUtils.formatDate(DateUtils.getCurrentDate());
        assertEquals("whatever_" + date, resolver.expandFormat("whatever_${special:date}", true));

        String time = DateUtils.formatTime(DateUtils.getCurrentDate());
        assertEquals("whatever_" + time, resolver.expandFormat("whatever_${special:time}", true));
        assertTrue(resolver.expandFormat("whatever_${special:time}", false).startsWith("whatever_" + time));

        assertEquals("a_string_b_15_c_true_d_" + date,
                     resolver.expandFormat("a_${stringValue}_b_${integer:intValue}_c_${boolean:booleanValue}_d_${special:date}", true));
    }

}
