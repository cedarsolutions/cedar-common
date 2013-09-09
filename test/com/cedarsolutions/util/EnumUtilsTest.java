/* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 *
 *              C E D A R
 *          S O L U T I O N S       "Software done right."
 *           S O F T W A R E
 *
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 *
 * Copyright (c) 2011-2012 Kenneth J. Pronovici.
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
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.Test;

import com.cedarsolutions.exception.EnumException;
import com.cedarsolutions.shared.domain.IntegerEnum;
import com.cedarsolutions.shared.domain.StringEnum;

/**
 * Unit tests for EnumUtils.
 * @author Kenneth J. Pronovici <pronovic@ieee.org>
 */
public class EnumUtilsTest {

    /** Test getEnum() for invalid (non-enum) classes. */
    @Test public void testGetEnumInvalidInput() {
        try {
            EnumUtils.getEnum(StandardEnum.class, "Hello");
        } catch (EnumException e) { }

        try {
            EnumUtils.getEnum(IntegerOneNotNullEnum.class, "Hello");
        } catch (EnumException e) { }

        try {
            EnumUtils.getEnum(StandardEnum.class, new Integer(2));
        } catch (EnumException e) { }

        try {
            EnumUtils.getEnum(StringOneNotNullEnum.class, new Integer(2));
        } catch (EnumException e) { }
    }

    /** Test isValid() for invalid (non-enum) classes. */
    @Test public void testIsValidInvalidInput() {
        try {
            EnumUtils.isValid(StandardEnum.class, "Hello");
        } catch (EnumException e) { }

        try {
            EnumUtils.isValid(IntegerOneNotNullEnum.class, "Hello");
        } catch (EnumException e) { }

        try {
            EnumUtils.isValid(StandardEnum.class, new Integer(2));
        } catch (EnumException e) { }

        try {
            EnumUtils.isValid(StringOneNotNullEnum.class, new Integer(2));
        } catch (EnumException e) { }
    }

    /** Test String toEnum() on an enumeration with only one value (not null). */
    @Test public void testStringEnumValueOneNotNull() throws Exception {
        assertEquals(StringOneNotNullEnum.ONE, StringOneNotNullEnum.getEnum("1"));

        try {
            StringOneNotNullEnum.getEnum(null);
            fail("Expected EnumException");
        } catch (EnumException e) { }

        try {
            StringOneNotNullEnum.getEnum("A");
            fail("Expected EnumException");
        } catch (EnumException e) { }
    }

    /** Test String toEnum() on an enumeration with only one value (null). */
    @Test public void testStringEnumValueOneNull() throws Exception {
        assertEquals(StringOneNullEnum.UNKNOWN, StringOneNullEnum.getEnum("1"));
        assertEquals(StringOneNullEnum.UNKNOWN, StringOneNullEnum.getEnum(null));
        assertEquals(StringOneNullEnum.UNKNOWN, StringOneNullEnum.getEnum("A"));
    }

    /** Test String toEnum() on an enumeration with multiple values (none null). */
    @Test public void testStringEnumValueMultipleNoneNull() throws Exception {
        assertEquals(StringMultipleNoneNullEnum.ONE, StringMultipleNoneNullEnum.getEnum("1"));
        assertEquals(StringMultipleNoneNullEnum.TWO, StringMultipleNoneNullEnum.getEnum("2"));
        assertEquals(StringMultipleNoneNullEnum.THREE, StringMultipleNoneNullEnum.getEnum("3"));
        assertEquals(StringMultipleNoneNullEnum.FOUR, StringMultipleNoneNullEnum.getEnum("4"));

        try {
            StringMultipleNoneNullEnum.getEnum(null);
            fail("Expected EnumException");
        } catch (EnumException e) { }

        try {
            StringMultipleNoneNullEnum.getEnum("A");
            fail("Expected EnumException");
        } catch (EnumException e) { }
    }

    /** Test String toEnum() on an enumeration with multiple values (one null). */
    @Test public void testStringEnumValueMultipleOneNull() throws Exception {
        assertEquals(StringMultipleOneNullEnum.ONE, StringMultipleOneNullEnum.getEnum("1"));
        assertEquals(StringMultipleOneNullEnum.TWO, StringMultipleOneNullEnum.getEnum("2"));
        assertEquals(StringMultipleOneNullEnum.THREE, StringMultipleOneNullEnum.getEnum("3"));
        assertEquals(StringMultipleOneNullEnum.FOUR, StringMultipleOneNullEnum.getEnum("4"));
        assertEquals(StringMultipleOneNullEnum.UNKNOWN, StringMultipleOneNullEnum.getEnum(null));
        assertEquals(StringMultipleOneNullEnum.UNKNOWN, StringMultipleOneNullEnum.getEnum("A"));
    }

    /** Test Integer toEnum() on an enumeration with only one value (not null). */
    @Test public void testIntegerEnumValueOneNotNull() throws Exception {
        assertEquals(IntegerOneNotNullEnum.ONE, IntegerOneNotNullEnum.getEnum(1));
        assertEquals(IntegerOneNotNullEnum.ONE, IntegerOneNotNullEnum.getEnum("1"));

        try {
            IntegerOneNotNullEnum.getEnum((Integer) null);
            fail("Expected EnumException");
        } catch (EnumException e) { }

        try {
            IntegerOneNotNullEnum.getEnum((String) null);
            fail("Expected EnumException");
        } catch (EnumException e) { }

        try {
            IntegerOneNotNullEnum.getEnum(0xA);
            fail("Expected EnumException");
        } catch (EnumException e) { }

        try {
            IntegerOneNotNullEnum.getEnum("A");
            fail("Expected EnumException");
        } catch (EnumException e) { }

    }

    /** Test Integer toEnum() on an enumeration with only one value (null). */
    @Test public void testIntegerEnumValueOneNull() throws Exception {
        assertEquals(IntegerOneNullEnum.UNKNOWN, IntegerOneNullEnum.getEnum(1));
        assertEquals(IntegerOneNullEnum.UNKNOWN, IntegerOneNullEnum.getEnum((Integer) null));
        assertEquals(IntegerOneNullEnum.UNKNOWN, IntegerOneNullEnum.getEnum(0xA));

        assertEquals(IntegerOneNullEnum.UNKNOWN, IntegerOneNullEnum.getEnum("1"));
        assertEquals(IntegerOneNullEnum.UNKNOWN, IntegerOneNullEnum.getEnum((String) null));
        assertEquals(IntegerOneNullEnum.UNKNOWN, IntegerOneNullEnum.getEnum("A"));
    }

    /** Test Integer toEnum() on an enumeration with multiple values (none null). */
    @Test public void testIntegerEnumValueMultipleNoneNull() throws Exception {
        assertEquals(IntegerMultipleNoneNullEnum.ONE, IntegerMultipleNoneNullEnum.getEnum(1));
        assertEquals(IntegerMultipleNoneNullEnum.TWO, IntegerMultipleNoneNullEnum.getEnum(2));
        assertEquals(IntegerMultipleNoneNullEnum.THREE, IntegerMultipleNoneNullEnum.getEnum(3));
        assertEquals(IntegerMultipleNoneNullEnum.FOUR, IntegerMultipleNoneNullEnum.getEnum(4));

        assertEquals(IntegerMultipleNoneNullEnum.ONE, IntegerMultipleNoneNullEnum.getEnum("1"));
        assertEquals(IntegerMultipleNoneNullEnum.TWO, IntegerMultipleNoneNullEnum.getEnum("2"));
        assertEquals(IntegerMultipleNoneNullEnum.THREE, IntegerMultipleNoneNullEnum.getEnum("3"));
        assertEquals(IntegerMultipleNoneNullEnum.FOUR, IntegerMultipleNoneNullEnum.getEnum("4"));

        try {
            IntegerMultipleNoneNullEnum.getEnum((Integer) null);
            fail("Expected EnumException");
        } catch (EnumException e) { }

        try {
            IntegerMultipleNoneNullEnum.getEnum((String) null);
            fail("Expected EnumException");
        } catch (EnumException e) { }

        try {
            IntegerMultipleNoneNullEnum.getEnum(0xA);
            fail("Expected EnumException");
        } catch (EnumException e) { }

        try {
            IntegerMultipleNoneNullEnum.getEnum("A");
            fail("Expected EnumException");
        } catch (EnumException e) { }
    }

    /** Test Integer toEnum() on an enumeration with multiple values (one null). */
    @Test public void testIntegerEnumValueMultipleOneNull() throws Exception {
        assertEquals(IntegerMultipleOneNullEnum.ONE, IntegerMultipleOneNullEnum.getEnum(1));
        assertEquals(IntegerMultipleOneNullEnum.TWO, IntegerMultipleOneNullEnum.getEnum(2));
        assertEquals(IntegerMultipleOneNullEnum.THREE, IntegerMultipleOneNullEnum.getEnum(3));
        assertEquals(IntegerMultipleOneNullEnum.FOUR, IntegerMultipleOneNullEnum.getEnum(4));
        assertEquals(IntegerMultipleOneNullEnum.UNKNOWN, IntegerMultipleOneNullEnum.getEnum((Integer) null));
        assertEquals(IntegerMultipleOneNullEnum.UNKNOWN, IntegerMultipleOneNullEnum.getEnum(0xA));

        assertEquals(IntegerMultipleOneNullEnum.ONE, IntegerMultipleOneNullEnum.getEnum("1"));
        assertEquals(IntegerMultipleOneNullEnum.TWO, IntegerMultipleOneNullEnum.getEnum("2"));
        assertEquals(IntegerMultipleOneNullEnum.THREE, IntegerMultipleOneNullEnum.getEnum("3"));
        assertEquals(IntegerMultipleOneNullEnum.FOUR, IntegerMultipleOneNullEnum.getEnum("4"));
        assertEquals(IntegerMultipleOneNullEnum.UNKNOWN, IntegerMultipleOneNullEnum.getEnum((String) null));
        assertEquals(IntegerMultipleOneNullEnum.UNKNOWN, IntegerMultipleOneNullEnum.getEnum("A"));
    }

    /** Test isValid() on an enumeration with only one value (not null). */
    @Test public void testIsValidOneNotNull() throws Exception {
        assertTrue(EnumUtils.isValid(StringOneNotNullEnum.class, "1"));
        assertFalse(EnumUtils.isValid(StringOneNotNullEnum.class, (String) null));
        assertFalse(EnumUtils.isValid(StringOneNotNullEnum.class, (Integer) null));
        assertFalse(EnumUtils.isValid(StringOneNotNullEnum.class, "A"));
        assertFalse(EnumUtils.isValid(StringOneNotNullEnum.class, 0xA));

        assertTrue(EnumUtils.isValid(IntegerOneNotNullEnum.class, "1"));
        assertTrue(EnumUtils.isValid(IntegerOneNotNullEnum.class, 1));
        assertFalse(EnumUtils.isValid(IntegerOneNotNullEnum.class, (String) null));
        assertFalse(EnumUtils.isValid(IntegerOneNotNullEnum.class, (Integer) null));
        assertFalse(EnumUtils.isValid(IntegerOneNotNullEnum.class, "A"));
        assertFalse(EnumUtils.isValid(IntegerOneNotNullEnum.class, 0xA));
    }

    /** Test isValid() on an enumeration with only one value (null). */
    @Test public void testIsValidOneNull() throws Exception {
        assertFalse(EnumUtils.isValid(StringOneNullEnum.class, "1"));
        assertFalse(EnumUtils.isValid(StringOneNullEnum.class, (String) null));
        assertFalse(EnumUtils.isValid(StringOneNullEnum.class, (Integer) null));
        assertFalse(EnumUtils.isValid(StringOneNullEnum.class, "A"));
        assertFalse(EnumUtils.isValid(StringOneNullEnum.class, 0xA));

        assertFalse(EnumUtils.isValid(IntegerOneNullEnum.class, "1"));
        assertFalse(EnumUtils.isValid(IntegerOneNullEnum.class, 1));
        assertFalse(EnumUtils.isValid(IntegerOneNullEnum.class, (Integer) null));
        assertFalse(EnumUtils.isValid(IntegerOneNullEnum.class, (Integer) null));
        assertFalse(EnumUtils.isValid(IntegerOneNullEnum.class, "A"));
        assertFalse(EnumUtils.isValid(IntegerOneNullEnum.class, 0xA));
    }

    /** Test isValid() on an enumeration with multiple values (none null). */
    @Test public void testIsValidMultipleNoneNull() throws Exception {
        assertTrue(EnumUtils.isValid(StringMultipleNoneNullEnum.class, "1"));
        assertTrue(EnumUtils.isValid(StringMultipleNoneNullEnum.class, "2"));
        assertTrue(EnumUtils.isValid(StringMultipleNoneNullEnum.class, "3"));
        assertTrue(EnumUtils.isValid(StringMultipleNoneNullEnum.class, "4"));
        assertFalse(EnumUtils.isValid(StringMultipleNoneNullEnum.class, (String) null));
        assertFalse(EnumUtils.isValid(StringMultipleNoneNullEnum.class, (Integer) null));
        assertFalse(EnumUtils.isValid(StringMultipleNoneNullEnum.class, "A"));
        assertFalse(EnumUtils.isValid(StringMultipleNoneNullEnum.class, 0xA));

        assertTrue(EnumUtils.isValid(IntegerMultipleNoneNullEnum.class, "1"));
        assertTrue(EnumUtils.isValid(IntegerMultipleNoneNullEnum.class, "2"));
        assertTrue(EnumUtils.isValid(IntegerMultipleNoneNullEnum.class, "3"));
        assertTrue(EnumUtils.isValid(IntegerMultipleNoneNullEnum.class, "4"));
        assertTrue(EnumUtils.isValid(IntegerMultipleNoneNullEnum.class, 1));
        assertTrue(EnumUtils.isValid(IntegerMultipleNoneNullEnum.class, 2));
        assertTrue(EnumUtils.isValid(IntegerMultipleNoneNullEnum.class, 3));
        assertTrue(EnumUtils.isValid(IntegerMultipleNoneNullEnum.class, 4));
        assertFalse(EnumUtils.isValid(IntegerMultipleNoneNullEnum.class, (String) null));
        assertFalse(EnumUtils.isValid(IntegerMultipleNoneNullEnum.class, (Integer) null));
        assertFalse(EnumUtils.isValid(IntegerMultipleNoneNullEnum.class, "A"));
        assertFalse(EnumUtils.isValid(IntegerMultipleNoneNullEnum.class, 0xA));
    }

    /** Test isValid() on an enumeration with multiple values (one null). */
    @Test public void testIsValidMultipleOneNull() throws Exception {
        assertTrue(EnumUtils.isValid(StringMultipleOneNullEnum.class, "1"));
        assertTrue(EnumUtils.isValid(StringMultipleOneNullEnum.class, "2"));
        assertTrue(EnumUtils.isValid(StringMultipleOneNullEnum.class, "3"));
        assertTrue(EnumUtils.isValid(StringMultipleOneNullEnum.class, "4"));
        assertFalse(EnumUtils.isValid(StringMultipleOneNullEnum.class, (String) null));
        assertFalse(EnumUtils.isValid(StringMultipleOneNullEnum.class, (Integer) null));
        assertFalse(EnumUtils.isValid(StringMultipleOneNullEnum.class, "A"));
        assertFalse(EnumUtils.isValid(StringMultipleOneNullEnum.class, 0xA));

        assertTrue(EnumUtils.isValid(IntegerMultipleOneNullEnum.class, "1"));
        assertTrue(EnumUtils.isValid(IntegerMultipleOneNullEnum.class, "2"));
        assertTrue(EnumUtils.isValid(IntegerMultipleOneNullEnum.class, "3"));
        assertTrue(EnumUtils.isValid(IntegerMultipleOneNullEnum.class, "4"));
        assertTrue(EnumUtils.isValid(IntegerMultipleOneNullEnum.class, 1));
        assertTrue(EnumUtils.isValid(IntegerMultipleOneNullEnum.class, 2));
        assertTrue(EnumUtils.isValid(IntegerMultipleOneNullEnum.class, 3));
        assertTrue(EnumUtils.isValid(IntegerMultipleOneNullEnum.class, 4));
        assertFalse(EnumUtils.isValid(IntegerMultipleOneNullEnum.class, (String) null));
        assertFalse(EnumUtils.isValid(IntegerMultipleOneNullEnum.class, (Integer) null));
        assertFalse(EnumUtils.isValid(IntegerMultipleOneNullEnum.class, "A"));
        assertFalse(EnumUtils.isValid(IntegerMultipleOneNullEnum.class, 0xA));
    }

    /** Standard enumeration, not one of our customized types. */
    private enum StandardEnum {
        ONE,
        TWO;
    }

    /** String enum, one value, not null. */
    private enum StringOneNotNullEnum implements StringEnum {
        ONE("1");

        private final String value;

        private StringOneNotNullEnum(String value) {
            this.value = value;
        }

        @Override
        public String getValue() {
            return value;
        }

        public static StringOneNotNullEnum getEnum(String value) throws EnumException {
            return (StringOneNotNullEnum) EnumUtils.getEnum(StringOneNotNullEnum.class, value);
        }
    }

    /** String enum, one value, null. */
    private enum StringOneNullEnum implements StringEnum {
        UNKNOWN;

        private final String value;

        private StringOneNullEnum() {
            this.value = null;
        }

        @Override
        public String getValue() {
            return value;
        }

        public static StringOneNullEnum getEnum(String value) throws EnumException {
            return (StringOneNullEnum) EnumUtils.getEnum(StringOneNullEnum.class, value);
        }
    }

    /** String enum, multiple values, none null. */
    private enum StringMultipleNoneNullEnum implements StringEnum {
        ONE("1"),
        TWO("2"),
        THREE("3"),
        FOUR("4");

        private final String value;

        private StringMultipleNoneNullEnum(String value) {
            this.value = value;
        }

        @Override
        public String getValue() {
            return value;
        }

        public static StringMultipleNoneNullEnum getEnum(String value) throws EnumException {
            return (StringMultipleNoneNullEnum) EnumUtils.getEnum(StringMultipleNoneNullEnum.class, value);
        }
    }

    /** String enum, multiple values, one null. */
    private enum StringMultipleOneNullEnum implements StringEnum {
        ONE("1"),
        TWO("2"),
        THREE("3"),
        FOUR("4"),
        UNKNOWN;

        private final String value;

        private StringMultipleOneNullEnum(String value) {
            this.value = value;
        }

        private StringMultipleOneNullEnum() {
            this.value = null;
        }

        @Override
        public String getValue() {
            return value;
        }

        public static StringMultipleOneNullEnum getEnum(String value) throws EnumException {
            return (StringMultipleOneNullEnum) EnumUtils.getEnum(StringMultipleOneNullEnum.class, value);
        }
    }

    /** Integer enum, one value, not null. */
    private enum IntegerOneNotNullEnum implements IntegerEnum {
        ONE(1);

        private final Integer value;

        private IntegerOneNotNullEnum(Integer value) {
            this.value = value;
        }

        @Override
        public Integer getValue() {
            return value;
        }

        public static IntegerOneNotNullEnum getEnum(Integer value) throws EnumException {
            return (IntegerOneNotNullEnum) EnumUtils.getEnum(IntegerOneNotNullEnum.class, value);
        }

        // Technically, an enumeration might not implement this method, but it's useful.
        public static IntegerOneNotNullEnum getEnum(String value) throws EnumException {
            return (IntegerOneNotNullEnum) EnumUtils.getEnum(IntegerOneNotNullEnum.class, value);
        }
    }

    /** Integer enum, one value, null. */
    private enum IntegerOneNullEnum implements IntegerEnum {
        UNKNOWN;

        private final Integer value;

        private IntegerOneNullEnum() {
            this.value = null;
        }

        @Override
        public Integer getValue() {
            return value;
        }

        public static IntegerOneNullEnum getEnum(Integer value) throws EnumException {
            return (IntegerOneNullEnum) EnumUtils.getEnum(IntegerOneNullEnum.class, value);
        }

        // Technically, an enumeration might not implement this method, but it's useful.
        public static IntegerOneNullEnum getEnum(String value) throws EnumException {
            return (IntegerOneNullEnum) EnumUtils.getEnum(IntegerOneNullEnum.class, value);
        }
    }

    /** Integer enum, multiple values, none null. */
    private enum IntegerMultipleNoneNullEnum implements IntegerEnum {
        ONE(1),
        TWO(2),
        THREE(3),
        FOUR(4);

        private final Integer value;

        private IntegerMultipleNoneNullEnum(Integer value) {
            this.value = value;
        }

        @Override
        public Integer getValue() {
            return value;
        }

        public static IntegerMultipleNoneNullEnum getEnum(Integer value) throws EnumException {
            return (IntegerMultipleNoneNullEnum) EnumUtils.getEnum(IntegerMultipleNoneNullEnum.class, value);
        }

        // Technically, an enumeration might not implement this method, but it's useful.
        public static IntegerMultipleNoneNullEnum getEnum(String value) throws EnumException {
            return (IntegerMultipleNoneNullEnum) EnumUtils.getEnum(IntegerMultipleNoneNullEnum.class, value);
        }
    }

    /** Integer enum, multiple values, one null. */
    private enum IntegerMultipleOneNullEnum implements IntegerEnum {
        ONE(1),
        TWO(2),
        THREE(3),
        FOUR(4),
        UNKNOWN;

        private final Integer value;

        private IntegerMultipleOneNullEnum(Integer value) {
            this.value = value;
        }

        private IntegerMultipleOneNullEnum() {
            this.value = null;
        }

        @Override
        public Integer getValue() {
            return value;
        }

        public static IntegerMultipleOneNullEnum getEnum(Integer value) throws EnumException {
            return (IntegerMultipleOneNullEnum) EnumUtils.getEnum(IntegerMultipleOneNullEnum.class, value);
        }

        // Technically, an enumeration might not implement this method, but it's useful.
        public static IntegerMultipleOneNullEnum getEnum(String value) throws EnumException {
            return (IntegerMultipleOneNullEnum) EnumUtils.getEnum(IntegerMultipleOneNullEnum.class, value);
        }
    }
}

