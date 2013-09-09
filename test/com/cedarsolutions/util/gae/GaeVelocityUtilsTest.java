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
package com.cedarsolutions.util.gae;

import static org.junit.Assert.assertEquals;

import java.util.HashMap;
import java.util.Map;

import org.apache.velocity.VelocityContext;
import org.junit.Test;

/**
 * Unit tests for GaeVelocityUtils.getInstance().
 * @author Kenneth J. Pronovici <pronovic@ieee.org>
 */
public class GaeVelocityUtilsTest {

    /** Template directory used for unit tests. */
    public static final String UNITTEST_TEMPLATE_DIR = "test/com/cedarsolutions/util/gae";

    /** Test renderTemplate(). */
    @Test public void testRenderTemplate() {
        VelocityContext context = new VelocityContext();
        context.put("foo", "Velocity");
        String result = GaeVelocityUtils.getInstance().renderTemplate(UNITTEST_TEMPLATE_DIR, "test.vm", context);
        assertEquals("Hello Velocity World!", result);

        Map<String, Object> map = new HashMap<String, Object>();
        map.put("foo", "Another");
        result = GaeVelocityUtils.getInstance().renderTemplate(UNITTEST_TEMPLATE_DIR, "test.vm", map);
        assertEquals("Hello Another World!", result);
    }

    /** Test createVelocityContext(). */
    @Test public void testCreateVelocityContext() {
        Map<String, Object> map = null;
        VelocityContext context = GaeVelocityUtils.getInstance().createVelocityContext(map);
        assertEquals(0, context.getKeys().length);

        map = new HashMap<String, Object>();
        map.put("key1", "value1");
        map.put("key2", new Integer(3));
        context = GaeVelocityUtils.getInstance().createVelocityContext(map);
        assertEquals(2, context.getKeys().length);
        assertEquals("value1", context.get("key1"));
        assertEquals(new Integer(3), context.get("key2"));
    }

}
