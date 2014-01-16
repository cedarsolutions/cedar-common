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
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

/**
 * Unit tests for RootCause.
 * @author Kenneth J. Pronovici <pronovic@ieee.org>
 */
public class RootCauseTest {

    /** Test the constructors. */
    @Test public void testConstructors() {
        RootCause rootCause = new RootCause();
        assertNull(rootCause.getName());
        assertNull(rootCause.getCanonicalName());
        assertNull(rootCause.getSimpleName());
        assertNull(rootCause.getMessage());
        assertNull(rootCause.getLocation());
        assertNull(rootCause.getCause());

        RootCause nested = new RootCause();

        rootCause = new RootCause("a", "b", "c", "d", "e", nested);
        assertEquals("a", rootCause.getName());
        assertEquals("b", rootCause.getCanonicalName());
        assertEquals("c", rootCause.getSimpleName());
        assertEquals("d", rootCause.getMessage());
        assertEquals("e", rootCause.getLocation());
        assertSame(nested, rootCause.getCause());
    }

    /** Test equals(). */
    @Test public void testEquals() {
        RootCause rootCause1;
        RootCause rootCause2;
        RootCause nested = new RootCause();

        rootCause1 = new RootCause("a", "b", "c", "d", "e", nested);
        rootCause2 = new RootCause("a", "b", "c", "d", "e", nested);
        assertTrue(rootCause1.equals(rootCause2));
        assertTrue(rootCause2.equals(rootCause1));

        try {
            rootCause1 = new RootCause("a", "b", "c", "d", "e", nested);
            rootCause2 = null;
            rootCause1.equals(rootCause2);
            fail("Expected NullPointerException");
        } catch (NullPointerException e) { }

        try {
            rootCause1 = new RootCause("a", "b", "c", "d", "e", nested);
            rootCause1.equals("blech");
            fail("Expected ClassCastException");
        } catch (ClassCastException e) { }

        rootCause1 = new RootCause("a", "b", "c", "d", "e", nested);
        rootCause2 = new RootCause("X", "b", "c", "d", "e", nested);
        assertFalse(rootCause1.equals(rootCause2));
        assertFalse(rootCause2.equals(rootCause1));

        rootCause1 = new RootCause("a", "b", "c", "d", "e", nested);
        rootCause2 = new RootCause("a", "X", "c", "d", "e", nested);
        assertFalse(rootCause1.equals(rootCause2));
        assertFalse(rootCause2.equals(rootCause1));

        rootCause1 = new RootCause("a", "b", "c", "d", "e", nested);
        rootCause2 = new RootCause("a", "b", "X", "d", "e", nested);
        assertFalse(rootCause1.equals(rootCause2));
        assertFalse(rootCause2.equals(rootCause1));

        rootCause1 = new RootCause("a", "b", "c", "d", "e", nested);
        rootCause2 = new RootCause("a", "b", "c", "X", "e", nested);
        assertFalse(rootCause1.equals(rootCause2));
        assertFalse(rootCause2.equals(rootCause1));

        rootCause1 = new RootCause("a", "b", "c", "d", "e", nested);
        rootCause2 = new RootCause("a", "b", "c", "d", "X", nested);
        assertFalse(rootCause1.equals(rootCause2));
        assertFalse(rootCause2.equals(rootCause1));

        rootCause1 = new RootCause("a", "b", "c", "d", "e", nested);
        rootCause2 = new RootCause("a", "b", "c", "d", "e", null);
        assertFalse(rootCause1.equals(rootCause2));
        assertFalse(rootCause2.equals(rootCause1));
    }

    /** Test hashCode(). */
    @Test public void testHashCode() {
        RootCause nested = new RootCause();

        RootCause rootCause1 = new RootCause("X", null, null, null, null, null);
        RootCause rootCause2 = new RootCause(null, "X", null, null, null, null);
        RootCause rootCause3 = new RootCause(null, null, "X", null, null, null);
        RootCause rootCause4 = new RootCause(null, null, null, "X", null, null);
        RootCause rootCause5 = new RootCause(null, null, null, null, "X", null);
        RootCause rootCause6 = new RootCause(null, null, null, null, null, nested);
        RootCause rootCause7 = new RootCause("X", null, null, null, null, null);  // same as rootCause1

        Map<RootCause, String> map = new HashMap<RootCause, String>();
        map.put(rootCause1, "ONE");
        map.put(rootCause2, "TWO");
        map.put(rootCause3, "THREE");
        map.put(rootCause4, "FOUR");
        map.put(rootCause5, "FIVE");
        map.put(rootCause6, "SIX");

        assertEquals("ONE", map.get(rootCause1));
        assertEquals("TWO", map.get(rootCause2));
        assertEquals("THREE", map.get(rootCause3));
        assertEquals("FOUR", map.get(rootCause4));
        assertEquals("FIVE", map.get(rootCause5));
        assertEquals("SIX", map.get(rootCause6));
        assertEquals("ONE", map.get(rootCause7));
    }

}
