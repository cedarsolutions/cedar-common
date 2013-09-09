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

import com.cedarsolutions.client.gwt.event.UnifiedEvent;
import com.cedarsolutions.client.gwt.event.UnifiedEventType;
import com.cedarsolutions.client.gwt.event.ViewEventHandler;
import com.google.gwt.user.cellview.client.ColumnSortEvent;

/**
 * Abstract event handler that delegates column sort events to a view event handler.
 * @param <P> Parent presenter or view associated with handler
 * @author Kenneth J. Pronovici <pronovic@ieee.org>
 */
public abstract class AbstractColumnSortEventHandler<P> extends AbstractEventHandler<P> implements ColumnSortEvent.Handler {

    /** Constructor. */
    protected AbstractColumnSortEventHandler(P parent) {
        super(parent);
    }

    /** Handle a column sort event. */
    @Override
    public void onColumnSort(ColumnSortEvent event) {
        if (this.getViewEventHandler() != null) {
            UnifiedEvent sort = new UnifiedEvent(UnifiedEventType.SORT_EVENT);
            this.getViewEventHandler().handleEvent(sort);
        }
    }

    /** Get the view event handler for the event. */
    public abstract ViewEventHandler getViewEventHandler();

}
