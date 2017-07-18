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

import java.io.Serializable;

import com.cedarsolutions.client.gwt.junit.ClientTestCase;
import com.cedarsolutions.client.gwt.widget.table.DataTable.DisabledResources;
import com.cedarsolutions.client.gwt.widget.table.DataTable.SelectionColumn;
import com.cedarsolutions.client.gwt.widget.table.DataTable.SelectionHeader;
import com.cedarsolutions.client.gwt.widget.table.DataTable.StandardResources;
import com.cedarsolutions.dao.domain.AbstractSearchCriteriaWithSort;
import com.cedarsolutions.dao.domain.ISearchCriteriaWithSort;
import com.cedarsolutions.dao.domain.SortOrder;
import com.google.gwt.cell.client.TextCell;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.cellview.client.ColumnSortList.ColumnSortInfo;
import com.google.gwt.view.client.ProvidesKey;

/**
 * Client-side unit tests for SortableTable.
 *
 * <p>
 * These are not the most detailed tests that I have ever done.  I
 * am mainly just spot-checking behavior.  I tested this functionality
 * fairly well by hand before pulling out the common classes.
 * </p>
 *
 * @author Kenneth J. Pronovici <pronovic@ieee.org>
 */
public class SortableTableClientTest extends ClientTestCase {

    /** Test constructor. */
    public void testConstructor() {
        KeyProvider provider = new KeyProvider();

        SortableTable<Whatever, WhateverColumn> table = new SortableTable<Whatever, WhateverColumn>(100, "200px");
        assertNotNull(table);
        assertEquals(100, table.getPageSize());
        // can't verify the width, apparently
        assertNull(table.getKeyProvider());
        assertNotNull(table.getLoadingIndicator());

        table = new SortableTable<Whatever, WhateverColumn>(100, "200px", provider);
        assertNotNull(table);
        assertEquals(100, table.getPageSize());
        // can't verify the width, apparently
        assertSame(provider, table.getKeyProvider());
        assertNotNull(table.getLoadingIndicator());
    }

    /** Test getPager(). */
    public void testGetPager() {
        SortableTable<Whatever, WhateverColumn> table = new SortableTable<Whatever, WhateverColumn>(100, "200px");
        DataTablePager pager = table.getPager();
        assertNotNull(pager);
        assertEquals(table.getPageSize(), pager.getPageSize());
        assertSame(table, pager.getDisplay());
    }

    /** Test getStandardResources(). */
    public void testGetStandardResources() {
        // Just confirm that it doesn't blow up; we can't really inspect the result
        CellTable.Resources style = DataTable.getStandardResources();
        assertNotNull(style);
        assertTrue(style instanceof StandardResources);
    }

    /** Test getDisabledResources(). */
    public void testGetDisabledResources() {
        // Just confirm that it doesn't blow up; we can't really inspect the result
        CellTable.Resources style = DataTable.getDisabledResources();
        assertNotNull(style);
        assertTrue(style instanceof DisabledResources);
    }

    /** Spot-check addColumn() and addSelectionColumn(). */
    public void testAddColumn() {
        SortableTable<Whatever, WhateverColumn> table = new SortableTable<Whatever, WhateverColumn>(100, "200px");
        assertFalse(table.hasSelectionColumn());

        table.addSelectionColumn(10, Unit.PCT, new KeyProvider());
        assertTrue(table.hasSelectionColumn());
        assertEquals(1, table.getColumnCount());
        assertTrue(table.getColumn(0) instanceof SelectionColumn);
        assertTrue(table.getColumnHeader(0) instanceof SelectionHeader);
        assertNull(table.getColumn(WhateverColumn.ONE.toString()));

        OneColumn column1 = new OneColumn();
        table.addColumn(column1, "header1", "footer1");
        assertEquals(2, table.getColumnCount());
        assertTrue(table.getColumn(0) instanceof SelectionColumn);
        assertTrue(table.getColumnHeader(0) instanceof SelectionHeader);
        assertSame(column1, table.getColumn(1));
        assertSame(column1, table.getColumn(WhateverColumn.ONE.toString()));
        assertEquals("header1", table.getColumnHeader(1));
        assertEquals("footer1", table.getColumnFooter(1));

        TwoColumn column2 = new TwoColumn();
        table.addColumn(column2, "header2", "footer2");
        assertEquals(3, table.getColumnCount());
        assertTrue(table.getColumn(0) instanceof SelectionColumn);
        assertTrue(table.getColumnHeader(0) instanceof SelectionHeader);
        assertSame(column1, table.getColumn(1));
        assertSame(column1, table.getColumn(WhateverColumn.ONE.toString()));
        assertEquals("header1", table.getColumnHeader(1));
        assertEquals("footer1", table.getColumnFooter(1));
        assertSame(column2, table.getColumn(2));
        assertSame(column2, table.getColumn(WhateverColumn.TWO.toString()));
        assertEquals("header2", table.getColumnHeader(2));
        assertEquals("footer2", table.getColumnFooter(2));
    }

    /** Spot-check setNoRowsMessage(). */
    public void testSetNoRowsMessage() {
        SortableTable<Whatever, WhateverColumn> table = new SortableTable<Whatever, WhateverColumn>(100, "200px");
        assertNull(table.getNoRowsMessage());
        table.setNoRowsMessage("hello");
        assertEquals("hello", table.getNoRowsMessage());
    }

    /** Spot-check selection functionality. */
    public void testGetSelections() {
        SortableTable<Whatever, WhateverColumn> table = new SortableTable<Whatever, WhateverColumn>(100, "200px");
        table.addColumn(new OneColumn());
        assertTrue(table.getSelectedRecords().isEmpty());
        table.selectNone();
        table.selectAll();
        table.selectItems(true);

        table = new SortableTable<Whatever, WhateverColumn>(100, "200px");
        table.addSelectionColumn(10, Unit.PCT, new KeyProvider());  // adds a selection model, too
        table.addColumn(new OneColumn());
        assertTrue(table.getSelectedRecords().isEmpty());
        table.selectNone();
        table.selectAll();
        table.selectItems(true);
    }

    /** Test the sort behavior. */
    public void testSortBehavior() {
        OneColumn column1 = new OneColumn();
        TwoColumn column2 = new TwoColumn();
        ThreeColumn column3 = new ThreeColumn();
        FourColumn column4 = new FourColumn();

        SortableTable<Whatever, WhateverColumn> table = new SortableTable<Whatever, WhateverColumn>(100, "200px");
        table.addColumn(column1, "header1", "footer1");
        table.addColumn(column2, "header2", "footer2");

        // Check that we get the default values out of the table
        ISearchCriteriaWithSort<Whatever, WhateverColumn> criteria = new WhateverCriteria();
        criteria.setSortOrder(null);
        criteria.setSortColumn(null);
        table.setCriteriaSortColumn(criteria);
        assertEquals(SortOrder.ASCENDING, criteria.getSortOrder());
        assertEquals(WhateverColumn.ONE, criteria.getSortColumn());

        // now change it to something other than the default and pull
        // it back out; if the round trip works, we should be OK
        criteria.setSortOrder(SortOrder.DESCENDING);
        criteria.setSortColumn(WhateverColumn.TWO);
        table.setDisplaySortColumn(criteria);
        ISearchCriteriaWithSort<Whatever, WhateverColumn> criteria2 = new WhateverCriteria();
        table.setCriteriaSortColumn(criteria2);
        assertEquals(SortOrder.DESCENDING, criteria2.getSortOrder());
        assertEquals(WhateverColumn.TWO, criteria2.getSortColumn());

        // now, confirm that a bogus column (not in the enumeration) gets the default value
        ColumnSortInfo sortInfo = new ColumnSortInfo(column3, false);
        table.getColumnSortList().push(sortInfo);
        ISearchCriteriaWithSort<Whatever, WhateverColumn> criteria3 = new WhateverCriteria();
        table.setCriteriaSortColumn(criteria3);
        assertEquals(SortOrder.DESCENDING, criteria3.getSortOrder());
        assertEquals(WhateverColumn.ONE, criteria3.getSortColumn());

        // now, confirm that a bogus column (null column  name) gets the default value
        sortInfo = new ColumnSortInfo(column4, true);
        table.getColumnSortList().push(sortInfo);
        ISearchCriteriaWithSort<Whatever, WhateverColumn> criteria4 = new WhateverCriteria();
        table.setCriteriaSortColumn(criteria4);
        assertEquals(SortOrder.ASCENDING, criteria4.getSortOrder());
        assertEquals(WhateverColumn.ONE, criteria4.getSortColumn());
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

    /** Column that exists in the enumeration as ONE. */
    private static class OneColumn extends ColumnWithName<Whatever, String> {
        OneColumn() {
            super(WhateverColumn.ONE.toString(), new TextCell());
        }

        @Override
        public String getValue(Whatever object) {
            return "one";
        }
    }

    /** Column that exists in the enumeration as TWO. */
    private static class TwoColumn extends ColumnWithName<Whatever, String> {
        TwoColumn() {
            super(WhateverColumn.TWO.toString(), new TextCell());
        }

        @Override
        public String getValue(Whatever object) {
            return "two";
        }
    }

    /** Column that does not exist in the enumeration, non-null column name. */
    private static class ThreeColumn extends ColumnWithName<Whatever, String> {
        ThreeColumn() {
            super("Three", new TextCell());
        }

        @Override
        public String getValue(Whatever object) {
            return "three";
        }
    }

    /** Column that does not exist in the enumeration, null column name. */
    private static class FourColumn extends ColumnWithName<Whatever, String> {
        FourColumn() {
            super((String) null, new TextCell());
        }

        @Override
        public String getValue(Whatever object) {
            return "four";
        }
    }

    /** Search criteria. */
    @SuppressWarnings("serial")
    private static class WhateverCriteria extends AbstractSearchCriteriaWithSort<Whatever, WhateverColumn> {

        @Override
        public WhateverColumn getDefaultSortColumn() {
            return WhateverColumn.ONE;
        }

        @Override
        public WhateverColumn getSortColumn(String columnName) {
            // Defined this way so that a null column maps to nul on the way out.
            // An unknown column will yield an exception, which should also be handled.
            return columnName == null ? null : WhateverColumn.valueOf(columnName);
        }

    }

    /** Sort column. */
    private enum WhateverColumn {
        ONE,
        TWO;
    }

}
