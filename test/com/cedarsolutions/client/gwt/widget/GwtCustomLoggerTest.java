/* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 *
 *              C E D A R
 *          S O L U T I O N S       "Software done right."
 *           S O F T W A R E
 *
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 *
 * Copyright (c) 2012 Kenneth J. Pronovici.
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
package com.cedarsolutions.client.gwt.widget;

import static com.cedarsolutions.client.gwt.widget.GwtCustomLogger.TABSTOP;
import static org.junit.Assert.assertEquals;

import org.junit.Test;

/**
 * Unit tests for GwtCustomLogger.
 * @author Kenneth J. Pronovici <pronovic@ieee.org>
 */
public class GwtCustomLoggerTest {

    /** Test generatePadding(). */
    @Test public void testGeneratePadding() {
        assertEquals(TABSTOP, GwtCustomLogger.generatePadding(1));
        assertEquals(TABSTOP + TABSTOP, GwtCustomLogger.generatePadding(2));
        assertEquals(TABSTOP + TABSTOP + TABSTOP, GwtCustomLogger.generatePadding(3));
    }

}
