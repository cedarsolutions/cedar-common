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
package com.cedarsolutions.client.gwt.validation;

import java.util.List;


/**
 * Interface for a widget that displays validation errors.
 * @author Kenneth J. Pronovici <pronovic@ieee.org>
 */
public interface IValidationErrorWidget {

    /** Hide validation errors. */
    void hide();

    /** Show validation errors. */
    void show();

    /** Whether the widget is visible. */
    boolean isVisible();

    /** Clear the error summary. */
    void clearErrorSummary();

    /** Set the error summary. */
    void setErrorSummary(String errorSummary);

    /** Get the error summary. */
    String getErrorSummary();

    /** Clear the error list. */
    void clearErrorList();

    /** Add an error to the list. */
    void addError(String error);

    /** Get a list of the displayed errors, independent of the underlying view. */
    List<String> getErrorList();

}
