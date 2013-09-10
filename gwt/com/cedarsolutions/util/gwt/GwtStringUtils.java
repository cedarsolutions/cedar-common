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
package com.cedarsolutions.util.gwt;

import com.google.gwt.regexp.shared.RegExp;
import com.google.gwt.regexp.shared.SplitResult;


/**
 * String utilities that are translatable to GWT client code.
 *
 * <p>
 * This class includes only methods which can be translated by the GWT
 * compiler.  GWT client code should use this class.  Back-end or pure-Java
 * functionality should use StringUtils instead.
 * </p>
 *
 * @author Kenneth J. Pronovici <pronovic@ieee.org>
 */
public class GwtStringUtils {

    /**
     * Chop the last character off a string, like Perl's chop().
     * @param value  String value to operate on
     * @return Input value with last character chopped off.
     */
    public static String chop(String value) {
        if (value == null) {
            return null;
        } else if (value.length() == 0 || value.length() == 1) {
            return "";
        } else {
            return value.substring(0, value.length() - 1);
        }
    }

    /**
     * Indicates whether a string is null or empty.
     * @param value  String value to operate on
     * @return True if string value is null or empty when trimmed, false otherwise.
     */
    public static boolean isEmpty(String value) {
        return value == null || value.trim().length() == 0;
    }

    /**
     * Indicates whether two strings are equal.
     * This method is null-safe: null strings are equivalent to empty strings.
     * @param left   First string to compare
     * @param right  Second string to compare
     * @return True if the strings are equal, false otherwise.
     */
    public static boolean equals(String left, String right) {
        return equals(left, right, true);
    }

    /**
     * Indicates whether two strings are equal up to and including a certain number of bytes.
     * This method is null-safe: null strings are equivalent to empty strings.
     * @param left  First string to compare
     * @param right Second string to compare
     * @param max   Maximum number of bytes to check
     * @return True if the strings are equal, false otherwise.
     */
    public static boolean equals(String left, String right, int max) {
        left = left == null ? "" : left.length() < max ? left : left.substring(0, max);
        right = right == null ? "" : right.length() < max ? right : right.substring(0, max);
        return equals(left, right, true);
    }

    /**
     * Indicates whether two strings are equal, optionally case-insensitive.
     * This method is null-safe: null strings are equivalent to empty strings.
     * @param left          First string to compare
     * @param right         Second string to compare
     * @param caseSensitive Whether the check should be case-sensitive
     * @return True if the strings are equal, false otherwise.
     */
    public static boolean equals(String left, String right, boolean caseSensitive) {
        left = left == null ? "" : left;
        right = right == null ? "" : right;
        return caseSensitive ? left.equals(right) : left.equalsIgnoreCase(right);
    }

    /**
     * Substring method that safely deals with null strings and an end index that is too large.
     * If the passed-in string is null, then the result will always be null.
     * The start index must always be valid, except when the string is null.
     * If the passed-in end index is past the end of the string, then the end of the string is used.
     * @param value       String value to operate on
     * @param startIndex  Start index
     * @param endIndex    End index
     * @return Substring, possibly null.
     */
    public static String substring(String value, int startIndex, int endIndex) {
        if (value == null) {
            return null;
        } else {
            endIndex = value.length() < endIndex ? value.length() : endIndex;
            return value.substring(startIndex, endIndex);
        }
    }

    /**
     * Safe substring method that safely deals with null strings.
     * If the passed-in string is null, then the result will always be null.
     * The start index must always be valid, except when the string is null.
     * @param value       String value to operate on
     * @param startIndex  Start index
     * @return Substring, possibly null.
     */
    public static String substring(String value, int startIndex) {
        return value == null ? null : value.substring(startIndex);
    }

    /**
     * Truncate a string to a maximum length.
     * A null string will always be truncated to an empty string.
     * @param value  String value to operate on
     * @param max    Maximum length to allow
     * @return String truncated to the maximum length.
     */
    public static String truncate(String value, int max) {
        if (value == null || max == 0) {
            return "";
        } else {
            if (value.length() <= max) {
                return trim(value);
            } else {
                return trim(substring(value, 0, max));
            }
        }
    }

    /**
     * Truncate a string to a maximum length.
     * A null string will always be truncated to a null value.
     * @param value  String value to operate on
     * @param max    Maximum length to allow
     * @return String truncated to the maximum length.
     */
    public static String truncateToNull(String value, int max) {
        if (value == null || max == 0) {
            return null;
        } else {
            if (value.length() <= max) {
                return trimToNull(value);
            } else {
                return trimToNull(substring(value, 0, max));
            }
        }
    }

    /**
     * Trim a string value (null-safe).
     * @param value  String value to operate on
     * @return Trimmed value, possibly null.
     */
    public static String trim(String value) {
        return value == null ? null : value.trim();
    }

    /**
     * Trim a string value, returning null if the trimmed string is empty.
     * @param value  String value to operate on
     * @return Trimmed value, possibly null.
     */
    public static String trimToNull(String value) {
        String result = trim(value);
        return result == null || result.length() == 0 ? null : result;
    }

    /**
     * Right-trim a string value (null-safe).
     * @param value  String value to operate on
     * @return Trimmed value, possibly null.
     */
    public static String rtrim(String value) {
        return value == null ? null : value.replaceAll("\\s+$", "");
    }

    /**
     * Right-trim a string value (null-safe).
     * @param value  String value to operate on
     * @return Trimmed value, possibly null.
     */
    public static String rtrimToNull(String value) {
        String result = rtrim(value);
        return result == null || result.length() == 0 ? null : result;
    }

    /**
     * Equivalent of string.contains(), but is null-safe.
     * @param value     String value to operate on
     * @param substring Substring to check for
     * @return True if string contains the substring, false otherwise.
     */
    public static boolean contains(String value, String substring) {
        return contains(value, substring, true);
    }

    /**
     * Equivalent of string.contains(), but is null-safe and optionally case-insensitive.
     * @param value         String value to operate on
     * @param substring     Substring to check for
     * @param caseSensitive Whether the check should be case-sensitive
     * @return True if string contains the substring, false otherwise.
     */
    public static boolean contains(String value, String substring, boolean caseSensitive) {
        if (value == null || substring == null) {
            return false;
        } else {
            if (!caseSensitive) {
                value = value.toUpperCase();
                substring = substring.toUpperCase();
            }

            return value.contains(substring);
        }
    }

    /**
     * Equivalent of String.startsWith(), but is null-safe.
     * @param value      String value to operate on
     * @param substring  Substring to check for
     * @return True if string starts with the substring, false otherwise.
     */
    public static boolean startsWith(String value, String substring) {
        return startsWith(value, substring, true);
    }

    /**
     * Equivalent of String.startsWith(), but is null-safe and optionally case-insensitive.
     * @param value         String value to operate on
     * @param substring     Substring to check for
     * @param caseSensitive Whether the check should be case-sensitive
     * @return True if string starts with the substring, false otherwise.
     */
    public static boolean startsWith(String value, String substring, boolean caseSensitive) {
        if (value == null || substring == null) {
            return false;
        } else {
            if (!caseSensitive) {
                value = value.toUpperCase();
                substring = substring.toUpperCase();
            }

            return value.startsWith(substring);
        }
    }

    /**
     * Equivalent of String.toUpperCase(), but is null-safe.
     * @param value  String value to operate on
     * @return String converted to upper case, or null if input is null.
     */
    public static String toUpperCase(String value) {
        return value == null ? null : value.toUpperCase();
    }

    /**
     * Formats a string more-or-less like String.format() or java.util.Formatter.format().
     *
     * <p>
     * This is needed because these format methods are not available in translated code.
     * This doesn't really work that well in general... for instance, %d and %s work, but
     * not %x.
     * </p>
     *
     * @param format   Format to use, like with String.format()
     * @param args     Arguments to apply into format
     * @return Formatted string.
     * @see <a href="http://stackoverflow.com/questions/3126232/string-formatter-in-gwt">Stack Overflow</a>
     */
    public static String format(final String format, final Object... args) {
        RegExp regex = RegExp.compile("%[a-z]");
        SplitResult split = regex.split(format);
        StringBuffer msg = new StringBuffer();

        for (int pos = 0; pos < split.length() - 1; pos += 1) {
            msg.append(split.get(pos));
            msg.append(args[pos].toString());
        }

        msg.append(split.get(split.length() - 1));
        return msg.toString();
    }

}
