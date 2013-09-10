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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.fail;

import org.junit.Test;

import com.cedarsolutions.exception.InvalidDataException;

/**
 * Unit tests for Variable.
 * @author Kenneth J. Pronovici <pronovic@ieee.org>
 */
public class VariableTest {

    /** Test the constructor. */
    @Test public void testConstructor() {
        Variable variable;

        try {
            new Variable(null);
            fail("Expected InvalidDataException");
        } catch (InvalidDataException e) { }

        try {
            new Variable("");
            fail("Expected InvalidDataException");
        } catch (InvalidDataException e) { }

        try {
            new Variable("var");
            fail("Expected InvalidDataException");
        } catch (InvalidDataException e) { }

        try {
            new Variable("integer:var");
            fail("Expected InvalidDataException");
        } catch (InvalidDataException e) { }

        try {
            new Variable("{integer:var}");
            fail("Expected InvalidDataException");
        } catch (InvalidDataException e) { }

        variable = new Variable("${var}");
        assertEquals("${var}", variable.getVariable());
        assertEquals("var", variable.getSpecifier());
        assertEquals("var", variable.getName());
        assertEquals(VariableType.STRING, variable.getVariableType());
        assertEquals(SpecialType.UNKNOWN, variable.getSpecialType());
        assertNull(variable.getValue());

        variable = new Variable("${string:var}");
        assertEquals("${string:var}", variable.getVariable());
        assertEquals("string:var", variable.getSpecifier());
        assertEquals("var", variable.getName());
        assertEquals(VariableType.STRING, variable.getVariableType());
        assertEquals(SpecialType.UNKNOWN, variable.getSpecialType());
        assertNull(variable.getValue());

        variable = new Variable("${integer:var}");
        assertEquals("${integer:var}", variable.getVariable());
        assertEquals("integer:var", variable.getSpecifier());
        assertEquals("var", variable.getName());
        assertEquals(VariableType.INTEGER, variable.getVariableType());
        assertEquals(SpecialType.UNKNOWN, variable.getSpecialType());
        assertNull(variable.getValue());

        variable = new Variable("${boolean:var}");
        assertEquals("${boolean:var}", variable.getVariable());
        assertEquals("boolean:var", variable.getSpecifier());
        assertEquals("var", variable.getName());
        assertEquals(VariableType.BOOLEAN, variable.getVariableType());
        assertEquals(SpecialType.UNKNOWN, variable.getSpecialType());
        assertNull(variable.getValue());

        variable = new Variable("${special:date}");
        assertEquals("${special:date}", variable.getVariable());
        assertEquals("special:date", variable.getSpecifier());
        assertEquals("date", variable.getName());
        assertEquals(VariableType.SPECIAL, variable.getVariableType());
        assertEquals(SpecialType.DATE, variable.getSpecialType());
        assertNull(variable.getValue());

        variable = new Variable("${special:time}");
        assertEquals("${special:time}", variable.getVariable());
        assertEquals("special:time", variable.getSpecifier());
        assertEquals("time", variable.getName());
        assertEquals(VariableType.SPECIAL, variable.getVariableType());
        assertEquals(SpecialType.TIME, variable.getSpecialType());
        assertNull(variable.getValue());

        variable = new Variable("${special:timestamp}");
        assertEquals("${special:timestamp}", variable.getVariable());
        assertEquals("special:timestamp", variable.getSpecifier());
        assertEquals("timestamp", variable.getName());
        assertEquals(VariableType.SPECIAL, variable.getVariableType());
        assertEquals(SpecialType.TIMESTAMP, variable.getSpecialType());
        assertNull(variable.getValue());
    }

    /** Test setValue(). */
    @Test public void testSetValue() {
        Variable variable = new Variable("${boolean:var}");
        variable.setValue("value");
        assertEquals("value", variable.getValue());
    }
}
