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

/**
 * Specialized page that holds a set of tabs on a tab layout panel.
 * @author Kenneth J. Pronovici <pronovic@ieee.org>
 */
public interface IModuleTabPanelView extends IModulePageView {

    /** Set the tab that is currently selected on the panel. */
    void setSelectedTabView(IModuleTabView selectedTabView);

    /** Get the tab that is currently selected on the panel. */
    IModuleTabView getSelectedTabView();

    /** Get the history token for the nested view that is currently selected. */
    String getSelectedHistoryToken();

    /** Get the underlying tab panel. */
    TabLayoutPanel getTabPanel();

    /**
     * Select the tab that displays the passed-in view.
     * @param view  View whose tab to select
     */
    void selectTabForView(IModuleTabView view);

    /**
     * Select the tab that displays the passed-in view.
     * @param view       View whose tab to select
     * @param fireEvents Whether to fire events, like those that result in history tokens
     */
    void selectTabForView(IModuleTabView view, boolean fireEvents);

}
