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
package com.cedarsolutions.dao.domain;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

/**
 * Unit tests for PaginatedResults.
 * @author Kenneth J. Pronovici <pronovic@ieee.org>
 */
public class PaginatedResultsTest {

    /** Test the constructor. */
    @Test public void testConstructor() {
        PaginatedResults<String> results = new PaginatedResults<String>();
        assertNotNull(results);
        assertNull(results.getPagination());
        assertTrue(results.isEmpty());
    }

    /** Test the custom getters and setters. */
    @Test public void testGettersSetters() {
        PaginatedResults<String> results = new PaginatedResults<String>();
        Pagination pagination = new Pagination(5);
        results.setPagination(pagination);
        assertSame(pagination, results.getPagination());
    }

}
