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
package com.cedarsolutions.config;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.junit.Test;

import com.cedarsolutions.exception.NotConfiguredException;
import com.cedarsolutions.shared.domain.email.EmailFormat;

/**
 * Unit tests for PropertyBasedConfig.
 * @author Kenneth J. Pronovici <pronovic@ieee.org>
 */
public class PropertyBasedConfigTest {

    /** Test isConfigured(). */
    @Test public void testIsConfigured() {
        Config config = null;

        config = new Config(null);
        assertFalse(config.isConfigured());

        config = new Config(new Properties());
        assertTrue(config.isConfigured());
    }

    /** Test parseRequiredString(). */
    @Test public void testParseRequiredString() {
        Config config = null;
        Properties properties = null;

        // Property does not exist
        try {
            properties = new Properties();
            config = new Config(properties);
            config.parseRequiredString("UnitTest.value");
            fail("Expected NotConfiguredException");
        } catch (NotConfiguredException e) { }

        // Property exists but is empty
        try {
            properties = new Properties();
            properties.setProperty("UnitTest.value", "");
            config = new Config(properties);
            config.parseRequiredString("UnitTest.value");
            fail("Expected NotConfiguredException");
        } catch (NotConfiguredException e) { }

        // Property exists and is not empty.
        properties = new Properties();
        properties.setProperty("UnitTest.value", "XXX");
        config = new Config(properties);
        assertEquals("XXX", config.parseRequiredString("UnitTest.value"));
    }

    /** Test parseOptionalString(). */
    @Test public void testParseOptionalString() {
        Config config = null;
        Properties properties = null;

        // Property does not exist
        properties = new Properties();
        config = new Config(properties);
        assertEquals("default", config.parseOptionalString("UnitTest.value", "default"));

        // Property exists but is empty
        properties = new Properties();
        properties.setProperty("UnitTest.value", "");
        config = new Config(properties);
        assertEquals("default", config.parseOptionalString("UnitTest.value", "default"));

        // Property exists and is not empty.
        properties = new Properties();
        properties.setProperty("UnitTest.value", "XXX");
        config = new Config(properties);
        assertEquals("XXX", config.parseOptionalString("UnitTest.value", "default"));
    }

    /** Test parseRequiredStringList(). */
    @Test public void testParseRequiredStringList() {
        Config config = null;
        Properties properties = null;
        List<String> expected = null;

        // Property does not exist
        try {
            properties = new Properties();
            config = new Config(properties);
            config.parseRequiredStringList("UnitTest.value");
            fail("Expected NotConfiguredException");
        } catch (NotConfiguredException e) { }

        // Property exists but is empty
        expected = new ArrayList<String>();
        properties = new Properties();
        properties.setProperty("UnitTest.value", "");
        config = new Config(properties);
        assertEquals(expected, config.parseRequiredStringList("UnitTest.value"));

        // Property exists and is not empty, one value
        expected = new ArrayList<String>();
        expected.add("XXX");
        properties = new Properties();
        properties.setProperty("UnitTest.value", "XXX");
        config = new Config(properties);
        assertEquals(expected, config.parseRequiredStringList("UnitTest.value"));

        // Property exists and is not empty, multiple values
        expected = new ArrayList<String>();
        expected.add("XXX");
        expected.add("YYY");
        properties = new Properties();
        properties.setProperty("UnitTest.value", "XXX, YYY");
        config = new Config(properties);
        assertEquals(expected, config.parseRequiredStringList("UnitTest.value"));
    }

    /** Test parseRequiredNonEmptyStringList(). */
    @Test public void testParseRequiredNonEmptyStringList() {
        Config config = null;
        Properties properties = null;
        List<String> expected = null;

        // Property does not exist
        try {
            properties = new Properties();
            config = new Config(properties);
            config.parseRequiredNonEmptyStringList("UnitTest.value");
            fail("Expected NotConfiguredException");
        } catch (NotConfiguredException e) { }

        // Property exists but is empty
        try {
            expected = new ArrayList<String>();
            properties = new Properties();
            properties.setProperty("UnitTest.value", "");
            config = new Config(properties);
            config.parseRequiredNonEmptyStringList("UnitTest.value");
            fail("Expected NotConfiguredException");
        } catch (NotConfiguredException e) { }

        // Property exists and is not empty, one value
        expected = new ArrayList<String>();
        expected.add("XXX");
        properties = new Properties();
        properties.setProperty("UnitTest.value", "XXX");
        config = new Config(properties);
        assertEquals(expected, config.parseRequiredNonEmptyStringList("UnitTest.value"));

        // Property exists and is not empty, multiple values
        expected = new ArrayList<String>();
        expected.add("XXX");
        expected.add("YYY");
        properties = new Properties();
        properties.setProperty("UnitTest.value", "XXX, YYY");
        config = new Config(properties);
        assertEquals(expected, config.parseRequiredNonEmptyStringList("UnitTest.value"));
    }

    /** Test parseOptionalStringList(). */
    @Test public void testParseOptionalStringList() {
        Config config = null;
        Properties properties = null;
        List<String> expected = null;

        List<String> defaultValue = new ArrayList<String>();
        defaultValue.add("default");

        // Property does not exist
        properties = new Properties();
        config = new Config(properties);
        assertEquals(defaultValue, config.parseOptionalStringList("UnitTest.value", defaultValue));

        // Property exists but is empty
        expected = new ArrayList<String>();
        properties = new Properties();
        properties.setProperty("UnitTest.value", "");
        config = new Config(properties);
        assertEquals(defaultValue, config.parseOptionalStringList("UnitTest.value", defaultValue));

        // Property exists and is not empty, one value
        expected = new ArrayList<String>();
        expected.add("XXX");
        properties = new Properties();
        properties.setProperty("UnitTest.value", "XXX");
        config = new Config(properties);
        assertEquals(expected, config.parseOptionalStringList("UnitTest.value", defaultValue));

        // Property exists and is not empty, multiple values
        expected = new ArrayList<String>();
        expected.add("XXX");
        expected.add("YYY");
        properties = new Properties();
        properties.setProperty("UnitTest.value", "XXX, YYY");
        config = new Config(properties);
        assertEquals(expected, config.parseOptionalStringList("UnitTest.value", defaultValue));
    }

    /** Test parseRequiredInteger(). */
    @Test public void testParseRequiredInteger() {
        Config config = null;
        Properties properties = null;

        // Property does not exist
        try {
            properties = new Properties();
            config = new Config(properties);
            config.parseRequiredInteger("UnitTest.value");
            fail("Expected NotConfiguredException");
        } catch (NotConfiguredException e) { }

        // Property exists but is empty
        try {
            properties = new Properties();
            properties.setProperty("UnitTest.value", "");
            config = new Config(properties);
            config.parseRequiredInteger("UnitTest.value");
            fail("Expected NotConfiguredException");
        } catch (NotConfiguredException e) { }

        // Property exists but is invalid
        try {
            properties = new Properties();
            properties.setProperty("UnitTest.value", "Z");
            config = new Config(properties);
            config.parseRequiredInteger("UnitTest.value");
            fail("Expected NotConfiguredException");
        } catch (NotConfiguredException e) { }

        // Property exists and is valid
        properties = new Properties();
        properties.setProperty("UnitTest.value", "1");
        config = new Config(properties);
        assertEquals(1, config.parseRequiredInteger("UnitTest.value"));
    }

    /** Test parseOptionalInteger(). */
    @Test public void testParseOptionalInteger() {
        Config config = null;
        Properties properties = null;

        // Property does not exist
        properties = new Properties();
        config = new Config(properties);
        assertEquals(999, config.parseOptionalInteger("UnitTest.value", 999));

        // Property exists but is empty
        properties = new Properties();
        properties.setProperty("UnitTest.value", "");
        config = new Config(properties);
        assertEquals(999, config.parseOptionalInteger("UnitTest.value", 999));

        // Property exists but is invalid
        try {
            properties = new Properties();
            properties.setProperty("UnitTest.value", "Z");
            config = new Config(properties);
            config.parseOptionalInteger("UnitTest.value", 999);
            fail("Expected NotConfiguredException");
        } catch (NotConfiguredException e) { }

        // Property exists and is valid
        properties = new Properties();
        properties.setProperty("UnitTest.value", "1");
        config = new Config(properties);
        assertEquals(1, config.parseOptionalInteger("UnitTest.value", 999));
    }

    /** Test parseRequiredBoolean(). */
    @Test public void testParseRequiredBoolean() {
        Config config = null;
        Properties properties = null;

        // Property does not exist
        try {
            properties = new Properties();
            config = new Config(properties);
            config.parseRequiredBoolean("UnitTest.value");
            fail("Expected NotConfiguredException");
        } catch (NotConfiguredException e) { }

        // Property exists but is empty
        try {
            properties = new Properties();
            properties.setProperty("UnitTest.value", "");
            config = new Config(properties);
            config.parseRequiredBoolean("UnitTest.value");
            fail("Expected NotConfiguredException");
        } catch (NotConfiguredException e) { }

        // Property exists but is invalid
        try {
            properties = new Properties();
            properties.setProperty("UnitTest.value", "Z");
            config = new Config(properties);
            config.parseRequiredBoolean("UnitTest.value");
            fail("Expected NotConfiguredException");
        } catch (NotConfiguredException e) { }

        // Property exists and is valid (true)
        properties = new Properties();
        properties.setProperty("UnitTest.value", "true");
        config = new Config(properties);
        assertEquals(true, config.parseRequiredBoolean("UnitTest.value"));

        // Property exists and is valid (false)
        properties = new Properties();
        properties.setProperty("UnitTest.value", "false");
        config = new Config(properties);
        assertEquals(false, config.parseRequiredBoolean("UnitTest.value"));
    }

    /** Test parseOptionalBoolean(). */
    @Test public void testParseOptionalBoolean() {
        Config config = null;
        Properties properties = null;

        // Property does not exist
        properties = new Properties();
        config = new Config(properties);
        assertEquals(true, config.parseOptionalBoolean("UnitTest.value", true));
        assertEquals(false, config.parseOptionalBoolean("UnitTest.value", false));

        // Property exists but is empty
        properties = new Properties();
        config = new Config(properties);
        properties.setProperty("UnitTest.value", "");
        assertEquals(true, config.parseOptionalBoolean("UnitTest.value", true));
        assertEquals(false, config.parseOptionalBoolean("UnitTest.value", false));

        // Property exists but is invalid
        try {
            properties = new Properties();
            properties.setProperty("UnitTest.value", "Z");
            config = new Config(properties);
            config.parseOptionalBoolean("UnitTest.value", true);
            fail("Expected NotConfiguredException");
        } catch (NotConfiguredException e) { }

        // Property exists and is valid (true)
        properties = new Properties();
        properties.setProperty("UnitTest.value", "true");
        config = new Config(properties);
        assertEquals(true, config.parseOptionalBoolean("UnitTest.value", true));
        assertEquals(true, config.parseOptionalBoolean("UnitTest.value", false));

        // Property exists and is valid (false)
        properties = new Properties();
        properties.setProperty("UnitTest.value", "false");
        config = new Config(properties);
        assertEquals(false, config.parseOptionalBoolean("UnitTest.value", true));
        assertEquals(false, config.parseOptionalBoolean("UnitTest.value", false));
    }

    /** Test parseRequiredEmailFormat(). */
    @Test public void testParseRequiredEmailFormat() {
        Config config = null;
        Properties properties = null;

        // Property does not exist
        try {
            properties = new Properties();
            config = new Config(properties);
            config.parseRequiredEmailFormat("UnitTest.value");
            fail("Expected NotConfiguredException");
        } catch (NotConfiguredException e) { }

        // Property exists but is empty
        try {
            properties = new Properties();
            properties.setProperty("UnitTest.value", "");
            config = new Config(properties);
            config.parseRequiredEmailFormat("UnitTest.value");
            fail("Expected NotConfiguredException");
        } catch (NotConfiguredException e) { }

        // Property exists but is invalid
        try {
            properties = new Properties();
            properties.setProperty("UnitTest.value", "Z");
            config = new Config(properties);
            config.parseRequiredEmailFormat("UnitTest.value");
            fail("Expected NotConfiguredException");
        } catch (NotConfiguredException e) { }

        // Property exists and is valid (MULTIPART)
        properties = new Properties();
        properties.setProperty("UnitTest.value", "MULTIPART");
        config = new Config(properties);
        assertEquals(EmailFormat.MULTIPART, config.parseRequiredEmailFormat("UnitTest.value"));

        // Property exists and is valid (PLAINTEXT)
        properties = new Properties();
        properties.setProperty("UnitTest.value", "PLAINTEXT");
        config = new Config(properties);
        assertEquals(EmailFormat.PLAINTEXT, config.parseRequiredEmailFormat("UnitTest.value"));
    }

    /** Test parseOptionalEmailFormat(). */
    @Test public void testParseOptionalEmailFormat() {
        Config config = null;
        Properties properties = null;

        // Property does not exist
        properties = new Properties();
        config = new Config(properties);
        assertEquals(EmailFormat.MULTIPART, config.parseOptionalEmailFormat("UnitTest.value", EmailFormat.MULTIPART));

        // Property exists but is empty
        properties = new Properties();
        config = new Config(properties);
        properties.setProperty("UnitTest.value", "");
        assertEquals(EmailFormat.PLAINTEXT, config.parseOptionalEmailFormat("UnitTest.value", EmailFormat.PLAINTEXT));

        // Property exists but is invalid
        try {
            properties = new Properties();
            properties.setProperty("UnitTest.value", "Z");
            config = new Config(properties);
            config.parseOptionalEmailFormat("UnitTest.value", EmailFormat.PLAINTEXT);
            fail("Expected NotConfiguredException");
        } catch (NotConfiguredException e) { }

        // Property exists and is valid (MULTIPART)
        properties = new Properties();
        properties.setProperty("UnitTest.value", "MULTIPART");
        config = new Config(properties);
        assertEquals(EmailFormat.MULTIPART, config.parseOptionalEmailFormat("UnitTest.value", EmailFormat.PLAINTEXT));

        // Property exists and is valid (PLAINTEXT)
        properties = new Properties();
        properties.setProperty("UnitTest.value", "PLAINTEXT");
        config = new Config(properties);
        assertEquals(EmailFormat.PLAINTEXT, config.parseOptionalEmailFormat("UnitTest.value", EmailFormat.MULTIPART));
    }

    /** Test splitString(). */
    @Test public void testParseStringList() {
        List<String> result = null;

        result = PropertyBasedConfig.splitString(null, null);
        assertTrue(result.isEmpty());

        result = PropertyBasedConfig.splitString("", null);
        assertTrue(result.isEmpty());

        result = PropertyBasedConfig.splitString("whatever", null);
        assertEquals(1, result.size());
        assertEquals("whatever", result.get(0));

        result = PropertyBasedConfig.splitString(null, ",");
        assertTrue(result.isEmpty());

        result = PropertyBasedConfig.splitString("", ",");
        assertTrue(result.isEmpty());

        result = PropertyBasedConfig.splitString("one", ",");
        assertEquals(1, result.size());
        assertEquals("one", result.get(0));

        result = PropertyBasedConfig.splitString("one,", ",");
        assertEquals(1, result.size());
        assertEquals("one", result.get(0));

        result = PropertyBasedConfig.splitString("one,two", ",");
        assertEquals(2, result.size());
        assertEquals("one", result.get(0));
        assertEquals("two", result.get(1));

        result = PropertyBasedConfig.splitString("one,two,", ",");
        assertEquals(2, result.size());
        assertEquals("one", result.get(0));
        assertEquals("two", result.get(1));

        result = PropertyBasedConfig.splitString("one,two,three", ",");
        assertEquals(3, result.size());
        assertEquals("one", result.get(0));
        assertEquals("two", result.get(1));
        assertEquals("three", result.get(2));

        result = PropertyBasedConfig.splitString("one,two,three,", ",");
        assertEquals(3, result.size());
        assertEquals("one", result.get(0));
        assertEquals("two", result.get(1));
        assertEquals("three", result.get(2));

        result = PropertyBasedConfig.splitString(" one , two,    three , ", ",");
        assertEquals(3, result.size());
        assertEquals("one", result.get(0));
        assertEquals("two", result.get(1));
        assertEquals("three", result.get(2));
    }

    /** Test toString(). */
    @Test public void testToString() {
        Config config = new Config();
        config.stringValue = "one";
        config.integerValue = 42;
        assertEquals("\n   Config:\n      integerValue: 42\n      stringValue.: one\n", config.toString());
    }

    /** Concrete bean that can be used for testing. */
    public static class Config extends PropertyBasedConfig {

        private String stringValue;
        private Integer integerValue;

        public Config() {
        }

        public Config(Properties properties) {
            this.setProperties(properties);
        }

        @Override
        public void afterPropertiesSet() throws Exception {
        }

        public String getStringValue() {
            return this.stringValue;
        }

        public Integer getIntegerValue() {
            return this.integerValue;
        }
    }
}
