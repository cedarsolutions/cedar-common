/* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 *
 *              C E D A R
 *          S O L U T I O N S       "Software done right."
 *           S O F T W A R E
 *
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 *
 * Copyright (c) 2011-2012 Kenneth J. Pronovici.
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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotSame;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.junit.Test;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import com.cedarsolutions.dao.domain.PaginatedResults;
import com.cedarsolutions.dao.domain.Pagination;

/**
 * Unit tests for PaginationUtils.
 * @author Kenneth J. Pronovici <pronovic@ieee.org>
 */
public class PaginationUtilsTest {

    /** Empty iterator. */
    @Test public void testConstructor1() {
        List<String> list = new ArrayList<String>();
        Pagination pagination = new Pagination(5);
        FilteredResultIterator<String> iterator = buildFilteredResultsIteratorForList(list);
        PaginatedResults<String> results = PaginationUtils.createPaginatedResults(pagination, iterator);
        assertEquals(0, results.size());
    }

    /** Non-empty iterator, not big enough. */
    @Test public void testConstructor2() {
        List<String> list = new ArrayList<String>();
        list.add("one");
        list.add("two");
        list.add("three");
        list.add("four");

        Pagination pagination = new Pagination(5);
        FilteredResultIterator<String> iterator = buildFilteredResultsIteratorForList(list);
        PaginatedResults<String> results = PaginationUtils.createPaginatedResults(pagination, iterator);
        assertEquals(4, results.size());
        assertEquals("one", results.get(0));
        assertEquals("two", results.get(1));
        assertEquals("three", results.get(2));
        assertEquals("four", results.get(3));
        assertNotSame(pagination, results.getPagination());
        assertEquals("one", results.getPagination().getCurrent());
        assertEquals("one", results.getPagination().previous().getCurrent());  // because there is no previous
        assertEquals("one", results.getPagination().next().getCurrent());      // because there is no previous
    }

    /** Non-empty iterator, exactly big enough. */
    @Test public void testConstructor3() {
        List<String> list = new ArrayList<String>();
        list.add("one");
        list.add("two");
        list.add("three");
        list.add("four");
        list.add("five");

        Pagination pagination = new Pagination(5);
        FilteredResultIterator<String> iterator = buildFilteredResultsIteratorForList(list);
        PaginatedResults<String> results = PaginationUtils.createPaginatedResults(pagination, iterator);
        assertEquals(5, results.size());
        assertEquals("one", results.get(0));
        assertEquals("two", results.get(1));
        assertEquals("three", results.get(2));
        assertEquals("four", results.get(3));
        assertEquals("five", results.get(4));
        assertNotSame(pagination, results.getPagination());
        assertEquals("one", results.getPagination().getCurrent());
        assertEquals("one", results.getPagination().previous().getCurrent());  // because there is no previous
        assertEquals("one", results.getPagination().next().getCurrent());      // because there is no previous
    }

    /** Non-empty iterator, more than big enough. */
    @Test public void testConstructor4() {
        List<String> list = new ArrayList<String>();
        list.add("one");
        list.add("two");
        list.add("three");
        list.add("four");
        list.add("five");
        list.add("six");
        list.add("seven");

        Pagination pagination = new Pagination(5);
        FilteredResultIterator<String> iterator = buildFilteredResultsIteratorForList(list);
        PaginatedResults<String> results = PaginationUtils.createPaginatedResults(pagination, iterator);
        assertEquals(5, results.size());
        assertEquals("one", results.get(0));
        assertEquals("two", results.get(1));
        assertEquals("three", results.get(2));
        assertEquals("four", results.get(3));
        assertEquals("five", results.get(4));
        assertNotSame(pagination, results.getPagination());
        assertEquals("one", results.getPagination().getCurrent());
        assertEquals("one", results.getPagination().previous().getCurrent());  // because there is no previous
        assertEquals("two", results.getPagination().next().getCurrent());      // because there is a next
    }

    /** Build a mocked FilteredResultsIterator from a list. */
    @SuppressWarnings("unchecked")
    private static FilteredResultIterator<String> buildFilteredResultsIteratorForList(List<String> list) {
        Iterator<String> iterator = list.iterator();
        Answer<Boolean> hasNextAnswer = new IteratorHasNextAnswer(iterator);
        Answer<String> nextAnswer = new IteratorNextAnswer(iterator);

        FilteredResultIterator<String> filteredResultIterator = mock(FilteredResultIterator.class);
        when(filteredResultIterator.hasNext()).thenAnswer(hasNextAnswer);
        when(filteredResultIterator.next()).thenAnswer(nextAnswer);
        when(filteredResultIterator.getCursor()).thenReturn("one", "two");
        return filteredResultIterator;
    }

    /** Mockito answer to wrap Iterator.hasNext(). */
    private static class IteratorNextAnswer implements Answer<String> {
        private Iterator<String> iterator;

        public IteratorNextAnswer(Iterator<String> iterator) {
            this.iterator = iterator;
        }

        @Override
        public String answer(InvocationOnMock invocation) throws Throwable {
            return iterator.next();
        }
    }

    /** Mockito answer to wrap Iterator.next(). */
    private static class IteratorHasNextAnswer implements Answer<Boolean> {
        private Iterator<String> iterator;

        public IteratorHasNextAnswer(Iterator<String> iterator) {
            this.iterator = iterator;
        }

        @Override
        public Boolean answer(InvocationOnMock invocation) throws Throwable {
            return iterator.hasNext();
        }
    }
}
