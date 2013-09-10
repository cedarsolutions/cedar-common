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
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Test;

import com.cedarsolutions.client.gwt.event.UnifiedEvent;
import com.cedarsolutions.client.gwt.event.UnifiedEventType;
import com.cedarsolutions.client.gwt.event.ViewEventHandler;

/**
 * Unit tests for BackendDataCriteriaResetHandler.
 * @author Kenneth J. Pronovici <pronovic@ieee.org>
 */
public class BackendDataCriteriaResetHandlerTest {

    /** Test constructor. */
    @SuppressWarnings("unchecked")
    @Test public void testConstructor() {
        IBackendDataPresenter<String, String> presenter = mock(IBackendDataPresenter.class);
        BackendDataCriteriaResetHandler<String, String> handler = new BackendDataCriteriaResetHandler<String, String>(presenter);
        assertNotNull(handler);
        assertSame(presenter, handler.getPresenter());
    }

    /** Test handleEvent() when there is no renderer set. */
    @SuppressWarnings("unchecked")
    @Test public void testHandleEventNoRenderer() {
        String criteria = "criteria";
        IBackendDataPresenter<String, String> presenter = mock(IBackendDataPresenter.class);
        when(presenter.getDefaultCriteria()).thenReturn(criteria);

        BackendDataCriteriaResetHandler<String, String> handler = new BackendDataCriteriaResetHandler<String, String>(presenter);
        UnifiedEvent event = new UnifiedEvent((UnifiedEventType) null);  // doesn't matter what contents are
        handler.handleEvent(event);
    }

    /** Test handleEvent() when the renderer does not have a refresh handler set. */
    @SuppressWarnings("unchecked")
    @Test public void testHandleEventNoRefreshHandler() {
        IBackendDataRenderer<String, String> renderer = mock(IBackendDataRenderer.class);

        String criteria = "criteria";
        IBackendDataPresenter<String, String> presenter = mock(IBackendDataPresenter.class);
        when(presenter.getDefaultCriteria()).thenReturn(criteria);
        when(presenter.getRenderer()).thenReturn(renderer);

        BackendDataCriteriaResetHandler<String, String> handler = new BackendDataCriteriaResetHandler<String, String>(presenter);
        UnifiedEvent event = new UnifiedEvent((UnifiedEventType) null);  // doesn't matter what contents are
        handler.handleEvent(event);

        verify(renderer).setSearchCriteria(criteria);
        verify(renderer, times(1)).getRefreshEventHandler();
    }

    /** Test handleEvent() with a normal setup. */
    @SuppressWarnings("unchecked")
    @Test public void testHandleEventNormal() {
        ViewEventHandler refreshHandler = mock(ViewEventHandler.class);
        IBackendDataRenderer<String, String> renderer = mock(IBackendDataRenderer.class);
        when(renderer.getRefreshEventHandler()).thenReturn(refreshHandler);

        String criteria = "criteria";
        IBackendDataPresenter<String, String> presenter = mock(IBackendDataPresenter.class);
        when(presenter.getDefaultCriteria()).thenReturn(criteria);
        when(presenter.getRenderer()).thenReturn(renderer);

        BackendDataCriteriaResetHandler<String, String> handler = new BackendDataCriteriaResetHandler<String, String>(presenter);
        UnifiedEvent event = new UnifiedEvent((UnifiedEventType) null);  // doesn't matter what contents are
        handler.handleEvent(event);

        verify(renderer).setSearchCriteria(criteria);
        verify(refreshHandler).handleEvent(event);
    }
}
