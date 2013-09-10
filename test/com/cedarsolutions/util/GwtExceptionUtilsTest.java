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
package com.cedarsolutions.util;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Test;

import com.cedarsolutions.util.gwt.GwtExceptionUtils;

/**
 * Unit tests for GwtExceptionUtils.
 * @author Kenneth J. Pronovici <pronovic@ieee.org>
 */
public class GwtExceptionUtilsTest {

    /** Test generateStackTrace(). */
    @Test public void testGenerateStackTrace() {
        // Just spot-check, since it's hard to confirm the exact output
        assertEquals("null", GwtExceptionUtils.generateStackTrace(null));
        assertNotNull(GwtExceptionUtils.generateStackTrace(new Exception("whatever")));
        assertNotNull(GwtExceptionUtils.generateStackTrace(new Exception("whatever", new Exception("cause"))));
    }

}
