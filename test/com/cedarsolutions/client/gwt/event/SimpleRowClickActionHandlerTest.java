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
package com.cedarsolutions.client.gwt.event;

import static org.junit.Assert.assertSame;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verifyNoMoreInteractions;

import org.junit.Test;
import org.mockito.InOrder;
import org.mockito.Mockito;

import com.cedarsolutions.junit.gwt.StubbedClientTestCase;
import com.cedarsolutions.web.metadata.NativeEventType;

/**
 * Unit tests for SimpleRowClickActionHandler.
 * @author Kenneth J. Pronovici <pronovic@ieee.org>
 */
public class SimpleRowClickActionHandlerTest extends StubbedClientTestCase {

    /** Test SimpleRowClickActionHandler when there's a selection column. */
    @Test public void testWithSelectionColumn() {
        // Unfortunately, it's not possible to mock NativeEvent, so
        // we just test the handleSelectedRow() method instead of the
        // onCellPreview() method.  Better than nothing.

        IParent parent = mock(IParent.class);

        WithSelectionColumn handler = new WithSelectionColumn(parent);
        assertSame(parent, handler.getParent());

        InOrder order = Mockito.inOrder(parent);

        handler.handleSelectedRow(null, 0, null);       // null event is ignored
        verifyNoMoreInteractions(parent);

        handler.handleSelectedRow("blech", 0, null);    // non-CLICK event is ignored
        verifyNoMoreInteractions(parent);

        handler.handleSelectedRow("blech", 5, null);    // non-CLICK event is ignored
        verifyNoMoreInteractions(parent);

        handler.handleSelectedRow(null, 0, "");         // null event is ignored
        verifyNoMoreInteractions(parent);

        handler.handleSelectedRow("blech", 0, "");      // non-CLICK event is ignored
        verifyNoMoreInteractions(parent);

        handler.handleSelectedRow("blech", 5, "");      // non-CLICK event is ignored
        verifyNoMoreInteractions(parent);

        handler.handleSelectedRow(null, 0, "hello");    // null event is ignored
        verifyNoMoreInteractions(parent);

        handler.handleSelectedRow("blech", 0, "hello"); // non-CLICK event is ignored
        verifyNoMoreInteractions(parent);

        handler.handleSelectedRow("blech", 5, "hello"); // non-CLICK event is ignored
        verifyNoMoreInteractions(parent);

        handler.handleSelectedRow(NativeEventType.CLICK.getValue(), 0, null);     // null rows are ignored
        verifyNoMoreInteractions(parent);

        handler.handleSelectedRow(NativeEventType.CLICK.getValue(), 5, null);     // null rows are ignored
        verifyNoMoreInteractions(parent);

        handler.handleSelectedRow(NativeEventType.CLICK.getValue(), 0, "");       // selection column is ignored
        verifyNoMoreInteractions(parent);

        handler.handleSelectedRow(NativeEventType.CLICK.getValue(), 5, "");       // click on something other than selection column is handled
        order.verify(parent).setValue("");

        handler.handleSelectedRow(NativeEventType.CLICK.getValue(), 0, "hello");  // selection column is ignored
        verifyNoMoreInteractions(parent);

        handler.handleSelectedRow(NativeEventType.CLICK.getValue(), 5, "hello");  // click on something other than selection column is handled
        order.verify(parent).setValue("hello");
    }

    /** Test SimpleRowClickActionHandler when there is not a selection column. */
    @Test public void testWithoutSelectionColumn() {
        // Unfortunately, it's not possible to mock NativeEvent, so
        // we just test the handleSelectedRow() method instead of the
        // onCellPreview() method.  Better than nothing.

        IParent parent = mock(IParent.class);

        WithoutSelectionColumn handler = new WithoutSelectionColumn(parent);
        assertSame(parent, handler.getParent());

        InOrder order = Mockito.inOrder(parent);

        handler.handleSelectedRow(null, 0, null);       // null event is ignored
        verifyNoMoreInteractions(parent);

        handler.handleSelectedRow("blech", 0, null);    // non-CLICK event is ignored
        verifyNoMoreInteractions(parent);

        handler.handleSelectedRow("blech", 5, null);    // non-CLICK event is ignored
        verifyNoMoreInteractions(parent);

        handler.handleSelectedRow(null, 0, "");         // null event is ignored
        verifyNoMoreInteractions(parent);

        handler.handleSelectedRow("blech", 0, "");      // non-CLICK event is ignored
        verifyNoMoreInteractions(parent);

        handler.handleSelectedRow("blech", 5, "");      // non-CLICK event is ignored
        verifyNoMoreInteractions(parent);

        handler.handleSelectedRow(NativeEventType.CLICK.getValue(), 0, null);     // null rows are ignored
        verifyNoMoreInteractions(parent);

        handler.handleSelectedRow(NativeEventType.CLICK.getValue(), 5, null);     // null rows are ignored
        verifyNoMoreInteractions(parent);

        handler.handleSelectedRow(NativeEventType.CLICK.getValue(), 0, "");       // all columns are handled
        order.verify(parent).setValue("");

        handler.handleSelectedRow(NativeEventType.CLICK.getValue(), 5, "");       // all columns are handled
        order.verify(parent).setValue("");

        handler.handleSelectedRow(NativeEventType.CLICK.getValue(), 0, "hello");  // all columns are handled
        order.verify(parent).setValue("hello");

        handler.handleSelectedRow(NativeEventType.CLICK.getValue(), 5, "hello");  // all columns are handled
        order.verify(parent).setValue("hello");
    }

    /** A simple parent interface for testing. */
    private interface IParent {
        void setValue(String value);
    }

    /** A click handler with a selection column (zero, like most of our tables). */
    private static class WithSelectionColumn extends SimpleRowClickActionHandler<IParent, String> {
        public WithSelectionColumn(IParent parent) {
            super(parent);
        }

        @Override
        protected Integer getSelectionColumn() {
            return 0;
        }

        @Override
        protected void handleRow(String value) {
            this.getParent().setValue(value);
        }
    }

    /** A click handler with no selection column. */
    private static class WithoutSelectionColumn extends SimpleRowClickActionHandler<IParent, String> {
        public WithoutSelectionColumn(IParent parent) {
            super(parent);
        }

        @Override
        protected Integer getSelectionColumn() {
            return null;
        }

        @Override
        protected void handleRow(String value) {
            this.getParent().setValue(value);
        }
    }
}
