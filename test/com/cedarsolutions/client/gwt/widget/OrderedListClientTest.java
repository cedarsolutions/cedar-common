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
package com.cedarsolutions.client.gwt.widget;

import com.cedarsolutions.client.gwt.junit.ClientTestCase;

/**
 * Client-side unit tests for OrderedList.
 * @author Kenneth J. Pronovici <pronovic@ieee.org>
 */
public class OrderedListClientTest extends ClientTestCase {

    /** Test the constructor(). */
    public void testConstructor() {
        OrderedList list = new OrderedList();
        assertEquals("ol", list.getElement().getTagName().toLowerCase());
        assertEquals(0, list.getElement().getChildCount());
    }

    /** Test add() for text data. */
    public void testAddText() {
        OrderedList list = new OrderedList();

        list.add("Hello");
        ListItem widget = (ListItem) list.getWidget(0);
        assertEquals("Hello", widget.getText());

        list.add("ken");
        widget = (ListItem) list.getWidget(0);
        assertEquals("Hello", widget.getText());
        widget = (ListItem) list.getWidget(1);
        assertEquals("ken", widget.getText());
    }

    /** Test add() for a ListItem. */
    public void testAddListItem() {
        OrderedList list = new OrderedList();

        ListItem listItem = new ListItem("Hello");
        list.add(listItem);
        ListItem widget = (ListItem) list.getWidget(0);
        assertEquals("Hello", widget.getText());

        listItem = new ListItem("ken");
        list.add(listItem);
        widget = (ListItem) list.getWidget(0);
        assertEquals("Hello", widget.getText());
        widget = (ListItem) list.getWidget(1);
        assertEquals("ken", widget.getText());
    }

    /** Test insert() for text data. */
    public void testInsertText() {
        OrderedList list = new OrderedList();

        list.insert("Hello", 0);
        ListItem widget = (ListItem) list.getWidget(0);
        assertEquals("Hello", widget.getText());

        list.insert("ken", 0);
        widget = (ListItem) list.getWidget(0);
        assertEquals("ken", widget.getText());
        widget = (ListItem) list.getWidget(1);
        assertEquals("Hello", widget.getText());

        list.insert("whatever", 1);
        widget = (ListItem) list.getWidget(0);
        assertEquals("ken", widget.getText());
        widget = (ListItem) list.getWidget(1);
        assertEquals("whatever", widget.getText());
        widget = (ListItem) list.getWidget(2);
        assertEquals("Hello", widget.getText());
    }

    /** Test insert() for a ListItem. */
    public void testInsertListItem() {
        OrderedList list = new OrderedList();

        ListItem listItem = new ListItem("Hello");
        list.insert(listItem, 0);
        ListItem widget = (ListItem) list.getWidget(0);
        assertEquals("Hello", widget.getText());

        listItem = new ListItem("ken");
        list.insert(listItem, 0);
        widget = (ListItem) list.getWidget(0);
        assertEquals("ken", widget.getText());
        widget = (ListItem) list.getWidget(1);
        assertEquals("Hello", widget.getText());

        listItem = new ListItem("whatever");
        list.insert(listItem, 1);
        widget = (ListItem) list.getWidget(0);
        assertEquals("ken", widget.getText());
        widget = (ListItem) list.getWidget(1);
        assertEquals("whatever", widget.getText());
        widget = (ListItem) list.getWidget(2);
        assertEquals("Hello", widget.getText());
    }

}
