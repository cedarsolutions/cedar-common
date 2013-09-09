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
package com.cedarsolutions.util.gae;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Vector;

import org.apache.commons.collections.ExtendedProperties;
import org.apache.velocity.exception.ResourceNotFoundException;
import org.apache.velocity.runtime.resource.Resource;
import org.apache.velocity.runtime.resource.loader.ResourceLoader;

/**
 * Velocity resource loader that works in Google App Engine.
 * @see <a href="http://jvdkamp.wordpress.com/2010/02/12/combining-gae-apache-velocity-and-jquery/">Combining GAE, Apache Velocity and JQuery</a>
 * @author Kenneth J. Pronovici <pronovic@ieee.org>
 */
public class GaeVelocityResourceLoader extends ResourceLoader {

    private Vector<String> paths = null;

    @SuppressWarnings("unchecked")
    @Override
    public void init(ExtendedProperties configuration) {
        paths = configuration.getVector("path");
    }

    @Override
    public long getLastModified(Resource resource) {
        return resource.getLastModified();
    }

    @Override
    public boolean isSourceModified(Resource resource) {
        return false;
    }

    @Override
    public InputStream getResourceStream(String template) throws ResourceNotFoundException {
        for (int i = 0; i < paths.size(); i++) {
            try {
                String path = paths.get(i);
                return new FileInputStream(path + "/" + template);
            } catch (FileNotFoundException e) { }
        }

        throw new ResourceNotFoundException(template);
    }
}
