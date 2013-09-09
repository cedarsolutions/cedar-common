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
package com.cedarsolutions.wiring.gwt.rpc;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import org.junit.Test;

import com.cedarsolutions.exception.ServiceException;
import com.cedarsolutions.server.service.IXsrfTokenService;
import com.google.gwt.user.client.rpc.RpcToken;


/**
 * Unit tests for SecuredServiceExporter.
 * @author Kenneth J. Pronovici <pronovic@ieee.org>
 */
public class SecuredServiceExporterTest {

    /** Test the constructor. */
    @Test public void testConstructor() {
        IXsrfTokenService xsrfTokenService = mock(IXsrfTokenService.class);
        SecuredServiceExporter exporter = new SecuredServiceExporter(false, xsrfTokenService);
        assertNotNull(exporter);
        assertFalse(exporter.getEnableXsrfProtection());
        assertFalse(exporter.getShouldCheckPermutationStrongName());
        assertSame(xsrfTokenService, exporter.getXsrfTokenService());
    }

    /** Test doUnexpectedFailure(). */
    @Test public void testDoUnexpectedFailure() {
        IXsrfTokenService xsrfTokenService = mock(IXsrfTokenService.class);
        SecuredServiceExporter exporter = new SecuredServiceExporter(true, xsrfTokenService);
        Exception unexpectedFailure = new Exception("This is a security error, or something like that");

        try {
            exporter.doUnexpectedFailure(unexpectedFailure);
            fail("Expected ServiceException");
        } catch (ServiceException e) {
            assertNotNull(e);
            assertSame(e.getCause(), unexpectedFailure);
        }
    }

    /** Test get/setEnableXsrfProtection(). */
    @Test public void testGetSetEnableXsrfProtection() {
        SecuredServiceExporter exporter = new SecuredServiceExporter(false, null);
        assertFalse(exporter.getShouldCheckPermutationStrongName());

        exporter.setEnableXsrfProtection(true);
        assertTrue(exporter.getEnableXsrfProtection());

        exporter.setEnableXsrfProtection(false);
        assertFalse(exporter.getEnableXsrfProtection());
    }

    /** Test get/setShouldCheckPermutationStrongName(). */
    @Test public void testGetSetShouldCheckPermutationStrongName() {
        SecuredServiceExporter exporter = new SecuredServiceExporter(false, null);
        assertFalse(exporter.getShouldCheckPermutationStrongName());

        exporter.setShouldCheckPermutationStrongName(true);
        assertTrue(exporter.getShouldCheckPermutationStrongName());

        exporter.setShouldCheckPermutationStrongName(false);
        assertFalse(exporter.getShouldCheckPermutationStrongName());
    }

    /** Test get/setXsrfTokenService(). */
    @Test public void testGetSetSessionCookieName() {
        IXsrfTokenService xsrfTokenService = mock(IXsrfTokenService.class);
        SecuredServiceExporter exporter = new SecuredServiceExporter(false, null);
        assertNull(exporter.getXsrfTokenService());

        exporter.setXsrfTokenService(xsrfTokenService);
        assertSame(xsrfTokenService, exporter.getXsrfTokenService());

        exporter.setXsrfTokenService(null);
        assertNull(exporter.getXsrfTokenService());
    }

    /** Test validateXsrfToken(). */
    @Test public void testValidateXsrfToken() {
        IXsrfTokenService xsrfTokenService = mock(IXsrfTokenService.class);
        SecuredServiceExporter exporter = new SecuredServiceExporter(false, null);
        exporter.setXsrfTokenService(xsrfTokenService);

        RpcToken token = mock(RpcToken.class);
        exporter.validateXsrfToken(token);
        verify(xsrfTokenService).validateXsrfToken(token);
    }
}
