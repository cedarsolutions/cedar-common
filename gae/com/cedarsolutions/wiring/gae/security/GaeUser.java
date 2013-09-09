/* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 *
 *              C E D A R
 *          S O L U T I O N S       "Software done right."
 *           S O F T W A R E
 *
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 *
 * Copyright (c) 2011-2013 Kenneth J. Pronovici.
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
import java.util.HashSet;
import java.util.Set;

import org.springframework.security.GrantedAuthority;

import com.google.appengine.api.users.User;

/**
 * User sourced from Google App Engine.
 * @see <a href="http://blog.springsource.com/2010/08/02/spring-security-in-google-app-engine/">Spring Security in Google App Engine</a>
 * @author Kenneth J. Pronovici <pronovic@ieee.org>
 */
public class GaeUser implements Serializable {

    /** User id assigned to anonymous users. */
    public static final String ANONYMOUS_USER_ID = "anonymous";

    /** Serialization version number, which can be important to the GAE back-end. */
    private static final long serialVersionUID = 1L;

    /** Authentication domain. */
    private String authDomain;

    /** Federated identity. */
    private String federatedIdentity;

    /** Email address. */
    private String email;

    /** User id. */
    private String userId;

    /** Set of roles assigned to this user. */
    private final Set<GaeRole> roles = new HashSet<GaeRole>();

    /** Create an anonymous user. */
    public GaeUser() {
        this.authDomain = "";
        this.federatedIdentity = "";
        this.email = "";
        this.userId = ANONYMOUS_USER_ID;
        this.addRole(GaeRole.ROLE_ANONYMOUS);
    }

    /**
     * Set GAE backend credentials for a user.
     * @param user        User information from the GAE backend.
     * @param admin       Whether the user is an adminstrator
     * @param clientRoles List of client-provided roles to associate with user (possibly null)
     */
    public void initializeCredentials(User user, boolean admin, String[] clientRoles) {
        this.authDomain = user.getAuthDomain();
        this.federatedIdentity = user.getFederatedIdentity();
        this.email = user.getEmail();
        this.userId = user.getUserId();

        this.addRole(GaeRole.ROLE_USER);
        if (admin) {
            this.addRole(GaeRole.ROLE_ADMIN);
        }

        if (clientRoles != null) {
            for (String clientRole : clientRoles) {
                this.addRole(clientRole);
            }
        }
    }

    /** Add a role to the user's list of roles. */
    public void addRole(String role) {
        this.addRole(new GaeRole(role));
    }

    /** Add a role to the user's list of roles. */
    public void addRole(GaeRole role) {
        this.roles.add(role);
    }

    /** String representation of a user. */
    @Override
    public String toString() {
        return "[" + this.userId + "]";
    }

    /** Indicates whether this is an anonymous user. */
    public boolean isAnonymous() {
        return !this.roles.contains(GaeRole.ROLE_USER) && !this.roles.contains(GaeRole.ROLE_ADMIN);
    }

    /** Indicates whether this is an admin user. */
    public boolean isAdmin() {
        return this.roles.contains(GaeRole.ROLE_ADMIN);
    }

    /** Generate a set of granted authorities based on assigned roles. */
    public GrantedAuthority[] getAuthorities() {
        GrantedAuthority[] authorities = new GrantedAuthority[this.roles.size()];

        int index = 0;
        for (GaeRole gaeRole : this.roles) {
            authorities[index++] = gaeRole;
        }

        return authorities;
    }

    /** Authentication domain. */
    public String getAuthDomain() {
        return this.authDomain;
    }

    /** Federated identity. */
    public String getFederatedIdentity() {
        return this.federatedIdentity;
    }

    /** Email address. */
    public String getEmail() {
        return this.email;
    }

    /** User id. */
    public String getUserId() {
        return this.userId;
    }

    /** Set of roles assigned to this user. */
    public Set<GaeRole> getRoles() {
        return this.roles;
    }

}
