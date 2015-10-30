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
package com.cedarsolutions.client.gwt.module.presenter;

import com.cedarsolutions.client.gwt.module.ModuleEventBus;
import com.cedarsolutions.client.gwt.module.view.IModuleNestedTabPanelView;

/**
 * Abstract presenter that understands how to render a nested tab panel view.
 *
 * <p>
 * This implementation allows one level of nesting, where a top-level tab
 * contains a set of other tabs.  It's not entirely obvious how to generalize
 * it further than that.  However, even if I could, I'm skeptical that 3
 * levels of tabs will really work that well anyway.  You'd start to lose
 * a lot of screen real estate.
 * </p>
 *
 * @param <V> Type of the view injected into the presenter
 * @param <E> Type of the event bus used by the presenter.
 *
 * @author Kenneth J. Pronovici <pronovic@ieee.org>
 */
public abstract class ModuleNestedTabPanelPresenter<V extends IModuleNestedTabPanelView, E extends ModuleEventBus> extends ModulePagePresenter<V, E> {

    /** Render the configured view via the replaceModuleBody event. */
    @Override
    protected void replaceModuleBody() {
        // Normally, this renders view.getViewWidget().  If we do that here, the
        // nested tabs take over for the top-level tabs and everything gets screwed up.
        if (view.getParentView() != null) {
            this.eventBus.replaceModuleBody(view.getParentView().getViewWidget());
        }
    }

    /** Select this tab via its parent view, without adding history. */
    protected void selectThisTab() {
        // We can't select a sub-tab unless it's already visible.  So, in order
        // to go to a bookmark of a sub-tab, the parent tab must be selected first.
        // We also need to avoid firing events, otherwise extra steps end up in history.
        this.getView().getParentView().selectTabForView(this.getView(), false);
    }

}
