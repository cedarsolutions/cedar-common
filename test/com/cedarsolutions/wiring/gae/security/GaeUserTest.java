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
package com.cedarsolutions.wiring.gae.security;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.Test;
import org.springframework.security.GrantedAuthority;

import com.google.appengine.api.users.User;

/**
 * Unit tests for GaeUser.
 * @author Kenneth J. Pronovici <pronovic@ieee.org>
 */
public class GaeUserTest {

    /** Test the default constructor. */
    @Test public void testDefaultConstructor() {
        GaeUser gaeUser = new GaeUser();
        assertNotNull(gaeUser);
        assertEquals("", gaeUser.getAuthDomain());
        assertEquals("", gaeUser.getFederatedIdentity());
        assertEquals("", gaeUser.getEmail());
        assertEquals(GaeUser.ANONYMOUS_USER_ID, gaeUser.getUserId());
        assertEquals(1, gaeUser.getRoles().size());
        assertTrue(gaeUser.getRoles().contains(GaeRole.ROLE_ANONYMOUS));
        assertEquals(1, gaeUser.getAuthorities().length);  // will validate contents elsewhere
        assertContains(GaeRole.ROLE_ANONYMOUS, gaeUser.getAuthorities());
        assertNotNull(gaeUser.toString());
        assertTrue(gaeUser.isAnonymous());
        assertFalse(gaeUser.isAdmin());
    }

    /** Test initializeCredentials(). */
    @Test public void testInitializeCredentials() {
        User user = new User("email", "authDomain", "userId", "federatedIdentity");

        GaeUser gaeUser = new GaeUser();
        gaeUser.initializeCredentials(user, false, null);
        assertNotNull(gaeUser);
        assertEquals(user.getAuthDomain(), gaeUser.getAuthDomain());
        assertEquals(user.getFederatedIdentity(), gaeUser.getFederatedIdentity());
        assertEquals(user.getEmail(), gaeUser.getEmail());
        assertEquals(user.getUserId(), gaeUser.getUserId());
        assertEquals(2, gaeUser.getRoles().size());
        assertTrue(gaeUser.getRoles().contains(GaeRole.ROLE_ANONYMOUS));
        assertTrue(gaeUser.getRoles().contains(GaeRole.ROLE_USER));
        assertEquals(2, gaeUser.getAuthorities().length);
        assertContains(GaeRole.ROLE_ANONYMOUS, gaeUser.getAuthorities());
        assertContains(GaeRole.ROLE_USER, gaeUser.getAuthorities());
        assertNotNull(gaeUser.toString());
        assertFalse(gaeUser.isAnonymous());
        assertFalse(gaeUser.isAdmin());

        gaeUser = new GaeUser();
        gaeUser.initializeCredentials(user, true, null);
        assertNotNull(gaeUser);
        assertEquals(user.getAuthDomain(), gaeUser.getAuthDomain());
        assertEquals(user.getFederatedIdentity(), gaeUser.getFederatedIdentity());
        assertEquals(user.getEmail(), gaeUser.getEmail());
        assertEquals(user.getUserId(), gaeUser.getUserId());
        assertEquals(3, gaeUser.getRoles().size());
        assertTrue(gaeUser.getRoles().contains(GaeRole.ROLE_ANONYMOUS));
        assertTrue(gaeUser.getRoles().contains(GaeRole.ROLE_USER));
        assertTrue(gaeUser.getRoles().contains(GaeRole.ROLE_ADMIN));
        assertEquals(3, gaeUser.getAuthorities().length);
        assertContains(GaeRole.ROLE_ANONYMOUS, gaeUser.getAuthorities());
        assertContains(GaeRole.ROLE_USER, gaeUser.getAuthorities());
        assertContains(GaeRole.ROLE_ADMIN, gaeUser.getAuthorities());
        assertNotNull(gaeUser.toString());
        assertFalse(gaeUser.isAnonymous());
        assertTrue(gaeUser.isAdmin());

        gaeUser = new GaeUser();
        gaeUser.initializeCredentials(user, true, new String[] { "ONE", "TWO", });
        assertNotNull(gaeUser);
        assertEquals(user.getAuthDomain(), gaeUser.getAuthDomain());
        assertEquals(user.getFederatedIdentity(), gaeUser.getFederatedIdentity());
        assertEquals(user.getEmail(), gaeUser.getEmail());
        assertEquals(user.getUserId(), gaeUser.getUserId());
        assertEquals(5, gaeUser.getRoles().size());
        assertTrue(gaeUser.getRoles().contains(GaeRole.ROLE_ANONYMOUS));
        assertTrue(gaeUser.getRoles().contains(GaeRole.ROLE_USER));
        assertTrue(gaeUser.getRoles().contains(GaeRole.ROLE_ADMIN));
        assertTrue(gaeUser.getRoles().contains(new GaeRole("ONE")));
        assertTrue(gaeUser.getRoles().contains(new GaeRole("TWO")));
        assertEquals(5, gaeUser.getAuthorities().length);
        assertContains(GaeRole.ROLE_ANONYMOUS, gaeUser.getAuthorities());
        assertContains(GaeRole.ROLE_USER, gaeUser.getAuthorities());
        assertContains(GaeRole.ROLE_ADMIN, gaeUser.getAuthorities());
        assertContains(new GaeRole("ONE"), gaeUser.getAuthorities());
        assertContains(new GaeRole("TWO"), gaeUser.getAuthorities());
        assertNotNull(gaeUser.toString());
        assertFalse(gaeUser.isAnonymous());
        assertTrue(gaeUser.isAdmin());
    }

    /** Assert that an array contains a role. */
    private static void assertContains(GaeRole expected, GrantedAuthority[] authorities) {
        if (authorities != null && authorities.length >= 1) {
            for (GrantedAuthority authority : authorities) {
                assertTrue(authority instanceof GaeRole);
                GaeRole gaeRole = (GaeRole) authority;
                if (gaeRole.equals(expected)) {
                    return;  // get out, since the condition was met
                }
            }
        }

        fail("Did not find expected authority: " + expected);
    }
}
