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

import com.cedarsolutions.dao.domain.PaginatedResults;
import com.cedarsolutions.dao.domain.Pagination;
import com.googlecode.objectify.Query;

/**
 * DAO over IntegerIdEntity, used for unit tests.
 * @author Kenneth J. Pronovici <pronovic@ieee.org>
 */
public class IntegerIdEntityDao extends AbstractGaeDao {

    public void insertTestEntity(IntegerIdEntity testEntity) {
        getObjectify().put(testEntity);
    }

    public IntegerIdEntity retrieveTestEntity(int id) {
        return getObjectify().find(IntegerIdEntity.class, id);
    }

    public void deleteTestEntity(int id) {
        IntegerIdEntity testEntity = retrieveTestEntity(id);
        if (testEntity != null) {
            this.deleteTestEntity(testEntity);
        }
    }

    public void deleteTestEntity(IntegerIdEntity testEntity) {
        getObjectify().delete(testEntity);
    }

    public void updateTestEntity(IntegerIdEntity testEntity) {
        getObjectify().put(testEntity);
    }

    public PaginatedResults<IntegerIdEntity> retrieveTestEntities() {
        Query<IntegerIdEntity> query = getObjectify().query(IntegerIdEntity.class);
        query.order("timestamp");
        NoLargeIdFilterPredicate predicate = new NoLargeIdFilterPredicate();
        FilteredResultIterator<IntegerIdEntity> iterator = new FilteredResultIterator<IntegerIdEntity>(query, predicate);
        return PaginationUtils.createPaginatedResults(iterator);
    }

   public PaginatedResults<IntegerIdEntity> retrieveTestEntities(Pagination pagination) {
       Query<IntegerIdEntity> query = getObjectify().query(IntegerIdEntity.class, pagination);
       query.order("timestamp");
       NoLargeIdFilterPredicate predicate = new NoLargeIdFilterPredicate();
       FilteredResultIterator<IntegerIdEntity> iterator = new FilteredResultIterator<IntegerIdEntity>(query, predicate);
       return PaginationUtils.createPaginatedResults(pagination, iterator);
   }

}
