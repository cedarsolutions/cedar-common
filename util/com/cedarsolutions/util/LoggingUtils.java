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

import org.apache.log4j.Logger;

/**
 * Utilities used to standardize logging behavior.
 * @author Kenneth J. Pronovici <pronovic@ieee.org>
 */
public class LoggingUtils {

    /**
     * Retrieve a log4j logger via Logger.getLogger(String).
     * The logger will be standardized to start with the common application-wide prefix.
     * @param name The name of the logger to retrieve.
     */
    public static Logger getLogger(String name) {
        return Logger.getLogger(name);
    }

    /**
     * Retrieve a log4j logger via Logger.getLogger(Class).
     * The logger will be standardized to start with the common application-wide prefix.
     * @param clazz Class of the logger to retrieve
     */
    @SuppressWarnings("rawtypes")
    public static Logger getLogger(Class clazz) {
        return getLogger(clazz.getName());
    }

}
