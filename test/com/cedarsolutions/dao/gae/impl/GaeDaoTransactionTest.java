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

import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Test;

import com.cedarsolutions.dao.IDaoTransaction;
import com.cedarsolutions.exception.DaoException;

/**
 * Unit tests for GaeDaoTransaction.
 * @author Kenneth J. Pronovici <pronovic@ieee.org>
 */
public class GaeDaoTransactionTest {

    /** Test the constructor. */
    @Test public void testConstructor() {
        ObjectifyProxy objectify = null;

        try {
            objectify = mock(ObjectifyProxy.class);
            when(objectify.isTransactional()).thenReturn(false);
            new GaeDaoTransaction(objectify);
            fail("Expected DaoException");
        } catch (DaoException e) { }

        objectify = mock(ObjectifyProxy.class);
        when(objectify.isTransactional()).thenReturn(true);
        GaeDaoTransaction transaction = new GaeDaoTransaction(objectify);
        assertTrue(transaction instanceof IDaoTransaction);
        assertSame(objectify, transaction.getObjectify());
    }

    /** Test commit(). */
    @Test public void testCommit() {
        ObjectifyProxy objectify = mock(ObjectifyProxy.class);
        when(objectify.isTransactional()).thenReturn(true);
        GaeDaoTransaction transaction = new GaeDaoTransaction(objectify);
        transaction.commit();
        verify(objectify).commit();
    }

    /** Test rollback(). */
    @Test public void testRollback() {
        ObjectifyProxy objectify = mock(ObjectifyProxy.class);
        when(objectify.isTransactional()).thenReturn(true);
        GaeDaoTransaction transaction = new GaeDaoTransaction(objectify);
        transaction.rollback();
        verify(objectify).rollback();
    }

}
