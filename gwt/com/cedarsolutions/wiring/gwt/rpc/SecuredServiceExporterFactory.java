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
package com.cedarsolutions.wiring.gwt.rpc;

import org.gwtwidgets.server.spring.RPCServiceExporter;
import org.gwtwidgets.server.spring.RPCServiceExporterFactory;
import org.springframework.beans.factory.InitializingBean;

import com.cedarsolutions.exception.NotConfiguredException;
import com.cedarsolutions.server.service.IXsrfTokenService;

/**
 * Factory that creates RPC service exporters in terms of SecuredServiceExporter.
 *
 * <p>
 * Typical use of the factory is to place something like this in your
 * <code>rpc-servlet.xml</code> file:
 * </p>
 *
 * <pre>
 *     &lt;bean class="org.gwtwidgets.server.spring.GWTHandler"&gt;
 *      &lt;property name="serviceExporterFactory" ref="securedServiceExporterFactory" /&gt;
 *      &lt;property name="mappings"&gt;
 *          &lt;map&gt;
 *              &lt;entry key="/santaexchange/rpc/gaeUserRpc.rpc" value-ref="gaeUserRpc" /&gt;
 *          &lt;/map&gt;
 *      &lt;/property&gt;
 *  &lt;/bean&gt;
 *
 *  &lt;bean id="securedServiceExporterFactory" class="com.cedarsolutions.wiring.gwt.rpc.SecuredServiceExporterFactory" /&gt;
 * </pre>
 *
 * @author Kenneth J. Pronovici <pronovic@ieee.org>
 */
public class SecuredServiceExporterFactory implements RPCServiceExporterFactory, InitializingBean {

    /** Whether to enable CSRF/XSRF protection. */
    private boolean enableXsrfProtection;

    /** CSRF/XSRF token service. */
    private IXsrfTokenService xsrfTokenService;

    /**
     * Invoked by a bean factory after it has set all bean properties.
     * @throws NotConfiguredException In the event of misconfiguration.
     */
    @Override
    public void afterPropertiesSet() throws NotConfiguredException {
        if (this.xsrfTokenService == null) {
            throw new NotConfiguredException("SecuredServiceExporterFactory is not configured.");
        }
    }

    /** Create an RPC service exporter of the proper type. */
    @Override
    public RPCServiceExporter create() {
        return new SecuredServiceExporter(this.enableXsrfProtection, this.xsrfTokenService);
    }

    public boolean getEnableXsrfProtection() {
        return this.enableXsrfProtection;
    }

    public void setEnableXsrfProtection(boolean enableXsrfProtection) {
        this.enableXsrfProtection = enableXsrfProtection;
    }

    public IXsrfTokenService getXsrfTokenService() {
        return this.xsrfTokenService;
    }

    public void setXsrfTokenService(IXsrfTokenService xsrfTokenService) {
        this.xsrfTokenService = xsrfTokenService;
    }

}
