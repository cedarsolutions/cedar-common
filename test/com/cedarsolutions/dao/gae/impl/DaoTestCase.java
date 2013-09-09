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

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;

import com.cedarsolutions.dao.gae.IDaoObjectifyService;
import com.cedarsolutions.junit.gae.DaoTestUtils;
import com.cedarsolutions.junit.util.TestUtils;
import com.google.appengine.tools.development.testing.LocalDatastoreServiceTestConfig;
import com.google.appengine.tools.development.testing.LocalServiceTestHelper;

/**
* Test case that all DAO tests inherit from.
*
* <p>
* This test case knows how to stub out the GAE datastore, via some utilities
* that Google provides.
* </p>
*
* <p>
* Note that you must have appengine-api-stubs.jar and appengine-testing.jar on
* your unit test classpath for this to work.  I can't find an easy way of making
* that happen, except by copying the jars into the lib directory... so, updating
* these jars is just one more thing that you have to do when a new AppEngine version
* is released.
* </p>
*
* @see <a href="http://code.google.com/appengine/docs/java/tools/localunittesting.html">Local Unit Testing for Java</a>
* @author Kenneth J. Pronovici <pronovic@ieee.org>
*/
public abstract class DaoTestCase {

   /** Singleton reference to DaoObjectifyService. */
   private static DaoObjectifyService DAO_OBJECTIFY_SERVICE;

   /** GAE helper class that stubs out datastore access. */
   protected final LocalServiceTestHelper helper = new LocalServiceTestHelper(new LocalDatastoreServiceTestConfig());

   @BeforeClass
   public static void setUpGaeLogging() {
       // The datastore writes lots of crap to the log, which we can't control otherwise
       TestUtils.setUpGaeLogging();
   }

   /** Set up the stubbed AppEngine datastore. */
   @Before
   public void setupAppEngineDatastore() {
       helper.setUp();
   }

   /** Tear down the stubbed AppEngine datastore. */
   @After
   public void tearDownAppEngineDatastore() {
       helper.tearDown();
   }

   /** Get a singleton reference to the DaoObjectifyService that should be used for testing. */
   @SuppressWarnings("rawtypes")
   protected static IDaoObjectifyService getDaoObjectifyService() {
       if (DAO_OBJECTIFY_SERVICE == null) {
           List<Class> classes = new ArrayList<Class>();
           classes.add(StringIdEntity.class);
           classes.add(IntegerIdEntity.class);

           DAO_OBJECTIFY_SERVICE = DaoTestUtils.createDaoObjectifyService(classes);
       }

       return DAO_OBJECTIFY_SERVICE;
   }

   /** Get a DaoObjectifyService that returns a mock ObjectifyProxy. */
   protected static IDaoObjectifyService getMockedDaoObjectifyService() {
       IDaoObjectifyService service = mock(IDaoObjectifyService.class);

       ObjectifyProxy proxy = mock(ObjectifyProxy.class);
       when(service.getObjectify()).thenReturn(proxy);
       when(service.getObjectifyWithTransaction()).thenReturn(proxy);

       return service;
   }

}
