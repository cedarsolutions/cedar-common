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
import static org.junit.Assert.assertNotNull;
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

}
