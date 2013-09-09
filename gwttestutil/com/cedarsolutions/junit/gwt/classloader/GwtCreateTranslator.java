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

import javassist.CannotCompileException;
import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtMethod;
import javassist.NotFoundException;
import javassist.Translator;

import com.google.gwt.core.client.GWT;

/**
 * Specialized translator used to override GWT.create() for stubbed GWT client tests.
 *
 * <p>
 * This works through the magic of Javassist.  Basically, we find the
 * GWT.create() method and replace its body with a call to
 * GwtResourceCreator.create(), which creates all of its resources as Mockito
 * mocks (with some additional logic layered on top to handle certain
 * particular types of interfaces and classes).
 * </p>
 *
 * <p>
 * This class was derived in part from source code in gwt-test-utils.  See
 * README.credits for more information.
 * </p>
 *
 * @author Kenneth J. Pronovici <pronovic@ieee.org>
 */
public class GwtCreateTranslator implements Translator {

    private static final String CREATOR = GwtResourceCreator.class.getCanonicalName();
    private static final String GWT = GWT.class.getCanonicalName();
    private static final String METHOD = "create";
    private static final String BODY = "{ return " + CREATOR + ".create($1); }";

    @Override
    public void start(ClassPool pool) throws NotFoundException, CannotCompileException {
    }

    @Override
    public void onLoad(ClassPool pool, String classname) throws NotFoundException, CannotCompileException {
        if (GWT.equals(classname)) {
            CtClass cc = pool.get(classname);
            for (CtMethod method : cc.getMethods()) {
                if (METHOD.equals(method.getName())) {
                    method.setBody(BODY);
                    return;
                }
            }
        }
    }

}
