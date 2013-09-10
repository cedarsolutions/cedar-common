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
package com.cedarsolutions.util;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

/**
 * Unit tests for MathUtils.
 * @author Kenneth J. Pronovici <pronovic@ieee.org>
 */
public class MathUtilsTest {

    /** Test the movingAverage() method. */
    @Test public void testMovingAverage() throws Exception {
        double[] values = { 90.0, 80.0, 70.0, 60.0, 50.0, 40.0, 30.0, 20.0, 10.0, };

        int currentIndex = 0;
        double actualAverage = 0.0;
        double previousAverage = 0.0;
        double expectedAverage = 0.0;

        // This method uses the efficient algorithm described at http://en.wikipedia.org/wiki/Moving_average_(technical_analysis)
        // To check the results, we will do a brute force calculation and compare against that.

        currentIndex = 0;
        previousAverage = 0.0;
        expectedAverage = 90.0;
        actualAverage = MathUtils.movingAverage(values[currentIndex], previousAverage, currentIndex);
        assertEquals(expectedAverage, actualAverage, 0);

        currentIndex = 1;
        previousAverage = actualAverage;
        expectedAverage = (90.0 + 80.0) / 2.0;
        actualAverage = MathUtils.movingAverage(values[currentIndex], previousAverage, currentIndex);
        assertEquals(expectedAverage, actualAverage, 0);

        currentIndex = 2;
        previousAverage = actualAverage;
        expectedAverage = (90.0 + 80.0 + 70.0) / 3.0;
        actualAverage = MathUtils.movingAverage(values[currentIndex], previousAverage, currentIndex);
        assertEquals(expectedAverage, actualAverage, 0);

        currentIndex = 3;
        previousAverage = actualAverage;
        expectedAverage = (90.0 + 80.0 + 70.0 + 60.0) / 4.0;
        actualAverage = MathUtils.movingAverage(values[currentIndex], previousAverage, currentIndex);
        assertEquals(expectedAverage, actualAverage, 0);

        currentIndex = 4;
        previousAverage = actualAverage;
        expectedAverage = (90.0 + 80.0 + 70.0 + 60.0 + 50.0) / 5.0;
        actualAverage = MathUtils.movingAverage(values[currentIndex], previousAverage, currentIndex);
        assertEquals(expectedAverage, actualAverage, 0);

        currentIndex = 5;
        previousAverage = actualAverage;
        expectedAverage = (90.0 + 80.0 + 70.0 + 60.0 + 50.0 + 40.0) / 6.0;
        actualAverage = MathUtils.movingAverage(values[currentIndex], previousAverage, currentIndex);
        assertEquals(expectedAverage, actualAverage, 0);

        currentIndex = 6;
        previousAverage = actualAverage;
        expectedAverage = (90.0 + 80.0 + 70.0 + 60.0 + 50.0 + 40.0 + 30.0) / 7.0;
        actualAverage = MathUtils.movingAverage(values[currentIndex], previousAverage, currentIndex);
        assertEquals(expectedAverage, actualAverage, 0);

        currentIndex = 7;
        previousAverage = actualAverage;
        expectedAverage = (90.0 + 80.0 + 70.0 + 60.0 + 50.0 + 40.0 + 30.0 + 20.0) / 8.0;
        actualAverage = MathUtils.movingAverage(values[currentIndex], previousAverage, currentIndex);
        assertEquals(expectedAverage, actualAverage, 0);

        currentIndex = 8;
        previousAverage = actualAverage;
        expectedAverage = (90.0 + 80.0 + 70.0 + 60.0 + 50.0 + 40.0 + 30.0 + 20.0 + 10.0) / 9.0;
        actualAverage = MathUtils.movingAverage(values[currentIndex], previousAverage, currentIndex);
        assertEquals(expectedAverage, actualAverage, 0);
    }

}

