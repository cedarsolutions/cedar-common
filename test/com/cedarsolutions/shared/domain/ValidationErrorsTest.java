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
package com.cedarsolutions.shared.domain;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;

/**
 * Unit tests for ValidationErrors.
 * @author Kenneth J. Pronovici <pronovic@ieee.org>
 */
public class ValidationErrorsTest {

    /** Check GWT behavior. */
    @Test public void testGwtBehavior() {
        // Anything GWT uses must be serializable and must have a zero-arg constructor.
        ValidationErrors obj = new ValidationErrors();
        assertTrue(obj instanceof TranslatableDomainObject);
        assertTrue(obj instanceof Serializable);
    }

    /** Test the constructor. */
    @Test public void testConstructors() {
        LocalizableMessage summary = new LocalizableMessage("whatever");
        List<LocalizableMessage> messages = new ArrayList<LocalizableMessage>();

        ValidationErrors errors = new ValidationErrors();
        assertNotNull(errors);
        assertNull(errors.getSummary());
        assertTrue(errors.getMessages().isEmpty());

        errors = new ValidationErrors("key", "text");
        assertNotNull(errors);
        assertEquals("key", errors.getSummary().getKey());
        assertNull(errors.getSummary().getContext());
        assertEquals("text", errors.getSummary().getText());

        errors = new ValidationErrors("key", "context", "text");
        assertNotNull(errors);
        assertEquals("key", errors.getSummary().getKey());
        assertEquals("context", errors.getSummary().getContext());
        assertEquals("text", errors.getSummary().getText());

        errors = new ValidationErrors(summary);
        assertNotNull(errors);
        assertSame(summary, errors.getSummary());
        assertTrue(errors.getMessages().isEmpty());

        errors = new ValidationErrors(summary, messages);
        assertNotNull(errors);
        assertSame(summary, errors.getSummary());
        assertSame(messages, errors.getMessages());
    }

    /** Test the getters and setters. */
    @Test public void testGettersSetters() {
        ValidationErrors errors = new ValidationErrors();

        LocalizableMessage summary = new LocalizableMessage("whatever");
        errors.setSummary(summary);
        assertSame(summary, errors.getSummary());

        List<LocalizableMessage> messages = new ArrayList<LocalizableMessage>();
        errors.setMessages(messages);
        assertSame(messages, errors.getMessages());
    }

    /** Test setSummary(). */
    @Test public void testSetSummary() {
        ValidationErrors errors = new ValidationErrors();
        assertNull(errors.getSummary());

        errors.setSummary("key", "text");
        assertEquals(new LocalizableMessage("key", "text"), errors.getSummary());

        errors.setSummary("key", "context", "text");
        assertEquals(new LocalizableMessage("key", "context", "text"), errors.getSummary());

        errors.setSummary(new LocalizableMessage("key", "context", "text"));
        assertEquals(new LocalizableMessage("key", "context", "text"), errors.getSummary());
    }

    /** Test addMessage(). */
    @Test public void testAddMessage() {
        LocalizableMessage message = new LocalizableMessage("whatever");

        ValidationErrors errors = new ValidationErrors();
        assertFalse(errors.hasMessages());

        errors.addMessage(message);
        assertTrue(errors.hasMessages());
        assertEquals(1, errors.getMessages().size());
        assertSame(message, errors.getMessages().get(0));

        errors.addMessage("key1", "text1");
        assertTrue(errors.hasMessages());
        assertEquals(2, errors.getMessages().size());
        assertSame(message, errors.getMessages().get(0));
        assertEquals("key1", errors.getMessages().get(1).getKey());
        assertNull(errors.getMessages().get(1).getContext());
        assertEquals("text1", errors.getMessages().get(1).getText());

        errors.addMessage("key2", "context", "text2");
        assertTrue(errors.hasMessages());
        assertEquals(3, errors.getMessages().size());
        assertSame(message, errors.getMessages().get(0));
        assertEquals("key1", errors.getMessages().get(1).getKey());
        assertNull(errors.getMessages().get(1).getContext());
        assertEquals("text1", errors.getMessages().get(1).getText());
        assertEquals("key2", errors.getMessages().get(2).getKey());
        assertEquals("context", errors.getMessages().get(2).getContext());
        assertEquals("text2", errors.getMessages().get(2).getText());
    }

    /** Test equals(). */
    @Test public void testEquals() {
        ValidationErrors errors1;
        ValidationErrors errors2;

        try {
            errors1 = createValidationErrors();
            errors2 = null;
            errors1.equals(errors2);
            fail("Expected NullPointerException");
        } catch (NullPointerException e) { }

        try {
            errors1 = createValidationErrors();
            errors1.equals("blech");
            fail("Expected ClassCastException");
        } catch (ClassCastException e) { }

        errors1 = createValidationErrors();
        errors2 = createValidationErrors();
        assertTrue(errors1.equals(errors2));
        assertTrue(errors2.equals(errors1));

        errors1 = createValidationErrors();
        errors2 = createValidationErrors();
        errors2.setSummary("blech", "blech");
        assertFalse(errors1.equals(errors2));
        assertFalse(errors2.equals(errors1));

        errors1 = createValidationErrors();
        errors2 = createValidationErrors();
        errors2.addMessage("blech", "blech");
        assertFalse(errors1.equals(errors2));
        assertFalse(errors2.equals(errors1));
    }

    /** Test hashCode(). */
    @Test public void testHashCode() {
        ValidationErrors errors1 = createValidationErrors();
        errors1.setSummary("blech", "blech");

        ValidationErrors errors2 = createValidationErrors();
        errors2.addMessage("blech", "blech");

        ValidationErrors errors3 = createValidationErrors();
        errors3.setSummary("blech", "blech");  // same as errors1

        Map<ValidationErrors, String> map = new HashMap<ValidationErrors, String>();
        map.put(errors1, "ONE");
        map.put(errors2, "TWO");

        assertEquals("ONE", map.get(errors1));
        assertEquals("TWO", map.get(errors2));
        assertEquals("ONE", map.get(errors3));
    }

    /** Create a ValidationErrors for testing. */
    private static ValidationErrors createValidationErrors() {
        ValidationErrors errors = new ValidationErrors();

        errors.setSummary("key", "text");
        errors.addMessage("key2", "text2");

        return errors;
    }
}
