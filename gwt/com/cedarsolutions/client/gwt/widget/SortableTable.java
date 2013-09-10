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

import static com.cedarsolutions.dao.domain.SortOrder.ASCENDING;
import static com.cedarsolutions.dao.domain.SortOrder.DESCENDING;

import java.util.HashMap;
import java.util.Map;

import com.cedarsolutions.dao.domain.ISearchCriteriaWithSort;
import com.google.gwt.user.cellview.client.Column;
import com.google.gwt.user.cellview.client.ColumnSortList.ColumnSortInfo;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.view.client.ProvidesKey;

/**
 * Sortable version of a DataTable.
 * @param <T> Type of the table.
 * @param <S> Enumeration that defines the sort columns
 * @author Kenneth J. Pronovici <pronovic@ieee.org>
 */
public class SortableTable<T, S> extends DataTable<T> {

    /** Map of column name to column, for columns which define a name. */
    private Map<String, ColumnWithName<T, ?>> columnNames = new HashMap<String, ColumnWithName<T, ?>>();

    /** Create a SortableTable. */
    public SortableTable(int pageSize, String width) {
        super(pageSize, width);
    }

    /** Create a SortableTable. */
    public SortableTable(int pageSize, String width, ProvidesKey<T> keyProvider) {
        super(pageSize, width, keyProvider);
    }

    /** Create a SortableTable. */
    public SortableTable(int pageSize, String width, ProvidesKey<T> keyProvider, Widget loadingIndicator) {
        super(pageSize, width, keyProvider, loadingIndicator);
    }

    /** Track the header and footer applied to a column. */
    @Override
    protected void trackColumn(Column<T, ?> col, Object header, Object footer) {
        super.trackColumn(col, header, footer);
        if (col instanceof ColumnWithName) {
            ColumnWithName<T, ?> named = (ColumnWithName<T, ?>) col;
            if (named.getName() != null) {
                this.columnNames.put(named.getName(), named);
            }
        }
    }

    /** Get a column by name. */
    public ColumnWithName<T, ?> getColumn(String columnName) {
        if (this.columnNames.containsKey(columnName)) {
            return this.columnNames.get(columnName);
        } else {
            return null;
        }
    }

    /**
     * Set the proper sort column on the table, based on criteria.
     * @param criteria  Criteria to use as source of sort order`
     */
    public void setDisplaySortColumn(ISearchCriteriaWithSort<T, S> criteria) {
        ColumnWithName<T, ?> column = this.getColumn(criteria.getColumnName(criteria.getSortColumn()));
        if (column != null) {
            boolean ascending = criteria.getSortOrder() == ASCENDING ? true : false;
            ColumnSortInfo sortInfo = new ColumnSortInfo(column, ascending);
            this.getColumnSortList().push(sortInfo);
        }
    }

    /**
     * Set the proper sort column on criteria, based on the table contents.
     * @param criteria   Criteria to modify
     */
    @SuppressWarnings("rawtypes")
    public void setCriteriaSortColumn(ISearchCriteriaWithSort<T, S> criteria) {
        if (this.getColumnSortList().size() <= 0) {
            criteria.setSortOrder(criteria.getDefaultSortOrder());
            criteria.setSortColumn(criteria.getDefaultSortColumn());
        } else {
            ColumnSortInfo sortInfo = this.getColumnSortList().get(0);
            criteria.setSortOrder(sortInfo.isAscending() ? ASCENDING : DESCENDING);
            ColumnWithName column = (ColumnWithName) sortInfo.getColumn();
            try {
                S sortColumn = criteria.getSortColumn(column.getName());
                if (sortColumn != null) {
                    criteria.setSortColumn(sortColumn);
                } else {
                    criteria.setSortColumn(criteria.getDefaultSortColumn());
                }
            } catch (Exception e) {
                criteria.setSortColumn(criteria.getDefaultSortColumn());
            }
        }
    }

}
