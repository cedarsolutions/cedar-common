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

import com.google.gwt.core.ext.GeneratorContext;
import com.google.gwt.core.ext.RebindResult;
import com.google.gwt.core.ext.TreeLogger;
import com.google.gwt.core.ext.UnableToCompleteException;
import com.google.gwt.core.ext.typeinfo.JClassType;
import com.google.gwt.user.rebind.rpc.ProxyCreator;
import com.google.gwt.user.rebind.rpc.ServiceInterfaceProxyGenerator;

/**
 * GWT generator to create a customized proxy that knows how to make CSRF/XSRF-protected requests.
 *
 * <p>
 * When you request an RPC interface with <code>GWT.create(MyRemoteService.class)</code>,
 * GWT normally does some magic to implement an asynchronous proxy over your
 * remote service interface.  Below, we generate customized code that knows how
 * to call the CSRF/XSRF token service before making the actual RPC call.  That
 * way, RPC clients don't need to know anything about the way the service is
 * actually invoked &mdash; it's all controlled by annotations.
 * </p>
 *
 * <p>
 * To configure this generator, put the following lines in your <code>.gwt.xml</code>
 * configuration file:
 * </p>
 *
 * <pre>
 *    &lt;generate-with class="com.cedarsolutions.wiring.gwt.rpc.XsrfRpcProxyGenerator"&gt;
 *       &lt;when-type-assignable class="com.google.gwt.user.client.rpc.RemoteService" /&gt;
 *    &lt;/generate-with&gt;
 * </pre>
 *
 * @see <a href="https://developers.google.com/web-toolkit/doc/latest/DevGuideSecurityRpcXsrf">GWT RPC XSRF protection</a>
 * @author Kenneth J. Pronovici <pronovic@ieee.org>
 */
public class XsrfRpcProxyGenerator extends ServiceInterfaceProxyGenerator {

    /** Tree logger that should be used for messages. */
    protected TreeLogger logger;

    /** Generator context that is in place. */
    protected GeneratorContext generatorContext;

    /** Instantiate the proxy generator. */
    public XsrfRpcProxyGenerator() {
        super();
    }

    /** Hook into the generatior process so we can get access to the generator context. */
    @Override
    public RebindResult generateIncrementally(TreeLogger logger, GeneratorContext ctx, String requestedClass) throws UnableToCompleteException {
        this.setLogger(logger);
        this.setGeneratorContext(ctx);
        return super.generateIncrementally(this.logger, ctx, requestedClass);
    }

    /** Use our customzied proxy creator. */
    @Override
    protected ProxyCreator createProxyCreator(JClassType remoteService) {
        return new XsrfRpcProxyCreator(remoteService);
    }

    /** Set the logger if it's not set already. */
    protected void setLogger(TreeLogger logger) {
        if (this.logger == null) {
            this.logger = logger;
        }
    }

    /** Set the generator context if not set already. */
    protected void setGeneratorContext(GeneratorContext generatorContext) {
        if (this.generatorContext == null) {
            this.generatorContext = generatorContext;
        }
    }

    /** Log a message, if the logger is configured. */
    protected void log(String message) {
        if (this.logger != null) {
            this.logger.log(TreeLogger.DEBUG, message);
        }
    }

}
