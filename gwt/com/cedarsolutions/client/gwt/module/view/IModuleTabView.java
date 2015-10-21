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
public interface IModuleTabView extends IModulePageView {

    /**
     * Set the context that this tab exists in.
     * @param parentPanel Parent tab panel view
     * @param tabIndex    Index of this tab on the layout panel
     */
    void setContext(IModuleTabPanelView parentView, int tabIndex);

    /**
     * Set the context that this tab exists in.
     * @param parentPanel Parent tab layout panel
     * @param tabIndex    Index of this tab on the layout panel
     * @deprecated Use setContext(IModuleTabPanelView parentView, tabIndex) instead.
     */
    @Deprecated
    void setContext(TabLayoutPanel parentPanel, int tabIndex);

    /** Disable the tab, so it no longer handles selection events. */
    void disableTab();

    /** Enable the tab, so it begins to handle selection events. */
    void enableTab();

    /** Get the parent TabLayoutPanel. */
    TabLayoutPanel getParentPanel();

    /** Get the parent module tab panel view. */
    IModuleTabPanelView getParentView();

    /** Get the history token for this tab, or null for no history. */
    String getHistoryToken();

    /** Get the tab index. */
    int getTabIndex();

    /** Whether the tab has been initialized yet. */
    boolean isInitialized();

    /** Mark that the tab has been initialized. */
    void markInitialized();

    /** Select the tab, as if the normal BeforeSelectionHandler was invoked. */
    void selectTab();

    /** Get the initialization event handler. */
    ViewEventHandler getInitializationEventHandler();

    /** Set the initialization event handler. */
    void setInitializationEventHandler(ViewEventHandler initializationEventHandler);

    /** Get the selected event handler. */
    ViewEventHandler getSelectedEventHandler();

    /** Set the selected event handler. */
    void setSelectedEventHandler(ViewEventHandler selectedEventHandler);

}
