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

import com.cedarsolutions.dao.domain.PaginatedResults;
import com.cedarsolutions.dao.domain.Pagination;

/**
 * Utilities used for interacting with PaginatedResults.
 *
 * <p>
 * The Objectify developers recommend using GAE cursors for pagination rather
 * than setting offset/limit on the query.  This is supposed to be a lot more
 * efficient.  However, it also exposes a lot of the underlying datastore
 * implementation outside the DAO, something I'm not excited about.  I have
 * designed this paginated results interface as a way to mitigate my concerns.
 * </p>
 *
 * <p>
 * There should be enough information in the returned paginated results to
 * implement most pagination schemes on the front end.  The only information
 * that's missing is a guess about the total number of pages before fetching
 * those pages.  I've decided to leave this out because there's no efficient
 * way to do it in GAE.  The GAE cursor information is encapsulated in the
 * paginated results, but the client should never need to deal with it
 * directly.
 * </p>
 *
 * @see <a href="http://groups.google.com/group/objectify-appengine/browse_thread/thread/b640b5d377b620b4">Google Groups</a>
 * @see <a href="https://code.google.com/p/cedar-common/wiki/Pagination">The wiki for notes on pagination for non-GAE platforms</a>
 * @author Kenneth J. Pronovici <pronovic@ieee.org>
 */
public class PaginationUtils {

    /**
     * Create paginated results based on data from GAE, using null pagination.
     * @param <T>  Type of the paginated results
     * @param iterator    Iterator to use as source of data
     */
    public static <T> PaginatedResults<T> createPaginatedResults(IIteratorWithCursor<T> iterator) {
        return createPaginatedResults(null, iterator);
    }

    /**
     * Create paginated results based on data from GAE.
     *
     * <p>
     * Normally, utility code like this would be put on a constructor or something.
     * However, we want PaginatedResults to be translatable to GWT code.
     * FilteredResultIterator and its class hierarchy are not translatable.  So,
     * back-end DAO code will use this utility method, and the PaginatedResults
     * class itself will be translatable.
     * </p>
     *
     * <p>
     * Note that it is legal to call this method with a null value for
     * pagination.  In that case, pagination will be ignored completely, and
     * the entire iterator will be emptied into the returned results.
     * Normally, you wouldn't do this, but for compatibility purposes it's
     * sometimes useful to maintain the interface.
     * </p>
     *
     * @param <T>  Type of the paginated results
     * @param pagination  Pagination that is in use
     * @param iterator    Iterator to use as source of data
     */
    public static <T> PaginatedResults<T> createPaginatedResults(Pagination pagination, IIteratorWithCursor<T> iterator) {
        PaginatedResults<T> results = new PaginatedResults<T>();

        if (pagination == null) {
            while (iterator.hasNext()) {
                T element = iterator.next();
                results.add(element);
            }
        } else {
            String current = iterator.getCursor();
            for (int i = 0; i < pagination.getPageSize() && iterator.hasNext(); i++) {
                T element = iterator.next();
                results.add(element);
            }

            String next = iterator.hasNext() ? iterator.getCursor() : null;
            results.setPagination(pagination.copy());
            results.getPagination().update(current, next, results.size());
        }

        return results;
    }

}
