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
package com.cedarsolutions.util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;

/**
 * Exception utilities.
 * @author Kenneth J. Pronovici <pronovic@ieee.org>
 */
public class ExceptionUtils {

    /**
     * Generate a stack trace for an exception.
     * @param exception  Exception to generate the stack trace for
     * @return Stack trace as a string.
     */
    public static String generateStackTrace(Throwable exception) {
        String stackTrace = null;

        if (exception != null) {
            OutputStream stream = null;
            PrintWriter writer = null;

            try {
                stream = new ByteArrayOutputStream();
                writer = new PrintWriter(stream);
                exception.printStackTrace(writer);
                writer.flush();
                stream.flush();
                stackTrace = stream.toString();
            } catch (IOException e) {
                // hopefully shouldn't ever happen?
            } finally {
                try {
                    if (writer != null) {
                        writer.close();
                    }
                } catch (Exception e) { }

                try {
                    if (stream != null) {
                        stream.close();
                    }
                } catch (Exception e) { }
            }
        }

        return stackTrace == null ? "null" : stackTrace;
    }
}

