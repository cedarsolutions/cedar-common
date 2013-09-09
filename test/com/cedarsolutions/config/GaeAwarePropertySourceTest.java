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

import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;

import java.util.Properties;

import org.junit.Test;

/**
 * Unit tests for GaeAwarePropertySource.
 * @author Kenneth J. Pronovici <pronovic@ieee.org>
 */
public class GaeAwarePropertySourceTest {

    /** Test constructor, getters and setters. */
    @Test public void testConstructor() {
        GaeAwarePropertySource source = new GaeAwarePropertySource();
        assertNull(source.getProductionModeProperties());
        assertNull(source.getDevelopmentModeProperties());

        Properties baseProperties = new Properties();
        source.setProductionModeProperties(baseProperties);
        assertSame(baseProperties, source.getProductionModeProperties());

        Properties developmentModeProperties = new Properties();
        source.setDevelopmentModeProperties(developmentModeProperties);
        assertSame(developmentModeProperties, source.getDevelopmentModeProperties());
    }

    /** Test getProperties(), default GAE behavior. */
    @Test public void testGetPropertiesDefaultBehavior() {
        Properties baseProperties = new Properties();
        Properties developmentModeProperties = new Properties();

        GaeAwarePropertySource source = new GaeAwarePropertySource();
        source.setProductionModeProperties(baseProperties);
        source.setDevelopmentModeProperties(developmentModeProperties);

        // local testing will always be non-production).
        assertSame(developmentModeProperties, source.getProperties());
    }

}
