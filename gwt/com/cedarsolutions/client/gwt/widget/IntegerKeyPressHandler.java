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

import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyPressEvent;
import com.google.gwt.event.dom.client.KeyPressHandler;
import com.google.gwt.user.client.ui.ValueBox;

/**
 * Key press handler that accepts only legal integer characters.
 * @see <a href="http://stackoverflow.com/questions/2865647/solution-for-numeric-text-field-in-gwt">StackOverflow</a>
 * @see <a href="http://stackoverflow.com/questions/8036561/allow-only-numbers-in-textbox-in-gwt">StackOverflow</a>
 * @author Kenneth J. Pronovici <pronovic@ieee.org>, after a Stack Overflow example
 */
public class IntegerKeyPressHandler implements KeyPressHandler {

    @Override
    @SuppressWarnings("rawtypes")
    public void onKeyPress(KeyPressEvent event) {
        switch (event.getNativeEvent().getKeyCode()) {
        case KeyCodes.KEY_TAB:
        case KeyCodes.KEY_BACKSPACE:
        case KeyCodes.KEY_DELETE:
        case KeyCodes.KEY_LEFT:
        case KeyCodes.KEY_RIGHT:
        case KeyCodes.KEY_UP:
        case KeyCodes.KEY_DOWN:
        case KeyCodes.KEY_END:
        case KeyCodes.KEY_ENTER:
        case KeyCodes.KEY_ESCAPE:
        case KeyCodes.KEY_PAGEDOWN:
        case KeyCodes.KEY_PAGEUP:
        case KeyCodes.KEY_HOME:
        case KeyCodes.KEY_SHIFT:
        case KeyCodes.KEY_ALT:
        case KeyCodes.KEY_CTRL:
            break;
        default:
            if (event.isAltKeyDown() ||
                    (event.isControlKeyDown() &&
                            (event.getCharCode() != 'v' && event.getCharCode() != 'V'))) {
                break;
            }
            if (!Character.isDigit(event.getCharCode())) {
                ((ValueBox) event.getSource()).cancelKey();
            }
        }
    }

}
