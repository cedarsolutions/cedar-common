/* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 *
 *              C E D A R
 *          S O L U T I O N S       "Software done right."
 *           S O F T W A R E
 *
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 *
 * Copyright (c) 2011-2012 Kenneth J. Pronovici.
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
package com.cedarsolutions.dao.gae.impl;

import java.util.ArrayList;
import java.util.List;

import com.googlecode.objectify.Objectify;
import com.googlecode.objectify.ObjectifyFactory;
import com.googlecode.objectify.ObjectifyOpts;
import com.googlecode.objectify.ObjectifyService;

/**
 * "Smart" proxy over ObjectifyService.
 *
 * <p>
 * There's no obvious way to make Objectify tell you what classes have already
 * been registered, but it throws an exception if you try to register a class
 * more than once.  This proxy class works around the problem by keeping a
 * list of previously-registered classes.  It's a full proxy, so it should
 * be used instead of referencing the original ObjectifyService.
 * </p>
 *
 * @author Kenneth J. Pronovici <pronovic@ieee.org>
 */
public class ObjectifyServiceProxy {

    /** Singleton instance. */
    private static ObjectifyServiceProxy INSTANCE;

    /** List of classes which have been registered, as recorded by the singleton. */
    private List<Class<?>> registered = new ArrayList<Class<?>>();

    /** Default constructor is private so class cannot be instantiated. */
    private ObjectifyServiceProxy() {
    }

    /** Get an instance of this class to use. */
    public static synchronized ObjectifyServiceProxy getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new ObjectifyServiceProxy();
        }

        return INSTANCE;
    }

    public Objectify begin() {
        return ObjectifyService.begin();
    }

    public Objectify begin(ObjectifyOpts opts) {
        return ObjectifyService.begin(opts);
    }

    public Objectify beginTransaction() {
        return ObjectifyService.beginTransaction();
    }

    public ObjectifyFactory factory() {
        return ObjectifyService.factory();
    }

    public synchronized void register(Class<?> clazz) {
        if (!registered.contains(clazz)) {
            registered.add(clazz);
            ObjectifyService.register(clazz);
        }
    }

}
