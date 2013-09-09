/* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 *
 *              C E D A R
 *          S O L U T I O N S       "Software done right."
 *           S O F T W A R E
 *
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 *
 * Copyright (c) 2011-2012 Kenneth J. Pronovici.
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


/**
 * Search criteria for a type, including sorting.
 * @param <T> Type of the search criteria.
 * @param <S> Enumeration that defines the sort columns
 * @author Kenneth J. Pronovici <pronovic@ieee.org>
 */
public interface ISearchCriteriaWithSort<T, S> extends ISearchCriteria<T> {

    /** Get default sort order. */
    SortOrder getDefaultSortOrder();

    /** Get default sort column name. */
    S getDefaultSortColumn();

    /** Get the current sort order. */
    SortOrder getSortOrder();

    /** Set the current sort order. */
    void setSortOrder(SortOrder sortOrder);

    /** Get the current sort column. */
    S getSortColumn();

    /** Set the current sort column. */
    void setSortColumn(S sortColumn);

    /** Translate an enumeration to a column name. */
    String getColumnName(S sortColumn);

    /** Translate a column name to an enumeration, or null if the name is unknown. */
    S getSortColumn(String columnName);

}
