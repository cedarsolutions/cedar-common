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
package com.cedarsolutions.util.gwt;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import org.junit.Test;

import com.cedarsolutions.junit.gwt.classloader.GwtResourceCreator;
import com.cedarsolutions.shared.domain.LocalizableMessage;
import com.google.gwt.i18n.client.ConstantsWithLookup;

/**
 * Unit tests for GwtLocalizationUtils.
 * @author Kenneth J. Pronovici <pronovic@ieee.org>
 */
public class GwtLocalizationUtilsTest {

    /** Test translate(). */
    @Test public void testTranslate() {
        LocalizableMessage message = null;
        String result = null;
        TestConstants constants = GwtResourceCreator.create(TestConstants.class);

        message = new LocalizableMessage(null, null, null);
        result = GwtLocalizationUtils.translate(message, constants, "prefix");
        assertNull(result);

        message = new LocalizableMessage(null, null, "fallback");
        result = GwtLocalizationUtils.translate(message, constants, "prefix");
        assertEquals("fallback", result);

        message = new LocalizableMessage("KEYX", null, "fallback");
        result = GwtLocalizationUtils.translate(message, constants, "prefix");
        assertEquals("fallback", result);

        message = new LocalizableMessage("KEYX", "context", "fallback");
        result = GwtLocalizationUtils.translate(message, constants, "prefix");
        assertEquals("fallback", result);

        message = new LocalizableMessage("KEY1", null, "fallback");
        result = GwtLocalizationUtils.translate(message, constants, "prefix");
        assertEquals("prefix_KEY1", result);

        message = new LocalizableMessage("KEY1", null, "fallback");
        result = GwtLocalizationUtils.translate(message, null, "prefix");
        assertEquals("fallback", result);  // fall back to message if there are no constants

        message = new LocalizableMessage("KEY1", "whatever", "fallback");
        result = GwtLocalizationUtils.translate(message, constants, "prefix");
        assertEquals("fallback", result);

        message = new LocalizableMessage("KEY2", null, "fallback");
        result = GwtLocalizationUtils.translate(message, constants, "prefix");
        assertEquals("fallback", result);

        message = new LocalizableMessage("KEY2", "whatever", "fallback");
        result = GwtLocalizationUtils.translate(message, constants, "prefix");
        assertEquals("fallback", result);

        message = new LocalizableMessage("KEY2", "context1", "fallback");
        result = GwtLocalizationUtils.translate(message, constants, "prefix");
        assertEquals("prefix_KEY2_context1", result);

        message = new LocalizableMessage("KEY2", "context2", "fallback");
        result = GwtLocalizationUtils.translate(message, constants, "prefix");
        assertEquals("prefix_KEY2_context2", result);

        message = new LocalizableMessage("KEY2", "context3", "fallback");
        result = GwtLocalizationUtils.translate(message, constants, "prefix");
        assertEquals("fallback", result);
    }

    /** Constants interface that we can use for testing. */
    protected interface TestConstants extends ConstantsWithLookup {

        @DefaultStringValue("prefix_KEY1")
        String prefix_KEY1();

        @DefaultStringValue("prefix_KEY2_context1")
        String prefix_KEY2_context1();

        @DefaultStringValue("prefix_KEY2_context2")
        String prefix_KEY2_context2();

        @DefaultIntValue(999)
        int prefix_KEY2_context3();

    }
}
