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
package com.cedarsolutions.dao.gae.impl;

import java.util.Date;

import javax.persistence.Id;

import com.googlecode.objectify.annotation.Indexed;

/**
 * Test entity for use in DAO unit tests, with an integer id.
 * @author Kenneth J. Pronovici <pronovic@ieee.org>
 */
public class IntegerIdEntity {

    @Id private long id;  // note that integer-style ids must be long, not int
    @Indexed private Date timestamp;

    public IntegerIdEntity() {
    }

    public IntegerIdEntity(long id, Date timestamp) {
        this.id = id;
        this.timestamp = timestamp;
    }

    public long getId() {
        return this.id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Date getTimestamp() {
        return this.timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }
}
