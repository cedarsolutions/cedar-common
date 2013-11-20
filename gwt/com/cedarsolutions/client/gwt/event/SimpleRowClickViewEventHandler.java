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

import static com.cedarsolutions.client.gwt.event.UnifiedEventType.CLICK_EVENT;
import static com.cedarsolutions.web.metadata.NativeEventType.CLICK;

import com.cedarsolutions.client.gwt.handler.AbstractEventHandler;
import com.cedarsolutions.web.metadata.NativeEventType;
import com.google.gwt.view.client.CellPreviewEvent;

/**
 * A simple click handler that deals with row click events by calling a view event handler.
 * @param <P> Parent presenter or view associated with handler
 * @param <T> Type of the expected row
 * @author Kenneth J. Pronovici <pronovic@ieee.org>
 */
public abstract class SimpleRowClickViewEventHandler<P, T> extends AbstractEventHandler<P> implements CellPreviewEvent.Handler<T> {

    /** Create a click handler in terms of a parent. */
    public SimpleRowClickViewEventHandler(P parent) {
        super(parent);
    }

    /** Handle the CellPreview event. */
    @Override
    public void onCellPreview(CellPreviewEvent<T> event) {
        this.handleSelectedRow(event.getNativeEvent().getType(), event.getColumn(), event.getValue());
    }

    /**
     * Get the zero-based selection column number, if any.
     * Child classes should override this to define which column is their selection column.
     * Return null if there is no selection column.
     */
    protected Integer getSelectionColumn() {
        return null;
    }

    /** Get the correct handler from the parent. */
    protected abstract ViewEventHandlerWithContext<T> getViewEventHandler();

    /** Handle the selected row, if it's a click event for something other than the selection column. */
    protected void handleSelectedRow(String event, int column, T row) {
        if (row != null) {
            if (this.getViewEventHandler() != null) {
                if (CLICK.equals(NativeEventType.convert(event))) {
                    if (this.getSelectionColumn() == null) {
                        UnifiedEventWithContext<T> context = new UnifiedEventWithContext<T>(CLICK_EVENT, row);
                        this.getViewEventHandler().handleEvent(context);
                    } else {
                        if (column != this.getSelectionColumn()) {
                            UnifiedEventWithContext<T> context = new UnifiedEventWithContext<T>(CLICK_EVENT, row);
                            this.getViewEventHandler().handleEvent(context);
                        }
                    }
                }
            }
        }
    }

}
