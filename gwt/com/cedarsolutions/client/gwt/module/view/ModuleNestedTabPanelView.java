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
import com.cedarsolutions.client.gwt.event.ViewEventHandler;
import com.cedarsolutions.client.gwt.handler.AbstractViewEventHandler;

/**
 * A ModuleTabPanelView that is also itself a ModuleTabView.
 *
 * <p>
 * This implementation allows one level of nesting, where a top-level tab
 * contains a set of other tabs.  It's not entirely obvious how to generalize
 * it further than that.  However, even if I could, I'm skeptical that 3
 * levels of tabs will really work that well anyway.  You'd start to lose
 * a lot of screen real estate.
 * </p>
 *
 * <p>
 * The associated presenter must be a ModuleNestedTabPresenter, otherwise
 * rendering will not always work properly.
 * </p>
 *
 * <p>
 * Child classes must make sure to call the superclass constructor, so the
 * proper initialization handler gets set, otherwise child tabs will not
 * be initialized properly.
 * <p>
 *
 * @author Kenneth J. Pronovici <pronovic@ieee.org>
 */
public abstract class ModuleNestedTabPanelView extends ModuleTabPanelView implements IModuleTabView {

    /** Parent tab panel view. */
    private IModuleTabPanelView parentView;

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

    /** Default constructor. */
    protected ModuleNestedTabPanelView() {
        this.setInitializationEventHandler(new ModuleNestedTabPanelInitializationHandler(this));
    }

    /**
     * Set the context that this tab exists in.
     * @param parentPanel Parent tab panel view
     * @param tabIndex    Index of this tab on the layout panel
     */
    @Override
    public void setContext(IModuleTabPanelView parentView, int tabIndex) {
        this.parentView = parentView;
        this.setContext(parentView.getTabPanel(), tabIndex);
    }

    /**
     * Set the context that this tab exists in.
     * @param parentPanel Parent tab layout panel
     * @param tabIndex    Index of this tab on the layout panel
     * @deprecated Use setContext(IModuleTabPanelView parentView, tabIndex) instead.
     */
    @Override
    @Deprecated
    public void setContext(TabLayoutPanel parentPanel, int tabIndex) {
        this.parentPanel = parentPanel;
        this.tabIndex = tabIndex;
        this.beforeSelectionHandler = new TabSelectionHandler(this);
        this.parentPanel.addBeforeSelectionHandler(this.beforeSelectionHandler);
    }

    /** Configure the tab panel so it takes up the full screen, using default scaling. */
    @Override
    protected void configureFullScreen() {
        this.configureFullScreen(0.9, 0.8);  // nested tabs need to be a little smaller than normal
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

    /** Get the parent tab panel view. */
    @Override
    public IModuleTabPanelView getParentView() {
        return this.parentView;
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

    /** Initialization handler for nested tab panels. */
    public static class ModuleNestedTabPanelInitializationHandler extends AbstractViewEventHandler<ModuleNestedTabPanelView> {
        public ModuleNestedTabPanelInitializationHandler(ModuleNestedTabPanelView parent) {
            super(parent);
        }

        @Override
        public void handleEvent(UnifiedEvent event) {
            ModuleTabUtils.initializeAllTabs(this.getParent());
        }
    }

}
