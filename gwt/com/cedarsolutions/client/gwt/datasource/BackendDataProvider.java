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

import com.google.gwt.view.client.AsyncDataProvider;
import com.google.gwt.view.client.HasData;

/**
 * Asynchronous data provider used by BackendDataSource.
 * @param <T> Type of the backend data
 * @param <C> Type of the search criteria
 * @author Kenneth J. Pronovici <pronovic@ieee.org>
 */
public class BackendDataProvider<T, C> extends AsyncDataProvider<T> {

    /** Underlying data source. */
    private BackendDataSource<T, C> dataSource;

    /** Create a back-end provider associated with a data source. */
    public BackendDataProvider(BackendDataSource<T, C> dataSource) {
        this.dataSource = dataSource;
    }

    /** Called when a display changes its range of interest. */
    @Override
    protected void onRangeChanged(HasData<T> display) {
        this.dataSource.updateDisplay(display);
    }

    /** Get the underlying data source. */
    public BackendDataSource<T, C> getDataSource() {
        return this.dataSource;
    }

}
