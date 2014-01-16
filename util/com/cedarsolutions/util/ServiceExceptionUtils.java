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
package com.cedarsolutions.util;

import com.cedarsolutions.exception.RootCause;
import com.cedarsolutions.exception.ServiceException;
import com.cedarsolutions.shared.domain.LocalizableMessage;

/**
 * Server-side utilities to standardize the way ServiceException is created.
 * @author Kenneth J. Pronovici <pronovic@ieee.org>
 */
public class ServiceExceptionUtils {

    /** Create a service exception. */
    public static ServiceException createServiceException() {
        return new ServiceException();
    }

    /** Create a service exception. */
    public static ServiceException createServiceException(String message) {
        return new ServiceException(message);
    }

    /** Create a service exception. */
    public static ServiceException createServiceException(LocalizableMessage localizableMessage) {
        return new ServiceException(localizableMessage);
    }

    /** Create a service exception, automatically filling in the root cause. */
    public static ServiceException createServiceException(String message, Throwable cause) {
        RootCause rootCause = ExceptionUtils.createRootCause(cause);
        return new ServiceException(message, cause, rootCause);
    }

    /** Create a service exception, automatically filling in the root cause. */
    public static ServiceException createServiceException(LocalizableMessage localizableMessage, Throwable cause) {
        RootCause rootCause = ExceptionUtils.createRootCause(cause);
        return new ServiceException(localizableMessage, cause, rootCause);
    }

}
