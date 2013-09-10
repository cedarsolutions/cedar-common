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

import java.io.Serializable;

import org.springframework.security.GrantedAuthorityImpl;

/**
 * User role within Google App Engine.
 * @see <a href="http://blog.springsource.com/2010/08/02/spring-security-in-google-app-engine/">Spring Security in Google App Engine</a>
 * @author Kenneth J. Pronovici <pronovic@ieee.org>
 */
public class GaeRole extends GrantedAuthorityImpl implements Serializable {

    /** The anonymous role, assigned to any application user, whether or not they have logged in. */
    public static final GaeRole ROLE_ANONYMOUS = new GaeRole("ROLE_ANONYMOUS");

    /** The user role, assigned to any user which has logged in. */
    public static final GaeRole ROLE_USER = new GaeRole("ROLE_USER");

    /** The admin role, assigned to any user which has logged in and is marked as an administrator in GAE. */
    public static final GaeRole ROLE_ADMIN = new GaeRole("ROLE_ADMIN");

    /** Serialization version number, which can be important to the GAE back-end. */
    private static final long serialVersionUID = 1L;

    /** Create a role in terms of a role name. */
    public GaeRole(String role) {
        super(role);
    }

}
