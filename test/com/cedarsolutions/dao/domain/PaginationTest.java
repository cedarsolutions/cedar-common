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
package com.cedarsolutions.dao.domain;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

import com.cedarsolutions.shared.domain.TranslatableDomainObject;


/**
 * Unit tests for Pagination.
 *
 * <p>
 * Not all of the pagination functionality is directly tested here.  Instead,
 * part of it is tested along with the AbstractGaeDao.  Fundamentally, we can't
 * confirm that this object is working correctly unless pagination works at the
 * DAO layer.
 * </p>
 *
 * @author Kenneth J. Pronovici <pronovic@ieee.org>
 */
@SuppressWarnings("unlikely-arg-type")
public class PaginationTest {

    /** Check GWT behavior. */
    @Test public void testGwtBehavior() {
        // Anything GWT uses must be serializable and must have a zero-arg constructor.
        Pagination obj = new Pagination();
        assertTrue(obj instanceof TranslatableDomainObject);
        assertTrue(obj instanceof Serializable);
    }

    /** Test the constructor. */
    @Test public void testConstructor() {
        Pagination pagination = new Pagination(3);
        assertNull(pagination.getCurrent());
        assertEquals(3, pagination.getPageSize());
        assertEquals(1, pagination.getPageNumber());
        assertFalse(pagination.hasData());
        assertFalse(pagination.hasPrevious());
        assertFalse(pagination.hasNext());
        assertFalse(pagination.isTotalFinalized());
        assertEquals(0, pagination.getTotalPages());
        assertEquals(0, pagination.getTotalRows());
    }

    /** Test equals(). */
    @Test public void testEquals() {
        Pagination pagination1;
        Pagination pagination2;

        try {
            pagination1 = createPagination();
            pagination2 = null;
            pagination1.equals(pagination2);
            fail("Expected NullPointerException");
        } catch (NullPointerException e) { }

        try {
            pagination1 = createPagination();
            pagination1.equals("blech");
            fail("Expected ClassCastException");
        } catch (ClassCastException e) { }

        pagination1 = createPagination();
        pagination2 = createPagination();
        assertTrue(pagination1.equals(pagination2));
        assertTrue(pagination2.equals(pagination1));

        pagination1 = createPagination();
        pagination2 = createPagination();
        pagination2.pageSize = 2;
        assertFalse(pagination1.equals(pagination2));
        assertFalse(pagination2.equals(pagination1));

        pagination1 = createPagination();
        pagination2 = createPagination();
        pagination2.pageNumber = 2;
        assertFalse(pagination1.equals(pagination2));
        assertFalse(pagination2.equals(pagination1));

        pagination1 = createPagination();
        pagination2 = createPagination();
        pagination2.current = "other";
        assertFalse(pagination1.equals(pagination2));
        assertFalse(pagination2.equals(pagination1));

        pagination1 = createPagination();
        pagination2 = createPagination();
        pagination2.totalRows = 50;  // shouldn't matter
        assertTrue(pagination1.equals(pagination2));
        assertTrue(pagination2.equals(pagination1));
    }

    /** Test hashCode(). */
    @Test public void testHashCode() {
        Pagination pagination1 = createPagination();
        pagination1.current = "one";

        Pagination pagination2 = createPagination();
        pagination2.current = "two";

        Pagination pagination3 = createPagination();
        pagination3.current = "three";

        Pagination pagination4 = createPagination();
        pagination4.current = "four";

        Pagination pagination5 = createPagination();
        pagination5.current = "one"; // matches pagination1

        Map<Pagination, String> map = new HashMap<Pagination, String>();
        map.put(pagination1, "ONE");
        map.put(pagination2, "TWO");
        map.put(pagination3, "THREE");

        assertEquals("ONE", map.get(pagination1));
        assertEquals("TWO", map.get(pagination2));
        assertEquals("THREE", map.get(pagination3));
        assertEquals(null, map.get(pagination4));
        assertEquals("ONE", map.get(pagination5));
    }

    /** Create a Pagination for testing. */
    private static Pagination createPagination() {
        Pagination pagination = new Pagination();

        pagination.pageSize = 15;
        pagination.pageNumber = 1;
        pagination.current = "cursor1";

        return pagination;
    }
}
