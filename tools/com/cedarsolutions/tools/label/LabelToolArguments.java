/* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 *
 *              C E D A R
 *          S O L U T I O N S       "Software done right."
 *           S O F T W A R E
 *
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 *
 * Copyright (c) 2012 Kenneth J. Pronovici.
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
package com.cedarsolutions.tools.label;

import java.util.List;

import com.cedarsolutions.exception.InvalidArgumentsException;
import com.cedarsolutions.util.commandline.CommandLineArguments;

/**
 * Command-line arguments for LabelTool.
 * @author Kenneth J. Pronovici <pronovic@ieee.org>
 */
public class LabelToolArguments extends CommandLineArguments {

    /** Label to apply, optionally including variable references. */
    private String label;

    /** Fully-qualified name of a resource class from which to resolve variable references. */
    private String resourceClass;

    /** Path to the Mercurial (hg) executable. */
    private String mercurial;

    /** Mercurial repositories to be labeled. */
    private List<String> repositories;

    /**
     * Constructor in terms of command-line arguments.
     * @param args Array of command-line arguments, as from main()
     */
    public LabelToolArguments(String[] args) {
        super(args);
    }

    /**
     * Parse command-line arguments.
     * @param args Array of command-line arguments, as from main()
     * @throws InvalidArgumentsException If the command-line arguments are invalid.
     */
    @Override
    protected void parseArguments(String[] args) throws InvalidArgumentsException {
        this.label = parseRequiredParameter(args, "--label");
        this.resourceClass = parseOptionalParameter(args, "--resource-class");
        this.mercurial = parseRequiredParameter(args, "--hg");
        this.repositories = parseRequiredParameterList(args, "--repo");
    }

    /** Label to apply, optionally including variable references. */
    public String getLabel() {
        return this.label;
    }

    /** Fully-qualified name of a resource class from which to resolve variable references. */
    public String getResourceClass() {
        return this.resourceClass;
    }

    /** Path to the Mercurial (hg) executable. */
    public String getMercurial() {
        return this.mercurial;
    }

    /** Mercurial repositories to be modified. */
    public List<String> getRepositories() {
        return this.repositories;
    }

}
