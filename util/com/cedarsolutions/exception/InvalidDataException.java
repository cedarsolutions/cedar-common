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
package com.cedarsolutions.exception;

import com.cedarsolutions.shared.domain.LocalizableMessage;
import com.cedarsolutions.shared.domain.ValidationErrors;

/**
 * Error due to invalid data.
 * @author Kenneth J. Pronovici <pronovic@ieee.org>
 */
public class InvalidDataException extends CedarRuntimeException {

    private static final long serialVersionUID = 1L;
    private ValidationErrors details;

    public InvalidDataException() {
        super();
    }

    public InvalidDataException(String message) {
        super(message);
    }

    public InvalidDataException(LocalizableMessage localizableMessage) {
        super(localizableMessage);
    }

    public InvalidDataException(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidDataException(LocalizableMessage localizableMessage, Throwable cause) {
        super(localizableMessage, cause);
    }

    public InvalidDataException(String message, ValidationErrors details) {
        this(new LocalizableMessage(message), details);
    }

    public InvalidDataException(LocalizableMessage localizableMessage, ValidationErrors details) {
        super(localizableMessage);
        this.details = details;
    }

    public boolean hasDetails() {
        return this.details != null;
    }

    public ValidationErrors getDetails() {
        return this.details;
    }
}
