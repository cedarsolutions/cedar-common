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

/**
 * Math utilities.
 * @author Kenneth J. Pronovici <pronovic@ieee.org>
 */
public class MathUtils {

    /**
     * Calculate the cumulative moving average.
     * Algorithm taken from http://en.wikipedia.org/wiki/Moving_average_(technical_analysis)
     * @param currentValue    Current value to add into the moving index
     * @param previousAverage Average as of the previous index
     * @param currentIndex    Current index into the dataset
     * @return Cumulative moving average through the current index.
     */
    public static double movingAverage(double currentValue, double previousAverage, long currentIndex) {
        return previousAverage + ((currentValue - previousAverage) / ((double) (currentIndex + 1)));
    }

}
