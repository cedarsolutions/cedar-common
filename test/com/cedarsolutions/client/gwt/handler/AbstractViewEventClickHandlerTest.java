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
import com.google.gwt.event.dom.client.ClickEvent;

/**
 * Unit tests for AbstractViewEventClickHandler.
 * @author Kenneth J. Pronovici <pronovic@ieee.org>
 */
public class AbstractViewEventClickHandlerTest extends StubbedClientTestCase {

    /** Test AbstractViewEventClickHandler. */
    @Test public void testAbstractViewEventClickHandler() {
        ClickEvent event = mock(ClickEvent.class);

        View view = mock(View.class);
        ClickHandler handler = new ClickHandler(view);
        assertSame(view, handler.getParent());

        when(view.getSaveHandler()).thenReturn(null);
        handler.onClick(event);  // just make sure it doesn't blow up

        ArgumentCaptor<UnifiedEvent> captor = ArgumentCaptor.forClass(UnifiedEvent.class);
        ViewEventHandler saveEventHandler = mock(ViewEventHandler.class);
        when(view.getSaveHandler()).thenReturn(saveEventHandler);
        handler.onClick(event);
        verify(saveEventHandler).handleEvent(captor.capture());
        assertEquals(UnifiedEventType.CLICK_EVENT, captor.getValue().getEventType());
        assertSame(event, captor.getValue().getClickEvent());
    }

    /** View for testing. */
    private interface View {
        ViewEventHandler getSaveHandler();
    }

    /** Click handler for testing. */
    protected static class ClickHandler extends AbstractViewEventClickHandler<View> {
        public ClickHandler(View parent) {
            super(parent);
        }

        @Override
        public ViewEventHandler getViewEventHandler() {
            return this.getParent().getSaveHandler();
        }
    }

}
