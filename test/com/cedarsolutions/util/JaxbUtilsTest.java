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
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.xml.bind.JAXBContext;

import org.junit.Test;

import com.cedarsolutions.exception.CedarRuntimeException;

/**
 * Unit tests for JaxbUtils.
 * @author Kenneth J. Pronovici <pronovic@ieee.org>
 */
public class JaxbUtilsTest {

    /** Test getJaxbContext(). */
    @Test public void testGetJaxbContext() {
        JAXBContext context1 = JaxbUtils.getInstance().getJaxbContext(TestClass.class);
        assertNotNull(context1);

        JAXBContext context2 = JaxbUtils.getInstance().getJaxbContext(TestClass.class.getName());
        assertSame(context1, context2);  // because the instance should be cached

        try {
            JaxbUtils.getInstance().getJaxbContext("bogus");
        } catch (CedarRuntimeException e) { }
    }

    /** Test a round-trip, no schema. */
    @Test public void testRoundTripNoSchema() {
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

        String serialized = JaxbUtils.getInstance().marshalDocument(input);
        assertNotNull(serialized);

        TestClass result = JaxbUtils.getInstance().unmarshalDocument(TestClass.class, serialized);
        assertNotNull(result);
        assertEquals(string, result.getString());
        assertEquals(date, result.getDate());
        assertEquals(integer, result.getInteger());
        assertEquals(longinteger, result.getLonginteger());
        assertEquals(list, result.getList());
    }

    /** Test a round-trip, with schema. */
    @Test public void testRoundTripWithSchema() {
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

        String serialized = JaxbUtils.getInstance().marshalDocument(input, "http://whatever/xsd/myschema.xsd");
        assertTrue(serialized.contains("xsi:schemaLocation=\"http://whatever/xsd/myschema.xsd\""));
        assertNotNull(serialized);

        TestClass result = JaxbUtils.getInstance().unmarshalDocument(TestClass.class, serialized);
        assertNotNull(result);
        assertEquals(string, result.getString());
        assertEquals(date, result.getDate());
        assertEquals(integer, result.getInteger());
        assertEquals(longinteger, result.getLonginteger());
        assertEquals(list, result.getList());
    }

    /** Test generating a schema. */
    @Test public void testGenerateSchema() {
        // Just make sure it doesn't blow up
        assertNotNull(JaxbUtils.getInstance().generateSchema(TestClass.class));
    }

}
