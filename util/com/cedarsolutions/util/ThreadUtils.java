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
 * Thread utilities.
 * @author Kenneth J. Pronovici <pronovic@ieee.org>
 */
public class ThreadUtils {

    /**
     * Sleep for a certain amount of time, ignoring interruptions.
     * The total wait time will equal or exceed the requested wait.
     * @param milliseconds  Number of milliseconds to sleep for
     */
    public static void sleep(int milliseconds) {
        if (milliseconds > 0) {
            Timer timer = new Timer();
            timer.start();
            timer.stop(); // so elapsed time can be calculated
            while (timer.getElapsedTime() <= milliseconds) {
                try {
                    Thread.sleep(milliseconds - timer.getElapsedTime());
                    timer.stop();  // so we get a new elapsed time
                } catch (InterruptedException e) { }
            }
        }
    }

}
