/* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 *
 *              C E D A R
 *          S O L U T I O N S       "Software done right."
 *           S O F T W A R E
 *
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 *
 * Copyright (c) 2011-2012 Kenneth J. Pronovici.
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

/**
 * Unit tests for ExceptionUtils.
 * @author Kenneth J. Pronovici <pronovic@ieee.org>
 */
public class ExceptionUtilsTest {

    /** Test generateStackTrace(). */
    @Test public void testGenerateStackTrace() {
        // Just spot-check, since it's hard to confirm the exact output
        assertEquals("null", ExceptionUtils.generateStackTrace(null));
        assertNotNull(ExceptionUtils.generateStackTrace(new Exception("whatever")));
        assertNotNull(ExceptionUtils.generateStackTrace(new Exception("whatever", new Exception("cause"))));
    }

}
