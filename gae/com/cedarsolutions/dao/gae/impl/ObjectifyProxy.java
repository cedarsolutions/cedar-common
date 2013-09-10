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

import java.util.Map;

import com.cedarsolutions.dao.domain.Pagination;
import com.cedarsolutions.exception.DaoException;
import com.google.appengine.api.datastore.Cursor;
import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.Transaction;
import com.googlecode.objectify.AsyncObjectify;
import com.googlecode.objectify.Key;
import com.googlecode.objectify.NotFoundException;
import com.googlecode.objectify.Objectify;
import com.googlecode.objectify.ObjectifyFactory;
import com.googlecode.objectify.Query;

/**
 * Proxy over the standard Objectify implementation.
 *
 * <p>
 * This proxy exists because it's a natural place to implement
 * customized behavior, like for transactionality or pagination.
 * Putting that custom behavior here makes the DAO implementations
 * a little easier to read.
 * </p>
 *
 * @author Kenneth J. Pronovici <pronovic@ieee.org>
 */
public class ObjectifyProxy {

    /** The underlying Objectify instance that is proxied. */
    private Objectify objectify;

    /** Whether this objectify instance is transactional. */
    private boolean transactional;

    /** Create a proxy instance. */
    public ObjectifyProxy(Objectify objectify) {
        this(objectify, false);
    }

    /** Create a proxy instance. */
    public ObjectifyProxy(Objectify objectify, boolean transactional) {
        this.objectify = objectify;
        this.transactional = transactional;
    }

    /** Whether this instance is transactional. */
    public boolean isTransactional() {
        return this.transactional;
    }

    /** Get the underlying Objectify instance that is proxied. */
    public Objectify getProxyTarget() {
        return this.objectify;
    }

    /** Commit a transaction. */
    public void commit() {
        if (!this.transactional) {
           throw new DaoException("This Objectify instance is not transactional.");
        }

        if (this.getTxn().isActive()) {
            this.getTxn().commit();
        }
    }

    /** Rollback a transaction. */
    public void rollback() {
        if (!this.transactional) {
           throw new DaoException("This Objectify instance is not transactional.");
        }

        if (this.getTxn().isActive()) {
            this.getTxn().rollback();
        }
    }

    /** Obtain the asynchronous version of the Objectify interface. */
    public AsyncObjectify async() {
        return this.objectify.async();
    }

    /** A convenience method, shorthand for creating a key and deleting it. */
    public <T> void delete(Class<T> clazz, long id) {
        this.objectify.delete(clazz, id);
    }

    /** A convenience method, shorthand for creating a key and deleting it. */
    public <T> void delete(Class<T> clazz, String name) {
        this.objectify.delete(clazz, name);
    }

    /** Deletes the specified entities in a parallel batch operation. */
    public void delete(Iterable<?> keysOrEntities) {
        this.objectify.delete(keysOrEntities);
    }

    /** Deletes the specified entity. */
    public void delete(Object... keysOrEntities) {
        this.objectify.delete(keysOrEntities);
    }

    /** Same as get(Class, long) but returns null instead of throwing NotFoundException. */
    public <T> T find(Class<? extends T> clazz, long id) {
        return this.objectify.find(clazz, id);
    }

    /** Same as get(Class, name) but returns null instead of throwing NotFoundException. */
    public <T> T find(Class<? extends T> clazz, String name) {
        return this.objectify.find(clazz, name);
    }

    /** Same as get(Key) but returns null instead of throwing NotFoundException. */
    public <T> T find(Key<? extends T> key) {
        return this.objectify.find(key);
    }

    /** A convenience method that prevents you from having to assemble all the Keys yourself and calling get(Iterable<Key>). */
    public <S, T> Map<S, T> get(Class<? extends T> clazz, Iterable<S> idsOrNames) {
        return this.objectify.get(clazz, idsOrNames);
    }

    /**  A convenience method, shorthand for creating a key and calling get(). */
    public <T> T get(Class<? extends T> clazz, long id) throws NotFoundException {
        return this.objectify.get(clazz, id);
    }

    /** Convenient varargs alias for get(Class, Iterable). */
    public <S, T> Map<S, T> get(Class<? extends T> clazz, S... idsOrNames) {
        return this.objectify.get(clazz, idsOrNames);
    }

    /**  A convenience method, shorthand for creating a key and calling get(). */
    public <T> T get(Class<? extends T> clazz, String name) throws NotFoundException {
        return this.objectify.get(clazz, name);
    }

    /** Performs a parallel batch get, returning your entities. */
    public <T> Map<Key<T>, T> get(Iterable<? extends Key<? extends T>> keys) {
        return this.objectify.get(keys);
    }

    /** Gets one instance of your entity. */
    public <T> T get(Key<? extends T> key) throws NotFoundException {
        return this.objectify.get(key);
    }

    /** Obtain a DatastoreService with parameters roughly equivalent to this Objectify instance. */
    public DatastoreService getDatastore() {
        return this.objectify.getDatastore();
    }

    /** Obtain the ObjectifyFactory from which this Objectify instance was created. */
    public ObjectifyFactory getFactory() {
        return this.objectify.getFactory();
    }

    /** Get the underlying transaction object associated with this Objectify instance. */
    public Transaction getTxn() {
        return this.objectify.getTxn();
    }

    /** Saves multiple entities to the datastore in a single parallel batch operation. */
    public <T> Map<Key<T>, T> put(Iterable<? extends T> objs) {
        return this.objectify.put(objs);
    }

    /** Convenient varargs alias for put(Iterable). */
    public <T> Map<Key<T>, T> put(T... objs) {
        return this.objectify.put(objs);
    }

    /** Puts an entity in the datastore. */
    public <T> Key<T> put(T obj) {
        return this.objectify.put(obj);
    }

    /** Create a typesafe query across all kinds of entities. */
    public <T> Query<T> query() {
        return this.objectify.query();
    }

    /** Create a typesafe query across one specific kind of entity. */
    public <T> Query<T> query(Class<T> clazz) {
        return this.objectify.query(clazz);
    }

    /** Create a query, taking into account pagination. */
    public <T> Query<T> query(Class<T> clazz, Pagination pagination) {
        Query<T> query = this.query(clazz);

        if (pagination != null) {
            if (pagination.getCurrent() != null) {
                Cursor cursor = Cursor.fromWebSafeString(pagination.getCurrent());
                query.startCursor(cursor);
            }
        }

        return query;
    }

}
