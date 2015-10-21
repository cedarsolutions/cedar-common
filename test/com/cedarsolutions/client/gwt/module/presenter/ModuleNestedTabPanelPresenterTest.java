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
package com.cedarsolutions.client.gwt.module.presenter;

import static org.junit.Assert.assertSame;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Test;

import com.cedarsolutions.client.gwt.module.ModuleEventBus;
import com.cedarsolutions.client.gwt.module.view.IModuleTabPanelView;
import com.cedarsolutions.client.gwt.module.view.IModuleTabView;
import com.cedarsolutions.junit.gwt.StubbedClientTestCase;
import com.google.gwt.user.client.ui.IsWidget;

/**
 * Unit tests for ModuleNestedTabPanelPresenter.
 * @author Kenneth J. Pronovici <pronovic@ieee.org>
 */
public class ModuleNestedTabPanelPresenterTest extends StubbedClientTestCase {

    /** Test replaceModuleBody(). */
    @Test public void testReplaceModuleBody() {
        IsWidget viewWidget = mock(IsWidget.class);
        IModuleTabPanelView parentView = mock(IModuleTabPanelView.class);
        when(parentView.getViewWidget()).thenReturn(viewWidget);

        ConcreteModuleNestedTabPanelPresenter presenter = createPresenter();
        when(presenter.getView().getParentView()).thenReturn(parentView);

        presenter.replaceModuleBody();
        verify(presenter.getEventBus()).replaceModuleBody(viewWidget);
    }

    /** Create a properly-mocked presenter, including everything that needs to be injected. */
    private static ConcreteModuleNestedTabPanelPresenter createPresenter() {
        TestEventBus eventBus = mock(TestEventBus.class);
        ITestView view = mock(ITestView.class);

        ConcreteModuleNestedTabPanelPresenter presenter = new ConcreteModuleNestedTabPanelPresenter();
        presenter.setEventBus(eventBus);
        presenter.setView(view);

        assertSame(eventBus, presenter.getEventBus());
        assertSame(view, presenter.getView());

        return presenter;
    }

    /** Concrete class that we can use for testing. */
    private static class ConcreteModuleNestedTabPanelPresenter extends ModuleNestedTabPanelPresenter<IModuleTabView, TestEventBus> {
    }

    /** Event bus that we can test with. */
    private interface TestEventBus extends ModuleEventBus {
    }

    /** View interface that we can test with. */
    private interface ITestView extends IModuleTabView {
    }

}
