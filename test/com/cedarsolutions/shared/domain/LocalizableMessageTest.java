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
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

/**
 * Unit tests for LocalizableMessage.
 * @author Kenneth J. Pronovici <pronovic@ieee.org>
 */
@SuppressWarnings("unlikely-arg-type")
public class LocalizableMessageTest {

    /** Check GWT behavior. */
    @Test public void testGwtBehavior() {
        // Anything GWT uses must be serializable and must have a zero-arg constructor.
        LocalizableMessage obj = new LocalizableMessage();
        assertTrue(obj instanceof TranslatableDomainObject);
        assertTrue(obj instanceof Serializable);
    }

    /** Test the constructors and getters. */
    @Test public void testConstructors() {
        LocalizableMessage message = null;

        message = new LocalizableMessage();
        assertNotNull(message);
        assertNull(message.getKey());
        assertNull(message.getContext());
        assertNull(message.getText());
        assertFalse(message.isTranslatable());

        message = new LocalizableMessage("text");
        assertNotNull(message);
        assertNull(message.getKey());
        assertNull(message.getContext());
        assertEquals("text", message.getText());
        assertFalse(message.isTranslatable());

        message = new LocalizableMessage("key", "text");
        assertNotNull(message);
        assertEquals("key", message.getKey());
        assertNull(message.getContext());
        assertEquals("text", message.getText());
        assertTrue(message.isTranslatable());

        message = new LocalizableMessage("key", "context", "text");
        assertNotNull(message);
        assertEquals("key", message.getKey());
        assertEquals("context", message.getContext());
        assertEquals("text", message.getText());
        assertTrue(message.isTranslatable());

        LocalizableMessage copy = new LocalizableMessage(message);
        assertNotNull(copy);
        assertEquals("key", copy.getKey());
        assertEquals("context", copy.getContext());
        assertEquals("text", copy.getText());
        assertTrue(message.isTranslatable());
    }

    /** Test equals(). */
    @Test public void testEquals() {
        LocalizableMessage message1;
        LocalizableMessage message2;

        message1 = createLocalizableMessage();
        message2 = createLocalizableMessage();
        assertTrue(message1.equals(message2));
        assertTrue(message2.equals(message1));

        try {
            message1 = createLocalizableMessage();
            message2 = null;
            message1.equals(message2);
            fail("Expected NullPointerException");
        } catch (NullPointerException e) { }

        try {
            message1 = createLocalizableMessage();
            message1.equals("blech");
            fail("Expected ClassCastException");
        } catch (ClassCastException e) { }

        message1 = createLocalizableMessage();
        message2 = createLocalizableMessage();
        message2.key = "X";
        assertFalse(message1.equals(message2));
        assertFalse(message2.equals(message1));

        message1 = createLocalizableMessage();
        message2 = createLocalizableMessage();
        message2.context = "X";
        assertFalse(message1.equals(message2));
        assertFalse(message2.equals(message1));

        message1 = createLocalizableMessage();
        message2 = createLocalizableMessage();
        message2.text = "X";
        assertFalse(message1.equals(message2));
        assertFalse(message2.equals(message1));
    }

    /** Test hashCode(). */
    @Test public void testHashCode() {
        LocalizableMessage message1 = createLocalizableMessage();
        message1.key = "one";

        LocalizableMessage message2 = createLocalizableMessage();
        message2.context = "two";

        LocalizableMessage message3 = createLocalizableMessage();
        message3.text = "three";

        LocalizableMessage message4 = createLocalizableMessage();
        message4.key = "one";  // same as message1

        Map<LocalizableMessage, String> map = new HashMap<LocalizableMessage, String>();
        map.put(message1, "ONE");
        map.put(message2, "TWO");
        map.put(message3, "THREE");

        assertEquals("ONE", map.get(message1));
        assertEquals("TWO", map.get(message2));
        assertEquals("THREE", map.get(message3));
        assertEquals("ONE", map.get(message4));
    }

    /** Create a LocalizableMessage for testing. */
    private static LocalizableMessage createLocalizableMessage() {
        return new LocalizableMessage("key", "context", "text");
    }
}
