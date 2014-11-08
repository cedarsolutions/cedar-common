/* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 *
 *              C E D A R
 *          S O L U T I O N S       "Software done right."
 *           S O F T W A R E
 *
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 *
 * Copyright (c) 2013-2014 Kenneth J. Pronovici.
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
package com.cedarsolutions.dao.gae.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.IOUtils;
import org.springframework.core.io.Resource;

import com.cedarsolutions.dao.gae.IDaoObjectifyService;
import com.cedarsolutions.exception.CedarRuntimeException;
import com.cedarsolutions.exception.NotConfiguredException;
import com.cedarsolutions.server.service.impl.AbstractService;
import com.cedarsolutions.util.StringUtils;

/**
 * Provides access to the Objectify infrastructure.
 * @author Kenneth J. Pronovici <pronovic@ieee.org>
 */
public class DaoObjectifyService extends AbstractService implements IDaoObjectifyService {

    /** Objectify service proxy to use. */
    private ObjectifyServiceProxy objectifyServiceProxy;

    /** Class path resource to use. */
    private Resource entities;

    /**
     * Invoked by a bean factory after it has set all bean properties.
     * @throws NotConfiguredException In the event of misconfiguration.
     */
    @Override
    public void afterPropertiesSet() throws NotConfiguredException {
        super.afterPropertiesSet();
        if (this.objectifyServiceProxy == null || this.entities == null) {
            throw new NotConfiguredException("DaoObjectifyService is not properly configured.");
        }

        this.registerObjectifyEntities();
    }

    /** Get an Objectify object for a DAO to operate on. */
    @Override
    public ObjectifyProxy getObjectify() {
        return new ObjectifyProxy(this.objectifyServiceProxy.begin(), false);
    }

    /** Get an Objectify object with an active transaction. */
    @Override
    public ObjectifyProxy getObjectifyWithTransaction() {
        return new ObjectifyProxy(this.objectifyServiceProxy.beginTransaction(), true);
    }

    /** Register entities with Objectify. */
    @SuppressWarnings("rawtypes")
    private void registerObjectifyEntities() {
        List<Class> classes = parseEntities(this.entities);
        if (classes.isEmpty()) {
            throw new NotConfiguredException("DaoObjectifyService did not register any classes.  Is entities file configured properly?");
        }

        for (Class clazz : classes) {
            this.objectifyServiceProxy.register(clazz);
        }
    }

    /** Parse the entities configuration file. */
    @SuppressWarnings("rawtypes")
    protected static List<Class> parseEntities(Resource entities) {
        try {
            List<Class> classes = new ArrayList<Class>();

            if (entities.exists()) {
                for (String line : (List<String>) IOUtils.readLines(entities.getInputStream())) {
                    String parsed = line.replaceFirst("^\\s*#.*$", "");  // strip comment lines
                    parsed = StringUtils.trimToNull(parsed);             // strip empty lines
                    if (parsed != null) {
                        Class clazz = Class.forName(parsed);             // anything else is a class name
                        classes.add(clazz);
                    }
                }
            }

            return classes;
        } catch (ClassNotFoundException e) {
            throw new CedarRuntimeException("Class was not found: " + e.getMessage(), e);
        } catch (Exception e) {
            throw new CedarRuntimeException("Failed to parse input entities file: " + e.getMessage(), e);
        }
    }

    public ObjectifyServiceProxy getObjectifyServiceProxy() {
        return this.objectifyServiceProxy;
    }

    public void setObjectifyServiceProxy(ObjectifyServiceProxy objectifyServiceProxy) {
        this.objectifyServiceProxy = objectifyServiceProxy;
    }

    public Resource getEntities() {
        return this.entities;
    }

    public void setEntities(Resource entities) {
        this.entities = entities;
    }

}
