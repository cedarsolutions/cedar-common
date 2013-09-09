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
package com.cedarsolutions.junit.gwt.classloader;

import javassist.ClassPool;
import javassist.Loader;

import com.cedarsolutions.exception.CedarRuntimeException;

/**
 * Specialized class loader used for stubbed GWT client tests.
 *
 * <p>
 * This works through the magic of Javassist.  Basically, we create a new
 * classloader that applies specific translations to certain classes.
 * </p>
 *
 * <p>
 * This class was derived in part from source code in gwt-test-utils.  See
 * README.credits for more information.
 * </p>
 *
 * @author Kenneth J. Pronovici <pronovic@ieee.org>
 */
public class GwtClassLoader extends Loader {

    private static final GwtClassLoader INSTANCE = new GwtClassLoader(ClassPool.getDefault());

    private GwtClassLoader(ClassPool classPool) {
        super(classPool);
        handleDelegatedClasses();
        addTranslators(classPool);
    }

    public static synchronized GwtClassLoader get() {
        Thread.currentThread().setContextClassLoader(INSTANCE);
        return INSTANCE;
    }

    public static void reset() {
        Thread.currentThread().setContextClassLoader(INSTANCE.getParent());
    }

    private void addTranslators(ClassPool classPool) {
        try {
            addTranslator(classPool, new GwtCreateTranslator());
        } catch (Exception e) {
            throw new CedarRuntimeException("Error adding translator: " + e.getMessage(), e);
        }
    }

    private void handleDelegatedClasses() {
        // This list was originally derived from the gwt-test-utils.properties file.
        // It should eventually be made configurable, I think. For now, I'm just going
        // to leave it and see what problems I find.
        delegateLoadingOf("javassist.");
        delegateLoadingOf("org.aopalliance.");
        delegateLoadingOf("org.junit.");
        delegateLoadingOf("org.apache.");
        delegateLoadingOf("com.cedarsolutions.util.junit.gwt.classloader.GwtClassLoader");
        delegateLoadingOf("org.springframework.");
    }

}
