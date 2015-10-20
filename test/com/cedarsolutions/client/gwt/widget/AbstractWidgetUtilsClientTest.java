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
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;

/**
 * Unit tests for AbstractWidgetUtils.
 * @author Kenneth J. Pronovici <pronovic@ieee.org>
 */
public class AbstractWidgetUtilsClientTest extends ClientTestCase {

    /** Test getWndLocationHref(). */
    public void testGetWndLocationHref() {
        // Just make sure it doesn't blow up and we get something back
        assertNotNull(ConcreteUtils.getInstance().getWndLocationHref());
    }

    // This test seems to cause strange problems with other tests; not sure exactly why
//    /** Test redirect(). */
//    public void testRedirect() {
//        // Redirect back to the current page, just to make sure the method doesn't blow up
//        WidgetUtils.getInstance().redirect(WidgetUtils.getInstance().getWndLocationHref());
//    }

    /** Test getDestinationUrl() and getDestinationToken. */
    public void testGetDestinationUrlAndToken() {
        // Just make sure it looks semi-sensible
        String result = ConcreteUtils.getInstance().getDestinationUrl("token");
        assertTrue(result.endsWith("#token"));
        assertEquals("token", ConcreteUtils.getInstance().getDestinationToken(result));
    }

    /** Test getWidget(). */
    public void testGetWidget() {
        Label label = new Label("A");

        TextBox textBox = new TextBox();
        textBox.setValue("B");

        Grid grid1 = new Grid(1, 1);
        grid1.setWidget(0, 0, label);

        Grid grid2 = new Grid(1, 1);
        grid2.setWidget(0, 0, textBox);

        VerticalPanel panel = new VerticalPanel();
        panel.add(grid1);
        panel.add(grid2);

        assertEquals(grid1, (Grid) ConcreteUtils.getInstance().getWidget(panel, 0));
        assertEquals(grid2, (Grid) ConcreteUtils.getInstance().getWidget(panel, 1));
    }

    /** Concrete test class, following the intended pattern where the application creates a singleton. */
    public static class ConcreteUtils extends AbstractWidgetUtils {
        private static ConcreteUtils INSTANCE;

        private ConcreteUtils() {
        }

        public static synchronized ConcreteUtils getInstance() {
            if (INSTANCE == null) {
                INSTANCE = GWT.create(ConcreteUtils.class);
            }

            return INSTANCE;
        }
    }
}
