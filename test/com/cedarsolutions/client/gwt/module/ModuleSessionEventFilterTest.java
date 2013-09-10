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
package com.cedarsolutions.client.gwt.module;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verifyZeroInteractions;
import static org.mockito.Mockito.when;

import java.util.HashSet;
import java.util.Set;

import org.junit.Test;
import org.mockito.InOrder;
import org.mockito.Mockito;

import com.cedarsolutions.junit.gwt.StubbedClientTestCase;

/**
 * Unit tests for ModuleSessionEventFilter.
 * @author Kenneth J. Pronovici <pronovic@ieee.org>
 */
public class ModuleSessionEventFilterTest extends StubbedClientTestCase {

    /** Test filterEvent() for an excluded event. */
    @Test public void testFilterEventExcludedEvent() {
        TestEventBus eventBus = mock(TestEventBus.class);
        String eventType = "one";
        Object[] params = new Object[] { "hello", };

        ConcreteModuleSessionEventFilter filter = new ConcreteModuleSessionEventFilter();
        filter.filterResult = false; // so we can tell that it wasn't called

        assertTrue(filter.filterEvent(eventType, params, eventBus));
        verifyZeroInteractions(eventBus);  // the event bus should not be involved
    }

    /** Test filterEvent() for a non-excluded event when there is no client session. */
    @Test public void testFilterEventNoClientSession() {
        TestEventBus eventBus = mock(TestEventBus.class);
        String eventType = "blech";
        Object[] params = new Object[] { "hello", };

        ConcreteModuleSessionEventFilter filter = new ConcreteModuleSessionEventFilter();
        when(filter.clientSession.isInitialized()).thenReturn(false);
        filter.filterResult = true; // so we can tell that it wasn't called

        assertFalse(filter.filterEvent(eventType, params, eventBus));
        InOrder inOrder = Mockito.inOrder(eventBus);
        inOrder.verify(eventBus).setFilteringEnabled(false);
        inOrder.verify(eventBus).initializeSession(eventType, params);
        inOrder.verify(eventBus).setFilteringEnabled(true);
    }

    /** Test filterEvent() for a non-excluded event when there is a client session. */
    @Test public void testFilterEventWithClientSession() {
        TestEventBus eventBus = mock(TestEventBus.class);
        String eventType = "blech";
        Object[] params = new Object[] { "hello", };

        ConcreteModuleSessionEventFilter filter = new ConcreteModuleSessionEventFilter();
        when(filter.clientSession.isInitialized()).thenReturn(true);
        filter.filterResult = false;  // so we can tell that it wasn't seen as an excluded event
        assertFalse(filter.filterEvent(eventType, params, eventBus));
        verifyZeroInteractions(eventBus);  // the event bus should not be involved
    }

    /** Concrete class that we can test with. */
    private static class ConcreteModuleSessionEventFilter extends ModuleSessionEventFilter<TestEventBus> {

        protected IClientSession clientSession;
        protected Set<String> excludedEventTypes;
        protected boolean filterResult;

        public ConcreteModuleSessionEventFilter() {
            this.clientSession = mock(IClientSession.class);

            this.excludedEventTypes = new HashSet<String>();
            this.excludedEventTypes.add("one");

            this.filterResult = false;
        }

        @Override
        protected IClientSession getClientSession() {
            return this.clientSession;
        }

        @Override
        protected Set<String> getExcludedEventTypes() {
            return this.excludedEventTypes;
        }

        @Override
        protected boolean filterEventOnceInitialized(String eventType, Object[] params, TestEventBus eventBus) {
            return this.filterResult;
        }

    }

    /** Event bus that we can test with. */
    private interface TestEventBus extends SessionAwareEventBus {
    }

}
