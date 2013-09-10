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
import com.googlecode.objectify.Query;

/**
 * DAO over StringIdEntity, used for unit tests.
 * @author Kenneth J. Pronovici <pronovic@ieee.org>
 */
public class StringIdEntityDao extends AbstractGaeDao {

    public void insertTestEntity(StringIdEntity testEntity) {
        getObjectify().put(testEntity);
    }

    public StringIdEntity retrieveTestEntity(String id) {
        return getObjectify().find(StringIdEntity.class, id);
    }

    public void deleteTestEntity(String id) {
        StringIdEntity testEntity = retrieveTestEntity(id);
        if (testEntity != null) {
            this.deleteTestEntity(testEntity);
        }
    }

    public void deleteTestEntity(StringIdEntity testEntity) {
        getObjectify().delete(testEntity);
    }

    public void updateTestEntity(StringIdEntity testEntity) {
        getObjectify().put(testEntity);
    }

    public PaginatedResults<StringIdEntity> retrieveTestEntities() {
        Query<StringIdEntity> query = getObjectify().query(StringIdEntity.class);
        query.order("timestamp");
        NoXFilterPredicate predicate = new NoXFilterPredicate();
        FilteredResultIterator<StringIdEntity> iterator = new FilteredResultIterator<StringIdEntity>(query, predicate);
        return PaginationUtils.createPaginatedResults(iterator);
    }

    public PaginatedResults<StringIdEntity> retrieveTestEntities(Pagination pagination) {
        Query<StringIdEntity> query = getObjectify().query(StringIdEntity.class, pagination);
        query.order("timestamp");
        NoXFilterPredicate predicate = new NoXFilterPredicate();
        FilteredResultIterator<StringIdEntity> iterator = new FilteredResultIterator<StringIdEntity>(query, predicate);
        return PaginationUtils.createPaginatedResults(pagination, iterator);
    }

}
