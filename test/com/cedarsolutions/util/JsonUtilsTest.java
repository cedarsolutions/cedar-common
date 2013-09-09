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
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import java.util.Date;

import org.junit.Test;

import com.cedarsolutions.exception.CedarRuntimeException;

/**
 * Unit tests for JsonUtils.
 * @author Kenneth J. Pronovici <pronovic@ieee.org>
 */
public class JsonUtilsTest {

    /** Test a round-trip. */
    @Test public void testRoundTrip() {
        String string = "1";
        Date date = DateUtils.getCurrentDate();
        Integer integer = new Integer(111);
        Long longinteger = new Long(222);

        TestClass input = new TestClass(string, date, integer, longinteger);
        assertNotNull(input);
        assertEquals(string, input.getString());
        assertEquals(date, input.getDate());
        assertEquals(integer, input.getInteger());
        assertEquals(longinteger, input.getLonginteger());

        String jsonString = JsonUtils.getJsonString(input);
        assertNotNull(jsonString);

        TestClass result1 = (TestClass) JsonUtils.parseJsonString(jsonString, TestClass.class);
        assertNotNull(result1);
        assertEquals(string, result1.getString());
        assertEquals(date, result1.getDate());
        assertEquals(integer, result1.getInteger());
        assertEquals(longinteger, result1.getLonginteger());

        TestClass result2 = (TestClass) JsonUtils.parseJsonString(jsonString, "com.cedarsolutions.util.TestClass");
        assertNotNull(result1);
        assertEquals(string, result2.getString());
        assertEquals(date, result2.getDate());
        assertEquals(integer, result2.getInteger());
        assertEquals(longinteger, result2.getLonginteger());

        try {
            // We should get an explicit failure if the class is unknown
            JsonUtils.parseJsonString(jsonString, "com.cedarsolutions.util.TestClassX");
            fail("Expected CedarRuntimException");
        } catch (CedarRuntimeException e) { }
    }

}
