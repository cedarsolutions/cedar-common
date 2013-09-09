/* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 *
 *              C E D A R
 *          S O L U T I O N S       "Software done right."
 *           S O F T W A R E
 *
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 *
 * Copyright (c) 2012 Kenneth J. Pronovici.
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
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import javax.servlet.http.Cookie;

import org.junit.Test;

import com.cedarsolutions.exception.NotConfiguredException;
import com.cedarsolutions.server.service.ISpringContextService;
import com.google.gwt.user.client.rpc.RpcToken;
import com.google.gwt.user.client.rpc.RpcTokenException;
import com.google.gwt.user.client.rpc.XsrfToken;
import com.google.gwt.util.tools.shared.Md5Utils;
import com.google.gwt.util.tools.shared.StringUtils;

/**
 * Unit tests for GwtCookieXsrfTokenService.
 * @author Kenneth J. Pronovici <pronovic@ieee.org>
 */
public class GwtCookieXsrfTokenServiceTest {

    /** Test constructor, getters and setters. */
    @Test public void testConstructor() {
        GwtCookieXsrfTokenService service = new GwtCookieXsrfTokenService();
        assertNull(service.getSessionCookieName());
        assertNull(service.getSpringContextService());

        service.setSessionCookieName("cookie");
        assertEquals("cookie", service.getSessionCookieName());

        ISpringContextService springContextService = mock(ISpringContextService.class);
        service.setSpringContextService(springContextService);
        assertSame(springContextService, service.getSpringContextService());
    }

    /** Test the afterPropertiesSet() method. */
    @Test public void testAfterPropertiesSet() throws Exception {
        GwtCookieXsrfTokenService service = new GwtCookieXsrfTokenService();
        ISpringContextService springContextService = mock(ISpringContextService.class);

        try {
            service.setSessionCookieName(null);
            service.setSpringContextService(springContextService);
            service.afterPropertiesSet();
            fail("Expected NotConfiguredException");
        } catch (NotConfiguredException e) { }

        try {
            service.setSessionCookieName("cookie");
            service.setSpringContextService(null);
            service.afterPropertiesSet();
            fail("Expected NotConfiguredException");
        } catch (NotConfiguredException e) { }

        service.setSessionCookieName("cookie");
        service.setSpringContextService(springContextService);
        service.afterPropertiesSet();
    }

    /** Test generateXsrfToken(). */
    @Test public void testGenerateXsrfToken() {
        ISpringContextService springContextService = mock(ISpringContextService.class);
        GwtCookieXsrfTokenService service = new GwtCookieXsrfTokenService();
        service.setSessionCookieName("cookie");
        service.setSpringContextService(springContextService);

        Cookie cookie = mock(Cookie.class);
        when(cookie.getValue()).thenReturn("value");
        when(springContextService.getCookie("cookie", false)).thenReturn(cookie);

        assertEquals(GwtCookieXsrfTokenService.generateTokenFromCookie("value"), service.generateXsrfToken());
    }

    /** Test validateXsrfToken() for an RpcToken. */
    @Test public void testValidateXsrfTokenRpcToken() {
        ISpringContextService springContextService = mock(ISpringContextService.class);
        GwtCookieXsrfTokenService service = new GwtCookieXsrfTokenService();
        service.setSessionCookieName("cookie");
        service.setSpringContextService(springContextService);

        Cookie cookie = mock(Cookie.class);
        when(cookie.getValue()).thenReturn("value");
        when(springContextService.getCookie("cookie", false)).thenReturn(cookie);

        try {
            service.validateXsrfToken((RpcToken) null);
            fail("Expected RpcTokenException");
        } catch (RpcTokenException e) { }

        try {
            RpcToken token = mock(RpcToken.class);
            service.validateXsrfToken(token);
            fail("Expected RpcTokenException");
        } catch (RpcTokenException e) { }

        try {
            XsrfToken token = mock(XsrfToken.class);
            when(token.getToken()).thenReturn("blech");
            service.validateXsrfToken((RpcToken) token);
            fail("Expected RpcTokenException");
        } catch (RpcTokenException e) { }

        XsrfToken token = mock(XsrfToken.class);
        when(token.getToken()).thenReturn(GwtCookieXsrfTokenService.generateTokenFromCookie("value"));
        service.validateXsrfToken((RpcToken) token);
    }

    /** Test validateXsrfToken() for an XsrfToken. */
    @Test public void testValidateXsrfTokenXsrfToken() {
        ISpringContextService springContextService = mock(ISpringContextService.class);
        GwtCookieXsrfTokenService service = new GwtCookieXsrfTokenService();
        service.setSessionCookieName("cookie");
        service.setSpringContextService(springContextService);

        Cookie cookie = mock(Cookie.class);
        when(cookie.getValue()).thenReturn("value");
        when(springContextService.getCookie("cookie", false)).thenReturn(cookie);

        try {
            service.validateXsrfToken((XsrfToken) null);
            fail("Expected RpcTokenException");
        } catch (RpcTokenException e) { }

        try {
            XsrfToken token = mock(XsrfToken.class);
            when(token.getToken()).thenReturn("blech");
            service.validateXsrfToken(token);
            fail("Expected RpcTokenException");
        } catch (RpcTokenException e) { }

        XsrfToken token = mock(XsrfToken.class);
        when(token.getToken()).thenReturn(GwtCookieXsrfTokenService.generateTokenFromCookie("value"));
        service.validateXsrfToken(token);
    }

    /** Test validateXsrfToken() for a string token. */
    @Test public void testValidateXsrfTokenStringToken() {
        ISpringContextService springContextService = mock(ISpringContextService.class);
        GwtCookieXsrfTokenService service = new GwtCookieXsrfTokenService();
        service.setSessionCookieName("cookie");
        service.setSpringContextService(springContextService);

        Cookie cookie = mock(Cookie.class);
        when(cookie.getValue()).thenReturn("value");
        when(springContextService.getCookie("cookie", false)).thenReturn(cookie);

        try {
            service.validateXsrfToken((String) null);
            fail("Expected RpcTokenException");
        } catch (RpcTokenException e) { }

        try {
            service.validateXsrfToken("blech");
            fail("Expected RpcTokenException");
        } catch (RpcTokenException e) { }

        service.validateXsrfToken(GwtCookieXsrfTokenService.generateTokenFromCookie("value"));
    }

    /** Test generateTokenFromCookie(). */
    @Test public void testGenerateTokenFromCookie() {
        Cookie cookie = mock(Cookie.class);

        try {
            GwtCookieXsrfTokenService.generateTokenFromCookie(null, "none");
            fail("Expected RpcTokenException");
        } catch (RpcTokenException e) { }

        try {
            when(cookie.getValue()).thenReturn(null);
            GwtCookieXsrfTokenService.generateTokenFromCookie(cookie, "none");
            fail("Expected RpcTokenException");
        } catch (RpcTokenException e) { }

        try {
            when(cookie.getValue()).thenReturn("");
            GwtCookieXsrfTokenService.generateTokenFromCookie(cookie, "none");
            fail("Expected RpcTokenException");
        } catch (RpcTokenException e) { }

        when(cookie.getValue()).thenReturn("value");
        String expected = StringUtils.toHexString(Md5Utils.getMd5Digest("value".getBytes()));
        String actual = GwtCookieXsrfTokenService.generateTokenFromCookie(cookie, "none");
        assertEquals(expected, actual);

        actual = GwtCookieXsrfTokenService.generateTokenFromCookie("value");
        assertEquals(expected, actual);
    }
}
