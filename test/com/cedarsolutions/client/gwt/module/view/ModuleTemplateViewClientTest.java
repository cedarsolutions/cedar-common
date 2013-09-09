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
package com.cedarsolutions.client.gwt.module.view;

import java.util.Iterator;

import com.cedarsolutions.client.gwt.junit.ClientTestCase;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.Widget;

/**
 * Client-side unit tests for ModuleTemplateView.
 * @author Kenneth J. Pronovici <pronovic@ieee.org>
 */
public class ModuleTemplateViewClientTest extends ClientTestCase {

    /** Test getViewWidget(). */
    public void testGetViewWidget() {
        ConcreteModuleTemplateView view = new ConcreteModuleTemplateView();
        assertSame(view, view.getViewWidget());
    }

    /** Test replaceModuleBody(). */
    public void testReplaceModuleBody() {
        StubbedModuleBody replacement = new StubbedModuleBody();
        ConcreteModuleTemplateView view = new ConcreteModuleTemplateView();
        view.replaceModuleBody(replacement);
        assertTrue(((StubbedModuleBody) view.getModuleBody()).clearCalled);
        assertSame(replacement, ((StubbedModuleBody) view.getModuleBody()).added);
    }

    /** Concrete class that we can use for testing. */
    private static class ConcreteModuleTemplateView extends ModuleTemplateView {
        private StubbedModuleBody moduleBody = new StubbedModuleBody();

        @Override
        protected Panel getModuleBody() {
            return moduleBody;
        }
    }

    /** Widget that we can test with. */
    private static class StubbedModuleBody extends Panel {
        protected Widget added;
        protected boolean clearCalled;

        @Override
        public void add(Widget child) {
            this.added = child;
        }

        @Override
        public void clear() {
            this.clearCalled = true;
        }

        @Override
        public Iterator<Widget> iterator() {
            return null;
        }

        @Override
        public boolean remove(Widget child) {
            return false;
        }
    }
}
