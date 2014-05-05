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

import java.io.Serializable;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.cedarsolutions.exception.CedarRuntimeException;
import com.cedarsolutions.exception.InvalidDataException;
import com.cedarsolutions.server.service.ISpringContextService;

/**
 * Functionality related to the Spring application context.
 *
 * <p>
 * This is all very Spring-specific and can't really be unit-tested.
 * All of the information is retrieved from Spring static method calls that
 * are supposed to be aware of the context in which they are being invoked.
 * </p>
 *
 * <p>
 * For this to work, you need to enable HTTP sessions in <code>appengine-web.xml</code>
 * </p>
 *
 * <pre>
 *     &lt;!-- Enable HTTP sessions --&gt;
 *     &lt;sessions-enabled&gt;true&lt;/sessions-enabled&gt;
 * </pre>
 *
 * @see <a href="http://forum.springsource.org/archive/index.php/t-38901.html">SpringSource</a>
 * @author Kenneth J. Pronovici <pronovic@ieee.org>
 */
public class SpringContextService extends AbstractService implements ISpringContextService {

    /** Whether to create a session if one is needed and doesn't exist. */
    private boolean createSessionIfNecessary = false;

    /**
     * Get the currently-assigned session id.
     * @return Session id of the current session, possibly null.
     * @see <a href="http://stackoverflow.com/questions/3542026/retrieving-session-id-with-spring-security">Stack Overflow</a>
     */
    @Override
    public String getCurrentSessionId() {
        HttpSession session = this.getRequestSession();
        if (session != null) {
            return session.getId();
        } else {
            return null;
        }
    }

    /**
     * Invalidate the current session.
     * @see <a href="http://stackoverflow.com/questions/2087174/how-to-invalidate-sessions-programmatically">Stack Overflow</a>
     */
    @Override
    public void invalidateCurrentSession() {
        HttpSession session = this.getRequestSessionNeverCreate();
        if (session != null) {
            session.invalidate();
        }
    }

    /** Get a named attribute from the session. */
    @Override
    @SuppressWarnings("unchecked")
    public <T extends Serializable> T getSessionAttribute(String name) {
        HttpSession session = this.getRequestSession();
        if (session != null) {
            return (T) session.getAttribute(name);
        } else {
            throw new CedarRuntimeException("Could not get session attribute: session is null");
        }
    }

    /**
     * Set (update) a named attribute in the session.
     *
     * <p>
     * Note: in some containers, it is critical that value.equals(null) returns
     * false.  If that method call blows up, you won't be able to set the attribute.
     * This is an example of something that works differently in Jetty 6 and 8.
     * </p>
     *
     * @param name   Name of the attribute
     * @param value  Value of the attribute
     */
    @Override
    public <T extends Serializable> void setSessionAttribute(String name, T value) {
        HttpSession session = this.getRequestSession();
        if (session != null) {
            session.setAttribute(name, value);
        } else {
            throw new CedarRuntimeException("Could not set session attribute: session is null");
        }
    }

    /**
     * Get a cookie from the current request, possibly null.
     *
     * <p>
     * If allowDuplicates is set to true, we'll check for duplicate cookies.
     * A duplicate cookie can be a sign of a cookie overwrite attack.
     * </p>
     *
     * @param cookieName       Name of the cookie to retrieve
     * @param allowDuplicates  Whether to allow duplicate cookies
     *
     * @return Cookie with the requested name, possibly null.
     * @throws InvalidDataException If a duplicate cookie is detected.
     *
     * @see com.google.gwt.user.server.Util
     */
    @Override
    public Cookie getCookie(String cookieName, boolean allowDuplicates) throws InvalidDataException {
        Cookie cookieToReturn = null;

        HttpServletRequest request = this.getServletRequest();
        if (request != null) {
            if (request.getCookies() != null) {
                for (Cookie cookie : request.getCookies()) {
                    if (cookieName.equals(cookie.getName())) {
                        if (cookieToReturn == null) {
                            if (allowDuplicates) {
                                return cookie;
                            } else {
                                cookieToReturn = cookie;
                            }
                        } else {
                            throw new InvalidDataException("Duplicate cookie [" + cookieName + "]: possible cookie override attack?");
                        }
                    }
                }
            }
        }

        return cookieToReturn;
    }

    /** Whether to create a session if one is needed and doesn't exist. */
    public boolean getCreateSessionIfNecessary() {
        return this.createSessionIfNecessary;
    }

    /** Whether to create a session if one is needed and doesn't exist. */
    public void setCreateSessionIfNecessary(boolean createSessionIfNecessary) {
        this.createSessionIfNecessary = createSessionIfNecessary;
    }

    /** Get the current servlet request's HTTP session, possibly null, never creating it if it doesn't exist. */
    protected HttpSession getRequestSessionNeverCreate() {
        HttpServletRequest request = this.getServletRequest();
        if (request != null) {
            return request.getSession(false);
        }

        return null;
    }

    /** Get the current servlet request's HTTP session, possibly null. */
    protected HttpSession getRequestSession() {
        HttpServletRequest request = this.getServletRequest();
        if (request != null) {
            return request.getSession(this.createSessionIfNecessary);
        }

        return null;
    }

    /** Get the current servlet request, possibly null. */
    protected HttpServletRequest getServletRequest() {
        ServletRequestAttributes requestAttributes = this.getServletRequestAttributes();
        if (requestAttributes != null) {
            return requestAttributes.getRequest();
        }

        return null;
    }

    /** Get the context's request attributes, possibly null. */
    protected ServletRequestAttributes getServletRequestAttributes() {
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        if (requestAttributes instanceof ServletRequestAttributes) {
            return (ServletRequestAttributes) requestAttributes;
        }

        return null;
    }
}
