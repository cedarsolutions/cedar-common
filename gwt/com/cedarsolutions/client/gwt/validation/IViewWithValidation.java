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
package com.cedarsolutions.client.gwt.validation;

import java.util.Map;

import com.cedarsolutions.shared.domain.LocalizableMessage;
import com.google.gwt.user.client.ui.UIObject;

/**
 * View that supports validation.
 * @author Kenneth J. Pronovici <pronovic@ieee.org>
 */
public interface IViewWithValidation {

    /** Get the validation error widget. */
    IValidationErrorWidget getValidationErrorWidget();

    /** Translate a localizable message using this view's resources. */
    String translate(LocalizableMessage message);

    /** Get a mapping from field name to input element. */
    Map<String, UIObject> getValidatedFieldMap();

}
