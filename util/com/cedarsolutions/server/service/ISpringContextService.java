/* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 *
 *              C E D A R
 *          S O L U T I O N S       "Software done right."
 *           S O F T W A R E
 *
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 *
 * Copyright (c) 2013-2014 Kenneth J. Pronovici.
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
package com.cedarsolutions.server.service;

import java.io.Serializable;

import javax.servlet.http.Cookie;

import com.cedarsolutions.exception.InvalidDataException;

/**
 * Functionality related to the Spring application context.
 * @author Kenneth J. Pronovici <pronovic@ieee.org>
 */
public interface ISpringContextService {

    /**
     * Get the currently-assigned session id.
     * @return Session id of the current session, possibly null.
     */
    String getCurrentSessionId();

    /** Invalidate the current session. */
    void invalidateCurrentSession();

    /** Get a named attribute from the session. */
    <T extends Serializable> T getSessionAttribute(String name);

    /**
     * Set (update) a named attribute in the session.
     *
     * <p>
     * Note: in some containers, it is <b>critical</b> that <code>value.equals(null)</code>
     * returns <code>false</code>.  If that method call blows up, you won't be able to
     * set the attribute.  (For instance, Jetty 8 blows up, but containers such as
     * Jetty 6 or WebSphere 7 don't.)
     * </p>
     *
     * @param name   Name of the attribute
     * @param value  Value of the attribute
     */
    <T extends Serializable> void setSessionAttribute(String name, T value);

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
     * @return Cookie with the requested name, possibly null.
     *
     * @throws InvalidDataException If a duplicate cookie is detected.
     */
    Cookie getCookie(String cookieName, boolean allowDuplicates) throws InvalidDataException;

}
