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

/**
 * Useful utility methods for working with backend data interfaces.
 * @author Kenneth J. Pronovici <pronovic@ieee.org>
 */
public class BackendDataUtils {

    /**
     * Set the standard backend data event handlers.
     * @param presenter  Presenter whose renderer to manage
     */
    public static <T, C> void setStandardEventHandlers(IBackendDataPresenter<T, C> presenter) {
        BackendDataInitializeHandler<T, C> initializeHandler = new BackendDataInitializeHandler<T, C>(presenter);
        presenter.getRenderer().setInitializationEventHandler(initializeHandler);

        BackendDataRefreshHandler<T, C> refreshHandler = new BackendDataRefreshHandler<T, C>(presenter);
        presenter.getRenderer().setRefreshEventHandler(refreshHandler);

        BackendDataCriteriaResetHandler<T, C> criteriaResetHandler = new BackendDataCriteriaResetHandler<T, C>(presenter);
        presenter.getRenderer().setCriteriaResetEventHandler(criteriaResetHandler);
    }

}

