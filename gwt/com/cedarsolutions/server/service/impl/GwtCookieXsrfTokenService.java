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

import javax.servlet.http.Cookie;

import org.apache.log4j.Logger;

import com.cedarsolutions.exception.NotConfiguredException;
import com.cedarsolutions.server.service.ISpringContextService;
import com.cedarsolutions.server.service.IXsrfTokenService;
import com.cedarsolutions.util.LoggingUtils;
import com.google.gwt.user.client.rpc.RpcToken;
import com.google.gwt.user.client.rpc.RpcTokenException;
import com.google.gwt.user.client.rpc.XsrfToken;
import com.google.gwt.util.tools.shared.Md5Utils;
import com.google.gwt.util.tools.shared.StringUtils;

/**
 * GWT CSRF/XSRF token service implemented in terms of the session cookie.
 * @author Kenneth J. Pronovici <pronovic@ieee.org>
 */
public class GwtCookieXsrfTokenService extends AbstractService implements IXsrfTokenService {

    /** Default value returned when token service is disabled. */
    public static final String DEFAULT_TOKEN = "none";

    /** Logger instance. */
    private static Logger LOGGER = LoggingUtils.getLogger(GwtCookieXsrfTokenService.class);

    /** Name of the configured session cookie. */
    private String sessionCookieName;  // I chose not to use a config object because this should change rarely

    /** Spring context service. */
    private ISpringContextService springContextService;

    /**
     * Invoked by a bean factory after it has set all bean properties.
     * @throws NotConfiguredException In the event of misconfiguration.
     */
    @Override
    public void afterPropertiesSet() throws NotConfiguredException {
        super.afterPropertiesSet();
        if (this.sessionCookieName == null || this.springContextService == null) {
            throw new NotConfiguredException("GwtCookieXsrfTokenService is not properly configured.");
        }
    }

    /**
     * Generate a CSRF/XSRF token.
     * @return CSRF/XSRF token string, always non-null.
     * @throws RpcTokenException If token generation failed.
     */
    @Override
    public String generateXsrfToken() throws RpcTokenException {
        String token = generateTokenFromCookie(this.getSessionCookie(), "generate");
        LOGGER.debug("Generated CSRF/XSRF token [" + token + "].");
        return token;
    }

    /**
     * Validate a CSRF/XSRF token.
     * @param token   RpcToken provided with an RPC request
     * @throws RpcTokenException If token verification failed.
     */
    @Override
    public void validateXsrfToken(RpcToken token) throws RpcTokenException {
        try {
            this.validateXsrfToken((XsrfToken) token);
        } catch (ClassCastException e) {
            LOGGER.error("Possible CSRF/XSRF attack: provided token is not XsrfToken");
            throw new RpcTokenException("Unable to verify CSRF/XSRF token: provided token is not XsrfToken");
        }
    }

    /**
     * Validate a CSRF/XSRF token.
     * @param token   XsrfToken provided with an RPC request
     * @throws RpcTokenException If token verification failed.
     */
    @Override
    public void validateXsrfToken(XsrfToken token) throws RpcTokenException {
        if (token == null) {
            LOGGER.error("Possible CSRF/XSRF attack: token not provided.");
            throw new RpcTokenException("Unable to verify CSRF/XSRF token: token not provided");
        }

        this.validateXsrfToken(token.getToken());
    }

    /**
     * Validate a CSRF/XSRF token.
     * @param token   String token provided with an RPC request
     * @throws RpcTokenException If token verification failed.
     */
    @Override
    public void validateXsrfToken(String token) throws RpcTokenException {
        if (token == null) {
            LOGGER.error("Possible CSRF/XSRF attack: token not provided.");
            throw new RpcTokenException("Unable to verify CSRF/XSRF token: token not provided");
        }

        String expectedToken = generateTokenFromCookie(this.getSessionCookie(), "verify");
        if (!expectedToken.equals(token)) {
            LOGGER.error("Possible CSRF/XSRF attack: expected [" + expectedToken + "], but got [" + token + "]");
            throw new RpcTokenException("CSRF/XSRF token not valid: possible CSRF/XSRF attack");
        }

        LOGGER.debug("CSRF/XSRF token [" + token + "] was valid.");
    }

    /** Generate a CSRF/XSRF token from a session cookie, validating that the cookie looks sensible. */
    protected static String generateTokenFromCookie(Cookie sessionCookie, String action) {
        if (sessionCookie == null || sessionCookie.getValue() == null || sessionCookie.getValue().length() == 0) {
            LOGGER.error("Unable to " + action + " CSRF/XSRF token: session cookie missing or empy");
            throw new RpcTokenException("Unable to " + action + " CSRF/XSRF token: session cookie missing or empy");
        }

        return generateTokenFromCookie(sessionCookie.getValue());
    }

    /** Generate a CSRF/XSRF token from a session cookie value. */
    protected static String generateTokenFromCookie(String sessionCookie) {
        return StringUtils.toHexString(Md5Utils.getMd5Digest(sessionCookie.getBytes()));
    }

    /** Get the session cookie from the Spring context. */
    protected Cookie getSessionCookie() {
        return this.springContextService.getCookie(this.getSessionCookieName(), false);
    }

    public String getSessionCookieName() {
        return this.sessionCookieName;
    }

    public void setSessionCookieName(String sessionCookieName) {
        this.sessionCookieName = sessionCookieName;
    }

    public ISpringContextService getSpringContextService() {
        return this.springContextService;
    }

    public void setSpringContextService(ISpringContextService springContextService) {
        this.springContextService = springContextService;
    }

}
