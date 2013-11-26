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
package com.cedarsolutions.client.gwt.datasource;

import com.cedarsolutions.dao.domain.PaginatedResults;
import com.cedarsolutions.dao.domain.Pagination;
import com.google.gwt.view.client.HasData;
import com.google.gwt.view.client.Range;

/**
 * Backend data source for use with a data renderer.
 * @param <T> Type of the backend data
 * @param <C> Type of the search criteria
 * @author Kenneth J. Pronovici <pronovic@ieee.org>
 */
public abstract class BackendDataSource<T, C> {

    /** Underlying renderer that this provider is connected to. */
    private IBackendDataRenderer<T, C> renderer;

    /** Pagination that controls the display. */
    private Pagination pagination;

    /** Asynchronous backend data provider. */
    private BackendDataProvider<T, C> dataProvider;

    /** Whether a retrieve is active for this data source. */
    private boolean retrieveActive;

    /** Create a data source connected to a renderer. */
    public BackendDataSource(IBackendDataRenderer<T, C> renderer) {
        this.renderer = renderer;
        this.pagination = new Pagination(renderer.getPageSize());
        this.dataProvider = new BackendDataProvider<T, C>(this);
        this.retrieveActive = false;
    }

    /**
     * Asynchronously retrieve data from the back-end.
     *
     * <p>
     * This should be implemented by the child class.  The child class's
     * callback must call either applyResults() or markRetrieveComplete()
     * when the RPC call completes.  Call applyResults() if the RPC call
     * was successful and markRetrieveComplete() otherwise.  This is the only
     * way I've found to work around a double-call of the retrieve method
     * when a data table is first initialized.
     * </p>
     *
     * @param start       Starting index in the display
     * @param pagination  Pagination to use when fetching
     */
    protected abstract void retrieveFromBackEnd(int start, Pagination pagination);

    /** Whether a retrieve is active.  */
    public boolean isRetrieveActive() {
        return this.retrieveActive;
    }

    /** Mark that a retrieve operation has started. */
    public void markRetrieveStart() {
        this.retrieveActive = true;
    }

    /** Mark that a retrieve operation has completed (successfully or not). */
    public void markRetrieveComplete() {
        this.retrieveActive = false;
    }

    /** Get the renderer. */
    public IBackendDataRenderer<T, C> getRenderer() {
        return this.renderer;
    }

    /** Get the underlying pagination. */
    protected Pagination getPagination() {
        return this.pagination;
    }

    /** Get the asynchronous data provider. */
    public BackendDataProvider<T, C> getDataProvider() {
        return this.dataProvider;
    }

    /** Get the display provided by the underlying renderer. */
    public HasData<T> getDisplay() {
        return this.renderer.getDisplay();
    }

    /** Get the criteria provided by the underlying renderer. */
    public C getSearchCriteria() {
        return this.renderer.getSearchCriteria();
    }

    /** Get the configured page size. */
    public int getPageSize() {
        return this.pagination.getPageSize();
    }

    /** Clear the data provider and retrieve new data from the back-end. */
    public void clear() {
        this.pagination = new Pagination(this.getPageSize());
        if (this.getDisplay() != null) {
            // Reset the display so that we're rendering the first page again
            this.getDisplay().setVisibleRangeAndClearData(new Range(0, this.getPageSize()), true);
        }
    }

    /**
     * Called by the async data provider when a display changes its range of interest.
     * @param display The display whose range has changed
     */
    protected void updateDisplay(HasData<T> display) {
        if (this.getDisplay() != null) {
            if (display == this.getDisplay()) {
                // page is 1-based, start is zero-based and (should be) an even multiple of a page
                int start = display.getVisibleRange().getStart();
                int pageNumber = ((start + 1) / this.pagination.getPageSize()) + 1;
                if (!this.isRetrieveActive()) {
                    this.markRetrieveStart();
                    this.retrieveFromBackEnd(start, this.pagination.page(pageNumber));
                }
            }
        }
    }

    /**
     * Apply paginated results to the display managed by this data source.
     * @param start     Starting index at which to place results
     * @param results   Paginated results to apply
     */
    public void applyResults(int start, PaginatedResults<T> results) {
        this.markRetrieveComplete();
        this.pagination = results.getPagination();
        int rowCount = this.pagination.getTotalRows();
        boolean isExact = this.pagination.isTotalFinalized();

        this.getDisplay().setRowData(start, results);
        this.getDisplay().setRowCount(rowCount, isExact);  // do this AFTER setting row data, otherwise oddities result

        if (this.getDisplay().getSelectionModel() != null) {
            for (T item : this.getDisplay().getVisibleItems()) {
                this.getDisplay().getSelectionModel().setSelected(item, false);  // clear any selected rows
            }
        }
    }

}
