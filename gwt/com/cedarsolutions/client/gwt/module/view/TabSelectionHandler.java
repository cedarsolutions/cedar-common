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

import com.google.gwt.event.logical.shared.BeforeSelectionEvent;
import com.google.gwt.event.logical.shared.BeforeSelectionHandler;

/**
 * Tab selection handler.
 * @author Kenneth J. Pronovici <pronovic@ieee.org>
 */
public class TabSelectionHandler implements BeforeSelectionHandler<Integer> {

    private boolean enabled;
    private IModuleTabView view;

    public TabSelectionHandler(IModuleTabView view) {
        this.enabled = true;
        this.view = view;
    }

    public void enable() {
        this.enabled = true;
    }

    public void disable() {
        this.enabled = false;
    }

    @Override
    public void onBeforeSelection(BeforeSelectionEvent<Integer> event) {
        onBeforeSelection(event.getItem());
    }

    protected void onBeforeSelection(int tabIndex) {
        if (this.enabled) {
            if (tabIndex == this.view.getTabIndex()) {
                this.view.selectTab();
            }
        }
    }

    protected boolean isEnabled() {
        return this.enabled;
    }

}
