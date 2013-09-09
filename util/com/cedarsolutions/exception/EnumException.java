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

/**
 * Error related to an enumeration.
 * @author Kenneth J. Pronovici <pronovic@ieee.org>
 */
public class EnumException extends CedarRuntimeException {

    private static final long serialVersionUID = 1L;

    public EnumException() {
        super();
    }

    public EnumException(String message) {
        super(message);
    }

    public EnumException(LocalizableMessage localizableMessage) {
        super(localizableMessage);
    }

    public EnumException(String message, Throwable cause) {
        super(message, cause);
    }

    public EnumException(LocalizableMessage localizableMessage, Throwable cause) {
        super(localizableMessage, cause);
    }

}
