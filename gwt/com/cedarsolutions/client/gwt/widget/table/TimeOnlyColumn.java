/* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 *
 *              C E D A R
 *          S O L U T I O N S       "Software done right."
 *           S O F T W A R E
 *
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 *
 * Copyright (c) 2014 Kenneth J. Pronovici.
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

import java.util.Date;

import com.cedarsolutions.util.gwt.GwtDateUtils;

/**
 * A ColumnWithName that displays the time only for a Date.
 * @param <T> the row type
 * @author Kenneth J. Pronovici <pronovic@ieee.org>
 */
public abstract class TimeOnlyColumn<T> extends TypedColumn<T, Date> {

    /** Create a column with no name. */
    public TimeOnlyColumn() {
        super();
    }

    /** Create a column with a name taken from an enum. */
    @SuppressWarnings("rawtypes")
    public TimeOnlyColumn(Enum name) {
        super(name);
    }

    /** Create a column with a name taken from an enum. */
    @SuppressWarnings("rawtypes")
    public TimeOnlyColumn(Enum name, Sortable sortable) {
        super(name, sortable);
    }

    /** Create a column with a name. */
    public TimeOnlyColumn(String name) {
        super(name);
    }

    /** Create a column with a name. */
    public TimeOnlyColumn(String name, Sortable sortable) {
        super(name, sortable);
    }

    /** Format a non-null field value properly. */
    @Override
    protected String formatField(Date field) {
        return GwtDateUtils.formatTimeOnly(field);
    }

}
