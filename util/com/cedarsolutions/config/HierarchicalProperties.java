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

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.core.io.Resource;

import com.cedarsolutions.exception.NotConfiguredException;

/**
 * Hierarchical properties, where properties from the environment
 * override properties from the classpath.
 *
 * <p>
 * This is intended for use with Spring.  Properties from the war file (the
 * "classpath" properties) will be loaded first, and then those properties can
 * be overridden one-by-one using properties loaded from the environment
 * resource.  The general idea is to make the environment-specific properties
 * files very small.  They can include only what's different on a per
 * environment basis, rather than having to include all the properties, even
 * the ones that don't change.
 * </p>
 *
 * <p>
 * To use it, configure a stanza in your <code>applicationContext.xml</code>
 * file:
 * </p>
 *
 * <pre>
 * &lt;bean name="applicationProperties" class="com.cedarsolutions.config.HierarchicalProperties"&gt;
 *    &lt;property name="classpathResource" value="classpath:application.properties" /&gt;
 *    &lt;property name="environmentResource" value="${app.properties}" /&gt;
 * &lt;/bean&gt;
 * </pre>
 *
 * <p>
 * As shown above, the <code>application.properties</code> file must exist in
 * your war file in the default package.  That file provides the defaults.
 * Then, you set a system property in your container called <code>app.properties</code>
 * that tells Spring where the environment properties file lives on the filesystem.
 * </p>
 *
 * <p>
 * Then, just inject the hierarchical properties into your configuration objects
 * like any other properties that you might have:
 * </p>
 *
 * <pre>
 * &lt;bean id="bugReportServiceConfig" class="com.cedarsolutions.santa.server.config.BugReportServiceConfig"&gt;
 *    &lt;property name="properties" ref="applicationProperties" /&gt;
 * &lt;/bean&gt;
 * </pre>
 *
 * <p>
 * If the <code>logContents</code> parameter is set to true, the resulting
 * properties will be logged after they're loaded.
 * </p>
 *
 * <p>
 * Note that the <code>app.properties</code> path is a <code>file://</code>
 * URL.  On some platforms, you may need to play with it to make it work
 * properly.  For instance, on Websphere running on Windows, you need a URL
 * with exactly 3 slashes, like <code>file:///c:/path/to/file.properties</code>.
 * </p>
 *
 * @author Kenneth J. Pronovici <pronovic@ieee.org>
 */
@SuppressWarnings("serial")
public class HierarchicalProperties extends Properties implements InitializingBean {

    /** Name of the configuration that is being loaded, for logging. */
    private String configName;

    /** Whether to log configuration contents. */
    private boolean logContents;

    /** Class path resource to use. */
    private Resource classpathResource;

    /** Environment resource to use. */
    private Resource environmentResource;

    /** Log4j logger. */
    private Logger LOGGER = Logger.getLogger(HierarchicalProperties.class);

    /**
     * Invoked by a bean factory after it has set all bean properties.
     * @throws NotConfiguredException In the event of misconfiguration.
     */
    @Override
    public void afterPropertiesSet() throws NotConfiguredException {
        try {
            Properties classpathProperties = new Properties();
            if (this.classpathResource != null && this.classpathResource.exists()) {
                LOGGER.debug("Classpath properties from: " + this.classpathResource.getURL());
                classpathProperties.load(this.classpathResource.getInputStream());
            }

            Properties environmentProperties = new Properties();
            if (this.environmentResource != null && this.environmentResource.exists()) {
                LOGGER.debug("Environment properties from: " + this.environmentResource.getURL());
                environmentProperties.load(this.environmentResource.getInputStream());
            }

            this.putAll(classpathProperties);
            this.putAll(environmentProperties);

            if (this.logContents) {
                LOGGER.info(this.toString());
            }
        } catch (IOException e) {
            throw new NotConfiguredException("Error loading properties: " + e.getMessage(), e);
        }
    }

    /** Give Spring a getter to use when referencing values in applicationContext.xml. */
    public Properties getValues() {
        return this;
    }

    /**
     * String representation of this object.
     * This is kind of ridiculously complicated, but I wanted the output to be readable.
     */
    @Override
    public String toString() {
        StringBuffer buffer = new StringBuffer();

        if (this.configName != null) {
            buffer.append("\n   " + this.configName + ":\n");
        } else {
            buffer.append("\n   HierarchicalProperties:\n");
        }

        int maxKey = 0;
        List<String> keyList = new ArrayList<String>();
        for (Object key : this.keySet()) {
            String keyStr = (String) key;
            keyList.add(keyStr);
            if (keyStr.length() > maxKey) {
                maxKey = keyStr.length();
            }
        }

        Collections.sort(keyList);

        for (String key : keyList) {
            buffer.append("      ");
            buffer.append(key);
            for (int i = 0; i < maxKey - key.length(); i++) {
                buffer.append(".");
            }
            buffer.append(": ");
            buffer.append(this.get(key));
            buffer.append("\n");
        }

        return buffer.toString();
    }

    public String getConfigName() {
        return configName;
    }

    public void setConfigName(String configName) {
        this.configName = configName;
    }

    public boolean getLogContents() {
        return logContents;
    }

    public void setLogContents(boolean logContents) {
        this.logContents = logContents;
    }

    public Resource getClasspathResource() {
        return this.classpathResource;
    }

    public void setClasspathResource(Resource classpathResource) {
        this.classpathResource = classpathResource;
    }

    public Resource getEnvironmentResource() {
        return this.environmentResource;
    }

    public void setEnvironmentResource(Resource environmentResource) {
        this.environmentResource = environmentResource;
    }

}
