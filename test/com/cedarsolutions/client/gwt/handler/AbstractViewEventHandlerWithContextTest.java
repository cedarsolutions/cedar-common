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
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertSame;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import org.junit.Test;

import com.cedarsolutions.client.gwt.event.UnifiedEventWithContext;
import com.cedarsolutions.junit.gwt.StubbedClientTestCase;

/**
 * Unit tests for AbstractViewEventHandlerWithContext.
 * @author Kenneth J. Pronovici <pronovic@ieee.org>
 */
public class AbstractViewEventHandlerWithContextTest extends StubbedClientTestCase {

    /** Test AbstractViewEventHandlerWithContext. */
    @Test public void testAbstractViewEventHandlerWithContext() {
        Presenter presenter = mock(Presenter.class);

        Handler handler = new Handler(presenter);
        assertNotNull(handler);
        assertSame(presenter, handler.getParent());

        UnifiedEventWithContext<String> event = new UnifiedEventWithContext<String>(CLICK_EVENT, "userId");
        handler.handleEvent(event);
        verify(presenter).showUserSearchById("userId");
    }

    /** Presenter for testing. */
    private interface Presenter {
        void showUserSearchById(String id);
    }

    /** Handler for testing. */
    protected static class Handler extends AbstractViewEventHandlerWithContext<Presenter, String> {
        public Handler(Presenter parent) {
            super(parent);
        }

        @Override
        public void handleEvent(UnifiedEventWithContext<String> event) {
            this.getParent().showUserSearchById(event.getContext());
        }
    }

}
