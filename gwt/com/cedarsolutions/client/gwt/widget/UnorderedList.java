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

import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.ui.ComplexPanel;

/**
 * UI widget for an unordered list (ul).
 * @see <a href="http://groups.google.com/group/Google-Web-Toolkit/browse_thread/thread/9d73b6cac18652bd">Google Groups</a>
 * @author Kenneth J. Pronovici <pronovic@ieee.org>
 */
public class UnorderedList extends ComplexPanel {

    /** Create an unordered list. */
    public UnorderedList() {
        setElement(DOM.createElement("ul"));
    }

    /** Append a text item onto the list. */
    public void add(String text) {
        ListItem listItem = new ListItem(text);
        this.add(listItem);
    }

    /** Append a list item onto the list. */
    public void add(ListItem listItem) {
        super.add(listItem, getElement());
    }

    /** Insert a text item at the specified position. */
    public void insert(String text, int beforeIndex) {
        ListItem listItem = new ListItem(text);
        this.insert(listItem, beforeIndex);
    }

    /** Insert a list item at the specified position. */
    public void insert(ListItem listItem, int beforeIndex) {
        super.insert(listItem, getElement(), beforeIndex, true);
    }

}
