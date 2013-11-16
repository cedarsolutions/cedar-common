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

import com.google.gwt.cell.client.SafeHtmlCell;
import com.google.gwt.safehtml.shared.SafeHtml;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;

/**
 * Specialized text column with a tooltip.
 *
 * <p>
 * This is incredibly ugly.  As far as I can tell, there's no way to set the tooltip
 * on a cell of arbitrary type.  Instead, you need to create a SafeHtml column and set
 * its title manually via a customized span tag.  It's not easy to generalize this
 * behavior, so what I've implemented here is the equivalent of a String column.
 * I have extended ColumnWithName rather than Column to make the implementation as
 * flexible as possible (it's legal to create ColumnWithName that has no name).
 * </p>
 *
 * @param <T> the row type
 * @see <a href="http://www.miraclecodes.com/?p=129">Miracle Codes</a>
 * @author Kenneth J. Pronovici <pronovic@ieee.org>
 */
public abstract class ColumnWithTooltip<T> extends ColumnWithName<T, SafeHtml> {

    /** Create a column with no name. */
    public ColumnWithTooltip() {
        this((String) null);
    }

    /** Create a column with a name taken from an enum. */
    @SuppressWarnings("rawtypes")
    public ColumnWithTooltip(Enum name) {
        this(name, NOT_SORTABLE);
    }

    /** Create a column with a name taken from an enum. */
    @SuppressWarnings("rawtypes")
    public ColumnWithTooltip(Enum name, Sortable sortable) {
        super(name, new SafeHtmlCell(), sortable);
    }

    /** Create a column with a name. */
    public ColumnWithTooltip(String name) {
        this(name, NOT_SORTABLE);
    }

    /** Create a column with a name. */
    public ColumnWithTooltip(String name, Sortable sortable) {
        super(name, new SafeHtmlCell(), sortable);
    }

    /** Display the string value with tooltip, using a custom span. */
    @Override
    public SafeHtml getValue(T item) {
        String value = getStringValue(item);
        String tooltip = item == null ? null : getTooltip(item);
        String contents = new SafeHtmlBuilder().appendEscaped(value).toSafeHtml().asString();
        if (tooltip != null) {
            String title = new SafeHtmlBuilder().appendEscaped(tooltip).toSafeHtml().asString();
            return new SafeHtmlBuilder().appendHtmlConstant("<span title='" + title + "'>" + contents + "</span>").toSafeHtml();
        } else {
            return new SafeHtmlBuilder().appendHtmlConstant("<span>" + contents + "</span>").toSafeHtml();
        }
    }

    /** Get the string value to be displayed. */
    public abstract String getStringValue(T item);

    /**
     * Get the tooltip value to be displayed.
     * You can assume the item is non-null, and you can safely return null.
     */
    public abstract String getTooltip(T item);

}
