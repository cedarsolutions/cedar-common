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

import static com.cedarsolutions.wiring.gae.security.GaeAuthenticationFilter.CLIENT_ROLES_ATTRIBUTE;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.junit.Test;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.springframework.security.Authentication;
import org.springframework.security.AuthenticationManager;
import org.springframework.security.GrantedAuthority;
import org.springframework.security.context.SecurityContext;

import com.cedarsolutions.exception.NotConfiguredException;
import com.cedarsolutions.exception.NotImplementedException;
import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;

/**
 * Unit tests for GaeAuthenticationFilter.
 * @author Kenneth J. Pronovici <pronovic@ieee.org>
 */
public class GaeAuthenticationFilterTest {

    /** Test the constructor. */
    @Test public void testConstructor() {
        GaeAuthenticationFilter filter = new GaeAuthenticationFilter();
        assertNotNull(filter);
        assertNull(filter.getUserService());
        assertNull(filter.getAuthenticationManager());
        assertNull(filter.getFilterConfig());
    }

    /** Test the getters and setters. */
    @Test public void testGettersSetters() {
        UserService userService = mock(UserService.class);
        AuthenticationManager authenticationManager = mock(AuthenticationManager.class);
        GaeAuthenticationFilter filter = new GaeAuthenticationFilter();

        filter.setUserService(userService);
        assertSame(userService, filter.getUserService());

        filter.setAuthenticationManager(authenticationManager);
        assertSame(authenticationManager, filter.getAuthenticationManager());
    }

    /** Test afterPropertiesSet(). */
    @Test public void testAfterPropertiesSet() {
        UserService userService = mock(UserService.class);
        AuthenticationManager authenticationManager = mock(AuthenticationManager.class);
        GaeAuthenticationFilter filter = new GaeAuthenticationFilter();

        try {
            filter.setUserService(null);
            filter.setAuthenticationManager(authenticationManager);
            filter.afterPropertiesSet();
            fail("Expected NotConfiguredException");
        } catch (NotConfiguredException e) { }

        try {
            filter.setUserService(userService);
            filter.setAuthenticationManager(null);
            filter.afterPropertiesSet();
            fail("Expected NotConfiguredException");
        } catch (NotConfiguredException e) { }

        filter.setUserService(userService);
        filter.setAuthenticationManager(authenticationManager);
        filter.afterPropertiesSet();
    }

    /** Test doFilter() when the user is not logged in. */
    @Test public void testDoFilterAnonymous() throws Exception {
        UserService userService = mock(UserService.class);
        AuthenticationManager authenticationManager = mock(AuthenticationManager.class);
        StubbedGaeAuthenticationFilter filter = new StubbedGaeAuthenticationFilter();

        filter.setUserService(userService);
        filter.setAuthenticationManager(authenticationManager);
        filter.afterPropertiesSet();

        when(authenticationManager.authenticate(any(GaeUserAuthenticationToken.class))).thenAnswer(new AuthenticationAnswer());
        when(userService.isUserLoggedIn()).thenReturn(false);     // user is not logged in

        GaeUser gaeUser = callDoFilter(filter);  // no client roles
        assertEquals("", gaeUser.getAuthDomain());
        assertEquals("", gaeUser.getFederatedIdentity());
        assertEquals("", gaeUser.getEmail());
        assertEquals(GaeUser.ANONYMOUS_USER_ID, gaeUser.getUserId());
        assertTrue(gaeUser.getRoles().contains(GaeRole.ROLE_ANONYMOUS));
        assertFalse(gaeUser.getRoles().contains(GaeRole.ROLE_USER));
        assertFalse(gaeUser.getRoles().contains(GaeRole.ROLE_ADMIN));
        assertTrue(gaeUser.isAnonymous());
        assertFalse(gaeUser.isAdmin());
    }

    /** Test doFilter() when the user is not logged in, with client roles set. */
    @Test public void testDoFilterAnonymousWithClientRoles() throws Exception {
        UserService userService = mock(UserService.class);
        AuthenticationManager authenticationManager = mock(AuthenticationManager.class);
        StubbedGaeAuthenticationFilter filter = new StubbedGaeAuthenticationFilter();

        filter.setUserService(userService);
        filter.setAuthenticationManager(authenticationManager);
        filter.afterPropertiesSet();

        when(authenticationManager.authenticate(any(GaeUserAuthenticationToken.class))).thenAnswer(new AuthenticationAnswer());
        when(userService.isUserLoggedIn()).thenReturn(false);     // user is not logged in

        GaeUser gaeUser = callDoFilter(filter, "ONE");
        assertEquals("", gaeUser.getAuthDomain());
        assertEquals("", gaeUser.getFederatedIdentity());
        assertEquals("", gaeUser.getEmail());
        assertEquals(GaeUser.ANONYMOUS_USER_ID, gaeUser.getUserId());
        assertTrue(gaeUser.getRoles().contains(GaeRole.ROLE_ANONYMOUS));
        assertFalse(gaeUser.getRoles().contains(GaeRole.ROLE_USER));
        assertFalse(gaeUser.getRoles().contains(GaeRole.ROLE_ADMIN));
        assertFalse(gaeUser.getRoles().contains(new GaeRole("ONE")));
        assertTrue(gaeUser.isAnonymous());
        assertFalse(gaeUser.isAdmin());
    }

    /** Test doFilter() when the user is logged in and is not an admin user. */
    @Test public void testDoFilterLoggedIn() throws Exception {
        UserService userService = mock(UserService.class);
        AuthenticationManager authenticationManager = mock(AuthenticationManager.class);
        StubbedGaeAuthenticationFilter filter = new StubbedGaeAuthenticationFilter();

        filter.setUserService(userService);
        filter.setAuthenticationManager(authenticationManager);
        filter.afterPropertiesSet();

        User user = new User("email", "authDomain", "userId", "federatedIdentity");
        when(authenticationManager.authenticate(any(GaeUserAuthenticationToken.class))).thenAnswer(new AuthenticationAnswer());
        when(userService.isUserLoggedIn()).thenReturn(true);      // user is logged in
        when(userService.isUserAdmin()).thenReturn(false);        // user is not an admin
        when(userService.getCurrentUser()).thenReturn(user);      // this is the logged in user

        GaeUser gaeUser = callDoFilter(filter);  // no client roles
        assertEquals(user.getAuthDomain(), gaeUser.getAuthDomain());
        assertEquals(user.getFederatedIdentity(), gaeUser.getFederatedIdentity());
        assertEquals(user.getEmail(), gaeUser.getEmail());
        assertEquals(user.getUserId(), gaeUser.getUserId());
        assertTrue(gaeUser.getRoles().contains(GaeRole.ROLE_ANONYMOUS));
        assertTrue(gaeUser.getRoles().contains(GaeRole.ROLE_USER));
        assertFalse(gaeUser.getRoles().contains(GaeRole.ROLE_ADMIN));
        assertFalse(gaeUser.isAnonymous());
        assertFalse(gaeUser.isAdmin());
    }

    /** Test doFilter() when the user is logged in and is not an admin user, with client roles. */
    @Test public void testDoFilterLoggedInWithClientRoles() throws Exception {
        UserService userService = mock(UserService.class);
        AuthenticationManager authenticationManager = mock(AuthenticationManager.class);
        StubbedGaeAuthenticationFilter filter = new StubbedGaeAuthenticationFilter();

        filter.setUserService(userService);
        filter.setAuthenticationManager(authenticationManager);
        filter.afterPropertiesSet();

        User user = new User("email", "authDomain", "userId", "federatedIdentity");
        when(authenticationManager.authenticate(any(GaeUserAuthenticationToken.class))).thenAnswer(new AuthenticationAnswer());
        when(userService.isUserLoggedIn()).thenReturn(true);      // user is logged in
        when(userService.isUserAdmin()).thenReturn(false);        // user is not an admin
        when(userService.getCurrentUser()).thenReturn(user);      // this is the logged in user

        GaeUser gaeUser = callDoFilter(filter, "ONE");
        assertEquals(user.getAuthDomain(), gaeUser.getAuthDomain());
        assertEquals(user.getFederatedIdentity(), gaeUser.getFederatedIdentity());
        assertEquals(user.getEmail(), gaeUser.getEmail());
        assertEquals(user.getUserId(), gaeUser.getUserId());
        assertTrue(gaeUser.getRoles().contains(GaeRole.ROLE_ANONYMOUS));
        assertTrue(gaeUser.getRoles().contains(GaeRole.ROLE_USER));
        assertFalse(gaeUser.getRoles().contains(GaeRole.ROLE_ADMIN));
        assertTrue(gaeUser.getRoles().contains(new GaeRole("ONE")));
        assertFalse(gaeUser.isAnonymous());
        assertFalse(gaeUser.isAdmin());
    }

    /** Test doFilter() when the user is logged in and is an admin user. */
    @Test public void testDoFilterAdmin() throws Exception {
        UserService userService = mock(UserService.class);
        AuthenticationManager authenticationManager = mock(AuthenticationManager.class);
        StubbedGaeAuthenticationFilter filter = new StubbedGaeAuthenticationFilter();

        filter.setUserService(userService);
        filter.setAuthenticationManager(authenticationManager);
        filter.afterPropertiesSet();

        User user = new User("email", "authDomain", "userId", "federatedIdentity");
        when(authenticationManager.authenticate(any(GaeUserAuthenticationToken.class))).thenAnswer(new AuthenticationAnswer());
        when(userService.isUserLoggedIn()).thenReturn(true);      // user is logged in
        when(userService.isUserAdmin()).thenReturn(true);         // user is an admin
        when(userService.getCurrentUser()).thenReturn(user);      // this is the logged in user

        GaeUser gaeUser = callDoFilter(filter);  // no client roles
        assertEquals(user.getAuthDomain(), gaeUser.getAuthDomain());
        assertEquals(user.getFederatedIdentity(), gaeUser.getFederatedIdentity());
        assertEquals(user.getEmail(), gaeUser.getEmail());
        assertEquals(user.getUserId(), gaeUser.getUserId());
        assertTrue(gaeUser.getRoles().contains(GaeRole.ROLE_ANONYMOUS));
        assertTrue(gaeUser.getRoles().contains(GaeRole.ROLE_USER));
        assertTrue(gaeUser.getRoles().contains(GaeRole.ROLE_ADMIN));
        assertFalse(gaeUser.isAnonymous());
        assertTrue(gaeUser.isAdmin());
    }

    /** Test doFilter() when the user is logged in and is an admin user, with client roles. */
    @Test public void testDoFilterAdminWithClientRoles() throws Exception {
        UserService userService = mock(UserService.class);
        AuthenticationManager authenticationManager = mock(AuthenticationManager.class);
        StubbedGaeAuthenticationFilter filter = new StubbedGaeAuthenticationFilter();

        filter.setUserService(userService);
        filter.setAuthenticationManager(authenticationManager);
        filter.afterPropertiesSet();

        User user = new User("email", "authDomain", "userId", "federatedIdentity");
        when(authenticationManager.authenticate(any(GaeUserAuthenticationToken.class))).thenAnswer(new AuthenticationAnswer());
        when(userService.isUserLoggedIn()).thenReturn(true);      // user is logged in
        when(userService.isUserAdmin()).thenReturn(true);         // user is an admin
        when(userService.getCurrentUser()).thenReturn(user);      // this is the logged in user

        GaeUser gaeUser = callDoFilter(filter, "ONE");
        assertEquals(user.getAuthDomain(), gaeUser.getAuthDomain());
        assertEquals(user.getFederatedIdentity(), gaeUser.getFederatedIdentity());
        assertEquals(user.getEmail(), gaeUser.getEmail());
        assertEquals(user.getUserId(), gaeUser.getUserId());
        assertTrue(gaeUser.getRoles().contains(GaeRole.ROLE_ANONYMOUS));
        assertTrue(gaeUser.getRoles().contains(GaeRole.ROLE_USER));
        assertTrue(gaeUser.getRoles().contains(GaeRole.ROLE_ADMIN));
        assertTrue(gaeUser.getRoles().contains(new GaeRole("ONE")));
        assertFalse(gaeUser.isAnonymous());
        assertTrue(gaeUser.isAdmin());
    }

    /** Call doFilter(), returning the user that was authenticated. */
    private static GaeUser callDoFilter(StubbedGaeAuthenticationFilter filter, String ... clientRoles) throws Exception {
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        FilterChain chain = mock(FilterChain.class);
        HttpSession session = mock(HttpSession.class);

        when(session.getAttribute(CLIENT_ROLES_ATTRIBUTE)).thenReturn(clientRoles);
        when(request.getSession(false)).thenReturn(session);

        filter.doFilter(request, response, chain);

        Authentication authentication = filter.getMockSecurityContext().getAuthentication();
        assertNotNull(authentication);
        assertTrue(authentication instanceof StubbedAuthentication);
        GaeUserAuthenticationToken token = ((StubbedAuthentication) authentication).getToken();
        assertNotNull(token.getDetails());  // don't care exactly what's in it, just that it's filled in
        assertTrue(token.getPrincipal() instanceof GaeUser);

        return (GaeUser) token.getPrincipal();
    }

    /**
     * Mockito answer to give back the proper response to a GaeUserAuthenticationToken.
     * @see <a href="http://stackoverflow.com/questions/2684630/mockito-how-to-make-a-method-return-an-argument-that-was-passed-to-it">Stack Overflow</a>
     */
    private static class AuthenticationAnswer implements Answer<Authentication> {
        @Override
        public Authentication answer(InvocationOnMock invocation) throws Throwable {
            Object[] args = invocation.getArguments();
            GaeUserAuthenticationToken token = (GaeUserAuthenticationToken) args[0];
            return new StubbedAuthentication(token);
        }
    }

    /**
     * Stubbed GaeAuthenticationFilter for testing.
     * This class lets us override the security context so we can introspect it.
     */
    private static class StubbedGaeAuthenticationFilter extends GaeAuthenticationFilter {
        private MockSecurityContext mockSecurityContext;

        public StubbedGaeAuthenticationFilter() {
            this.mockSecurityContext = new MockSecurityContext();
        }

        @Override
        protected SecurityContext getSecurityContext() {
            return this.mockSecurityContext;
        }

        public MockSecurityContext getMockSecurityContext() {
            return this.mockSecurityContext;
        }
    }

    /** Mock security context class. */
    @SuppressWarnings("serial")
    private static class MockSecurityContext implements SecurityContext {

        private Authentication authentication;

        @Override
        public Authentication getAuthentication() {
            return this.authentication;
        }

        @Override
        public void setAuthentication(Authentication authentication) {
            this.authentication = authentication;
        }
    }

    /** Stubbed authentication object that we can use to verify behavior. */
    @SuppressWarnings("serial")
    private static class StubbedAuthentication implements Authentication {

        private GaeUserAuthenticationToken token;

        public StubbedAuthentication(GaeUserAuthenticationToken token) {
            this.token = token;
        }

        public GaeUserAuthenticationToken getToken() {
            return this.token;
        }

        @Override
        public String getName() {
            throw new NotImplementedException("StubbedAuthentication");
        }

        @Override
        public GrantedAuthority[] getAuthorities() {
            throw new NotImplementedException("StubbedAuthentication");
        }

        @Override
        public Object getCredentials() {
            throw new NotImplementedException("StubbedAuthentication");
        }

        @Override
        public Object getDetails() {
            throw new NotImplementedException("StubbedAuthentication");
        }

        @Override
        public Object getPrincipal() {
            throw new NotImplementedException("StubbedAuthentication");
        }

        @Override
        public boolean isAuthenticated() {
            throw new NotImplementedException("StubbedAuthentication");
        }

        @Override
        public void setAuthenticated(boolean arg0) throws IllegalArgumentException {
            throw new NotImplementedException("StubbedAuthentication");
        }
    }
}
