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
import static org.junit.Assert.assertSame;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.junit.Test;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import com.cedarsolutions.dao.gae.IFilterPredicate;
import com.cedarsolutions.dao.gae.domain.IContainer;
import com.google.appengine.api.datastore.QueryResultIterator;
import com.googlecode.objectify.Query;

/**
 * Unit tests for FilteredContainerIterator.
 * @author Kenneth J. Pronovici <pronovic@ieee.org>
 */
@SuppressWarnings({ "rawtypes", "unchecked" })
public class FilteredContainerIteratorTest {

    /** Test the constructor. */
    @Test public void testConstructor() {
        QueryResultIterator queryResultIterator = mock(QueryResultIterator.class);
        Query query = mock(Query.class);
        IFilterPredicate predicate = mock(IFilterPredicate.class);

        when(query.iterator()).thenReturn(queryResultIterator);

        SafeIterator iterator = new SafeIterator(query, predicate);
        assertSame(queryResultIterator, iterator.getIterator());
        assertSame(predicate, iterator.getPredicate());
        assertEquals("cursor1", iterator.getCursor());
    }

    /** Test the remove() method. */
    @Test public void testRemove() {
        QueryResultIterator queryResultIterator = mock(QueryResultIterator.class);
        Query query = mock(Query.class);
        IFilterPredicate predicate = mock(IFilterPredicate.class);
        when(query.iterator()).thenReturn(queryResultIterator);

        SafeIterator iterator = new SafeIterator(query, predicate);

        try {
            iterator.remove();
            fail("Expected UnsupportedOperationException");
        } catch (UnsupportedOperationException e) { }
    }

    /** Test iterator methods. */
    @Test public void testIteratorMethods() {
        List<Container> list = new ArrayList<Container>();
        list.add(new Container("one"));
        list.add(new Container("two"));
        list.add(new Container("three"));

        Iterator<Container> iterator = list.iterator();
        Answer<Boolean> hasNextAnswer = new IteratorHasNextAnswer(iterator);
        Answer<Container> nextAnswer = new IteratorNextAnswer(iterator);

        QueryResultIterator queryResultIterator = mock(QueryResultIterator.class);
        Query query = mock(Query.class);

        when(queryResultIterator.hasNext()).thenAnswer(hasNextAnswer);
        when(queryResultIterator.next()).thenAnswer(nextAnswer);
        when(query.iterator()).thenReturn(queryResultIterator);

        IFilterPredicate<String> predicate = new TwoFilterPredicate();
        SafeIterator filteredResultIterator = new SafeIterator(query, predicate);

        List<String> results = new ArrayList<String>();
        while (filteredResultIterator.hasNext()) {
            String value = filteredResultIterator.next();
            results.add(value);
        }

        assertEquals(2, results.size());
        assertEquals("one", results.get(0));
        assertEquals("three", results.get(1));

        // Just spot-check that it seems to be called the right number of times
        assertEquals("cursor4", filteredResultIterator.getCursor());
    }

    /** Predicate that discards value "two". */
    private static class TwoFilterPredicate implements IFilterPredicate<String> {
        @Override
        public boolean evaluate(String value) {
            return !"two".equals(value);
        }
    }

    /** Iterator we can actually test with. */
    private static class SafeIterator extends FilteredContainerIterator<String> {
        private int calls;

        public SafeIterator(Query<Container> query, IFilterPredicate<String> predicate) {
            super(query, predicate);
        }

        @Override
        protected String deriveCursorValue() {
            // This is so annoying.  I wish Google would stop making so many of
            // their classes and methods final.  This makes it really difficult to test.
            this.calls += 1;
            return "cursor" + this.calls;
        }
    }

    /** Container class for testing. */
    @SuppressWarnings("serial")
    private class Container implements IContainer<String> {
        private String value;

        public Container(String value) {
            this.value = value;
        }

        @Override
        public void fromValue(String value) {
            this.value = value;
        }

        @Override
        public String toValue() {
            return value;
        }
    }

    /** Mockito answer to wrap Iterator.hasNext(). */
    public static class IteratorNextAnswer implements Answer<Container> {
        private Iterator<Container> iterator;

        public IteratorNextAnswer(Iterator<Container> iterator) {
            this.iterator = iterator;
        }

        @Override
        public Container answer(InvocationOnMock invocation) throws Throwable {
            return iterator.next();
        }
    }

    /** Mockito answer to wrap Iterator.next(). */
    public static class IteratorHasNextAnswer implements Answer<Boolean> {
        private Iterator<Container> iterator;

        public IteratorHasNextAnswer(Iterator<Container> iterator) {
            this.iterator = iterator;
        }

        @Override
        public Boolean answer(InvocationOnMock invocation) throws Throwable {
            return iterator.hasNext();
        }
    }
}
