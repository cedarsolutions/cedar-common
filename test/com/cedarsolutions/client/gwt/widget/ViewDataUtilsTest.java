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
package com.cedarsolutions.client.gwt.widget;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import com.cedarsolutions.junit.gwt.StubbedClientTestCase;
import com.google.gwt.user.client.ui.HasValue;
import com.google.gwt.user.client.ui.ValueBoxBase;

/**
 * Unit tests for ViewDataUtils.
 * @author Kenneth J. Pronovici <pronovic@ieee.org>
 */
@SuppressWarnings("unchecked")
public class ViewDataUtilsTest extends StubbedClientTestCase {

    /** Test fillInput() for HasValue, single item. */
    @Test public void testFillInputHasValueSingle() {
        HasValue<String> input = mock(HasValue.class);
        ViewDataUtils.fillInput(input, "hello");
        verify(input).setValue("hello");
    }

    /** Test fillInput() for HasValue, list (null). */
    @Test public void testFillInputHasValueListNull() {
        HasValue<String> input = mock(HasValue.class);
        List<String> list = null;
        ViewDataUtils.fillInput(input, list);
        verify(input).setValue(null);
    }

    /** Test fillInput() for HasValue, list (empty). */
    @Test public void testFillInputHasValueListEmpty() {
        HasValue<String> input = mock(HasValue.class);
        List<String> list = new ArrayList<String>();
        ViewDataUtils.fillInput(input, list);
        verify(input).setValue(null);
    }

    /** Test fillInput() for HasValue, list (not empty). */
    @Test public void testFillInputHasValueListNotEmpty() {
        HasValue<String> input = mock(HasValue.class);
        List<String> list = new ArrayList<String>();
        list.add("hello");
        list.add("goodbye");
        ViewDataUtils.fillInput(input, list);
        verify(input).setValue("hello");
    }

    /** Test fillInput() for dropdown, single item. */
    @Test public void testFillInputDropdownSingle() {
        AbstractDropdownList<String> input = mock(AbstractDropdownList.class);
        ViewDataUtils.fillInput(input, "hello");
        verify(input).setSelectedValue("hello");
    }

    /** Test fillInput() for dropdown, list (null). */
    @Test public void testFillInputDropdownListNull() {
        AbstractDropdownList<String> input = mock(AbstractDropdownList.class);
        List<String> list = null;
        ViewDataUtils.fillInput(input, list);
        verify(input).setSelectedValue(null);
    }

    /** Test fillInput() for dropdown, list (empty). */
    @Test public void testFillInputDropdownListEmpty() {
        AbstractDropdownList<String> input = mock(AbstractDropdownList.class);
        List<String> list = new ArrayList<String>();
        ViewDataUtils.fillInput(input, list);
        verify(input).setSelectedValue(null);
    }

    /** Test fillInput() for dropdown, list (not empty). */
    @Test public void testFillInputDropdownListNotEmpty() {
        AbstractDropdownList<String> input = mock(AbstractDropdownList.class);
        List<String> list = new ArrayList<String>();
        list.add("hello");
        list.add("goodbye");
        ViewDataUtils.fillInput(input, list);
        verify(input).setSelectedValue("hello");
    }

    /** Test fillInput() for ValueBoxBase, single item. */
    @Test public void testFillInputValueBoxSingle() {
        ValueBoxBase<String> input = mock(ValueBoxBase.class);
        ViewDataUtils.fillInput(input, "hello");
        verify(input).setValue("hello");
    }

    /** Test fillInput() for ValueBoxBase, list (null). */
    @Test public void testFillInputValueBoxListNull() {
        ValueBoxBase<String> input = mock(ValueBoxBase.class);
        List<String> list = null;
        ViewDataUtils.fillInput(input, list);
        verify(input).setValue(null);
    }

    /** Test fillInput() for ValueBoxBase, list (empty). */
    @Test public void testFillInputValueBoxListEmpty() {
        ValueBoxBase<String> input = mock(ValueBoxBase.class);
        List<String> list = new ArrayList<String>();
        ViewDataUtils.fillInput(input, list);
        verify(input).setValue(null);
    }

    /** Test fillInput() for ValueBoxBase, list (not empty). */
    @Test public void testFillInputValueBoxListNotEmpty() {
        ValueBoxBase<String> input = mock(ValueBoxBase.class);
        List<String> list = new ArrayList<String>();
        list.add("hello");
        list.add("goodbye");
        ViewDataUtils.fillInput(input, list);
        verify(input).setValue("hello");
    }

    /** Test getCriteriaList() for ValueBoxBase. */
    @Test public void testGetCriteriaListValueBox() {
        ValueBoxBase<String> input = mock(ValueBoxBase.class);

        when(input.getValue()).thenReturn(null);
        when(input.getText()).thenReturn("");
        assertEquals(null, ViewDataUtils.getCriteriaList(input));

        when(input.getValue()).thenReturn(null);
        when(input.getText()).thenReturn("");
        assertEquals(null, ViewDataUtils.getCriteriaList(input));

        when(input.getValue()).thenReturn("");
        when(input.getText()).thenReturn(null);
        assertEquals(null, ViewDataUtils.getCriteriaList(input));

        when(input.getValue()).thenReturn("hello");
        when(input.getText()).thenReturn(null);
        assertEquals(null, ViewDataUtils.getCriteriaList(input));

        when(input.getValue()).thenReturn("hello");
        when(input.getText()).thenReturn("");
        assertEquals(null, ViewDataUtils.getCriteriaList(input));

        List<String> expected = new ArrayList<String>();
        expected.add("hello");
        when(input.getValue()).thenReturn("hello");
        when(input.getText()).thenReturn("text");
        assertEquals(expected, ViewDataUtils.getCriteriaList(input));
    }

    /** Test getCriteria() for ValueBoxBase. */
    @Test public void testGetCriteriaValueBox() {
        ValueBoxBase<String> input = mock(ValueBoxBase.class);

        when(input.getValue()).thenReturn(null);
        when(input.getText()).thenReturn("");
        assertEquals(null, ViewDataUtils.getCriteria(input));

        when(input.getValue()).thenReturn(null);
        when(input.getText()).thenReturn("");
        assertEquals(null, ViewDataUtils.getCriteria(input));

        when(input.getValue()).thenReturn("");
        when(input.getText()).thenReturn(null);
        assertEquals(null, ViewDataUtils.getCriteria(input));

        when(input.getValue()).thenReturn("hello");
        when(input.getText()).thenReturn(null);
        assertEquals(null, ViewDataUtils.getCriteria(input));

        when(input.getValue()).thenReturn("hello");
        when(input.getText()).thenReturn("");
        assertEquals(null, ViewDataUtils.getCriteria(input));

        when(input.getValue()).thenReturn("hello");
        when(input.getText()).thenReturn("text");
        assertEquals("hello", ViewDataUtils.getCriteria(input));
    }

    /** Test getCriteriaList() for dropdown. */
    @Test public void testGetCriteriaListDropdown() {
        AbstractDropdownList<String> input = mock(AbstractDropdownList.class);

        when(input.getSelectedValue()).thenReturn(null);
        assertEquals(null, ViewDataUtils.getCriteriaList(input));

        List<String> expected = new ArrayList<String>();
        expected.add("");  // because we can't tell whether the empty string is a valid value
        when(input.getSelectedValue()).thenReturn("");
        assertEquals(expected, ViewDataUtils.getCriteriaList(input));

        expected = new ArrayList<String>();
        expected.add("hello");
        when(input.getSelectedValue()).thenReturn("hello");
        assertEquals(expected, ViewDataUtils.getCriteriaList(input));
    }

    /** Test getCriteria() for dropdown. */
    @Test public void testGetCriteriaDropdown() {
        AbstractDropdownList<String> input = mock(AbstractDropdownList.class);

        when(input.getSelectedValue()).thenReturn(null);
        assertEquals(null, ViewDataUtils.getCriteria(input));

        when(input.getSelectedValue()).thenReturn("");
        assertEquals("", ViewDataUtils.getCriteria(input));  // because we can't tell whether empty string is a valid value

        when(input.getSelectedValue()).thenReturn("hello");
        assertEquals("hello", ViewDataUtils.getCriteria(input));
    }

}
