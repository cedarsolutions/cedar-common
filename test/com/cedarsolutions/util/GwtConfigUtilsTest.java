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

import com.cedarsolutions.exception.NotFoundException;

/**
 * Unit tests for GwtConfigUtils.
 * @author Kenneth J. Pronovici <pronovic@ieee.org>
 */
public class GwtConfigUtilsTest {

    /** Test getBoolean(). */
    @Test public void testGetBoolean() {
        GwtConfigUtils utils = new GwtConfigUtils();

        assertTrue(utils.getBoolean(TestConfig.class, "testTrueBooleanValue"));
        assertFalse(utils.getBoolean(TestConfig.class, "testFalseBooleanValue"));

        try {
            utils.getBoolean(TestConfig.class, "bogus");
            fail("Expected NotFoundException");
        } catch (NotFoundException e) { }

        try {
            utils.getBoolean(TestConfig.class, "testStringValue"); // wrong type
            fail("Expected NotFoundException");
        } catch (NotFoundException e) { }
    }

    /** Test getDouble(). */
    @Test public void testGetDouble() {
        GwtConfigUtils utils = new GwtConfigUtils();

        assertEquals(432.0d, utils.getDouble(TestConfig.class, "testDoubleValue"), 0.0);

        try {
            utils.getDouble(TestConfig.class, "bogus");
            fail("Expected NotFoundException");
        } catch (NotFoundException e) { }

        try {
            utils.getDouble(TestConfig.class, "testStringValue"); // wrong type
            fail("Expected NotFoundException");
        } catch (NotFoundException e) { }
    }


    /** Test getFloat(). */
    @Test public void testGetFloat() {
        GwtConfigUtils utils = new GwtConfigUtils();

        assertEquals(16.0f, utils.getFloat(TestConfig.class, "testFloatValue"), 0.0);

        try {
            utils.getFloat(TestConfig.class, "bogus");
            fail("Expected NotFoundException");
        } catch (NotFoundException e) { }

        try {
            utils.getFloat(TestConfig.class, "testStringValue"); // wrong type
            fail("Expected NotFoundException");
        } catch (NotFoundException e) { }
    }

    /** Test getInteger(). */
    @Test public void testGetInteger() {
        GwtConfigUtils utils = new GwtConfigUtils();

        assertEquals(new Integer(5), utils.getInteger(TestConfig.class, "testIntegerValue"));

        try {
            utils.getInteger(TestConfig.class, "bogus");
            fail("Expected NotFoundException");
        } catch (NotFoundException e) { }

        try {
            utils.getInteger(TestConfig.class, "testStringValue"); // wrong type
            fail("Expected NotFoundException");
        } catch (NotFoundException e) { }
    }

    /** Test getString(). */
    @Test public void testGetString() {
        GwtConfigUtils utils = new GwtConfigUtils();

        assertEquals("StringValue", utils.getString(TestConfig.class, "testStringValue"));

        try {
            utils.getString(TestConfig.class, "bogus");
            fail("Expected NotFoundException");
        } catch (NotFoundException e) { }

        try {
            utils.getString(TestConfig.class, "testIntegerValue"); // wrong type
            fail("Expected NotFoundException");
        } catch (NotFoundException e) { }
    }

    /** Test getMessage(). */
    @Test public void testGetMessage() {
        GwtConfigUtils utils = new GwtConfigUtils();

        assertEquals("Message", utils.getMessage(TestMessages.class, "testMessage"));

        try {
            utils.getMessage(TestMessages.class, "bogus");
            fail("Expected NotFoundException");
        } catch (NotFoundException e) { }
    }

}
