/* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 *
 *              C E D A R
 *          S O L U T I O N S       "Software done right."
 *           S O F T W A R E
 *
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 *
 * Copyright (c) 2011-2013 Kenneth J. Pronovici.
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
package com.cedarsolutions.shared.domain;

import java.util.ArrayList;
import java.util.List;

import com.flipthebird.gwthashcodeequals.EqualsBuilder;
import com.flipthebird.gwthashcodeequals.HashCodeBuilder;


/**
 * Details about a set of validation errors.
 * @author Kenneth J. Pronovici <pronovic@ieee.org>
 */
public class ValidationErrors extends TranslatableDomainObject {

    private static final long serialVersionUID = 1L;
    private LocalizableMessage summary;
    private List<LocalizableMessage> messages;

    /** Create an empty set of details. */
    public ValidationErrors() {
        this(null);
    }

    /** Create details with a specific summary. */
    public ValidationErrors(String key, String text) {
        this(key, null, text);
    }

    /** Create details with a specific summary. */
    public ValidationErrors(String key, String context, String text) {
        this(new LocalizableMessage(key, context, text));
    }

    /** Create details with a specific summary. */
    public ValidationErrors(LocalizableMessage summary) {
        this(summary, null);
    }

    /** Create details with a specific summary and list of messages. */
    protected ValidationErrors(LocalizableMessage summary, List<LocalizableMessage> messages) {
        this.summary = summary;
        this.messages = messages != null ? messages : new ArrayList<LocalizableMessage>();
    }

    /** Get the validation error summary. */
    public LocalizableMessage getSummary() {
        return this.summary;
    }

    /** Set the validation error summary. */
    public void setSummary(String key, String text) {
        this.setSummary(key, null, text);
    }

    /** Set the validation error summary. */
    public void setSummary(String key, String context, String text) {
        this.setSummary(new LocalizableMessage(key, context, text));
    }

    /** Set the validation error summary. */
    public void setSummary(LocalizableMessage summary) {
        this.summary = summary;
    }

    /** Indicates whether there are any messages. */
    public boolean hasMessages() {
        return this.messages != null && !this.messages.isEmpty();
    }

    /** Get the detailed validation error messages. */
    public List<LocalizableMessage> getMessages() {
        return this.messages;
    }

    /** Set the detailed validation error messages. */
    public void setMessages(List<LocalizableMessage> messages) {
        this.messages = messages;
    }

    /** Add a new message. */
    public void addMessage(String key, String text) {
        this.addMessage(key, null, text);
    }

    /** Add a new message. */
    public void addMessage(String key, String context, String text) {
        this.addMessage(new LocalizableMessage(key, context, text));
    }

    /** Add a new message. */
    public void addMessage(LocalizableMessage message) {
        this.getMessages().add(message);
    }

    /** Compare this object to another object. */
    @Override
    public boolean equals(Object obj) {
        ValidationErrors other = (ValidationErrors) obj;
        return new EqualsBuilder()
                    .append(this.summary, other.summary)
                    .append(this.messages, other.messages)
                    .isEquals();
    }

    /** Generate a hash code for this object. */
    @Override
    public int hashCode() {
        return new HashCodeBuilder()
                    .append(this.summary)
                    .append(this.messages)
                    .toHashCode();
    }
}
