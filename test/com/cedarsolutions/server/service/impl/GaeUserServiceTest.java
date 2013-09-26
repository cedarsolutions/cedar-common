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
package com.cedarsolutions.server.service.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.HashSet;
import java.util.Set;

import org.junit.Test;

import com.cedarsolutions.exception.EnumException;
import com.cedarsolutions.exception.NotConfiguredException;
import com.cedarsolutions.shared.domain.FederatedUser;
import com.cedarsolutions.shared.domain.OpenIdProvider;
import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;

/**
 * Unit tests for GaeUserService.
 * @author Kenneth J. Pronovici <pronovic@ieee.org>
 */
public class GaeUserServiceTest {

    /** Test constructor, getters and setters. */
    @Test public void testConstructor() {
        GaeUserService service = new GaeUserService();
        assertNull(service.getUserService());

        UserService userService = mock(UserService.class);
        service.setUserService(userService);
        assertSame(userService, service.getUserService());

        SpringContextService springContextService = mock(SpringContextService.class);
        service.setSpringContextService(springContextService);
        assertSame(springContextService, service.getSpringContextService());
    }

    /** Test the afterPropertiesSet() method. */
    @Test public void testAfterPropertiesSet() throws Exception {
        GaeUserService service = new GaeUserService();
        UserService userService = mock(UserService.class);
        SpringContextService springContextService = mock(SpringContextService.class);

        try {
            service.setUserService(userService);
            service.setSpringContextService(null);
            service.afterPropertiesSet();
            fail("Expected NotConfiguredException");
        } catch (NotConfiguredException e) { }

        try {
            service.setUserService(null);
            service.setSpringContextService(springContextService);
            service.afterPropertiesSet();
            fail("Expected NotConfiguredException");
        } catch (NotConfiguredException e) { }

        service.setUserService(userService);
        service.setSpringContextService(springContextService);
        service.afterPropertiesSet();
    }

    /** Test getLoginUrl(), simple case. */
    @Test public void testGetLoginUrl() {
        String destinationUrl = "destUrl";  // in this scenario, there's nothing to lose
        String loginUrl = "loginUrl";
        String expected = loginUrl;         // we won't do any translation

        String providerUrl = GaeUserService.deriveOpenIdProviderUrl(OpenIdProvider.MYOPENID);
        Set<String> attributes = new HashSet<String>();

        UserService userService = mock(UserService.class);
        SpringContextService springContextService = mock(SpringContextService.class);
        GaeUserService service = new GaeUserService();
        service.setUserService(userService);
        service.setSpringContextService(springContextService);
        service.afterPropertiesSet();

        when(userService.createLoginURL(destinationUrl, null, providerUrl, attributes)).thenReturn(loginUrl);
        assertEquals(expected, service.getLoginUrl(OpenIdProvider.MYOPENID, destinationUrl));
    }

    /** Test getLoginUrl(), including bookmarks. */
    @Test public void testGetLoginUrlBookmarkNonGoogle() {

        // Note that this scenario does not really work on a production system
        // The bookmarks get stripped off.  But, what I want to test here is that
        // we're at least passing the right values into the method calls.  It's
        // Google's problem (I hope) that the functionality doesn't work on their
        // end.

        String destinationUrl = "destUrl#whatever/else";  // in this scenario, anything after # gets lost
        String loginUrl = "loginUrl?continue=destUrl";    // this is basically what we get back
        String expected = loginUrl;                       // but we can't fix it for this provider

        String providerUrl = GaeUserService.deriveOpenIdProviderUrl(OpenIdProvider.MYOPENID);
        Set<String> attributes = new HashSet<String>();

        UserService userService = mock(UserService.class);
        SpringContextService springContextService = mock(SpringContextService.class);
        GaeUserService service = new GaeUserService();
        service.setUserService(userService);
        service.setSpringContextService(springContextService);
        service.afterPropertiesSet();

        when(userService.createLoginURL(destinationUrl, null, providerUrl, attributes)).thenReturn(loginUrl);
        assertEquals(expected, service.getLoginUrl(OpenIdProvider.MYOPENID, destinationUrl));
    }

    /** Test getLogoutUrl(). */
    @Test public void testGetLogoutUrl() {
        String logoutUrl = "logoutUrl";
        String destinationUrl = "destinationUrl";

        UserService userService = mock(UserService.class);
        SpringContextService springContextService = mock(SpringContextService.class);
        GaeUserService service = new GaeUserService();
        service.setUserService(userService);
        service.setSpringContextService(springContextService);
        service.afterPropertiesSet();

        when(userService.createLogoutURL(destinationUrl)).thenReturn(logoutUrl);
        assertEquals(logoutUrl, service.getLogoutUrl(destinationUrl));
    }

    /** Test isUserLoggedIn(). */
    @Test public void testIsUserLoggedIn() {
        UserService userService = mock(UserService.class);
        SpringContextService springContextService = mock(SpringContextService.class);
        GaeUserService service = new GaeUserService();
        service.setUserService(userService);
        service.setSpringContextService(springContextService);
        service.afterPropertiesSet();

        when(userService.isUserLoggedIn()).thenReturn(true);
        assertTrue(service.isUserLoggedIn());

        when(userService.isUserLoggedIn()).thenReturn(false);
        assertFalse(service.isUserLoggedIn());
    }

    /** Test isUserAdmin(). */
    @Test public void testIsUserAdmin() {
        UserService userService = mock(UserService.class);
        SpringContextService springContextService = mock(SpringContextService.class);
        GaeUserService service = new GaeUserService();
        service.setUserService(userService);
        service.setSpringContextService(springContextService);
        service.afterPropertiesSet();

        when(userService.isUserAdmin()).thenReturn(true);
        assertTrue(service.isUserAdmin());

        when(userService.isUserAdmin()).thenReturn(false);
        assertFalse(service.isUserAdmin());
    }

    /** Test getCurrentUser(). */
    @Test public void testGetCurrentUser() {
        User user = new User("email", "google.com", "userId", "federatedIdentity");
        UserService userService = mock(UserService.class);
        SpringContextService springContextService = mock(SpringContextService.class);
        GaeUserService service = new GaeUserService();
        service.setUserService(userService);
        service.setSpringContextService(springContextService);
        service.afterPropertiesSet();

        when(userService.isUserLoggedIn()).thenReturn(false);
        FederatedUser currentUser = service.getCurrentUser();
        assertNull(currentUser);

        when(userService.isUserLoggedIn()).thenReturn(true);
        when(userService.getCurrentUser()).thenReturn(user);
        when(userService.isUserAdmin()).thenReturn(true);

        currentUser = service.getCurrentUser();
        assertEquals(user.getEmail(), currentUser.getEmailAddress());
        assertEquals(user.getAuthDomain(), currentUser.getAuthenticationDomain());
        assertEquals(OpenIdProvider.GOOGLE, currentUser.getOpenIdProvider());  // because auth domain is "google.com"
        assertEquals(user.getUserId(), currentUser.getUserId());
        assertEquals(user.getFederatedIdentity(), currentUser.getFederatedIdentity());
        assertTrue(currentUser.isAdmin());

        when(userService.isUserLoggedIn()).thenReturn(true);
        when(userService.getCurrentUser()).thenReturn(user);
        when(userService.isUserAdmin()).thenReturn(false);

        currentUser = service.getCurrentUser();
        assertEquals(user.getEmail(), currentUser.getEmailAddress());
        assertEquals(user.getAuthDomain(), currentUser.getAuthenticationDomain());
        assertEquals(OpenIdProvider.GOOGLE, currentUser.getOpenIdProvider());  // because auth domain is "google.com"
        assertEquals(user.getUserId(), currentUser.getUserId());
        assertEquals(user.getFederatedIdentity(), currentUser.getFederatedIdentity());
        assertFalse(currentUser.isAdmin());
    }


    /** Test deriveOpenIdProviderUrl(). */
    @Test public void testDeriveOpenIdProviderUrl() {
        for (OpenIdProvider openIdProvider : OpenIdProvider.values()) {
            if (openIdProvider == OpenIdProvider.UNKNOWN) {
                try {
                    GaeUserService.deriveOpenIdProviderUrl(openIdProvider);
                    fail("Expected EnumException");
                } catch (EnumException e) { }
            } else {
                assertNotNull(GaeUserService.deriveOpenIdProviderUrl(openIdProvider));
            }
        }
    }

    /** Test deriveOpenIdProvider(). */
    @Test public void testDeriveOpenIdProvider() {
        assertEquals(OpenIdProvider.GOOGLE, GaeUserService.deriveOpenIdProvider("google.com"));
        assertEquals(OpenIdProvider.GOOGLE, GaeUserService.deriveOpenIdProvider("gmail.com"));
        assertEquals(OpenIdProvider.GOOGLE, GaeUserService.deriveOpenIdProvider("https://www.google.com/accounts/o8/ud"));

        assertEquals(OpenIdProvider.YAHOO, GaeUserService.deriveOpenIdProvider("yahoo.com"));
        assertEquals(OpenIdProvider.YAHOO, GaeUserService.deriveOpenIdProvider("https://open.login.yahooapis.com/openid/op/auth"));

        assertEquals(OpenIdProvider.MYSPACE, GaeUserService.deriveOpenIdProvider("myspace.com"));

        assertEquals(OpenIdProvider.AOL, GaeUserService.deriveOpenIdProvider("aol.com"));
        assertEquals(OpenIdProvider.AOL, GaeUserService.deriveOpenIdProvider("https://api.screenname.aol.com/auth/openidServer"));

        assertEquals(OpenIdProvider.MYOPENID, GaeUserService.deriveOpenIdProvider("myopenid.com"));
        assertEquals(OpenIdProvider.MYOPENID, GaeUserService.deriveOpenIdProvider("https://www.myopenid.com/server"));

        assertEquals(OpenIdProvider.UNKNOWN, GaeUserService.deriveOpenIdProvider("blech"));
        assertEquals(OpenIdProvider.UNKNOWN, GaeUserService.deriveOpenIdProvider(""));
        assertEquals(OpenIdProvider.UNKNOWN, GaeUserService.deriveOpenIdProvider(null));
    }

    /** Test deriveUserName() for OpenIdProvider.GOOGLE. */
    @Test public void testDeriveUserNameGoogle() {
        FederatedUser federatedUser = new FederatedUser();

        federatedUser.setOpenIdProvider(OpenIdProvider.GOOGLE);
        federatedUser.setEmailAddress(null);
        assertEquals("", GaeUserService.deriveUserName(federatedUser));

        federatedUser.setOpenIdProvider(OpenIdProvider.GOOGLE);
        federatedUser.setEmailAddress("");
        assertEquals("", GaeUserService.deriveUserName(federatedUser));

        federatedUser.setOpenIdProvider(OpenIdProvider.GOOGLE);
        federatedUser.setEmailAddress("user@example.com");
        assertEquals("user@example.com", GaeUserService.deriveUserName(federatedUser));
    }

    /** Test deriveUserName() for OpenIdProvider.YAHOO. */
    @Test public void testDeriveUserNameYahoo() {
        FederatedUser federatedUser = new FederatedUser();

        federatedUser.setOpenIdProvider(OpenIdProvider.YAHOO);
        federatedUser.setEmailAddress(null);
        assertEquals("", GaeUserService.deriveUserName(federatedUser));

        federatedUser.setOpenIdProvider(OpenIdProvider.YAHOO);
        federatedUser.setEmailAddress("");
        assertEquals("", GaeUserService.deriveUserName(federatedUser));

        federatedUser.setOpenIdProvider(OpenIdProvider.YAHOO);
        federatedUser.setEmailAddress("user@example.com");
        assertEquals("user@example.com", GaeUserService.deriveUserName(federatedUser));
    }

    /** Test deriveUserName() for OpenIdProvider.MYSPACE. */
    @Test public void testDeriveUserNameMySpace() {
        FederatedUser federatedUser = new FederatedUser();

        federatedUser.setOpenIdProvider(OpenIdProvider.MYSPACE);
        federatedUser.setFederatedIdentity(null);
        assertEquals("", GaeUserService.deriveUserName(federatedUser));

        federatedUser.setOpenIdProvider(OpenIdProvider.MYSPACE);
        federatedUser.setFederatedIdentity("");
        assertEquals("", GaeUserService.deriveUserName(federatedUser));

        // This is the normal format
        federatedUser.setOpenIdProvider(OpenIdProvider.MYSPACE);
        federatedUser.setFederatedIdentity("http://www.myspace.com/its_misterphil");
        assertEquals("its_misterphil@myspace.com", GaeUserService.deriveUserName(federatedUser));

        federatedUser.setOpenIdProvider(OpenIdProvider.MYSPACE);
        federatedUser.setFederatedIdentity("https://www.myspace.com/its_misterphil");
        assertEquals("its_misterphil@myspace.com", GaeUserService.deriveUserName(federatedUser));
    }

    /** Test deriveUserName() for OpenIdProvider.AOL. */
    @Test public void testDeriveUserNameAol() {
        FederatedUser federatedUser = new FederatedUser();

        federatedUser.setOpenIdProvider(OpenIdProvider.AOL);
        federatedUser.setEmailAddress(null);
        assertEquals("", GaeUserService.deriveUserName(federatedUser));

        federatedUser.setOpenIdProvider(OpenIdProvider.AOL);
        federatedUser.setEmailAddress("");
        assertEquals("", GaeUserService.deriveUserName(federatedUser));

        federatedUser.setOpenIdProvider(OpenIdProvider.AOL);
        federatedUser.setEmailAddress("user@example.com");
        assertEquals("user@example.com", GaeUserService.deriveUserName(federatedUser));
    }

    /** Test deriveUserName() for OpenIdProvider.MYOPENID. */
    @Test public void testDeriveUserNameMyOpenId() {
        FederatedUser federatedUser = new FederatedUser();

        federatedUser.setOpenIdProvider(OpenIdProvider.MYOPENID);
        federatedUser.setFederatedIdentity(null);
        assertEquals("", GaeUserService.deriveUserName(federatedUser));

        federatedUser.setOpenIdProvider(OpenIdProvider.MYOPENID);
        federatedUser.setFederatedIdentity("");
        assertEquals("", GaeUserService.deriveUserName(federatedUser));

        // This is the normal format
        federatedUser.setOpenIdProvider(OpenIdProvider.MYOPENID);
        federatedUser.setFederatedIdentity("https://pronovic.myopenid.com/");
        assertEquals("pronovic@myopenid.com", GaeUserService.deriveUserName(federatedUser));

        federatedUser.setOpenIdProvider(OpenIdProvider.MYOPENID);
        federatedUser.setFederatedIdentity("http://pronovic.myopenid.com/");
        assertEquals("pronovic@myopenid.com", GaeUserService.deriveUserName(federatedUser));

        federatedUser.setOpenIdProvider(OpenIdProvider.MYOPENID);
        federatedUser.setFederatedIdentity("https://pronovic.myopenid.com");
        assertEquals("pronovic@myopenid.com", GaeUserService.deriveUserName(federatedUser));

        federatedUser.setOpenIdProvider(OpenIdProvider.MYOPENID);
        federatedUser.setFederatedIdentity("http://pronovic.myopenid.com");
        assertEquals("pronovic@myopenid.com", GaeUserService.deriveUserName(federatedUser));
    }

    /** Test deriveUserName() for OpenIdProvider.UNKNOWN. */
    @Test public void testDeriveUserNameUnknown() {
        FederatedUser federatedUser = new FederatedUser();

        federatedUser.setOpenIdProvider(OpenIdProvider.UNKNOWN);
        federatedUser.setEmailAddress(null);
        assertEquals("", GaeUserService.deriveUserName(federatedUser));

        federatedUser.setOpenIdProvider(OpenIdProvider.UNKNOWN);
        federatedUser.setEmailAddress("");
        assertEquals("", GaeUserService.deriveUserName(federatedUser));

        federatedUser.setOpenIdProvider(OpenIdProvider.UNKNOWN);
        federatedUser.setEmailAddress("user@example.com");
        assertEquals("", GaeUserService.deriveUserName(federatedUser));
    }

}
