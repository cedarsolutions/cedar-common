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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Test;

import com.cedarsolutions.dao.IDaoTransaction;
import com.cedarsolutions.dao.domain.PaginatedResults;
import com.cedarsolutions.dao.domain.Pagination;
import com.cedarsolutions.dao.domain.SortOrder;
import com.cedarsolutions.dao.gae.IDaoObjectifyService;
import com.cedarsolutions.exception.NotConfiguredException;
import com.cedarsolutions.util.DateUtils;
import com.googlecode.objectify.Query;
import com.sun.util.PrintfFormat;

/**
 * Unit tests for AbstractGaeDao.
 * @author Kenneth J. Pronovici <pronovic@ieee.org>
 */
public class AbstractGaeDaoTest extends DaoTestCase {

    /** Test the constructor, getters, and setters. */
    @Test public void testConstructor() {
        StringIdEntityDao dao = new StringIdEntityDao();
        assertNull(dao.getDaoObjectifyService());

        DaoObjectifyService daoObjectifyService = mock(DaoObjectifyService.class);
        dao.setDaoObjectifyService(daoObjectifyService);
        assertSame(daoObjectifyService, dao.getDaoObjectifyService());
    }

    /** Test the afterPropertiesSet() method. */
    @Test public void testAfterPropertiesSet() throws Exception {
        DaoObjectifyService daoObjectifyService = mock(DaoObjectifyService.class);
        StringIdEntityDao dao = new StringIdEntityDao();

        dao.setDaoObjectifyService(daoObjectifyService);
        dao.afterPropertiesSet();

        try {
            dao.setDaoObjectifyService(null);
            dao.afterPropertiesSet();
            fail("Expected NotConfiguredException");
        } catch (NotConfiguredException e) { }
    }

    /** Test getObjectify(). */
    @Test public void testGetObjectify() throws Exception {
        ObjectifyProxy objectify = mock(ObjectifyProxy.class);
        DaoObjectifyService daoObjectifyService = mock(DaoObjectifyService.class);
        when(daoObjectifyService.getObjectify()).thenReturn(objectify);

        StringIdEntityDao dao = new StringIdEntityDao();
        dao.setDaoObjectifyService(daoObjectifyService);
        dao.afterPropertiesSet();

        assertSame(objectify, dao.getObjectify());
    }

    /** Test getDaoTransaction(). */
    @Test public void testGetDaoTransaction() throws Exception {
        ObjectifyProxy objectify = mock(ObjectifyProxy.class);
        when(objectify.isTransactional()).thenReturn(true);
        DaoObjectifyService daoObjectifyService = mock(DaoObjectifyService.class);
        when(daoObjectifyService.getObjectifyWithTransaction()).thenReturn(objectify);

        StringIdEntityDao dao = new StringIdEntityDao();
        dao.setDaoObjectifyService(daoObjectifyService);
        dao.afterPropertiesSet();

        IDaoTransaction daoTransaction = dao.getDaoTransaction();
        assertNotNull(daoTransaction);
        assertTrue(daoTransaction instanceof GaeDaoTransaction);
        GaeDaoTransaction gaeTransaction = (GaeDaoTransaction) daoTransaction;
        assertSame(objectify, gaeTransaction.getObjectify());
    }

    /** Test getGaeTransaction(). */
    @Test public void testGetGaeTransaction() throws Exception {
        ObjectifyProxy objectify = mock(ObjectifyProxy.class);
        when(objectify.isTransactional()).thenReturn(true);
        DaoObjectifyService daoObjectifyService = mock(DaoObjectifyService.class);
        when(daoObjectifyService.getObjectifyWithTransaction()).thenReturn(objectify);

        StringIdEntityDao dao = new StringIdEntityDao();
        dao.setDaoObjectifyService(daoObjectifyService);
        dao.afterPropertiesSet();

        GaeDaoTransaction transaction = dao.getGaeTransaction();
        assertNotNull(transaction);
        assertSame(objectify, transaction.getObjectify());
    }

    /** Test setSort() for an ascending sort. */
    @SuppressWarnings({ "rawtypes", "unchecked" })
    @Test public void testSetSortAscending() {
        Query query = mock(Query.class);
        StringIdEntityDao.setSort(query, SortOrder.ASCENDING, "field");
        verify(query).order("field");
    }

    /** Test setSort() for a descending sort. */
    @SuppressWarnings({ "rawtypes", "unchecked" })
    @Test public void testSetSortDescending() {
        Query query = mock(Query.class);
        StringIdEntityDao.setSort(query, SortOrder.DESCENDING, "field");
        verify(query).order("-field");
    }

    /** Verify the behavior of StringIdEntityDao. */
    @Test public void testStringIdEntityDao() {
        StringIdEntity result = null;
        StringIdEntity entity1 = new StringIdEntity("entity1", DateUtils.createDate("2011-06-01"));
        StringIdEntity entity2 = new StringIdEntity("entity2", DateUtils.createDate("2011-06-02"));

        StringIdEntityDao dao = createStringIdEntityDao();

        result = dao.retrieveTestEntity("entity1");
        assertNull(result);

        result = dao.retrieveTestEntity("entity2");
        assertNull(result);

        result = dao.retrieveTestEntity("entity3");
        assertNull(result);

        dao.insertTestEntity(entity1);

        result = dao.retrieveTestEntity("entity1");
        assertEquals("entity1", result.getId());
        assertEquals(DateUtils.createDate("2011-06-01"), result.getTimestamp());

        assertNull(dao.retrieveTestEntity("entity2"));

        dao.insertTestEntity(entity2);

        result = dao.retrieveTestEntity("entity1");
        assertEquals("entity1", result.getId());
        assertEquals(DateUtils.createDate("2011-06-01"), result.getTimestamp());

        result = dao.retrieveTestEntity("entity2");
        assertEquals("entity2", result.getId());
        assertEquals(DateUtils.createDate("2011-06-02"), result.getTimestamp());

        result = dao.retrieveTestEntity("entity3");
        assertNull(result);

        entity2.setTimestamp(DateUtils.createDate("2012-06-02"));
        dao.updateTestEntity(entity2);

        result = dao.retrieveTestEntity("entity1");
        assertEquals("entity1", result.getId());
        assertEquals(DateUtils.createDate("2011-06-01"), result.getTimestamp());

        result = dao.retrieveTestEntity("entity2");
        assertEquals("entity2", result.getId());
        assertEquals(DateUtils.createDate("2012-06-02"), result.getTimestamp());

        result = dao.retrieveTestEntity("entity3");
        assertNull(result);

        dao.deleteTestEntity("entity2");

        result = dao.retrieveTestEntity("entity1");
        assertEquals("entity1", result.getId());
        assertEquals(DateUtils.createDate("2011-06-01"), result.getTimestamp());

        result = dao.retrieveTestEntity("entity2");
        assertNull(result);

        result = dao.retrieveTestEntity("entity3");
        assertNull(result);

        dao.deleteTestEntity(entity1);

        result = dao.retrieveTestEntity("entity1");
        assertNull(result);

        result = dao.retrieveTestEntity("entity2");
        assertNull(result);

        result = dao.retrieveTestEntity("entity3");
        assertNull(result);
    }

    /** Verify the behavior of IntegerIdEntityDao. */
    @Test public void testIntegerIdEntityDao() {
        IntegerIdEntity result = null;
        IntegerIdEntity entity1 = new IntegerIdEntity(1, DateUtils.createDate("2011-06-01"));
        IntegerIdEntity entity2 = new IntegerIdEntity(2, DateUtils.createDate("2011-06-02"));

        IntegerIdEntityDao dao = createIntegerIdEntityDao();

        result = dao.retrieveTestEntity(1);
        assertNull(result);

        result = dao.retrieveTestEntity(2);
        assertNull(result);

        result = dao.retrieveTestEntity(3);
        assertNull(result);

        dao.insertTestEntity(entity1);

        result = dao.retrieveTestEntity(1);
        assertEquals(1, result.getId());
        assertEquals(DateUtils.createDate("2011-06-01"), result.getTimestamp());

        assertNull(dao.retrieveTestEntity(2));

        dao.insertTestEntity(entity2);

        result = dao.retrieveTestEntity(1);
        assertEquals(1, result.getId());
        assertEquals(DateUtils.createDate("2011-06-01"), result.getTimestamp());

        result = dao.retrieveTestEntity(2);
        assertEquals(2, result.getId());
        assertEquals(DateUtils.createDate("2011-06-02"), result.getTimestamp());

        result = dao.retrieveTestEntity(3);
        assertNull(result);

        entity2.setTimestamp(DateUtils.createDate("2012-06-02"));
        dao.updateTestEntity(entity2);

        result = dao.retrieveTestEntity(1);
        assertEquals(1, result.getId());
        assertEquals(DateUtils.createDate("2011-06-01"), result.getTimestamp());

        result = dao.retrieveTestEntity(2);
        assertEquals(2, result.getId());
        assertEquals(DateUtils.createDate("2012-06-02"), result.getTimestamp());

        result = dao.retrieveTestEntity(3);
        assertNull(result);

        dao.deleteTestEntity(2);

        result = dao.retrieveTestEntity(1);
        assertEquals(1, result.getId());
        assertEquals(DateUtils.createDate("2011-06-01"), result.getTimestamp());

        result = dao.retrieveTestEntity(2);
        assertNull(result);

        result = dao.retrieveTestEntity(3);
        assertNull(result);

        dao.deleteTestEntity(entity1);

        result = dao.retrieveTestEntity(1);
        assertNull(result);

        result = dao.retrieveTestEntity(2);
        assertNull(result);

        result = dao.retrieveTestEntity(3);
        assertNull(result);
    }

    /** Test pagination when filtering is not involved, for StringIdEntity. */
    @Test public void testPaginationNoFilteringString() {
        Pagination pagination;
        PaginatedResults<StringIdEntity> results;

        // These entities by default do not have values that get caught by the filter
        List<StringIdEntity> entities = createTestEntitiesString(19);
        StringIdEntityDao dao = createStringIdEntityDao();
        for (StringIdEntity entity : entities) {
            dao.insertTestEntity(entity);
        }

        // The fetch (with no pagination) retrieves everything
        results = dao.retrieveTestEntities();
        assertFound(results, "entity1", "entity2", "entity3", "entity4", "entity5",
                             "entity6", "entity7", "entity8", "entity9", "entity10",
                             "entity11", "entity12", "entity13", "entity14", "entity15",
                             "entity16", "entity17", "entity18", "entity19");
        assertNull(results.getPagination());

        // We'll set up pagination at 5 items per page, which will get us 4 pages
        pagination = new Pagination(5);
        assertPagination(pagination, 1, false, false, false, false, 0, 0);

        // The fetch retrieves page 1, and we know there are at least 2 pages
        results = dao.retrieveTestEntities(pagination);
        assertFound(results, "entity1", "entity2", "entity3", "entity4", "entity5");
        assertNotSame(pagination, results.getPagination());
        assertPagination(results.getPagination(), 1, true, false, true, false, 2, 5);

        // page(5) doesn't exist yet, so we get the largest known page, page 2
        pagination = results.getPagination();
        results = dao.retrieveTestEntities(pagination.page(5));
        assertFound(results, "entity6", "entity7", "entity8", "entity9", "entity10");
        assertNotSame(pagination, results.getPagination());
        assertPagination(results.getPagination(), 2, true, true, true, false, 3, 10);

        // next() retrieves page 3, and we know there are at least 4 pages
        pagination = results.getPagination();
        results = dao.retrieveTestEntities(pagination.next());
        assertFound(results, "entity11", "entity12", "entity13", "entity14", "entity15");
        assertNotSame(pagination, results.getPagination());
        assertPagination(results.getPagination(), 3, true, true, true, false, 4, 15);

        // next() retrieves page 4, and we now know that there are 4 pages total
        pagination = results.getPagination();
        results = dao.retrieveTestEntities(pagination.next());
        assertFound(results, "entity16", "entity17", "entity18", "entity19");
        assertNotSame(pagination, results.getPagination());
        assertPagination(results.getPagination(), 4, true, true, false, true, 4, 19);

        // page(2) exists, so we get back to page 2
        pagination = results.getPagination();
        results = dao.retrieveTestEntities(pagination.page(2));
        assertFound(results, "entity6", "entity7", "entity8", "entity9", "entity10");
        assertNotSame(pagination, results.getPagination());
        assertPagination(results.getPagination(), 2, true, true, true, true, 4, 19);

        // previous() brings us to page 1
        pagination = results.getPagination();
        results = dao.retrieveTestEntities(pagination.previous());
        assertFound(results, "entity1", "entity2", "entity3", "entity4", "entity5");
        assertNotSame(pagination, results.getPagination());
        assertPagination(results.getPagination(), 1, true, false, true, true, 4, 19);

        // previous() again doesn't exist, so we get page 1 again
        pagination = results.getPagination();
        results = dao.retrieveTestEntities(pagination.previous());
        assertFound(results, "entity1", "entity2", "entity3", "entity4", "entity5");
        assertNotSame(pagination, results.getPagination());
        assertPagination(results.getPagination(), 1, true, false, true, true, 4, 19);

        // next() gets us page 2
        pagination = results.getPagination();
        results = dao.retrieveTestEntities(pagination.next());
        assertFound(results, "entity6", "entity7", "entity8", "entity9", "entity10");
        assertNotSame(pagination, results.getPagination());
        assertPagination(results.getPagination(), 2, true, true, true, true, 4, 19);

        // page(0) doesn't exist, so we get page 1
        pagination = results.getPagination();
        results = dao.retrieveTestEntities(pagination.page(0));
        assertFound(results, "entity1", "entity2", "entity3", "entity4", "entity5");
        assertNotSame(pagination, results.getPagination());
        assertPagination(results.getPagination(), 1, true, false, true, true, 4, 19);

        // page(5) doesn't exist, so we get the largest known page
        pagination = results.getPagination();
        results = dao.retrieveTestEntities(pagination.page(5));
        assertFound(results, "entity16", "entity17", "entity18", "entity19");
        assertNotSame(pagination, results.getPagination());
        assertPagination(results.getPagination(), 4, true, true, false, true, 4, 19);

        // page(-1) doesn't exist, so we get page 1
        pagination = results.getPagination();
        results = dao.retrieveTestEntities(pagination.page(-1));
        assertFound(results, "entity1", "entity2", "entity3", "entity4", "entity5");
        assertNotSame(pagination, results.getPagination());
        assertPagination(results.getPagination(), 1, true, false, true, true, 4, 19);
    }

    /** Test pagination when filtering is not involved, for StringIdEntity. */
    @Test public void testPaginationWithFilteringString() {
        Pagination pagination;
        PaginatedResults<StringIdEntity> results;

        // These entities by default do not have values that get caught by the filter
        // I'm going to pick a few of them and change them so the filter catches them.
        List<StringIdEntity> entities = createTestEntitiesString(19);
        entities.get(1).setId("entity2X");
        entities.get(9).setId("entity10X");
        entities.get(17).setId("entity18X");

        StringIdEntityDao dao = createStringIdEntityDao();
        for (StringIdEntity entity : entities) {
            dao.insertTestEntity(entity);
        }

        // We'll set up pagination at 5 items per page, which will get us 4 pages
        pagination = new Pagination(5);
        assertPagination(pagination, 1, false, false, false, false, 0, 0);

        // The fetch retrieves page 1, and we know there are at least 2 pages
        results = dao.retrieveTestEntities(pagination);
        assertFound(results, "entity1", "entity3", "entity4", "entity5", "entity6");
        assertNotSame(pagination, results.getPagination());
        assertPagination(results.getPagination(), 1, true, false, true, false, 2, 5);

        // page(5) doesn't exist yet, so we get the largest known page, page 2
        pagination = results.getPagination();
        results = dao.retrieveTestEntities(pagination.page(5));
        assertFound(results, "entity7", "entity8", "entity9", "entity11", "entity12");
        assertNotSame(pagination, results.getPagination());
        assertPagination(results.getPagination(), 2, true, true, true, false, 3, 10);

        // next() retrieves page 3, and we know there are at least 4 pages
        pagination = results.getPagination();
        results = dao.retrieveTestEntities(pagination.next());
        assertFound(results, "entity13", "entity14", "entity15", "entity16", "entity17");
        assertNotSame(pagination, results.getPagination());
        assertPagination(results.getPagination(), 3, true, true, true, false, 4, 15);

        // next() retrieves page 4, and we now know that there are 4 pages total
        pagination = results.getPagination();
        results = dao.retrieveTestEntities(pagination.next());
        assertFound(results, "entity19");
        assertNotSame(pagination, results.getPagination());
        assertPagination(results.getPagination(), 4, true, true, false, true, 4, 16);

        // page(2) exists, so we get back to page 2
        pagination = results.getPagination();
        results = dao.retrieveTestEntities(pagination.page(2));
        assertFound(results, "entity7", "entity8", "entity9", "entity11", "entity12");
        assertNotSame(pagination, results.getPagination());
        assertPagination(results.getPagination(), 2, true, true, true, true, 4, 16);

        // previous() brings us to page 1
        pagination = results.getPagination();
        results = dao.retrieveTestEntities(pagination.previous());
        assertFound(results, "entity1", "entity3", "entity4", "entity5", "entity6");
        assertNotSame(pagination, results.getPagination());
        assertPagination(results.getPagination(), 1, true, false, true, true, 4, 16);

        // previous() again doesn't exist, so we get page 1 again
        pagination = results.getPagination();
        results = dao.retrieveTestEntities(pagination.previous());
        assertFound(results, "entity1", "entity3", "entity4", "entity5", "entity6");
        assertNotSame(pagination, results.getPagination());
        assertPagination(results.getPagination(), 1, true, false, true, true, 4, 16);

        // next() gets us page 2
        pagination = results.getPagination();
        results = dao.retrieveTestEntities(pagination.next());
        assertFound(results, "entity7", "entity8", "entity9", "entity11", "entity12");
        assertNotSame(pagination, results.getPagination());
        assertPagination(results.getPagination(), 2, true, true, true, true, 4, 16);

        // page(0) doesn't exist, so we get page 1
        pagination = results.getPagination();
        results = dao.retrieveTestEntities(pagination.page(0));
        assertFound(results, "entity1", "entity3", "entity4", "entity5", "entity6");
        assertNotSame(pagination, results.getPagination());
        assertPagination(results.getPagination(), 1, true, false, true, true, 4, 16);

        // page(5) doesn't exist, so we get the largest known page
        pagination = results.getPagination();
        results = dao.retrieveTestEntities(pagination.page(5));
        assertFound(results, "entity19");
        assertNotSame(pagination, results.getPagination());
        assertPagination(results.getPagination(), 4, true, true, false, true, 4, 16);

        // page(-1) doesn't exist, so we get page 1
        pagination = results.getPagination();
        results = dao.retrieveTestEntities(pagination.page(-1));
        assertFound(results, "entity1", "entity3", "entity4", "entity5", "entity6");
        assertNotSame(pagination, results.getPagination());
        assertPagination(results.getPagination(), 1, true, false, true, true, 4, 16);
    }


    /** Test pagination when filtering is not involved, for IntegerIdEntity. */
    @Test public void testPaginationNoFilteringInteger() {
        Pagination pagination;
        PaginatedResults<IntegerIdEntity> results;

        // These entities by default do not have values that get caught by the filter
        List<IntegerIdEntity> entities = createTestEntitiesInteger(19);
        IntegerIdEntityDao dao = createIntegerIdEntityDao();
        for (IntegerIdEntity entity : entities) {
            dao.insertTestEntity(entity);
        }

        // The fetch (with no pagination) retrieves everything
        results = dao.retrieveTestEntities();
        assertFound(results, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19);
        assertNull(results.getPagination());

        // We'll set up pagination at 5 items per page, which will get us 4 pages
        pagination = new Pagination(5);
        assertPagination(pagination, 1, false, false, false, false, 0, 0);

        // The fetch retrieves page 1, and we know there are at least 2 pages
        results = dao.retrieveTestEntities(pagination);
        assertFound(results, 1, 2, 3, 4, 5);
        assertNotSame(pagination, results.getPagination());
        assertPagination(results.getPagination(), 1, true, false, true, false, 2, 5);

        // page(5) doesn't exist yet, so we get the largest known page, page 2
        pagination = results.getPagination();
        results = dao.retrieveTestEntities(pagination.page(5));
        assertFound(results, 6, 7, 8, 9, 10);
        assertNotSame(pagination, results.getPagination());
        assertPagination(results.getPagination(), 2, true, true, true, false, 3, 10);

        // next() retrieves page 3, and we know there are at least 4 pages
        pagination = results.getPagination();
        results = dao.retrieveTestEntities(pagination.next());
        assertFound(results, 11, 12, 13, 14, 15);
        assertNotSame(pagination, results.getPagination());
        assertPagination(results.getPagination(), 3, true, true, true, false, 4, 15);

        // next() retrieves page 4, and we now know that there are 4 pages total
        pagination = results.getPagination();
        results = dao.retrieveTestEntities(pagination.next());
        assertFound(results, 16, 17, 18, 19);
        assertNotSame(pagination, results.getPagination());
        assertPagination(results.getPagination(), 4, true, true, false, true, 4, 19);

        // page(2) exists, so we get back to page 2
        pagination = results.getPagination();
        results = dao.retrieveTestEntities(pagination.page(2));
        assertFound(results, 6, 7, 8, 9, 10);
        assertNotSame(pagination, results.getPagination());
        assertPagination(results.getPagination(), 2, true, true, true, true, 4, 19);

        // previous() brings us to page 1
        pagination = results.getPagination();
        results = dao.retrieveTestEntities(pagination.previous());
        assertFound(results, 1, 2, 3, 4, 5);
        assertNotSame(pagination, results.getPagination());
        assertPagination(results.getPagination(), 1, true, false, true, true, 4, 19);

        // previous() again doesn't exist, so we get page 1 again
        pagination = results.getPagination();
        results = dao.retrieveTestEntities(pagination.previous());
        assertFound(results, 1, 2, 3, 4, 5);
        assertNotSame(pagination, results.getPagination());
        assertPagination(results.getPagination(), 1, true, false, true, true, 4, 19);

        // next() gets us page 2
        pagination = results.getPagination();
        results = dao.retrieveTestEntities(pagination.next());
        assertFound(results, 6, 7, 8, 9, 10);
        assertNotSame(pagination, results.getPagination());
        assertPagination(results.getPagination(), 2, true, true, true, true, 4, 19);

        // page(0) doesn't exist, so we get page 1
        pagination = results.getPagination();
        results = dao.retrieveTestEntities(pagination.page(0));
        assertFound(results, 1, 2, 3, 4, 5);
        assertNotSame(pagination, results.getPagination());
        assertPagination(results.getPagination(), 1, true, false, true, true, 4, 19);

        // page(5) doesn't exist, so we get the largest known page
        pagination = results.getPagination();
        results = dao.retrieveTestEntities(pagination.page(5));
        assertFound(results, 16, 17, 18, 19);
        assertNotSame(pagination, results.getPagination());
        assertPagination(results.getPagination(), 4, true, true, false, true, 4, 19);

        // page(-1) doesn't exist, so we get page 1
        pagination = results.getPagination();
        results = dao.retrieveTestEntities(pagination.page(-1));
        assertFound(results, 1, 2, 3, 4, 5);
        assertNotSame(pagination, results.getPagination());
        assertPagination(results.getPagination(), 1, true, false, true, true, 4, 19);
    }

    /** Test pagination when filtering is not involved, for IntegerIdEntity. */
    @Test public void testPaginationWithFilteringInteger() {
        Pagination pagination;
        PaginatedResults<IntegerIdEntity> results;

        // These entities by default do not have values that get caught by the filter
        // I'm going to pick a few of them and change them so the filter catches them.
        List<IntegerIdEntity> entities = createTestEntitiesInteger(19);
        entities.get(1).setId(101);
        entities.get(9).setId(109);
        entities.get(17).setId(117);

        IntegerIdEntityDao dao = createIntegerIdEntityDao();
        for (IntegerIdEntity entity : entities) {
            dao.insertTestEntity(entity);
        }

        // We'll set up pagination at 5 items per page, which will get us 4 pages
        pagination = new Pagination(5);
        assertPagination(pagination, 1, false, false, false, false, 0, 0);

        // The fetch retrieves page 1, and we know there are at least 2 pages
        results = dao.retrieveTestEntities(pagination);
        assertFound(results, 1, 3, 4, 5, 6);
        assertNotSame(pagination, results.getPagination());
        assertPagination(results.getPagination(), 1, true, false, true, false, 2, 5);

        // page(5) doesn't exist yet, so we get the largest known page, page 2
        pagination = results.getPagination();
        results = dao.retrieveTestEntities(pagination.page(5));
        assertFound(results, 7, 8, 9, 11, 12);
        assertNotSame(pagination, results.getPagination());
        assertPagination(results.getPagination(), 2, true, true, true, false, 3, 10);

        // next() retrieves page 3, and we know there are at least 4 pages
        pagination = results.getPagination();
        results = dao.retrieveTestEntities(pagination.next());
        assertFound(results, 13, 14, 15, 16, 17);
        assertNotSame(pagination, results.getPagination());
        assertPagination(results.getPagination(), 3, true, true, true, false, 4, 15);

        // next() retrieves page 4, and we now know that there are 4 pages total
        pagination = results.getPagination();
        results = dao.retrieveTestEntities(pagination.next());
        assertFound(results, 19);
        assertNotSame(pagination, results.getPagination());
        assertPagination(results.getPagination(), 4, true, true, false, true, 4, 16);

        // page(2) exists, so we get back to page 2
        pagination = results.getPagination();
        results = dao.retrieveTestEntities(pagination.page(2));
        assertFound(results, 7, 8, 9, 11, 12);
        assertNotSame(pagination, results.getPagination());
        assertPagination(results.getPagination(), 2, true, true, true, true, 4, 16);

        // previous() brings us to page 1
        pagination = results.getPagination();
        results = dao.retrieveTestEntities(pagination.previous());
        assertFound(results, 1, 3, 4, 5, 6);
        assertNotSame(pagination, results.getPagination());
        assertPagination(results.getPagination(), 1, true, false, true, true, 4, 16);

        // previous() again doesn't exist, so we get page 1 again
        pagination = results.getPagination();
        results = dao.retrieveTestEntities(pagination.previous());
        assertFound(results, 1, 3, 4, 5, 6);
        assertNotSame(pagination, results.getPagination());
        assertPagination(results.getPagination(), 1, true, false, true, true, 4, 16);

        // next() gets us page 2
        pagination = results.getPagination();
        results = dao.retrieveTestEntities(pagination.next());
        assertFound(results, 7, 8, 9, 11, 12);
        assertNotSame(pagination, results.getPagination());
        assertPagination(results.getPagination(), 2, true, true, true, true, 4, 16);

        // page(0) doesn't exist, so we get page 1
        pagination = results.getPagination();
        results = dao.retrieveTestEntities(pagination.page(0));
        assertFound(results, 1, 3, 4, 5, 6);
        assertNotSame(pagination, results.getPagination());
        assertPagination(results.getPagination(), 1, true, false, true, true, 4, 16);

        // page(5) doesn't exist, so we get the largest known page
        pagination = results.getPagination();
        results = dao.retrieveTestEntities(pagination.page(5));
        assertFound(results, 19);
        assertNotSame(pagination, results.getPagination());
        assertPagination(results.getPagination(), 4, true, true, false, true, 4, 16);

        // page(-1) doesn't exist, so we get page 1
        pagination = results.getPagination();
        results = dao.retrieveTestEntities(pagination.page(-1));
        assertFound(results, 1, 3, 4, 5, 6);
        assertNotSame(pagination, results.getPagination());
        assertPagination(results.getPagination(), 1, true, false, true, true, 4, 16);
    }

    /** Assert that the passed-in events, and no others, are found in results. */
    private static void assertFound(PaginatedResults<StringIdEntity> results, String... ids) {
        assertEquals(ids.length, results.size());
        for (int i = 0; i < ids.length; i++) {
            assertEquals(ids[i], results.get(i).getId());
        }
    }

    /** Assert that the passed-in events, and no others, are found in results. */
    private static void assertFound(PaginatedResults<IntegerIdEntity> results, int... ids) {
        assertEquals(ids.length, results.size());
        for (int i = 0; i < ids.length; i++) {
            assertEquals(ids[i], results.get(i).getId());
        }
    }

    /** Assert that a pagination object contains the expected values. */
    private static void assertPagination(Pagination actual,
                                         int pageNumber, boolean hasData,
                                         boolean hasPrevious, boolean hasNext,
                                         boolean isTotalFinalized,
                                         int totalPages, int totalRows) {
        assertEquals("For pageNumber, ", pageNumber, actual.getPageNumber());
        assertEquals("For hasData, ", hasData, actual.hasData());
        assertEquals("For hasPrevious, ", hasPrevious, actual.hasPrevious());
        assertEquals("For hasNext, ", hasNext, actual.hasNext());
        assertEquals("For isTotalFinalized, ", isTotalFinalized, actual.isTotalFinalized());
        assertEquals("For totalPages, ", totalPages, actual.getTotalPages());
        assertEquals("For totalRows, ", totalRows, actual.getTotalRows());
    }

    /** Add test entities to our stubbed data store. */
    private static List<StringIdEntity> createTestEntitiesString(int count) {
        List<StringIdEntity> entities = new ArrayList<StringIdEntity>();

        for (int i = 1; i <= count; i++) {
            String id = "entity" + i;
            String milliseconds = new PrintfFormat("%03d").sprintf(i);
            Date timestamp = DateUtils.createDate("2011-06-01T14:00:00." + milliseconds);
            StringIdEntity testEntity = new StringIdEntity(id, timestamp);
            entities.add(testEntity);
        }

        return entities;
    }

    /** Add test entities to our stubbed data store. */
    private static List<IntegerIdEntity> createTestEntitiesInteger(int count) {
        List<IntegerIdEntity> entities = new ArrayList<IntegerIdEntity>();

        for (int i = 1; i <= count; i++) {
            long id = i;
            String milliseconds = new PrintfFormat("%03d").sprintf(i);
            Date timestamp = DateUtils.createDate("2011-06-01T14:00:00." + milliseconds);
            IntegerIdEntity testEntity = new IntegerIdEntity(id, timestamp);
            entities.add(testEntity);
        }

        return entities;
    }

    /** Create a DAO for testing against the stubbed datastore. */
    private static StringIdEntityDao createStringIdEntityDao() {
        IDaoObjectifyService daoObjectifyService = getDaoObjectifyService();
        StringIdEntityDao dao = new StringIdEntityDao();
        dao.setDaoObjectifyService(daoObjectifyService);
        dao.afterPropertiesSet();
        return dao;
    }

    /** Create a DAO for testing against the stubbed datastore. */
    private static IntegerIdEntityDao createIntegerIdEntityDao() {
        IDaoObjectifyService daoObjectifyService = getDaoObjectifyService();
        IntegerIdEntityDao dao = new IntegerIdEntityDao();
        dao.setDaoObjectifyService(daoObjectifyService);
        dao.afterPropertiesSet();
        return dao;
    }

}
