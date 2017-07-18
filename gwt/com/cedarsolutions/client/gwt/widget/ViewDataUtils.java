/* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 *
 *              C E D A R
 *          S O L U T I O N S       "Software done right."
 *           S O F T W A R E
 *
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 *
 * Copyright (c) 2013-2015 Kenneth J. Pronovici.
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

import static com.cedarsolutions.util.gwt.GwtStringUtils.substring;
import static com.cedarsolutions.util.gwt.GwtStringUtils.trim;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.cedarsolutions.client.gwt.custom.datepicker.DateBox;
import com.cedarsolutions.util.gwt.GwtDateUtils;
import com.cedarsolutions.util.gwt.GwtStringUtils;
import com.google.gwt.user.client.ui.HasValue;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.ValueBoxBase;

/**
 * Data-related utility methods used by views.
 * @author Kenneth J. Pronovici, Cedar Solutions Inc.
 */
public class ViewDataUtils {

    /** The timestamp used for creating start dates. */
    public static final String START_TIMESTAMP = "00:00:00,000";

    /** The timestamp used for creating end dates. */
    public static final String END_TIMESTAMP = "23:59:59,999";

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
     * Fill a label with a value.
     * @param label  Label to fill in
     * @param value  Value to fill in
     */
    public static <T> void fillLabel(Label label, T value) {
        if (value == null) {
            label.setText("");
        } else {
            label.setText(String.valueOf(value));
        }
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
        input.setSelectedObjectValue(value);
    }

    /**
     * Fill an input field based on a criteria list, using the first value in the list.
     * @param input  Input to fill in
     * @param values List of values (the first one will be used)
     */
    public static <T> void fillInput(AbstractDropdownList<T> input, List<T> values) {
        if (values == null || values.isEmpty()) {
            input.setSelectedObjectValue(null);
        } else {
            input.setSelectedObjectValue(values.get(0));
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
     * Fill an input field based on a criteria list, filling the field with comma-separated values.
     * @param input     Input to fill in
     * @param values    List of values (the first one will be used)
     */
    public static void fillInputMultiple(TextBox input, List<String> values) {
        fillInputMultiple(input, values, ", ");
    }

    /**
     * Fill an input field based on a criteria list, filling the field with multiple separated values.
     * @param input     Input to fill in
     * @param values    List of values (the first one will be used)
     * @param separator String separator to use between values
     */
    public static void fillInputMultiple(TextBox input, List<String> values, String separator) {
        if (values == null || values.isEmpty()) {
            input.setValue(null);
        } else {
            StringBuilder buffer = new StringBuilder();

            buffer.append(values.get(0));
            for (int i = 1; i < values.size(); i++) {
                buffer.append(separator);
                buffer.append(values.get(i));
            }

            input.setValue(buffer.toString());
        }
    }

    /**
     * Get a list of criteria based on an input field.
     * @param input  Input field to get data from
     * @return A single-item list containing the criteria, or null if criteria is empty.
     */
    public static <T> List<T> getCriteriaList(ValueBoxBase<T> input) {
        if (input.getValue() == null || GwtStringUtils.isEmpty(input.getText())) {
            return null;
        } else {
            List<T> result = new ArrayList<T>();
            result.add(input.getValue());
            return result;
        }
    }

    /**
     * Get a list of criteria based on an input field, assuming the input contains comma-separated values.
     * Clearly, this only makes sense if your input data doesn't normally contain comma characters.
     * @param input  Input field to get data from
     * @return A single-item list containing the criteria, or null if criteria is empty.
     */
    public static List<String> getCriteriaListMultiple(TextBox input) {
        return getCriteriaListMultiple(input, ", *");
    }

    /**
     * Get a list of criteria based on an input field, assuming the input contains a list of separated values.
     * When you specify your regular expression, don't forget to take into account whitespace.
     * @param input  Input field to get data from
     * @param regex  Regex that should be used to split the the values into a list
     * @return A single-item list containing the criteria, or null if criteria is empty.
     */
    public static List<String> getCriteriaListMultiple(ValueBoxBase<String> input, String regex) {
        if (input.getValue() == null || GwtStringUtils.isEmpty(input.getText())) {
            return null;
        } else {
            List<String> result = new ArrayList<String>();

            for (String value : input.getValue().split(regex)) {
                if (!GwtStringUtils.isEmpty(value)) {
                    result.add(value);
                }
            }

            return result;
        }
    }

    /**
     * Get criteria based on an input field.
     * @param input  Input field to get data from
     * @return Value from the input field.
     */
    public static <T> T getCriteria(ValueBoxBase<T> input) {
        if (input.getValue() == null || GwtStringUtils.isEmpty(input.getText())) {
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
        if (input.getSelectedObjectValue() == null) {
            return null;
        } else {
            List<T> result = new ArrayList<T>();
            result.add(input.getSelectedObjectValue());
            return result;
        }
    }

    /**
     * Get criteria based on an input field.
     * @param input  Input field to get data from
     * @return Value from the input field.
     */
    public static <T> T getCriteria(AbstractDropdownList<T> input) {
        return input.getSelectedObjectValue();
    }

    /**
     * Build a list of criteria based on an input field.
     * @param input  Input field to get data from
     * @return Date from the input field, with time reset.
     */
    public static List<Date> getCriteriaList(DateBox input) {
        List<Date> result = new ArrayList<Date>();
        result.add(input.getDatePicker().getValue());
        return result;
    }

    /**
     * Build criteria based on an input field.
     * @param input     Input field to get data from
     * @return Date from the input field, with time reset.
     */
    public static Date getCriteria(DateBox input) {
        return input.getDatePicker().getValue();
    }

    /**
     * Build date criteria based on an input field, resetting the time.
     * @param input       Input field to get data from
     * @param timestamp   Timestamp to reset the returned data to, like "23:59:59,999"
     * @return Date from the input field, with time reset.
     */
    public static Date getDateCriteria(DateBox input, String timestamp) {
        Date result = getCriteria(input);
        return GwtDateUtils.resetTimestamp(result, timestamp);
    }

    /**
     * Build date criteria based on an input field, treating as a start date (starts at 00:00:00,000).
     * @param input  Input field to get data from
     * @return Date from the input field, with time reset so it's a sensible start date.
     */
    public static Date getStartDateCriteria(DateBox input) {
        return getDateCriteria(input, START_TIMESTAMP);
    }

    /**
     * Build date criteria based on an input field, treating as an end date (ends at 23:59:59,999).
     * @param input  Input field to get data from
     * @return Date from the input field, with time reset so it's a sensible end date.
     */
    public static Date getEndDateCriteria(DateBox input) {
        return getDateCriteria(input, END_TIMESTAMP);
    }

    /** Format an integer, with a maximum width. */
    public static String formatInteger(Integer value, int width) {
        return formatString(value == null ? "" : String.valueOf(value), width);
    }

    /** Format an integer. */
    public static String formatInteger(Integer value) {
        return formatString(value == null ? "" : String.valueOf(value));
    }

    /** Format a long value, with a maximum width. */
    public static String formatLong(Long value, int width) {
        return formatString(value == null ? "" : String.valueOf(value), width);
    }

    /** Format a long value. */
    public static String formatLong(Long value) {
        return formatString(value == null ? "" : String.valueOf(value));
    }

    /** Format an enum value, with a maxium width. */
    public static String formatEnum(Enum<?> value,  int width) {
        return formatString(value == null ? "" : value.name(), width);
    }

    /** Format an enum value. */
    public static String formatEnum(Enum<?> value) {
        return formatString(value == null ? "" : value.name());
    }

    /** Format a boolean value, with a maxium width. */
    public static String formatBoolean(Boolean value, String trueValue, String falseValue, int width) {
        if (value == null) {
            return "";
        } else {
            return formatString(value.booleanValue() ? trueValue : falseValue, width);
        }
    }

    /** Format a boolean value. */
    public static String formatBoolean(Boolean value, String trueValue, String falseValue) {
        if (value == null) {
            return "";
        } else {
            return formatString(value.booleanValue() ? trueValue : falseValue);
        }
    }

    /** Format a string value, with a maximum width. */
    public static String formatString(String value, int width) {
        if (value == null || width == 0) {
            return "";
        } else {
            if (trim(value).length() <= width) {
                return trim(value);
            } else {
                return substring(trim(value), 0, width - 3) + "...";
            }
        }
    }

    /** Format a string value. */
    public static String formatString(String value) {
        return value == null ? "" : trim(value);
    }

}
