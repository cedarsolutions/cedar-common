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
package com.cedarsolutions.util;

import static com.cedarsolutions.util.StringUtils.substring;

import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import com.cedarsolutions.exception.InvalidDataException;

/**
 * Utilities for working with Java Properties.
 * @author Kenneth J. Pronovici <pronovic@ieee.org>
 */
public class PropertiesUtils {

    /**
     * Load Java properties from disk.
     * @param paths   Paths of the Java properties files to process, in the desired order
     * @return Properties object filled in with all properties from the passed-in files.
     * @throws InvalidDataException  If properties could not be loaded.
     */
    public static Properties loadProperties(String... paths) {
        Properties properties = new Properties();

        for (String path : paths) {
            FileInputStream stream = null;
            try {
                stream = new FileInputStream(path);
                properties.load(stream);
            } catch (Exception e) {
                throw new InvalidDataException("Properties could not be loaded: " + e.getMessage(), e);
            } finally {
                if (stream != null) {
                    try {
                        stream.close();
                    } catch (Exception e) { }
                }
            }
        }

        return properties;
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
    public static List<String> getMatchingPrefixes(Properties properties, String parentPrefix) {
        List<String> matches = new ArrayList<String>();

        if (properties != null) {
            for (Object key : properties.keySet()) {
                String name = (String) key;
                if (name.startsWith(parentPrefix + ".")) {
                    String remaining = substring(name, (parentPrefix + ".").length());
                    if (remaining.indexOf(".") != -1) {
                        String prefix = parentPrefix + "." + substring(remaining, 0, remaining.indexOf("."));
                        if (!matches.contains(prefix)) {
                            matches.add(prefix);
                        }
                    }
                }
            }
        }

        return matches;
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
    public static List<String> getMatchingChildren(Properties properties, String parentPrefix) {
        List<String> matches = new ArrayList<String>();

        if (properties != null) {
            for (Object key : properties.keySet()) {
                String name = (String) key;
                if (name.startsWith(parentPrefix + ".")) {
                    String remaining = substring(name, (parentPrefix + ".").length());
                    String match = parentPrefix + "." + remaining.replaceFirst("\\..*$", "");
                    if (!matches.contains(match)) {
                        matches.add(match);
                    }
                }
            }
        }

        return matches;
    }

}
