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
package com.cedarsolutions.server.service.impl;

import org.springframework.beans.factory.InitializingBean;

import com.cedarsolutions.exception.NotConfiguredException;

/**
 * Abstract class that all GWT back-end services inherit from.
 *
 * <p>
 * Note that services cannot throw any exception that isn't translated. You will
 * also have problems if undeclared exceptions bubble up through the service
 * interface. As a result, you need to configure your services properly. Use the
 * SecuredServiceExporter, and design your service interfaces to always throw
 * ServiceException.
 * </p>
 *
 * @author Kenneth J. Pronovici <pronovic@ieee.org>
 */
public abstract class AbstractService implements InitializingBean {

    /**
     * Invoked by a bean factory after it has set all bean properties.
     * @throws NotConfiguredException In the event of misconfiguration.
     */
    @Override
    public void afterPropertiesSet() throws NotConfiguredException {
        // no-op right now, but reserved for future functionality
    }

}
