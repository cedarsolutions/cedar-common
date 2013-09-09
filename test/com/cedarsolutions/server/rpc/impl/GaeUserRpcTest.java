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
package com.cedarsolutions.server.rpc.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.Test;

import com.cedarsolutions.exception.NotConfiguredException;
import com.cedarsolutions.server.service.IGaeUserService;
import com.cedarsolutions.shared.domain.FederatedUser;
import com.cedarsolutions.shared.domain.OpenIdProvider;

/**
 * Unit tests for GaeUserRpc.
 * @author Kenneth J. Pronovici <pronovic@ieee.org>
 */
public class GaeUserRpcTest {

    /** Test constructor, getters and setters. */
    @Test public void testConstructor() {
        GaeUserRpc rpc = new GaeUserRpc();
        assertNull(rpc.getGaeUserService());

        IGaeUserService gaeUserService = mock(IGaeUserService.class);
        rpc.setGaeUserService(gaeUserService);
        assertSame(gaeUserService, rpc.getGaeUserService());
    }

    /** Test the afterPropertiesSet() method. */
    @Test public void testAfterPropertiesSet() throws Exception {
        GaeUserRpc rpc = new GaeUserRpc();
        IGaeUserService gaeUserService = mock(IGaeUserService.class);

        try {
            rpc.setGaeUserService(null);
            rpc.afterPropertiesSet();
            fail("Expected NotConfiguredException");
        } catch (NotConfiguredException e) { }

        rpc.setGaeUserService(gaeUserService);
        rpc.afterPropertiesSet();
    }

    /** Test getLoginUrl(). */
    @Test public void testGetLoginUrl() {
        IGaeUserService gaeUserService = mock(IGaeUserService.class);
        GaeUserRpc rpc = new GaeUserRpc();
        rpc.setGaeUserService(gaeUserService);

        when(gaeUserService.getLoginUrl(OpenIdProvider.GOOGLE, "dest")).thenReturn("whatever");
        assertEquals("whatever", rpc.getLoginUrl(OpenIdProvider.GOOGLE, "dest"));
    }

    /** Test getLogoutUrl(). */
    @Test public void testGetLogoutUrl() {
        IGaeUserService gaeUserService = mock(IGaeUserService.class);
        GaeUserRpc rpc = new GaeUserRpc();
        rpc.setGaeUserService(gaeUserService);

        when(gaeUserService.getLogoutUrl("dest")).thenReturn("whatever");
        assertEquals("whatever", rpc.getLogoutUrl("dest"));
    }

    /** Test isUserLoggedIn(). */
    @Test public void testIsUserLoggedIn() {
        IGaeUserService gaeUserService = mock(IGaeUserService.class);
        GaeUserRpc rpc = new GaeUserRpc();
        rpc.setGaeUserService(gaeUserService);

        when(gaeUserService.isUserLoggedIn()).thenReturn(true);
        assertTrue(rpc.isUserLoggedIn());

        when(gaeUserService.isUserLoggedIn()).thenReturn(false);
        assertFalse(rpc.isUserLoggedIn());
    }

    /** Test isUserAdmin(). */
    @Test public void testIsUserAdmin() {
        IGaeUserService gaeUserService = mock(IGaeUserService.class);
        GaeUserRpc rpc = new GaeUserRpc();
        rpc.setGaeUserService(gaeUserService);

        when(gaeUserService.isUserAdmin()).thenReturn(true);
        assertTrue(rpc.isUserAdmin());

        when(gaeUserService.isUserAdmin()).thenReturn(false);
        assertFalse(rpc.isUserAdmin());
    }

    /** Test getCurrentUser(). */
    @Test public void testGetCurrentUser() {
        IGaeUserService gaeUserService = mock(IGaeUserService.class);
        GaeUserRpc rpc = new GaeUserRpc();
        rpc.setGaeUserService(gaeUserService);

        FederatedUser user = new FederatedUser();
        when(gaeUserService.getCurrentUser()).thenReturn(user);
        assertSame(user, rpc.getCurrentUser());
    }

}
