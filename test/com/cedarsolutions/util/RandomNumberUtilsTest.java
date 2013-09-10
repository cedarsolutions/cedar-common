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
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import org.junit.Test;

import com.cedarsolutions.junit.util.Assertions;

/**
 * Unit tests for RandomNumberUtils.
 * @author Kenneth J. Pronovici <pronovic@ieee.org>
 */
public class RandomNumberUtilsTest {

    /** Test the generateRandomInteger() method. */
    @Test public void testGenerateRandomInteger() {

        /* Test for positive ranges starting at 0. */
        assertValidRange(0, 1, 0, 1);
        assertValidRange(0, 2, 0, 2);
        assertValidRange(0, 100, 0, 100);

        /* Test for negative ranges ending at 0. */
        assertValidRange(-1, 0, -1, 0);
        assertValidRange(-2, 0, -2, 0);
        assertValidRange(-100, 0, -100, 0);

        /* Test for positive ranges that do not start at zero. */
        assertValidRange(1, 1, 1, 1);
        assertValidRange(1, 2, 1, 2);
        assertValidRange(1, 99, 1, 99);
        assertValidRange(500, 500, 500, 500);
        assertValidRange(500, 501, 500, 501);
        assertValidRange(100000, 200000, 100000, 200000);

        /* Test for negative ranges that do not start at zero. */
        assertValidRange(-1, -1, -1, -1);
        assertValidRange(-2, -1, -2, -1);
        assertValidRange(-99, -1, -99, -1);
        assertValidRange(-500, -500, -500, -500);
        assertValidRange(-501, -500, -501, -500);
        assertValidRange(-200000, -100000, -200000, -100000);

        /* Test for ranges that span zero. */
        assertValidRange(0, 0, 0, 0);
        assertValidRange(-1, 1, -1, 1);
        assertValidRange(-2, 2, -2, 2);
        assertValidRange(-1000, 1000, -1000, 1000);
        assertValidRange(-200000, 100000, -200000, 100000);

        /* Test for degenerate ranges that we correct. */
        assertValidRange(100, 0, 0, 100);
        assertValidRange(0, -2, -2, 0);
        assertValidRange(501, 500, 500, 501);
        assertValidRange(1000, -1000, -1000, 1000);
        assertValidRange(100000, -200000, -200000, 100000);

    }

    /** Test the generateRandomAlphabeticCharacter() method. */
    @Test public void testGenerateRandomAlphabeticCharacter() {
        for (int i = 0; i < 10000; i++) {
            char result = RandomNumberUtils.generateRandomAlphabeticCharacter();
            Assertions.assertIsAlphabetic(String.valueOf(result));
        }
    }

    /** Test the generateRandomAlphabeticString() method. */
    @Test public void testGenerateRandomAlphabeticString() {
        String result;

        result = RandomNumberUtils.generateRandomAlphabeticString(0);
        assertEquals("", result);

        for (int length = 1; length < 0x40000; length *= 2) {
            result = RandomNumberUtils.generateRandomAlphabeticString(length);
            assertNotNull(result);
            assertEquals(length, result.length());
            Assertions.assertIsAlphabetic(String.valueOf(result));
        }
    }

    /** Test the generateRandomNumericCharacter() method. */
    @Test public void testGenerateRandomNumericCharacter() {
        for (int i = 0; i < 10000; i++) {
            char result = RandomNumberUtils.generateRandomNumericCharacter();
            Assertions.assertIsNumeric(String.valueOf(result));
        }
    }

    /** Test the generateRandomNumericString() method. */
    @Test public void testGenerateRandomNumericString() {
        String result;

        result = RandomNumberUtils.generateRandomNumericString(0);
        assertEquals("", result);

        for (int length = 1; length < 0x40000; length *= 2) {
            result = RandomNumberUtils.generateRandomNumericString(length);
            assertNotNull(result);
            assertEquals(length, result.length());
            Assertions.assertIsNumeric(String.valueOf(result));
        }
    }

    /** Test the generateRandomAlphanumericCharacter() method. */
    @Test public void testGenerateRandomAlphanumericCharacter() {
        for (int i = 0; i < 10000; i++) {
            char result = RandomNumberUtils.generateRandomAlphanumericCharacter();
            Assertions.assertIsAlphanumeric(String.valueOf(result));
        }
    }

    /** Test the generateRandomAlphanumericString() method. */
    @Test public void testGenerateRandomAlphanumericString() {
        String result;

        result = RandomNumberUtils.generateRandomAlphanumericString(0);
        assertEquals("", result);

        for (int length = 1; length < 0x40000; length *= 2) {
            result = RandomNumberUtils.generateRandomAlphanumericString(length);
            assertNotNull(result);
            assertEquals(length, result.length());
            Assertions.assertIsAlphanumeric(String.valueOf(result));
        }
    }

    /** Assert that we generate valid random integers on a range. */
    private void assertValidRange(int argMin, int argMax, int expectedMin, int expectedMax) {
        int iterations = (argMax - argMin) * 2 < 1000 ? 1000 : (argMax - argMin) * 2;
        for (int i = 0; i < iterations; i++) {
            int value = RandomNumberUtils.generateRandomInteger(argMin, argMax);
            if (value < expectedMin || value > expectedMax) {
                fail("Expected range from " + expectedMin + " to " + expectedMax + ", but got value " + value);
            }
        }
    }

}
