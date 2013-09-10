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
package com.cedarsolutions.client.gwt.widget;

import java.io.Serializable;

import com.cedarsolutions.client.gwt.junit.ClientTestCase;
import com.cedarsolutions.client.gwt.widget.DataTable.DataTableStyle;
import com.cedarsolutions.client.gwt.widget.DataTable.SelectionColumn;
import com.cedarsolutions.client.gwt.widget.DataTable.SelectionHeader;
import com.google.gwt.cell.client.TextCell;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.cellview.client.Column;
import com.google.gwt.view.client.ProvidesKey;

/**
 * Client-side unit tests for DataTable.
 *
 * <p>
 * These are not the most detailed tests that I have ever done.  I
 * am mainly just spot-checking behavior.  I tested this functionality
 * fairly well by hand before pulling out the common classes.
 * </p>
 *
 * @author Kenneth J. Pronovici <pronovic@ieee.org>
 */
public class DataTableClientTest extends ClientTestCase {

    /** Test constructor. */
    public void testConstructor() {
        KeyProvider provider = new KeyProvider();

        DataTable<Whatever> table = new DataTable<Whatever>(100, "200px");
        assertNotNull(table);
        assertEquals(100, table.getPageSize());
        // can't verify the width, apparently
        assertNull(table.getKeyProvider());
        assertNotNull(table.getLoadingIndicator());

        table = new DataTable<Whatever>(100, "200px", provider);
        assertNotNull(table);
        assertEquals(100, table.getPageSize());
        // can't verify the width, apparently
        assertSame(provider, table.getKeyProvider());
        assertNotNull(table.getLoadingIndicator());
    }

    /** Test getPager(). */
    public void testGetPager() {
        DataTable<Whatever> table = new DataTable<Whatever>(100, "200px");
        DataTablePager pager = table.getPager();
        assertNotNull(pager);
        assertEquals(table.getPageSize(), pager.getPageSize());
        assertSame(table, pager.getDisplay());
    }

    /** Test getStyle(). */
    public void testGetStyle() {
        // Just confirm that it doesn't blow up; we can't really inspect the result
        CellTable.Resources style = DataTable.getStyle();
        assertNotNull(style);
        assertTrue(style instanceof DataTableStyle);
    }

    /** Spot-check addColumn() and addSelectionColumn(). */
    public void testAddColumn() {
        DataTable<Whatever> table = new DataTable<Whatever>(100, "200px");
        assertFalse(table.hasSelectionColumn());

        table.addSelectionColumn(10, Unit.PCT, new KeyProvider());
        assertTrue(table.hasSelectionColumn());
        assertEquals(1, table.getColumnCount());
        assertTrue(table.getColumn(0) instanceof SelectionColumn);
        assertTrue(table.getColumnHeader(0) instanceof SelectionHeader);

        SomethingColumn column = new SomethingColumn();
        table.addColumn(column, "header", "footer");
        assertEquals(2, table.getColumnCount());
        assertTrue(table.getColumn(0) instanceof SelectionColumn);
        assertTrue(table.getColumnHeader(0) instanceof SelectionHeader);
        assertSame(column, table.getColumn(1));
        assertEquals("header", table.getColumnHeader(1));
        assertEquals("footer", table.getColumnFooter(1));
    }

    /** Spot-check setNoRowsMessage(). */
    public void testSetNoRowsMessage() {
        DataTable<Whatever> table = new DataTable<Whatever>(100, "200px");
        assertNull(table.getNoRowsMessage());
        table.setNoRowsMessage("hello");
        assertEquals("hello", table.getNoRowsMessage());
    }

    /** Spot-check selection functionality. */
    public void testGetSelections() {
        DataTable<Whatever> table = new DataTable<Whatever>(100, "200px");
        table.addColumn(new SomethingColumn());
        assertTrue(table.getSelectedRecords().isEmpty());
        table.selectNone();
        table.selectAll();
        table.selectItems(true);

        table = new DataTable<Whatever>(100, "200px");
        table.addSelectionColumn(10, Unit.PCT, new KeyProvider());  // adds a selection model, too
        table.addColumn(new SomethingColumn());
        assertTrue(table.getSelectedRecords().isEmpty());
        table.selectNone();
        table.selectAll();
        table.selectItems(true);
    }

    /** Class for our data table to hold. */
    @SuppressWarnings("serial")
    private static class Whatever implements Serializable {
    }

    /** Key provider. */
    private static class KeyProvider implements ProvidesKey<Whatever> {
        @Override
        public Object getKey(Whatever item) {
            return "1";
        }
    }

    /** Column. */
    private static class SomethingColumn extends Column<Whatever, String> {
        public SomethingColumn() {
            super(new TextCell());
        }

        @Override
        public String getValue(Whatever object) {
            return "whatever";
        }
    }

}
