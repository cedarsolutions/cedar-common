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

import com.cedarsolutions.client.gwt.event.ViewEventHandler;
import com.cedarsolutions.client.gwt.event.UnifiedEvent;

/**
 * View handler to refresh the view and data source.
 * @param <T> Type of the backend data
 * @param <C> Type of the search criteria
 * @author Kenneth J. Pronovici <pronovic@ieee.org>
 */
public class BackendDataRefreshHandler<T, C> implements ViewEventHandler {

    /** Underlying presenter. */
    private IBackendDataPresenter<T, C> presenter;

    /** Create a handler. */
    public BackendDataRefreshHandler(IBackendDataPresenter<T, C> presenter) {
        this.presenter = presenter;
    }

    /** Handle the event. */
    @Override
    public void handleEvent(UnifiedEvent event) {
        if (this.presenter.getDataSource() != null) {
            this.presenter.getDataSource().clear();
        }
    }

    /** Get the underlying presenter. */
    public IBackendDataPresenter<T, C> getPresenter() {
        return this.presenter;
    }

}
