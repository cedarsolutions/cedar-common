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

/**
 * Client-side unit tests for EnumColumn.
 * @author Kenneth J. Pronovici <pronovic@ieee.org>
 */
public class EnumColumnClientTest extends ClientTestCase {

    /** Test constructor and getName() method. */
    public void testConstructor() {
        EnumColumn<Row, TestEnum> column  = new TestColumn();
        assertNotNull(column);
        assertEquals(null, column.getName());
        assertFalse(column.isSortable());

        column  = new TestColumn("test");
        assertNotNull(column);
        assertEquals("test", column.getName());
        assertFalse(column.isSortable());

        column = new TestColumn(TestEnum.ONE);
        assertNotNull(column);
        assertEquals("ONE", column.getName());
        assertFalse(column.isSortable());

        column = new TestColumn("test", Sortable.NOT_SORTABLE);
        assertNotNull(column);
        assertEquals("test", column.getName());
        assertFalse(column.isSortable());

        column = new TestColumn(TestEnum.ONE, Sortable.NOT_SORTABLE);
        assertNotNull(column);
        assertEquals("ONE", column.getName());
        assertFalse(column.isSortable());

        column = new TestColumn("test", Sortable.SORTABLE);
        assertNotNull(column);
        assertEquals("test", column.getName());
        assertTrue(column.isSortable());

        column = new TestColumn(TestEnum.ONE, Sortable.SORTABLE);
        assertNotNull(column);
        assertEquals("ONE", column.getName());
        assertTrue(column.isSortable());
    }

    /** Test formatField(). */
    @SuppressWarnings("rawtypes")
    public void testFormatField() {
        Enum field;
        Row row = new Row();
        EnumColumn<Row, TestEnum> column  = new TestColumn();

        // note: no need to test null because it never gets called with null

        row.setValue(TestEnum.ONE);
        field = column.getField(row);
        assertEquals("ONE", column.formatField(field));

        row.setValue(TestEnum.TWO);
        field = column.getField(row);
        assertEquals("TWO", column.formatField(field));
    }

    /** Test column to work with. */
    private static class TestColumn extends EnumColumn<Row, TestEnum> {
        public TestColumn() {
            super();
        }

        public TestColumn(String name) {
            super(name);
        }

        public TestColumn(TestEnum name) {
            super(name);
        }

        public TestColumn(String name, Sortable sortable) {
            super(name, sortable);
        }

        public TestColumn(TestEnum name, Sortable sortable) {
            super(name, sortable);
        }

        @Override
        protected TestEnum getField(Row item) {
            return item.getValue();
        }
    }

    /** A sample row to work with. */
    private static class Row {
        private TestEnum value;

        public TestEnum getValue() {
            return this.value;
        }

        public void setValue(TestEnum value) {
            this.value = value;
        }
    }

    /** Enumeration to test with. */
    private enum TestEnum {
        ONE,
        TWO
    }
}
