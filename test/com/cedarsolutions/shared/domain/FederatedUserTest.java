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
 * Unit tests for FederatedUser.
 * @author Kenneth J. Pronovici <pronovic@ieee.org>
 */
public class FederatedUserTest {

    /** Check GWT behavior. */
    @Test public void testGwtBehavior() {
        // Anything GWT uses must be serializable and must have a zero-arg constructor.
        FederatedUser obj = new FederatedUser();
        assertTrue(obj instanceof TranslatableDomainObject);
        assertTrue(obj instanceof Serializable);
    }

    /** Test the constructor. */
    @Test public void testConstructor() {
        FederatedUser federatedUser = new FederatedUser();
        assertNotNull(federatedUser);
        assertNull(federatedUser.getUserId());
        assertNull(federatedUser.getUserName());
        assertNull(federatedUser.getEmailAddress());
        assertNull(federatedUser.getAuthenticationDomain());
        assertNull(federatedUser.getOpenIdProvider());
        assertNull(federatedUser.getFederatedIdentity());
        assertFalse(federatedUser.isAdmin());
        assertFalse(federatedUser.isFirstLogin());
    }

    /** Test the getters and setters. */
    @Test public void testGettersSetters() {
        FederatedUser federatedUser = new FederatedUser();

        federatedUser.setUserId("userId");
        assertEquals("userId", federatedUser.getUserId());

        federatedUser.setUserName("userName");
        assertEquals("userName", federatedUser.getUserName());

        federatedUser.setEmailAddress("email");
        assertEquals("email", federatedUser.getEmailAddress());

        federatedUser.setAuthenticationDomain("gmail.com");
        assertEquals("gmail.com", federatedUser.getAuthenticationDomain());

        federatedUser.setOpenIdProvider(OpenIdProvider.AOL);
        assertEquals(OpenIdProvider.AOL, federatedUser.getOpenIdProvider());

        federatedUser.setFederatedIdentity("federatedIdentity");
        assertEquals("federatedIdentity", federatedUser.getFederatedIdentity());

        federatedUser.setAdmin(true);
        assertTrue(federatedUser.isAdmin());

        federatedUser.setFirstLogin(true);
        assertTrue(federatedUser.isFirstLogin());
    }

    /** Test equals(). */
    @Test public void testEquals() {
        FederatedUser user1;
        FederatedUser user2;

        user1 = createFederatedUser();
        user2 = createFederatedUser();
        assertTrue(user1.equals(user2));
        assertTrue(user2.equals(user1));

        try {
            user1 = createFederatedUser();
            user2 = null;
            user1.equals(user2);
            fail("Expected NullPointerException");
        } catch (NullPointerException e) { }

        try {
            user1 = createFederatedUser();
            user2 = null;
            user1.equals("blech");
            fail("Expected ClassCastException");
        } catch (ClassCastException e) { }

        user1 = createFederatedUser();
        user2 = createFederatedUser();
        user2.setUserId("X");
        assertFalse(user1.equals(user2));
        assertFalse(user2.equals(user1));

        user1 = createFederatedUser();
        user2 = createFederatedUser();
        user2.setUserName("X");
        assertFalse(user1.equals(user2));
        assertFalse(user2.equals(user1));

        user1 = createFederatedUser();
        user2 = createFederatedUser();
        user2.setEmailAddress("X");
        assertFalse(user1.equals(user2));
        assertFalse(user2.equals(user1));

        user1 = createFederatedUser();
        user2 = createFederatedUser();
        user2.setAuthenticationDomain("X");
        assertFalse(user1.equals(user2));
        assertFalse(user2.equals(user1));

        user1 = createFederatedUser();
        user2 = createFederatedUser();
        user2.setOpenIdProvider(OpenIdProvider.AOL);
        assertFalse(user1.equals(user2));
        assertFalse(user2.equals(user1));

        user1 = createFederatedUser();
        user2 = createFederatedUser();
        user2.setFederatedIdentity("X");
        assertFalse(user1.equals(user2));
        assertFalse(user2.equals(user1));

        user1 = createFederatedUser();
        user2 = createFederatedUser();
        user2.setAdmin(false);
        assertFalse(user1.equals(user2));
        assertFalse(user2.equals(user1));

        user1 = createFederatedUser();
        user2 = createFederatedUser();
        user2.setFirstLogin(true);
        assertTrue(user1.equals(user2));
        assertTrue(user2.equals(user1));
    }

    /** Test hashCode(). */
    @Test public void testHashCode() {
        FederatedUser user1 = createFederatedUser();
        user1.setUserId("X");

        FederatedUser user2 = createFederatedUser();
        user2.setUserName("X");

        FederatedUser user3 = createFederatedUser();
        user3.setEmailAddress("X");

        FederatedUser user4 = createFederatedUser();
        user4.setAuthenticationDomain("X");

        FederatedUser user5 = createFederatedUser();
        user5.setOpenIdProvider(OpenIdProvider.AOL);

        FederatedUser user6 = createFederatedUser();
        user6.setFederatedIdentity("X");

        FederatedUser user7 = createFederatedUser();
        user7.setAdmin(false);

        FederatedUser user8 = createFederatedUser();
        user8.setFirstLogin(false);

        FederatedUser user9 = createFederatedUser();
        user9.setUserId("X");  // same as user1

        Map<FederatedUser, String> map = new HashMap<FederatedUser, String>();
        map.put(user1, "ONE");
        map.put(user2, "TWO");
        map.put(user3, "THREE");
        map.put(user4, "FOUR");
        map.put(user5, "FIVE");
        map.put(user6, "SIX");
        map.put(user7, "SEVEN");
        map.put(user8, "EIGHT");

        assertEquals("ONE", map.get(user1));
        assertEquals("TWO", map.get(user2));
        assertEquals("THREE", map.get(user3));
        assertEquals("FOUR", map.get(user4));
        assertEquals("FIVE", map.get(user5));
        assertEquals("SIX", map.get(user6));
        assertEquals("SEVEN", map.get(user7));
        assertEquals("EIGHT", map.get(user8));
        assertEquals("ONE", map.get(user9));
    }

    /** Create a FederatedUser for testing. */
    private static FederatedUser createFederatedUser() {
        FederatedUser user = new FederatedUser();

        user.setUserId("userId");
        user.setUserName("userName");
        user.setEmailAddress("emailAddress");
        user.setAuthenticationDomain("domain");
        user.setOpenIdProvider(OpenIdProvider.GOOGLE);
        user.setFederatedIdentity("identity");
        user.setAdmin(true);
        user.setFirstLogin(true);

        return user;
    }

}
