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

import static org.junit.Assert.assertSame;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;

import org.junit.Test;

import com.cedarsolutions.junit.gwt.StubbedClientTestCase;
import com.cedarsolutions.web.metadata.NativeEventType;

/**
 * Unit tests for AbstractRowClickActionHandler.
 * @author Kenneth J. Pronovici <pronovic@ieee.org>
 */
public class AbstractChangeActionHandlerTest extends StubbedClientTestCase {

    /** Test SimpleClickHandler when there is not a selection column. */
    @Test public void testWithoutSelectionColumn() {
        // Unfortunately, it's not possible to mock NativeEvent, so
        // we just test the handleSelectedRow() method instead of the
        // onCellPreview() method.  Better than nothing.

        IParent parent = mock(IParent.class);

        TestHandler handler = new TestHandler(parent);
        assertSame(parent, handler.getParent());

        handler.handleChangeEvent(null);    // null event is ignored
        verifyNoMoreInteractions(parent);

        handler.handleChangeEvent("blech"); // non-CHANGE event is ignored
        verifyNoMoreInteractions(parent);

        handler.handleChangeEvent(NativeEventType.CHANGE.getValue()); // CHANGE events are handled
        verify(parent).doSomething();
    }

    /** A simple parent interface for testing. */
    private interface IParent {
        void doSomething();
    }

    /** A click handler with a selection column (zero, like most of our tables). */
    private static class TestHandler extends AbstractChangeActionHandler<IParent> {
        public TestHandler(IParent parent) {
            super(parent);
        }

        @Override
        protected void handleEvent() {
            this.getParent().doSomething();
        }
    }
}
