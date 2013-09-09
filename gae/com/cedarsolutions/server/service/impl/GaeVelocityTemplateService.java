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
package com.cedarsolutions.server.service.impl;

import java.io.IOException;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.core.io.Resource;

import com.cedarsolutions.exception.NotConfiguredException;
import com.cedarsolutions.server.service.ITemplateService;
import com.cedarsolutions.util.FilesystemUtils;
import com.cedarsolutions.util.LoggingUtils;
import com.cedarsolutions.util.gae.GaeVelocityUtils;

/**
 * Velocity-based template service for use with Google App Engine.
 * @author Kenneth J. Pronovici <pronovic@ieee.org>
 */
public class GaeVelocityTemplateService extends AbstractService implements ITemplateService {

    /** Logger instance. */
    private static Logger LOGGER = LoggingUtils.getLogger(GaeVelocityTemplateService.class);

    /** GAE Velocity utils. */
    private GaeVelocityUtils gaeVelocityUtils;

    /** Template directory resource. */
    private Resource templateDirResource;

    /** Template directory. */
    private String templateDir;

    /**
     * Invoked by a bean factory after it has set all bean properties.
     * @throws NotConfiguredException In the event of misconfiguration.
     */
    @Override
    public void afterPropertiesSet() throws NotConfiguredException {
        super.afterPropertiesSet();
        if (this.gaeVelocityUtils == null || this.templateDirResource == null) {
            throw new NotConfiguredException("GaeVelocityTemplateService is not properly configured.");
        }

        if (!this.templateDirResource.exists()) {
            throw new NotConfiguredException("Configured template directory does not exist.");
        }

        try {
            this.templateDir = this.templateDirResource.getFile().getPath();
            LOGGER.debug("Velocity template directory is: " + this.templateDir);
        } catch (IOException e) {
            throw new NotConfiguredException("Unable to get templates path: " + e.getMessage(), e);
        }
    }

    /**
     * Render an identified template.
     * @param group          Template group, like "notifications"
     * @param name           Template name, like "register"
     * @param component      Template component, like "subject.vm"
     * @param context        Context to use when rendering template
     * @return Rendered template, as a String.
     */
    @Override
    public String renderTemplate(String group, String name, String component, Map<String, Object> context) {
        String template = FilesystemUtils.join(group, name, component);
        return this.gaeVelocityUtils.renderTemplate(this.templateDir, template, context);
    }

    public String getTemplateDir() {
        return this.templateDir;
    }

    public GaeVelocityUtils getGaeVelocityUtils() {
        return this.gaeVelocityUtils;
    }

    public void setGaeVelocityUtils(GaeVelocityUtils gaeVelocityUtils) {
        this.gaeVelocityUtils = gaeVelocityUtils;
    }

    public Resource getTemplateDirResource() {
        return this.templateDirResource;
    }

    public void setTemplateDirResource(Resource templateDirResource) {
        this.templateDirResource = templateDirResource;
    }

}
