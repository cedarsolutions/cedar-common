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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import com.cedarsolutions.exception.InvalidDataException;

/**
 * Unit tests for PropertiesUtils.
 * @author Kenneth J. Pronovici <pronovic@ieee.org>
 */
public class PropertiesUtilsTest {

    /** Create the build/tmp/PropertiesUtilsTest directory before running tests. */
    @BeforeClass
    public static void createTempDir() {
        FilesystemUtils.createDir("build/tmp/PropertiesUtilsTest");
    }

    /** Remove the build/tmp/PropertiesUtilsTest directory before running tests. */
    @AfterClass
    public static void removeTempDir() {
        FilesystemUtils.removeEmptyDir("build/tmp/PropertiesUtilsTest");
    }

    /** Test loadProperties() for valid properties. */
    @Test public void testLoadPropertiesValid() throws Exception {
        try {
            FilesystemUtils.removeFile("build/tmp/PropertiesUtilsTest/valid.properties");

            try {
                PropertiesUtils.loadProperties("build/tmp/PropertiesUtilsTest/valid.properties");
                fail("Expected InvalidDataException");
            } catch (InvalidDataException e) { }

            List<String> lines = new ArrayList<String>();
            lines.add("instance.instanceName=instance");
            FilesystemUtils.writeFileContents("build/tmp/PropertiesUtilsTest/valid.properties", lines);

            Properties properties = PropertiesUtils.loadProperties("build/tmp/PropertiesUtilsTest/valid.properties");
            assertNotNull(properties);
            assertEquals("instance", properties.get("instance.instanceName"));  // just spot-check
        } finally {
            FilesystemUtils.removeFile("build/tmp/PropertiesUtilsTest/valid.properties");
        }
    }

    /** Test loadProperties() for invalid properties. */
    @Test public void testLoadPropertiesInvalid() throws Exception {
        try {
            FilesystemUtils.removeFile("build/tmp/PropertiesUtilsTest/invalid.properties");

            List<String> lines = new ArrayList<String>();
            lines.add("\\uZXXASA");  // illegal unicode escape sequence means properties can't be parsed
            FilesystemUtils.writeFileContents("build/tmp/PropertiesUtilsTest/invalid.properties", lines);

            try {
                PropertiesUtils.loadProperties("build/tmp/PropertiesUtilsTest/invalid.properties");
                fail("Expected InvalidDataException");
            } catch (InvalidDataException e) { }
        } finally {
            FilesystemUtils.removeFile("build/tmp/PropertiesUtilsTest/invalid.properties");
        }
    }

    /** Test getMatchingPrefixes() for null properties. */
    public void testGetMatchingPrefixesNull() {
        Properties properties = null;
        List<String> matches = PropertiesUtils.getMatchingPrefixes(properties, "prefix");
        assertTrue(matches.isEmpty());
    }

    /** Test getMatchingPrefixes() for empty properties. */
    public void testGetMatchingPrefixesEmpty() {
        Properties properties = new Properties();
        List<String> matches = PropertiesUtils.getMatchingPrefixes(properties, "prefix");
        assertTrue(matches.isEmpty());
    }

    /** Test getMatchingPrefixes() for properties where nothing matches the prefix. */
    public void testGetMatchingPrefixesNoMatch() {
        Properties properties = createProperties();
        List<String> matches = PropertiesUtils.getMatchingPrefixes(properties, "prefix");
        assertTrue(matches.isEmpty());
    }

    /** Test getMatchingPrefixes() for properties where non-parent matches the prefix. */
    public void testGetMatchingPrefixesNonParent() {
        Properties properties = createProperties();
        List<String> matches = PropertiesUtils.getMatchingPrefixes(properties, "b");
        assertTrue(matches.isEmpty());
    }

    /** Test getMatchingPrefixes() for properties where a single parent matches the prefix. */
    public void testGetMatchingPrefixesSingleParent() {
        Properties properties = createProperties();
        List<String> matches = PropertiesUtils.getMatchingPrefixes(properties, "c");
        assertFalse(matches.isEmpty());
        assertEquals(1, matches.size());
        assertEquals("c.one", matches.get(0));
    }

    /** Test getMatchingPrefixes() for properties where a multiple parents match the prefix. */
    public void testGetMatchingPrefixesMultipleParents() {
        Properties properties = createProperties();
        List<String> matches = PropertiesUtils.getMatchingPrefixes(properties, "a");
        assertFalse(matches.isEmpty());
        assertEquals(2, matches.size());
        assertTrue(matches.contains("a.two"));
        assertTrue(matches.contains("a.three"));
    }

    /** Test getMatchingPrefixes() for properties where parents and non-parents match the prefix. */
    public void testGetMatchingPrefixesVarious() {
        Properties properties = createProperties();
        List<String> matches = PropertiesUtils.getMatchingPrefixes(properties, "starts.with.d");
        assertFalse(matches.isEmpty());
        assertEquals(2, matches.size());
        assertTrue(matches.contains("starts.with.d.two"));
        assertTrue(matches.contains("starts.with.d.three"));
    }

    /** Test getMatchingChildren() for null properties. */
    public void testGetMatchingChildrenNull() {
        Properties properties = null;
        List<String> matches = PropertiesUtils.getMatchingChildren(properties, "prefix");
        assertTrue(matches.isEmpty());
    }

    /** Test getMatchingChildren() for empty properties. */
    public void testGetMatchingChildrenEmpty() {
        Properties properties = new Properties();
        List<String> matches = PropertiesUtils.getMatchingChildren(properties, "prefix");
        assertTrue(matches.isEmpty());
    }

    /** Test getMatchingChildren() for properties where nothing matches the prefix. */
    public void testGetMatchingChildrenNoMatch() {
        Properties properties = createProperties();
        List<String> matches = PropertiesUtils.getMatchingChildren(properties, "prefix");
        assertTrue(matches.isEmpty());
    }

    /** Test getMatchingChildren() for properties where non-parent matches the prefix. */
    public void testGetMatchingChildrenNonParent() {
        Properties properties = createProperties();
        List<String> matches = PropertiesUtils.getMatchingChildren(properties, "b");
        assertFalse(matches.isEmpty());
        assertEquals(1, matches.size());
        assertEquals("b.one", matches.get(0));
    }

    /** Test getMatchingChildren() for properties where a single parent matches the prefix. */
    public void testGetMatchingChildrenSingleParent() {
        Properties properties = createProperties();
        List<String> matches = PropertiesUtils.getMatchingChildren(properties, "c");
        assertFalse(matches.isEmpty());
        assertEquals(1, matches.size());
        assertEquals("c.one", matches.get(0));
    }

    /** Test getMatchingChildren() for properties where a multiple parents match the prefix. */
    public void testGetMatchingChildrenMultipleParents() {
        Properties properties = createProperties();
        List<String> matches = PropertiesUtils.getMatchingChildren(properties, "a");
        assertFalse(matches.isEmpty());
        assertEquals(2, matches.size());
        assertTrue(matches.contains("a.two"));
        assertTrue(matches.contains("a.three"));
    }

    /** Test getMatchingChildren() for properties where parents and non-parents match the prefix. */
    public void testGetMatchingChildrenVarious() {
        Properties properties = createProperties();
        List<String> matches = PropertiesUtils.getMatchingChildren(properties, "starts.with.d");
        assertFalse(matches.isEmpty());
        assertEquals(3, matches.size());
        assertTrue(matches.contains("starts.with.d.one"));
        assertTrue(matches.contains("starts.with.d.two"));
        assertTrue(matches.contains("starts.with.d.three"));
    }

    /** Create properties for testing. */
    private static Properties createProperties() {
        Properties properties = new Properties();

        properties.put("a.two.1", "a21");
        properties.put("a.two.2", "a21");
        properties.put("a.three.1", "a31");
        properties.put("b.one", "b1");
        properties.put("c.one.1", "c11");
        properties.put("starts.with.d.one", "d1");
        properties.put("starts.with.d.two.1", "d21");
        properties.put("starts.with.d.two.2", "d21");
        properties.put("starts.with.d.three.1", "d31");

        return properties;
    }

}
