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

    /** Select a tab view, as if the normal BeforeSelectionHandler was invoked. */
    public static void selectTab(IModuleTabView view) {
        if (!view.isInitialized()) {
            if (view.getInitializationEventHandler() != null) {
                UnifiedEvent init = new UnifiedEvent(UnifiedEventType.INIT_EVENT);
                view.getInitializationEventHandler().handleEvent(init);
            }

            view.markInitialized();
        }

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
