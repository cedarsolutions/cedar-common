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
package com.cedarsolutions.client.gwt.rpc.proxy;


/**
 * Singleton to hold XSRF RPF proxy configuration.
 *
 * <p>
 * This feels like a humongous hack.  I would like to make the XSRF RPC timeout
 * configurable.  However, there's no obvious way to do it.  The proxy is
 * instantiated on the client side at runtime, but there's no hook to set
 * properties on the proxy when it's instantiated.  I've fallen back on using
 * this singleton to hold the value.  If the application doesn't set a value,
 * we'll use a default of 30 seconds.  If the caller sets a value, we'll use
 * that instead.
 * </p>
 *
 * <p>
 * Theoretically, this should be set globally (once per application).  However,
 * that's difficult to do in a GWT application.  So, it's probably ok if
 * callers set the value before each RPC invocation.  That's ugly, but since
 * client-side code is single-threaded by definition, it shouldn't be too big
 * of a deal.
 * </p>
 *
 * @author Kenneth J. Pronovici <pronovic@ieee.org>
 */
public class XsrfRpcProxyConfig {

    /** Default timeout to use if nothing is configured. */
    private static final int DEFAULT_TIMEOUT_MS = 30000;

    /** Singleton instance. */
    private static XsrfRpcProxyConfig INSTANCE;

    /** The configured timeout, in milliseconds. */
    private Integer timeoutMs;

    /** Default constructor is private so class cannot be instantiated. */
    private XsrfRpcProxyConfig() {
    }

    /** Get an instance of this class to use. */
    public static synchronized XsrfRpcProxyConfig getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new XsrfRpcProxyConfig();
        }

        return INSTANCE;
    }

    /** Set the timeout, in milliseconds. */
    public synchronized void setTimeoutMs(int timeoutMs) {
        this.timeoutMs = timeoutMs;
    }

    /** Get the timeout, in milliseconds. */
    public synchronized int getTimeoutMs() {
        return this.timeoutMs;
    }

    /** Get the timeout, using a default if none is set. */
    public synchronized int getConfiguredTimeoutMs() {
        return this.timeoutMs == null ? DEFAULT_TIMEOUT_MS : this.timeoutMs;
    }

}
