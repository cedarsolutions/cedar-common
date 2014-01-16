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
 *
 * <p>
 * The class implements Serializable so it's safe to use it for GWT code.
 * </p>
 *
 * <p>
 * GWT does not return the exception cause hierarchy back to the client.  This
 * is because the GWT compiler can't be certain that it will be able to
 * successfully serialize every exception in the hierarchy.  Unfortunately,
 * this results in a lot of lost information.  Often, the cause hiearchy is
 * quite useful in deciding what error message to show to a client.  For
 * instance, we often want to look at the cause to show a targeted error
 * message in the user interface.
 * </p>
 *
 * <p>
 * The RootCause associated with this exception preserves most of the useful
 * information from the exception hierarchy.  We can't save off the actual
 * Class object because it's not translatable.  However, we can save off useful
 * information from the class, like its name and canonical name.  That should
 * be enough, even if it's not as clean as we might hope.  Similarly, we can't
 * create the root cause in here, because any code that touches Class is not
 * translatable.  So, that code lives server-side, in ExceptionUtils.
 * </p>
 *
 * @author Kenneth J. Pronovici <pronovic@ieee.org>
 * @see <a href="http://stackoverflow.com/questions/12448061">Stack Overflow</a>
 */
public class CedarRuntimeException extends RuntimeException implements Serializable {

    private static final long serialVersionUID = 1L;
    private LocalizableMessage localizableMessage;
    private RootCause rootCause;

    public CedarRuntimeException() {
        this((String) null);
    }

    public CedarRuntimeException(String message) {
        this(message, (Throwable) null);
    }

    public CedarRuntimeException(String message, Throwable cause) {
        this(message, cause, (RootCause) null);
    }

    public CedarRuntimeException(String message, Throwable cause, RootCause rootCause) {
        this(new LocalizableMessage(message), cause, rootCause);
    }

    public CedarRuntimeException(LocalizableMessage localizableMessage) {
        this(localizableMessage, (Throwable) null);
    }

    public CedarRuntimeException(LocalizableMessage localizableMessage, Throwable cause) {
        this(localizableMessage, cause, (RootCause) null);
    }

    public CedarRuntimeException(LocalizableMessage localizableMessage, Throwable cause, RootCause rootCause) {
        super(localizableMessage == null ? null : localizableMessage.getText(), cause);
        this.localizableMessage = localizableMessage;
        this.rootCause = rootCause;
    }

    public LocalizableMessage getLocalizableMessage() {
        return this.localizableMessage;
    }

    public RootCause getRootCause() {
        return this.rootCause;
    }

}
