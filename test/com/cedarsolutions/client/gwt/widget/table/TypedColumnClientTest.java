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
 * Client-side unit tests for TypedColumn.
 * @author Kenneth J. Pronovici <pronovic@ieee.org>
 */
public class TypedColumnClientTest extends ClientTestCase {

    /** Test constructor and getName() method. */
    public void testConstructor() {
        TypedColumn<Row, String> column  = new TestColumn();
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

    /** Test getStringValue(). */
    public void testGetStringValue() {
        Row row;
        TypedColumn<Row, String> column  = new TestColumn();

        row = null;
        assertEquals("XXX", column.getStringValue(row));

        row = new Row();
        row.setValue(null);
        assertEquals("XXX", column.getStringValue(row));

        row = new Row();
        row.setValue("");
        assertEquals("__", column.getStringValue(row));

        row = new Row();
        row.setValue("hello");
        assertEquals("_hello_", column.getStringValue(row));
    }

    /** Test getTooltip(), default behavior. */
    public void testGetTooltipDefault() {
        Row row;
        TypedColumn<Row, String> column  = new TestColumn();

        row = null;
        assertEquals(null, column.getTooltip(row));

        row = new Row();
        row.setValue(null);
        assertEquals(null, column.getTooltip(row));

        row = new Row();
        row.setValue("");
        assertEquals(null, column.getTooltip(row));

        row = new Row();
        row.setValue("hello");
        assertEquals(null, column.getTooltip(row));
    }

    /** Test getTooltip(), make sure we can override default behavior. */
    public void testGetTooltipOverride() {
        Row row;
        TypedColumn<Row, String> column  = new TestColumnWithTooltip();

        row = null;
        assertEquals("_null_tooltip", column.getTooltip(row));

        row = new Row();
        row.setValue(null);
        assertEquals(null, column.getTooltip(row));  // if they want to return null, let them!

        row = new Row();
        row.setValue("");
        assertEquals("_tooltip", column.getTooltip(row));

        row = new Row();
        row.setValue("hello");
        assertEquals("hello_tooltip", column.getTooltip(row));
    }

    /** Test column to work with. */
    private static class TestColumn extends TypedColumn<Row, String> {
        TestColumn() {
            super();
        }

        TestColumn(String name) {
            super(name);
        }

        TestColumn(TestEnum name) {
            super(name);
        }

        TestColumn(String name, Sortable sortable) {
            super(name, sortable);
        }

        TestColumn(TestEnum name, Sortable sortable) {
            super(name, sortable);
        }

        @Override
        protected String getDefaultValue() {
            return "XXX";
        }

        @Override
        protected String formatField(String field) {
            return "_" + field + "_";
        }

        @Override
        protected String getField(Row item) {
            return item.getValue();
        }
    }

    /** Test column to work with. */
    private static class TestColumnWithTooltip extends TestColumn {
        TestColumnWithTooltip() {
            super();
        }

        @Override
        protected String getDefaultTooltip() {
            return "_null_tooltip";
        }

        @Override
        protected String getFieldTooltip(Row item) {
            return item.getValue() == null ? null : item.getValue() + "_tooltip";
        }
    }

    /** A sample row to work with. */
    private static class Row {
        private String value;

        public String getValue() {
            return this.value;
        }

        public void setValue(String value) {
            this.value = value;
        }
    }

    /** Enumeration to test with. */
    private enum TestEnum {
        ONE,
        TWO
    }

}
