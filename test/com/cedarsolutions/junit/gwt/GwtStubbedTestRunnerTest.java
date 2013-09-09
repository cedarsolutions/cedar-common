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
package com.cedarsolutions.junit.gwt;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.runner.RunWith;

import com.google.gwt.core.client.GWT;
import com.google.gwt.i18n.client.Messages;

/**
 * Unit test for GwtStubbedTestRunner and the related classloader.
 *
 * <p>
 * If this test passes, then the GwtClassLoader and GwtCreateTranslator
 * are generally working and are hooked up properly into the JUnit 4.5
 * environment.
 * </p>
 *
 * @author Kenneth J. Pronovici <pronovic@ieee.org>
 */
@RunWith(GwtStubbedTestRunner.class)
public class GwtStubbedTestRunnerTest {

    /** Test the test runner. */
    @Test public void testTestRunner() {
        TestMessages messages = GWT.create(TestMessages.class);
        assertEquals("Default message", messages.message());
    }

    /** Constants interface that we can use for testing. */
    private interface TestMessages extends Messages {
        @DefaultMessage("Default message")
        String message();
    }

}
