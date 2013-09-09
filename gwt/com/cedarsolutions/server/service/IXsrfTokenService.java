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
package com.cedarsolutions.server.service;

import com.google.gwt.user.client.rpc.RpcToken;
import com.google.gwt.user.client.rpc.RpcTokenException;
import com.google.gwt.user.client.rpc.XsrfToken;

/**
 * Service for generating and validating CSRF/XSRF tokens.
 * @author Kenneth J. Pronovici <pronovic@ieee.org>
 */
public interface IXsrfTokenService {

    /**
     * Generate a CSRF/XSRF token.
     * @return CSRF/XSRF token string, always non-null.
     * @throws RpcTokenException If token generation failed.
     */
    String generateXsrfToken() throws RpcTokenException;

    /**
     * Validate a CSRF/XSRF token.
     * @param token   String token provided with an RPC request
     * @throws RpcTokenException If token verification failed.
     */
    void validateXsrfToken(String token) throws RpcTokenException;

    /**
     * Validate a CSRF/XSRF token.
     * @param token   RpcToken provided with an RPC request
     * @throws RpcTokenException If token verification failed.
     */
    void validateXsrfToken(RpcToken token) throws RpcTokenException;

    /**
     * Validate a CSRF/XSRF token.
     * @param token   XsrfToken provided with an RPC request
     * @throws RpcTokenException If token verification failed.
     */
    void validateXsrfToken(XsrfToken token) throws RpcTokenException;

}
