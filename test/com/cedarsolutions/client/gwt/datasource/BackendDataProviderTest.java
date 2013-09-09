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
package com.cedarsolutions.client.gwt.datasource;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertSame;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import org.junit.Test;

import com.google.gwt.view.client.HasData;

/**
 * Unit tests for BackendDataProvider.
 * @author Kenneth J. Pronovici <pronovic@ieee.org>
 */
public class BackendDataProviderTest {

    /** Test the constructor. */
    @SuppressWarnings("unchecked")
    @Test public void testConstructor() {
        BackendDataSource<String, String> dataSource = mock(BackendDataSource.class);
        BackendDataProvider<String, String> provider = new BackendDataProvider<String, String>(dataSource);
        assertNotNull(provider);
        assertSame(dataSource, provider.getDataSource());
    }

    /** Test onRangeChanged(). */
    @SuppressWarnings("unchecked")
    @Test public void testOnRangeChanged() {
        HasData<String> display = mock(HasData.class);
        BackendDataSource<String, String> dataSource = mock(BackendDataSource.class);
        BackendDataProvider<String, String> provider = new BackendDataProvider<String, String>(dataSource);
        provider.onRangeChanged(display);
        verify(dataSource).updateDisplay(display);
    }
}
