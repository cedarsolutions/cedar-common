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
package com.cedarsolutions.junit.util;

import static com.cedarsolutions.junit.util.Assertions.assertAfter;
import static com.cedarsolutions.junit.util.Assertions.assertAfterStrict;
import static com.cedarsolutions.junit.util.Assertions.assertContainsMessage;
import static com.cedarsolutions.junit.util.Assertions.assertIsAlphabetic;
import static com.cedarsolutions.junit.util.Assertions.assertIsAlphanumeric;
import static com.cedarsolutions.junit.util.Assertions.assertIsNumeric;
import static com.cedarsolutions.junit.util.Assertions.assertIteratorSize;
import static com.cedarsolutions.junit.util.Assertions.assertOnlyMessage;
import static com.cedarsolutions.junit.util.Assertions.assertSummary;
import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Test;

import com.cedarsolutions.exception.InvalidDataException;
import com.cedarsolutions.shared.domain.LocalizableMessage;
import com.cedarsolutions.shared.domain.ValidationErrors;
import com.cedarsolutions.util.DateUtils;

/**
 * Spot-checks for Assertions.
 * @author Kenneth J. Pronovici <pronovic@ieee.org>
 */
public class AssertionsTest {

    /** Test assertIsAlphabetic(). */
    @Test public void testAssertIsAlphabetic() {
        assertIsAlphabetic("a");
        assertIsAlphabetic("ab");
        assertIsAlphabetic("abc");

        try {
            assertIsAlphabetic(null);
            fail("Expected AssertionError");
        } catch (AssertionError e) { }

        try {
            assertIsAlphabetic("");
            fail("Expected AssertionError");
        } catch (AssertionError e) { }

        try {
            assertIsAlphabetic("ab1");
            fail("Expected AssertionError");
        } catch (AssertionError e) { }

        try {
            assertIsAlphabetic("ab-");
            fail("Expected AssertionError");
        } catch (AssertionError e) { }

        try {
            assertIsAlphabetic("1");
            fail("Expected AssertionError");
        } catch (AssertionError e) { }
    }

    /** Test assertIsNumeric(). */
    @Test public void testAssertIsNumeric() {
        assertIsNumeric("1");
        assertIsNumeric("12");
        assertIsNumeric("123");

        try {
            assertIsNumeric(null);
            fail("Expected AssertionError");
        } catch (AssertionError e) { }

        try {
            assertIsNumeric("");
            fail("Expected AssertionError");
        } catch (AssertionError e) { }

        try {
            assertIsNumeric("12c");
            fail("Expected AssertionError");
        } catch (AssertionError e) { }

        try {
            assertIsNumeric("12-");
            fail("Expected AssertionError");
        } catch (AssertionError e) { }

        try {
            assertIsNumeric("a");
            fail("Expected AssertionError");
        } catch (AssertionError e) { }
    }

    /** Test assertIsAlphanumeric().  */
    @Test public void testAssertIsAlphanumeric() {
        assertIsAlphanumeric("a");
        assertIsAlphanumeric("ab");
        assertIsAlphanumeric("abc");
        assertIsAlphanumeric("1");
        assertIsAlphanumeric("12");
        assertIsAlphanumeric("123");
        assertIsAlphanumeric("12c");

        try {
            assertIsAlphanumeric(null);
            fail("Expected AssertionError");
        } catch (AssertionError e) { }

        try {
            assertIsAlphanumeric("");
            fail("Expected AssertionError");
        } catch (AssertionError e) { }

        try {
            assertIsAlphanumeric("12-");
            fail("Expected AssertionError");
        } catch (AssertionError e) { }
    }

    /** Test assertAfter(). */
    @Test public void testAssertAfter() {
        Date date1 = DateUtils.createDate(2010, 12, 14, 16, 2, 48, 993);
        Date date2 = DateUtils.createDate(2010, 12, 14, 16, 2, 48, 994);
        Date date3 = DateUtils.createDate(2010, 12, 14, 16, 2, 48, 995);

        assertAfter(date1, date1);
        assertAfter(date1, date2);
        assertAfter(date1, date3);

        try {
            assertAfter(date2, date1);
            fail("Expected AssertionError");
        } catch (AssertionError e) { }

        assertAfter(date2, date2);
        assertAfter(date2, date3);

        try {
            assertAfter(date3, date1);
            fail("Expected AssertionError");
        } catch (AssertionError e) { }

        try {
            assertAfter(date3, date2);
            fail("Expected AssertionError");
        } catch (AssertionError e) { }

        assertAfter(date3, date3);
    }

    /** Test assertAfterStrict(). */
    @Test public void testAssertAfterStrict() {
        Date date1 = DateUtils.createDate(2010, 12, 14, 16, 2, 48, 993);
        Date date2 = DateUtils.createDate(2010, 12, 14, 16, 2, 48, 994);
        Date date3 = DateUtils.createDate(2010, 12, 14, 16, 2, 48, 995);

        try {
            assertAfterStrict(date1, date1);
            fail("Expected AssertionError");
        } catch (AssertionError e) { }

        assertAfterStrict(date1, date2);
        assertAfterStrict(date1, date3);

        try {
            assertAfterStrict(date2, date1);
            fail("Expected AssertionError");
        } catch (AssertionError e) { }

        try {
            assertAfterStrict(date2, date2);
            fail("Expected AssertionError");
        } catch (AssertionError e) { }

        assertAfterStrict(date2, date3);

        try {
            assertAfterStrict(date3, date1);
            fail("Expected AssertionError");
        } catch (AssertionError e) { }

        try {
            assertAfterStrict(date3, date2);
            fail("Expected AssertionError");
        } catch (AssertionError e) { }

        try {
            assertAfterStrict(date3, date3);
            fail("Expected AssertionError");
        } catch (AssertionError e) { }
    }

    /** Test assertIteratorSize(). */
    @Test public void testAssertIteratorSize() {
        List<String> list = new ArrayList<String>();
        list.add("one");
        list.add("two");

        assertIteratorSize(2, list.iterator());

        try {
            assertIteratorSize(0, list.iterator());
            fail("Expected AssertionError");
        } catch (AssertionError e) { }
    }

    /** Test assertSummary(). */
    @Test public void testAssertSummary() {
        ValidationErrors details1 = new ValidationErrors();
        details1.setSummary(new LocalizableMessage("key1", null, null));
        InvalidDataException e1 = new InvalidDataException("message1", details1);

        ValidationErrors details2 = new ValidationErrors();
        details2.setSummary(new LocalizableMessage("key2", "context2", null));
        InvalidDataException e2 = new InvalidDataException("message2", details2);

        assertSummary(e1, "key1");
        assertSummary(e1, "key1", null);
        assertSummary(e2, "key2", "context2");

        try {
            assertSummary(e2, "key2");
            fail("Expected AssertionError");
        } catch (AssertionError e) { }

        try {
            assertSummary(e2, "key2", "blech");
            fail("Expected AssertionError");
        } catch (AssertionError e) { }
    }

    /** Test assertOnlyMessage(). */
    @Test public void testAssertOnlyMessage() {
        ValidationErrors details1 = new ValidationErrors();
        details1.addMessage("key1", null);
        InvalidDataException e1 = new InvalidDataException("message1", details1);

        ValidationErrors details2 = new ValidationErrors();
        details2.addMessage("key2", "context2", null);
        InvalidDataException e2 = new InvalidDataException("message2", details2);

        ValidationErrors details3 = new ValidationErrors();
        details3.addMessage("key3a", "context3a", null);
        details3.addMessage("key3b", null);
        InvalidDataException e3 = new InvalidDataException("message3", details3);

        assertOnlyMessage(e1, "key1");
        assertOnlyMessage(e1, "key1", null);
        assertOnlyMessage(e2, "key2", "context2");

        try {
            assertOnlyMessage(e2, "key2");
            fail("Expected AssertionError");
        } catch (AssertionError e) { }

        try {
            assertOnlyMessage(e3, "key3b");
            fail("Expected AssertionError");
        } catch (AssertionError e) { }

        try {
            assertOnlyMessage(e2, "key2", "blech");
            fail("Expected AssertionError");
        } catch (AssertionError e) { }

        try {
            assertOnlyMessage(e3, "key3a", "context3a");
            fail("Expected AssertionError");
        } catch (AssertionError e) { }
    }

    /** Test assertContainsMessage(). */
    @Test public void testAssertContainsMessage() {
        ValidationErrors details1 = new ValidationErrors();
        details1.addMessage("key1", null);
        InvalidDataException e1 = new InvalidDataException("message1", details1);

        ValidationErrors details2 = new ValidationErrors();
        details2.addMessage("key2", "context2", null);
        InvalidDataException e2 = new InvalidDataException("message2", details2);

        ValidationErrors details3 = new ValidationErrors();
        details3.addMessage("key3a", "context3a", null);
        details3.addMessage("key3b", null);
        InvalidDataException e3 = new InvalidDataException("message3", details3);

        assertContainsMessage(e1, "key1");
        assertContainsMessage(e1, "key1", null);
        assertContainsMessage(e2, "key2", "context2");
        assertContainsMessage(e3, "key3b");
        assertContainsMessage(e3, "key3a", "context3a");

        try {
            assertContainsMessage(e2, "key2");
            fail("Expected AssertionError");
        } catch (AssertionError e) { }

        try {
            assertContainsMessage(e2, "key2", "blech");
            fail("Expected AssertionError");
        } catch (AssertionError e) { }
    }

}
