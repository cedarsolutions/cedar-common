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

import com.cedarsolutions.web.metadata.NativeEventType;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;

/**
 * A simple handler that accepts change events, like for dropdowns.
 * @param <P> Parent presenter or view associated with handler
 * @author Kenneth J. Pronovici <pronovic@ieee.org>
 */
public abstract class AbstractChangeActionHandler<P> extends AbstractEventHandler<P> implements ChangeHandler {

    /** Create a change handler in terms of a parent. */
    public AbstractChangeActionHandler(P parent) {
        super(parent);
    }

    /** Handle the event. */
    protected abstract void handleEvent();

    /** Handle the on-change trigger. */
    @Override
    public void onChange(ChangeEvent event) {
        this.handleChangeEvent(event.getNativeEvent().getType());
    }

    /** Handle a change event. */
    protected void handleChangeEvent(String event) {
        if (NativeEventType.CHANGE.equals(NativeEventType.convert(event))) {
            this.handleEvent();
        }
    }

}
