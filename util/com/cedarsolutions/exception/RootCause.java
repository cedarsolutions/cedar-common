/* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 *
 *              C E D A R
 *          S O L U T I O N S       "Software done right."
 *           S O F T W A R E
 *
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 *
 * Copyright (c) 2014 Kenneth J. Pronovici.
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

import com.cedarsolutions.shared.domain.TranslatableDomainObject;
import com.flipthebird.gwthashcodeequals.EqualsBuilder;
import com.flipthebird.gwthashcodeequals.HashCodeBuilder;


/**
 * Translatable root cause for an exception.
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
 * The RootCause associated with this exception preserves as much of the
 * exception hierarchy as possible.  We can't save off the actual Class object
 * because it's not translatable.  However, we can save off useful information
 * from the class, like its name and canonical name.  That should be enough,
 * even if it's not as clean as we might hope.  Unfortunately, we can't include
 * the code to <i>create</i> the root cause in here, because any code that
 * touches Class is not translatable.  So, that code lives server-side, in
 * ServiceExceptionUtils.
 * </p>
 *
 * @author Kenneth J. Pronovici <pronovic@ieee.org>
 * @see <a href="http://stackoverflow.com/questions/12448061">Stack Overflow</a>
 */
public class RootCause extends TranslatableDomainObject {
    private static final long serialVersionUID = 1L;

    private String name;
    private String canonicalName;
    private String simpleName;
    private String message;
    private String location;
    private String stackTrace;
    private RootCause cause;

    public RootCause() {
    }

    public RootCause(String name, String canonicalName, String simpleName, String message, String location, String stackTrace, RootCause cause) {
        this.name = name;
        this.canonicalName = canonicalName;
        this.simpleName = simpleName;
        this.message = message;
        this.location = location;
        this.stackTrace = stackTrace;
        this.cause = cause;
    }

    @Override
    public boolean equals(Object obj) {
        RootCause other = (RootCause) obj;
        return new EqualsBuilder()
                    .append(this.name, other.name)
                    .append(this.canonicalName, other.canonicalName)
                    .append(this.simpleName, other.simpleName)
                    .append(this.message, other.message)
                    .append(this.location, other.location)
                    .append(this.stackTrace, other.stackTrace)
                    .append(this.cause, other.cause)
                    .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
                    .append(this.name)
                    .append(this.canonicalName)
                    .append(this.simpleName)
                    .append(this.message)
                    .append(this.location)
                    .append(this.stackTrace)
                    .append(this.cause)
                    .toHashCode();
    }

    public String getName() {
        return this.name;
    }

    public String getCanonicalName() {
        return this.canonicalName;
    }

    public String getSimpleName() {
        return this.simpleName;
    }

    public String getMessage() {
        return this.message;
    }

    public String getLocation() {
        return this.location;
    }

    public String getStackTrace() {
        return this.stackTrace;
    }

    public RootCause getCause() {
        return this.cause;
    }

}
