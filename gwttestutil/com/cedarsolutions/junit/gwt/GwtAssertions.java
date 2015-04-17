/* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 *
 *              C E D A R
 *          S O L U T I O N S       "Software done right."
 *           S O F T W A R E
 *
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 *
 * Copyright (c) 2015 Kenneth J. Pronovici.
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
package com.cedarsolutions.junit.gwt;

import static org.junit.Assert.assertTrue;
import static org.springframework.util.ClassUtils.convertClassNameToResourcePath;
import static org.springframework.util.SystemPropertyUtils.resolvePlaceholders;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.core.type.classreading.CachingMetadataReaderFactory;
import org.springframework.core.type.classreading.MetadataReader;
import org.springframework.core.type.classreading.MetadataReaderFactory;

import com.cedarsolutions.exception.CedarRuntimeException;
import com.google.gwt.user.client.rpc.RemoteService;

/**
 * Assertions related to GWT code.
 * @author Kenneth J. Pronovici <pronovic@ieee.org>
 */
public class GwtAssertions {

    /**
     * Assert that all RPC interfaces in a package properly implement RemoteService.
     *
     * <p>
     * All RPC interfaces must extend RemoteService.  However, if you don't do
     * this, the first time you see any error is after the server boots, the
     * first time GIN attempts to instantiate the RPC.  The error messages
     * do not directly lead you to a problem with the interface definition,
     * which can be confusing.  This assertion checks that every known RPC
     * interface within a package extends RemoteService.
     * </p>
     *
     * @param rpcPackage  Name of the Java package to check for RPC interfaces
     */
    @SuppressWarnings("rawtypes")
    public static void assertRpcsImplementRemoteService(String rpcPackage) {
        try {
            for (Class rpcInterface : findRpcInterfaces(rpcPackage)) {
                assertTrue("RPC interface does not implement RemoteService: " + rpcInterface.getCanonicalName(),
                           RemoteService.class.isAssignableFrom(rpcInterface));
            }
        } catch (Exception e) {
            throw new CedarRuntimeException("Error checking RPC interfaces: " + e.getMessage(), e);
        }
    }

    /**
     * Find all RPC interfaces defined in the passed-in packages.
     * @param rpcPackages List of packages to check
     * @return Return all RPC interfaces, excluding Async interfaces.
     * @see <a href="http://stackoverflow.com/questions/1456930">StackOverflow</a>
     */
    @SuppressWarnings("rawtypes")
    public static List<Class> findRpcInterfaces(String ... rpcPackages) throws IOException, ClassNotFoundException {
        List<Class> rpcInterfaces = new ArrayList<Class>();

        for (String rpcPackage : rpcPackages) {
            ResourcePatternResolver resourcePatternResolver = new PathMatchingResourcePatternResolver();
            MetadataReaderFactory metadataReaderFactory = new CachingMetadataReaderFactory(resourcePatternResolver);

            String packageSearchPath = ResourcePatternResolver.CLASSPATH_ALL_URL_PREFIX +
                                       convertClassNameToResourcePath(resolvePlaceholders(rpcPackage)) +
                                       "/I*.class";

            Resource[] resources = resourcePatternResolver.getResources(packageSearchPath);
            for (Resource resource : resources) {
                if (resource.isReadable()) {
                    MetadataReader metadataReader = metadataReaderFactory.getMetadataReader(resource);
                    String className = metadataReader.getClassMetadata().getClassName();
                    if (!className.endsWith("Async")) {
                        rpcInterfaces.add(Class.forName(className));
                    }
                }
            }
        }

        return rpcInterfaces;
    }

}
