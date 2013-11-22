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

import org.junit.Test;

import com.cedarsolutions.junit.gwt.StubbedClientTestCase;
import com.google.gwt.event.dom.client.ClickEvent;

/**
 * Unit tests for AbstractClickHandler.
 * @author Kenneth J. Pronovici <pronovic@ieee.org>
 */
public class AbstractClickHandlerTest extends StubbedClientTestCase {

    /** Test AbstractClickHandler. */
    @Test public void testAbstractClickHandler() {
        EventHandler handler = new EventHandler("parent");
        assertEquals("parent", handler.getParent());
        ClickEvent clickEvent = mock(ClickEvent.class);
        handler.onClick(clickEvent);
        assertSame(clickEvent, handler.event);
    }

    /** Concrete class for testing. */
    private static class EventHandler extends AbstractClickHandler<String> {
        protected ClickEvent event;

        protected EventHandler(String parent) {
            super(parent);
        }

        @Override
        public void onClick(ClickEvent event) {
            this.event = event;
        }
    }

}
