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

import java.io.IOException;
import java.io.Serializable;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.security.Authentication;
import org.springframework.security.AuthenticationManager;
import org.springframework.security.context.SecurityContext;
import org.springframework.security.context.SecurityContextHolder;
import org.springframework.security.ui.AuthenticationDetailsSource;
import org.springframework.security.ui.WebAuthenticationDetailsSource;
import org.springframework.web.filter.GenericFilterBean;

import com.cedarsolutions.exception.NotConfiguredException;
import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;

/**
 * Authentication filter for Google App Engine.
 *
 * <p>
 * Clients have the opportunity to inject extra roles into the authentication
 * mechanism.  These roles can be used just like the ROLE_ADMIN and ROLE_USER
 * roles that are provided by the standard mechanism.  To inject extra roles,
 * use <code>ISpringContextService</code> to set a session attribute named
 * <code>GaeAuthenticationFilter.CLIENT_ROLES_ATTRIBUTE</code>.  The value
 * must be a string array.  Anonymous users (ROLE_ANONYMOUS) may not have
 * any other roles.  So, even if a client sets roles for an anonymous user,
 * those roles will be ignored.
 * </p>
 *
 * @see <a href="http://blog.springsource.com/2010/08/02/spring-security-in-google-app-engine/">Spring Security in Google App Engine</a>
 * @author Kenneth J. Pronovici <pronovic@ieee.org>
 */
public class GaeAuthenticationFilter extends GenericFilterBean implements InitializingBean {

    /** Name of the session attribute that holds a list of client-provided roles. */
    public static final String CLIENT_ROLES_ATTRIBUTE = "GAE_AUTHENTICATION_CLIENT_ROLES";

    /** Underlying GAE user service. */
    private UserService userService;

    /** Spring authentication manager. */
    private AuthenticationManager authenticationManager;

    /** Spring authentication details source. */
    private AuthenticationDetailsSource authenticationDetailsSource = new WebAuthenticationDetailsSource();

    /**
     * Invoked by a bean factory after it has set all bean properties.
     * @throws NotConfiguredException In the event of misconfiguration.
     */
    @Override
    public void afterPropertiesSet() throws NotConfiguredException {
        if (this.userService == null || this.authenticationManager == null) {
            throw new NotConfiguredException("GaeAuthenticationFilter is not properly configured.");
        }
    }

    /** Get the security context. */
    protected SecurityContext getSecurityContext() {
        return SecurityContextHolder.getContext();
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        // Always get the current user from the GAE back-end regardless of our existing context
        GaeUser gaeUser = new GaeUser(); // anonymous by default
        if (this.userService.isUserLoggedIn()) {
            User user = this.userService.getCurrentUser();
            boolean admin = this.userService.isUserAdmin();
            String[] clientRoles = this.getSessionAttribute(request, CLIENT_ROLES_ATTRIBUTE);
            gaeUser.initializeCredentials(user, admin, clientRoles);
        }

        // We have to fill something into the security context regardless of whether the user is anonymous
        GaeUserAuthenticationToken token = new GaeUserAuthenticationToken(gaeUser);
        token.setDetails(this.authenticationDetailsSource.buildDetails(request));
        Authentication authentication = this.authenticationManager.authenticate(token);
        this.getSecurityContext().setAuthentication(authentication);

        // Execute the rest of the filter chain now that we have a valid security context
        chain.doFilter(request, response);
    }

    /** Get the HTTP session from the passed-in request. */
    protected HttpSession getRequestSession(ServletRequest request) {
        return request == null ? null : ((HttpServletRequest) request).getSession(false);
    }

    /** Get a named attribute from the session, or null if the session is not set. */
    @SuppressWarnings("unchecked")
    protected <T extends Serializable> T getSessionAttribute(ServletRequest request, String name) {
        HttpSession session = this.getRequestSession(request);
        return session == null ? null : (T) session.getAttribute(name);
    }

    public UserService getUserService() {
        return this.userService;
    }

    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    public AuthenticationManager getAuthenticationManager() {
        return this.authenticationManager;
    }

    public void setAuthenticationManager(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

}
