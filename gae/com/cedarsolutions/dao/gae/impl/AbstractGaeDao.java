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

import static com.cedarsolutions.dao.domain.SortOrder.DESCENDING;

import org.springframework.beans.factory.InitializingBean;

import com.cedarsolutions.dao.IDaoTransaction;
import com.cedarsolutions.dao.ITransactionalDao;
import com.cedarsolutions.dao.domain.SortOrder;
import com.cedarsolutions.dao.gae.IDaoObjectifyService;
import com.cedarsolutions.exception.DaoException;
import com.cedarsolutions.exception.NotConfiguredException;
import com.googlecode.objectify.Query;

/**
 * Abstract class that all GWT back-end DAOs inherit from when running on GAE.
 * These DAOs are implemented in terms of the Objectify persistence library.
 * @author Kenneth J. Pronovici <pronovic@ieee.org>
 */
public abstract class AbstractGaeDao implements InitializingBean, ITransactionalDao {

    /** DAO Objectify service. */
    private IDaoObjectifyService daoObjectifyService;

    /**
     * Invoked by a bean factory after it has set all bean properties.
     * @throws NotConfiguredException In the event of misconfiguration.
     */
    @Override
    public void afterPropertiesSet() throws NotConfiguredException {
        if (this.daoObjectifyService == null) {
            throw new NotConfiguredException("DAO is not properly configured.");
        }
    }

    /** Get a DAO transaction that spans DAO operations and crosses DAO boundaries. */
    @Override
    public IDaoTransaction getDaoTransaction() {
        return this.getGaeTransaction();
    }

    /** Check that a transaction is a valid GAE transaction this DAO can use. */
    protected static GaeDaoTransaction checkTransactionType(IDaoTransaction transaction) {
        if (transaction == null) {
            throw new NullPointerException("transaction");
        } else if (!(transaction instanceof GaeDaoTransaction)) {
            throw new DaoException("A transaction of type GaeDaoTransaction is required, got " + transaction.getClass().getName());
        } else {
            return (GaeDaoTransaction) transaction;
        }
    }

    /** Get an object datastore for use in a DAO. */
    protected ObjectifyProxy getObjectify() {
        return this.daoObjectifyService.getObjectify();
    }

    /**
     * Get an object datastore for use in a DAO, with a transaction started.
     * Most child classes should use getGaeTransaction() instead.
     */
    protected ObjectifyProxy getObjectifyWithTransaction() {
        return this.daoObjectifyService.getObjectifyWithTransaction();
    }

    /** Get a GAE-specific transaction for use internally, within the DAO. */
    protected GaeDaoTransaction getGaeTransaction() {
        return new GaeDaoTransaction(this.getObjectifyWithTransaction());
    }

    /** Set sort for a named field. */
    protected static <T> void setSort(Query<T> query, SortOrder sortOrder, String field) {
        field = sortOrder == DESCENDING ? "-" + field : field;
        query.order(field);
    }

    public IDaoObjectifyService getDaoObjectifyService() {
        return this.daoObjectifyService;
    }

    public void setDaoObjectifyService(IDaoObjectifyService daoObjectifyService) {
        this.daoObjectifyService = daoObjectifyService;
    }

}
