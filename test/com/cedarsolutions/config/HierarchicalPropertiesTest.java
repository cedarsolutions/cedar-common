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
package com.cedarsolutions.config;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URL;
import java.util.Properties;

import org.junit.Test;
import org.springframework.core.io.Resource;

import com.cedarsolutions.exception.NotImplementedException;

/**
 * Unit tests for HierarchicalProperties.
 * @author Kenneth J. Pronovici <pronovic@ieee.org>
 */
public class HierarchicalPropertiesTest {

    /** Test constructor, getters and setters. */
    @Test public void testConstructor() {
        Resource classpathResource = new PropertiesResource(null);
        Resource environmentResource = new PropertiesResource(null);

        HierarchicalProperties properties = new HierarchicalProperties();
        properties.toString(); // make sure it works for empty object

        properties.setLogContents(true);
        properties.setConfigName("name");
        properties.setClasspathResource(classpathResource);
        properties.setEnvironmentResource(environmentResource);
        properties.toString(); // make sure it works for non-empty object

        assertTrue(properties.getLogContents());
        assertEquals("name", properties.getConfigName());
        assertSame(properties, properties.getValues());
        assertSame(classpathResource, properties.getClasspathResource());
        assertSame(environmentResource, properties.getEnvironmentResource());
    }

    /** Test afterPropertiesSet() when both resources are unset. */
    @Test public void testAfterPropertiesSet1() {
        Properties classpathProperties = null;
        Properties environmentProperties = null;
        HierarchicalProperties properties = getProperties(classpathProperties, environmentProperties);
        assertEquals(null, properties.get("MyConfig.one"));
        assertEquals(null, properties.get("MyConfig.two"));
        assertEquals(null, properties.get("MyConfig.three"));
    }

    /** Test afterPropertiesSet() when only application properties are set. */
    @Test public void testAfterPropertiesSet2() {
        Properties classpathProperties = buildClasspathProperties();
        Properties environmentProperties = null;
        HierarchicalProperties properties = getProperties(classpathProperties, environmentProperties);
        assertEquals("AAAA", properties.get("MyConfig.one"));
        assertEquals("2000", properties.get("MyConfig.two"));
        assertEquals(null, properties.get("MyConfig.three"));
    }

    /** Test afterPropertiesSet() when only environment properties are set. */
    @Test public void testAfterPropertiesSet3() {
        Properties classpathProperties = null;
        Properties environmentProperties = buildEnvironmentProperties();
        HierarchicalProperties properties = getProperties(classpathProperties, environmentProperties);
        assertEquals("BBBB", properties.get("MyConfig.one"));
        assertEquals(null, properties.get("MyConfig.two"));
        assertEquals("1000", properties.get("MyConfig.three"));
    }

    /** Test afterPropertiesSet() when both resources are set (check override behavior when both values are set). */
    @Test public void testAfterPropertiesSet4() {
        Properties classpathProperties = buildClasspathProperties();
        Properties environmentProperties = buildEnvironmentProperties();
        HierarchicalProperties properties = getProperties(classpathProperties, environmentProperties);
        assertEquals("BBBB", properties.get("MyConfig.one"));
        assertEquals("2000", properties.get("MyConfig.two"));
        assertEquals("1000", properties.get("MyConfig.three"));
    }

    /** Test afterPropertiesSet() when both resources are set (check override behavior when classpath value is unset). */
    @Test public void testAfterPropertiesSet5() {
        Properties classpathProperties = buildClasspathPropertiesEmpty();
        Properties environmentProperties = buildEnvironmentProperties();
        HierarchicalProperties properties = getProperties(classpathProperties, environmentProperties);
        assertEquals("BBBB", properties.get("MyConfig.one"));
        assertEquals("2000", properties.get("MyConfig.two"));
        assertEquals("1000", properties.get("MyConfig.three"));
    }

    /** Test afterPropertiesSet() when both resources are set (check override behavior when environment value is unset). */
    @Test public void testAfterPropertiesSet6() {
        Properties classpathProperties = buildClasspathProperties();
        Properties environmentProperties = buildEnvironmentPropertiesEmpty();
        HierarchicalProperties properties = getProperties(classpathProperties, environmentProperties);
        assertEquals("", properties.get("MyConfig.one"));
        assertEquals("2000", properties.get("MyConfig.two"));
        assertEquals("1000", properties.get("MyConfig.three"));
    }

    private static HierarchicalProperties getProperties(Properties classpathProperties, Properties environmentProperties) {
        Resource classpathResource = new PropertiesResource(classpathProperties);
        Resource environmentResource = new PropertiesResource(environmentProperties);
        HierarchicalProperties properties = new HierarchicalProperties();
        properties.setClasspathResource(classpathResource);
        properties.setEnvironmentResource(environmentResource);
        properties.afterPropertiesSet();
        return properties;
    }

    private static Properties buildClasspathProperties() {
        Properties properties = new Properties();
        properties.put("MyConfig.one", "AAAA");
        properties.put("MyConfig.two", "2000");
        return properties;
    }

    private static Properties buildEnvironmentProperties() {
        Properties properties = new Properties();
        properties.put("MyConfig.one", "BBBB");
        properties.put("MyConfig.three", "1000");
        return properties;
    }

    private static Properties buildClasspathPropertiesEmpty() {
        Properties properties = new Properties();
        properties.put("MyConfig.one", "");
        properties.put("MyConfig.two", "2000");
        return properties;
    }

    private static Properties buildEnvironmentPropertiesEmpty() {
        Properties properties = new Properties();
        properties.put("MyConfig.one", "");
        properties.put("MyConfig.three", "1000");
        return properties;
    }

    /** Resource class to hold properties for testing. */
    private static class PropertiesResource implements Resource {

        private Properties properties;

        public PropertiesResource(Properties properties) {
            this.properties = properties;
        }

        @Override
        public boolean exists() {
            return this.properties != null;
        }

        @Override
        public InputStream getInputStream() throws IOException {
            ByteArrayOutputStream output = new ByteArrayOutputStream();
            this.properties.store(output, "Properties");
            return new ByteArrayInputStream(output.toByteArray());
        }

        @Override
        public Resource createRelative(String arg0) throws IOException {
            throw new NotImplementedException("createRelative");
        }

        @Override
        public String getDescription() {
            throw new NotImplementedException("getDescription");
        }

        @Override
        public File getFile() throws IOException {
            throw new NotImplementedException("getFile");
        }

        @Override
        public String getFilename() {
            throw new NotImplementedException("getFilename");
        }

        @Override
        public URI getURI() throws IOException {
            throw new NotImplementedException("getURI");
        }

        @Override
        public URL getURL() throws IOException {
            return new URL("file://whatever");
        }

        @Override
        public boolean isOpen() {
            throw new NotImplementedException("isOpen");
        }

        @Override
        public boolean isReadable() {
            throw new NotImplementedException("isReadable");
        }

        @Override
        public long lastModified() throws IOException {
            throw new NotImplementedException("lastModified");
        }

        @Override
        public long contentLength() throws IOException {
            throw new NotImplementedException("contentLength");
        }
    }

}
