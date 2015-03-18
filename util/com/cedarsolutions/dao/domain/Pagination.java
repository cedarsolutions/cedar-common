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

import java.util.HashMap;
import java.util.Map;

import com.cedarsolutions.shared.domain.TranslatableDomainObject;
import com.flipthebird.gwthashcodeequals.EqualsBuilder;
import com.flipthebird.gwthashcodeequals.HashCodeBuilder;

/**
 * Controls pagination state for back-end queries.
 * @see <a href="https://bitbucket.org/cedarsolutions/cedar-common/wiki/Pagination">The wiki for notes on pagination for non-GAE platforms</a>
 * @author Kenneth J. Pronovici <pronovic@ieee.org>
 */
@SuppressWarnings("serial")
public class Pagination extends TranslatableDomainObject {

    /** The configured page size. */
    protected int pageSize;

    /** The current page number. */
    protected int pageNumber;

    /** Whether there is any page data. */
    protected boolean hasData;

    /** Whether there is a previous page. */
    protected boolean hasPrevious;

    /** Whether there is a next page. */
    protected boolean hasNext;

    /** Whether the total number of pages is finalized. */
    protected boolean isTotalFinalized;

    /** The total number of pages so far, possibly not finalized. */
    protected int totalPages;

    /** The total number of rows so far, possibly not finalized. */
    protected int totalRows;

    /**
     * Current datastore location for pagination, useful for platforms that have such a concept.
     * This is an opaque value from the perspective of a DAO client.
     * For GAE, this is the cursor for the current page, as from Cursor.toWebSafeString().
     */
    protected String current;

    /** A map from page number datastore location, useful for platforms that have such a concept. */
    protected Map<Integer, String> cursors;

    /** A map from page number to number of rows on the page. */
    protected Map<Integer, Integer> counts;

    /** Default constructor, for GWT's benefit. */
    public Pagination() {
    }

    /** Create pagination for a specific page size. */
    public Pagination(int pageSize) {
        this.pageSize = pageSize;
        this.pageNumber = 1;
        this.hasData = false;
        this.hasPrevious = false;
        this.hasNext = false;
        this.isTotalFinalized = false;
        this.totalPages = 0;
        this.totalRows = 0;
        this.current = null;
        this.cursors = new HashMap<Integer, String>();
        this.counts = new HashMap<Integer, Integer>();
    }

    /** Copy constructor. */
    protected Pagination(Pagination source) {
        this.pageSize = source.pageSize;
        this.pageNumber = source.pageNumber;
        this.hasData = source.hasData;
        this.hasPrevious = source.hasPrevious;
        this.hasNext = source.hasNext;
        this.isTotalFinalized = source.isTotalFinalized;
        this.totalPages = source.totalPages;
        this.totalRows = source.totalRows;
        this.current = source.current;
        this.cursors = new HashMap<Integer, String>(source.cursors);
        this.counts = new HashMap<Integer, Integer>(source.counts);
    }

    /**
     * Current datastore location for pagination, useful for platforms that have such a concept.
     * This is an opaque value from the perspective of a DAO client.
     * For GAE, this is the cursor for the current page, as from Cursor.toWebSafeString().
     */
    public String getCurrent() {
        return current;
    }

    /** Get the configured page size. */
    public int getPageSize() {
        return pageSize;
    }

    /** Get the current page number. */
    public int getPageNumber() {
        return pageNumber;
    }

    /**
     * Get the next page number.
     * This value cannot advance below 1.
     */
    public int getPreviousPageNumber() {
        if (pageNumber <= 1) {
            return 1;
        } else {
            return pageNumber - 1;
        }
    }

    /**
     * Get the next page number.
     * If the total is finalized, this value cannot advance beyond the total.
     */
    public int getNextPageNumber() {
        if (this.isTotalFinalized) {
            if (pageNumber >= totalPages) {
                return totalPages;
            } else {
                return pageNumber + 1;
            }
        } else {
            return pageNumber + 1;
        }
    }

    /** Whether there is page data. */
    public boolean hasData() {
        return hasData;
    }

    /** Whether there is a previous page. */
    public boolean hasPrevious() {
        return hasPrevious;
    }

    /** Whether there is a next page. */
    public boolean hasNext() {
        return hasNext;
    }

    /** Whether the total number of pages is finalized. */
    public boolean isTotalFinalized() {
        return isTotalFinalized;
    }

    /** The total number of pages so far, possibly not finalized. */
    public int getTotalPages() {
        return totalPages;
    }

    /** The total number of rows so far, possibly not finalized. */
    public int getTotalRows() {
        return totalRows;
    }

    /** Make a copy of the object. */
    public Pagination copy() {
        return new Pagination(this);
    }

    /**
     * Return pagination for the previous page.
     * @return Pagination for the previous page, or for the current page if there is no previous page.
     */
    public Pagination previous() {
        return page(getPreviousPageNumber());
    }

    /**
     * Return pagination for the next page.
     * @return Pagination for the next page, or for the current page if there is no next page.
     */
    public Pagination next() {
        return page(getNextPageNumber());
    }

    /**
     * Return pagination for the requested page.
     * @param pageNumber  Page number to retrieve
     * @return Pagination for the requested page, or for the current page if the requested page is unknown.
     */
    public Pagination page(int pageNumber) {
        Pagination copy = this.copy();

        String cursor = cursors.get(pageNumber);
        if (cursor != null) {
            copy.pageNumber = pageNumber;
            copy.current = cursor;
        } else {
            if (pageNumber < 1) {
                cursor = cursors.get(1);
                if (cursor != null) {
                    copy.pageNumber = 1;
                    copy.current = cursor;
                }
            } else if (pageNumber > totalPages) {
                cursor = cursors.get(totalPages);
                if (cursor != null) {
                    copy.pageNumber = totalPages;
                    copy.current = cursor;
                }
            }
        }

        return copy;
    }

    /**
     * Update the pagination state in place.
     * @param current   Cursor for current page
     * @param next      Cursor for next page, null if no next page exists
     */
    public void update(String current, String next, int count) {
        hasData = true;

        this.current = current;
        cursors.put(pageNumber, this.current);
        counts.put(pageNumber, count);

        if (next == null) {
            isTotalFinalized = true;
        } else {
            cursors.put(getNextPageNumber(), next);
        }

        hasPrevious = false;
        if (pageNumber != getPreviousPageNumber()) {
            hasPrevious = cursors.get(getPreviousPageNumber()) != null;
        }

        hasNext = false;
        if (pageNumber != getNextPageNumber()) {
            hasNext = cursors.get(getNextPageNumber()) != null;
        }

        totalPages = cursors.size();
        totalRows = this.calculateTotalRows();
    }

    /** Calculate the total number of rows based on the counts map. */
    private int calculateTotalRows() {
        int total = 0;

        for (Integer value : this.counts.values()) {
            total += value;
        }

        return total;
    }

    /** Compare this object to another object. */
    @Override
    public boolean equals(Object obj) {
        Pagination other = (Pagination) obj;
        return new EqualsBuilder()
                    .append(this.pageSize, other.pageSize)
                    .append(this.pageNumber, other.pageNumber)
                    .append(this.current, other.current)
                    .isEquals();
    }

    /** Generate a hash code for this object. */
    @Override
    public int hashCode() {
        return new HashCodeBuilder()
                    .append(this.pageSize)
                    .append(this.pageNumber)
                    .append(this.current)
                    .toHashCode();
    }
}
