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
package com.cedarsolutions.client.gwt.module.view;

import com.cedarsolutions.client.gwt.custom.tab.TabLayoutPanel;
import com.cedarsolutions.client.gwt.junit.ClientTestCase;
import com.cedarsolutions.client.gwt.module.view.ModuleTabPanelView.TabPanelResizeHandler;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Label;

/**
 * Client-side unit tests for ModuleTabPanelView.
 * @author Kenneth J. Pronovici <pronovic@ieee.org>
 */
public class ModuleTabPanelViewClientTest extends ClientTestCase {

    /** Test constructor. */
    public void testConstructor() {
        ConcreteModuleTabPanelView view = new ConcreteModuleTabPanelView();
        assertNotNull(view);
        assertNotNull(view.getTabPanel());
    }

    /** Test addTab(). */
    public void testAddTab() {
        ConcreteModuleTabPanelView view = new ConcreteModuleTabPanelView();
        view.setElementId("ConcreteModuleTabPanelView");

        ConcreteModuleTabView tab1 = new ConcreteModuleTabView();
        ConcreteModuleTabView tab2 = new ConcreteModuleTabView();

        view.addTab(tab1, "Title1");
        assertEquals(1, view.tabPanel.getWidgetCount());
        assertEquals(0, view.tabPanel.getSelectedIndex());
        assertSame(tab1, view.tabPanel.getWidget(0));
        assertEquals("Title1", view.tabPanel.getTitle(0));
        assertEquals("", view.tabPanel.getElementId(0));
        assertEquals(view.getTabPanel(), tab1.getParentPanel());

        view.addTab(tab2, "tab2", "Title2");
        assertEquals(2, view.tabPanel.getWidgetCount());
        assertEquals(0, view.tabPanel.getSelectedIndex());
        assertSame(tab1, view.tabPanel.getWidget(0));
        assertSame(tab2, view.tabPanel.getWidget(1));
        assertEquals("Title1", view.tabPanel.getTitle(0));
        assertEquals("Title2", view.tabPanel.getTitle(1));
        assertEquals("", view.tabPanel.getElementId(0));
        assertEquals("ConcreteModuleTabPanelView_tab2", view.tabPanel.getElementId(1));
        assertEquals(view.getTabPanel(), tab1.getParentPanel());
        assertEquals(view.getTabPanel(), tab2.getParentPanel());
    }

    /** Test selectTabForView(). */
    public void testSelectTabForView() {
        ConcreteModuleTabPanelView view = new ConcreteModuleTabPanelView();
        assertEquals(-1, view.tabPanel.getSelectedIndex());

        ConcreteModuleTabView tab1 = new ConcreteModuleTabView();
        ConcreteModuleTabView tab2 = new ConcreteModuleTabView();

        view.addTab(tab1, "Title1");
        assertEquals(0, view.tabPanel.getSelectedIndex());

        view.addTab(tab2, "Title2");
        assertEquals(0, view.tabPanel.getSelectedIndex());

        view.selectTabForView(tab2);
        assertEquals(1, view.tabPanel.getSelectedIndex());

        view.selectTabForView(tab1);
        assertEquals(0, view.tabPanel.getSelectedIndex());
    }

    /** Test replaceFirstTabWithView(). */
    public void testReplaceFirstTabWithView() {
        ConcreteModuleTabPanelView view = new ConcreteModuleTabPanelView();
        view.setElementId("ConcreteModuleTabPanelView");

        ConcreteModuleTabView tab1 = new ConcreteModuleTabView();
        ConcreteModuleTabView tab2 = new ConcreteModuleTabView();
        ConcreteModuleTabView tab3 = new ConcreteModuleTabView("historyToken"); // to hit case in selectTab()

        view.addTab(tab1, "Title1");
        assertEquals(1, view.tabPanel.getWidgetCount());
        assertEquals(0, view.tabPanel.getSelectedIndex());
        assertSame(tab1, view.tabPanel.getWidget(0));
        assertEquals("Title1", view.tabPanel.getTitle(0));
        assertEquals("", view.tabPanel.getElementId(0));

        view.replaceFirstTabWithView(tab2, "Title2");
        assertEquals(1, view.tabPanel.getWidgetCount());
        assertEquals(0, view.tabPanel.getSelectedIndex());
        assertSame(tab2, view.tabPanel.getWidget(0));
        assertEquals("Title2", view.tabPanel.getTitle(0));
        assertEquals("", view.tabPanel.getElementId(0));

        view.replaceFirstTabWithView(tab3, "tab3", "Title3");
        assertEquals(1, view.tabPanel.getWidgetCount());
        assertEquals(0, view.tabPanel.getSelectedIndex());
        assertSame(tab3, view.tabPanel.getWidget(0));
        assertEquals("Title3", view.tabPanel.getTitle(0));
        assertEquals("ConcreteModuleTabPanelView_tab3", view.tabPanel.getElementId(0));
    }

    /** Test configureFullScreen(). */
    public void testConfigureFullScreen() {
        ConcreteModuleTabPanelView view = new ConcreteModuleTabPanelView();
        assertFalse(view.isFullScreen());
        view.configureFullScreen();
        assertTrue(view.isFullScreen());
        assertNotNull(view.resizeHandler);
        assertEquals(ModuleTabPanelView.WIDTH_SCALING, view.resizeHandler.getWidthScaling());
        assertEquals(ModuleTabPanelView.HEIGHT_SCALING, view.resizeHandler.getHeightScaling());

        view = new ConcreteModuleTabPanelView();
        assertFalse(view.isFullScreen());
        view.configureFullScreen(30.0, 40.0);
        assertTrue(view.isFullScreen());
        assertNotNull(view.resizeHandler);
        assertEquals(30.0, view.resizeHandler.getWidthScaling());
        assertEquals(40.0, view.resizeHandler.getHeightScaling());
    }

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

        public void setElementId(String elementId) {
            this.tabPanel.setElementId(elementId);
        }
    }

    /** Concrete class that we can test with. */
    private static class ConcreteModuleTabView extends ModuleTabView {
        private String historyToken;

        public ConcreteModuleTabView() {
            this(null);
        }

        public ConcreteModuleTabView(String historyToken) {
            this.initWidget(new Label());  // just put anything in here, so the view renders
            this.historyToken = historyToken;
        }

        @Override
        public String getHistoryToken() {
            return this.historyToken;
        }
    }
}
