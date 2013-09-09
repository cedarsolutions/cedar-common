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
package com.cedarsolutions.client.gwt.widget;

import java.io.Serializable;

import com.cedarsolutions.client.gwt.junit.ClientTestCase;

/**
 * Client-side unit tests for DataTablePager.
 * @author Kenneth J. Pronovici <pronovic@ieee.org>
 */
public class DataTablePagerClientTest extends ClientTestCase {

    /** Test constructor. */
    public void testConstructor() {
        DataTable<Whatever> table = new DataTable<Whatever>(100, "200px");
        DataTablePager pager = new DataTablePager(table);
        assertNotNull(pager);
        assertEquals(100, pager.getPageSize());
        assertSame(table, pager.getDisplay());
    }

    /** Test getResources().  */
    public void testGetResources() {
        assertNotNull(DataTablePager.getResources());
    }

    /** Class for our data table to hold. */
    @SuppressWarnings("serial")
    private static class Whatever implements Serializable {
    }
}
