/* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 *
 *              C E D A R
 *          S O L U T I O N S       "Software done right."
 *           S O F T W A R E
 *
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 *
 * Copyright (c) 2013,2015 Kenneth J. Pronovici.
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
import com.cedarsolutions.client.gwt.event.ViewEventHandler;

/**
 * Specialized page that is intended to be a tab.
 * @author Kenneth J. Pronovici <pronovic@ieee.org>
 */
public abstract class ModuleTabView extends ModulePageView implements IModuleTabView {

    /** Parent tab layout panel. */
    private TabLayoutPanel parentPanel;

    /** Index of this tab on the layout panel. */
    private int tabIndex;

    /** Whether the tab has been initialized. */
    private boolean initialized;

    /** Event handler for the initialization event (if any). */
    private ViewEventHandler initializationEventHandler;

    /** Event handler for the selected event (if any). */
    private ViewEventHandler selectedEventHandler;

    /** Save off the "before selection" handler, so we can manipulate it. */
    private TabSelectionHandler beforeSelectionHandler;

    /**
     * Set the context that this tab exists in.
     * @param parentPanel Parent tab layout panel
     * @param tabIndex    Index of this tab on the layout panel
     */
    @Override
    public void setContext(TabLayoutPanel parentPanel, int tabIndex) {
        this.parentPanel = parentPanel;
        this.tabIndex = tabIndex;
        this.beforeSelectionHandler = new TabSelectionHandler(this);
        this.parentPanel.addBeforeSelectionHandler(this.beforeSelectionHandler);
    }

    /** Disable the tab, so it no longer handles selection events. */
    @Override
    public void disableTab() {
        this.beforeSelectionHandler.disable();
    }

    /** Enable the tab, so it begins to handle selection events. */
    @Override
    public void enableTab() {
        this.beforeSelectionHandler.enable();
    }

    /** Get the parent TabLayoutPanel. */
    @Override
    public TabLayoutPanel getParentPanel() {
        return this.parentPanel;
    }

    /** Get the history token for this tab, or null for no history. */
    @Override
    public String getHistoryToken() {
        return null;
    }

    /** Get the tab index. */
    @Override
    public int getTabIndex() {
        return this.tabIndex;
    }

    /** Whether the tab has been initialized yet. */
    @Override
    public boolean isInitialized() {
        return this.initialized;
    }

    /** Mark that the tab has been initialized. */
    @Override
    public void markInitialized() {
        this.initialized = true;
    }

    /** Get the initialization event handler. */
    @Override
    public ViewEventHandler getInitializationEventHandler() {
        return this.initializationEventHandler;
    }

    /** Set the initialization event handler. */
    @Override
    public void setInitializationEventHandler(ViewEventHandler initializationEventHandler) {
        this.initializationEventHandler = initializationEventHandler;
    }

    /** Get the selected event handler. */
    @Override
    public ViewEventHandler getSelectedEventHandler() {
        return this.selectedEventHandler;
    }

    /** Set the selected event handler. */
    @Override
    public void setSelectedEventHandler(ViewEventHandler selectedEventHandler) {
        this.selectedEventHandler = selectedEventHandler;
    }

    /** Select the tab, as if the normal BeforeSelectionHandler was invoked. */
    @Override
    public void selectTab() {
        ModuleTabUtils.selectTab(this);
    }

    /**
     * Tab selection handler.
     * @deprecated Use com.cedarsolutions.client.gwt.module.view.TabSelectionHandler instead.
     */
    @Deprecated
    protected static class SelectionHandler extends com.cedarsolutions.client.gwt.module.view.TabSelectionHandler {
        public SelectionHandler(IModuleTabView view) {
            super(view);
        }
    }

}
