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

import org.junit.Test;

import com.cedarsolutions.junit.gwt.StubbedClientTestCase;

/**
 * Unit tests for AbstractEventHandler.
 * @author Kenneth J. Pronovici <pronovic@ieee.org>
 */
public class AbstractEventHandlerTest extends StubbedClientTestCase {

    /** Test AbstractEventHandler. */
    @Test public void testAbstractEventHandler() {
        AbstractEventHandler<String> handler = new EventHandler("parent");
        assertEquals("parent", handler.getParent());

        handler.setParent("whatever");
        assertEquals("whatever", handler.getParent());
    }

    /** Concrete class for testing. */
    private static class EventHandler extends AbstractEventHandler<String> {
        protected EventHandler(String parent) {
            super(parent);
        }
    }

}
