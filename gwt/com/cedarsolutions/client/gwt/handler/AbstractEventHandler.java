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
package com.cedarsolutions.client.gwt.handler;


/**
 * Abstract event handler with an associated parent.
 * @param <P> Parent presenter or view associated with handler
 * @author Kenneth J. Pronovici <pronovic@ieee.org>
 */
public class AbstractEventHandler<P> {

    /** Parent presenter or view associated with handler. */
    private P parent;

    /** Constructor. */
    protected AbstractEventHandler(P parent) {
        this.parent = parent;
    }

    /** Parent presenter or view associated with handler. */
    public P getParent() {
        return this.parent;
    }

    /** Parent presenter or view associated with handler. */
    public void setParent(P parent) {
        this.parent = parent;
    }

}
