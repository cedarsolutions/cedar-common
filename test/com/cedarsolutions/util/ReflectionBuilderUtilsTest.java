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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

/**
 * Unit tests for ToStringUtils.
 * @author Kenneth J. Pronovici <pronovic@ieee.org>
 */
public class ReflectionBuilderUtilsTest {

    /** Test the mechanism to set the default string style. */
    @Test public void testDefaultStringStyle() {
        StringStyle originalStyle = ReflectionBuilderUtils.getDefaultStringStyle();
        try {
            for (StringStyle style : StringStyle.values()) {
                ReflectionBuilderUtils.setDefaultStringStyle(style);
                assertEquals(style, ReflectionBuilderUtils.getDefaultStringStyle());
                assertNotNull(ReflectionBuilderUtils.generateToString(new Integer(6)));  // just make sure it doesn't blow up
            }
        } finally {
            ReflectionBuilderUtils.setDefaultStringStyle(originalStyle);
        }
    }

    /** Spot-check toString(). */
    @Test public void testToString() {
        ReflectionBuilderUtilsTestClass obj = new ReflectionBuilderUtilsTestClass();
        assertNotNull(ReflectionBuilderUtils.generateToString(obj));
    }

    /** Spot-check equals(), no exclusions. */
    @Test public void testEqualsNoExclusions() {
        ReflectionBuilderUtilsTestClass obj1 = new ReflectionBuilderUtilsTestClass("valueA", "valueC");
        ReflectionBuilderUtilsTestClass obj2 = new ReflectionBuilderUtilsTestClass("valueB", "valueC");
        ReflectionBuilderUtilsTestClass obj3 = new ReflectionBuilderUtilsTestClass("valueA", "valueC");
        ReflectionBuilderUtilsTestClass obj4 = new ReflectionBuilderUtilsTestClass("valueA", "valueD");

        assertFalse(ReflectionBuilderUtils.generateEquals(obj1, (Integer) null));
        assertFalse(ReflectionBuilderUtils.generateEquals(obj1, new Integer(1)));
        assertTrue(ReflectionBuilderUtils.generateEquals(obj1, obj1));
        assertFalse(ReflectionBuilderUtils.generateEquals(obj1, obj2));
        assertTrue(ReflectionBuilderUtils.generateEquals(obj1, obj3));
        assertFalse(ReflectionBuilderUtils.generateEquals(obj1, obj4));

        assertFalse(ReflectionBuilderUtils.generateEquals(obj2, (Integer) null));
        assertFalse(ReflectionBuilderUtils.generateEquals(obj2, new Integer(1)));
        assertFalse(ReflectionBuilderUtils.generateEquals(obj2, obj1));
        assertTrue(ReflectionBuilderUtils.generateEquals(obj2, obj2));
        assertFalse(ReflectionBuilderUtils.generateEquals(obj2, obj3));
        assertFalse(ReflectionBuilderUtils.generateEquals(obj2, obj4));

        assertFalse(ReflectionBuilderUtils.generateEquals(obj3, (Integer) null));
        assertFalse(ReflectionBuilderUtils.generateEquals(obj3, new Integer(1)));
        assertTrue(ReflectionBuilderUtils.generateEquals(obj3, obj1));
        assertFalse(ReflectionBuilderUtils.generateEquals(obj3, obj2));
        assertTrue(ReflectionBuilderUtils.generateEquals(obj3, obj3));
        assertFalse(ReflectionBuilderUtils.generateEquals(obj3, obj4));
    }

    /** Spot-check equals(), with exclusions. */
    @Test public void testEqualsWithExclusions() {
        String[] exclude = new String[] { "two", };

        ReflectionBuilderUtilsTestClass obj1 = new ReflectionBuilderUtilsTestClass("valueA", "valueC");
        ReflectionBuilderUtilsTestClass obj2 = new ReflectionBuilderUtilsTestClass("valueB", "valueC");
        ReflectionBuilderUtilsTestClass obj3 = new ReflectionBuilderUtilsTestClass("valueA", "valueC");
        ReflectionBuilderUtilsTestClass obj4 = new ReflectionBuilderUtilsTestClass("valueA", "valueD");

        assertFalse(ReflectionBuilderUtils.generateEquals(obj1, (Integer) null, exclude));
        assertFalse(ReflectionBuilderUtils.generateEquals(obj1, new Integer(1), exclude));
        assertTrue(ReflectionBuilderUtils.generateEquals(obj1, obj1, exclude));
        assertFalse(ReflectionBuilderUtils.generateEquals(obj1, obj2, exclude));
        assertTrue(ReflectionBuilderUtils.generateEquals(obj1, obj3, exclude));
        assertTrue(ReflectionBuilderUtils.generateEquals(obj1, obj4, exclude));

        assertFalse(ReflectionBuilderUtils.generateEquals(obj2, (Integer) null, exclude));
        assertFalse(ReflectionBuilderUtils.generateEquals(obj2, new Integer(1), exclude));
        assertFalse(ReflectionBuilderUtils.generateEquals(obj2, obj1, exclude));
        assertTrue(ReflectionBuilderUtils.generateEquals(obj2, obj2, exclude));
        assertFalse(ReflectionBuilderUtils.generateEquals(obj2, obj3, exclude));
        assertFalse(ReflectionBuilderUtils.generateEquals(obj2, obj4, exclude));

        assertFalse(ReflectionBuilderUtils.generateEquals(obj3, (Integer) null, exclude));
        assertFalse(ReflectionBuilderUtils.generateEquals(obj3, new Integer(1), exclude));
        assertTrue(ReflectionBuilderUtils.generateEquals(obj3, obj1, exclude));
        assertFalse(ReflectionBuilderUtils.generateEquals(obj3, obj2, exclude));
        assertTrue(ReflectionBuilderUtils.generateEquals(obj3, obj3, exclude));
        assertTrue(ReflectionBuilderUtils.generateEquals(obj3, obj4, exclude));
    }

    /** Spot-check hashCode(), no exclusions. */
    @Test public void testHashCodeNoExclusions() {
        ReflectionBuilderUtilsTestClass obj1 = new ReflectionBuilderUtilsTestClass("valueA", "valueC");
        ReflectionBuilderUtilsTestClass obj2 = new ReflectionBuilderUtilsTestClass("valueB", "valueC");
        ReflectionBuilderUtilsTestClass obj3 = new ReflectionBuilderUtilsTestClass("valueA", "valueC");
        ReflectionBuilderUtilsTestClass obj4 = new ReflectionBuilderUtilsTestClass("valueA", "valueD");

        assertTrue(ReflectionBuilderUtils.generateHashCode(obj1) != ReflectionBuilderUtils.generateHashCode(obj2));
        assertTrue(ReflectionBuilderUtils.generateHashCode(obj1) == ReflectionBuilderUtils.generateHashCode(obj3));
        assertTrue(ReflectionBuilderUtils.generateHashCode(obj1) != ReflectionBuilderUtils.generateHashCode(obj4));
        assertTrue(ReflectionBuilderUtils.generateHashCode(obj2) != ReflectionBuilderUtils.generateHashCode(obj3));
        assertTrue(ReflectionBuilderUtils.generateHashCode(obj2) != ReflectionBuilderUtils.generateHashCode(obj4));
        assertTrue(ReflectionBuilderUtils.generateHashCode(obj3) != ReflectionBuilderUtils.generateHashCode(obj4));
    }

    /** Spot-check hashCode(), with exclusions. */
    @Test public void testHashCodeWithExclusions() {
        String[] exclude = new String[] { "two", };

        ReflectionBuilderUtilsTestClass obj1 = new ReflectionBuilderUtilsTestClass("valueA", "valueC");
        ReflectionBuilderUtilsTestClass obj2 = new ReflectionBuilderUtilsTestClass("valueB", "valueC");
        ReflectionBuilderUtilsTestClass obj3 = new ReflectionBuilderUtilsTestClass("valueA", "valueC");
        ReflectionBuilderUtilsTestClass obj4 = new ReflectionBuilderUtilsTestClass("valueA", "valueD");

        assertTrue(ReflectionBuilderUtils.generateHashCode(obj1, exclude) != ReflectionBuilderUtils.generateHashCode(obj2, exclude));
        assertTrue(ReflectionBuilderUtils.generateHashCode(obj1, exclude) == ReflectionBuilderUtils.generateHashCode(obj3, exclude));
        assertTrue(ReflectionBuilderUtils.generateHashCode(obj1, exclude) == ReflectionBuilderUtils.generateHashCode(obj4, exclude));
        assertTrue(ReflectionBuilderUtils.generateHashCode(obj2, exclude) != ReflectionBuilderUtils.generateHashCode(obj3, exclude));
        assertTrue(ReflectionBuilderUtils.generateHashCode(obj2, exclude) != ReflectionBuilderUtils.generateHashCode(obj4, exclude));
        assertTrue(ReflectionBuilderUtils.generateHashCode(obj3, exclude) == ReflectionBuilderUtils.generateHashCode(obj4, exclude));
    }

    /** Class we can use for testing (has to be public so reflection builders work properly). */
    public class ReflectionBuilderUtilsTestClass {
        private String one;
        private String two;

        public ReflectionBuilderUtilsTestClass() {
        }

        public ReflectionBuilderUtilsTestClass(String one, String two) {
            this.one = one;
            this.two = two;
        }

        public String getOne() {
            return one;
        }

        public void setOne(String one) {
            this.one = one;
        }

        public String getTwo() {
            return two;
        }

        public void setTwo(String two) {
            this.two = two;
        }
    }

}
