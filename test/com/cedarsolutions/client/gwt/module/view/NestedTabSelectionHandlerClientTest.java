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
import com.cedarsolutions.client.gwt.event.UnifiedEvent;
import com.cedarsolutions.client.gwt.event.UnifiedEventType;
import com.cedarsolutions.client.gwt.event.ViewEventHandler;
import com.cedarsolutions.client.gwt.junit.ClientTestCase;
import com.google.gwt.dom.client.Style.Unit;

/**
 * Client-side unit tests for NestedTabSelectionHandler.
 * @author Kenneth J. Pronovici <pronovic@ieee.org>
 */
public class NestedTabSelectionHandlerClientTest extends ClientTestCase {

    /** Test NestedTabSelectionHandler. */
    public void testNestedTabSelectionHandler() {
        Handler initHandler = new Handler();
        Handler selectedHandler = new Handler();
        Handler selectDefaultNestedTabHandler = new Handler();

        ConcreteNestedModulePanelTabView view = new ConcreteNestedModulePanelTabView();
        TabLayoutPanel parentPanel = new TabLayoutPanel(10, Unit.CM);
        ConcreteModuleTabPanelView parentView = new ConcreteModuleTabPanelView(parentPanel);
        view.setContext(parentView, 1);
        view.setInitializationEventHandler(initHandler);
        view.setSelectedEventHandler(selectedHandler);
        view.setSelectDefaultNestedTabHandler(selectDefaultNestedTabHandler);

        NestedTabSelectionHandler handler = new NestedTabSelectionHandler(view);

        view.selectedTabView = new ConcreteModuleTabView();
        handler.onBeforeSelection(2);
        assertFalse(view.isInitialized());
        assertEquals(0, initHandler.calls);
        assertEquals(0, selectedHandler.calls);
        assertEquals(0, selectDefaultNestedTabHandler.calls);

        view.selectedTabView = null;
        handler.onBeforeSelection(2);
        assertFalse(view.isInitialized());
        assertEquals(0, initHandler.calls);
        assertEquals(0, selectedHandler.calls);
        assertEquals(0, selectDefaultNestedTabHandler.calls);

        view.selectedTabView = new ConcreteModuleTabView();
        handler.onBeforeSelection(1);
        assertTrue(view.isInitialized());
        assertEquals(1, initHandler.calls);
        assertEquals(UnifiedEventType.INIT_EVENT, initHandler.lastType);
        assertEquals(1, selectedHandler.calls);
        assertEquals(UnifiedEventType.SELECTED_EVENT, selectedHandler.lastType);
        assertEquals(0, selectDefaultNestedTabHandler.calls);

        view.selectedTabView = null;
        handler.onBeforeSelection(1);
        assertTrue(view.isInitialized());
        assertEquals(1, initHandler.calls);
        assertEquals(UnifiedEventType.INIT_EVENT, initHandler.lastType);
        assertEquals(2, selectedHandler.calls);
        assertEquals(UnifiedEventType.SELECTED_EVENT, selectedHandler.lastType);
        assertEquals(1, selectDefaultNestedTabHandler.calls);

        view.selectedTabView = new ConcreteModuleTabView();
        handler.disable();
        handler.onBeforeSelection(1);
        assertTrue(view.isInitialized());
        assertEquals(1, initHandler.calls);
        assertEquals(UnifiedEventType.INIT_EVENT, initHandler.lastType);
        assertEquals(2, selectedHandler.calls);
        assertEquals(UnifiedEventType.SELECTED_EVENT, selectedHandler.lastType);
        assertEquals(1, selectDefaultNestedTabHandler.calls);

        view.selectedTabView = null;
        handler.disable();
        handler.onBeforeSelection(1);
        assertTrue(view.isInitialized());
        assertEquals(1, initHandler.calls);
        assertEquals(UnifiedEventType.INIT_EVENT, initHandler.lastType);
        assertEquals(2, selectedHandler.calls);
        assertEquals(UnifiedEventType.SELECTED_EVENT, selectedHandler.lastType);
        assertEquals(1, selectDefaultNestedTabHandler.calls);

        view.selectedTabView = new ConcreteModuleTabView();
        handler.enable();
        handler.onBeforeSelection(1);
        assertTrue(view.isInitialized());
        assertEquals(1, initHandler.calls);
        assertEquals(UnifiedEventType.INIT_EVENT, initHandler.lastType);
        assertEquals(3, selectedHandler.calls);
        assertEquals(UnifiedEventType.SELECTED_EVENT, selectedHandler.lastType);
        assertEquals(1, selectDefaultNestedTabHandler.calls);

        view.selectedTabView = null;
        handler.enable();
        handler.onBeforeSelection(1);
        assertTrue(view.isInitialized());
        assertEquals(1, initHandler.calls);
        assertEquals(UnifiedEventType.INIT_EVENT, initHandler.lastType);
        assertEquals(4, selectedHandler.calls);
        assertEquals(UnifiedEventType.SELECTED_EVENT, selectedHandler.lastType);
        assertEquals(2, selectDefaultNestedTabHandler.calls);
    }

    /** Concrete class that we can test with. */
    private static class ConcreteModuleTabView extends ModuleTabView {
    }

    /** Concrete class that we can test with. */
    private static class ConcreteNestedModulePanelTabView extends ModuleNestedTabPanelView {
        ConcreteNestedModulePanelTabView() {
        }

        @Override
        public TabLayoutPanel getTabPanel() {
            return null;
        }
    }

    /** Concrete class that we can test with. */
    private static class ConcreteModuleTabPanelView extends ModuleTabPanelView {
        private TabLayoutPanel tabPanel;

        ConcreteModuleTabPanelView(TabLayoutPanel tabPanel) {
            this.tabPanel = tabPanel;
        }

        @Override
        public TabLayoutPanel getTabPanel() {
            return this.tabPanel;
        }
    }

    /** Handler that we can use for testing. */
    private static class Handler implements ViewEventHandler {

        protected int calls = 0;
        protected UnifiedEventType lastType;

        @Override
        public void handleEvent(UnifiedEvent event) {
            this.calls += 1;
            this.lastType = event.getEventType();
        }

    }

}
