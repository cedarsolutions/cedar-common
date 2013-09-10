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

import org.springframework.security.Authentication;
import org.springframework.security.AuthenticationException;
import org.springframework.security.providers.AuthenticationProvider;

/**
 * Spring authentication provider in terms of Google App Engine.
 * @see <a href="http://blog.springsource.com/2010/08/02/spring-security-in-google-app-engine/">Spring Security in Google App Engine</a>
 * @author Kenneth J. Pronovici <pronovic@ieee.org>
 */
public class GaeAuthenticationProvider implements AuthenticationProvider {

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        GaeUser gaeUser = (GaeUser) authentication.getPrincipal();
        return new GaeUserAuthenticationToken(gaeUser, authentication.getDetails());
    }

    @Override
    @SuppressWarnings("rawtypes")
    public final boolean supports(Class clazz) {
        return clazz != null && GaeUserAuthenticationToken.class.isAssignableFrom(clazz);
    }

}
