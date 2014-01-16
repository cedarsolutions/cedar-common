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

import com.cedarsolutions.shared.domain.LocalizableMessage;

/**
 * Encapsulates an error from a service call, including a translatable root cause.
 *
 * <p>
 * ServiceException is a special exception intended to be used in the RPC interface.
 * All of your RPC methods should declare that they throw ServiceException.  The
 * SecuredServiceExporter converts other exceptions into ServiceException, which
 * ensures that GWT returns all of the useful information (like 403 error codes,
 * etc.) to the client.
 * </p>
 *
 * @author Kenneth J. Pronovici <pronovic@ieee.org>
 */
public class ServiceException extends CedarRuntimeException {

    private static final long serialVersionUID = 1L;

    public ServiceException() {
        super();
    }

    public ServiceException(String message) {
        super(message);
    }

    public ServiceException(String message, Throwable cause) {
        super(message, cause);
    }

    public ServiceException(String message, Throwable cause, RootCause rootCause) {
        super(message, cause, rootCause);
    }

    public ServiceException(LocalizableMessage localizableMessage) {
        super(localizableMessage);
    }

    public ServiceException(LocalizableMessage localizableMessage, Throwable cause) {
        super(localizableMessage, cause);
    }

    public ServiceException(LocalizableMessage localizableMessage, Throwable cause, RootCause rootCause) {
        super(localizableMessage, cause, rootCause);
    }

}
