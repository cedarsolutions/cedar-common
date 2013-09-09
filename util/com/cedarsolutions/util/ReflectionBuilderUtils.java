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
package com.cedarsolutions.util;

import org.apache.commons.lang.SystemUtils;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.StandardToStringStyle;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import com.cedarsolutions.exception.CedarRuntimeException;

/**
 * Facade over the various CommonsLang reflection-based builder utilities.
 * @author Kenneth J. Pronovici <pronovic@ieee.org>
 */
public class ReflectionBuilderUtils {

    /** Default string style . */
    private static StringStyle STRING_STYLE = StringStyle.DEFAULT;

    /** Internal single-line string style. */
    private static final ToStringStyle SINGLE_LINE_STYLE = generateSingleLineStyle();

    /** Internal multi-line string style. */
    private static final ToStringStyle MULTI_LINE_STYLE = generateMultiLineStyle();

    /**
     * Set the default string style.
     * This should be done once at application initialization, if possible.
     * @param stringStyle  String style to set
     */
    public static synchronized void setDefaultStringStyle(StringStyle stringStyle) {
        STRING_STYLE = stringStyle;
    }

    /** Get the default string style. */
    public static synchronized StringStyle getDefaultStringStyle() {
        return STRING_STYLE;
    }

    /**
     * Use reflection to compare two objects.
     * @param left  Left hand object to compare
     * @param right Right hand object to compare
     * @return True if objects are equal, false otherwise.
     */
    public static boolean generateEquals(Object left, Object right) {
        return EqualsBuilder.reflectionEquals(left, right);
    }

    /**
     * Use reflection to compare two objects.
     * @param left     Left hand object to compare
     * @param right    Right hand object to compare
     * @param exclude  List of named fields to exclude
     * @return True if objects are equal, false otherwise.
     */
    public static boolean generateEquals(Object left, Object right, String[] exclude) {
        return EqualsBuilder.reflectionEquals(left, right, exclude);
    }

    /**
     * Use reflection to generate a hash code for an object.
     * @param obj  Object to generate hash code for
     * @return Hash code for the object.
     */
    public static int generateHashCode(Object obj) {
        return HashCodeBuilder.reflectionHashCode(obj);
    }

    /**
     * Use reflection to generate a hash code for an object.
     * @param obj      Object to generate hash code for
     * @param exclude  List of named fields to exclude
     * @return Hash code for the object.
     */
    public static int generateHashCode(Object obj, String[] exclude) {
        return HashCodeBuilder.reflectionHashCode(obj, exclude);
    }

    /**
     * Use reflection to generate a string representation using the default string style.
     * @param obj  Object to generate string representation for
     * @return String representation of the object.
     */
    public static String generateToString(Object obj) {
        return generateToString(obj, getDefaultStringStyle());
    }

    /**
     * Use reflection to generate a string representation using the passed-in string style.
     * @param obj          Object to generate string representation for
     * @param stringStyle  String style to use
     * @return String representation of the object.
     */
    public static String generateToString(Object obj, StringStyle stringStyle) {
        switch(stringStyle) {
        case DEFAULT:
        case SINGLE_LINE:
            return ToStringBuilder.reflectionToString(obj, SINGLE_LINE_STYLE);
        case MULTI_LINE:
            return ToStringBuilder.reflectionToString(obj, MULTI_LINE_STYLE);
        default:
            throw new CedarRuntimeException("Unknown string style: " + stringStyle);
        }
    }

    /** Generate an internal single-line string style for use by Apache Commons. */
    private static ToStringStyle generateSingleLineStyle() {
        return org.apache.commons.lang.builder.ToStringStyle.SHORT_PREFIX_STYLE;
    }

    /** Generate an internal multi-line string style for use by Apache Commons. */
    private static ToStringStyle generateMultiLineStyle() {
        StandardToStringStyle style = new StandardToStringStyle();

        // This is similar to org.apache.commons.lang.builder.ToStringStyle.MULTI_LINE_STYLE
        style.setContentStart("[");
        style.setFieldSeparator(SystemUtils.LINE_SEPARATOR + "  ");
        style.setFieldSeparatorAtStart(true);
        style.setContentEnd(SystemUtils.LINE_SEPARATOR + "]");
        style.setUseShortClassName(true);
        style.setUseIdentityHashCode(false);

        return style;
    }

}
