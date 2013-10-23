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

import java.io.FileInputStream;
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

}
