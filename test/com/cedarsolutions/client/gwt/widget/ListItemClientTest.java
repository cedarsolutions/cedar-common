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
package com.cedarsolutions.client.gwt.widget;

import com.cedarsolutions.client.gwt.junit.ClientTestCase;

/**
 * Client-side unit tests for ListItem.
 * @author Kenneth J. Pronovici <pronovic@ieee.org>
 */
public class ListItemClientTest extends ClientTestCase {

    /** Test the constructors. */
    public void testConstructors() {
        ListItem listItem = new ListItem();
        assertEquals("li", listItem.getElement().getTagName().toLowerCase());
        assertEquals("", listItem.getText());

        listItem = new ListItem("value");
        assertEquals("li", listItem.getElement().getTagName().toLowerCase());
        assertEquals("value", listItem.getText());

        listItem.setText("ken");
        assertEquals("li", listItem.getElement().getTagName().toLowerCase());
        assertEquals("ken", listItem.getText());
    }

    /** Test the setText() method. */
    public void testSetText() {
        ListItem listItem = new ListItem();

        listItem.setText("ken");
        assertEquals("ken", listItem.getText());

        listItem.setText(null);
        assertEquals("", listItem.getText());
    }

}
