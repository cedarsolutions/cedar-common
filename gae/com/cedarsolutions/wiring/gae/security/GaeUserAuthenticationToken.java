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

import java.io.Serializable;

import org.springframework.security.providers.preauth.PreAuthenticatedAuthenticationToken;

/**
 * Authentication token for Google App Engine.
 * @see <a href="http://blog.springsource.com/2010/08/02/spring-security-in-google-app-engine/">Spring Security in Google App Engine</a>
 * @author Kenneth J. Pronovici <pronovic@ieee.org>
 */
public class GaeUserAuthenticationToken extends PreAuthenticatedAuthenticationToken implements Serializable {

    /** Serialization version number, which can be important to the GAE back-end. */
    private static final long serialVersionUID = 1L;

    /**
     * Create an authentication token with no credentials.
     * @param principal   The current GAE user, possibly anonymous
     */
    public GaeUserAuthenticationToken(GaeUser principal) {
        this(principal, null);
    }

    /**
     * Create an authentication token.
     * @param principal   The current GAE user, possibly anonymous
     * @param credentials Credentials associated with the principal, or null
     */
    public GaeUserAuthenticationToken(GaeUser principal, Object credentials) {
        super(principal, credentials, principal.getAuthorities());
    }

}
