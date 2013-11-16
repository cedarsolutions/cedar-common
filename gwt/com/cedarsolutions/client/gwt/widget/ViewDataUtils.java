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

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.cedarsolutions.client.gwt.custom.datepicker.DateBox;
import com.cedarsolutions.util.gwt.GwtDateUtils;
import com.cedarsolutions.util.gwt.GwtStringUtils;
import com.google.gwt.user.client.ui.HasValue;
import com.google.gwt.user.client.ui.ValueBoxBase;

/**
 * Data-related utility methods used by views.
 * @author Kenneth J. Pronovici, Cedar Solutions Inc.
 */
public class ViewDataUtils {

    /** Create a DateBox using the standard GwtDateUtils date format. */
    public static DateBox createDateBoxForDate() {
        DateBox dateBox = new DateBox();
        dateBox.setFormat(new DateBox.DefaultFormat(GwtDateUtils.getDateFormat()));
        return dateBox;
    }

    /** Create a DateBox using the standard GwtDateUtils time format. */
    public static DateBox createDateBoxForTime() {
        DateBox dateBox = new DateBox();
        dateBox.setFormat(new DateBox.DefaultFormat(GwtDateUtils.getTimeFormat()));
        return dateBox;
    }

    /** Create a DateBox using the standard GwtDateUtils timestamp format. */
    public static DateBox createDateBoxForTimestamp() {
        DateBox dateBox = new DateBox();
        dateBox.setFormat(new DateBox.DefaultFormat(GwtDateUtils.getTimestampFormat()));
        return dateBox;
    }

    /**
     * Fill an input field based on a value from criteria.
     * @param input  Input to fill in
     * @param value  Value to fill in
     */
    public static <T> void fillInput(HasValue<T> input, T value) {
        input.setValue(value);
    }

    /**
     * Fill an input field based on a criteria list, using the first value in the list.
     * @param input  Input to fill in
     * @param values List of values (the first one will be used)
     */
    public static <T> void fillInput(ValueBoxBase<T> input, List<T> values) {
        if (values == null || values.isEmpty()) {
            input.setValue(null);
        } else {
            input.setValue(values.get(0));
        }
    }

    /**
     * Build list criteria based on an input field.
     * @param input  Input field to get data from
     * @return A single-item list containing the criteria, or null if criteria is empty.
     */
    public static <T> List<T> buildCriteria(ValueBoxBase<T> input) {
        if (GwtStringUtils.isEmpty(input.getText())) {
            return null;
        } else {
            List<T> result = new ArrayList<T>();
            result.add(input.getValue());
            return result;
        }
    }

    /**
     * Build date criteria based on an input field, resetting the time.
     * @param input  Input field to get data from
     * @param time   Time to reset the returned data to
     * @return Date from the input field, with time reset.
     */
    public static Date buildCriteria(DateBox input) {
        return input.getDatePicker().getValue();
    }

    /**
     * Build date criteria based on an input field, resetting the time.
     * @param input  Input field to get data from
     * @param time   Time to reset the returned data to
     * @return Date from the input field, with time reset.
     */
    public static Date buildCriteria(DateBox input, String time) {
        Date result = buildCriteria(input);
        return GwtDateUtils.resetTime(result, time);
    }

}
