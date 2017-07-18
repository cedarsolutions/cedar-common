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
 * Unit tests for UnifiedEvent.
 * @author Kenneth J. Pronovici <pronovic@ieee.org>
 */
@SuppressWarnings("unlikely-arg-type")
public class UnifiedEventTest {

    /** Test the ClickEvent constructor. */
    @Test public void testConstructorClickEvent() {
        UnifiedEvent event = new UnifiedEvent();
        assertNotNull(event);
        assertEquals(UnifiedEventType.DEFAULT_EVENT, event.getEventType());
        assertNull(event.getClickEvent());

        event = new UnifiedEvent((UnifiedEventType) null);
        assertNotNull(event);
        assertNull(event.getEventType());
        assertNull(event.getClickEvent());

        event = new UnifiedEvent(UnifiedEventType.MENU_EVENT);
        assertNotNull(event);
        assertEquals(UnifiedEventType.MENU_EVENT, event.getEventType());
        assertNull(event.getClickEvent());

        event = new UnifiedEvent((ClickEvent) null);
        assertNotNull(event);
        assertEquals(UnifiedEventType.CLICK_EVENT, event.getEventType());
        assertNull(event.getClickEvent());

        ClickEvent clickEvent = mock(ClickEvent.class);
        event = new UnifiedEvent(clickEvent);
        assertNotNull(event);
        assertEquals(UnifiedEventType.CLICK_EVENT, event.getEventType());
        assertSame(clickEvent, event.getClickEvent());
    }

    /** Test equals(). */
    @Test public void testEquals() {
        ClickEvent clickEvent1 = mock(ClickEvent.class);
        ClickEvent clickEvent2 = mock(ClickEvent.class);

        UnifiedEvent event1;
        UnifiedEvent event2;

        event1 = new UnifiedEvent(UnifiedEventType.MENU_EVENT);
        event2 = new UnifiedEvent(UnifiedEventType.MENU_EVENT);
        assertTrue(event1.equals(event2));
        assertTrue(event2.equals(event1));

        event1 = new UnifiedEvent(clickEvent1);
        event2 = new UnifiedEvent(clickEvent1);
        assertTrue(event1.equals(event2));
        assertTrue(event2.equals(event1));

        try {
            event1 = new UnifiedEvent(UnifiedEventType.MENU_EVENT);
            event2 = null;
            event1.equals(event2);
            fail("Expected NullPointerException");
        } catch (NullPointerException e) { }

        try {
            event1 = new UnifiedEvent(UnifiedEventType.MENU_EVENT);
            event2 = null;
            event1.equals("blech");
            fail("Expected ClassCastException");
        } catch (ClassCastException e) { }

        event1 = new UnifiedEvent(UnifiedEventType.MENU_EVENT);
        event2 = new UnifiedEvent(UnifiedEventType.BUTTON_EVENT);
        assertFalse(event1.equals(event2));
        assertFalse(event2.equals(event1));

        event1 = new UnifiedEvent(clickEvent1);
        event2 = new UnifiedEvent(clickEvent2);
        assertFalse(event1.equals(event2));
        assertFalse(event2.equals(event1));
    }

    /** Test hashCode(). */
    @Test public void testHashCode() {
        ClickEvent clickEvent1 = mock(ClickEvent.class);
        ClickEvent clickEvent2 = mock(ClickEvent.class);

        UnifiedEvent event1 = new UnifiedEvent(UnifiedEventType.MENU_EVENT);
        UnifiedEvent event2 = new UnifiedEvent(UnifiedEventType.BUTTON_EVENT);
        UnifiedEvent event3 = new UnifiedEvent(clickEvent1);
        UnifiedEvent event4 = new UnifiedEvent(clickEvent2);
        UnifiedEvent event5 = new UnifiedEvent(UnifiedEventType.MENU_EVENT);  // same as event1

        Map<UnifiedEvent, String> map = new HashMap<UnifiedEvent, String>();
        map.put(event1, "ONE");
        map.put(event2, "TWO");
        map.put(event3, "THREE");
        map.put(event4, "FOUR");

        assertEquals("ONE", map.get(event1));
        assertEquals("TWO", map.get(event2));
        assertEquals("THREE", map.get(event3));
        assertEquals("FOUR", map.get(event4));
        assertEquals("ONE", map.get(event5));
    }

}
