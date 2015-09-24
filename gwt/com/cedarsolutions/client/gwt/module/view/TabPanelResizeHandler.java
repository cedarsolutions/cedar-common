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

import com.google.gwt.event.logical.shared.ResizeEvent;
import com.google.gwt.event.logical.shared.ResizeHandler;
import com.google.gwt.user.client.Window;

/**
 * Window resize handler, which resizes a tab panel to full-screen.
 * @author Kenneth J. Pronovici <pronovic@ieee.org>
 */
public class TabPanelResizeHandler implements ResizeHandler {

    private IModuleTabPanelView view;
    private double widthScaling;
    private double heightScaling;

    public TabPanelResizeHandler(IModuleTabPanelView view, double widthScaling, double heightScaling) {
        this.view = view;
        this.widthScaling = widthScaling;
        this.heightScaling = heightScaling;
        this.resize(Window.getClientWidth(), Window.getClientHeight());
    }

    @Override
    public void onResize(ResizeEvent event) {
        // This method cannot be unit tested because it's not possible to mock ResizeEvent
        this.resize(event.getWidth(), event.getHeight());
    }

    private void resize(int w, int h) {
        String width = ((int) (this.widthScaling * w)) + "px";
        String height = ((int) (this.heightScaling * h)) + "px";
        this.view.getTabPanel().setWidth(width);
        this.view.getTabPanel().setHeight(height);
    }

    public IModuleTabPanelView getView() {
        return this.view;
    }

    public double getWidthScaling() {
        return this.widthScaling;
    }

    public double getHeightScaling() {
        return this.heightScaling;
    }

}
