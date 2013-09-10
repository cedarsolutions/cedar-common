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

import java.util.regex.Pattern;

import com.cedarsolutions.exception.InvalidDataException;
import com.cedarsolutions.util.StringUtils;


/**
 * Variable parsed out of a format string.
 * @author Kenneth J. Pronovici <pronovic@ieee.org>
 */
public class Variable {

    /** Regular expression that identifies a variable. */
    public static final String REGEX = "(\\$\\{)(special:|string:|integer:|boolean:)?([a-zA-Z0-9_]+)(\\})";

    /** Pattern used to search for REGEX. */
    public static final Pattern PATTERN = Pattern.compile(REGEX);

    /** Full variable pulled out of the format string, like "${integer:var}". */
    private String variable;

    /** Specifier from the variable, like "integer:var". */
    private String specifier;

    /** Variable name, like "var". */
    private String name;

    /** Type of the variable. */
    private VariableType variableType;

    /** Special type, if the variable type is SPECIAL. */
    private SpecialType specialType;

    /** Value of the variable. */
    private String value;

    /**
     * Create a variable in terms of a variable string.
     * @param variable  Full variable pulled out of the format string, like "${integer:var}"
     * @throws InvalidDataException If the passed-in variable string does not have valid syntax.
     */
    public Variable(String variable) {
        if (variable == null || !StringUtils.matches(variable, PATTERN)) {
            throw new InvalidDataException("Passed in variable does not have proper syntax.");
        }

        this.variable = variable;
        this.specifier = this.variable.replace("${", "").replace("}", "");

        this.name = specifier;
        this.variableType = VariableType.STRING;
        this.specialType = SpecialType.UNKNOWN;

        if (this.name.startsWith("string:")) {
            this.name = this.name.replace("string:", "");
            this.variableType = VariableType.STRING;
        } else if (this.name.startsWith("integer:")) {
            this.name = this.name.replace("integer:", "");
            this.variableType = VariableType.INTEGER;
        } else if (this.name.startsWith("boolean:")) {
            this.name = this.name.replace("boolean:", "");
            this.variableType = VariableType.BOOLEAN;
        } else if (this.name.startsWith("special:")) {
            this.name = this.name.replace("special:", "");
            this.variableType = VariableType.SPECIAL;
            this.specialType = SpecialType.convert(this.name);
        }
    }

    public String getVariable() {
        return this.variable;
    }

    public String getSpecifier() {
        return this.specifier;
    }

    public String getName() {
        return this.name;
    }

    public VariableType getVariableType() {
        return this.variableType;
    }

    public SpecialType getSpecialType() {
        return this.specialType;
    }

    public String getValue() {
        return this.value;
    }

    public void setValue(String value) {
        this.value = value;
    }

}
