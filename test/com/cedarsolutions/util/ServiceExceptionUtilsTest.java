/* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 *
 *              C E D A R
 *          S O L U T I O N S       "Software done right."
 *           S O F T W A R E
 *
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 *
 * Copyright (c) 2013-2014 Kenneth J. Pronovici.
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
package com.cedarsolutions.util;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.cedarsolutions.exception.CedarRuntimeException;
import com.cedarsolutions.exception.ServiceException;
import com.cedarsolutions.shared.domain.LocalizableMessage;

/**
 * Unit tests for ServiceExceptionUtils.
 * @author Kenneth J. Pronovici <pronovic@ieee.org>
 */
public class ServiceExceptionUtilsTest {

    /** Test createServiceException. */
    @Test public void testCreateServiceException() {
        Throwable nested = new IllegalAccessException("nested");
        Throwable cause1 = new Exception("cause1");
        Throwable cause2 = new Exception("cause2", nested);
        LocalizableMessage message = new LocalizableMessage("whatever");

        try {
            throw ServiceExceptionUtils.createServiceException();
        } catch (ServiceException e) {
            assertTrue(e instanceof CedarRuntimeException);
            assertEquals(null, e.getMessage());
            assertMessageValues(e.getLocalizableMessage(), (String) null);
            assertNull(e.getCause());
            assertNotNull(e.getContext());
            assertNull(e.getContext().getRootCause());
        }

        try {
            throw ServiceExceptionUtils.createServiceException("error");
        } catch (ServiceException e) {
            assertTrue(e instanceof CedarRuntimeException);
            assertEquals("error", e.getMessage());
            assertMessageValues(e.getLocalizableMessage(), "error");
            assertNull(e.getCause());
            assertNotNull(e.getContext());
            assertNull(e.getContext().getRootCause());
        }

        try {
            throw ServiceExceptionUtils.createServiceException(message);
        } catch (ServiceException e) {
            assertTrue(e instanceof CedarRuntimeException);
            assertEquals(message.getText(), e.getMessage());
            assertMessageValues(e.getLocalizableMessage(), message);
            assertNull(e.getCause());
            assertNotNull(e.getContext());
            assertNull(e.getContext().getRootCause());
        }

        try {
            throw ServiceExceptionUtils.createServiceException("error", cause1);
        } catch (ServiceException e) {
            assertTrue(e instanceof CedarRuntimeException);
            assertEquals("error", e.getMessage());
            assertMessageValues(e.getLocalizableMessage(), "error");
            assertSame(cause1, e.getCause());
            assertNotNull(e.getContext());
            assertNotNull(e.getContext().getRootCause());
        }

        try {
            throw ServiceExceptionUtils.createServiceException(message, cause1);
        } catch (ServiceException e) {
            assertTrue(e instanceof CedarRuntimeException);
            assertEquals(message.getText(), e.getMessage());
            assertMessageValues(e.getLocalizableMessage(), message);
            assertSame(cause1, e.getCause());
            assertNotNull(e.getContext());
            assertNotNull(e.getContext().getRootCause());
        }

        try {
            throw ServiceExceptionUtils.createServiceException("error", cause2);
        } catch (ServiceException e) {
            assertTrue(e instanceof CedarRuntimeException);
            assertEquals("error", e.getMessage());
            assertMessageValues(e.getLocalizableMessage(), "error");
            assertSame(cause2, e.getCause());
            assertNotNull(e.getContext());
            assertNotNull(e.getContext().getRootCause());
        }

        try {
            throw ServiceExceptionUtils.createServiceException(message, cause2);
        } catch (ServiceException e) {
            assertTrue(e instanceof CedarRuntimeException);
            assertEquals(message.getText(), e.getMessage());
            assertMessageValues(e.getLocalizableMessage(), message);
            assertSame(cause2, e.getCause());
            assertNotNull(e.getContext());
            assertNotNull(e.getContext().getRootCause());
        }
    }

    /** Assert that message values match expectations. */
    private static void assertMessageValues(LocalizableMessage message, LocalizableMessage expected) {
        assertMessageValues(message, expected.getText(), expected.getKey(), expected.getContext());
    }

    /** Assert that message values match expectations. */
    private static void assertMessageValues(LocalizableMessage message, String text) {
        assertMessageValues(message, text, null, null);
    }

    /** Assert that message values match expectations. */
    private static void assertMessageValues(LocalizableMessage message, String text, String key, String context) {
        assertNotNull(message);
        assertEquals(text, message.getText());
        assertEquals(key, message.getKey());
        assertEquals(context, message.getContext());
    }

}
