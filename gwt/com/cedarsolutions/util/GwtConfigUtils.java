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

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

import com.cedarsolutions.exception.NotFoundException;
import com.google.gwt.i18n.client.Constants;
import com.google.gwt.i18n.client.Constants.DefaultBooleanValue;
import com.google.gwt.i18n.client.Constants.DefaultDoubleValue;
import com.google.gwt.i18n.client.Constants.DefaultFloatValue;
import com.google.gwt.i18n.client.Constants.DefaultIntValue;
import com.google.gwt.i18n.client.Constants.DefaultStringValue;

/**
 * Reads GWT configuration on the server-side.
 *
 * <p>
 * This is intended for use with GWT constants interfaces that are used for
 * configuration or settings rather than for localization.  It looks at the
 * default value annotations on the interface, but does not interpret any of
 * the underlying properties files that are used for translations.  Also, it
 * only supports the more common configuration types (no String array or Map).
 * </p>
 *
 * @author Kenneth J. Pronovici <pronovic@ieee.org>
 */
public class GwtConfigUtils {

    /** Get a Boolean constant from a GWT resource class. */
    public Boolean getBoolean(Class<? extends Constants> clazz, String name) {
        return (Boolean) get(clazz, ConstantType.BOOLEAN, name);
    }

    /** Get a Double constant from a GWT resource class. */
    public Double getDouble(Class<? extends Constants> clazz, String name) {
        return (Double) get(clazz, ConstantType.DOUBLE, name);
    }

    /** Get a Float constant from a GWT resource class. */
    public Float getFloat(Class<? extends Constants> clazz, String name) {
        return (Float) get(clazz, ConstantType.FLOAT, name);
    }

    /** Get a Integer constant from a GWT resource class. */
    public Integer getInteger(Class<? extends Constants> clazz, String name) {
        return (Integer) get(clazz, ConstantType.INTEGER, name);
    }

    /** Get a String constant from a GWT resource class. */
    public String getString(Class<? extends Constants> clazz, String name) {
        return (String) get(clazz, ConstantType.STRING, name);
    }

    /** Get a mocked value of a particular type from a class. */
    private Object get(Class<? extends Constants> clazz, ConstantType type, String name) {
        for (Method method : clazz.getMethods()) {
            if (method.getName().equals(name)) {
                return get(clazz, method, type);
            }
        }

        throw new NotFoundException("Could not find: " + clazz.getCanonicalName() + "." + name + "()");
    }

    /** Get a mocked value of a particular type from a method. */
    private Object get(Class<? extends Constants> clazz, Method method, ConstantType type) {
        switch (type) {
        case BOOLEAN:
            for (Annotation annotation : method.getDeclaredAnnotations()) {
                if (annotation instanceof DefaultBooleanValue) {
                    return ((DefaultBooleanValue) annotation).value();
                }
            }
            break;
        case DOUBLE:
            for (Annotation annotation : method.getDeclaredAnnotations()) {
                if (annotation instanceof DefaultDoubleValue) {
                    return ((DefaultDoubleValue) annotation).value();
                }
            }
            break;
        case FLOAT:
            for (Annotation annotation : method.getDeclaredAnnotations()) {
                if (annotation instanceof DefaultFloatValue) {
                    return ((DefaultFloatValue) annotation).value();
                }
            }
            break;
        case INTEGER:
            for (Annotation annotation : method.getDeclaredAnnotations()) {
                if (annotation instanceof DefaultIntValue) {
                    return ((DefaultIntValue) annotation).value();
                }
            }
            break;
        case STRING:
            for (Annotation annotation : method.getDeclaredAnnotations()) {
                if (annotation instanceof DefaultStringValue) {
                    return ((DefaultStringValue) annotation).value();
                }
            }
            break;
        }

        throw new NotFoundException("Wrong type: " + clazz.getCanonicalName() + "." + method.getName() + "() is not " + type);
    }

    /** Available constant types. */
    protected enum ConstantType {
        BOOLEAN,
        DOUBLE,
        FLOAT,
        INTEGER,
        STRING,
    }

}
