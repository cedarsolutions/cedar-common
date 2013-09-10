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
package com.cedarsolutions.web.metadata;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

/**
 * Unit tests for HttpStatusCode.
 * @author Kenneth J. Pronovici <pronovic@ieee.org>
 */
public class HttpStatusCodeTest {

    /** Test convert() and getValue(). */
    @Test public void testConvert() {
        for (HttpStatusCode httpStatusCode : HttpStatusCode.values()) {
            Integer value = httpStatusCode.getValue();
            assertEquals(httpStatusCode, HttpStatusCode.convert(value));
        }

        assertEquals(HttpStatusCode.UNKNOWN, HttpStatusCode.convert(null));
        assertEquals(HttpStatusCode.UNKNOWN, HttpStatusCode.convert(9999));
    }

}
