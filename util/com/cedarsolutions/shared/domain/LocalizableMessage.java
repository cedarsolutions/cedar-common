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
package com.cedarsolutions.shared.domain;

import com.flipthebird.gwthashcodeequals.EqualsBuilder;
import com.flipthebird.gwthashcodeequals.HashCodeBuilder;


/**
 * A message which supports localization.
 *
 * <p>
 * The simplest message includes just human-readable text.  Optionally, you can
 * add a message key and/or indicate which context the message is related to.
 * Both the message key and context are free-form text.  This class does not
 * enforce any semantics on these values.
 * </p>
 *
 * <p>
 * You should always set human-readable text in any message.  This text will be
 * used whenever localization is not required (like in log files).  The message
 * key and context are optional.  If they are provided, they can be used (along
 * with a localization framework) to generate context-sensitive messages to be
 * displayed to a user.  For instance, the context could be the field name that
 * a message is related to.
 * </p>
 *
 * @author Kenneth J. Pronovici <pronovic@ieee.org>
 */
public class LocalizableMessage extends TranslatableDomainObject {

    private static final long serialVersionUID = 1L;
    protected String key;
    protected String context;
    protected String text;

    /** Default constructor, for GWT's benefit. */
    public LocalizableMessage() {
        this(null, null, null);
    }

    /** Create a message. */
    public LocalizableMessage(String text) {
        this(null, null, text);
    }

    /** Create a message. */
    public LocalizableMessage(String key, String text) {
        this(key, null, text);
    }

    /** Create a message tied to a specific field. */
    public LocalizableMessage(String key, String context, String text) {
        this.key = key;
        this.context = context;
        this.text = text;
    }

    /** Copy constructor. */
    public LocalizableMessage(LocalizableMessage source) {
        this.key = source.key;
        this.context = source.context;
        this.text = source.text;
    }

    /** Get the message key, if any. */
    public String getKey() {
        return this.key;
    }

    /** Get the related context, if any. */
    public String getContext() {
        return this.context;
    }

    /** Get the message text. */
    public String getText() {
        return this.text;
    }

    /** Whether this message can be translated. */
    public boolean isTranslatable() {
        return !(this.key == null || this.key.trim().length() == 0);  // !GwtStringUtils.isEmpty()
    }

    /** Compare this object to another object. */
    @Override
    public boolean equals(Object obj) {
        LocalizableMessage other = (LocalizableMessage) obj;
        return new EqualsBuilder()
                    .append(this.key, other.key)
                    .append(this.context, other.context)
                    .append(this.text, other.text)
                    .isEquals();
    }

    /** Generate a hash code for this object. */
    @Override
    public int hashCode() {
        return new HashCodeBuilder()
                    .append(this.key)
                    .append(this.context)
                    .append(this.text)
                    .toHashCode();
    }
}
