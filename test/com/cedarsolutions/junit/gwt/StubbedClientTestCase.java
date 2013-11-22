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

import org.junit.BeforeClass;
import org.junit.runner.RunWith;

import com.cedarsolutions.junit.util.TestUtils;

/**
 * Test case for stubbed client testing, rather than relying on GWTTestCase.
 *
 * <p>
 * This test case is intended to be used when testing presenters, event handlers
 * and the like.  It works for views and widgets, but not for all tests, because
 * many of the required classes can't be mocked successfully.  I usually end up
 * with a stubbed client test and a real GWT client test for each view.
 * </p>
 *
 * <p>
 * Speaking of mocking... we've actually been kind of lucky so far, because
 * it is possible to mock the Widget class using Mockito, and that's the main
 * client class that presenters need to interact with.  If Google ever screws
 * with the definition for Widget (i.e. makes it final, or makes some of its
 * methods final), we're going to have problems.  We can partially work around
 * this by trying to build our own interfaces using IsWidget rather than Widget,
 * but that doesn't help when extending classes like Panel (which uses the two
 * rather interchangeably).
 * </p>
 *
 * @author Kenneth J. Pronovici <pronovic@ieee.org>
 */
@RunWith(GwtStubbedTestRunner.class)
public abstract class StubbedClientTestCase {

    @BeforeClass
    public static void setUpGaeLogging() {
        // The datastore writes lots of crap to the log, which we can't control otherwise
        TestUtils.setUpGaeLogging();
    }

}
