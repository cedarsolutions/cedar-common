/* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 *
 *              C E D A R
 *          S O L U T I O N S       "Software done right."
 *           S O F T W A R E
 *
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 *
 * Copyright (c) 2012 Kenneth J. Pronovici.
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

import com.cedarsolutions.client.gwt.event.UnifiedEventWithContext;
import com.cedarsolutions.client.gwt.event.ViewEventHandlerWithContext;

/**
 * Abstract view event handler with an associated parent.
 * @param <P> Parent presenter or view associated with handler
 * @param <T> Type of the event context
 * @author Kenneth J. Pronovici <pronovic@ieee.org>
 */
public abstract class AbstractViewEventHandlerWithContext<P, T> extends AbstractEventHandler<P> implements ViewEventHandlerWithContext<T> {

    /** Constructor. */
    protected AbstractViewEventHandlerWithContext(P parent) {
        super(parent);
    }

    /** Handle a unified view event, including context information. */
    @Override
    public abstract void handleEvent(UnifiedEventWithContext<T> event);

}
