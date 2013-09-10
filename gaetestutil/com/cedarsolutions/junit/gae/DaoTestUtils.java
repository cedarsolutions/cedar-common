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
package com.cedarsolutions.junit.gae;

import java.util.List;

import org.springframework.core.io.Resource;

import com.cedarsolutions.dao.gae.impl.DaoObjectifyService;
import com.cedarsolutions.dao.gae.impl.ObjectifyServiceProxy;
import com.cedarsolutions.junit.util.TestUtils;
import com.cedarsolutions.util.FilesystemUtils;

/**
 * Utility methods useful for testing DAOs.
 * @author Kenneth J. Pronovici <pronovic@ieee.org>
 */
public class DaoTestUtils {

    /**
     * Create a DaoObjectifyService, configured using the indicated entities file.
     * @param entitiesPath  Path to the entities configuration file on disk
     * @return DaoObjectifyService created based on the contents of the entities file.
     */
    public static DaoObjectifyService createDaoObjectifyService(String entitiesPath) {
        String contents = FilesystemUtils.getFileContentsAsString(entitiesPath);
        Resource entities = TestUtils.createMockedResource(contents);
        return createDaoObjectifyService(entities);
    }

    /**
     * Create a DaoObjectifyService, configured with a list of classes.
     * @param classes  List of classes to register with Objectify
     * @return DaoObjectifyService created based on the contents of the entities file.
     */
    @SuppressWarnings("rawtypes")
    public static DaoObjectifyService createDaoObjectifyService(List<Class> classes) {
        StringBuffer contents = new StringBuffer();
        for (Class clazz : classes) {
            contents.append(clazz.getCanonicalName());
            contents.append("\n");
        }

        Resource entities = TestUtils.createMockedResource(contents.toString());
        return createDaoObjectifyService(entities);
    }

    /**
     * Create a DaoObjectifyService, configured based an entities Resource.
     * @param entities   Entities Resource to be injected into the service
     * @return DaoObjectifyService with the entities Resource injected
     */
    public static DaoObjectifyService createDaoObjectifyService(Resource entities) {
        DaoObjectifyService service = new DaoObjectifyService();
        service.setObjectifyServiceProxy(ObjectifyServiceProxy.getInstance());
        service.setEntities(entities);
        service.afterPropertiesSet();
        return service;
    }

}
