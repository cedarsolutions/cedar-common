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
import static org.junit.Assert.assertNotNull;

import org.junit.Test;

/**
 * Unit tests for GaeRole.
 * @author Kenneth J. Pronovici <pronovic@ieee.org>
 */
public class GaeRoleTest {

    /** Test the constructor. */
    @Test public void testConstructor() {
        GaeRole role = new GaeRole("whatever");
        assertNotNull(role);
        assertEquals("whatever", role.getAuthority());
    }

    /** Spot-check the constants. */
    @Test public void testConstants() {
        assertEquals("ROLE_ANONYMOUS", GaeRole.ROLE_ANONYMOUS.getAuthority());
        assertEquals("ROLE_USER", GaeRole.ROLE_USER.getAuthority());
        assertEquals("ROLE_ADMIN", GaeRole.ROLE_ADMIN.getAuthority());
    }

}
