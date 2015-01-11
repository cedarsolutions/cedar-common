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
package com.cedarsolutions.server.rpc.impl;

import com.cedarsolutions.client.gwt.rpc.IGaeUserRpc;
import com.cedarsolutions.exception.NotConfiguredException;
import com.cedarsolutions.exception.ServiceException;
import com.cedarsolutions.server.service.IGaeUserService;
import com.cedarsolutions.server.service.impl.AbstractService;
import com.cedarsolutions.shared.domain.FederatedUser;
import com.cedarsolutions.shared.domain.OpenIdProvider;

/**
 * Client-visible user functionality for Google App Engine.
 * @author Kenneth J. Pronovici <pronovic@ieee.org>
 */
public class GaeUserRpc extends AbstractService implements IGaeUserRpc {

    /** The back-end GAE user service. */
    private IGaeUserService gaeUserService;

    /**
     * Invoked by a bean factory after it has set all bean properties.
     * @throws NotConfiguredException In the event of misconfiguration.
     */
    @Override
    public void afterPropertiesSet() throws NotConfiguredException {
        super.afterPropertiesSet();
        if (this.gaeUserService == null) {
            throw new NotConfiguredException("GaeUserRpc is not properly configured.");
        }
    }

    /**
     * Get a proper login URL to use with GAE's Google Accounts login mechanism.
     * @param destinationUrl  Destination URL to redirect to after login
     * @return Login URL that should be presented to the user.
     */
    @Override
    public String getGoogleAccountsLoginUrl(String destinationUrl) throws ServiceException {
        return this.gaeUserService.getGoogleAccountsLoginUrl(destinationUrl);
    }

    /**
     * Get a proper logout URL to use with GAE's Google Accounts login mechanism.
     * @param destinationUrl Destination URL to redirect to after logout
     * @return Logout URL that should be presented to the user.
     */
    @Override
    public String getGoogleAccountsLogoutUrl(String destinationUrl) throws ServiceException {
        return this.gaeUserService.getGoogleAccountsLogoutUrl(destinationUrl);
    }

    /**
     * Get a proper login URL to use with GAE's federated login mechanism.
     * @param openIdProvider Open id provider to use
     * @param destinationUrl Destination URL to redirect to after login
     * @return Login URL that should be presented to the user.
     */
    @Override
    @SuppressWarnings("deprecation")
    public String getLoginUrl(OpenIdProvider openIdProvider, String destinationUrl) throws ServiceException {
        return this.gaeUserService.getLoginUrl(openIdProvider, destinationUrl);
    }

    /**
     * Get a proper logout URL to use with GAE's federated login mechanism.
     * @param destinationUrl Destination URL to redirect to after logout
     * @return Logout URL that should be presented to the user.
     */
    @Override
    @SuppressWarnings("deprecation")
    public String getLogoutUrl(String destinationUrl) throws ServiceException {
        return this.gaeUserService.getLogoutUrl(destinationUrl);
    }

    /** Returns true if there is a user logged in, false otherwise. */
    @Override
    public boolean isUserLoggedIn() throws ServiceException {
        return this.gaeUserService.isUserLoggedIn();
    }

    /** Returns true if the user making this request is an admin for this application, false otherwise. */
    @Override
    public boolean isUserAdmin() throws ServiceException {
        return this.gaeUserService.isUserAdmin();
    }

    /** The current logged in user, or null if no user is logged in. */
    @Override
    public FederatedUser getCurrentUser() throws ServiceException {
        return this.gaeUserService.getCurrentUser();
    }

    public IGaeUserService getGaeUserService() {
        return this.gaeUserService;
    }

    public void setGaeUserService(IGaeUserService gaeUserService) {
        this.gaeUserService = gaeUserService;
    }

}
