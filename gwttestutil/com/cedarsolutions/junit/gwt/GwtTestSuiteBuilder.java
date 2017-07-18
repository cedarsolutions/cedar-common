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
package com.cedarsolutions.junit.gwt;

import java.io.File;
import java.util.List;

import junit.framework.Test;
import junit.framework.TestSuite;
import junitx.util.DirectorySuiteBuilder;
import junitx.util.SimpleTestFilter;

import com.google.gwt.junit.client.GWTTestCase;

/**
 * Suite builder for GWT test cases.
 * @author Kenneth J. Pronovici <pronovic@ieee.org>
 */
@SuppressWarnings("rawtypes")
public class GwtTestSuiteBuilder extends DirectorySuiteBuilder {

    /** Create a suite builder configured with the proper type of filter. */
    public GwtTestSuiteBuilder() {
        this.setFilter(new SimpleTestFilter() {
            @Override
            public boolean include(Class clazz) {
                return GWTTestCase.class.isAssignableFrom(clazz);
            }
        });
    }

    /**
     * Generate a suite.
     * @param suiteName    Name of the suite
     * @param directories  Directories that should be scanned for classes
     * @return JUnit test suite generated from the indicated directories
     * @throws Exception If there is a problem generating the suite.
     */
    public static Test generateSuite(String suiteName, String... directories) throws Exception {
        TestSuite suite = new TestSuite(suiteName);

        GwtTestSuiteBuilder builder = new GwtTestSuiteBuilder();
        for (String directoryName : directories) {
            File directory = new File(directoryName);
            List classnames = builder.browse(directory);
            builder.merge(classnames, suite);
        }

        return suite;
    }

}
