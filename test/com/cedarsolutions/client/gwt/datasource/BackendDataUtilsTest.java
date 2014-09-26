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
package com.cedarsolutions.client.gwt.datasource;

import static org.mockito.Matchers.isA;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Test;

/**
 * Unit tests for BackendDataUtils.
 * @author Kenneth J. Pronovici <pronovic@ieee.org>
 */
public class BackendDataUtilsTest {

    /** Test setStandardEventHandlers(). */
    @SuppressWarnings("unchecked")
    @Test public void testSetStandardEventHandlers() {
        IBackendDataRenderer<String, String> renderer = mock(IBackendDataRenderer.class);
        IBackendDataPresenter<String, String> presenter = mock(IBackendDataPresenter.class);
        when(presenter.getRenderer()).thenReturn(renderer);

        BackendDataUtils.setStandardEventHandlers(presenter);
        verify(renderer).setInitializationEventHandler(isA(BackendDataInitializeHandler.class));
        verify(renderer).setRefreshEventHandler(isA(BackendDataRefreshHandler.class));
        verify(renderer).setCriteriaResetEventHandler(isA(BackendDataCriteriaResetHandler.class));
    }

}
