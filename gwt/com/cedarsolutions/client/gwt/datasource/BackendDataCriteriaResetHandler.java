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

import com.cedarsolutions.client.gwt.event.UnifiedEvent;
import com.cedarsolutions.client.gwt.event.ViewEventHandler;

/**
 * View handler to reset a view's search criteria and refresh the display.
 * @param <T> Type of the backend data
 * @param <C> Type of the search criteria
 * @author Kenneth J. Pronovici <pronovic@ieee.org>
 */
public class BackendDataCriteriaResetHandler<T, C> implements ViewEventHandler {

    /** Underlying presenter. */
    private IBackendDataPresenter<T, C> presenter;

    /** Create a handler. */
    public BackendDataCriteriaResetHandler(IBackendDataPresenter<T, C> presenter) {
        this.presenter = presenter;
    }

    /** Handle the event. */
    @Override
    public void handleEvent(UnifiedEvent event) {
        C defaultCriteria = this.presenter.getDefaultCriteria();
        if (this.presenter.getRenderer() != null) {
            this.presenter.getRenderer().setSearchCriteria(defaultCriteria);
            if (this.presenter.getRenderer().getRefreshEventHandler() != null) {
                this.presenter.getRenderer().getRefreshEventHandler().handleEvent(event);
            }
        }
    }

    /** Get the underlying presenter. */
    public IBackendDataPresenter<T, C> getPresenter() {
        return this.presenter;
    }

}
