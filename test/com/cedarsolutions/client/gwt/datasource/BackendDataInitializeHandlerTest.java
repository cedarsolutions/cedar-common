/* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 *
 *              C E D A R
 *          S O L U T I O N S       "Software done right."
 *           S O F T W A R E
 *
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 *
 * Copyright (c) 2013-2014 Kenneth J. Pronovici.
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
import static org.mockito.Matchers.isA;
import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Test;
import org.mockito.InOrder;

import com.cedarsolutions.client.gwt.event.UnifiedEvent;
import com.cedarsolutions.client.gwt.event.UnifiedEventType;
import com.google.gwt.view.client.HasData;

/**
 * Unit tests for BackendDataInitializeHandler.
 * @author Kenneth J. Pronovici <pronovic@ieee.org>
 */
public class BackendDataInitializeHandlerTest {

    /** Test constructor. */
    @SuppressWarnings("unchecked")
    @Test public void testConstructor() {
        IBackendDataPresenter<String, String> presenter = mock(IBackendDataPresenter.class);
        BackendDataInitializeHandler<String, String> handler = new BackendDataInitializeHandler<String, String>(presenter);
        assertNotNull(handler);
        assertSame(presenter, handler.getPresenter());
    }

    /** Test handleEvent() when no data source is generated. */
    @SuppressWarnings("unchecked")
    @Test public void testHandleEventNoDataSource() {
        IBackendDataRenderer<String, String> renderer = mock(IBackendDataRenderer.class);

        String criteria = "criteria";
        IBackendDataPresenter<String, String> presenter = mock(IBackendDataPresenter.class);
        when(presenter.getDefaultCriteria()).thenReturn(criteria);
        when(presenter.getRenderer()).thenReturn(renderer);
        when(presenter.createDataSource()).thenReturn(null);

        BackendDataInitializeHandler<String, String> handler = new BackendDataInitializeHandler<String, String>(presenter);
        UnifiedEvent event = new UnifiedEvent((UnifiedEventType) null);  // doesn't matter what contents are
        handler.handleEvent(event);

        // Criteria will be set, but nothing else happens
        verify(renderer).setSearchCriteria(criteria);
        verify(presenter, times(0)).setDataSource(isA(BackendDataSource.class));
    }

    /** Test handleEvent() when no renderer is set. */
    @SuppressWarnings("unchecked")
    @Test public void testHandleEventNoRenderer() {
        BackendDataProvider<String, String> dataProvider = mock(BackendDataProvider.class);
        BackendDataSource<String, String> dataSource = mock(BackendDataSource.class);
        when(dataSource.getDataProvider()).thenReturn(dataProvider);

        IBackendDataPresenter<String, String> presenter = mock(IBackendDataPresenter.class);
        when(presenter.createDataSource()).thenReturn(dataSource);
        when(presenter.getDataSource()).thenReturn(dataSource); // for consistency

        BackendDataInitializeHandler<String, String> handler = new BackendDataInitializeHandler<String, String>(presenter);
        UnifiedEvent event = new UnifiedEvent((UnifiedEventType) null);  // doesn't matter what contents are
        handler.handleEvent(event);

        // Data source will be set, but nothing else happens
        verify(presenter).setDataSource(dataSource);
        verify(presenter, times(0)).getDefaultCriteria();
        verify(dataSource.getDataProvider(), times(0)).addDataDisplay(isA(HasData.class));
    }

    /** Test handleEvent() when no display is set. */
    @SuppressWarnings("unchecked")
    @Test public void testHandleEventNoDisplay() {
        IBackendDataRenderer<String, String> renderer = mock(IBackendDataRenderer.class);

        BackendDataProvider<String, String> dataProvider = mock(BackendDataProvider.class);
        BackendDataSource<String, String> dataSource = mock(BackendDataSource.class);
        when(dataSource.getDataProvider()).thenReturn(dataProvider);

        String criteria = "criteria";
        IBackendDataPresenter<String, String> presenter = mock(IBackendDataPresenter.class);
        when(presenter.getDefaultCriteria()).thenReturn(criteria);
        when(presenter.getRenderer()).thenReturn(renderer);
        when(presenter.createDataSource()).thenReturn(dataSource);
        when(presenter.getDataSource()).thenReturn(dataSource); // for consistency

        BackendDataInitializeHandler<String, String> handler = new BackendDataInitializeHandler<String, String>(presenter);
        UnifiedEvent event = new UnifiedEvent((UnifiedEventType) null);  // doesn't matter what contents are
        handler.handleEvent(event);

        // Criteria will be set first, then data source set, but no display will be configured
        InOrder order = inOrder(presenter, renderer);
        order.verify(renderer).setSearchCriteria(criteria);
        order.verify(presenter).setDataSource(dataSource);
        verify(dataSource.getDataProvider(), times(0)).addDataDisplay(isA(HasData.class));
    }

    /** Test handleEvent() with a normal setup. */
    @SuppressWarnings("unchecked")
    @Test public void testHandleEventNormal() {
        HasData<String> display = mock(HasData.class);
        IBackendDataRenderer<String, String> renderer = mock(IBackendDataRenderer.class);
        when(renderer.getDisplay()).thenReturn(display);

        BackendDataProvider<String, String> dataProvider = mock(BackendDataProvider.class);
        BackendDataSource<String, String> dataSource = mock(BackendDataSource.class);
        when(dataSource.getDataProvider()).thenReturn(dataProvider);

        String criteria = "criteria";
        IBackendDataPresenter<String, String> presenter = mock(IBackendDataPresenter.class);
        when(presenter.getDefaultCriteria()).thenReturn(criteria);
        when(presenter.getRenderer()).thenReturn(renderer);
        when(presenter.createDataSource()).thenReturn(dataSource);
        when(presenter.getDataSource()).thenReturn(dataSource); // for consistency

        BackendDataInitializeHandler<String, String> handler = new BackendDataInitializeHandler<String, String>(presenter);
        UnifiedEvent event = new UnifiedEvent((UnifiedEventType) null);  // doesn't matter what contents are
        handler.handleEvent(event);

        // Criteria will be set first, then data source and displasy will be configured
        InOrder order = inOrder(presenter, renderer, dataProvider);
        order.verify(renderer).setSearchCriteria(criteria);
        order.verify(presenter).setDataSource(dataSource);
        order.verify(dataProvider).addDataDisplay(display);
    }

}
