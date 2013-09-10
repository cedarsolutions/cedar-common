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
package com.cedarsolutions.client.gwt.handler;

import com.cedarsolutions.client.gwt.event.UnifiedEvent;
import com.cedarsolutions.client.gwt.event.ViewEventHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;

/**
 * Abstract event handler that delegates click events to a view event handler.
 * @param <P> Parent presenter or view associated with handler
 * @author Kenneth J. Pronovici <pronovic@ieee.org>
 */
public abstract class AbstractClickHandler<P> extends AbstractEventHandler<P> implements ClickHandler {

    /** Constructor. */
    protected AbstractClickHandler(P parent) {
        super(parent);
    }

    /** Handle a click event. */
    @Override
    public void onClick(ClickEvent event) {
        if (this.getViewEventHandler() != null) {
            UnifiedEvent click = new UnifiedEvent(event);
            this.getViewEventHandler().handleEvent(click);
        }
    }

    /** Get the view event handler for the event. */
    public abstract ViewEventHandler getViewEventHandler();

}
