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
package com.cedarsolutions.wiring.gae.security;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.Test;
import org.springframework.security.Authentication;
import org.springframework.security.GrantedAuthority;

/**
 * Unit tests for GaeAuthenticationProvider.
 * @author Kenneth J. Pronovici <pronovic@ieee.org>
 */
public class GaeAuthenticationProviderTest {

    /** Test the supports() method. */
    @Test public void testSupports() {
        assertFalse(new GaeAuthenticationProvider().supports(null));
        assertFalse(new GaeAuthenticationProvider().supports(String.class));
        assertTrue(new GaeAuthenticationProvider().supports(GaeUserAuthenticationToken.class));
    }

    /** Test the authenticate() method. */
    @Test public void testAuthenticate() {
        GaeUser gaeUser = new GaeUser();
        Authentication authentication = mock(Authentication.class);
        when(authentication.getPrincipal()).thenReturn(gaeUser);
        GaeAuthenticationProvider provider = new GaeAuthenticationProvider();
        Authentication token = provider.authenticate(authentication);
        assertTrue(token instanceof GaeUserAuthenticationToken);
        GaeUserAuthenticationToken gaeToken = (GaeUserAuthenticationToken) token;
        assertEquals(gaeUser, gaeToken.getPrincipal());
        assertEquals(1, gaeToken.getAuthorities().length);
        assertContains(GaeRole.ROLE_ANONYMOUS, gaeToken.getAuthorities());
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
