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
package com.cedarsolutions.client.gwt.event;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.mock;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

import com.google.gwt.event.dom.client.ClickEvent;

/**
 * Unit tests for UnifiedEventWithContext.
 * @author Kenneth J. Pronovici <pronovic@ieee.org>
 */
@SuppressWarnings("unlikely-arg-type")
public class UnifiedEventWithContextTest {

    /** Test the ClickEvent constructor. */
    @Test public void testConstructorClickEvent() {
        UnifiedEventWithContext<String> event = new UnifiedEventWithContext<String>(null);
        assertNotNull(event);
        assertEquals(UnifiedEventType.DEFAULT_EVENT, event.getEventType());
        assertNull(event.getClickEvent());
        assertNull(event.getContext());

        event = new UnifiedEventWithContext<String>((UnifiedEventType) null, null);
        assertNotNull(event);
        assertNull(event.getEventType());
        assertNull(event.getClickEvent());
        assertNull(event.getContext());

        event = new UnifiedEventWithContext<String>("hello");
        assertNotNull(event);
        assertEquals(UnifiedEventType.DEFAULT_EVENT, event.getEventType());
        assertNull(event.getClickEvent());
        assertEquals("hello", event.getContext());

        event = new UnifiedEventWithContext<String>(UnifiedEventType.MENU_EVENT, "hello");
        assertNotNull(event);
        assertEquals(UnifiedEventType.MENU_EVENT, event.getEventType());
        assertNull(event.getClickEvent());
        assertEquals("hello", event.getContext());

        event = new UnifiedEventWithContext<String>((ClickEvent) null, null);
        assertNotNull(event);
        assertEquals(UnifiedEventType.CLICK_EVENT, event.getEventType());
        assertNull(event.getClickEvent());
        assertNull(event.getContext());

        ClickEvent clickEvent = mock(ClickEvent.class);
        event = new UnifiedEventWithContext<String>(clickEvent, "hello");
        assertNotNull(event);
        assertEquals(UnifiedEventType.CLICK_EVENT, event.getEventType());
        assertSame(clickEvent, event.getClickEvent());
        assertEquals("hello", event.getContext());
    }

    /** Test equals(). */
    @Test public void testEquals() {
        ClickEvent clickEvent1 = mock(ClickEvent.class);
        ClickEvent clickEvent2 = mock(ClickEvent.class);

        UnifiedEventWithContext<String> event1;
        UnifiedEventWithContext<String> event2;

        event1 = new UnifiedEventWithContext<String>(UnifiedEventType.MENU_EVENT, "hello");
        event2 = new UnifiedEventWithContext<String>(UnifiedEventType.MENU_EVENT, "hello");
        assertTrue(event1.equals(event2));
        assertTrue(event2.equals(event1));

        event1 = new UnifiedEventWithContext<String>(clickEvent1, "hello");
        event2 = new UnifiedEventWithContext<String>(clickEvent1, "hello");
        assertTrue(event1.equals(event2));
        assertTrue(event2.equals(event1));

        try {
            event1 = new UnifiedEventWithContext<String>(UnifiedEventType.MENU_EVENT, "hello");
            event2 = null;
            event1.equals(event2);
            fail("Expected NullPointerException");
        } catch (NullPointerException e) { }

        try {
            event1 = new UnifiedEventWithContext<String>(UnifiedEventType.MENU_EVENT, "hello");
            event2 = null;
            event1.equals("blech");
            fail("Expected ClassCastException");
        } catch (ClassCastException e) { }

        event1 = new UnifiedEventWithContext<String>(UnifiedEventType.MENU_EVENT, "hello");
        event2 = new UnifiedEventWithContext<String>(UnifiedEventType.BUTTON_EVENT, "hello");
        assertFalse(event1.equals(event2));
        assertFalse(event2.equals(event1));

        event1 = new UnifiedEventWithContext<String>(UnifiedEventType.MENU_EVENT, "hello");
        event2 = new UnifiedEventWithContext<String>(UnifiedEventType.MENU_EVENT, "blech");
        assertFalse(event1.equals(event2));
        assertFalse(event2.equals(event1));

        event1 = new UnifiedEventWithContext<String>(clickEvent1, "hello");
        event2 = new UnifiedEventWithContext<String>(clickEvent2, "hello");
        assertFalse(event1.equals(event2));
        assertFalse(event2.equals(event1));

        event1 = new UnifiedEventWithContext<String>(clickEvent1, "hello");
        event2 = new UnifiedEventWithContext<String>(clickEvent1, "blech");
        assertFalse(event1.equals(event2));
        assertFalse(event2.equals(event1));
    }

    /** Test hashCode(). */
    @Test public void testHashCode() {
        ClickEvent clickEvent1 = mock(ClickEvent.class);
        ClickEvent clickEvent2 = mock(ClickEvent.class);

        UnifiedEventWithContext<String> event1 = new UnifiedEventWithContext<String>(UnifiedEventType.MENU_EVENT, "hello");
        UnifiedEventWithContext<String> event2 = new UnifiedEventWithContext<String>(UnifiedEventType.MENU_EVENT, "blech");
        UnifiedEventWithContext<String> event3 = new UnifiedEventWithContext<String>(UnifiedEventType.BUTTON_EVENT, "hello");
        UnifiedEventWithContext<String> event4 = new UnifiedEventWithContext<String>(clickEvent1, "hello");
        UnifiedEventWithContext<String> event5 = new UnifiedEventWithContext<String>(clickEvent1, "blech");
        UnifiedEventWithContext<String> event6 = new UnifiedEventWithContext<String>(clickEvent2, "hello");
        UnifiedEventWithContext<String> event7 = new UnifiedEventWithContext<String>(UnifiedEventType.MENU_EVENT, "hello");  // same as event1

        Map<UnifiedEventWithContext<String>, String> map = new HashMap<UnifiedEventWithContext<String>, String>();
        map.put(event1, "ONE");
        map.put(event2, "TWO");
        map.put(event3, "THREE");
        map.put(event4, "FOUR");
        map.put(event5, "FIVE");
        map.put(event6, "SIX");

        assertEquals("ONE", map.get(event1));
        assertEquals("TWO", map.get(event2));
        assertEquals("THREE", map.get(event3));
        assertEquals("FOUR", map.get(event4));
        assertEquals("FIVE", map.get(event5));
        assertEquals("SIX", map.get(event6));
        assertEquals("ONE", map.get(event7));
    }

}
