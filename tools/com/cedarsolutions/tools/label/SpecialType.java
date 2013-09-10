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
package com.cedarsolutions.tools.label;

import com.cedarsolutions.shared.domain.StringEnum;
import com.cedarsolutions.util.EnumUtils;

/**
 * Types of special variables.
 * @author Kenneth J. Pronovici <pronovic@ieee.org>
 */
public enum SpecialType implements StringEnum {

    UNKNOWN(),
    DATE("date"),
    TIME("time"),
    TIMESTAMP("timestamp"),
    NUMERIC_TIMESTAMP("ntimestamp");

    /** Underlying value of the enumeration. */
    private String value;

    /** Create an enumeration with a null value. */
    private SpecialType() {
        this.value = null;
    }

    /** Create an enumeration with the passed-in value. */
    private SpecialType(String value) {
        this.value = value;
    }

    /** Get the value associated with an HTTP status code. */
    @Override
    public String getValue() {
        return this.value;
    }

    /** Convert a string value into a NativeEventType enumeration. */
    public static SpecialType convert(String value) {
        return EnumUtils.getEnum(SpecialType.class, value);
    }

}
