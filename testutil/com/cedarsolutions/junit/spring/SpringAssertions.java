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
package com.cedarsolutions.junit.spring;

import static org.junit.Assert.assertNotNull;

import org.springframework.context.ApplicationContext;

/**
 * Assertion code useful when testing Spring annotations.
 *
 * <p>
 * The assertion below simulates what happens when the application server
 * boots, at least to the extent that some screw-ups in the Spring context
 * files become obvious.
 * </p>
 *
 * <p>
 * Sometimes, odd errors result from this test, and it's difficult to track
 * then down.  Here's one that took me a while:
 * </p>
 *
 * <pre>
 * Initialization of bean failed; nested exception is java.lang.LinkageError:
 * loader constraint violation:
 * loader (instance of com/cedarsolutions/junit/gwt/classloader/GwtClassLoader)
 * previously initiated loading for a different type with name "org/aopalliance/aop/Advice"
 * </pre>
 *
 * <p>
 * This is a hint that GwtClassLoader should delegate loading of this class
 * (and probably all classes in the package) to the default classloader.
 * See GwtClassLoader.handleDelegatedClasses() in CedarCommon.
 * </p>
 *
 * @author Kenneth J. Pronovici <pronovic@ieee.org>
 */
public class SpringAssertions {

    /**
     * Assert that the Spring context is valid.
     * @param contextPath  Context path directory, like "war/WEB-INF"
     * @param locations    List of Spring context files within contextPath, like "rpc-servlet.xml"
     */
    public static void assertSpringContextValid(String contextPath, String... locations) throws Exception {
        // Load the context, which confirms that the files have a valid format
        MockWebContextLoader loader = new MockWebContextLoader(contextPath);
        ApplicationContext context = loader.loadContext(locations);
        assertNotNull(context);

        // Try to retrieve every bean, which lets us see problems even for lazily-instantiated beans
        for (String name : context.getBeanDefinitionNames()) {
            Object bean = context.getBean(name);
            assertNotNull(bean);
        }
    }

}
