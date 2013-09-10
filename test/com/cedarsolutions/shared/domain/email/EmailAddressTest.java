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
package com.cedarsolutions.shared.domain.email;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

/**
 * Unit tests for EmailAddress.
 * @author Kenneth J. Pronovici <pronovic@ieee.org>
 */
public class EmailAddressTest {

    /** Test the constructor. */
    @Test public void testConstructor() {
        EmailAddress address = new EmailAddress();
        assertNotNull(address);
        assertNull(address.getName());
        assertNull(address.getAddress());

        address = new EmailAddress("address");
        assertNotNull(address);
        assertNull(address.getName());
        assertEquals("address", address.getAddress());

        address = new EmailAddress("name", "address");
        assertNotNull(address);
        assertEquals("name", address.getName());
        assertEquals("address", address.getAddress());
    }

    /** Test toString(). */
    @Test public void testToString() {
        EmailAddress address = new EmailAddress("name", "address");
        assertEquals("\"name\" <address>", address.toString());

        address.setName(null);
        assertEquals("address", address.toString());

        address.setAddress(null);
        assertEquals("", address.toString());
    }

    /** Test the getters and setters. */
    @Test public void testGettersSetters() {
        EmailAddress address = new EmailAddress();

        address.setName("name");
        assertEquals("name", address.getName());

        address.setAddress("name@example.com");
        assertEquals("name@example.com", address.getAddress());
    }

    /** Test equals(). */
    @Test public void testEquals() {
        EmailAddress address1;
        EmailAddress address2;

        address1 = createEmailAddress();
        address2 = createEmailAddress();
        assertTrue(address1.equals(address2));
        assertTrue(address2.equals(address1));

        try {
            address1 = createEmailAddress();
            address2 = null;
            address1.equals(address2);
            fail("Expected NullPointerException");
        } catch (NullPointerException e) { }

        try {
            address1 = createEmailAddress();
            address2 = null;
            address1.equals("blech");
            fail("Expected ClassCastException");
        } catch (ClassCastException e) { }

        address1 = createEmailAddress();
        address2 = createEmailAddress();
        address2.setName("X");
        assertFalse(address1.equals(address2));
        assertFalse(address2.equals(address1));

        address1 = createEmailAddress();
        address2 = createEmailAddress();
        address2.setAddress("X");
        assertFalse(address1.equals(address2));
        assertFalse(address2.equals(address1));
    }

    /** Test hashCode(). */
    @Test public void testHashCode() {
        EmailAddress address1 = createEmailAddress();
        address1.setName("X");

        EmailAddress address2 = createEmailAddress();
        address2.setAddress("X");

        EmailAddress address3 = createEmailAddress();
        address3.setName("X");

        Map<EmailAddress, String> map = new HashMap<EmailAddress, String>();
        map.put(address1, "ONE");
        map.put(address2, "TWO");

        assertEquals("ONE", map.get(address1));
        assertEquals("TWO", map.get(address2));
        assertEquals("ONE", map.get(address3));
    }

    /** Create a EmailAddress for testing. */
    private static EmailAddress createEmailAddress() {
        EmailAddress address = new EmailAddress();

        address.setName("name");
        address.setAddress("address");

        return address;
    }
}
