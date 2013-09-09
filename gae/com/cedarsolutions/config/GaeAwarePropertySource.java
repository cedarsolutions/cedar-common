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

import java.util.Properties;

import com.google.appengine.api.utils.SystemProperty;
import com.google.appengine.api.utils.SystemProperty.Environment;

/**
 * GAE-aware property source which chooses configuration based on where it's being run.
 *
 * <p>
 * The Google Plugin for Eclipse runs and deploys code based on what's in the
 * Eclipse classpath.  There is no (apparent) facility for having different
 * configuration in Eclipse vs. deployed to AppEngine.  This can be a pain,
 * since it's not that unusual to want different defaults.
 * <p>
 *
 * <p>
 * This GWT-aware property source helps simplify that problem. It takes a set
 * of production mode properties, and a set of development mode properties.
 * The set of production mode properties are used by default.  If the server
 * is running in development mode, the development mode properties are used
 * instead.
 * </p>
 *
 * @author Kenneth J. Pronovici <pronovic@ieee.org>
 */
public class GaeAwarePropertySource implements IPropertySource {

    /** Production mode properties. */
    private Properties productionModeProperties;

    /** Development mode properties. */
    private Properties developmentModeProperties;

    /** Get the properties to be used by PropertyBasedConfig. */
    @Override
    public Properties getProperties() {
        if (isProductionMode()) {
            return this.productionModeProperties;
        } else {
            return this.developmentModeProperties;
        }
    }

    /** Whether the server is running in production mode. */
    protected static boolean isProductionMode() {
        return Environment.Value.Production.equals(SystemProperty.environment.value());
    }

    /** Print a legible string describing this source. */
    @Override
    public String toString() {
        return isProductionMode() ? "Production Mode" : "Development Mode";
    }

    public Properties getProductionModeProperties() {
        return this.productionModeProperties;
    }

    public void setProductionModeProperties(Properties productionModeProperties) {
        this.productionModeProperties = productionModeProperties;
    }

    public Properties getDevelopmentModeProperties() {
        return this.developmentModeProperties;
    }

    public void setDevelopmentModeProperties(Properties developmentModeProperties) {
        this.developmentModeProperties = developmentModeProperties;
    }

}
