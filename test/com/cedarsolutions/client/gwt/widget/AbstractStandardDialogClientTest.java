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
 * Client-side unit tests for AbstractStandardDialog.
 * @author Kenneth J. Pronovici <pronovic@ieee.org>
 */
public class AbstractStandardDialogClientTest extends ClientTestCase {

    /** Test constructor. */
    public void testConstructor() {
        ConcreteDialog dialog = new ConcreteDialog();
        assertFalse(dialog.isAnimationEnabled());
        assertTrue(dialog.isGlassEnabled());
        assertEquals("whatever", dialog.getStylePrimaryName());
    }

    /** Test show(). */
    public void testShow() {
        ConcreteDialog dialog = new ConcreteDialog();
        dialog.show();  // make sure it doesn't blow up, and look for moderately sensible results
        assertTrue(dialog.getPopupLeft() >= 0);
        assertTrue(dialog.getPopupTop() >= 0);
    }

    /** Concrete dialog to test with. */
    private static class ConcreteDialog extends AbstractStandardDialog {
        public ConcreteDialog() {
            super("whatever");
        }
    }

}
