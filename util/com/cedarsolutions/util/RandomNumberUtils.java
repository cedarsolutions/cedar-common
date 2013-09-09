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
package com.cedarsolutions.util;

import java.util.Random;

/**
 * Random number utilities.
 * @author Kenneth J. Pronovici <pronovic@ieee.org>
 */
public class RandomNumberUtils {

    /** Set of alphabetic ASCII characters. */
    private static final String ALPHABETIC = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";

    /** Set of numeric ASCII characters. */
    private static final String NUMERIC = "1234567890";

    /** Set of alphanumeric ASCII characters. */
    private static final String ALPHANUMERIC = ALPHABETIC + NUMERIC;

    /** Random number generator. */
    private static final Random GENERATOR = new Random();

    /**
     * Get a random integer on a specified range.
     * If the passed in min and max are equal, then that value is immediately returned.
     * If max is less than min, then the order is reversed so that a sensible result is generated.
     * @param min   Minimum value (inclusive)
     * @param max   Maximum value (inclusive)
     * @return Random integer on the specified range.
     */
    public static int generateRandomInteger(int min, int max) {
        if (min == max) {
            return min;
        } else if (max < min) {
            return generateRandomInteger(max, min);
        } else if (min == 0 && max > 0) {
            return GENERATOR.nextInt(max + 1);  // +1 so that maximum is made inclusive
        } else {
            double range = (double) max - min;
            double offset = (double) min;
            double value = (GENERATOR.nextDouble() * range) + offset;
            return (int) Math.floor(value);
        }
    }

    /**
     * Generate a random alphanumeric character.
     * @return Random alphanumeric character.
     */
    public static char generateRandomAlphanumericCharacter() {
        int sourceLength = ALPHANUMERIC.length();
        int index = generateRandomInteger(0, sourceLength - 1);
        return ALPHANUMERIC.charAt(index);
    }

    /**
     * Generate a random alphabetic character.
     * @return Random alphabetic character.
     */
    public static char generateRandomAlphabeticCharacter() {
        int sourceLength = ALPHABETIC.length();
        int index = generateRandomInteger(0, sourceLength - 1);
        return ALPHABETIC.charAt(index);
    }

    /**
     * Generate a random numeric character.
     * @return Random alphabetic character.
     */
    public static char generateRandomNumericCharacter() {
        int sourceLength = NUMERIC.length();
        int index = generateRandomInteger(0, sourceLength - 1);
        return NUMERIC.charAt(index);
    }

    /**
     * Generate a random alphanumeric string with a given length.
     * @param length Length of the string to generate.
     * @return Randomly generated string.
     */
    public static String generateRandomAlphanumericString(int length) {
        StringBuffer buffer = new StringBuffer(length);

        for (int i = 0; i < length; i++) {
            buffer.append(generateRandomAlphanumericCharacter());
        }

        return buffer.toString();
    }

    /**
     * Generate a random alphabetic string with a given length.
     * @param length Length of the string to generate.
     * @return Randomly generated string.
     */
    public static String generateRandomAlphabeticString(int length) {
        StringBuffer buffer = new StringBuffer(length);

        for (int i = 0; i < length; i++) {
            buffer.append(generateRandomAlphabeticCharacter());
        }

        return buffer.toString();
    }

    /**
     * Generate a random numeric string with a given length.
     * @param length Length of the string to generate.
     * @return Randomly generated string.
     */
    public static String generateRandomNumericString(int length) {
        StringBuffer buffer = new StringBuffer(length);

        for (int i = 0; i < length; i++) {
            buffer.append(generateRandomNumericCharacter());
        }

        return buffer.toString();
    }

}
