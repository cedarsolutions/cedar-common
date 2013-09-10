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
package com.cedarsolutions.client.gwt.event;

import static com.cedarsolutions.client.gwt.event.UnifiedEventType.MENU_EVENT;

import com.google.gwt.core.client.Scheduler.ScheduledCommand;
import com.google.gwt.user.client.Command;

/**
 * Unified menu event handler that proxies to a ViewEventHandler.
 * @author Kenneth J. Pronovici <pronovic@ieee.org>
 */
public class UnifiedMenuHandler implements Command, ScheduledCommand {

    /** View event handler. */
    private ViewEventHandler viewEventHandler;

    /** Constructor in terms of ViewEventHandler. */
    public UnifiedMenuHandler(ViewEventHandler viewEventHandler) {
        this.viewEventHandler = viewEventHandler;
    }

    /** Called when a menu item is selected. */
    @Override
    public void execute() {
        UnifiedEvent event = new UnifiedEvent(MENU_EVENT);
        this.viewEventHandler.handleEvent(event);
    }

}
