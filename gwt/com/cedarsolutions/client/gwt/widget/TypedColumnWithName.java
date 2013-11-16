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
package com.cedarsolutions.client.gwt.widget;

import static com.cedarsolutions.client.gwt.widget.ColumnWithName.Sortable.NOT_SORTABLE;

import com.google.gwt.cell.client.TextCell;

/**
 * A ColumnWithName that displays typed data in a TextCell.
 * @param <T> the row type
 * @param <F> the field type within the row
 * @author Kenneth J. Pronovici <pronovic@ieee.org>
 */
public abstract class TypedColumnWithName<T, F> extends ColumnWithName<T, String> {

    /** Create a column with no name. */
    public TypedColumnWithName() {
        this((String) null);
    }

    /** Create a column with a name taken from an enum. */
    @SuppressWarnings("rawtypes")
    public TypedColumnWithName(Enum name) {
        this(name, NOT_SORTABLE);
    }

    /** Create a column with a name taken from an enum. */
    @SuppressWarnings("rawtypes")
    public TypedColumnWithName(Enum name, Sortable sortable) {
        super(name, new TextCell(), sortable);
    }

    /** Create a column with a name. */
    public TypedColumnWithName(String name) {
        this(name, NOT_SORTABLE);
    }

    /** Create a column with a name. */
    public TypedColumnWithName(String name, Sortable sortable) {
        super(name, new TextCell(), sortable);
    }

    /** Get the value to display for an item. */
    @Override
    public String getValue(T item) {
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
