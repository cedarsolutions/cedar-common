/* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 *
 *              C E D A R
 *          S O L U T I O N S       "Software done right."
 *           S O F T W A R E
 *
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 *
 * Copyright (c) 2012-2013 Kenneth J. Pronovici.
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
 * Describes an error to be displayed to a user.
 * @author Kenneth J. Pronovici <pronovic@ieee.org>
 */
public class ErrorDescription extends TranslatableDomainObject {

    /** Serialization version number, which can be important to the GAE back-end. */
    private static final long serialVersionUID = 1L;

    /** Error message to be displayed. */
    private String message;

    /** Exception related to the error, or null. */
    private Throwable exception;

    /** Supporting text related to the error, if any. */
    private List<String> supportingTextItems;

    /** Create an empty error description. */
    public ErrorDescription() {
        this.message = null;
        this.exception = null;
        this.supportingTextItems = new ArrayList<String>();
    }

    /**
     * Create an error description.
     * @param message Error message
     */
    public ErrorDescription(String message) {
        this(message, null, (String) null);
    }

    /**
     * Create an error description.
     * @param message     Error message
     * @param exception   Exception, or null
     */
    public ErrorDescription(String message, Throwable exception) {
        this(message, exception, (String) null);
    }

    /**
     * Create an error description.
     * @param message             Error message
     * @param supportingTextItem  Supporting text item, or null
     */
    public ErrorDescription(String message, String supportingTextItem) {
        this(message, null, supportingTextItem);
    }

    /**
     * Create an error description.
     * @param message             Error message
     * @param exception           Exception, or null
     * @param supportingTextItem  Supporting text item, or null
     */
    public ErrorDescription(String message, Throwable exception, String supportingTextItem) {
        this();
        this.message = message;
        this.exception = exception;
        if (supportingTextItem != null) {
            this.supportingTextItems.add(supportingTextItem);
        }
    }

    /**
     * Create an error description.
     * @param message              Error message
     * @param supportingTextItems  List of supporting text items, or null
     */
    public ErrorDescription(String message, List<String> supportingTextItems) {
        this(message, null, supportingTextItems);
    }

    /**
     * Create an error description.
     * @param message              Error message
     * @param exception            Exception, or null
     * @param supportingTextItems  List of supporting text items, or null
     */
    public ErrorDescription(String message, Throwable exception, List<String> supportingTextItems) {
        this();
        this.message = message;
        this.exception = exception;
        if (supportingTextItems != null) {
            this.supportingTextItems.addAll(supportingTextItems);
        }
    }

    /** Compare this object to another object. */
    @Override
    public boolean equals(Object obj) {
        ErrorDescription other = (ErrorDescription) obj;
        return new EqualsBuilder()
                    .append(this.message, other.message)
                    .append(getExceptionClass(this.exception), getExceptionClass(other.exception))
                    .append(getExceptionMessage(this.exception), getExceptionMessage(other.exception))
                    .append(this.supportingTextItems, other.supportingTextItems)
                    .isEquals();
    }

    /** Generate a hash code for this object. */
    @Override
    public int hashCode() {
        return new HashCodeBuilder()
                    .append(this.message)
                    .append(getExceptionClass(this.exception))
                    .append(getExceptionMessage(this.exception))
                    .append(this.supportingTextItems)
                    .toHashCode();
    }

    /** Get the class for an exception, null-safe. */
    @SuppressWarnings("rawtypes")
    private static Class getExceptionClass(Throwable exception) {
        return exception == null ? null : exception.getClass();
    }

    /** Get the message for an exception, null-safe. */
    private static String getExceptionMessage(Throwable exception) {
        return exception == null ? null : exception.getMessage();
    }

    public String getMessage() {
        return this.message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Throwable getException() {
        return this.exception;
    }

    public void setException(Throwable exception) {
        this.exception = exception;
    }

    public List<String> getSupportingTextItems() {
        return this.supportingTextItems;
    }

    public void setSupportingTextItems(List<String> supportingTextItems) {
        this.supportingTextItems = supportingTextItems;
    }

}
