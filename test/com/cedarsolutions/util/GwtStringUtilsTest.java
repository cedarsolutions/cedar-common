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
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.Test;

import com.cedarsolutions.util.gwt.GwtStringUtils;

/**
 * Unit tests for CommonCommonStringUtils.
 * @author Kenneth J. Pronovici <pronovic@ieee.org>
 */
public class GwtStringUtilsTest {

    /** Test the chop() method. */
    @Test public void testChop() {
        assertEquals(null, GwtStringUtils.chop(null));
        assertEquals("", GwtStringUtils.chop(""));
        assertEquals("", GwtStringUtils.chop("A"));
        assertEquals("AB", GwtStringUtils.chop("ABC"));
        assertEquals("whatever", GwtStringUtils.chop("whatever\03"));
    }

    /** Test isEmpty() method. */
    @Test public void testIsEmpty() {
        assertTrue(GwtStringUtils.isEmpty(null));
        assertTrue(GwtStringUtils.isEmpty(""));
        assertTrue(GwtStringUtils.isEmpty(" "));
        assertTrue(GwtStringUtils.isEmpty("\t"));
        assertTrue(GwtStringUtils.isEmpty("  \t \t\t   "));
        assertFalse(GwtStringUtils.isEmpty("a"));
        assertFalse(GwtStringUtils.isEmpty(" a"));
        assertFalse(GwtStringUtils.isEmpty(" a "));
    }

    /** Test the equals() method. */
    @Test public void testEquals() {

        assertTrue(GwtStringUtils.equals(null, null));
        assertTrue(GwtStringUtils.equals("", ""));
        assertTrue(GwtStringUtils.equals(null, ""));
        assertTrue(GwtStringUtils.equals("", null));

        assertTrue(GwtStringUtils.equals(null, ""));
        assertFalse(GwtStringUtils.equals(null, "a"));
        assertFalse(GwtStringUtils.equals(null, "ab"));
        assertFalse(GwtStringUtils.equals(null, "abc"));

        assertTrue(GwtStringUtils.equals("", ""));
        assertFalse(GwtStringUtils.equals("", "a"));
        assertFalse(GwtStringUtils.equals("", "ab"));
        assertFalse(GwtStringUtils.equals("", "abc"));

        assertFalse(GwtStringUtils.equals("a", null));
        assertFalse(GwtStringUtils.equals("a", ""));
        assertTrue(GwtStringUtils.equals("a", "a"));
        assertFalse(GwtStringUtils.equals("a", "ab"));
        assertFalse(GwtStringUtils.equals("a", "abc"));

        assertFalse(GwtStringUtils.equals("ab", null));
        assertFalse(GwtStringUtils.equals("ab", ""));
        assertFalse(GwtStringUtils.equals("ab", "a"));
        assertTrue(GwtStringUtils.equals("ab", "ab"));
        assertFalse(GwtStringUtils.equals("ab", "abc"));

        assertFalse(GwtStringUtils.equals("abc", null));
        assertFalse(GwtStringUtils.equals("abc", ""));
        assertFalse(GwtStringUtils.equals("abc", "a"));
        assertFalse(GwtStringUtils.equals("abc", "ab"));
        assertTrue(GwtStringUtils.equals("abc", "abc"));
    }

    /** Test the equals() method that includes a length. */
    @Test public void testEqualsWithLength() {

        assertTrue(GwtStringUtils.equals(null, null, 0));
        assertTrue(GwtStringUtils.equals(null, null, 1));

        assertTrue(GwtStringUtils.equals(null, "", 0));
        assertTrue(GwtStringUtils.equals(null, "a", 0));
        assertTrue(GwtStringUtils.equals(null, "abc", 0));

        assertTrue(GwtStringUtils.equals(null, "", 1));
        assertFalse(GwtStringUtils.equals(null, "a", 1));
        assertFalse(GwtStringUtils.equals(null, "abc", 1));

        assertTrue(GwtStringUtils.equals(null, "", 2));
        assertFalse(GwtStringUtils.equals(null, "a", 2));
        assertFalse(GwtStringUtils.equals(null, "abc", 2));

        assertTrue(GwtStringUtils.equals(null, "", 3));
        assertFalse(GwtStringUtils.equals(null, "a", 3));
        assertFalse(GwtStringUtils.equals(null, "abc", 3));

        assertTrue(GwtStringUtils.equals("", null, 0));
        assertTrue(GwtStringUtils.equals("a", null, 0));
        assertTrue(GwtStringUtils.equals("abc", null, 0));

        assertTrue(GwtStringUtils.equals("", null, 1));
        assertFalse(GwtStringUtils.equals("a", null, 1));
        assertFalse(GwtStringUtils.equals("abc", null, 1));

        assertTrue(GwtStringUtils.equals("", null, 2));
        assertFalse(GwtStringUtils.equals("a", null, 2));
        assertFalse(GwtStringUtils.equals("abc", null, 2));

        assertTrue(GwtStringUtils.equals("", null, 3));
        assertFalse(GwtStringUtils.equals("a", null, 3));
        assertFalse(GwtStringUtils.equals("abc", null, 3));

        assertTrue(GwtStringUtils.equals("", "", 0));
        assertTrue(GwtStringUtils.equals("", "", 1));
        assertTrue(GwtStringUtils.equals("", "", 2));
        assertTrue(GwtStringUtils.equals("", "", 3));

        assertTrue(GwtStringUtils.equals("A", "A", 0));
        assertTrue(GwtStringUtils.equals("A", "A", 1));
        assertTrue(GwtStringUtils.equals("A", "A", 2));
        assertTrue(GwtStringUtils.equals("A", "A", 3));

        assertTrue(GwtStringUtils.equals("A", "B", 0));
        assertFalse(GwtStringUtils.equals("A", "B", 1));
        assertFalse(GwtStringUtils.equals("A", "B", 2));
        assertFalse(GwtStringUtils.equals("A", "B", 3));

        assertTrue(GwtStringUtils.equals("AA", "AA", 0));
        assertTrue(GwtStringUtils.equals("AA", "AA", 1));
        assertTrue(GwtStringUtils.equals("AA", "AA", 2));
        assertTrue(GwtStringUtils.equals("AA", "AA", 3));

        assertTrue(GwtStringUtils.equals("AA", "AB", 0));
        assertTrue(GwtStringUtils.equals("AA", "AB", 1));
        assertFalse(GwtStringUtils.equals("AA", "AB", 2));
        assertFalse(GwtStringUtils.equals("AA", "AB", 3));

        assertTrue(GwtStringUtils.equals("AAA", "AAA", 0));
        assertTrue(GwtStringUtils.equals("AAA", "AAA", 1));
        assertTrue(GwtStringUtils.equals("AAA", "AAA", 2));
        assertTrue(GwtStringUtils.equals("AAA", "AAA", 3));

        assertTrue(GwtStringUtils.equals("AAA", "AAB", 0));
        assertTrue(GwtStringUtils.equals("AAA", "AAB", 1));
        assertTrue(GwtStringUtils.equals("AAA", "AAB", 2));
        assertFalse(GwtStringUtils.equals("AAA", "AAB", 3));

        assertTrue(GwtStringUtils.equals("A", "AAB", 0));
        assertTrue(GwtStringUtils.equals("AA", "AAB", 0));

        assertTrue(GwtStringUtils.equals("A", "AAB", 1));
        assertTrue(GwtStringUtils.equals("AA", "AAB", 1));

        assertFalse(GwtStringUtils.equals("A", "AAB", 2));
        assertTrue(GwtStringUtils.equals("AA", "AAB", 2));

        assertFalse(GwtStringUtils.equals("A", "AAB", 3));
        assertFalse(GwtStringUtils.equals("AA", "AAB", 3));
    }

    /** Test the equals() method, case insensitive. */
    @Test public void testEqualsCaseInsensitive() {

        assertTrue(GwtStringUtils.equals(null, null, false));
        assertTrue(GwtStringUtils.equals("", "", false));
        assertTrue(GwtStringUtils.equals(null, "", false));
        assertTrue(GwtStringUtils.equals("", null, false));

        assertTrue(GwtStringUtils.equals(null, "", false));
        assertFalse(GwtStringUtils.equals(null, "a", false));
        assertFalse(GwtStringUtils.equals(null, "ab", false));
        assertFalse(GwtStringUtils.equals(null, "abc", false));

        assertTrue(GwtStringUtils.equals("", "", false));
        assertFalse(GwtStringUtils.equals("", "a", false));
        assertFalse(GwtStringUtils.equals("", "ab", false));
        assertFalse(GwtStringUtils.equals("", "abc", false));

        assertFalse(GwtStringUtils.equals("a", null, false));
        assertFalse(GwtStringUtils.equals("a", "", false));
        assertTrue(GwtStringUtils.equals("a", "A", false));
        assertFalse(GwtStringUtils.equals("a", "Ab", false));
        assertFalse(GwtStringUtils.equals("a", "Abc", false));

        assertFalse(GwtStringUtils.equals("ab", null));
        assertFalse(GwtStringUtils.equals("ab", "", false));
        assertFalse(GwtStringUtils.equals("ab", "A", false));
        assertTrue(GwtStringUtils.equals("ab", "aB", false));
        assertFalse(GwtStringUtils.equals("ab", "aBc", false));

        assertFalse(GwtStringUtils.equals("abc", null, false));
        assertFalse(GwtStringUtils.equals("abc", "", false));
        assertFalse(GwtStringUtils.equals("abc", "a", false));
        assertFalse(GwtStringUtils.equals("abc", "AB", false));
        assertTrue(GwtStringUtils.equals("abc", "ABC", false));
    }

    /** Test the substring() method. */
    @Test public void testSubstring() {
        assertEquals(null, GwtStringUtils.substring(null, 0));
        assertEquals("", GwtStringUtils.substring("", 0));
        assertEquals("1", GwtStringUtils.substring("1", 0));
        assertEquals("12", GwtStringUtils.substring("12", 0));
        assertEquals("123", GwtStringUtils.substring("123", 0));
        assertEquals("1234", GwtStringUtils.substring("1234", 0));
        assertEquals("12345", GwtStringUtils.substring("12345", 0));
        assertEquals("123456", GwtStringUtils.substring("123456", 0));

        try {
            GwtStringUtils.substring("", 1);
            fail("Expected StringIndexOutOfBoundsException");
        } catch (StringIndexOutOfBoundsException e) {
        }

        assertEquals("", GwtStringUtils.substring("1", 1));
        assertEquals("2", GwtStringUtils.substring("12", 1));
        assertEquals("23", GwtStringUtils.substring("123", 1));
        assertEquals("234", GwtStringUtils.substring("1234", 1));
        assertEquals("2345", GwtStringUtils.substring("12345", 1));
        assertEquals("23456", GwtStringUtils.substring("123456", 1));

        try {
            GwtStringUtils.substring("", 2);
            fail("Expected StringIndexOutOfBoundsException");
        } catch (StringIndexOutOfBoundsException e) {
        }

        try {
            GwtStringUtils.substring("1", 2);
            fail("Expected StringIndexOutOfBoundsException");
        } catch (StringIndexOutOfBoundsException e) {
        }

        assertEquals("", GwtStringUtils.substring("12", 2));
        assertEquals("3", GwtStringUtils.substring("123", 2));
        assertEquals("34", GwtStringUtils.substring("1234", 2));
        assertEquals("345", GwtStringUtils.substring("12345", 2));
        assertEquals("3456", GwtStringUtils.substring("123456", 2));
    }

    /** Test the substring() method with an endIndex. */
    @Test public void testSubstringWithEndIndex() {
        assertEquals(null, GwtStringUtils.substring(null, 0, 5));
        assertEquals("", GwtStringUtils.substring("", 0, 5));
        assertEquals("1", GwtStringUtils.substring("1", 0, 5));
        assertEquals("12", GwtStringUtils.substring("12", 0, 5));
        assertEquals("123", GwtStringUtils.substring("123", 0, 5));
        assertEquals("1234", GwtStringUtils.substring("1234", 0, 5));
        assertEquals("12345", GwtStringUtils.substring("12345", 0, 5));
        assertEquals("12345", GwtStringUtils.substring("123456", 0, 5));

        try {
            GwtStringUtils.substring("", 1, 5);
            fail("Expected StringIndexOutOfBoundsException");
        } catch (StringIndexOutOfBoundsException e) {
        }

        assertEquals("", GwtStringUtils.substring("1", 1, 5));
        assertEquals("2", GwtStringUtils.substring("12", 1, 5));
        assertEquals("23", GwtStringUtils.substring("123", 1, 5));
        assertEquals("234", GwtStringUtils.substring("1234", 1, 5));
        assertEquals("2345", GwtStringUtils.substring("12345", 1, 5));
        assertEquals("2345", GwtStringUtils.substring("123456", 1, 5));

        try {
            GwtStringUtils.substring("", 2, 5);
            fail("Expected StringIndexOutOfBoundsException");
        } catch (StringIndexOutOfBoundsException e) {
        }

        try {
            GwtStringUtils.substring("1", 2, 5);
            fail("Expected StringIndexOutOfBoundsException");
        } catch (StringIndexOutOfBoundsException e) {
        }

        assertEquals("", GwtStringUtils.substring("12", 2, 5));
        assertEquals("3", GwtStringUtils.substring("123", 2, 5));
        assertEquals("34", GwtStringUtils.substring("1234", 2, 5));
        assertEquals("345", GwtStringUtils.substring("12345", 2, 5));
        assertEquals("345", GwtStringUtils.substring("123456", 2, 5));
    }

    /** Test the truncate() method. */
    @Test public void testTruncate() {
        assertEquals("", GwtStringUtils.truncate(null, 0));
        assertEquals("", GwtStringUtils.truncate("", 0));
        assertEquals("", GwtStringUtils.truncate("1", 0));
        assertEquals("", GwtStringUtils.truncate("12", 0));
        assertEquals("", GwtStringUtils.truncate("123", 0));
        assertEquals("", GwtStringUtils.truncate("1234", 0));
        assertEquals("", GwtStringUtils.truncate("12345", 0));
        assertEquals("", GwtStringUtils.truncate("123456", 0));
        assertEquals("", GwtStringUtils.truncate("      ", 0));

        assertEquals("", GwtStringUtils.truncate(null, 1));
        assertEquals("", GwtStringUtils.truncate("", 1));
        assertEquals("1", GwtStringUtils.truncate("1", 1));
        assertEquals("1", GwtStringUtils.truncate("12", 1));
        assertEquals("1", GwtStringUtils.truncate("123", 1));
        assertEquals("1", GwtStringUtils.truncate("1234", 1));
        assertEquals("1", GwtStringUtils.truncate("12345", 1));
        assertEquals("1", GwtStringUtils.truncate("123456", 1));
        assertEquals("", GwtStringUtils.truncate("      ", 1));

        assertEquals("", GwtStringUtils.truncate(null, 2));
        assertEquals("", GwtStringUtils.truncate("", 2));
        assertEquals("1", GwtStringUtils.truncate("1", 2));
        assertEquals("12", GwtStringUtils.truncate("12", 2));
        assertEquals("12", GwtStringUtils.truncate("123", 2));
        assertEquals("12", GwtStringUtils.truncate("1234", 2));
        assertEquals("12", GwtStringUtils.truncate("12345", 2));
        assertEquals("12", GwtStringUtils.truncate("123456", 2));
        assertEquals("", GwtStringUtils.truncate("      ", 2));

        assertEquals("", GwtStringUtils.truncate(null, 3));
        assertEquals("", GwtStringUtils.truncate("", 3));
        assertEquals("1", GwtStringUtils.truncate("1", 3));
        assertEquals("12", GwtStringUtils.truncate("12", 3));
        assertEquals("123", GwtStringUtils.truncate("123", 3));
        assertEquals("123", GwtStringUtils.truncate("1234", 3));
        assertEquals("123", GwtStringUtils.truncate("12345", 3));
        assertEquals("123", GwtStringUtils.truncate("123456", 3));
        assertEquals("", GwtStringUtils.truncate("      ", 3));

        assertEquals("", GwtStringUtils.truncate(null, 4));
        assertEquals("", GwtStringUtils.truncate("", 4));
        assertEquals("1", GwtStringUtils.truncate("1", 4));
        assertEquals("12", GwtStringUtils.truncate("12", 4));
        assertEquals("123", GwtStringUtils.truncate("123", 4));
        assertEquals("1234", GwtStringUtils.truncate("1234", 4));
        assertEquals("1234", GwtStringUtils.truncate("12345", 4));
        assertEquals("1234", GwtStringUtils.truncate("123456", 4));
        assertEquals("", GwtStringUtils.truncate("      ", 4));

        assertEquals("", GwtStringUtils.truncate(null, 5));
        assertEquals("", GwtStringUtils.truncate("", 5));
        assertEquals("1", GwtStringUtils.truncate("1", 5));
        assertEquals("12", GwtStringUtils.truncate("12", 5));
        assertEquals("123", GwtStringUtils.truncate("123", 5));
        assertEquals("1234", GwtStringUtils.truncate("1234", 5));
        assertEquals("12345", GwtStringUtils.truncate("12345", 5));
        assertEquals("12345", GwtStringUtils.truncate("123456", 5));
        assertEquals("", GwtStringUtils.truncate("      ", 5));

        assertEquals("", GwtStringUtils.truncate(null, 6));
        assertEquals("", GwtStringUtils.truncate("", 6));
        assertEquals("1", GwtStringUtils.truncate("1", 6));
        assertEquals("12", GwtStringUtils.truncate("12", 6));
        assertEquals("123", GwtStringUtils.truncate("123", 6));
        assertEquals("1234", GwtStringUtils.truncate("1234", 6));
        assertEquals("12345", GwtStringUtils.truncate("12345", 6));
        assertEquals("123456", GwtStringUtils.truncate("123456", 6));
        assertEquals("", GwtStringUtils.truncate("      ", 6));

        assertEquals("", GwtStringUtils.truncate(null, 7));
        assertEquals("", GwtStringUtils.truncate("", 7));
        assertEquals("1", GwtStringUtils.truncate("1", 7));
        assertEquals("12", GwtStringUtils.truncate("12", 7));
        assertEquals("123", GwtStringUtils.truncate("123", 7));
        assertEquals("1234", GwtStringUtils.truncate("1234", 7));
        assertEquals("12345", GwtStringUtils.truncate("12345", 7));
        assertEquals("123456", GwtStringUtils.truncate("123456", 7));
        assertEquals("", GwtStringUtils.truncate("      ", 7));
    }

    /** Test the truncateToNull() method. */
    @Test public void testTruncateToNull() {
        assertEquals(null, GwtStringUtils.truncateToNull(null, 0));
        assertEquals(null, GwtStringUtils.truncateToNull(null, 0));
        assertEquals(null, GwtStringUtils.truncateToNull("1", 0));
        assertEquals(null, GwtStringUtils.truncateToNull("12", 0));
        assertEquals(null, GwtStringUtils.truncateToNull("123", 0));
        assertEquals(null, GwtStringUtils.truncateToNull("1234", 0));
        assertEquals(null, GwtStringUtils.truncateToNull("12345", 0));
        assertEquals(null, GwtStringUtils.truncateToNull("123456", 0));
        assertEquals(null, GwtStringUtils.truncateToNull("      ", 0));

        assertEquals(null, GwtStringUtils.truncateToNull(null, 1));
        assertEquals(null, GwtStringUtils.truncateToNull(null, 1));
        assertEquals("1", GwtStringUtils.truncateToNull("1", 1));
        assertEquals("1", GwtStringUtils.truncateToNull("12", 1));
        assertEquals("1", GwtStringUtils.truncateToNull("123", 1));
        assertEquals("1", GwtStringUtils.truncateToNull("1234", 1));
        assertEquals("1", GwtStringUtils.truncateToNull("12345", 1));
        assertEquals("1", GwtStringUtils.truncateToNull("123456", 1));
        assertEquals(null, GwtStringUtils.truncateToNull("      ", 1));

        assertEquals(null, GwtStringUtils.truncateToNull(null, 2));
        assertEquals(null, GwtStringUtils.truncateToNull(null, 2));
        assertEquals("1", GwtStringUtils.truncateToNull("1", 2));
        assertEquals("12", GwtStringUtils.truncateToNull("12", 2));
        assertEquals("12", GwtStringUtils.truncateToNull("123", 2));
        assertEquals("12", GwtStringUtils.truncateToNull("1234", 2));
        assertEquals("12", GwtStringUtils.truncateToNull("12345", 2));
        assertEquals("12", GwtStringUtils.truncateToNull("123456", 2));
        assertEquals(null, GwtStringUtils.truncateToNull("      ", 2));

        assertEquals(null, GwtStringUtils.truncateToNull(null, 3));
        assertEquals(null, GwtStringUtils.truncateToNull(null, 3));
        assertEquals("1", GwtStringUtils.truncateToNull("1", 3));
        assertEquals("12", GwtStringUtils.truncateToNull("12", 3));
        assertEquals("123", GwtStringUtils.truncateToNull("123", 3));
        assertEquals("123", GwtStringUtils.truncateToNull("1234", 3));
        assertEquals("123", GwtStringUtils.truncateToNull("12345", 3));
        assertEquals("123", GwtStringUtils.truncateToNull("123456", 3));
        assertEquals(null, GwtStringUtils.truncateToNull("      ", 3));

        assertEquals(null, GwtStringUtils.truncateToNull(null, 4));
        assertEquals(null, GwtStringUtils.truncateToNull(null, 4));
        assertEquals("1", GwtStringUtils.truncateToNull("1", 4));
        assertEquals("12", GwtStringUtils.truncateToNull("12", 4));
        assertEquals("123", GwtStringUtils.truncateToNull("123", 4));
        assertEquals("1234", GwtStringUtils.truncateToNull("1234", 4));
        assertEquals("1234", GwtStringUtils.truncateToNull("12345", 4));
        assertEquals("1234", GwtStringUtils.truncateToNull("123456", 4));
        assertEquals(null, GwtStringUtils.truncateToNull("      ", 4));

        assertEquals(null, GwtStringUtils.truncateToNull(null, 5));
        assertEquals(null, GwtStringUtils.truncateToNull(null, 5));
        assertEquals("1", GwtStringUtils.truncateToNull("1", 5));
        assertEquals("12", GwtStringUtils.truncateToNull("12", 5));
        assertEquals("123", GwtStringUtils.truncateToNull("123", 5));
        assertEquals("1234", GwtStringUtils.truncateToNull("1234", 5));
        assertEquals("12345", GwtStringUtils.truncateToNull("12345", 5));
        assertEquals("12345", GwtStringUtils.truncateToNull("123456", 5));
        assertEquals(null, GwtStringUtils.truncateToNull("      ", 5));

        assertEquals(null, GwtStringUtils.truncateToNull(null, 6));
        assertEquals(null, GwtStringUtils.truncateToNull(null, 6));
        assertEquals("1", GwtStringUtils.truncateToNull("1", 6));
        assertEquals("12", GwtStringUtils.truncateToNull("12", 6));
        assertEquals("123", GwtStringUtils.truncateToNull("123", 6));
        assertEquals("1234", GwtStringUtils.truncateToNull("1234", 6));
        assertEquals("12345", GwtStringUtils.truncateToNull("12345", 6));
        assertEquals("123456", GwtStringUtils.truncateToNull("123456", 6));
        assertEquals(null, GwtStringUtils.truncateToNull("      ", 6));

        assertEquals(null, GwtStringUtils.truncateToNull(null, 7));
        assertEquals(null, GwtStringUtils.truncateToNull(null, 7));
        assertEquals("1", GwtStringUtils.truncateToNull("1", 7));
        assertEquals("12", GwtStringUtils.truncateToNull("12", 7));
        assertEquals("123", GwtStringUtils.truncateToNull("123", 7));
        assertEquals("1234", GwtStringUtils.truncateToNull("1234", 7));
        assertEquals("12345", GwtStringUtils.truncateToNull("12345", 7));
        assertEquals("123456", GwtStringUtils.truncateToNull("123456", 7));
        assertEquals(null, GwtStringUtils.truncateToNull("      ", 7));
    }

    /** Test the trim() method. */
    @Test public void testTrim() {
        assertEquals(null, GwtStringUtils.trim(null));
        assertEquals("", GwtStringUtils.trim(""));
        assertEquals("", GwtStringUtils.trim(" "));
        assertEquals("", GwtStringUtils.trim("\t"));
        assertEquals("", GwtStringUtils.trim("\n"));
        assertEquals("", GwtStringUtils.trim("   \n  \t\t    \t"));
        assertEquals("A", GwtStringUtils.trim("A"));
        assertEquals("A", GwtStringUtils.trim(" A"));
        assertEquals("A", GwtStringUtils.trim("A "));
        assertEquals("A", GwtStringUtils.trim(" A "));
        assertEquals("A", GwtStringUtils.trim(" \t   \n A \t\t  "));
        assertEquals("A \tB", GwtStringUtils.trim(" \t   \n A \tB\t  "));
    }

    /** Test the trimToNull() method. */
    @Test public void testTrimToNull() {
        assertEquals(null, GwtStringUtils.trimToNull(null));
        assertEquals(null, GwtStringUtils.trimToNull(""));
        assertEquals(null, GwtStringUtils.trimToNull(" "));
        assertEquals(null, GwtStringUtils.trimToNull("\t"));
        assertEquals(null, GwtStringUtils.trimToNull("\n"));
        assertEquals(null, GwtStringUtils.trimToNull("   \n  \t\t    \t"));
        assertEquals("A", GwtStringUtils.trimToNull("A"));
        assertEquals("A", GwtStringUtils.trimToNull(" A"));
        assertEquals("A", GwtStringUtils.trimToNull("A "));
        assertEquals("A", GwtStringUtils.trimToNull(" A "));
        assertEquals("A", GwtStringUtils.trimToNull(" \t   \n A \t\t  "));
        assertEquals("A \tB", GwtStringUtils.trimToNull(" \t   \n A \tB\t  "));
    }

    /** Test the rtrim() method. */
    @Test public void testRtrim() {
        assertEquals(null, GwtStringUtils.rtrim(null));
        assertEquals("", GwtStringUtils.rtrim(""));
        assertEquals("", GwtStringUtils.rtrim(" "));
        assertEquals("", GwtStringUtils.rtrim("\t"));
        assertEquals("", GwtStringUtils.rtrim("\n"));
        assertEquals("", GwtStringUtils.rtrim("   \n  \t\t    \t"));
        assertEquals("A", GwtStringUtils.rtrim("A"));
        assertEquals(" A", GwtStringUtils.rtrim(" A"));
        assertEquals("A", GwtStringUtils.rtrim("A "));
        assertEquals(" A", GwtStringUtils.rtrim(" A "));
        assertEquals(" \t   \n A", GwtStringUtils.rtrim(" \t   \n A \t\t  "));
        assertEquals(" \t   \n A \tB", GwtStringUtils.rtrim(" \t   \n A \tB\t  "));
    }

    /** Test the rtrimToNull() method. */
    @Test public void testRtrimToNull() {
        assertEquals(null, GwtStringUtils.rtrimToNull(null));
        assertEquals(null, GwtStringUtils.rtrimToNull(""));
        assertEquals(null, GwtStringUtils.rtrimToNull(" "));
        assertEquals(null, GwtStringUtils.rtrimToNull("\t"));
        assertEquals(null, GwtStringUtils.rtrimToNull("\n"));
        assertEquals(null, GwtStringUtils.rtrimToNull("   \n  \t\t    \t"));
        assertEquals("A", GwtStringUtils.rtrimToNull("A"));
        assertEquals(" A", GwtStringUtils.rtrimToNull(" A"));
        assertEquals("A", GwtStringUtils.rtrimToNull("A "));
        assertEquals(" A", GwtStringUtils.rtrimToNull(" A "));
        assertEquals(" \t   \n A", GwtStringUtils.rtrimToNull(" \t   \n A \t\t  "));
        assertEquals(" \t   \n A \tB", GwtStringUtils.rtrimToNull(" \t   \n A \tB\t  "));
    }

    /** Test the contains() method. */
    @Test public void testContains() {
        assertFalse(GwtStringUtils.contains(null, null));
        assertFalse(GwtStringUtils.contains("string", null));
        assertFalse(GwtStringUtils.contains(null, "A"));
        assertFalse(GwtStringUtils.contains("a", "A"));
        assertFalse(GwtStringUtils.contains("B", "A"));
        assertTrue(GwtStringUtils.contains("A", "A"));
        assertFalse(GwtStringUtils.contains("whatever", "AT"));
        assertFalse(GwtStringUtils.contains("whAtever", "AT"));
        assertTrue(GwtStringUtils.contains("whATever", "AT"));

        assertFalse(GwtStringUtils.contains(null, null, true));
        assertFalse(GwtStringUtils.contains("string", null, true));
        assertFalse(GwtStringUtils.contains(null, "A", true));
        assertFalse(GwtStringUtils.contains("a", "A", true));
        assertFalse(GwtStringUtils.contains("B", "A", true));
        assertTrue(GwtStringUtils.contains("A", "A", true));
        assertFalse(GwtStringUtils.contains("whatever", "AT", true));
        assertFalse(GwtStringUtils.contains("whAtever", "AT", true));
        assertTrue(GwtStringUtils.contains("whATever", "AT", true));

        assertFalse(GwtStringUtils.contains(null, null, false));
        assertFalse(GwtStringUtils.contains("string", null, false));
        assertFalse(GwtStringUtils.contains(null, "A", false));
        assertTrue(GwtStringUtils.contains("a", "A", false));
        assertFalse(GwtStringUtils.contains("B", "A", false));
        assertTrue(GwtStringUtils.contains("A", "A", false));
        assertTrue(GwtStringUtils.contains("whatever", "AT", false));
        assertTrue(GwtStringUtils.contains("whAtever", "AT", false));
        assertTrue(GwtStringUtils.contains("whATever", "AT", false));
    }

    /** Test the startsWith() method. */
    @Test public void testStartsWith() {
        assertFalse(GwtStringUtils.startsWith(null, null));
        assertFalse(GwtStringUtils.startsWith("string", null));
        assertFalse(GwtStringUtils.startsWith(null, "A"));
        assertFalse(GwtStringUtils.startsWith("a", "A"));
        assertFalse(GwtStringUtils.startsWith("B", "A"));
        assertTrue(GwtStringUtils.startsWith("A", "A"));
        assertFalse(GwtStringUtils.startsWith("whatever", "AT"));
        assertFalse(GwtStringUtils.startsWith("whAtever", "AT"));
        assertFalse(GwtStringUtils.startsWith("whATever", "AT"));
        assertFalse(GwtStringUtils.startsWith("whATever", "what"));
        assertFalse(GwtStringUtils.startsWith("whATever", "What"));
        assertFalse(GwtStringUtils.startsWith("whATever", "whatever"));
        assertTrue(GwtStringUtils.startsWith("whATever", "whAT"));
        assertTrue(GwtStringUtils.startsWith("whATever", "whATever"));

        assertFalse(GwtStringUtils.startsWith(null, null, true));
        assertFalse(GwtStringUtils.startsWith("string", null, true));
        assertFalse(GwtStringUtils.startsWith(null, "A", true));
        assertFalse(GwtStringUtils.startsWith("a", "A", true));
        assertFalse(GwtStringUtils.startsWith("B", "A", true));
        assertTrue(GwtStringUtils.startsWith("A", "A", true));
        assertFalse(GwtStringUtils.startsWith("whatever", "AT", true));
        assertFalse(GwtStringUtils.startsWith("whAtever", "AT", true));
        assertFalse(GwtStringUtils.startsWith("whATever", "AT", true));
        assertFalse(GwtStringUtils.startsWith("whATever", "what", true));
        assertFalse(GwtStringUtils.startsWith("whATever", "What", true));
        assertFalse(GwtStringUtils.startsWith("whATever", "whatever", true));
        assertTrue(GwtStringUtils.startsWith("whATever", "whAT", true));
        assertTrue(GwtStringUtils.startsWith("whATever", "whATever", true));

        assertFalse(GwtStringUtils.startsWith(null, null, false));
        assertFalse(GwtStringUtils.startsWith("string", null, false));
        assertFalse(GwtStringUtils.startsWith(null, "A", false));
        assertTrue(GwtStringUtils.startsWith("a", "A", false));
        assertFalse(GwtStringUtils.startsWith("B", "A", false));
        assertTrue(GwtStringUtils.startsWith("A", "A", false));
        assertFalse(GwtStringUtils.startsWith("whatever", "AT", false));
        assertFalse(GwtStringUtils.startsWith("whAtever", "AT", false));
        assertFalse(GwtStringUtils.startsWith("whATever", "AT", false));
        assertTrue(GwtStringUtils.startsWith("whATever", "what", false));
        assertTrue(GwtStringUtils.startsWith("whATever", "What", false));
        assertTrue(GwtStringUtils.startsWith("whATever", "whatever", false));
        assertTrue(GwtStringUtils.startsWith("whATever", "whAT", false));
        assertTrue(GwtStringUtils.startsWith("whATever", "whATever", false));
    }

    /** Test toUpperCase(). */
    @Test public void testToUpperCase() {
        assertEquals(null, GwtStringUtils.toUpperCase(null));
        assertEquals("", GwtStringUtils.toUpperCase(""));
        assertEquals("   ", GwtStringUtils.toUpperCase("   "));
        assertEquals("A", GwtStringUtils.toUpperCase("a"));
        assertEquals("AB", GwtStringUtils.toUpperCase("aB"));
        assertEquals("AB", GwtStringUtils.toUpperCase("AB"));
        assertEquals(" AB ", GwtStringUtils.toUpperCase(" AB "));
    }

    /** Test format. */
    @Test public void testFormat() {
        assertEquals("whatever", GwtStringUtils.format("whatever"));
        assertEquals("whatever", GwtStringUtils.format("whatever", "hello"));
        assertEquals("whatever hello", GwtStringUtils.format("whatever %s", "hello"));
        assertEquals("whatever 5", GwtStringUtils.format("whatever %d", 5));
        assertEquals("whatever 12", GwtStringUtils.format("whatever %d", 12));
        assertEquals("whatever 18", GwtStringUtils.format("whatever %x", 0x12));  // hex doesn't work <sigh>
    }

}
