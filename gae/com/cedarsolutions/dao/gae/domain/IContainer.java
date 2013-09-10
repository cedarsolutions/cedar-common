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
package com.cedarsolutions.dao.gae.domain;

import java.io.Serializable;

/**
 * Container to hold data in GAE's data store.
 *
 * <p>
 * Practically speaking, it's difficult to directly store large nested object
 * graphs in GAE's datastore.  It's easier to just create a container
 * object and store the real data in serialized format. The container
 * object can then have all of the indexable fields in one place, and GAE
 * doesn't have to care about the structure of the underlying data.  Since
 * the DAO interface doesn't expose the container, no other code has to
 * know anything about it.
 * </p>
 *
 * @param <T> Type of data the container holds.
 * @author Kenneth J. Pronovici <pronovic@ieee.org>
 */
public interface IContainer<T> extends Serializable {

    /** Initialize the container based on a value of type T. */
    void fromValue(T value);

    /** Turn the container into a value of type T. */
    T toValue();

}
