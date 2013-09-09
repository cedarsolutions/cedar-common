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
package com.cedarsolutions.server.domain;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

/**
 * Unit tests for ServerDomainObject.
 * @author Kenneth J. Pronovici <pronovic@ieee.org>
 */
public class ServerDomainObjectTest {

    /** Spot-check toString(). */
    @Test public void testToString() {
        ServerDomainObjectTestClass obj = new ServerDomainObjectTestClass();
        assertNotNull(obj.toString());

        obj.setValue("value");
        assertNotNull(obj.toString());
    }

    /** Spot-check equals(). */
    @Test public void testEquals() {
        ServerDomainObjectTestClass obj1 = new ServerDomainObjectTestClass("value1");
        ServerDomainObjectTestClass obj2 = new ServerDomainObjectTestClass("value2");
        ServerDomainObjectTestClass obj3 = new ServerDomainObjectTestClass("value1");

        assertFalse(obj1.equals((Integer) null));
        assertFalse(obj1.equals(new Integer(1)));
        assertTrue(obj1.equals(obj1));
        assertFalse(obj1.equals(obj2));
        assertTrue(obj1.equals(obj3));

        assertFalse(obj2.equals((Integer) null));
        assertFalse(obj2.equals(new Integer(1)));
        assertFalse(obj2.equals(obj1));
        assertTrue(obj2.equals(obj2));
        assertFalse(obj2.equals(obj3));

        assertFalse(obj3.equals((Integer) null));
        assertFalse(obj3.equals(new Integer(1)));
        assertTrue(obj3.equals(obj1));
        assertFalse(obj3.equals(obj2));
        assertTrue(obj3.equals(obj3));
    }

    /** Spot-check hashCode(). */
    @Test public void testHashCode() {
        ServerDomainObjectTestClass obj1 = new ServerDomainObjectTestClass("value1");
        ServerDomainObjectTestClass obj2 = new ServerDomainObjectTestClass("value2");
        ServerDomainObjectTestClass obj3 = new ServerDomainObjectTestClass("value1");

        assertTrue(obj1.hashCode() == obj3.hashCode());
        assertFalse(obj2.hashCode() == obj3.hashCode());
    }

    /** Class we can use for testing (has to be public so reflection builders work properly). */
    @SuppressWarnings("serial")
    public class ServerDomainObjectTestClass extends DomainObject {
        private String value;

        public ServerDomainObjectTestClass() {
        }

        public ServerDomainObjectTestClass(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }
    }

}
