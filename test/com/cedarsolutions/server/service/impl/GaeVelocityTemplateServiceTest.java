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
package com.cedarsolutions.server.service.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.RETURNS_DEEP_STUBS;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;
import org.springframework.core.io.Resource;

import com.cedarsolutions.exception.NotConfiguredException;
import com.cedarsolutions.util.gae.GaeVelocityUtils;

/**
 * Unit tests for GaeVelocityTemplateService.
 * @author Kenneth J. Pronovici <pronovic@ieee.org>
 */
public class GaeVelocityTemplateServiceTest {

    /** Directory where templates are stored. */
    private static final String TEMPLATE_DIR = "test/com/cedarsolutions/server/service/impl/templates";

    /** Test constructor, getters and setters. */
    @Test public void testConstructor() {
        GaeVelocityTemplateService service = new GaeVelocityTemplateService();
        assertNull(service.getGaeVelocityUtils());
        assertNull(service.getTemplateDirResource());

        GaeVelocityUtils gaeVelocityUtils = mock(GaeVelocityUtils.class);
        service.setGaeVelocityUtils(gaeVelocityUtils);
        assertSame(gaeVelocityUtils, service.getGaeVelocityUtils());

        Resource templateDirResource = mock(Resource.class);
        service.setTemplateDirResource(templateDirResource);
        assertSame(templateDirResource, service.getTemplateDirResource());
    }

    /** Test the afterPropertiesSet() method. */
    @Test public void testAfterPropertiesSet() throws Exception {
        GaeVelocityTemplateService service = new GaeVelocityTemplateService();
        GaeVelocityUtils gaeVelocityUtils = mock(GaeVelocityUtils.class);
        Resource templateDirResource = mock(Resource.class, RETURNS_DEEP_STUBS);   // so call1().call2().call3() works

        try {
            service.setGaeVelocityUtils(null);
            service.setTemplateDirResource(templateDirResource);
            service.afterPropertiesSet();
            fail("Expected NotConfiguredException");
        } catch (NotConfiguredException e) { }

        try {
            service.setGaeVelocityUtils(gaeVelocityUtils);
            service.setTemplateDirResource(null);
            service.afterPropertiesSet();
            fail("Expected NotConfiguredException");
        } catch (NotConfiguredException e) { }

        try {
            when(templateDirResource.exists()).thenReturn(false);
            service.setGaeVelocityUtils(gaeVelocityUtils);
            service.setTemplateDirResource(templateDirResource);
            service.afterPropertiesSet();
        } catch (NotConfiguredException e) { }

        when(templateDirResource.exists()).thenReturn(true);
        when(templateDirResource.getFile().getPath()).thenReturn("path");
        service.setGaeVelocityUtils(gaeVelocityUtils);
        service.setTemplateDirResource(templateDirResource);
        service.afterPropertiesSet();
        assertEquals("path", service.getTemplateDir());
    }

    /** Test renderTemplate(). */
    @Test public void testRenderTemplate() throws Exception {
        GaeVelocityTemplateService service = createService();
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("name", "Ken");
        String result = service.renderTemplate("testing", "test", "test.vm", map);
        assertEquals("This is a test for Ken.", result);
    }

    /** Create a service instance properly stubbed for testing. */
    private static GaeVelocityTemplateService createService() throws Exception {
        GaeVelocityTemplateService service = new GaeVelocityTemplateService();
        GaeVelocityUtils gaeVelocityUtils = GaeVelocityUtils.getInstance();
        Resource templateDirResource = mock(Resource.class, RETURNS_DEEP_STUBS);   // so call1().call2().call3() works

        when(templateDirResource.exists()).thenReturn(true);
        when(templateDirResource.getFile().getPath()).thenReturn(TEMPLATE_DIR);

        service.setGaeVelocityUtils(gaeVelocityUtils);
        service.setTemplateDirResource(templateDirResource);
        service.afterPropertiesSet();

        return service;
    }
}
