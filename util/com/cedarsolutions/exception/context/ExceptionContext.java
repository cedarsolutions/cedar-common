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
package com.cedarsolutions.exception.context;

import com.cedarsolutions.shared.domain.TranslatableDomainObject;
import com.flipthebird.gwthashcodeequals.EqualsBuilder;
import com.flipthebird.gwthashcodeequals.HashCodeBuilder;

/**
 * Context associated with an exception.
 * @author Kenneth J. Pronovici <pronovic@ieee.org>
 */
public class ExceptionContext extends TranslatableDomainObject {

    private static final long serialVersionUID = 1L;

    private String location;
    private String stackTrace;
    private RootCause rootCause;

    public String getLocation() {
        return this.location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getStackTrace() {
        return this.stackTrace;
    }

    public void setStackTrace(String stackTrace) {
        this.stackTrace = stackTrace;
    }

    public RootCause getRootCause() {
        return this.rootCause;
    }

    public void setRootCause(RootCause rootCause) {
        this.rootCause = rootCause;
    }

    @Override
    public boolean equals(Object obj) {
        ExceptionContext other = (ExceptionContext) obj;
        return new EqualsBuilder()
                    .append(this.location, other.location)
                    .append(this.stackTrace, other.stackTrace)
                    .append(this.rootCause, other.rootCause)
                    .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
                    .append(this.location)
                    .append(this.stackTrace)
                    .append(this.rootCause)
                    .toHashCode();
    }
}
