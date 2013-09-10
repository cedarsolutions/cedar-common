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
package com.cedarsolutions.junit.util;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.springframework.core.io.Resource;

import com.cedarsolutions.exception.CedarRuntimeException;
import com.cedarsolutions.util.FilesystemUtils;
import com.cedarsolutions.util.StringUtils;

/**
 * Useful test utility methods and definitions.
 * @author Kenneth J. Pronovici <pronovic@ieee.org>
 */
public class TestUtils {

    /** Set up logging for GAE, so log messages go to Log4J. */
    public static void setUpGaeLogging() {
        // Normally, this is done in appengine-web.xml, but tests need to set it for themselves
        if (StringUtils.isEmpty(System.getProperty("java.util.logging.config.file"))) {
            if (FilesystemUtils.fileExists("war/WEB-INF/logging.properties")) {
                System.setProperty("java.util.logging.config.file", "war/WEB-INF/logging.properties");
            } else if (FilesystemUtils.fileExists("resources/logging.properties")) {
                System.setProperty("java.util.logging.config.file", "resources/logging.properties");
            }
        }
    }

    /**
     * Create a mocked Spring Resource based on a String.
     * This is particularly useful when testing items that take Spring-based configuration.
     * As a special case, a null string results in a null input stream.
     * @param contents  String containing the contents of the Resource
     * @return Mocked Resource containing the contents of the String.
     */
    public static Resource createMockedResource(String contents) {
        try {
            Resource entities = mock(Resource.class);
            when(entities.exists()).thenReturn(true);

            if (contents == null) {
                when(entities.getInputStream()).thenReturn((InputStream) null);
            } else {
                InputStream inputStream = new ByteArrayInputStream(contents.getBytes());
                when(entities.getInputStream()).thenReturn(inputStream);
            }

            return entities;
        } catch (IOException e) {
            throw new CedarRuntimeException("Error creating Resource: " + e.getMessage(), e);
        }
    }

}
