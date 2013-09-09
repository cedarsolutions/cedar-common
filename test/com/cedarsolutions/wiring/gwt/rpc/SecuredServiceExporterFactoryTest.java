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
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.mock;

import org.gwtwidgets.server.spring.RPCServiceExporter;
import org.junit.Test;

import com.cedarsolutions.exception.NotConfiguredException;
import com.cedarsolutions.server.service.IXsrfTokenService;

/**
 * Unit tests for SecuredServiceExporterFactory.
 * @author Kenneth J. Pronovici <pronovic@ieee.org>
 */
public class SecuredServiceExporterFactoryTest {

    /** Test the constructor, getters and setters. */
    @Test public void testConstructor() {
        SecuredServiceExporterFactory factory = new SecuredServiceExporterFactory();
        assertFalse(factory.getEnableXsrfProtection());
        assertNull(factory.getXsrfTokenService());

        factory.setEnableXsrfProtection(true);
        assertTrue(factory.getEnableXsrfProtection());

        IXsrfTokenService xsrfTokenService = mock(IXsrfTokenService.class);
        factory.setXsrfTokenService(xsrfTokenService);
        assertSame(xsrfTokenService, factory.getXsrfTokenService());
    }

    /** Test afterPropertiesSet(). */
    @Test public void testAfterPropertiesSet() {
        IXsrfTokenService xsrfTokenService = mock(IXsrfTokenService.class);
        SecuredServiceExporterFactory factory = new SecuredServiceExporterFactory();

        factory.setEnableXsrfProtection(false);
        factory.setXsrfTokenService(xsrfTokenService);
        factory.afterPropertiesSet();

        factory.setEnableXsrfProtection(true);
        factory.setXsrfTokenService(xsrfTokenService);
        factory.afterPropertiesSet();

        try {
            factory.setEnableXsrfProtection(true);
            factory.setXsrfTokenService(null);
            factory.afterPropertiesSet();
            fail("Expected NotConfiguredException");
        } catch (NotConfiguredException e) { }
    }

    /** Test create(). */
    @Test public void testCreate() {
        RPCServiceExporter exporter = null;
        IXsrfTokenService xsrfTokenService = mock(IXsrfTokenService.class);
        SecuredServiceExporterFactory factory = new SecuredServiceExporterFactory();

        factory.setEnableXsrfProtection(false);
        factory.setXsrfTokenService(xsrfTokenService);
        exporter = factory.create();
        assertTrue(exporter instanceof SecuredServiceExporter);
        assertFalse(((SecuredServiceExporter) exporter).getEnableXsrfProtection());
        assertFalse(((SecuredServiceExporter) exporter).getShouldCheckPermutationStrongName());
        assertSame(xsrfTokenService, ((SecuredServiceExporter) exporter).getXsrfTokenService());
    }

}
