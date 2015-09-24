/* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 *
 *              C E D A R
 *          S O L U T I O N S       "Software done right."
 *           S O F T W A R E
 *
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 *
 * Copyright (c) 2015 Kenneth J. Pronovici.
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
package com.cedarsolutions.client.gwt.module.view;

import com.cedarsolutions.client.gwt.custom.tab.TabLayoutPanel;
import com.cedarsolutions.client.gwt.junit.ClientTestCase;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.user.client.Window;

/**
 * Client-side unit tests for TabPanelResizeHandler.
 * @author Kenneth J. Pronovici <pronovic@ieee.org>
 */
public class TabPanelResizeHandlerClientTest extends ClientTestCase {

    /** Test TabPanelResizeHandler. */
    public void testTabPanelResizeHandler() {
        int width = Window.getClientWidth();
        int height = Window.getClientHeight();
        int scaledWidth = (int) (0.10 * (double) width);
        int scaledHeight = (int) (0.20 * (double) height);

        ConcreteModuleTabPanelView view = new ConcreteModuleTabPanelView();
        TabPanelResizeHandler handler = new TabPanelResizeHandler(view, 10.0, 20.0);
        assertNotNull(handler);
        assertSame(view, handler.getView());
        assertEquals(10.0, handler.getWidthScaling());
        assertEquals(20.0, handler.getHeightScaling());

        assertTrue(view.getOffsetWidth() >= scaledWidth);   // can't get back the "real" width
        assertTrue(view.getOffsetHeight() >= scaledHeight); // can't get back the "real" height
    }

    /** Concrete class that we can test with. */
    private static class ConcreteModuleTabPanelView extends ModuleTabPanelView {
        private TabLayoutPanel tabPanel = new TabLayoutPanel(45, Unit.PX);

        public ConcreteModuleTabPanelView() {
            this.initWidget(this.tabPanel);
        }

        @Override
        public TabLayoutPanel getTabPanel() {
            return this.tabPanel;
        }
    }

}
