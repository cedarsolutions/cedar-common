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
package com.cedarsolutions.server.rpc.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.Test;

import com.cedarsolutions.exception.NotConfiguredException;
import com.cedarsolutions.server.service.IXsrfTokenService;

/**
 * Unit tests for XsrfTokenRpc.
 * @author Kenneth J. Pronovici <pronovic@ieee.org>
 */
public class XsrfTokenRpcTest {

    /** Test constructor, getters and setters. */
    @Test public void testConstructorGettersSetters() {
        IXsrfTokenService xsrfTokenService = mock(IXsrfTokenService.class);

        XsrfTokenRpc rpc = new XsrfTokenRpc();
        assertNull(rpc.getXsrfTokenService());

        rpc.setXsrfTokenService(xsrfTokenService);
        assertSame(xsrfTokenService, rpc.getXsrfTokenService());
    }

    /** Test afterPropertiesSet(). */
    @Test public void testAfterPropertieSet() {
        IXsrfTokenService xsrfTokenService = mock(IXsrfTokenService.class);
        XsrfTokenRpc rpc = new XsrfTokenRpc();

        rpc.setXsrfTokenService(xsrfTokenService);
        rpc.afterPropertiesSet();

        try {
            rpc.setXsrfTokenService(null);
            rpc.afterPropertiesSet();
            fail("Expected NotConfiguredException");
        } catch (NotConfiguredException e) { }
    }

    /** Test generateXsrfToken(). */
    @Test public void testGenerateXsrfToken() {
        IXsrfTokenService xsrfTokenService = mock(IXsrfTokenService.class);
        when(xsrfTokenService.generateXsrfToken()).thenReturn("Hello");
        XsrfTokenRpc rpc = new XsrfTokenRpc();
        rpc.setXsrfTokenService(xsrfTokenService);
        assertEquals("Hello", rpc.generateXsrfToken());
    }

}
