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
package com.cedarsolutions.junit.gwt;

import java.lang.reflect.Constructor;

import org.junit.runner.Description;
import org.junit.runner.Runner;
import org.junit.runner.manipulation.Filter;
import org.junit.runner.manipulation.Filterable;
import org.junit.runner.manipulation.NoTestsRemainException;
import org.junit.runner.manipulation.Sortable;
import org.junit.runner.manipulation.Sorter;
import org.junit.runner.notification.RunNotifier;
import org.junit.runners.BlockJUnit4ClassRunner;

import com.cedarsolutions.junit.gwt.classloader.GwtClassLoader;


/**
 * JUnit 4.5 test runner used for running stubbed GWT client tests.
 *
 * <p>
 * This test runner configures JUnit 4.5 to use a different classloader that
 * stubs out certain GWT operations like GWT.create().  This way, unit tests
 * that are annotated as RunWith(GwtStubbedTestRunner.class) don't need to run
 * real GWT client tests (i.e. extending GWTTestCase). Instead, they can
 * validate using mocks.  This is a lot faster and easier, especially for MVP4G
 * presenters and event handlers.  You will probably need to write true client
 * tests for views and other client-side widgets.
 * </p>
 *
 * <p>
 * This class was derived in part from source code in gwt-test-utils.  See
 * README.credits for more information.
 * </p>
 *
 * @author Kenneth J. Pronovici <pronovic@ieee.org>
 */
public class GwtStubbedTestRunner extends Runner implements Filterable, Sortable {

    private static final String JUNIT4_TEST_RUNNER = BlockJUnit4ClassRunner.class.getCanonicalName();

    private final Runner runner;

    public GwtStubbedTestRunner(Class<?> clazz) throws Exception {
        Class<?> runnerClass = GwtClassLoader.get().loadClass(JUNIT4_TEST_RUNNER);
        Constructor<?> constructor = runnerClass.getConstructor(Class.class);
        runner = (Runner) constructor.newInstance(GwtClassLoader.get().loadClass(clazz.getCanonicalName()));
    }

    @Override
    public void run(RunNotifier notifier) {
        runner.run(notifier);
    }

    @Override
    public int testCount() {
        return runner.testCount();
    }

    @Override
    public Description getDescription() {
        return runner.getDescription();
    }

    @Override
    public void filter(Filter filter) throws NoTestsRemainException {
        if (Filterable.class.isInstance(runner)) {
            ((Filterable) runner).filter(filter);
        }
    }

    @Override
    public void sort(Sorter sorter) {
        if (Sortable.class.isInstance(runner)) {
            ((Sortable) runner).sort(sorter);
        }
    }

}
