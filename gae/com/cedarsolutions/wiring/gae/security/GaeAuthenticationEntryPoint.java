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
package com.cedarsolutions.wiring.gae.security;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.springframework.security.AuthenticationException;
import org.springframework.security.ui.AuthenticationEntryPoint;

/**
 * Stubbed entry point.
 * @see <a href="http://blog.springsource.com/2010/08/02/spring-security-in-google-app-engine/">Spring Security in Google App Engine</a>
 * @author Kenneth J. Pronovici <pronovic@ieee.org>
 */
public class GaeAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(ServletRequest request, ServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        // nothing is required for our authentication scheme
    }

}
