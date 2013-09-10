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

import static com.cedarsolutions.client.gwt.event.UnifiedEventType.CLICK_EVENT;

import com.flipthebird.gwthashcodeequals.EqualsBuilder;
import com.flipthebird.gwthashcodeequals.HashCodeBuilder;
import com.google.gwt.event.dom.client.ClickEvent;

/**
 * Unified event that can be handled by ViewEventHandler.
 *
 * <p>
 * There are a variety of different events that a view might need to handle.
 * The usual strategy is to delegate event handling back to the presenter.
 * However, if the presenter knows exactly what kind of event to handle, this
 * makes for a tight coupling between view and presenter.  For instance, what
 * if the view wants to change a button to a menu item?  It's a different kind
 * of event handler, so the presenter would also need to change, even if all it
 * cares about is that the item was clicked on.  That seems wrong, since the main
 * reason to separate the view and presenter is to give us some flexibility in
 * how to implement the UI.
 * </p>
 *
 * <p>
 * This class allows the view interface to be structured in terms of a generic
 * event handler.  If the presenter is just handling the fact that an event
 * occurred (i.e. that something was clicked), then it won't have to be
 * reimplemented just because the UI element changed.  Of course, if the
 * presenter needs to do special handling based on the contents of the event
 * itself, the presenter will still have to change... but in that case we
 * haven't really lost anything.
 * </p>
 *
 * @author Kenneth J. Pronovici <pronovic@ieee.org>
 */
public class UnifiedEvent {

    /** Type of the event. */
    private UnifiedEventType eventType;

    /** Underlying click event, if any. */
    private ClickEvent clickEvent;

    /** Create a unified event of a particular type. */
    public UnifiedEvent(UnifiedEventType eventType) {
        this.eventType = eventType;
    }

    /** Create a unified event based on a ClickEvent. */
    public UnifiedEvent(ClickEvent clickEvent) {
        this(CLICK_EVENT);
        this.clickEvent = clickEvent;
    }

    /** Get the event type. */
    public UnifiedEventType getEventType() {
        return this.eventType;
    }

    /** Get the underlying click event, if any. */
    public ClickEvent getClickEvent() {
        return this.clickEvent;
    }

    /** Compare this object to another object. */
    @Override
    public boolean equals(Object obj) {
        UnifiedEvent other = (UnifiedEvent) obj;
        return new EqualsBuilder()
                    .append(this.eventType, other.eventType)
                    .append(this.clickEvent, other.clickEvent)
                    .isEquals();
    }

    /** Generate a hash code for this object. */
    @Override
    public int hashCode() {
        return new HashCodeBuilder()
                    .append(this.eventType)
                    .append(this.clickEvent)
                    .toHashCode();
    }

}
