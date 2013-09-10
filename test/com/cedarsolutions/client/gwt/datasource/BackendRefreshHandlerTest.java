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

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertSame;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Test;

import com.cedarsolutions.client.gwt.event.UnifiedEvent;
import com.cedarsolutions.client.gwt.event.UnifiedEventType;

/**
 * AUnit tests for BackendDataRefreshHandler.
 * @author Kenneth J. Pronovici <pronovic@ieee.org>
 */
public class BackendRefreshHandlerTest {

    /** Test constructor. */
    @SuppressWarnings("unchecked")
    @Test public void testConstructor() {
        IBackendDataPresenter<String, String> presenter = mock(IBackendDataPresenter.class);
        BackendDataRefreshHandler<String, String> handler = new BackendDataRefreshHandler<String, String>(presenter);
        assertNotNull(handler);
        assertSame(presenter, handler.getPresenter());
    }

    /** Test handleEvent() when the renderer does not have a data source set. */
    @SuppressWarnings("unchecked")
    @Test public void testHandleEventNoDataSource() {
        BackendDataSource<String, String> dataSource = mock(BackendDataSource.class);
        IBackendDataPresenter<String, String> presenter = mock(IBackendDataPresenter.class);
        when(presenter.getDataSource()).thenReturn(dataSource);

        BackendDataRefreshHandler<String, String> handler = new BackendDataRefreshHandler<String, String>(presenter);
        UnifiedEvent event = new UnifiedEvent((UnifiedEventType) null);  // doesn't matter what contents are
        handler.handleEvent(event);  // just make sure it doesn't blow up
    }

    /** Test handleEvent() with a normal setup. */
    @SuppressWarnings("unchecked")
    @Test public void testHandleEventNormal() {
        BackendDataSource<String, String> dataSource = mock(BackendDataSource.class);
        IBackendDataPresenter<String, String> presenter = mock(IBackendDataPresenter.class);
        when(presenter.getDataSource()).thenReturn(dataSource);

        BackendDataRefreshHandler<String, String> handler = new BackendDataRefreshHandler<String, String>(presenter);
        UnifiedEvent event = new UnifiedEvent((UnifiedEventType) null);  // doesn't matter what contents are
        handler.handleEvent(event);

        verify(dataSource).clear();
    }

}
