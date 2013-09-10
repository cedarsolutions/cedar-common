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

import com.cedarsolutions.exception.EnumException;
import com.cedarsolutions.shared.domain.IntegerEnum;
import com.cedarsolutions.shared.domain.StringEnum;

/**
 * Enumeration utilities.
 * @author Kenneth J. Pronovici <pronovic@ieee.org>
 */
@SuppressWarnings({ "rawtypes", "unchecked" })
public class EnumUtils {

    /**
     * Get the enumeration constant associated with a string value.
     * If no match is found, the null enumeration constant is returned (if it exists).
     * @param clazz  Enumeration class to convert to (must be StringEnum or IntegerEnum)
     * @param value  String value to convert
     * @return Enumeration constant with the given value.
     * @throws EnumException If the enumeration class is not valid or the string value is not found.
     */
    public static <T> T getEnum(Class<?> clazz, String value) throws EnumException {
        if (isStringEnum(clazz)) {
            for (StringEnum constant : (StringEnum[]) clazz.getEnumConstants()) {
                if (StringUtils.equals(constant.getValue(), value)) {
                    return (T) constant;
                }
            }

            for (StringEnum constant : (StringEnum[]) clazz.getEnumConstants()) {
                if (constant.getValue() == null) {
                    return (T) constant;
                }
            }

            throw new EnumException("String enumeration value not found: " + value);
        } else if (isIntegerEnum(clazz)) {
            try {
                Integer intValue = value == null ? null : Integer.parseInt(value);
                return (T) getEnum(clazz, intValue);
            } catch (NumberFormatException e) {
                return (T) getEnum(clazz, (Integer) null);  // a value that can't be parsed is effectively "unknown"
            }
        } else {
            throw new EnumException("Enumeration class is not StringEnum or IntegerEnum");
        }
    }

    /**
     * Get the enumeration constant associated with an integer value.
     * If no match is found, the null enumeration constant is returned (if it exists).
     * @param clazz  Enumeration class to convert to (must be IntegerEnum)
     * @param value  Integer value to convert
     * @return Enumeration constant with the given value.
     * @throws EnumException If the enumeration class is not valid or the integer value is not found.
     */
    public static <T> T getEnum(Class<?> clazz, Integer value) throws EnumException {
        if (isIntegerEnum(clazz)) {
            for (IntegerEnum constant : (IntegerEnum[]) clazz.getEnumConstants()) {
                if (equals(constant.getValue(), value)) {
                    return (T) constant;
                }
            }

            for (IntegerEnum constant : (IntegerEnum[]) clazz.getEnumConstants()) {
                if (constant.getValue() == null) {
                    return (T) constant;
                }
            }

            throw new EnumException("Integer enumeration value not found: " + value);
        } else {
            throw new EnumException("Enumeration class is not IntegerEnum.");
        }
    }

    /**
     * Indicates whether a string enumeration value is valid, not counting the null enumeration.
     * @param clazz  Enumeration class to convert to (must be StringEnum or IntegerEnum)
     * @param value  String value to check
     * @return True if the string value is a valid (not counting the null enumeration), false otherwise.
     */
    public static boolean isValid(Class clazz, String value) {
        try {
            if (isStringEnum(clazz)) {
                StringEnum constant = (StringEnum) getEnum(clazz, value);
                return constant.getValue() != null;
            } else if (isIntegerEnum(clazz)) {
                IntegerEnum constant = (IntegerEnum) getEnum(clazz, value);
                return constant.getValue() != null;
            } else {
                return false;
            }
        } catch (EnumException e) {
            return false;
        }
    }

    /**
     * Indicates whether an integer enumeration value is valid, not counting the null enumeration.
     * @param clazz  Enumeration class to convert to (must be IntegerEnum)
     * @param value  Integer value to check
     * @return True if the integer value is a valid (not counting the null enumeration), false otherwise.
     */
    public static boolean isValid(Class clazz, Integer value) {
        try {
            if (isIntegerEnum(clazz)) {
                IntegerEnum constant = (IntegerEnum) getEnum(clazz, value);
                return constant.getValue() != null;
            } else {
                return false;
            }
        } catch (EnumException e) {
            return false;
        }
    }

    /** Indicates whether an enumeration class is a StringEnum. */
    private static boolean isStringEnum(Class clazz) {
        return clazz.getEnumConstants()[0] instanceof StringEnum;
    }

    /** Indicates whether an enumeration class is an IntegerEnum. */
    private static boolean isIntegerEnum(Class clazz) {
        return clazz.getEnumConstants()[0] instanceof IntegerEnum;
    }

    /**
     * Indicates whether two objects are equal (null-safe).
     * @param obj1  First object to compare
     * @param obj2  Second object to compare
     * @return True if the objects are equal, false otherwise.
     */
    private static boolean equals(Object obj1, Object obj2) {
        if (obj1 == null) {
            return obj2 == null;
        } else if (obj2 == null) {
            return obj1 == null;
        } else {
            return obj1.equals(obj2);
        }
    }

}
