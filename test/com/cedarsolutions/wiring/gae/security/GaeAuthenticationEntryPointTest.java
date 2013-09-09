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
package com.cedarsolutions.wiring.gae.security;

import static org.mockito.Mockito.mock;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.junit.Test;
import org.springframework.security.AuthenticationException;

/**
 * Unit tests for GaeAuthenticationEntryPoint.
 * @author Kenneth J. Pronovici <pronovic@ieee.org>
 */
public class GaeAuthenticationEntryPointTest {

    /** Test commence(). */
    @Test public void testCommence() throws Exception {
        ServletRequest request = mock(ServletRequest.class);
        ServletResponse response = mock(ServletResponse.class);
        AuthenticationException exception = mock(AuthenticationException.class);
        GaeAuthenticationEntryPoint entryPoint = new GaeAuthenticationEntryPoint();
        entryPoint.commence(request, response, exception);  // just make sure it runs (it's a no-op)
    }
}
