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
    public static <T> void fillInput(HasValue<T> input, List<T> values) {
        if (values == null || values.isEmpty()) {
            input.setValue(null);
        } else {
            input.setValue(values.get(0));
        }
    }

    /**
     * Fill an input field based on a value from criteria.
     * @param input  Input to fill in
     * @param value  Value to fill in
     */
    public static <T> void fillInput(AbstractDropdownList<T> input, T value) {
        input.setSelectedValue(value);
    }

    /**
     * Fill an input field based on a criteria list, using the first value in the list.
     * @param input  Input to fill in
     * @param values List of values (the first one will be used)
     */
    public static <T> void fillInput(AbstractDropdownList<T> input, List<T> values) {
        if (values == null || values.isEmpty()) {
            input.setSelectedValue(null);
        } else {
            input.setSelectedValue(values.get(0));
        }
    }

    /**
     * Fill an input field based on a criteria list.
     * @param input  Input to fill in
     * @param value  Value to fill in
     */
    public static <T> void fillInput(ValueBoxBase<T> input, T value) {
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
     * Get a list of criteria based on an input field.
     * @param input  Input field to get data from
     * @return A single-item list containing the criteria, or null if criteria is empty.
     */
    public static <T> List<T> getCriteriaList(ValueBoxBase<T> input) {
        if (GwtStringUtils.isEmpty(input.getText())) {
            return null;
        } else {
            List<T> result = new ArrayList<T>();
            result.add(input.getValue());
            return result;
        }
    }

    /**
     * Get criteria based on an input field.
     * @param input  Input field to get data from
     * @return Value from the input field.
     */
    public static <T> T getCriteria(ValueBoxBase<T> input) {
        if (GwtStringUtils.isEmpty(input.getText())) {
            return null;
        } else {
            return input.getValue();
        }
    }

    /**
     * Get a list of criteria based on an input field.
     * @param input  Input field to get data from
     * @return A single-item list containing the criteria, or null if criteria is empty.
     */
    public static <T> List<T> getCriteriaList(AbstractDropdownList<T> input) {
        if (input.getSelectedValue() == null) {
            return null;
        } else {
            List<T> result = new ArrayList<T>();
            result.add(input.getSelectedValue());
            return result;
        }
    }

    /**
     * Get criteria based on an input field.
     * @param input  Input field to get data from
     * @return Value from the input field.
     */
    public static <T> T getCriteria(AbstractDropdownList<T> input) {
        return input.getSelectedValue();
    }

    /**
     * Build a list of criteria based on an input field.
     * @param input  Input field to get data from
     * @param time   Time to reset the returned data to
     * @return Date from the input field, with time reset.
     */
    public static List<Date> getCriteriaList(DateBox input) {
        List<Date> result = new ArrayList<Date>();
        result.add(input.getDatePicker().getValue());
        return result;
    }

    /**
     * Build criteria based on an input field.
     * @param input  Input field to get data from
     * @param time   Time to reset the returned data to
     * @return Date from the input field, with time reset.
     */
    public static Date getCriteria(DateBox input) {
        return input.getDatePicker().getValue();
    }

    /**
     * Build date criteria based on an input field, resetting the time.
     * @param input  Input field to get data from
     * @param time   Time to reset the returned data to
     * @return Date from the input field, with time reset.
     */
    public static Date getDateCriteria(DateBox input, String time) {
        Date result = getCriteria(input);
        return GwtDateUtils.resetTime(result, time);
    }

}
