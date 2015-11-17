/* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 *
 *              C E D A R
 *          S O L U T I O N S       "Software done right."
 *           S O F T W A R E
 *
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 *
 * Copyright (c) 2014-2015 Kenneth J. Pronovici.
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

import java.util.List;
import java.util.Properties;

import com.cedarsolutions.exception.InvalidDataException;
import com.cedarsolutions.server.service.IPropertiesService;
import com.cedarsolutions.util.PropertiesUtils;

/**
 * Service wrapper around PropertiesUtils.
 * @author Kenneth J. Pronovici <pronovic@ieee.org>
 */
public class PropertiesService extends AbstractService implements IPropertiesService {

    /**
     * Load Java properties from disk.
     * @param paths   Paths of the Java properties files to process, in the desired order
     * @return Properties object filled in with all properties from the passed-in files.
     * @throws InvalidDataException  If properties could not be loaded.
     */
    @Override
    public Properties loadProperties(String... paths) throws InvalidDataException {
        return PropertiesUtils.loadProperties(paths);
    }

    /**
     * Get a list of existing prefixes which match the passed-in prefix one level down.
     *
     * <p>
     * For instance, if prefix is "database" and the properties object contains properties
     * like "database.production.x.y" and "database.integration.x.y.", the returned list
     * will contain "database.production" and "database.integration".
     * </p>
     *
     * @param properties   Properties to look in
     * @param parentPrefix Parent property prefix to use as starting point
     *
     * @return List of prefixes that match the passed-in prefix, always non-null.
     */
    @Override
    public List<String> getMatchingPrefixes(Properties properties, String parentPrefix) {
        return PropertiesUtils.getMatchingPrefixes(properties, parentPrefix);
    }

    /**
     * Get a list of children which have a given parent prefix.
     *
     * <p>
     * We only find children that are one level down, i.e. if prefix is "yy.file" then we
     * will find "yy.file.A", "yy.file.B", but not "yy.file.B.C".
     * </p>
     *
     * @param properties   Properties to look in
     * @param parentPrefix Parent property prefix to use as starting point
     *
     * @return List of children that match the passed-in parent prefix, always non-null.
     */
    @Override
    public List<String> getMatchingChildren(Properties properties, String parentPrefix) {
        return PropertiesUtils.getMatchingChildren(properties, parentPrefix);
    }

}
