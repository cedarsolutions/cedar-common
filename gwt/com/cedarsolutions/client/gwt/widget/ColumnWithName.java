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
        this(null, cell);
    }

    /** Create a column with a name. */
    public ColumnWithName(String name, Cell<C> cell) {
        super(cell);
        this.name = name;
    }

    /** Get the name of this column. */
    public String getName() {
        return this.name;
    }

}
