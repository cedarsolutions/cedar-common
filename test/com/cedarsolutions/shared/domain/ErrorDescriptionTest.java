/* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 *
 *              C E D A R
 *          S O L U T I O N S       "Software done right."
 *           S O F T W A R E
 *
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 *
 * Copyright (c) 2012 Kenneth J. Pronovici.
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
package com.cedarsolutions.shared.domain;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;

/**
 * Unit tests for ErrorDescription.
 * @author Kenneth J. Pronovici <pronovic@ieee.org>
 */
public class ErrorDescriptionTest {

    /** Test the constructors. */
    @Test public void testConstructors() {
        ErrorDescription error = null;
        Exception exception = new Exception("Hello");

        List<String> supportingTextItems = new ArrayList<String>();
        supportingTextItems.add("one");
        supportingTextItems.add("two");

        error = new ErrorDescription();
        assertNull(error.getMessage());
        assertNull(error.getException());
        assertTrue(error.getSupportingTextItems().isEmpty());

        error = new ErrorDescription("message");
        assertEquals("message", error.getMessage());
        assertNull(error.getException());
        assertTrue(error.getSupportingTextItems().isEmpty());

        error = new ErrorDescription("message", exception);
        assertEquals("message", error.getMessage());
        assertSame(exception, error.getException());
        assertTrue(error.getSupportingTextItems().isEmpty());

        error = new ErrorDescription("message", "supporting");
        assertEquals("message", error.getMessage());
        assertNull(error.getException());
        assertEquals(1, error.getSupportingTextItems().size());
        assertEquals("supporting", error.getSupportingTextItems().get(0));

        error = new ErrorDescription("message", exception, "supporting");
        assertEquals("message", error.getMessage());
        assertEquals(exception, error.getException());
        assertEquals(1, error.getSupportingTextItems().size());
        assertEquals("supporting", error.getSupportingTextItems().get(0));

        error = new ErrorDescription("message", exception, "supporting");
        assertEquals("message", error.getMessage());
        assertEquals(exception, error.getException());
        assertEquals(1, error.getSupportingTextItems().size());
        assertEquals("supporting", error.getSupportingTextItems().get(0));

        error = new ErrorDescription("message", supportingTextItems);
        assertEquals("message", error.getMessage());
        assertNull(error.getException());
        assertEquals(supportingTextItems, error.getSupportingTextItems());

        error = new ErrorDescription("message", exception, supportingTextItems);
        assertEquals("message", error.getMessage());
        assertEquals(exception, error.getException());
        assertEquals(supportingTextItems, error.getSupportingTextItems());
    }

    /** Test the getters and setters. */
    @Test public void testGettersSetters() {
        ErrorDescription error = new ErrorDescription();

        List<String> supportingTextItems = new ArrayList<String>();
        supportingTextItems.add("one");
        supportingTextItems.add("two");

        Exception exception = new Exception("Hello");
        error.setException(exception);
        assertEquals(exception, error.getException());

        error.setMessage("message");
        assertEquals("message", error.getMessage());

        error.setSupportingTextItems(supportingTextItems);
        assertEquals(supportingTextItems, error.getSupportingTextItems());
    }

    /** Test equals(). */
    @Test public void testEquals() {
        ErrorDescription error1;
        ErrorDescription error2;

        error1 = createErrorDescription();
        error2 = createErrorDescription();
        assertTrue(error1.equals(error2));
        assertTrue(error2.equals(error1));

        try {
            error1 = createErrorDescription();
            error2 = null;
            error1.equals(error2);
            fail("Expected NullPointerException");
        } catch (NullPointerException e) { }

        try {
            error1 = createErrorDescription();
            error2 = null;
            error1.equals("blech");
            fail("Expected ClassCastException");
        } catch (ClassCastException e) { }

        error1 = createErrorDescription();
        error2 = createErrorDescription();
        error2.setException(new Exception("blech"));
        assertFalse(error1.equals(error2));
        assertFalse(error2.equals(error1));

        error1 = createErrorDescription();
        error2 = createErrorDescription();
        error2.setMessage("X");
        assertFalse(error1.equals(error2));
        assertFalse(error2.equals(error1));

        error1 = createErrorDescription();
        error2 = createErrorDescription();
        error2.getSupportingTextItems().clear();
        assertFalse(error1.equals(error2));
        assertFalse(error2.equals(error1));

        error1 = createErrorDescription();
        error2 = createErrorDescription();
        error2.setException(null);
        assertFalse(error1.equals(error2));
        assertFalse(error2.equals(error1));

        error1 = createErrorDescription();
        error2 = createErrorDescription();
        error1.setException(null);
        assertFalse(error1.equals(error2));
        assertFalse(error2.equals(error1));

        error1 = createErrorDescription();
        error2 = createErrorDescription();
        error1.setException(null);
        error2.setException(null);
        assertTrue(error1.equals(error2));
        assertTrue(error2.equals(error1));
    }

    /** Test hashCode(). */
    @Test public void testHashCode() {
        ErrorDescription error1 = createErrorDescription();
        error1.setException(new Exception("blech"));

        ErrorDescription error2 = createErrorDescription();
        error2.setMessage("X");

        ErrorDescription error3 = createErrorDescription();
        error3.getSupportingTextItems().clear();

        ErrorDescription error4 = createErrorDescription();
        error4.setException(new Exception("blech")); // same as error1

        Map<ErrorDescription, String> map = new HashMap<ErrorDescription, String>();
        map.put(error1, "ONE");
        map.put(error2, "TWO");
        map.put(error3, "THREE");

        assertEquals("ONE", map.get(error1));
        assertEquals("TWO", map.get(error2));
        assertEquals("THREE", map.get(error3));
        assertEquals("ONE", map.get(error4));
    }

    /** Create a ErrorDescription for testing. */
    private static ErrorDescription createErrorDescription() {
        ErrorDescription error = new ErrorDescription();

        List<String> supporting = new ArrayList<String>();
        supporting.add("supporting");

        error.setException(new Exception("whatever"));
        error.setMessage("message");
        error.setSupportingTextItems(supporting);

        return error;
    }

}
