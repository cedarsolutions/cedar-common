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

import com.google.gwt.cell.client.Cell;
import com.google.gwt.user.cellview.client.Column;

/**
 * Specialized column with a name.
 * @param <T> the row type
 * @param <C> the column type
 * @author Kenneth J. Pronovici <pronovic@ieee.org>
 */
public abstract class ColumnWithName<T, C> extends Column<T, C> {

    /** Name of the column. */
    private String name;

    /** Create a column. */
    public ColumnWithName(Cell<C> cell) {
        this((String) null, cell);
    }

    /** Create a column. */
    public ColumnWithName(Cell<C> cell, Sortable sortable) {
        this((String) null, cell, sortable);
    }

    /** Create a column with a name taken from an enum. */
    @SuppressWarnings("rawtypes")
    public ColumnWithName(Enum e, Cell<C> cell) {
        this(e.name(), cell);
    }

    /** Create a column with a name taken from an enum. */
    @SuppressWarnings("rawtypes")
    public ColumnWithName(Enum name, Cell<C> cell, Sortable sortable) {
        this(name.name(), cell, sortable);
    }

    /** Create a column with a name. */
    public ColumnWithName(String name, Cell<C> cell) {
        this(name, cell, Sortable.NOT_SORTABLE);
    }

    /** Create a column with a name. */
    public ColumnWithName(String name, Cell<C> cell, Sortable sortable) {
        super(cell);
        this.name = name;
        this.setSortable(sortable);
    }

    /** Set the sortable flag. */
    public void setSortable(Sortable sortable) {
        switch(sortable) {
        case SORTABLE:
            this.setSortable(true);
            break;
        case NOT_SORTABLE:
            this.setSortable(false);
            break;
        }
    }

    /** Get the name of this column. */
    public String getName() {
        return this.name;
    }

    /** Indicates whether a column is sortable or not. */
    public enum Sortable {
        SORTABLE,
        NOT_SORTABLE,
    }

}
