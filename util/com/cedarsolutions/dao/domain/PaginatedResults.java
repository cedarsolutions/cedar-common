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
package com.cedarsolutions.dao.domain;

import java.util.ArrayList;

/**
 * Paginated results for use by GAE DAOs.
 * @param <T> Type of the results
 * @author Kenneth J. Pronovici <pronovic@ieee.org>
 */
@SuppressWarnings("serial")
public class PaginatedResults<T> extends ArrayList<T> {

    /** Pagination that is in use. */
    private Pagination pagination;

    /** Create empty paginated results. */
    public PaginatedResults() {
    }

    /** Pagination that applies to the current result set. */
    public Pagination getPagination() {
        return pagination;
    }

    public void setPagination(Pagination pagination) {
        this.pagination = pagination;
    }

}
