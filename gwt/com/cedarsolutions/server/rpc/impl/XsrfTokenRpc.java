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
package com.cedarsolutions.server.rpc.impl;

import com.cedarsolutions.client.gwt.rpc.IXsrfTokenRpc;
import com.cedarsolutions.exception.NotConfiguredException;
import com.cedarsolutions.server.service.IXsrfTokenService;
import com.cedarsolutions.server.service.impl.AbstractService;
import com.google.gwt.user.client.rpc.RpcTokenException;

/**
 * Provides CSRF/XSRF tokens to callers.
 * @author Kenneth J. Pronovici <pronovic@ieee.org>
 */
public class XsrfTokenRpc extends AbstractService implements IXsrfTokenRpc {

    /** CSRF/XSRF token service. */
    private IXsrfTokenService xsrfTokenService;

    /**
     * Invoked by a bean factory after it has set all bean properties.
     * @throws NotConfiguredException In the event of misconfiguration.
     */
    @Override
    public void afterPropertiesSet() throws NotConfiguredException {
        super.afterPropertiesSet();
        if (this.xsrfTokenService == null) {
            throw new NotConfiguredException("XsrfTokenRpc is not properly configured.");
        }
    }

    /**
     * Generate a CSRF/XSRF token to be passed in with an RPC request.
     * @return CSRF/XSRF token string, always non-null.
     * @throws RpcTokenException If token generation failed.
     */
    @Override
    public String generateXsrfToken() throws RpcTokenException {
        return this.xsrfTokenService.generateXsrfToken();
    }

    public IXsrfTokenService getXsrfTokenService() {
        return this.xsrfTokenService;
    }

    public void setXsrfTokenService(IXsrfTokenService xsrfTokenService) {
        this.xsrfTokenService = xsrfTokenService;
    }

}
