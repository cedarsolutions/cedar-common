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

import org.junit.Test;

import com.cedarsolutions.client.gwt.event.UnifiedEvent;
import com.cedarsolutions.junit.gwt.StubbedClientTestCase;


/**
 * Unit tests for AbstractViewEventHandler.
 * @author Kenneth J. Pronovici <pronovic@ieee.org>
 */
public class AbstractViewEventHandlerTest extends StubbedClientTestCase {

    /** Test AbstractViewEventHandler. */
    @Test public void testAbstractViewEventHandler() {
        Presenter parent = mock(Presenter.class);

        EventHandler handler = new EventHandler(parent);
        assertSame(parent, handler.getParent());

        handler.handleEvent(null);  // event is ignored
        verify(parent).logout();
    }

    /** Presenter. */
    private interface Presenter {
        void logout();
    }

    /** Event handler for testing. */
    protected static class EventHandler extends AbstractViewEventHandler<Presenter> {
        public EventHandler(Presenter parent) {
            super(parent);
        }

        @Override
        public void handleEvent(UnifiedEvent event) {
            this.getParent().logout();
        }
    }

}
