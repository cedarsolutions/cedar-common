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
package com.cedarsolutions.client.gwt.module;

import java.util.Set;

import com.mvp4g.client.event.EventFilter;

/**
 * Filter that requires the session to be filled in.
 *
 * <p>
 * Child classes inherit from this class and provide their own specialized
 * rules that depend on the session information (i.e. to require that a user
 * is logged in, or something like that).
 * </p>
 *
 * <p>
 * If session data is not available when the filter is invoked, we kick off
 * an event that initializes the session and then re-invokes this event after the
 * session data is available.  This should be safe, because the async call to
 * initialize the session should either succeed, fail, or time out. There
 * shouldn't be an opportunity to get into a loop.
 * </p>
 *
 * <p>
 * Child classes also need to set an appropriate list of events to exclude from
 * filtering.  This list should include start and initialization events that
 * don't require a session, pure rendering steps that are just handling content
 * that has already been rendered (like replaceRootBody), and events whose
 * primary purpose is to clear the session.
 * </p>
 *
 * @param <E> Type of the event bus used by the event handler
 *
 * @author Kenneth J. Pronovici <pronovic@ieee.org>
 */
public abstract class ModuleSessionEventFilter<E extends SessionAwareEventBus> implements EventFilter<E> {

    /** Filter an event handled by this module. */
    @Override
    public boolean filterEvent(String eventType, Object[] params, E eventBus) {
        if (this.getExcludedEventTypes().contains(eventType)) {
            return true;
        } else {
            if (!this.getClientSession().isInitialized()) {
                // If we don't have a session, kick off an event to get it before continuing
                eventBus.setFilteringEnabled(false);
                eventBus.initializeSession(eventType, params);
                eventBus.setFilteringEnabled(true);
                return false;
            } else {
                return this.filterEventOnceInitialized(eventType, params, eventBus);
            }
        }
    }

    /** Get the client session in use by this module. */
    protected abstract IClientSession getClientSession();

    /** Get the event types that should be excluded from filtering. */
    protected abstract Set<String> getExcludedEventTypes();

    /** Filter the event once the session has been initialized. */
    protected abstract boolean filterEventOnceInitialized(String eventType, Object[] params, E eventBus);

}
