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
package com.cedarsolutions.client.gwt.widget.table;

import com.cedarsolutions.client.gwt.junit.ClientTestCase;
import com.cedarsolutions.client.gwt.widget.table.ColumnWithName.Sortable;
import com.google.gwt.cell.client.Cell;
import com.google.gwt.cell.client.TextCell;

/**
 * Client-side unit tests for ColumnWithName.
 * @author Kenneth J. Pronovici <pronovic@ieee.org>
 */
public class ColumnWithNameClientTest extends ClientTestCase {

    /** Test constructor and getName() method. */
    @SuppressWarnings("rawtypes")
    public void testConstructor() {
        Cell cell = new TextCell();

        ColumnWithName column = new TestColumn(cell);
        assertNotNull(column);
        assertEquals(null, column.getName());
        assertSame(cell, column.getCell());
        assertFalse(column.isSortable());

        column = new TestColumn("test", cell);
        assertNotNull(column);
        assertEquals("test", column.getName());
        assertSame(cell, column.getCell());
        assertFalse(column.isSortable());

        column = new TestColumn(TestEnum.ONE, cell);
        assertNotNull(column);
        assertEquals("ONE", column.getName());
        assertSame(cell, column.getCell());
        assertFalse(column.isSortable());

        column = new TestColumn("test", cell, Sortable.NOT_SORTABLE);
        assertNotNull(column);
        assertEquals("test", column.getName());
        assertSame(cell, column.getCell());
        assertFalse(column.isSortable());

        column = new TestColumn(TestEnum.ONE, cell, Sortable.NOT_SORTABLE);
        assertNotNull(column);
        assertEquals("ONE", column.getName());
        assertSame(cell, column.getCell());
        assertFalse(column.isSortable());

        column = new TestColumn("test", cell, Sortable.SORTABLE);
        assertNotNull(column);
        assertEquals("test", column.getName());
        assertSame(cell, column.getCell());
        assertTrue(column.isSortable());

        column = new TestColumn(TestEnum.ONE, cell, Sortable.SORTABLE);
        assertNotNull(column);
        assertEquals("ONE", column.getName());
        assertSame(cell, column.getCell());
        assertTrue(column.isSortable());
    }

    /** Test column to work with. */
    @SuppressWarnings({ "rawtypes", "unchecked" })
    private static class TestColumn extends ColumnWithName<String, String> {
        TestColumn(Cell cell) {
            super(cell);
        }

        TestColumn(Enum name, Cell cell) {
            super(name, cell);
        }

        TestColumn(String name, Cell cell) {
            super(name, cell);
        }

        TestColumn(Enum name, Cell cell, Sortable sortable) {
            super(name, cell, sortable);
        }

        TestColumn(String name, Cell cell, Sortable sortable) {
            super(name, cell, sortable);
        }

        @Override
        public String getValue(String object) {
            return object;
        }
    }

    /** Enumeration to test with. */
    private enum TestEnum {
        ONE,
        TWO
    }
}
