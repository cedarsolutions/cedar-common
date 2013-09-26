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
package com.cedarsolutions.server.service;

import com.cedarsolutions.exception.ServiceException;
import com.cedarsolutions.shared.domain.FederatedUser;
import com.cedarsolutions.shared.domain.OpenIdProvider;

/**
 * User service designed for use with Google App Engine.
 *
 * <p>
 * This interface is intended as stable proxy over the static methods
 * in the Google-provided GAE UserService.  All of the service methods
 * are targeted around federated users, with authentication provided
 * via OpenId.
 * <p>
 *
 * @author Kenneth J. Pronovici <pronovic@ieee.org>
 */
public interface IGaeUserService {

    /**
     * Get a proper login URL.
     * @param openIdProvider  Open id provider to use
     * @param destinationUrl  Destination URL to redirect to after login
     * @return Login URL that should be presented to the user.
     */
    String getLoginUrl(OpenIdProvider openIdProvider, String destinationUrl) throws ServiceException;

    /**
     * Get a proper logout URL.
     * @param destinationUrl Destination URL to redirect to after logout
     * @return Logout URL that should be presented to the user.
     */
    String getLogoutUrl(String destinationUrl) throws ServiceException;

    /** Returns true if there is a user logged in, false otherwise. */
    boolean isUserLoggedIn() throws ServiceException;

    /** Returns true if the user making this request is an admin for this application, false otherwise. */
    boolean isUserAdmin() throws ServiceException;

    /** The current logged in user, or null if no user is logged in. */
    FederatedUser getCurrentUser() throws ServiceException;

    /** Add extra roles for the current user, for use by the security framework. */
    void addClientRoles(String ... roles);

}
