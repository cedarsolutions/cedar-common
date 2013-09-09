/* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 *
 *              C E D A R
 *          S O L U T I O N S       "Software done right."
 *           S O F T W A R E
 *
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 *
 * Copyright (c) 2012 Kenneth J. Pronovici.
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
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;


/**
 * Unit tests for AbstractSearchCriteriaWithSort.
 * @author Kenneth J. Pronovici <pronovic@ieee.org>
 */
public class AbstractSearchCriteriaWithSortTest {

    /** Test the constructor. */
    @Test public void testConstructor() {
        Criteria criteria = new Criteria();
        assertEquals(criteria.getDefaultSortOrder(), criteria.getSortOrder());
        assertEquals(criteria.getDefaultSortColumn(), criteria.getSortColumn());
    }

    /** Test getDefaultSortOrder(). */
    @Test public void testGetDefaultSortOrder() {
        Criteria criteria = new Criteria();
        assertEquals(SortOrder.ASCENDING, criteria.getDefaultSortOrder());
    }

    /** Test getDefaultSortColumn(). */
    @Test public void testGetDefaultSortColumn() {
        Criteria criteria = new Criteria();
        assertEquals(Columns.USER_ID, criteria.getDefaultSortColumn());
    }

    /** Test getSortOrder() and setSortOrder(). */
    @Test public void testGetSetSortOrder() {
        Criteria criteria = new Criteria();

        criteria.setSortOrder(SortOrder.DESCENDING);
        assertEquals(SortOrder.DESCENDING, criteria.getSortOrder());

        criteria.setSortOrder(SortOrder.ASCENDING);
        assertEquals(SortOrder.ASCENDING, criteria.getSortOrder());
    }

    /** Test getSortColumn() and setSortColumn(). */
    @Test public void testGetSetSortColumn() {
        Criteria criteria = new Criteria();

        criteria.setSortColumn(Columns.USER_NAME);
        assertEquals(Columns.USER_NAME, criteria.getSortColumn());

        criteria.setSortColumn(Columns.USER_ID);
        assertEquals(Columns.USER_ID, criteria.getSortColumn());
    }

    /** Test getColumnName(). */
    @Test public void testGetColumnName() {
        Criteria criteria = new Criteria();
        assertEquals("USER_ID", criteria.getColumnName(Columns.USER_ID));
        assertEquals("USER_NAME", criteria.getColumnName(Columns.USER_NAME));
    }

    /** Test equals(). */
    @Test public void testEquals() {
        Criteria criteria1;
        Criteria criteria2;

        criteria1 = new Criteria();
        criteria2 = new Criteria();
        assertTrue(criteria1.equals(criteria2));
        assertTrue(criteria2.equals(criteria1));

        try {
            criteria1 = new Criteria();
            criteria2 = null;
            criteria1.equals(criteria2);
            fail("Expected NullPointerException");
        } catch (NullPointerException e) { }

        criteria1 = new Criteria();
        criteria2 = new Criteria();
        criteria1.setSortOrder(SortOrder.ASCENDING);
        criteria2.setSortOrder(SortOrder.DESCENDING);
        assertFalse(criteria1.equals(criteria2));
        assertFalse(criteria2.equals(criteria1));

        criteria1 = new Criteria();
        criteria2 = new Criteria();
        criteria1.setSortColumn(Columns.USER_ID);
        criteria2.setSortColumn(Columns.USER_NAME);
        assertFalse(criteria1.equals(criteria2));
        assertFalse(criteria2.equals(criteria1));
    }

    /** Test hashCode(). */
    @Test public void testHashCode() {
        Criteria criteria1 = new Criteria();
        criteria1.setSortOrder(SortOrder.ASCENDING);
        criteria1.setSortColumn(Columns.USER_ID);

        Criteria criteria2 = new Criteria();
        criteria2.setSortOrder(SortOrder.DESCENDING);
        criteria2.setSortColumn(Columns.USER_ID);

        Criteria criteria3 = new Criteria();
        criteria3.setSortOrder(SortOrder.ASCENDING);
        criteria3.setSortColumn(Columns.USER_NAME);

        // same as criteria1
        Criteria criteria4 = new Criteria();
        criteria4.setSortOrder(SortOrder.ASCENDING);
        criteria4.setSortColumn(Columns.USER_ID);

        Map<Criteria, String> map = new HashMap<Criteria, String>();
        map.put(criteria1, "ONE");
        map.put(criteria2, "TWO");
        map.put(criteria3, "THREE");

        assertEquals("ONE", map.get(criteria1));
        assertEquals("TWO", map.get(criteria2));
        assertEquals("THREE", map.get(criteria3));
        assertEquals("ONE", map.get(criteria4));
    }

    /** Concrete class that we can test with. */
    @SuppressWarnings("serial")
    private static class Criteria extends AbstractSearchCriteriaWithSort<String, Columns> {

        @Override
        public Columns getDefaultSortColumn() {
            return Columns.USER_ID;
        }

        @Override
        public Columns getSortColumn(String columnName) {
            return columnName == null ? null : Columns.valueOf(columnName);
        }

    }

    /** Column name enumeration. */
    private enum Columns {
        USER_ID,
        USER_NAME;
    }

}
