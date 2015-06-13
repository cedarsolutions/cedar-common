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

import com.google.gwt.i18n.client.ConstantsWithLookup;

/**
 * Test configuration interface.
 * @author Kenneth J. Pronovici <pronovic@ieee.org>
 */
public interface TestConfig extends ConstantsWithLookup {

    @DefaultBooleanValue(true)
    Boolean testTrueBooleanValue();

    @DefaultBooleanValue(false)
    Boolean testFalseBooleanValue();

    @DefaultDoubleValue(432.0d)
    Double testDoubleValue();

    @DefaultFloatValue(16.0f)
    Float testFloatValue();

    @DefaultIntValue(5)
    Integer testIntegerValue();

    @DefaultStringValue("StringValue")
    String testStringValue();

}
