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
package com.cedarsolutions.wiring.gwt.rpc;

import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.Test;

import com.google.gwt.core.ext.GeneratorContext;
import com.google.gwt.core.ext.TreeLogger;
import com.google.gwt.core.ext.typeinfo.JClassType;
import com.google.gwt.user.rebind.rpc.ProxyCreator;
import com.google.gwt.user.server.rpc.XsrfProtect;

/**
 * Unit tests for XsrfRpcProxyGenerator.
 * @author Kenneth J. Pronovici <pronovic@ieee.org>
 */
public class XsrfRpcProxyGeneratorTest {

    /** Test createProxyCreator() for an annotated class. */
    @Test public void testCreateProxyCreatorAnnotated() {
        XsrfRpcProxyGenerator generator = new XsrfRpcProxyGenerator();
        JClassType remoteService = mock(JClassType.class);
        when(remoteService.isAnnotationPresent(XsrfProtect.class)).thenReturn(true);
        ProxyCreator creator = generator.createProxyCreator(remoteService);
        assertTrue(creator instanceof XsrfRpcProxyCreator);
    }

    /** Test createProxyCreator() for a class that's not annotated. */
    @Test public void testCreateProxyCreatorNotAnnotated() {
        XsrfRpcProxyGenerator generator = new XsrfRpcProxyGenerator();
        JClassType remoteService = mock(JClassType.class);
        when(remoteService.isAnnotationPresent(XsrfProtect.class)).thenReturn(false);
        ProxyCreator creator = generator.createProxyCreator(remoteService);
        assertTrue(creator instanceof XsrfRpcProxyCreator);  // decision is down in creator itself
    }

    /** Test setLogger(). */
    @Test public void testSetLogger() {
        TreeLogger logger1 = mock(TreeLogger.class);
        TreeLogger logger2 = mock(TreeLogger.class);

        XsrfRpcProxyGenerator generator = new XsrfRpcProxyGenerator();
        assertNull(generator.logger);

        generator.setLogger(logger1);
        assertSame(logger1, generator.logger);

        generator.setLogger(logger2);
        assertSame(logger1, generator.logger);
    }

    /** Test setGeneratorContext(). */
    @Test public void testSetGeneratorContext() {
        GeneratorContext context1 = mock(GeneratorContext.class);
        GeneratorContext context2 = mock(GeneratorContext.class);

        XsrfRpcProxyGenerator generator = new XsrfRpcProxyGenerator();
        assertNull(generator.generatorContext);

        generator.setGeneratorContext(context1);
        assertSame(context1, generator.generatorContext);

        generator.setGeneratorContext(context2);
        assertSame(context1, generator.generatorContext);
    }

}
