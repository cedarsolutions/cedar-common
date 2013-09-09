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

import com.google.gwt.user.client.ui.LongBox;

/**
 * Text box that limits its input to long integers only.
 *
 * <p>
 * GWT does provide <code>LongBox</code> and <code>IntegerBox</code>, but those
 * widgets don't do quite what I want.  They do at least give back values of
 * the proper type.  However, error-handling is a pain: if an invalid value is
 * entered, you either get back null from <code>getValue()</code>, or you have
 * to catch an exception from <code>getValueOrThrow()</code>.  I'd prefer
 * something that's a little simpler from the perspective of the view.  This
 * subclass restricts input so users can't enter values that aren't valid
 * integers.  That way, all validation can happen on the back-end.
 * <p>
 *
 * @see <a href="http://stackoverflow.com/questions/2865647/solution-for-numeric-text-field-in-gwt">StackOverflow</a>
 * @see <a href="http://stackoverflow.com/questions/8036561/allow-only-numbers-in-textbox-in-gwt">StackOverflow</a>
 * @author Kenneth J. Pronovici <pronovic@ieee.org>
 */
public class LongTextBox extends LongBox {

    /** Create an integer box with a specific keypress handler. */
    public LongTextBox() {
        this.addKeyPressHandler(new IntegerKeyPressHandler());
    }

}
