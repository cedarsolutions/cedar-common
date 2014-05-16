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

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import org.apache.commons.codec.binary.Base64;

import com.cedarsolutions.exception.CedarRuntimeException;

/**
 * Utilities for serializing objects back and forth to strings.
 *
 * <p>
 * The serialized objects will be encoded in base64 string format.  This
 * serialization mechanism is likely to be brittle.  However, it will work on
 * any object that implements Serializable, which is not something you can say
 * about either JSON or JAXB.  In general, you're better off using a structured
 * data format (like a JAXB XML binding), rather than relying on this mechanism.
 * </p>
 *
 * @see <a href="http://stackoverflow.com/questions/134492/how-to-serialize-an-object-into-a-string">Stack Overflow</a>
 * @author OscarRyz
 * @author Kenneth J. Pronovici <pronovic@ieee.org>
 */
public class SerializationUtils {

    /** Read the object from Base64 string. */
    @SuppressWarnings("unchecked")
    public static <T> T fromString(String string) {
        try {
            byte[] data = Base64.decodeBase64(string.getBytes("UTF-8"));
            ObjectInputStream ois = new ObjectInputStream(new ByteArrayInputStream(data));
            try {
                return (T) ois.readObject();
            } finally {
                ois.close();
            }
        } catch (Exception e) {
            throw new CedarRuntimeException(e.getMessage(), e);
        }
    }

    /** Write the object to a Base64 string. */
    public static <T> String toString(T object) {
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(baos);
            try {
                oos.writeObject(object);
                return new String(Base64.encodeBase64(baos.toByteArray()));
            } finally {
                oos.close();
            }
        } catch (Exception e) {
            throw new CedarRuntimeException(e.getMessage(), e);
        }
    }

}
