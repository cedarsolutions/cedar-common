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

import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.fail;

import org.junit.Test;

/**
 * Unit tests for GaeUserAuthenticationToken.
 * @author Kenneth J. Pronovici <pronovic@ieee.org>
 */
public class GaeUserAuthenticationTokenTest {

    /** Test the constructors. */
    @Test public void testConstructors() {
        GaeUserAuthenticationToken token = null;
        String credentials = "whatever";
        GaeUser principal = new GaeUser();

        try {
            new GaeUserAuthenticationToken(null);
            fail("Expected NullPointerException");
        } catch (NullPointerException e) { }

        token = new GaeUserAuthenticationToken(principal);
        assertSame(principal, token.getPrincipal());
        assertNull(token.getCredentials());

        token = new GaeUserAuthenticationToken(principal, credentials);
        assertSame(principal, token.getPrincipal());
        assertSame(credentials, token.getCredentials());
    }
}
