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
package com.cedarsolutions.client.gwt.event;

import com.flipthebird.gwthashcodeequals.EqualsBuilder;
import com.flipthebird.gwthashcodeequals.HashCodeBuilder;
import com.google.gwt.event.dom.client.ClickEvent;

/**
 * Unified event that includes context information about the event.
 * @param <T> Type of the event context
 * @author Kenneth J. Pronovici <pronovic@ieee.org>
 */
public class UnifiedEventWithContext<T> extends UnifiedEvent {

    /** Context associated with this event. */
    private T context;

    /** Create a unified event of a particular type. */
    public UnifiedEventWithContext(UnifiedEventType eventType, T context) {
        super(eventType);
        this.context = context;
    }

    /** Create a unified event based on a ClickEvent. */
    public UnifiedEventWithContext(ClickEvent clickEvent, T context) {
        super(clickEvent);
        this.context = context;
    }

    /** Get the context associated with this event. */
    public T getContext() {
        return this.context;
    }

    /** Compare this object to another object. */
    @Override
    @SuppressWarnings("unchecked")
    public boolean equals(Object obj) {
        UnifiedEventWithContext<T> other = (UnifiedEventWithContext<T>) obj;
        return new EqualsBuilder()
                    .appendSuper(super.equals(obj))
                    .append(this.context, other.context)
                    .isEquals();
    }

    /** Generate a hash code for this object. */
    @Override
    public int hashCode() {
        return new HashCodeBuilder()
                    .appendSuper(super.hashCode())
                    .append(this.context)
                    .toHashCode();
    }

}
