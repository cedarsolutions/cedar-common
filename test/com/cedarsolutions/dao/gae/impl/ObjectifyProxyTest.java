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

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Test;
import org.mockito.Mockito;

import com.cedarsolutions.exception.DaoException;
import com.googlecode.objectify.Objectify;

/**
 * Unit tests for ObjectifyProxy.
 * @author Kenneth J. Pronovici <pronovic@ieee.org>
 */
public class ObjectifyProxyTest {

    /** Test the constructor. */
    @Test public void testConstructor() {
        Objectify objectify = mock(Objectify.class);

        ObjectifyProxy proxy = new ObjectifyProxy(objectify);
        assertSame(objectify, proxy.getProxyTarget());
        assertFalse(proxy.isTransactional());

        proxy = new ObjectifyProxy(objectify, false);
        assertSame(objectify, proxy.getProxyTarget());
        assertFalse(proxy.isTransactional());

        proxy = new ObjectifyProxy(objectify, true);
        assertSame(objectify, proxy.getProxyTarget());
        assertTrue(proxy.isTransactional());
    }

    /** Test commit(). */
    @Test public void testCommit() {
        Objectify objectify = mock(Objectify.class, Mockito.RETURNS_DEEP_STUBS);
        ObjectifyProxy proxy = null;

        try {
            proxy = new ObjectifyProxy(objectify, false);
            proxy.commit();
            fail("Expected DaoException");
        } catch (DaoException e) { }

        proxy = new ObjectifyProxy(objectify, true);

        when(objectify.getTxn().isActive()).thenReturn(false);
        proxy.commit();
        verify(objectify.getTxn(), times(0)).commit();

        when(objectify.getTxn().isActive()).thenReturn(true);
        proxy.commit();
        verify(objectify.getTxn()).commit();
    }

    /** Test rollback(). */
    @Test public void testRollback() {
        Objectify objectify = mock(Objectify.class, Mockito.RETURNS_DEEP_STUBS);
        ObjectifyProxy proxy = null;

        try {
            proxy = new ObjectifyProxy(objectify, false);
            proxy.rollback();
            fail("Expected DaoException");
        } catch (DaoException e) { }

        proxy = new ObjectifyProxy(objectify, true);

        when(objectify.getTxn().isActive()).thenReturn(false);
        proxy.rollback();
        verify(objectify.getTxn(), times(0)).rollback();

        when(objectify.getTxn().isActive()).thenReturn(true);
        proxy.rollback();
        verify(objectify.getTxn()).rollback();
    }

}
