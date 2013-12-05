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

import com.google.gwt.event.logical.shared.ResizeEvent;
import com.google.gwt.event.logical.shared.ResizeHandler;
import com.google.gwt.user.client.Window;

/**
 * Specialized page that holds a set of tabs on a tab layout panel.
 * @author Kenneth J. Pronovici <pronovic@ieee.org>
 */
public abstract class ModuleTabPanelView extends ModulePageView implements IModuleTabPanelView {

    /** Default scaling for setting full-screen width. */
    public static final double WIDTH_SCALING = 0.95;

    /** Default scaling for setting full-screen height. */
    public static final double HEIGHT_SCALING = 0.85;

    /** The resize handler that is in use, if any. */
    protected TabPanelResizeHandler resizeHandler = null;

    /**
     * Add a new tab to the tab panel, with no view name.
     * @param view     View to use as tab contents
     * @param title    Title of the tab
     */
    protected void addTab(IModuleTabView view, String title) {
        addTab(view, null, title);
    }

    /**
     * Add a new tab to the tab panel.
     * @param view     View to use as tab contents
     * @param viewName Name of the view, to be used in a legible HTML id
     * @param title    Title of the tab
     */
    protected void addTab(IModuleTabView view, String viewName, String title) {
        this.getTabPanel().add(view.getViewWidget(), viewName, title);
        int tabIndex = this.getTabPanel().getWidgetCount() - 1;  // tab index for events is zero-based
        view.setContext(this.getTabPanel(), tabIndex);
    }

    /**
     * Add a new tab to the tab panel, automatically marking it as enabled.
     * The tab will be placed at the desired index, or at the end of the list if that's not possible.
     * @param view     View to use as tab contents
     * @param viewName Name of the view, to be used in a legible HTML id
     * @param title    Title of the tab
     * @param tabIndex Index at which tab should be placed, if possible
     */
    protected void addTab(IModuleTabView view, String viewName, String title, int tabIndex) {
        int actual = this.getTabPanel().add(view.getViewWidget(), viewName, title, tabIndex);
        view.setContext(this.getTabPanel(), actual);
        view.enableTab();
    }

    /** Configure the tab panel so it takes up the full screen, using default scaling. */
    protected void configureFullScreen() {
        this.configureFullScreen(WIDTH_SCALING, HEIGHT_SCALING);
    }

    /**
     * Configure the tab panel so it takes up the full screen.
     * @param widthScaling  Scaling percentage to use for the panel width
     * @param heightScaling Scaling percentage to use for the panel height
     */
    protected void configureFullScreen(double widthScaling, double heightScaling) {
        this.resizeHandler = new TabPanelResizeHandler(this, widthScaling, heightScaling);
        Window.addResizeHandler(this.resizeHandler);
    }

    /** Whether full-screen mode is configured. */
    public boolean isFullScreen() {
        return this.resizeHandler != null;
    }

    /**
     * Select the tab that displays the passed-in view.
     * @param view  View whose tab to select
     */
    protected void selectTabForView(IModuleTabView view) {
        int index = this.getTabPanel().getWidgetIndex(view.getViewWidget());
        if (index >= 0) {
            this.getTabPanel().selectTab(index);
        }
    }

    /**
     * Replace the first tab on the screen with the passed-in view.
     *
     * <p>
     * This is intended for use with views that have a single tab and swap
     * out the view that is actually being displayed as part of that tab.
     * If you have multiple tabs and you want to select one of them, use
     * selectTabForView().
     * </p>
     *
     * @param view   View to use as tab contents
     * @param title  Title of the tab
     */
    protected void replaceFirstTabWithView(IModuleTabView view, String title) {
        this.replaceFirstTabWithView(view, null, title);
    }

    /**
     * Replace the first tab on the screen with the passed-in view.
     * @param view     View to use as tab contents
     * @param viewName Name of the view, to be used in a legible HTML id
     * @param title    Title of the tab
     */
    protected void replaceFirstTabWithView(IModuleTabView view, String viewName, String title) {
        replaceTabWithView(view, viewName, title, 0);
    }

    /**
     * Replace a tab on the screen with the passed-in view, by index.
     * @param view     View to use as tab contents
     * @param viewName Name of the view, to be used in a legible HTML id
     * @param title    Title of the tab
     * @param tabIndex Index of the tab to replace.
     */
    protected void replaceTabWithView(IModuleTabView view, String viewName, String title, int tabIndex) {
        // Check whether there's already a tab at this index
        IModuleTabView oldTab = null;
        if (this.getTabPanel().getWidgetCount() > tabIndex) {
            oldTab = (IModuleTabView) this.getTabPanel().getWidget(tabIndex);
        }

        if (oldTab == null) {
            // If there's no old tab, just add the new one at the specified index
            this.addTab(view, viewName, title, tabIndex);
            this.getTabPanel().selectTab(tabIndex);
        } else {
            if (oldTab == view) {
                // If the old tab is exactly the same as the new one, just select it
                this.getTabPanel().selectTab(tabIndex);
            } else {
                // Otherwise, replace the old tab with the new tab
                oldTab.disableTab();
                this.addTab(view, viewName, title, tabIndex);
                this.getTabPanel().removeWithoutSelecting(tabIndex);
                this.getTabPanel().selectTab(tabIndex);
            }
        }
    }

    /** Window resize handler, which resizes a tab panel to full-screen. */
    public static class TabPanelResizeHandler implements ResizeHandler {

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

}
