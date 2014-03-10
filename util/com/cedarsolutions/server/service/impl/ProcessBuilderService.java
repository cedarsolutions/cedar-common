/* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 *
 *              C E D A R
 *          S O L U T I O N S       "Software done right."
 *           S O F T W A R E
 *
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 *
 * Copyright (c) 2014 Kenneth J. Pronovici.
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

import java.io.File;

import com.cedarsolutions.exception.CedarRuntimeException;
import com.cedarsolutions.server.service.IProcessBuilderService;

/**
 * Service that wraps Java ProcessBuilder functionality for easier testing.
 * @author Kenneth J. Pronovici <pronovic@ieee.org>
 */
public class ProcessBuilderService extends AbstractService implements IProcessBuilderService {

    /**
     * Start a process, returning a reference to the new process.
     * @param command               Command to execute
     * @param redirectErrorStream   If true, merge stderr and stdout in the output
     * @param workingDirectory      Working directory to start the process within
     * @return The started process.
     */
    @Override
    public Process startProcess(String[] command, boolean redirectErrorStream, String workingDirectory) {
        try {
            ProcessBuilder pb = new ProcessBuilder(command);
            pb.redirectErrorStream(redirectErrorStream);
            pb.directory(new File(workingDirectory));
            return pb.start();
        } catch (Exception e) {
            throw new CedarRuntimeException("Error starting process: " + e.getMessage(), e);
        }
    }

}
