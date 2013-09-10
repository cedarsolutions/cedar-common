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

import com.cedarsolutions.dao.gae.IFilterPredicate;
import com.cedarsolutions.dao.gae.domain.IContainer;
import com.google.appengine.api.datastore.QueryResultIterator;
import com.googlecode.objectify.Query;

/**
 * Result iterator for data persisted in an IContainer.
 * @param <T>  Type of the iterator
 * @author Kenneth J. Pronovici <pronovic@ieee.org>
 */
public class FilteredContainerIterator<T> implements IIteratorWithCursor<T> {

    private QueryResultIterator<? extends IContainer<T>> iterator;
    private IFilterPredicate<T> predicate;
    private T next;
    private String cursor;

    /** Create an iterator based on a query and a predicate. */
    public FilteredContainerIterator(Query<? extends IContainer<T>> query, IFilterPredicate<T> predicate) {
        this.iterator = query.iterator();
        this.predicate = predicate;
        this.next = this.getNextItem();
    }

    /** Returns true if the iteration has more elements. */
    @Override
    public boolean hasNext() {
        return this.next != null;
    }

    /** Get the next value from the iterator. */
    @Override
    public T next() {
        T result = this.next;
        this.next = this.getNextItem();
        return result;
    }

    /** Not supported in this implementation. */
    @Override
    public void remove() {
        // The Iterator interface specifies that we should throw this exception
        throw new UnsupportedOperationException("Remove operation is not supported");
    }

    /** Get the underlying iterator. */
    protected QueryResultIterator<? extends IContainer<T>> getIterator() {
        return this.iterator;
    }

    /** Get the underlying predicate. */
    public IFilterPredicate<T> getPredicate() {
        return this.predicate;
    }

    /**
     * Get a serialized cursor representing the current state of iterator.
     * @return Serialized cursor, as from Cursor.toWebSafeString().
     */
    @Override
    public String getCursor() {
        return this.cursor;
    }

    /**
     * Get the next item from the iterator, applying criteria from the predicate.
     * @return Next item, possibly null.
     */
    private T getNextItem() {

        // Remember that we're pre-fetching the next item here, and the
        // user might never retrieve it.  So, we need to make sure that the
        // cursor reflects the position *before* calling next().  That way,
        // if the caller is paginating, they start in the right place.

        if (this.cursor == null) {
            this.cursor = this.deriveCursorValue();
        }

        while (iterator.hasNext()) {
            this.cursor = this.deriveCursorValue();
            IContainer<T> element = iterator.next();
            T value = element.toValue();
            if (predicate.evaluate(value)) {
                return value;
            }
        }

        return null;
    }

    /** Derive the cursor value that should be saved off. */
    protected String deriveCursorValue() {
        return this.iterator.getCursor().toWebSafeString();
    }
}
