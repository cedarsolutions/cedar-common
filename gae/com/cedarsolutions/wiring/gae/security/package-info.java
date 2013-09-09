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

/**
 * Classes uses to integrate Spring 2.5 Security with Google App Engine (GAE).
 *
 * <p>
 * This functionality is based on the
 * <a href="http://blog.springsource.com/2010/08/02/spring-security-in-google-app-engine/">Spring Security in Google App Engine</a>
 * discussion at the SpringSource blog. It is used to integrate Spring Security
 * around Spring services that have been exposed as GWT RPC services via
 * <a href="http://code.google.com/p/gwt-sl">GWT-SL</a>.
 * </p>
 *
 * <p>
 * GAE has basically three user roles: anonymous users (i.e. not logged in),
 * logged-in users, and admin users (which are logged-in users that are flagged
 * as administrators in GAE configuration). These three user roles are exposed
 * to Spring security as as ROLE_ANONYMOUS, ROLE_USER, and ROLE_ADMIN. If you
 * add the annotation \@Secured("ROLE_USER") to a service method, then Spring will
 * enforce that only logged-in users may invoke that method.
 * </p>
 *
 * <p>
 * Typical Spring configuration in <code>applicationContext.xml</code> would
 * look something like this:
 * </p>
 *
 * <pre>
 *  &lt;http auto-config="false" entry-point-ref="gaeEntryPoint" session-fixation-protection="newSession" /&gt;
 *
 *  &lt;authentication-manager alias="gaeAuthenticationManager" /&gt;
 *
 *  &lt;beans:bean id="gaeEntryPoint" class="com.cedarsolutions.gae.security.GaeAuthenticationEntryPoint" /&gt;
 *
 *  &lt;beans:bean id="gaeAuthenticationProvider" class="com.cedarsolutions.gae.security.GaeAuthenticationProvider"&gt;
 *      &lt;custom-authentication-provider /&gt;
 *  &lt;/beans:bean&gt;
 *
 *  &lt;beans:bean id="gaeAuthenticationFilter" class="com.cedarsolutions.gae.security.GaeAuthenticationFilter"&gt;
 *      &lt;custom-filter position="PRE_AUTH_FILTER" /&gt;
 *      &lt;beans:property name="userService" ref="userService" /&gt;
 *      &lt;beans:property name="authenticationManager" ref="gaeAuthenticationManager" /&gt;
 *  &lt;/beans:bean&gt;
 *
 *  &lt;beans:bean id="userService" class="com.google.appengine.api.users.UserServiceFactory" factory-method="getUserService"/&gt;
 * </pre>
 *
 * @author Kenneth J. Pronovici <pronovic@ieee.org>
 */
package com.cedarsolutions.wiring.gae.security;
