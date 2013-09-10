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
package com.cedarsolutions.client.gwt.datasource;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.RETURNS_DEEP_STUBS;
import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import org.junit.Test;
import org.mockito.InOrder;

import com.cedarsolutions.dao.domain.PaginatedResults;
import com.cedarsolutions.dao.domain.Pagination;
import com.google.gwt.view.client.HasData;
import com.google.gwt.view.client.Range;

/**
 * Unit tests for BackendDataSource.
 * @author Kenneth J. Pronovici <pronovic@ieee.org>
 */
public class BackendDataSourceTest {

    /** Test the constructor. */
    @Test public void testConstructor() {
        IBackendDataRenderer<String, String> renderer = createRenderer();
        BackendDataSource<String, String> dataSource = new DataSource(renderer);
        assertNotNull(dataSource);
        assertSame(renderer, dataSource.getRenderer());
        assertEquals(new Pagination(renderer.getPageSize()), dataSource.getPagination());
        assertNotNull(dataSource.getDataProvider());
        assertSame(renderer.getDisplay(), dataSource.getDisplay());
        assertEquals(renderer.getSearchCriteria(), dataSource.getSearchCriteria());
        assertEquals(renderer.getPageSize(), dataSource.getPageSize());
    }

    /** Test clear() when no display is set. */
    @Test public void testClearNoDisplay() {
        IBackendDataRenderer<String, String> renderer = createRenderer();
        BackendDataSource<String, String> dataSource = new DataSource(renderer);
        Pagination pagination = dataSource.getPagination();
        when(renderer.getDisplay()).thenReturn(null);
        dataSource.clear();
        assertNotSame(pagination, dataSource.getPagination());
        assertEquals(new Pagination(renderer.getPageSize()), dataSource.getPagination());
    }

    /** Test clear() when a display is set. */
    @Test public void testClearWithDisplay() {
        IBackendDataRenderer<String, String> renderer = createRenderer();
        BackendDataSource<String, String> dataSource = new DataSource(renderer);
        Pagination pagination = dataSource.getPagination();
        dataSource.clear();
        assertNotSame(pagination, dataSource.getPagination());
        assertEquals(new Pagination(renderer.getPageSize()), dataSource.getPagination());
        verify(renderer.getDisplay()).setVisibleRangeAndClearData(new Range(0, renderer.getPageSize()), true);
    }

    /** Test updateDisplay() when no display is set. */
    @SuppressWarnings("unchecked")
    @Test public void testUpdateDisplayNoDisplay() {
        HasData<String> display = (HasData<String>) mock(HasData.class);
        IBackendDataRenderer<String, String> renderer = createRenderer();
        BackendDataSource<String, String> dataSource = new DataSource(renderer);
        when(renderer.getDisplay()).thenReturn(null);
        dataSource.updateDisplay(display);
        verifyNoMoreInteractions(display);
    }

    /** Test updateDisplay() when passed-in display does not match. */
    @SuppressWarnings("unchecked")
    @Test public void testUpdateDisplayNoMatch() {
        HasData<String> display = (HasData<String>) mock(HasData.class);
        IBackendDataRenderer<String, String> renderer = createRenderer();
        BackendDataSource<String, String> dataSource = new DataSource(renderer);
        dataSource.updateDisplay(display);
        verifyNoMoreInteractions(display);
    }

    /** Test updateDisplay() when passed-in display will be updated. */
    @Test public void testUpdateDisplay() {
        IBackendDataRenderer<String, String> renderer = createRenderer();
        DataSource dataSource = new DataSource(renderer);

        when(renderer.getDisplay().getVisibleRange().getStart()).thenReturn(0);
        dataSource.reset();
        dataSource.updateDisplay(renderer.getDisplay());
        dataSource.markRetrieveComplete(); // because this is what the caller would do (required for later calls)
        assertEquals(0, dataSource.retrieveStart);
        assertEquals(dataSource.getPagination().page(1), dataSource.retrievePagination);

        when(renderer.getDisplay().getVisibleRange().getStart()).thenReturn(1); // bogus value
        dataSource.reset();
        dataSource.updateDisplay(renderer.getDisplay());
        dataSource.markRetrieveComplete(); // because this is what the caller would do (required for later calls)
        assertEquals(1, dataSource.retrieveStart);
        assertEquals(dataSource.getPagination().page(1), dataSource.retrievePagination);

        when(renderer.getDisplay().getVisibleRange().getStart()).thenReturn(5);
        dataSource.reset();
        dataSource.updateDisplay(renderer.getDisplay());
        dataSource.markRetrieveComplete(); // because this is what the caller would do (required for later calls)
        assertEquals(5, dataSource.retrieveStart);
        assertEquals(dataSource.getPagination().page(2), dataSource.retrievePagination);

        when(renderer.getDisplay().getVisibleRange().getStart()).thenReturn(27);
        dataSource.reset();
        dataSource.updateDisplay(renderer.getDisplay());
        dataSource.markRetrieveComplete(); // because this is what the caller would do (required for later calls)
        assertEquals(27, dataSource.retrieveStart);
        assertEquals(dataSource.getPagination().page(6), dataSource.retrievePagination);
    }

    /** Test applyResults(). */
    @SuppressWarnings("unchecked")
    @Test public void testApplyResults() {
        Pagination pagination = mock(Pagination.class);
        when(pagination.getTotalRows()).thenReturn(57);
        when(pagination.isTotalFinalized()).thenReturn(false);

        PaginatedResults<String> results = (PaginatedResults<String>) mock(PaginatedResults.class);
        when(results.getPagination()).thenReturn(pagination);

        IBackendDataRenderer<String, String> renderer = createRenderer();
        BackendDataSource<String, String> dataSource = new DataSource(renderer);
        assertFalse(dataSource.isRetrieveActive());

        dataSource.markRetrieveStart();
        assertTrue(dataSource.isRetrieveActive());

        dataSource.applyResults(43, results);
        assertFalse(dataSource.isRetrieveActive());
        InOrder order = inOrder(dataSource.getDisplay());
        order.verify(dataSource.getDisplay()).setRowData(43, results);
        order.verify(dataSource.getDisplay()).setRowCount(57, false);
    }

    /** Create a mock renderer for testing. */
    @SuppressWarnings("unchecked")
    private static IBackendDataRenderer<String, String> createRenderer() {
        HasData<String> display = (HasData<String>) mock(HasData.class, RETURNS_DEEP_STUBS);
        IBackendDataRenderer<String, String> renderer = (IBackendDataRenderer<String, String>) mock(IBackendDataRenderer.class);
        when(renderer.getPageSize()).thenReturn(5);
        when(renderer.getDisplay()).thenReturn(display);
        when(renderer.getSearchCriteria()).thenReturn("criteria");
        return renderer;
    }

    /** Class for testing with. */
    private static class DataSource extends BackendDataSource<String, String> {
        protected int retrieveStart;
        protected Pagination retrievePagination;

        public DataSource(IBackendDataRenderer<String, String> renderer) {
            super(renderer);
        }

        @Override
        protected void retrieveFromBackEnd(int start, Pagination pagination) {
            this.retrieveStart = start;
            this.retrievePagination = pagination;
        }

        protected void reset() {
            this.retrieveStart = -1;
            this.retrievePagination = null;
        }
    }
}
