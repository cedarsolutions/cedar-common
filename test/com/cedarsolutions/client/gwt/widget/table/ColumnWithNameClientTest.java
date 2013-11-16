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

        column = new TestColumn("test", cell);
        assertNotNull(column);
        assertEquals("test", column.getName());
        assertSame(cell, column.getCell());
    }

    /** Test column to work with. */
    @SuppressWarnings({ "rawtypes", "unchecked" })
    private static class TestColumn extends ColumnWithName<String, String> {
        public TestColumn(Cell cell) {
            super(cell);
        }

        public TestColumn(String name, Cell cell) {
            super(name, cell);
        }

        @Override
        public String getValue(String object) {
            return object;
        }
    }
}
