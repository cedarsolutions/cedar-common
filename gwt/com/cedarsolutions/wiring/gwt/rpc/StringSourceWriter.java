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
package com.cedarsolutions.wiring.gwt.rpc;

import com.google.gwt.core.ext.TreeLogger;
import com.google.gwt.user.rebind.SourceWriter;

/**
 * Source writer that lets us easily get back a string of what was written.
 *
 * <p>
 * This implementation was mostly copied from GWT's ClassSourceFileComposer
 * class.  The functionality I need is a lot simpler than that class offers,
 * (like, I don't need the class declaration, etc.) but there isn't a way
 * to work around that without copying the code.
 * </p>
 *
 * @author Kenneth J. Pronovici <pronovic@ieee.org>
 */
public class StringSourceWriter implements SourceWriter {

    private static final String STAR_COMMENT_LINE = " * ";

    private StringBuffer buffer;
    private boolean atStart;
    private String commentIndicator;
    private boolean inComment;
    private int indent;

    public StringSourceWriter() {
        this.buffer = new StringBuffer();
    }

    @Override
    public String toString() {
        return this.buffer.toString();
    }

    @Override
    public void beginJavaDocComment() {
        println("\n/**");
        inComment = true;
        commentIndicator = STAR_COMMENT_LINE;
    }

    @Override
    public void commit(TreeLogger logger) {
        outdent();
        println("}");
    }

    @Override
    public void endJavaDocComment() {
        inComment = false;
        println("\n */");
    }

    @Override
    public void indent() {
        ++indent;
    }

    @Override
    public void indentln(String s) {
        indent();
        println(s);
        outdent();
    }

    @Override
    public void indentln(String s, Object... args) {
        indentln(String.format(s, args));
    }

    @Override
    public void outdent() {
        --indent;
    }

    @Override
    public void print(String s) {
        // If we just printed a newline, print an indent.
        //
        if (atStart) {
            for (int j = 0; j < indent; ++j) {
                buffer.append("  ");
            }
            if (inComment) {
                buffer.append(commentIndicator);
            }
            atStart = false;
        }
        // Now print up to the end of the string or the next newline.
        //
        String rest = null;
        int i = s.indexOf("\n");
        if (i > -1 && i < s.length() - 1) {
            rest = s.substring(i + 1);
            s = s.substring(0, i + 1);
        }
        buffer.append(s);
        // If rest is non-null, then s ended with a newline and we recurse.
        //
        if (rest != null) {
            atStart = true;
            print(rest);
        }
    }

    @Override
    public void print(String s, Object... args) {
        print(String.format(s, args));
    }

    @Override
    public void println() {
        print("\n");
        atStart = true;
    }

    @Override
    public void println(String s) {
        print(s + "\n");
        atStart = true;
    }

    @Override
    public void println(String s, Object... args) {
        println(String.format(s, args));
    }

}
