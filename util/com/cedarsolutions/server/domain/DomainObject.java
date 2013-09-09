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
package com.cedarsolutions.server.domain;

import java.io.Serializable;

import com.cedarsolutions.util.ReflectionBuilderUtils;

/**
 * Abstract domain object (not translatable to GWT code).
 * @author Kenneth J. Pronovici <pronovic@ieee.org>
 */
@SuppressWarnings("serial")
public abstract class DomainObject implements Serializable {

    /** String representation of this object. */
    @Override
    public String toString() {
        return ReflectionBuilderUtils.generateToString(this);
    }

    /** Compare this object to another object. */
    @Override
    public boolean equals(Object obj) {
        return ReflectionBuilderUtils.generateEquals(this, obj);
    }

    /** Generate a hash code for this object. */
    @Override
    public int hashCode() {
        return ReflectionBuilderUtils.generateHashCode(this);
    }

}
