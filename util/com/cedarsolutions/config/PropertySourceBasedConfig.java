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

import com.cedarsolutions.exception.NotConfiguredException;

/**
 * Property-based configuration object that gets properties from an IPropertySource.
 * @author Kenneth J. Pronovici <pronovic@ieee.org>
 */
public class PropertySourceBasedConfig extends PropertyBasedConfig {

    /** Property source. */
    private IPropertySource propertySource;

    /**
     * Invoked by a bean factory after it has set all bean properties.
     * @throws NotConfiguredException In the event of misconfiguration.
     */
    @Override
    public void afterPropertiesSet() throws NotConfiguredException {
        if (this.propertySource != null) {
            this.setProperties(this.propertySource.getProperties());
        }
    }

    public IPropertySource getPropertySource() {
        return this.propertySource;
    }

    public void setPropertySource(IPropertySource propertySource) {
        this.propertySource = propertySource;
    }

}
