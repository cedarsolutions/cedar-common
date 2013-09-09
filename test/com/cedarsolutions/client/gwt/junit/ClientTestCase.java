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
package com.cedarsolutions.client.gwt.junit;

import com.google.gwt.junit.client.GWTTestCase;

/**
 * Test case that all client tests inherit from.
 *
 * <p>
 * When invoked, client-side tests use a customized JUnit test runner
 * provided by Google.  The test runner starts up a servlet container and
 * invokes the client side code in that container with a stubbed browser.
 * </p>
 *
 * <p>
 * To run tests for an individual class, right-click on the class and choose
 * Run As &gt; GWT JUnit Test.  Sometimes, Eclipse gets confused and doesn't
 * offer the Run As &gt; GWT JUnit Test option.  To work around this, open the
 * class in the Java editor and and right click on the class name instead.
 * </p>
 *
 * <p>
 * For more information about test suites, code coverage, etc. see
 * <code>doc/README.tests.</code>
 * </p>
 *
 * @author Kenneth J. Pronovici <pronovic@ieee.org>
 */
public abstract class ClientTestCase extends GWTTestCase {

    @Override
    public String getModuleName() {
        return "com.cedarsolutions.CedarCommon";
    }

}
