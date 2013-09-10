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

import static org.springframework.web.context.WebApplicationContext.ROOT_WEB_APPLICATION_CONTEXT_ATTRIBUTE;

import javax.servlet.ServletContext;

import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigUtils;
import org.springframework.core.io.FileSystemResourceLoader;
import org.springframework.mock.web.MockServletContext;
import org.springframework.test.context.MergedContextConfiguration;
import org.springframework.test.context.support.AbstractContextLoader;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.GenericWebApplicationContext;

import com.cedarsolutions.exception.NotImplementedException;

/**
 * Mock web container, used for loading the Spring context in unit tests.
 *
 * <p>
 * This simulates what happens when the application server boots, at least
 * to the extent that some screw-ups in the Spring context files become obvious.
 * </p>
 *
 * @see <a href="http://www.vijedi.net/2009/integration-testing-spring-mvc-annotated-controllers/">Integration testing Spring MVC Controllers</a>
 * @author Kenneth J. Pronovici <pronovic@ieee.org>
 */
public class MockWebContextLoader extends AbstractContextLoader {

    private String contextPath;
    private String[] locations;
    private ServletContext servletContext;
    private GenericWebApplicationContext webContext;

    public MockWebContextLoader(String contextPath) {
        this.contextPath = contextPath;
    }

    @Override
    protected String getResourceSuffix() {
        return "-context.xml";
    }

    @Override
    public ApplicationContext loadContext(final String... locations) throws Exception {
        this.locations = locations;

        this.servletContext = new MockServletContext(this.contextPath, new FileSystemResourceLoader());
        this.webContext = new GenericWebApplicationContext();

        this.servletContext.setAttribute(ROOT_WEB_APPLICATION_CONTEXT_ATTRIBUTE, this.webContext);
        this.webContext.setServletContext(this.servletContext);

        new XmlBeanDefinitionReader(this.webContext).loadBeanDefinitions(locations);
        AnnotationConfigUtils.registerAnnotationConfigProcessors(this.webContext);
        this.webContext.refresh();
        this.webContext.registerShutdownHook();

        return this.webContext;
    }

    @Override
    public ApplicationContext loadContext(MergedContextConfiguration context) throws Exception {
        throw new NotImplementedException("Not implemented");
    }

    public String getContextPath() {
        return this.contextPath;
    }

    public String[] getLocations() {
        return this.locations;
    }

    public WebApplicationContext getWebContext() {
        return this.webContext;
    }

    public ServletContext getServletContext() {
        return this.servletContext;
    }

}
