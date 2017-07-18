/* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 *
 *              C E D A R
 *          S O L U T I O N S       "Software done right."
 *           S O F T W A R E
 *
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 *
 * Copyright (c) 2013-2014 Kenneth J. Pronovici.
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
package com.cedarsolutions.junit.gwt.classloader;

import static com.cedarsolutions.junit.gwt.classloader.GwtResourceCreator.substituteReplaceVariables;
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.when;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

import com.cedarsolutions.client.gwt.module.IClientSession;
import com.google.gwt.i18n.client.Constants;
import com.google.gwt.i18n.client.ConstantsWithLookup;
import com.google.gwt.i18n.client.Messages;

/**
 * Unit tests for GwtResourceCreator.
 * @author Kenneth J. Pronovici <pronovic@ieee.org>
 */
public class GwtResourceCreatorTest {

    /** Test create() for the general case. */
    @Test public void testCreateGeneralCase() {
        IClientSession session = GwtResourceCreator.create(IClientSession.class);
        when(session.isInitialized()).thenReturn(false);
        assertFalse(session.isInitialized());
        when(session.isInitialized()).thenReturn(true);
        assertTrue(session.isInitialized());
    }

    /** Test create() for a messages interface. */
    @Test public void testCreateMessages() {
        TestMessages messages = GwtResourceCreator.create(TestMessages.class);
        assertEquals("Default message", messages.message());
    }

    /** Test create() for a Constants interface. */
    @Test public void testCreateConstants() {
        Map<String, String>  map = new HashMap<String, String>();
        map.put("key1", "value1");
        map.put("key2", "value2");

        TestConstants constants = GwtResourceCreator.create(TestConstants.class);

        assertEquals(true, constants.booleanValue());
        assertEquals(3.0, constants.doubleValue(), 0.0);
        assertEquals(99.0, constants.floatValue(), 0.0);
        assertEquals(15, constants.intValue());
        assertEquals("string", constants.stringValue());
        assertArrayEquals(new String[] { "one", "two", "three", }, constants.stringArrayValue());
        assertEquals(map, constants.stringMapValue());
    }

    /** Test create() for a ConstantsWithLookup interface. */
    @Test public void testCreateConstantsWithLookup() {
        Map<String, String>  map = new HashMap<String, String>();
        map.put("key1", "value1");
        map.put("key2", "value2");

        TestConstantsWithLookup constants = GwtResourceCreator.create(TestConstantsWithLookup.class);

        assertEquals(true, constants.booleanValue());
        assertEquals(3.0, constants.doubleValue(), 0.0);
        assertEquals(99.0, constants.floatValue(), 0.0);
        assertEquals(15, constants.intValue());
        assertEquals("string", constants.stringValue());
        assertArrayEquals(new String[] { "one", "two", "three", }, constants.stringArrayValue());
        assertEquals(map, constants.stringMapValue());

        assertEquals(true, constants.getBoolean("booleanValue"));
        assertEquals(3.0, constants.getDouble("doubleValue"), 0.0);
        assertEquals(99.0, constants.getFloat("floatValue"), 0.0);
        assertEquals(15, constants.getInt("intValue"));
        assertEquals("string", constants.getString("stringValue"));
        assertArrayEquals(new String[] { "one", "two", "three", }, constants.getStringArray("stringArrayValue"));
        assertEquals(map, constants.getMap("stringMapValue"));
    }

    /** Test parseDefaultStringMapValue(). */
    @Test public void testParseDefaultStringMapValue() {
        Map<String, String> empty = new HashMap<String, String>();

        Map<String, String> one = new HashMap<String, String>();
        one.put("key1", "value1");

        Map<String, String> two = new HashMap<String, String>();
        two.put("key1", "value1");
        two.put("key2", "value2");

        Map<String, String> three = new HashMap<String, String>();
        three.put("key1", null);
        three.put(null, "value2");

        assertEquals(empty, GwtResourceCreator.parseDefaultStringMapValue(null));
        assertEquals(empty, GwtResourceCreator.parseDefaultStringMapValue(new String[] { }));
        assertEquals(empty, GwtResourceCreator.parseDefaultStringMapValue(new String[] { "key1", }));
        assertEquals(empty, GwtResourceCreator.parseDefaultStringMapValue(new String[] { "key1", "value1", "key2", }));
        assertEquals(one, GwtResourceCreator.parseDefaultStringMapValue(new String[] { "key1", "value1",  }));
        assertEquals(two, GwtResourceCreator.parseDefaultStringMapValue(new String[] { "key1", "value1", "key2", "value2", }));
        assertEquals(three, GwtResourceCreator.parseDefaultStringMapValue(new String[] { "key1", null,  null, "value2", }));
    }

    /** Test substituteReplaceVariables(). */
    @Test public void testSubstituteReplaceVariables() {
        assertSubsituteReplaceWorks("", "");
        assertSubsituteReplaceWorks("whatever", "whatever");
        assertSubsituteReplaceWorks("whatever null", "whatever {0}", (Object) null);
        assertSubsituteReplaceWorks("whatever hello", "whatever {0}", "hello");
        assertSubsituteReplaceWorks("whatever 12", "whatever {0}", 12);
        assertSubsituteReplaceWorks("whatever 196.423", "whatever {0}", 196.423);
        assertSubsituteReplaceWorks("whatever true", "whatever {0}", true);
        assertSubsituteReplaceWorks("whatever false", "whatever {0}", false);
        assertSubsituteReplaceWorks("whatever {-1}", "whatever {-1}");  // {-1} is not a parameter, so it's ignored
        assertSubsituteReplaceWorks("whatever {a}", "whatever {a}");    // {a} is not a parameter, so it's ignored
        assertSubsituteReplaceWorks("whatever A B C", "whatever {0} {1} {2}", "A", "B", "C");
        assertSubsituteReplaceWorks("whatever C B A", "whatever {2} {1} {0}", "A", "B", "C");

        assertSubsituteReplaceFails("whatever", (Object[]) null);            // null input
        assertSubsituteReplaceFails(null, "whatever");                       // null input
        assertSubsituteReplaceFails("whatever", "arg1");                     // no {0} for "arg1"
        assertSubsituteReplaceFails("whatever", 12);                         // no {0} for 12
        assertSubsituteReplaceFails("whatever {0}");                         // no argument for {0}
        assertSubsituteReplaceFails("whatever {1}", "arg1");                 // no {0} for "arg1"
        assertSubsituteReplaceFails("whatever {0} {1}", "arg1");             // no argument for {0}
        assertSubsituteReplaceFails("whatever {-1}", "arg1");                // no {0} for "arg1"
        assertSubsituteReplaceFails("whatever {2} {1}", "A", "B", "C");      // no argument {0} for "A"
        assertSubsituteReplaceFails("whatever {3} {2} {1}", "A", "B", "C");  // uses 1/2/3 instead of 0/1/2
    }

    /** Assert that substituteReplaceVariables() succeeds for particular input. */
    private static void assertSubsituteReplaceWorks(String expected, String message, Object... arguments) {
        assertEquals(expected, substituteReplaceVariables(message, arguments));
    }

    /** Assert that substituteReplaceVariables() fails for particular input. */
    private static void assertSubsituteReplaceFails(String message, Object... arguments) {
        try {
            substituteReplaceVariables(message, arguments);
            fail("Expected IllegalArgumentException");
        } catch (IllegalArgumentException e) { }
    }


    /** Constants interface that we can use for testing. */
    protected interface TestMessages extends Messages {

        @DefaultMessage("Default message")
        String message();

    }

    /** Constants interface that we can use for testing. */
    protected interface TestConstants extends Constants {
        @DefaultBooleanValue(true)
        boolean booleanValue();

        @DefaultDoubleValue(3.0)
        double doubleValue();

        @DefaultFloatValue((float) 99.0)
        float floatValue();

        @DefaultIntValue(15)
        int intValue();

        @DefaultStringValue("string")
        String stringValue();

        @DefaultStringArrayValue({ "one", "two", "three" })
        String[] stringArrayValue();

        @DefaultStringMapValue({ "key1", "value1", "key2", "value2" })
        Map<String, String> stringMapValue();
    }

    /** ConstantsWithLookup interface that we can use for testing. */
    protected interface TestConstantsWithLookup extends ConstantsWithLookup {
        @DefaultBooleanValue(true)
        boolean booleanValue();

        @DefaultDoubleValue(3.0)
        double doubleValue();

        @DefaultFloatValue((float) 99.0)
        float floatValue();

        @DefaultIntValue(15)
        int intValue();

        @DefaultStringValue("string")
        String stringValue();

        @DefaultStringArrayValue({ "one", "two", "three" })
        String[] stringArrayValue();

        @DefaultStringMapValue({ "key1", "value1", "key2", "value2" })
        Map<String, String> stringMapValue();
    }

}
