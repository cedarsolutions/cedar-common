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
package com.cedarsolutions.junit.util;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import com.cedarsolutions.exception.InvalidDataException;
import com.cedarsolutions.shared.domain.LocalizableMessage;
import com.cedarsolutions.util.DateUtils;
import com.cedarsolutions.util.StringUtils;

/**
 * Common Junit assertions.
 * @author Kenneth J. Pronovici <pronovic@ieee.org>
 */
public class Assertions {

    /** List of strings that evaluate as empty via StringUtils.isEmpty(). */
    public static final String[] EMPTY_STRINGS = new String[]  { null, "", "    ", "\n \t   ", };

    /**
     * Asserts that a given string is alphabetic.
     * @param string  String to check
     */
    public static void assertIsAlphabetic(String string) {
        assertTrue(string != null && string.matches("^[A-Za-z]+$"));
    }

    /**
     * Asserts that a given string is numeric.
     * @param string  String to check
     */
    public static void assertIsNumeric(String string) {
        assertTrue(string != null && string.matches("^[0-9]+$"));
    }

    /**
     * Asserts that a given string is alphanumeric.
     * @param string  String to check
     */
    public static void assertIsAlphanumeric(String string) {
        assertTrue(string != null && string.matches("^[A-Za-z0-9]+$"));
    }

    /** Assert that first date is less than or equal to a second date. */
    public static void assertAfter(Date first, Date second) {
        assertNotNull(first);
        assertNotNull(second);
        if (!DateUtils.isAfter(second, first)) {
            String message = DateUtils.formatTimestamp(second) + " is not after " + DateUtils.formatTimestamp(first);
            fail(message);
        }
    }

    /** Assert that first date is strictly less than a second date. */
    public static void assertAfterStrict(Date first, Date second) {
        assertNotNull(first);
        assertNotNull(second);
        if (!DateUtils.isStrictlyAfter(second, first)) {
            String message = DateUtils.formatTimestamp(second) + " is not strictly after " + DateUtils.formatTimestamp(first);
            fail(message);
        }
    }

    /** Assert that first date is greater than or equal to a second date. */
    public static void assertBefore(Date first, Date second) {
        assertNotNull(first);
        assertNotNull(second);
        if (!DateUtils.isBefore(second, first)) {
            String message = DateUtils.formatTimestamp(second) + " is not before " + DateUtils.formatTimestamp(first);
            fail(message);
        }
    }

    /** Assert that first date is strictly greater than a second date. */
    public static void assertBeforeStrict(Date first, Date second) {
        assertNotNull(first);
        assertNotNull(second);
        if (!DateUtils.isStrictlyBefore(second, first)) {
            String message = DateUtils.formatTimestamp(second) + " is not strictly before " + DateUtils.formatTimestamp(first);
            fail(message);
        }
    }

    /**
     * Assert that an iterator contains as many elements as expected.
     * @return A list built from the iterator, useful for further validations.
     */
    public static <T> List<T> assertIteratorSize(int expected, Iterator<T> iterator) {
        List<T> list = new ArrayList<T>();

        while (iterator.hasNext()) {
            list.add(iterator.next());
        }

        assertEquals(expected, list.size());
        return list;
    }

    /** Assert that an exception contains the expected key as its summary. */
    public static void assertSummary(InvalidDataException e, String key) {
        assertSummary(e, key, null);
    }

    /** Assert that an exception contains the expected key/context pair as its summary. */
    public static void assertSummary(InvalidDataException e, String key, String context) {
        assertEquals(key, e.getDetails().getSummary().getKey());
        assertEquals(context, e.getDetails().getSummary().getContext());
    }

    /** Assert that an exception contains the expected key as its only message. */
    public static void assertOnlyMessage(InvalidDataException e, String key) {
        assertOnlyMessage(e, key, null);
    }

    /** Assert that an exception contains the expected key/context pair as its only message. */
    public static void assertOnlyMessage(InvalidDataException e, String key, String context) {
        assertEquals("Expected only message, but found " + e.getDetails().getMessages().size(), 1, e.getDetails().getMessages().size());
        assertEquals(key, e.getDetails().getMessages().get(0).getKey());
        assertEquals(context, e.getDetails().getMessages().get(0).getContext());
    }

    /** Assert that an exception contains the expected key exactly once. */
    public static void assertContainsMessage(InvalidDataException e, String key) {
        assertContainsMessage(e, key, null);
    }

    /** Assert that an exception contains the expected key/context pair exactly once. */
    public static void assertContainsMessage(InvalidDataException e, String key, String context) {
        int found = 0;
        for (LocalizableMessage message : e.getDetails().getMessages()) {
            if (StringUtils.equals(key, message.getKey()) && StringUtils.equals(context, message.getContext())) {
                found += 1;
            }
        }

        if (found == 0) {
            fail("Exception did not contain expected key/context");
        } else if (found > 1) {
            fail("Exception contained more than once instance of key/context");
        }
    }
}
