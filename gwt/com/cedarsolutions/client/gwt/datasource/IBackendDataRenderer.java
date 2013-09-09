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
import com.cedarsolutions.exception.InvalidDataException;
import com.google.gwt.view.client.HasData;

/**
 * View that renders back-end data.
 * @param <T> Type of the backend data
 * @param <C> Type of the search criteria
 * @author Kenneth J. Pronovici <pronovic@ieee.org>
 */
public interface IBackendDataRenderer<T, C> {

    /** Get the number of rows per page. */
    int getPageSize();

    /** Get the display for this renderer. */
    HasData<T> getDisplay();

    /** Set the search criteria for this renderer. */
    void setSearchCriteria(C criteria);

    /** Whether this renderer already has criteria set. */
    boolean hasSearchCriteria();

    /** Get the current search criteria for this renderer. */
    C getSearchCriteria();

    /** Show a validation error related to search criteria. */
    void showValidationError(InvalidDataException error);

    /** Get the initialization event handler. */
    ViewEventHandler getInitializationEventHandler();

    /** Set the initialization event handler. */
    void setInitializationEventHandler(ViewEventHandler initializationEventHandler);

    /** Get the refresh event handler. */
    ViewEventHandler getRefreshEventHandler();

    /** Set the refresh event handler. */
    void setRefreshEventHandler(ViewEventHandler refreshEventHandler);

    /** Get the criteria reset event handler. */
    ViewEventHandler getCriteriaResetEventHandler();

    /** Set the refresh event handler. */
    void setCriteriaResetEventHandler(ViewEventHandler criteriaResetEventHandler);

}
