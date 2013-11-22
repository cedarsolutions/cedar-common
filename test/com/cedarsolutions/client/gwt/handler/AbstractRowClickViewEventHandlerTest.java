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

import static com.cedarsolutions.client.gwt.event.UnifiedEventType.CLICK_EVENT;
import static org.junit.Assert.assertSame;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import org.junit.Test;
import org.mockito.InOrder;
import org.mockito.Mockito;

import com.cedarsolutions.client.gwt.event.UnifiedEventWithContext;
import com.cedarsolutions.client.gwt.event.ViewEventHandlerWithContext;
import com.cedarsolutions.junit.gwt.StubbedClientTestCase;
import com.cedarsolutions.web.metadata.NativeEventType;

/**
 * Unit tests for AbstractRowClickViewEventHandler.
 * @author Kenneth J. Pronovici <pronovic@ieee.org>
 */
public class AbstractRowClickViewEventHandlerTest extends StubbedClientTestCase {

    /** Test AbstractRowClickViewEventHandler when there's a selection column. */
    @SuppressWarnings("unchecked")
    @Test public void testWithSelectionColumn() {
        // Unfortunately, it's not possible to mock NativeEvent, so
        // we just test the handleSelectedRow() method instead of the
        // onCellPreview() method.  Better than nothing.

        ViewEventHandlerWithContext<String> parentHandler = mock(ViewEventHandlerWithContext.class);
        IParent parent = mock(IParent.class);
        when(parent.getHandler()).thenReturn(parentHandler);

        WithSelectionColumn handler = new WithSelectionColumn(parent);
        assertSame(parent, handler.getParent());

        InOrder order = Mockito.inOrder(parent.getHandler());

        UnifiedEventWithContext<String> emptyRow = new UnifiedEventWithContext<String>(CLICK_EVENT, "");
        UnifiedEventWithContext<String> helloRow = new UnifiedEventWithContext<String>(CLICK_EVENT, "hello");

        handler.handleSelectedRow(null, 0, null);       // null event is ignored
        verifyNoMoreInteractions(parent.getHandler());

        handler.handleSelectedRow("blech", 0, null);    // non-CLICK event is ignored
        verifyNoMoreInteractions(parent.getHandler());

        handler.handleSelectedRow("blech", 5, null);    // non-CLICK event is ignored
        verifyNoMoreInteractions(parent.getHandler());

        handler.handleSelectedRow(null, 0, "");         // null event is ignored
        verifyNoMoreInteractions(parent.getHandler());

        handler.handleSelectedRow("blech", 0, "");      // non-CLICK event is ignored
        verifyNoMoreInteractions(parent.getHandler());

        handler.handleSelectedRow("blech", 5, "");      // non-CLICK event is ignored
        verifyNoMoreInteractions(parent.getHandler());

        handler.handleSelectedRow(null, 0, "hello");    // null event is ignored
        verifyNoMoreInteractions(parent.getHandler());

        handler.handleSelectedRow("blech", 0, "hello"); // non-CLICK event is ignored
        verifyNoMoreInteractions(parent.getHandler());

        handler.handleSelectedRow("blech", 5, "hello"); // non-CLICK event is ignored
        verifyNoMoreInteractions(parent.getHandler());

        handler.handleSelectedRow(NativeEventType.CLICK.getValue(), 0, null);     // null rows are ignored
        verifyNoMoreInteractions(parent.getHandler());

        handler.handleSelectedRow(NativeEventType.CLICK.getValue(), 5, null);     // null rows are ignored
        verifyNoMoreInteractions(parent.getHandler());

        handler.handleSelectedRow(NativeEventType.CLICK.getValue(), 0, "");       // selection column is ignored
        verifyNoMoreInteractions(parent.getHandler());

        handler.handleSelectedRow(NativeEventType.CLICK.getValue(), 5, "");       // click on something other than selection column is handled
        order.verify(parent.getHandler()).handleEvent(emptyRow);

        handler.handleSelectedRow(NativeEventType.CLICK.getValue(), 0, "hello");  // selection column is ignored
        verifyNoMoreInteractions(parent.getHandler());

        handler.handleSelectedRow(NativeEventType.CLICK.getValue(), 5, "hello");  // click on something other than selection column is handled
        order.verify(parent.getHandler()).handleEvent(helloRow);
    }

    /** Test AbstractRowClickViewEventHandler when there is not a selection column. */
    @SuppressWarnings("unchecked")
    @Test public void testWithoutSelectionColumn() {
        // Unfortunately, it's not possible to mock NativeEvent, so
        // we just test the handleSelectedRow() method instead of the
        // onCellPreview() method.  Better than nothing.

        ViewEventHandlerWithContext<String> parentHandler = mock(ViewEventHandlerWithContext.class);
        IParent parent = mock(IParent.class);
        when(parent.getHandler()).thenReturn(parentHandler);

        WithoutSelectionColumn handler = new WithoutSelectionColumn(parent);
        assertSame(parent, handler.getParent());

        InOrder order = Mockito.inOrder(parent.getHandler());

        UnifiedEventWithContext<String> emptyRow = new UnifiedEventWithContext<String>(CLICK_EVENT, "");
        UnifiedEventWithContext<String> helloRow = new UnifiedEventWithContext<String>(CLICK_EVENT, "hello");

        handler.handleSelectedRow(null, 0, null);       // null event is ignored
        verifyNoMoreInteractions(parent.getHandler());

        handler.handleSelectedRow("blech", 0, null);    // non-CLICK event is ignored
        verifyNoMoreInteractions(parent.getHandler());

        handler.handleSelectedRow("blech", 5, null);    // non-CLICK event is ignored
        verifyNoMoreInteractions(parent.getHandler());

        handler.handleSelectedRow(null, 0, "");         // null event is ignored
        verifyNoMoreInteractions(parent.getHandler());

        handler.handleSelectedRow("blech", 0, "");      // non-CLICK event is ignored
        verifyNoMoreInteractions(parent.getHandler());

        handler.handleSelectedRow("blech", 5, "");      // non-CLICK event is ignored
        verifyNoMoreInteractions(parent.getHandler());

        handler.handleSelectedRow(null, 0, "hello");    // null event is ignored
        verifyNoMoreInteractions(parent.getHandler());

        handler.handleSelectedRow("blech", 0, "hello"); // non-CLICK event is ignored
        verifyNoMoreInteractions(parent.getHandler());

        handler.handleSelectedRow("blech", 5, "hello"); // non-CLICK event is ignored
        verifyNoMoreInteractions(parent.getHandler());

        handler.handleSelectedRow(NativeEventType.CLICK.getValue(), 0, null);     // null rows are ignored
        verifyNoMoreInteractions(parent.getHandler());

        handler.handleSelectedRow(NativeEventType.CLICK.getValue(), 5, null);     // null rows are ignored
        verifyNoMoreInteractions(parent.getHandler());

        handler.handleSelectedRow(NativeEventType.CLICK.getValue(), 0, "");       // all columns are handled
        order.verify(parent.getHandler()).handleEvent(emptyRow);

        handler.handleSelectedRow(NativeEventType.CLICK.getValue(), 5, "");       // all columns are handled
        order.verify(parent.getHandler()).handleEvent(emptyRow);

        handler.handleSelectedRow(NativeEventType.CLICK.getValue(), 0, "hello");  // all columns are handled
        order.verify(parent.getHandler()).handleEvent(helloRow);

        handler.handleSelectedRow(NativeEventType.CLICK.getValue(), 5, "hello");  // all columns are handled
        order.verify(parent.getHandler()).handleEvent(helloRow);
    }

    /** A simple parent interface for testing. */
    private interface IParent {
        ViewEventHandlerWithContext<String> getHandler();
    }

    /** A click handler with a selection column (zero, like most of our tables). */
    private static class WithSelectionColumn extends AbstractRowClickViewEventHandler<IParent, String> {
        public WithSelectionColumn(IParent parent) {
            super(parent);
        }

        @Override
        protected Integer getSelectionColumn() {
            return 0;
        }

        @Override
        protected ViewEventHandlerWithContext<String> getViewEventHandler() {
            return this.getParent().getHandler();
        }
    }

    /** A click handler with no selection column. */
    private static class WithoutSelectionColumn extends AbstractRowClickViewEventHandler<IParent, String> {
        public WithoutSelectionColumn(IParent parent) {
            super(parent);
        }

        @Override
        protected Integer getSelectionColumn() {
            return null;
        }

        @Override
        protected ViewEventHandlerWithContext<String> getViewEventHandler() {
            return this.getParent().getHandler();
        }
    }
}
