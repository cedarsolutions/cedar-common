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
 * Exception that all Cedar Solutions checked exceptions inherit from.
 * The class implements Serializable, so it's safe to use it for GWT code.
 * @author Kenneth J. Pronovici <pronovic@ieee.org>
 */
public class CedarException extends Exception implements Serializable {

    private static final long serialVersionUID = 1L;
    private LocalizableMessage localizableMessage;

    public CedarException() {
        this((String) null);
    }

    public CedarException(String message) {
        this(message, null);
    }

    public CedarException(String message, Throwable cause) {
        this(new LocalizableMessage(message), cause);
    }

    public CedarException(LocalizableMessage localizableMessage) {
        this(localizableMessage, null);
    }

    public CedarException(LocalizableMessage localizableMessage, Throwable cause) {
        super(localizableMessage.getText(), cause);
        this.localizableMessage = localizableMessage;
    }

    public LocalizableMessage getLocalizableMessage() {
        return this.localizableMessage;
    }
}
