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
package com.cedarsolutions.shared.domain.email;

import com.cedarsolutions.shared.domain.TranslatableDomainObject;
import com.flipthebird.gwthashcodeequals.EqualsBuilder;
import com.flipthebird.gwthashcodeequals.HashCodeBuilder;

/**
 * Generic representation of an email address.
 * @author Kenneth J. Pronovici <pronovic@ieee.org>
 */
public class EmailAddress extends TranslatableDomainObject {

    /** Serialization version number, which can be important to the GAE back-end. */
    private static final long serialVersionUID = 1L;

    /** Real name. */
    private String name;

    /** Email address. */
    private String address;

    /** Default constructor. */
    public EmailAddress() {
    }

    /** Construct an email address with no name. */
    public EmailAddress(String address) {
        this.name = null;
        this.address = address;
    }

    /** Construct a complete email address including name. */
    public EmailAddress(String name, String address) {
        this.name = name;
        this.address = address;
    }

    /** String representation of an email address in standard format. */
    @Override
    public String toString() {
        if (this.name != null && this.address != null) {
            return "\"" + this.name + "\" <" + this.address + ">";
        } else if (this.address != null) {
            return this.address;
        } else {
            return "";
        }
    }

    /** Compare this object to another object. */
    @Override
    public boolean equals(Object obj) {
        EmailAddress other = (EmailAddress) obj;
        return new EqualsBuilder()
                    .append(this.name, other.name)
                    .append(this.address, other.address)
                    .isEquals();
    }

    /** Generate a hash code for this object. */
    @Override
    public int hashCode() {
        return new HashCodeBuilder()
                    .append(this.name)
                    .append(this.address)
                    .toHashCode();
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return this.address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

}
