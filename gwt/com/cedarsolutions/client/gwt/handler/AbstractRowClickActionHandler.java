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

import static com.cedarsolutions.web.metadata.NativeEventType.CLICK;

import com.cedarsolutions.web.metadata.NativeEventType;
import com.google.gwt.view.client.CellPreviewEvent;

/**
 * A simple click handler that deals with row click events by invoking a simple action.
 * @param <P> Parent presenter or view associated with handler
 * @param <T> Type of the expected row
 * @author Kenneth J. Pronovici <pronovic@ieee.org>
 */
public abstract class AbstractRowClickActionHandler<P, T> extends AbstractEventHandler<P> implements CellPreviewEvent.Handler<T> {

    /** Create a click handler in terms of a parent. */
    public AbstractRowClickActionHandler(P parent) {
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

    /** Handle the row that was selected. */
    protected abstract void handleRow(T row);

    /** Handle the selected row, if it's a click event for something other than the selection column. */
    protected void handleSelectedRow(String event, int column, T row) {
        if (row != null) {
            if (CLICK.equals(NativeEventType.convert(event))) {
                if (this.getSelectionColumn() == null) {
                    this.handleRow(row);
                } else {
                    if (column != this.getSelectionColumn()) {
                        this.handleRow(row);
                    }
                }
            }
        }
    }

}
