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
import com.google.gwt.user.client.ui.HasText;

/**
 * List item (li) to be added to an ordered (ol) or unordered (ul) list.
 *
 * <p>
 * This is a simplistic list item that only accepts text values.  If and when
 * I need something more sophisticated -- and I know how to test it -- I will
 * enhance this class.
 * </p>
 *
 * @see <a href="http://groups.google.com/group/Google-Web-Toolkit/browse_thread/thread/9d73b6cac18652bd">Google Groups</a>
 * @author Kenneth J. Pronovici <pronovic@ieee.org>
 */
public class ListItem extends ComplexPanel implements HasText {

    /** Create an empty list item. */
    public ListItem() {
        this(null);
    }

    /** Create a list item with specified text. */
    public ListItem(String text) {
        this.setElement(DOM.createElement("li"));
        this.setText(text);
    }

    /** Set the text. */
    @Override
    public void setText(String text) {
        DOM.setInnerText(getElement(), text == null ? "" : text);
    }

    /** Get the text. */
    @Override
    public String getText() {
        return DOM.getInnerText(getElement());
    }

}
