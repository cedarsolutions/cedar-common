/* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 *
 *              C E D A R
 *          S O L U T I O N S       "Software done right."
 *           S O F T W A R E
 *
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 *
 * Copyright (c) 2013-2014,2016 Kenneth J. Pronovici.
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

import com.cedarsolutions.client.gwt.junit.ClientTestCase;

/**
 * Client-side unit tests for AbstractDropdownList.
 * @author Kenneth J. Pronovici <pronovic@ieee.org>
 */
public class AbstractDropdownListClientTest extends ClientTestCase {

    /** Test the constructor for IntegerList. */
    public void testConstructorInteger() {
        IntegerList list = new IntegerList();
        assertNotNull(list);
        assertEquals(2, list.getItemCount());

        assertEquals("1", list.getValue(0));
        assertEquals("1", list.getItemText(0));
        assertEquals("2", list.getValue(1));
        assertEquals("2", list.getItemText(1));

        assertFalse(list.isDropdownKeyKnown(null));
        assertFalse(list.isDropdownKeyKnown(""));
        assertFalse(list.isDropdownItemKnown(null));
        assertTrue(list.isDropdownKeyKnown("1"));
        assertTrue(list.isDropdownItemKnown(1));
        assertTrue(list.isDropdownKeyKnown("2"));
        assertTrue(list.isDropdownItemKnown(2));
    }

    /** Test the constructor for StringList. */
    public void testConstructorString() {
        StringList list = new StringList();
        assertNotNull(list);
        assertEquals(2, list.getItemCount());

        assertEquals("1", list.getValue(0));
        assertEquals("1", list.getItemText(0));
        assertEquals("2", list.getValue(1));
        assertEquals("2", list.getItemText(1));

        assertFalse(list.isDropdownKeyKnown(null));
        assertFalse(list.isDropdownKeyKnown(""));
        assertFalse(list.isDropdownItemKnown(null));
        assertTrue(list.isDropdownKeyKnown("1"));
        assertTrue(list.isDropdownItemKnown("1"));
        assertTrue(list.isDropdownKeyKnown("2"));
        assertTrue(list.isDropdownItemKnown("2"));
    }

    /** Test the constructor for ComplicatedList. */
    public void testConstructorComplicated() {
        ComplicatedList list = new ComplicatedList();
        assertNotNull(list);
        assertEquals(3, list.getItemCount());

        assertEquals("one", list.getValue(0));
        assertEquals("ONE", list.getItemText(0));
        assertEquals("two", list.getValue(1));
        assertEquals("TWO", list.getItemText(1));
        assertEquals("", list.getValue(2));
        assertEquals("any", list.getItemText(2));

        assertFalse(list.isDropdownKeyKnown(null));
        assertTrue(list.isDropdownKeyKnown(""));
        assertTrue(list.isDropdownItemKnown(null));
        assertTrue(list.isDropdownKeyKnown("one"));
        assertTrue(list.isDropdownItemKnown(1));
        assertTrue(list.isDropdownKeyKnown("two"));
        assertTrue(list.isDropdownItemKnown(2));
    }

    /** Check that every value can be selected, for SimpleList. */
    public void testSetSelectedValueSimple() {
        // Note: the behavior when setting null changed with GWT 2.5.0-rc1.  In
        // prior versions, selecting null would return null even if there was
        // no null ("any") option in the list.  As of 2.5.0-rc1, you get back
        // the first item in the list instead.

        IntegerList list = new IntegerList();

        list.setSelectedObjectValue(1);
        assertEquals(new Integer(1), list.getSelectedObjectValue());

        list.setSelectedObjectValue(null);
        assertEquals(new Integer(1), list.getSelectedObjectValue());

        list.setSelectedObjectValue(2);
        assertEquals(new Integer(2), list.getSelectedObjectValue());

        list.setSelectedObjectValue(null);
        assertEquals(new Integer(1), list.getSelectedObjectValue());
    }

    /** Check that every value can be selected, for ComplicatedList. */
    public void testSetSelectedValueComplicated() {
        ComplicatedList list = new ComplicatedList();

        list.setSelectedObjectValue(null);
        assertEquals(null, list.getSelectedObjectValue());

        list.setSelectedObjectValue(1);
        assertEquals(new Integer(1), list.getSelectedObjectValue());

        list.setSelectedObjectValue(2);
        assertEquals(new Integer(2), list.getSelectedObjectValue());
    }

    /** Test removeDropdownItem(). */
    public void testRemoveDropdownItem() {
        IntegerList list = new IntegerList();
        list.removeDropdownItem(1);
        assertEquals(1, list.getItemCount());
        assertEquals("2", list.getValue(0));
        assertEquals("2", list.getItemText(0));
    }

    /** Integer-based list for testing. */
    private static class IntegerList extends AbstractDropdownList<Integer> {
        IntegerList() {
            this.addDropdownItem(1);
            this.addDropdownItem(2);
        }

        @Override
        protected String getAnyDisplay() {
            return "any";
        }
    }

    /** String-based list for testing. */
    private static class StringList extends AbstractDropdownList<String> {
        StringList() {
            this.addDropdownItem("1");
            this.addDropdownItem("2");
        }

        @Override
        protected String getAnyDisplay() {
            return "any";
        }
    }

    /** List for testing. */
    private static class ComplicatedList extends IntegerList {

        ComplicatedList() {
            super();
            this.addDropdownItemAny();
        }

        @Override
        protected String getKey(Integer value) {
            if (value == 1) {
                return "one";
            } else if (value == 2) {
                return "two";
            } else {
                return String.valueOf(value);
            }
        }

        @Override
        protected String getDisplay(Integer value) {
            if (value == 1) {
                return "ONE";
            } else if (value == 2) {
                return "TWO";
            } else {
                return String.valueOf(value);
            }
        }

    }
}
