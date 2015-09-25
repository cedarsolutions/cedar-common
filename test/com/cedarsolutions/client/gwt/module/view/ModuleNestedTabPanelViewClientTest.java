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
import com.cedarsolutions.client.gwt.module.view.ModuleNestedTabPanelView.ModuleNestedTabPanelInitializationHandler;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.user.client.ui.Label;

/**
 * Client-side unit tests for ModuleNestedTabPanelView.
 * @author Kenneth J. Pronovici <pronovic@ieee.org>
 */
public class ModuleNestedTabPanelViewClientTest extends ClientTestCase {

    /** Test constructor. */
    public void testConstructor() {
        ConcreteModuleNestedTabPanelView view = new ConcreteModuleNestedTabPanelView();
        assertNotNull(view);
        assertNotNull(view.getTabPanel());
        assertNull(view.getParentPanel());
        assertEquals(0, view.getTabIndex());
        assertFalse(view.isInitialized());
        assertTrue(view.getInitializationEventHandler() instanceof ModuleNestedTabPanelInitializationHandler);
        assertNull(view.getSelectedEventHandler());
    }

    /** Test addTab(). */
    public void testAddTab() {
        ConcreteModuleNestedTabPanelView view = new ConcreteModuleNestedTabPanelView();
        view.setElementId("ConcreteModuleNestedTabPanelView");

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
        assertEquals("ConcreteModuleNestedTabPanelView_tab2", view.tabPanel.getElementId(1));
        assertEquals(view.getTabPanel(), tab1.getParentPanel());
        assertEquals(view.getTabPanel(), tab2.getParentPanel());
    }

    /** Test selectTabForView(). */
    public void testSelectTabForView() {
        ConcreteModuleNestedTabPanelView view = new ConcreteModuleNestedTabPanelView();
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
        ConcreteModuleNestedTabPanelView view = new ConcreteModuleNestedTabPanelView();
        view.setElementId("ConcreteModuleNestedTabPanelView");

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
        assertEquals("ConcreteModuleNestedTabPanelView_tab3", view.tabPanel.getElementId(0));
    }

    /** Test configureFullScreen(). */
    public void testConfigureFullScreen() {
        ConcreteModuleNestedTabPanelView view = new ConcreteModuleNestedTabPanelView();
        assertFalse(view.isFullScreen());
        view.configureFullScreen();
        assertTrue(view.isFullScreen());
        assertNotNull(view.resizeHandler);
        assertEquals(0.9, view.resizeHandler.getWidthScaling());   // specialized for this class
        assertEquals(0.8, view.resizeHandler.getHeightScaling());  // specialized for this class

        view = new ConcreteModuleNestedTabPanelView();
        assertFalse(view.isFullScreen());
        view.configureFullScreen(30.0, 40.0);
        assertTrue(view.isFullScreen());
        assertNotNull(view.resizeHandler);
        assertEquals(30.0, view.resizeHandler.getWidthScaling());
        assertEquals(40.0, view.resizeHandler.getHeightScaling());
    }

    /** Test the getViewWidget() method (which is really on the IModulePageView interface). */
    public void testGetViewWidget() {
        IModulePageView view = new ConcreteModuleNestedTabPanelView();
        assertEquals(view, view.getViewWidget());
    }

    /** Test setContext(). */
    public void testSetContext() {
        ConcreteModuleNestedTabPanelView view = new ConcreteModuleNestedTabPanelView();
        TabLayoutPanel parentPanel = new TabLayoutPanel(10, Unit.CM);
        view.setContext(parentPanel, 1);
        assertSame(parentPanel, view.getParentPanel());
        assertEquals(1, view.getTabIndex());
        // unfortunately, can't verify that the selection handler was set properly
    }

    /** Concrete class that we can test with. */
    private static class ConcreteModuleNestedTabPanelView extends ModuleNestedTabPanelView {
        private TabLayoutPanel tabPanel = new TabLayoutPanel(45, Unit.PX);

        public ConcreteModuleNestedTabPanelView() {
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
