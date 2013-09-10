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
package com.cedarsolutions.exception;

import java.io.Serializable;

import com.cedarsolutions.shared.domain.LocalizableMessage;


/**
 * Exception that all Cedar Solutions runtime exceptions inherit from.
 * The class implements Serializable so it's safe to use it for GWT code.
 * @author Kenneth J. Pronovici <pronovic@ieee.org>
 */
public class CedarRuntimeException extends RuntimeException implements Serializable {

    private static final long serialVersionUID = 1L;
    private LocalizableMessage localizableMessage;

    public CedarRuntimeException() {
        this((String) null);
    }

    public CedarRuntimeException(String message) {
        this(message, null);
    }

    public CedarRuntimeException(String message, Throwable cause) {
        this(new LocalizableMessage(message), cause);
    }

    public CedarRuntimeException(LocalizableMessage localizableMessage) {
        this(localizableMessage, null);
    }

    public CedarRuntimeException(LocalizableMessage localizableMessage, Throwable cause) {
        super(localizableMessage.getText(), cause);
        this.localizableMessage = localizableMessage;
    }

    public LocalizableMessage getLocalizableMessage() {
        return this.localizableMessage;
    }

}
