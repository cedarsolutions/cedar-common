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
import com.google.gwt.cell.client.SafeHtmlCell;
import com.google.gwt.safehtml.shared.SafeHtml;

/**
 * Client-side unit tests for ColumnWithTooltip.
 * @author Kenneth J. Pronovici <pronovic@ieee.org>
 */
public class ColumnWithTooltipClientTest extends ClientTestCase {

    /** Test constructor and new getName() method. */
    @SuppressWarnings("rawtypes")
    public void testConstructor() {
        ColumnWithTooltip column = new TestColumn();
        assertNotNull(column);
        assertEquals(null, column.getName());
        assertTrue(column.getCell() instanceof SafeHtmlCell);

        column = new TestColumn("test");
        assertNotNull(column);
        assertEquals("test", column.getName());
        assertTrue(column.getCell() instanceof SafeHtmlCell);
    }

    /** Test getValue(). */
    public void testGetValue() {
        TestColumn column = new TestColumn();
        SafeHtml result = null;

        column.returnTooltip = false;
        result = column.getValue("hello");
        assertNotNull(result);
        assertEquals("<span>hello</span>", result.asString());

        column.returnTooltip = true;
        result = column.getValue("hello");
        assertNotNull(result);
        assertEquals("<span title='hello_tooltip'>hello</span>", result.asString());

        column.returnTooltip = true;
        result = column.getValue("here > there");
        assertNotNull(result);
        assertEquals("<span title='here &gt; there_tooltip'>here &gt; there</span>", result.asString());
    }

    /** Test column to work with. */
    private static class TestColumn extends ColumnWithTooltip<String> {
        protected boolean returnTooltip = true;

        public TestColumn() {
            super();
        }

        public TestColumn(String name) {
            super(name);
        }

        @Override
        public String getStringValue(String item) {
            return item;
        }

        @Override
        public String getTooltip(String item) {
            return returnTooltip ? item + "_tooltip" : null;
        }
    }

}
