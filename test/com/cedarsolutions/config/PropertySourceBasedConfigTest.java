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
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Properties;

import org.junit.Test;

/**
 * Unit tests for PropertySourceBasedConfig.
 * @author Kenneth J. Pronovici <pronovic@ieee.org>
 */
public class PropertySourceBasedConfigTest {

    /** Test constructor, getters and setters. */
    @Test public void testConstructor() {
        PropertySourceBasedConfig config = new PropertySourceBasedConfig();
        assertNull(config.getPropertySource());

        IPropertySource propertySource = mock(IPropertySource.class);
        config.setPropertySource(propertySource);
        assertSame(propertySource, config.getPropertySource());
    }

    /** Test afterPropertiesSet(). */
    @Test public void testAfterPropertiesSet() {
        Properties properties = new Properties();

        IPropertySource propertySource = mock(IPropertySource.class);
        PropertySourceBasedConfig config = new PropertySourceBasedConfig();
        config.setPropertySource(propertySource);

        when(propertySource.getProperties()).thenReturn(properties);

        config.afterPropertiesSet();
        assertSame(properties, config.getProperties());
    }

}
