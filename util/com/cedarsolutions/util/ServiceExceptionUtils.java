/* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 *
 *              C E D A R
 *          S O L U T I O N S       "Software done right."
 *           S O F T W A R E
 *
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 *
 * Copyright (c) 2013-2014 Kenneth J. Pronovici.
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

import com.cedarsolutions.exception.ServiceException;
import com.cedarsolutions.shared.domain.LocalizableMessage;

/**
 * Server-side utilities to standardize the way ServiceException is created.
 * @author Kenneth J. Pronovici <pronovic@ieee.org>
 */
public class ServiceExceptionUtils {

    /** Create a service exception, filling in exception context. */
    public static ServiceException createServiceException() {
        ServiceException exception = new ServiceException();
        return ExceptionUtils.addExceptionContext(exception, 1);
    }

    /** Create a service exception, filling in exception context. */
    public static ServiceException createServiceException(String message) {
        ServiceException exception = new ServiceException(message);
        return ExceptionUtils.addExceptionContext(exception, 1);
    }

    /** Create a service exception, filling in exception context. */
    public static ServiceException createServiceException(LocalizableMessage localizableMessage) {
        ServiceException exception = new ServiceException(localizableMessage);
        return ExceptionUtils.addExceptionContext(exception, 1);
    }

    /** Create a service exception, filling in exception context. */
    public static ServiceException createServiceException(String message, Throwable cause) {
        ServiceException exception = new ServiceException(message, cause);
        return ExceptionUtils.addExceptionContext(exception, 1);
    }

    /** Create a service exception, filling in exception context. */
    public static ServiceException createServiceException(LocalizableMessage localizableMessage, Throwable cause) {
        ServiceException exception = new ServiceException(localizableMessage, cause);
        return ExceptionUtils.addExceptionContext(exception, 1);
    }

}
