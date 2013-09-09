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

import com.cedarsolutions.dao.IDaoTransaction;
import com.cedarsolutions.exception.DaoException;

/**
 * A transaction that can span multiple DAO operations, for use with GAE DAOs.
 * @author Kenneth J. Pronovici <pronovic@ieee.org>
 */
public class GaeDaoTransaction implements IDaoTransaction {

    /** Transactional Objectify proxy.  */
    private ObjectifyProxy objectify;

    /** Create a DAO transaction based on a transactional Objectify proxy. */
    public GaeDaoTransaction(ObjectifyProxy objectify) {
        this.objectify = objectify;
        if (!this.objectify.isTransactional()) {
            throw new DaoException("Objectify proxy is not transactional.");
        }
    }

    /** Commit the transaction. */
    @Override
    public void commit() {
        this.objectify.commit();
    }

    /** Rollback the transaction. */
    @Override
    public void rollback() {
        this.objectify.rollback();
    }

    /** Get the transactional Objectify proxy. */
    public ObjectifyProxy getObjectify() {
        return this.objectify;
    }

}
