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

import static com.cedarsolutions.util.gwt.GwtDateUtils.createDate;
import static com.cedarsolutions.util.gwt.GwtDateUtils.formatDate;
import static com.cedarsolutions.util.gwt.GwtDateUtils.formatTime;
import static com.cedarsolutions.util.gwt.GwtDateUtils.formatTimestamp;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.cedarsolutions.client.gwt.custom.datepicker.DateBox;
import com.cedarsolutions.client.gwt.junit.ClientTestCase;
import com.cedarsolutions.util.gwt.GwtDateUtils;

/**
 * Client-side unit tests for ViewDataUtils.
 * @author Kenneth J. Pronovici <pronovic@ieee.org>
 */
public class ViewDataUtilsClientTest extends ClientTestCase {

    /** Test createDateBoxForDate(). */
    public void testCreateDateBoxForDate() {
        Date date = createDate(2013, 1, 2, 3, 4, 5, 6);
        DateBox dateBox = ViewDataUtils.createDateBoxForDate();
        assertNotNull(dateBox);
        assertEquals(formatDate(date), dateBox.getFormat().format(dateBox, date));
    }

    /** Test createDateBoxForTime(). */
    public void testCreateDateBoxForTime() {
        Date date = createDate(2013, 1, 2, 3, 4, 5, 6);
        DateBox dateBox = ViewDataUtils.createDateBoxForTime();
        assertNotNull(dateBox);
        assertEquals(formatTime(date), dateBox.getFormat().format(dateBox, date));
    }

    /** Test createDateBoxForTimestamp(). */
    public void testCreateDateBoxForTimestamp() {
        Date date = createDate(2013, 1, 2, 3, 4, 5, 6);
        DateBox dateBox = ViewDataUtils.createDateBoxForTimestamp();
        assertNotNull(dateBox);
        assertEquals(formatTimestamp(date), dateBox.getFormat().format(dateBox, date));
    }

    /** Test getCriteriaList() for DateBox. */
    public void testGetCriteriaListDateBox() {
        Date date = GwtDateUtils.createDate(2011, 11, 15, 19, 32, 19, 224);

        DateBox input = new DateBox();
        input.setValue(date);

        List<Date> expected = new ArrayList<Date>();
        expected.add(date);
        assertEquals(expected, ViewDataUtils.getCriteriaList(input));
    }

    /** Test getCriteria() for DateBox. */
    public void testGetCriteriaDateBox() {
        Date date = GwtDateUtils.createDate(2011, 11, 15, 19, 32, 19, 224);

        DateBox input = new DateBox();
        input.setValue(date);

        assertEquals(date, ViewDataUtils.getCriteria(input));
    }

    /** Test getDateCriteria(). */
    public void testGetDateCriteria() {
        Date date = GwtDateUtils.createDate(2011, 11, 15, 19, 32, 19, 224);
        Date expected = GwtDateUtils.createDate(2011, 11, 15, 19, 18, 22, 1);

        DateBox input = new DateBox();
        input.setValue(date);

        assertEquals(expected, ViewDataUtils.getDateCriteria(input, "19:18:22,001"));
    }

    /** Test getStartDateCriteria(). */
    public void testGetStartDateCriteria() {
        Date date = GwtDateUtils.createDate(2011, 11, 15, 19, 32, 19, 224);
        Date expected = GwtDateUtils.createDate(2011, 11, 15, 0, 0, 0, 0);

        DateBox input = new DateBox();
        input.setValue(date);

        assertEquals(expected, ViewDataUtils.getStartDateCriteria(input));
    }

    /** Test getEndDateCriteria(). */
    public void testGetEndDateCriteria() {
        Date date = GwtDateUtils.createDate(2011, 11, 15, 19, 32, 19, 224);
        Date expected = GwtDateUtils.createDate(2011, 11, 15, 23, 59, 59, 999);

        DateBox input = new DateBox();
        input.setValue(date);

        assertEquals(expected, ViewDataUtils.getEndDateCriteria(input));
    }

}
