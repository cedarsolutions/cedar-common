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

import com.cedarsolutions.client.gwt.event.UnifiedEvent;
import com.cedarsolutions.client.gwt.event.UnifiedEventType;
import com.google.gwt.user.client.History;

/**
 * Utility methods tied to module tabs.
 * @author Kenneth J. Pronovici <pronovic@ieee.org>
 */
public class ModuleTabUtils {

    /** Private constructor so it can't be instantiated. */
    private ModuleTabUtils() {
    }

    /** Initialize a tab view, if necessary. */
    public static void initializeTab(IModuleTabView view) {
        // We only want to mark the view as initialized if we actually ran an
        // initialization handler, otherwise attempts to work around ordering
        // issues (i.e. initialize something later when we know it got missed)
        // will fail because the view was "initialized" even before a handler
        // was set.  This is especially important on nested tabs, which seem to
        // get initialized before they're bound.

        if (!view.isInitialized()) {
            if (view.getInitializationEventHandler() != null) {
                UnifiedEvent init = new UnifiedEvent(UnifiedEventType.INIT_EVENT);
                view.getInitializationEventHandler().handleEvent(init);
                view.markInitialized();
            }
        }
    }

    /** Initialize all tabs associated with a module tab view, if necessary. */
    public static void initializeAllTabs(IModuleTabPanelView view) {
        for (int i = 0; i < view.getTabPanel().getWidgetCount(); i++) {
            IModuleTabView tab = (IModuleTabView) view.getTabPanel().getWidget(i);
            initializeTab(tab);
        }
    }

    /** Select a tab view, as if the normal BeforeSelectionHandler was invoked. */
    public static void selectTab(IModuleTabView view) {
        initializeTab(view);

        // The selected event handler is always called, even when initialize is also called.
        // That way, the client doesn't need any special logic for the "selected first time" case.
        if (view.getSelectedEventHandler() != null) {
            UnifiedEvent selected = new UnifiedEvent(UnifiedEventType.SELECTED_EVENT);
            view.getSelectedEventHandler().handleEvent(selected);
        }

        if (view.getHistoryToken() != null) {
            History.newItem(view.getHistoryToken(), false);
        }
    }

}
