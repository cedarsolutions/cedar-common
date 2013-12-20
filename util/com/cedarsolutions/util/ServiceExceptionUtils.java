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

import com.cedarsolutions.exception.ServiceException.RootCause;
import com.cedarsolutions.exception.ServiceException;
import com.cedarsolutions.shared.domain.LocalizableMessage;

/**
 * Server-side utilities related to ServiceException.
 *
 * <p>
 * GWT does not return the exception cause hierarchy back to the client.  This
 * is because the GWT compiler can't be certain that it will be able to
 * successfully serialize every exception in the hierarchy.  Unfortunately,
 * this results in a lot of lost information.  Often, the cause hiearchy is
 * quite useful in deciding what error message to show to a client.  For
 * instance, we might want to know that our ServiceException was caused by a
 * NotImplementedException, so we can show a better error in the UI.
 * </p>
 *
 * <p>
 * ServiceException works around this using the RootCause class.  The root
 * cause preserves as much of the exception hierarchy as possible.
 * Unfortunately, ServiceException can't include the code to create the root
 * cause, because any code that touches Class is not translatable.  So, that
 * creation code has to live here, in a server-side utility class.
 * </p>
 *
 * @author Kenneth J. Pronovici <pronovic@ieee.org>
 * @see <a href="http://stackoverflow.com/questions/12448061">Stack Overflow</a>
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

    /** Create a service exception. */
    public static ServiceException createServiceException(String message, Throwable cause) {
        RootCause rootCause = createRootCause(cause);
        return new ServiceException(message, cause, rootCause);
    }

    /** Create a service exception. */
    public static ServiceException createServiceException(LocalizableMessage localizableMessage, Throwable cause) {
        RootCause rootCause = createRootCause(cause);
        return new ServiceException(localizableMessage, cause, rootCause);
    }

    /**
     * Create a a root cause based on a Throwable exception.
     * @param exception  Exception to use as source
     * @return ExceptionCause generated from the input cause.
     */
    public static RootCause createRootCause(Throwable exception) {
        if (exception == null) {
            return null;
        } else {
            String name = exception.getClass().getName();
            String canonicalName = exception.getClass().getCanonicalName();
            String simpleName = exception.getClass().getSimpleName();
            String message = exception.getMessage();
            RootCause cause = createRootCause(exception.getCause());
            return new RootCause(name, canonicalName, simpleName, message, cause);
        }
    }

}
