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
package com.cedarsolutions.client.gwt.module.view;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.Panel;

/**
 * Abstract class that module template views can inherit from.
 * @author Kenneth J. Pronovici <pronovic@ieee.org>
 */
public abstract class ModuleTemplateView extends Composite implements IModuleTemplateView {

    /** Get the module body from the UI binder. */
    protected abstract Panel getModuleBody();

    /** Get the widget associated with this view. */
    @Override
    public IsWidget getViewWidget() {
        return this;
    }

    /** Set the contents of the module body, replacing the previous contents. */
    @Override
    public void replaceModuleBody(IsWidget contents) {
        this.getModuleBody().clear();
        this.getModuleBody().add(contents);
    }

}
