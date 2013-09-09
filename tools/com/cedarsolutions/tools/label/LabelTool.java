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

import com.cedarsolutions.exception.CedarRuntimeException;
import com.cedarsolutions.exception.InvalidArgumentsException;
import com.cedarsolutions.util.commandline.CommandLine;
import com.cedarsolutions.util.commandline.CommandLineResult;
import com.cedarsolutions.util.commandline.CommandLineUtils;

/**
 * Cedar Solutions label tool.
 *
 * <p>
 * The label tool understands how to label code in a Mercurial repository.  It
 * can apply a constant label, and also supports variables in the label text.
 * If you want to use variable references, then you need to provide a resource
 * class that can be used to resolve the variable references.  As of this
 * writing, the resource class must extend the GWT <code>ConstantsWithLookup</code>
 * class.  See the Javadoc for <code>VariableResolver</code> for information
 * about the supported variable syntax.
 * </p>
 *
 * @author Kenneth J. Pronovici <pronovic@ieee.org>
 */
public class LabelTool {

    /** Command-line arguments. */
    private LabelToolArguments arguments;

    /**
     * Constructor in terms of command-line arguments.
     *
     * <p>
     * Note: This constructor calls system.exit(2) if command-line arguments
     * are invalid.  It does this to strictly control the output from the
     * program (we want to make sure that the usage is displayed simply and
     * prominently).
     * </p>
     *
     * @param args     Array of command-line arguments, as from main()
     */
    public LabelTool(String[] args) {
        try {
            this.arguments = new LabelToolArguments(args);
        } catch (InvalidArgumentsException e) {
            System.err.println(generateHelp());
            System.exit(2);
        }
    }

    /** Java entry point. */
    public static void main(String[] args) throws Exception {
        new LabelTool(args).internalMain();
    }

    /** Command-line arguments. */
    public LabelToolArguments getArguments() {
        return this.arguments;
    }

    /** Internal implementation of the main routine. */
    public void internalMain() {
        String label = deriveLabel(this.getArguments().getLabel(), this.getArguments().getResourceClass());
        System.out.println("Generated label: " + label);

        String mercurial = this.getArguments().getMercurial();
        for (String repository : this.getArguments().getRepositories()) {
            applyLabel(mercurial, repository, label);
            System.out.println("Completed labeling repository: " + repository);
        }

        System.out.println("Completed labeling all Mercurial repositories.");
    }

    /** Derive the label to use, based on command-line arguments. */
    protected static String deriveLabel(String label, String resourceClass) {
        VariableResolver resolver = new VariableResolver(resourceClass);

        String derived = resolver.expandFormat(label, true);
        derived = derived.replace(" ", "_");  // Mercurial doesn't allow space in a tag name
        derived = derived.replace(":", "");   // Mercurial doesn't allow colon in a tag name

        return derived;
    }

    /** Apply a label to a Mercurial repository. */
    protected static void applyLabel(String mercurial, String repository, String label) {
        CommandLine command = new CommandLine(mercurial);
        command.addArg("tag");
        command.addArg("-f");
        command.addArg(label);

        // It works best to execute the command from within the repository location.
        // Mercurial is not always consistent about behavior if you specify the path to the repo.
        CommandLineResult result = CommandLineUtils.executeCommand(command, repository, true);
        if (result.getExitCode() != 0) {
            throw new CedarRuntimeException("Command failed: " + command.toString());
        }
    }

    /** Generate a command-line help statement. */
    protected static String generateHelp() {
        return "Usage: LabelTool --label label [--resource-class resource-class] --hg hgpath --repo repository [--repo repository]\n" +
               "\n" +
               "   label           Label to apply, optionally including variable references\n" +
               "   resource-class  Fully-qualified name of a resource class used to resolve variables." +
               "   hgpath          Path to the Mercurial executable, including .exe on Windows\n" +
               "   repository      Path to a Mercurial repository to be updated\n" +
               "\n" +
               "The label will be applied to all of the indicated Mercurial repositories.\n" +
               "Optionally, the label can include variable references of the style ${var}\n" +
               "If you want to use variable references, you must also include the name of a\n" +
               "resources class that can be used to resolve the variables. See the Javadoc in\n" +
               "LabelTool for more information about the variable syntax.\n";
    }

}
