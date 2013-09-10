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
package com.cedarsolutions.tools.label;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import org.junit.Test;

import com.cedarsolutions.exception.InvalidArgumentsException;

/**
 * Unit tests for LabelToolArguments.
 * @author Kenneth J. Pronovici <pronovic@ieee.org>
 */
public class LabelToolArgumentsTest {

    /** Test the constructor. */
    @Test public void testConstructor() {
        LabelToolArguments args;

        try {
            args = new LabelToolArguments(null);
            fail("Expected InvalidArgumentsException");
        } catch (InvalidArgumentsException e) { }

        try {
            args = new LabelToolArguments(new String[] {});
            fail("Expected InvalidArgumentsException");
        } catch (InvalidArgumentsException e) { }

        args = new LabelToolArguments(new String[] { "--label", "one",
                                                     "--hg", "two", "--repo", "three", });
        assertEquals("one", args.getLabel());
        assertEquals(null, args.getResourceClass());
        assertEquals("two", args.getMercurial());
        assertEquals(1, args.getRepositories().size());
        assertEquals("three", args.getRepositories().get(0));

        args = new LabelToolArguments(new String[] { "--label", "one", "--resource-class", "two",
                                                     "--hg", "three", "--repo", "four", });
        assertEquals("one", args.getLabel());
        assertEquals("two", args.getResourceClass());
        assertEquals("three", args.getMercurial());
        assertEquals(1, args.getRepositories().size());
        assertEquals("four", args.getRepositories().get(0));

        args = new LabelToolArguments(new String[] { "--label", "one",
                                                     "--hg", "two", "--repo", "three", "--repo", "four", });
        assertEquals("one", args.getLabel());
        assertEquals(null, args.getResourceClass());
        assertEquals("two", args.getMercurial());
        assertEquals(2, args.getRepositories().size());
        assertEquals("three", args.getRepositories().get(0));
        assertEquals("four", args.getRepositories().get(1));

        args = new LabelToolArguments(new String[] { "--label", "one", "--resource-class", "two",
                                                     "--hg", "three", "--repo", "four", "--repo", "five", });
        assertEquals("one", args.getLabel());
        assertEquals("two", args.getResourceClass());
        assertEquals("three", args.getMercurial());
        assertEquals(2, args.getRepositories().size());
        assertEquals("four", args.getRepositories().get(0));
        assertEquals("five", args.getRepositories().get(1));

        try {
            new LabelToolArguments(new String[] { "--hg", "two", "--repo", "three", });
            fail("Expected InvalidArgumentsException");
        } catch (InvalidArgumentsException e) { }

        try {
            new LabelToolArguments(new String[] { "--label", "one", "--repo", "three", });
            fail("Expected InvalidArgumentsException");
        } catch (InvalidArgumentsException e) { }

        try {
            new LabelToolArguments(new String[] { "--label", "one", "--hg", "two", });
            fail("Expected InvalidArgumentsException");
        } catch (InvalidArgumentsException e) { }
    }

}
