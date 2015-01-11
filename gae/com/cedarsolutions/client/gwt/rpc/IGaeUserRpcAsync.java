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
package com.cedarsolutions.client.gwt.rpc;

import com.cedarsolutions.shared.domain.FederatedUser;
import com.cedarsolutions.shared.domain.OpenIdProvider;
import com.google.gwt.user.client.rpc.AsyncCallback;

/**
 * Asynchronous version of IGaeUserRpc.
 * @author Kenneth J. Pronovici <pronovic@ieee.org>
 */
public interface IGaeUserRpcAsync {

    /** Get a proper login URL to use with GAE's Google Accounts login mechanism. */
    void getGoogleAccountsLoginUrl(String destinationUrl, AsyncCallback<String> callback);

    /** Get a proper logout URL to use with GAE's Google Accounts login mechanism. */
    void getGoogleAccountsLogoutUrl(String destinationUrl, AsyncCallback<String> callback);

    /**
     * Get a proper login URL to use with GAE's federated login mechanism.
     * @deprecated Google has (vaguely) documented that the federated login mechanism (always "experimental") will be going away.
     */
    @Deprecated
    void getLoginUrl(OpenIdProvider openIdProvider, String destinationUrl, AsyncCallback<String> callback);

    /**
     * Get a proper logout URL to use with GAE's federated login mechanism.
     * @deprecated Google has (vaguely) documented that the federated login mechanism (always "experimental") will be going away.
     */
    @Deprecated
    void getLogoutUrl(String destinationUrl, AsyncCallback<String> callback);

    /** Returns true if there is a user logged in, false otherwise. */
    void isUserLoggedIn(AsyncCallback<Boolean> callback);

    /** Returns true if the user making this request is an admin for this application, false otherwise. */
    void isUserAdmin(AsyncCallback<Boolean> callback);

    /** The current logged in user, or null if no user is logged in. */
    void getCurrentUser(AsyncCallback<FederatedUser> callback);

}
