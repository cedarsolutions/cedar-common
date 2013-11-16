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
package com.cedarsolutions.client.gwt.widget.table;

import static com.cedarsolutions.client.gwt.widget.table.ColumnWithName.Sortable.NOT_SORTABLE;

/**
 * A column that displays typed data as a string, optionally displaying a tooltip.
 * @param <T> the row type
 * @param <F> the field type within the row
 * @author Kenneth J. Pronovici <pronovic@ieee.org>
 */
public abstract class TypedColumn<T, F> extends ColumnWithTooltip<T> {

    /** Create a column with no name. */
    public TypedColumn() {
        this((String) null);
    }

    /** Create a column with a name taken from an enum. */
    @SuppressWarnings("rawtypes")
    public TypedColumn(Enum name) {
        this(name, NOT_SORTABLE);
    }

    /** Create a column with a name taken from an enum. */
    @SuppressWarnings("rawtypes")
    public TypedColumn(Enum name, Sortable sortable) {
        super(name, sortable);
    }

    /** Create a column with a name. */
    public TypedColumn(String name) {
        this(name, NOT_SORTABLE);
    }

    /** Create a column with a name. */
    public TypedColumn(String name, Sortable sortable) {
        super(name, sortable);
    }

    /** Get the value to display for an item. */
    @Override
    public String getStringValue(T item) {
        if (item == null) {
            return this.getDefaultValue();
        } else {
            F field = this.getField(item);
            if (field == null) {
                return this.getDefaultValue();
            } else {
                return this.formatField(field);
            }
        }
    }

    /** Get the tooltip value to be displayed. */
    @Override
    public String getTooltip(T item) {
        return null; // no tooltip by default
    }

    /** Get the default string value for the cell, if nothing is set. */
    protected String getDefaultValue() {
        return "";
    }

    /** Format a non-null field value properly. */
    protected abstract String formatField(F field);

    /**
     * Get the field value to display.
     * You can assume the item is non-null, and you can safely return null.
     * @param item  Item to get the value from
     * @return The correct field value from the item.
     */
    protected abstract F getField(T item);

}
