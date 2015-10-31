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

import com.cedarsolutions.client.gwt.event.UnifiedEvent;
import com.cedarsolutions.client.gwt.event.UnifiedEventType;
import com.cedarsolutions.client.gwt.widget.NativeUtils;
import com.google.gwt.event.logical.shared.BeforeSelectionEvent;
import com.google.gwt.event.logical.shared.BeforeSelectionHandler;

/**
 * Tab selection handler for a nested tab panel.
 * @author Kenneth J. Pronovici <pronovic@ieee.org>
 */
public class NestedTabSelectionHandler implements BeforeSelectionHandler<Integer> {

    private boolean enabled;
    private IModuleNestedTabPanelView view;

    public NestedTabSelectionHandler(IModuleNestedTabPanelView view) {
        this.enabled = true;
        this.view = view;
    }

    public void enable() {
        this.enabled = true;
    }

    public void disable() {
        this.enabled = false;
    }

    @Override
    public void onBeforeSelection(BeforeSelectionEvent<Integer> event) {
        this.onBeforeSelection(event.getItem());
    }

    // If none of the nested tabs is selected, then we need to select a
    // default.  Otherwise, nothing useful related to the tab selection gets
    // into the browser history.  If a nested tab has previously been selected,
    // then we need to manually make sure that this click on the parent tab
    // results in the proper tokens in the history.
    //
    // If we don't do this, clicks on the parent tab (which result in visual
    // changes on the screen, i.e. display of the nested tab) are not tracked
    // in history, and then the browser back and forward buttons don't work in
    // an intuitive way.
    //
    // I wish I could come up with a better way to accomplish this, but I don't
    // see anything obvious.  The event bus lets me get a history token, but
    // doesn't seem to offer a way to go to that token.  By redirecting, I
    // invoke the event bus's standard bookmark mechanism, which does what
    // I need.

    protected void onBeforeSelection(int tabIndex) {
        if (this.enabled) {
            if (tabIndex == this.view.getTabIndex()) {
                this.view.selectTab();
                if (this.view.getSelectedTabView() == null) {
                    if (this.view.getSelectDefaultNestedTabHandler() != null) {
                        UnifiedEvent selected = new UnifiedEvent(UnifiedEventType.SELECTED_EVENT);
                        view.getSelectDefaultNestedTabHandler().handleEvent(selected);
                    }
                } else {
                    String token = this.view.getSelectedHistoryToken();
                    if (token != null) {
                        // This is equivalent to WidgetUtils.redirect(WidgetUtils.getDestinationUrl(token))
                        // Unfortunately, WidgetUtils is not available to code within CedarCommon.
                        String url = NativeUtils.getWndLocationHref().replaceAll("#.*$", "") + "#" + token;
                        NativeUtils.redirect(url);
                    }
                }
            }
        }
    }

    protected boolean isEnabled() {
        return this.enabled;
    }

}
