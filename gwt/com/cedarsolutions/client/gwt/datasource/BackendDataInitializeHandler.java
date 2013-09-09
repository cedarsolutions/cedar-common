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
package com.cedarsolutions.client.gwt.datasource;

import com.cedarsolutions.client.gwt.event.ViewEventHandler;
import com.cedarsolutions.client.gwt.event.UnifiedEvent;
import com.google.gwt.view.client.HasData;

/**
 * View handler to initialize the view and data source.
 * @param <T> Type of the backend data
 * @param <C> Type of the search criteria
 * @author Kenneth J. Pronovici <pronovic@ieee.org>
 */
public class BackendDataInitializeHandler<T, C> implements ViewEventHandler {

    /** Underlying presenter. */
    private IBackendDataPresenter<T, C> presenter;

    /** Create a handler. */
    public BackendDataInitializeHandler(IBackendDataPresenter<T, C> presenter) {
        this.presenter = presenter;
    }

    /** Handle the event. */
    @Override
    public void handleEvent(UnifiedEvent event) {
        // Do this *first*, otherwise criteria will be invalid once the data source is set
        // Only set the criteria if someone else hasn't done it first, so we don't wipe it out
        if (this.presenter.getRenderer() != null) {
            if (!this.presenter.getRenderer().hasSearchCriteria()) {
                C defaultCriteria = this.presenter.getDefaultCriteria();
                this.presenter.getRenderer().setSearchCriteria(defaultCriteria);
            }
        }

        // Set the data source once we're ready to search for data
        BackendDataSource<T, C> dataSource = this.presenter.createDataSource();
        if (dataSource != null) {
            this.presenter.setDataSource(dataSource);
            if (presenter.getRenderer() != null) {
                HasData<T> display = presenter.getRenderer().getDisplay();
                if (display != null) {
                    this.presenter.getDataSource().getDataProvider().addDataDisplay(display);
                }
            }
        }
    }

    /** Get the underlying presenter. */
    public IBackendDataPresenter<T, C> getPresenter() {
        return this.presenter;
    }
}
