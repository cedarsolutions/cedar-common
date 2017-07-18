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
package com.cedarsolutions.client.gwt.handler;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Test;
import org.mockito.ArgumentCaptor;

import com.cedarsolutions.client.gwt.event.UnifiedEvent;
import com.cedarsolutions.client.gwt.event.UnifiedEventType;
import com.cedarsolutions.client.gwt.event.ViewEventHandler;
import com.cedarsolutions.junit.gwt.StubbedClientTestCase;
import com.google.gwt.user.cellview.client.ColumnSortEvent;

/**
 * Unit tests for AbstractColumnSortEventHandler.
 * @author Kenneth J. Pronovici <pronovic@ieee.org>
 */
public class AbstractColumnSortEventHandlerTest extends StubbedClientTestCase {

    /** Test AbstractColumnSortEventHandler. */
    @Test public void testAbstractColumnSortEventHandler() {
        ColumnSortEvent event = mock(ColumnSortEvent.class);

        View view = mock(View.class);
        SortHandler handler = new SortHandler(view);
        assertSame(view, handler.getParent());

        when(view.getRefreshEventHandler()).thenReturn(null);
        handler.onColumnSort(event);  // just make sure it doesn't blow up

        ArgumentCaptor<UnifiedEvent> captor = ArgumentCaptor.forClass(UnifiedEvent.class);
        ViewEventHandler refreshEventHandler = mock(ViewEventHandler.class);
        when(view.getRefreshEventHandler()).thenReturn(refreshEventHandler);
        handler.onColumnSort(event);  // just make sure it doesn't blow up
        verify(refreshEventHandler).handleEvent(captor.capture());
        assertEquals(UnifiedEventType.SORT_EVENT, captor.getValue().getEventType());
    }

    /** View for testing. */
    private interface View {
        ViewEventHandler getRefreshEventHandler();
    }

    /** Sort handler for testing. */
    private static class SortHandler extends AbstractColumnSortEventHandler<View> {
        SortHandler(View parent) {
            super(parent);
        }

        @Override
        public ViewEventHandler getViewEventHandler() {
            return this.getParent().getRefreshEventHandler();
        }
    }

}
