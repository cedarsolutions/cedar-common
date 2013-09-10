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
package com.cedarsolutions.dao.domain;

import static com.cedarsolutions.dao.domain.SortOrder.ASCENDING;

import com.flipthebird.gwthashcodeequals.EqualsBuilder;
import com.flipthebird.gwthashcodeequals.HashCodeBuilder;

/**
 * Base functionality for search criteria with sort.
 * @param <T> Type of the search criteria.
 * @param <S> Enumeration that defines the sort columns
 * @author Kenneth J. Pronovici <pronovic@ieee.org>
 */
@SuppressWarnings("serial")
public abstract class AbstractSearchCriteriaWithSort<T, S> extends AbstractSearchCriteria<T> implements ISearchCriteriaWithSort<T, S> {

    private SortOrder sortOrder;
    private S sortColumn;

    /** Default constructor. */
    public AbstractSearchCriteriaWithSort() {
        this.sortOrder = getDefaultSortOrder();
        this.sortColumn = getDefaultSortColumn();
    }

    /** Get default sort column name. */
    @Override
    public abstract S getDefaultSortColumn();

    /** Translate a column name to an enumeration, or null if the name is unknown. */
    @Override
    public abstract S getSortColumn(String columnName);

    /** Get default sort order. */
    @Override
    public SortOrder getDefaultSortOrder() {
        return ASCENDING;
    }

    /** Get the current sort order. */
    @Override
    public SortOrder getSortOrder() {
        return this.sortOrder;
    }

    /** Set the current sort order. */
    @Override
    public void setSortOrder(SortOrder sortOrder) {
        this.sortOrder = sortOrder;
    }

    /** Get the current sort column. */
    @Override
    public S getSortColumn() {
        return this.sortColumn;
    }

    /** Set the current sort column. */
    @Override
    public void setSortColumn(S sortColumn) {
        this.sortColumn = sortColumn;
    }

    /** Translate an enumeration to a column name. */
    @Override
    public String getColumnName(S sortColumn) {
        return sortColumn.toString();
    }

    /** Compare this object to another object. */
    @Override
    @SuppressWarnings("unchecked")
    public boolean equals(Object obj) {
        AbstractSearchCriteriaWithSort<T, S> other = (AbstractSearchCriteriaWithSort<T, S>) obj;
        return new EqualsBuilder()
                    .append(this.sortOrder, other.sortOrder)
                    .append(this.sortColumn, other.sortColumn)
                    .isEquals();
    }

    /** Generate a hash code for this object. */
    @Override
    public int hashCode() {
        return new HashCodeBuilder()
                    .append(this.sortOrder)
                    .append(this.sortColumn)
                    .toHashCode();
    }
}
