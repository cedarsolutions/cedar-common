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
package com.cedarsolutions.dao.gae.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.util.List;

import org.junit.Test;
import org.springframework.core.io.Resource;

import com.cedarsolutions.exception.CedarRuntimeException;
import com.cedarsolutions.exception.NotConfiguredException;
import com.cedarsolutions.junit.util.TestUtils;
import com.googlecode.objectify.Objectify;

/**
 * Unit tests for DaoObjectifyService.
 * A lot of this class is tested via actual DAO interactions in other tests.
 * @author Kenneth J. Pronovici <pronovic@ieee.org>
 */
public class DaoObjectifyServiceTest {

    /** Test the constructor, getters, and setters. */
    @Test public void testConstructor() {
        DaoObjectifyService service = new DaoObjectifyService();
        assertNull(service.getEntities());

        ObjectifyServiceProxy objectifyServiceProxy = mock(ObjectifyServiceProxy.class);
        service.setObjectifyServiceProxy(objectifyServiceProxy);
        assertSame(objectifyServiceProxy, service.getObjectifyServiceProxy());

        Resource entities = mock(Resource.class);
        service.setEntities(entities);
        assertSame(entities, service.getEntities());
    }

    /** Test the afterPropertiesSet() method. */
    @Test public void testAfterPropertiesSet() throws Exception {
        Resource invalid = TestUtils.createMockedResource(""); // invalid because no entities are configured
        Resource valid = TestUtils.createMockedResource("java.lang.String");
        ObjectifyServiceProxy objectifyServiceProxy = mock(ObjectifyServiceProxy.class);

        DaoObjectifyService service = new DaoObjectifyService();
        service.setObjectifyServiceProxy(objectifyServiceProxy);
        service.setEntities(valid);
        service.afterPropertiesSet();
        verify(objectifyServiceProxy).register(java.lang.String.class);

        try {
            service = new DaoObjectifyService();
            service.setObjectifyServiceProxy(objectifyServiceProxy);
            service.setEntities(null);  // required
            service.afterPropertiesSet();
            fail("Expected NotConfiguredException");
        } catch (NotConfiguredException e) { }

        try {
            service = new DaoObjectifyService();
            service.setObjectifyServiceProxy(null); // required
            service.setEntities(valid);
            service.afterPropertiesSet();
            fail("Expected NotConfiguredException");
        } catch (NotConfiguredException e) { }

        try {
            service = new DaoObjectifyService();
            service.setObjectifyServiceProxy(objectifyServiceProxy);
            service.setEntities(invalid);
            service.afterPropertiesSet();
            fail("Expected NotConfiguredException");
        } catch (NotConfiguredException e) { }
    }

    /** Test getObjectify(). */
    @Test public void testGetObjectify() {
        Objectify objectify = mock(Objectify.class);
        DaoObjectifyService service = createService();
        when(service.getObjectifyServiceProxy().begin()).thenReturn(objectify);
        ObjectifyProxy proxy = service.getObjectify();
        assertSame(objectify, proxy.getProxyTarget());
        assertFalse(proxy.isTransactional());
    }

    /** Test getObjectifyWithTransaction(). */
    @Test public void testGetObjectifyWithTransaction() {
        Objectify objectify = mock(Objectify.class);
        DaoObjectifyService service = createService();
        when(service.getObjectifyServiceProxy().beginTransaction()).thenReturn(objectify);
        ObjectifyProxy proxy = service.getObjectifyWithTransaction();
        assertSame(objectify, proxy.getProxyTarget());
        assertTrue(proxy.isTransactional());
    }

    /** Test parseEntities() for valid configuration, simple example. */
    @SuppressWarnings("rawtypes")
    @Test public void testParseEntitiesValidSimple() throws IOException {
        Resource entities = TestUtils.createMockedResource("java.lang.String\n");
        List<Class> classes = DaoObjectifyService.parseEntities(entities);
        assertEquals(1, classes.size());
        assertEquals(java.lang.String.class, classes.get(0));
    }

    /** Test parseEntities() for valid configuration, complicated example. */
    @SuppressWarnings("rawtypes")
    @Test public void testParseEntitiesValidComplicated() throws IOException {
        StringBuffer buffer = new StringBuffer();
        buffer.append("# This is a comment\n");
        buffer.append("\n");  // empty line
        buffer.append("java.lang.String\n");
        buffer.append("java.util.Date\n");
        buffer.append("   \t    \n");  // whitespace-only line
        buffer.append("   # This is a comment with some leading spaces\n");
        Resource entities = TestUtils.createMockedResource(buffer.toString());
        List<Class> classes = DaoObjectifyService.parseEntities(entities);
        assertEquals(2, classes.size());
        assertEquals(java.lang.String.class, classes.get(0));
        assertEquals(java.util.Date.class, classes.get(1));
    }

    /** Test parseEntities() for invalid data (unknown class). */
    @Test public void testParseEntitiesUnknownClass() throws IOException {
        Resource entities = TestUtils.createMockedResource("ken.whatever.Bogus\n");
        try {
            DaoObjectifyService.parseEntities(entities);
        } catch (CedarRuntimeException e) { }
    }

    /** Test parseEntities() for invalid data (null input reader). */
    @Test public void testParseEntitiesBadInput() throws IOException {
        Resource entities = TestUtils.createMockedResource(null);
        try {
            DaoObjectifyService.parseEntities(entities);
        } catch (CedarRuntimeException e) { }
    }

    /** Create a mocked service for testing. */
    private static DaoObjectifyService createService() {
        Resource entities = TestUtils.createMockedResource("java.lang.String");  // valid
        ObjectifyServiceProxy objectifyServiceProxy = mock(ObjectifyServiceProxy.class);
        DaoObjectifyService service = new DaoObjectifyService();
        service.setObjectifyServiceProxy(objectifyServiceProxy);
        service.setEntities(entities);  // valid because String is a known class
        service.afterPropertiesSet();
        return service;
    }

}
