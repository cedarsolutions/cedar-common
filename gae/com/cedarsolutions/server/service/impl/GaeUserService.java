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

import static com.cedarsolutions.shared.domain.OpenIdProvider.AOL;
import static com.cedarsolutions.shared.domain.OpenIdProvider.GOOGLE;
import static com.cedarsolutions.shared.domain.OpenIdProvider.MYOPENID;
import static com.cedarsolutions.shared.domain.OpenIdProvider.MYSPACE;
import static com.cedarsolutions.shared.domain.OpenIdProvider.UNKNOWN;
import static com.cedarsolutions.shared.domain.OpenIdProvider.YAHOO;
import static com.cedarsolutions.wiring.gae.security.GaeAuthenticationFilter.CLIENT_ROLES_ATTRIBUTE;

import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.cedarsolutions.exception.EnumException;
import com.cedarsolutions.exception.NotConfiguredException;
import com.cedarsolutions.exception.ServiceException;
import com.cedarsolutions.server.service.IGaeUserService;
import com.cedarsolutions.server.service.ISpringContextService;
import com.cedarsolutions.shared.domain.FederatedUser;
import com.cedarsolutions.shared.domain.OpenIdProvider;
import com.cedarsolutions.util.StringUtils;
import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;

/**
 * User service designed for use with Google App Engine.
 * This interface is intended as stable proxy over the static methods in the Google-provided GAE UserService.
 * @author Kenneth J. Pronovici <pronovic@ieee.org>
 */
public class GaeUserService extends AbstractService implements IGaeUserService {

    /** The underlying Google user service. */
    private UserService userService;

    /** Spring context service. */
    private ISpringContextService springContextService;

    /**
     * Invoked by a bean factory after it has set all bean properties.
     * @throws NotConfiguredException In the event of misconfiguration.
     */
    @Override
    public void afterPropertiesSet() throws NotConfiguredException {
        super.afterPropertiesSet();
        if (this.userService == null || this.springContextService == null) {
            throw new NotConfiguredException("GaeUserService is not properly configured.");
        }
    }

    /**
     * Get a proper login URL to use with GAE's Google Accounts login mechanism.
     * @param destinationUrl  Destination URL to redirect to after login
     * @return Login URL that should be presented to the user.
     */
    @Override
    public String getGoogleAccountsLoginUrl(String destinationUrl) throws ServiceException {
        return this.userService.createLoginURL(destinationUrl);
    }

    /**
     * Get a proper logout URL to use with GAE's Google Accounts login mechanism.
     * @param destinationUrl Destination URL to redirect to after logout
     * @return Logout URL that should be presented to the user.
     */
    @Override
    public String getGoogleAccountsLogoutUrl(String destinationUrl) throws ServiceException {
        return this.userService.createLogoutURL(destinationUrl);
    }

    /**
     * Get a proper login URL to use with GAE's federated login mechanism.
     *
     * <p>
     * This doesn't work as consistently as one might hope. It does seem to work
     * OK on the development server. However, when the application is deployed
     * into the cloud, UserService strips off URL parameters and fragments. This
     * means that deep bookmarks won't work properly.
     * </p>
     *
     * <p>
     * For a while, I was able to work around this when using Google as the
     * OpenId provider, by manually correcting the generated URL.  However,
     * as of late March 2011, that has started causing a server error on
     * Google's end.
     * </p>
     *
     * @param openIdProvider Open id provider to use
     * @param destinationUrl Destination URL to redirect to after login
     *
     * @return Login URL that should be presented to the user.
     *
     * @see <a href="http://code.google.com/p/googleappengine/issues/detail?id=3756">Issue 3756</a>
     * @see <a href="http://code.google.com/p/googleappengine/issues/detail?id=4303">Issue 4303</a>
     */
    @Override
    public String getLoginUrl(OpenIdProvider openIdProvider, String destinationUrl) throws ServiceException {
        Set<String> attributes = new HashSet<String>();
        String providerUrl = deriveOpenIdProviderUrl(openIdProvider);
        String loginUrl = this.userService.createLoginURL(destinationUrl, null, providerUrl, attributes);
        return loginUrl;
    }

    /**
     * Get a proper logout URL to use with GAE's federated login mechanism.
     * @param destinationUrl Destination URL to redirect to after logout
     * @return Logout URL that should be presented to the user.
     */
    @Override
    public String getLogoutUrl(String destinationUrl) throws ServiceException {
        String logoutUrl = this.userService.createLogoutURL(destinationUrl);
        return logoutUrl;
    }

    /** Returns true if there is a user logged in, false otherwise. */
    @Override
    public boolean isUserLoggedIn() throws ServiceException {
        return this.userService.isUserLoggedIn();
    }

    /** Returns true if the user making this request is an admin for this application, false otherwise. */
    @Override
    public boolean isUserAdmin() throws ServiceException {
        return this.userService.isUserAdmin();
    }

    /** The current logged in user, or null if no user is logged in. */
    @Override
    public FederatedUser getCurrentUser() throws ServiceException {
        if (!this.isUserLoggedIn()) {
            return null;
        } else {
            User user = this.userService.getCurrentUser();
            boolean admin = this.isUserAdmin();
            OpenIdProvider openIdProvider = deriveOpenIdProvider(user.getAuthDomain());

            // Normally, I'd put a User constructor onto CurrentUser.  That won't
            // work, because then CurrentUser has a dependency on code which isn't
            // translated and isn't available to GWT clients.

            FederatedUser currentUser = new FederatedUser();
            currentUser.setAuthenticationDomain(user.getAuthDomain());
            currentUser.setOpenIdProvider(openIdProvider);
            currentUser.setEmailAddress(user.getEmail());
            currentUser.setFederatedIdentity(user.getFederatedIdentity());
            currentUser.setUserId(user.getUserId());
            currentUser.setAdmin(admin);

            // We can't generate a user name until everything else is filled in

            String userName = deriveUserName(currentUser);
            currentUser.setUserName(userName);

            return currentUser;
        }
    }

    /** Add extra roles for the current user, for use by the security framework. */
    @Override
    public void addClientRoles(String ... roles) {
        this.springContextService.setSessionAttribute(CLIENT_ROLES_ATTRIBUTE, roles);
    }


    /**
     * Derive the proper URL to use when logging in with an OpenIdProvider.
     * These constants are provided by Google.
     * @param openIdProvider  Provider to get URL for
     * @return Provider URL that must be used
     */
    protected static String deriveOpenIdProviderUrl(OpenIdProvider openIdProvider) {

        // Originally, I put this information in with OpenIdProvider itself,
        // assuming these were basically constants.  However, it turns out that
        // there's a lot of business logic around dealing with OpenId.  This
        // functionality belongs in the service layer.

        switch(openIdProvider) {
        case GOOGLE:
            return "www.google.com/accounts/o8/id";
        case YAHOO:
            return "yahoo.com";
        case MYSPACE:
            return "myspace.com";
        case AOL:
            return "aol.com";
        case MYOPENID:
            return "myopenid.com";
        default:
            throw new EnumException("Unknown open id provider: " + openIdProvider);
        }
    }

    /**
     * Get an OpenIdProvider based on authentication domain.
     * @param authenticationDomain  Authentication domain as from User.getAuthDomain()
     * @return OpenIdProvider associated with the authentication domain, or UNKNOWN.
     */
    protected static OpenIdProvider deriveOpenIdProvider(String authenticationDomain) {

        // Originally, I put this information in with OpenIdProvider itself,
        // assuming these were basically constants.  However, it turns out that
        // there's a lot of business logic around dealing with OpenId.  This
        // functionality belongs in the service layer.
        //
        // I initially expected the values in the authentication domain to map back
        // to the values that we use (above) to select a particular open id
        // provider.  However, that's not the case.  Instead, we get back provider-
        // specific URLs.

        if (StringUtils.contains(authenticationDomain, "google.com")) {
            // Something like: https://www.google.com/accounts/o8/ud
            return GOOGLE;
        } else if (StringUtils.contains(authenticationDomain, "gmail.com")) {
            // Handle gmail.com just in case
            return GOOGLE;
        } else if (StringUtils.contains(authenticationDomain, "yahoo.com")) {
            // Handle yahoo.com just in case
            return YAHOO;
        } else if (StringUtils.contains(authenticationDomain, "yahooapis.com")) {
            // Something like: https://open.login.yahooapis.com/openid/op/auth
            return YAHOO;
        } else if (StringUtils.contains(authenticationDomain, "myspace.com")) {
            // Something like: http://api.myspace.com/openid
            return MYSPACE;
        } else if (StringUtils.contains(authenticationDomain, "aol.com")) {
            // Something like: https://api.screenname.aol.com/auth/openidServer
            return AOL;
        } else if (StringUtils.contains(authenticationDomain, "myopenid.com")) {
            // Something like: https://www.myopenid.com/server
            return MYOPENID;
        } else {
            return UNKNOWN;
        }
    }

    /**
     * Derive a user name for a federated user.
     *
     * <p>
     * I want the username to be something legible and email-like.  However, some
     * providers don't provide an email address.  So, I have to derive it based
     * on other returned information -- usually based on the federated identity.
     * </p>
     *
     * @param federatedUser  Federated user to generate user name for
     * @return Valid username for the passed-in user, possibly empty.
     */
    protected static String deriveUserName(FederatedUser federatedUser) {
        switch(federatedUser.getOpenIdProvider()) {
        case GOOGLE:
            return deriveEmailBasedUserName(federatedUser);
        case YAHOO:
            return deriveEmailBasedUserName(federatedUser);
        case MYSPACE:
            return deriveMySpaceUserName(federatedUser);
        case AOL:
            return deriveEmailBasedUserName(federatedUser);
        case MYOPENID:
            return deriveMyOpenIdUserName(federatedUser);
        default:
            return "";
        }
    }

    /**
     * Derive a user name for a federated user based on email address.
     * @param federatedUser  Federated user to generate user name for
     * @return Valid username for the passed-in user, possibly empty.
     */
    private static String deriveEmailBasedUserName(FederatedUser federatedUser) {
        if (!StringUtils.isEmpty(federatedUser.getEmailAddress())) {
            return federatedUser.getEmailAddress();
        }

        return "";
    }

    /**
     * Derive the user name for a MySpace federated user.
     * @param federatedUser  Federated user to generate user name for
     * @return Valid username for the passed-in user, possibly empty.
     */
    private static String deriveMySpaceUserName(FederatedUser federatedUser) {
        // The federated identity looks like "http://www.myspace.com/its_misterphil"
        final Pattern pattern = Pattern.compile("(http[s]?://www\\.myspace\\.com\\/)(.*$)");

        if (!StringUtils.isEmpty(federatedUser.getFederatedIdentity())) {
            Matcher matcher = pattern.matcher(federatedUser.getFederatedIdentity());
            if (matcher.matches()) {
                return matcher.group(2) + "@myspace.com";
            }
        }

        return "";
    }

    /**
     * Derive the user name for a MyOpenId federated user.
     * @param federatedUser  Federated user to generate user name for
     * @return Valid username for the passed-in user, possibly empty.
     */
    private static String deriveMyOpenIdUserName(FederatedUser federatedUser) {
        // The federated identity looks like "https://pronovic.myopenid.com/"
        final Pattern pattern = Pattern.compile("(http[s]?://)(.*)(\\.myopenid\\.com[\\/]?$)");

        if (!StringUtils.isEmpty(federatedUser.getFederatedIdentity())) {
            Matcher matcher = pattern.matcher(federatedUser.getFederatedIdentity());
            if (matcher.matches()) {
                return matcher.group(2) + "@myopenid.com";
            }
        }

        return "";
    }

    public UserService getUserService() {
        return this.userService;
    }

    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    public ISpringContextService getSpringContextService() {
        return this.springContextService;
    }

    public void setSpringContextService(ISpringContextService springContextService) {
        this.springContextService = springContextService;
    }

}
