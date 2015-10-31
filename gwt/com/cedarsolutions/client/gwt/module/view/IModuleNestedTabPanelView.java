/* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 *
 *              C E D A R
 *          S O L U T I O N S       "Software done right."
 *           S O F T W A R E
 *
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 *
 * Copyright (c) 2015 Kenneth J. Pronovici.
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
package com.cedarsolutions.client.gwt.module.view;

import com.cedarsolutions.client.gwt.event.ViewEventHandler;

/**
 * An interface that all nested tab panel views implement.
 * @author Kenneth J. Pronovici <pronovic@ieee.org>
 */
public interface IModuleNestedTabPanelView extends IModuleTabView, IModuleTabPanelView {

    /** Get the handler that selects the default nested tab. */
    ViewEventHandler getSelectDefaultNestedTabHandler();

    /** Set the handler that selects the default nested tab. */
    void setSelectDefaultNestedTabHandler(ViewEventHandler selectDefaultNestedTabHandler);

}
