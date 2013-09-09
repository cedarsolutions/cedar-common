/* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 *
 *              C E D A R
 *          S O L U T I O N S       "Software done right."
 *           S O F T W A R E
 *
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 *
 * Copyright (c) 2012 Kenneth J. Pronovici.
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

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;

import com.cedarsolutions.exception.InvalidDataException;
import com.cedarsolutions.exception.NotSupportedException;
import com.cedarsolutions.junit.gwt.classloader.GwtResourceCreator;
import com.cedarsolutions.util.DateUtils;
import com.google.gwt.i18n.client.ConstantsWithLookup;

/**
 * Variable resolver for use with LabelTool.
 *
 * <h3>Variable Syntax</h3>
 *
 * <p>
 * Variable syntax is straightforward.  Variables all have the form
 * <code>${var}</code>.  Variable names can be comprised of letters, numbers,
 * and the underscore characeter, and must start with a letter (just like Java
 * variables).
 * </p>
 *
 * <p>
 * With no other modifiers, a variable is assumed to be a string.  You can use
 * modifiers to specifically indicate that a variable is a <code>string</code>,
 * <code>integer</code>, or <code>boolean</code> value.  The modifier is used
 * when retrieving the value from the resource class, but in the end a string
 * representation will be generated when creating the label.  Modifiers are
 * placed within the normal variable syntax, like <code>${integer:var}</code>.
 * </p>
 *
 * <p>
 * There are also a number of special variables, denoted by the special
 * modifier.  These are <code>special:date</code>, <code>special:time</code>,
 * and <code>special:timestamp</code>.  These generate time-related values
 * using the standard formats in DateUtils.
 * </p>
 *
 * <h3>
 * Future Functionality
 * </h3>
 *
 * <p>
 * This resolver is currently implemented in terms of GWT's ConstantsWithLookup
 * resource class.  However, it's structured so that it should be easy to
 * utilize other kinds of classes in the future, as long as they support
 * similar operations.
 * </p>
 *
 * @author Kenneth J. Pronovici <pronovic@ieee.org>
 */
public class VariableResolver {

    /** GWT constants that will be used to resolve variable references. */
    protected ConstantsWithLookup constantsWithLookup;

    /**
     * Create a variable resolver in terms of a named resource class.
     * @throws InvalidDataException   If the resource class is not found
     * @throws NotSupportedException  If the resource class is not supported
     */
    public VariableResolver(String resourceClass) {
        this(classForName(resourceClass));
    }

    /**
     * Create a variable resolver in terms of a specific class.
     * @throws NotSupportedException  If the resource class is not supported
     */
    public VariableResolver(Class<?> resourceClass) {
        if (resourceClass == null) {
            this.constantsWithLookup = null;
        } else {
            if (ConstantsWithLookup.class.isAssignableFrom(resourceClass)) {
                Object result = GwtResourceCreator.create(resourceClass);
                this.constantsWithLookup = (ConstantsWithLookup) result;
            } else {
                throw new NotSupportedException("Variable resolver does not support classes of type: " + resourceClass.getCanonicalName());
            }
        }
    }

    /**
     * Get the class for resource class name.
     * @throws InvalidDataException   If the resource class is not found
     */
    private static Class<?> classForName(String resourceClass) {
        try {
            return resourceClass == null ? null : Class.forName(resourceClass);
        } catch (ClassNotFoundException e) {
            throw new InvalidDataException("Variable resolver could not find class: " + resourceClass);
        }
    }

    /**
     * Expand all variable references in a format string.
     * @param format   Format string to expand
     * @param strict   Whether to be strict (all variable references must be resolved)
     * @return String generated by expanding variable references.
     * @throws InvalidDataException If strict is set and any variable cannot be resolved.
     */
    public String expandFormat(String format, boolean strict) {
        List<Variable> variables = parseVariables(format);
        this.resolveVariables(variables, strict);
        return expandVariables(format, variables);
    }

    /** Parse a list of variables out of a format string. */
    protected static List<Variable> parseVariables(String format) {
        List<Variable> variables = new ArrayList<Variable>();

        if (format != null) {
            Matcher matcher = Variable.PATTERN.matcher(format);
            while (matcher.find()) {
                int start = matcher.start();
                int end = matcher.end();
                Variable variable = new Variable(format.substring(start, end));
                variables.add(variable);
            }
        }

        return variables;
    }

    /** Expand a format string using a list of variables. */
    protected static String expandVariables(String format, List<Variable> variables) {
        String expanded = format;

        if (expanded != null) {
            for (Variable variable : variables) {
                if (variable.getValue() != null) {
                    expanded = expanded.replace(variable.getVariable(), variable.getValue());
                }
            }
        }

        return expanded;
    }

    /**
     * Resolve a list of variables, setting the value on each variable object.
     * @param variables  List of variables to operate on
     * @param strict     Whether to be strict (all variable references must be resolved)
     * @throws InvalidDataException If strict is set and any variable cannot be resolved.
     */
    protected void resolveVariables(List<Variable> variables, boolean strict) {
        for (Variable variable : variables) {
            String value = resolveVariable(variable);
            if (value == null && strict) {
                throw new InvalidDataException("Could not resolve variable " + variable.getVariable());
            }
            variable.setValue(value);
        }
    }

    /**
     * Resolve a variable, returning the string value for that variable.
     * @param variable  Varible to resolve
     * @return Value for the variable, or null if it cannot be resolved.
     */
    private String resolveVariable(Variable variable) {
        String stringValue = null;

        try {
            switch (variable.getVariableType()) {
            case STRING:
                stringValue = this.constantsWithLookup.getString(variable.getName());
                break;
            case INTEGER:
                int integerValue = this.constantsWithLookup.getInt(variable.getName());
                stringValue = Integer.toString(integerValue);
                break;
            case BOOLEAN:
                boolean booleanValue = this.constantsWithLookup.getBoolean(variable.getName());
                stringValue = booleanValue ? "true" : "false";
                break;
            case SPECIAL:
                switch(variable.getSpecialType()) {
                case DATE:
                    stringValue = DateUtils.formatDate(DateUtils.getCurrentDate());
                    break;
                case TIME:
                    stringValue = DateUtils.formatTime(DateUtils.getCurrentDate());
                    break;
                case TIMESTAMP:
                    stringValue = DateUtils.formatTimestamp(DateUtils.getCurrentDate());
                    break;
                case NUMERIC_TIMESTAMP:
                    stringValue = DateUtils.formatNumericTimestamp(DateUtils.getCurrentDate());
                    break;
                }
            }
        } catch (Exception e) { }

        return stringValue;
    }

}
