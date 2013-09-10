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

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Test;

/**
 * Unit tests for SerializationUtils.
 * @author Kenneth J. Pronovici <pronovic@ieee.org>
 */
public class SerializationUtilsTest {

    /** Test a round-trip. */
    @Test public void testRoundTrip() {
        String string = "1";
        Date date = DateUtils.getCurrentDate();
        Integer integer = new Integer(111);
        Long longinteger = new Long(222);
        List<String> list = new ArrayList<String>();
        list.add("one");
        list.add("two");

        TestClass input = new TestClass(string, date, integer, longinteger, list);
        assertNotNull(input);
        assertEquals(string, input.getString());
        assertEquals(date, input.getDate());
        assertEquals(integer, input.getInteger());
        assertEquals(longinteger, input.getLonginteger());

        String serialized = SerializationUtils.toString(input);
        assertNotNull(serialized);

        TestClass result = (TestClass) SerializationUtils.fromString(serialized);
        assertNotNull(result);
        assertEquals(string, result.getString());
        assertEquals(date, result.getDate());
        assertEquals(integer, result.getInteger());
        assertEquals(longinteger, result.getLonginteger());
        assertEquals(list, result.getList());
    }

}
