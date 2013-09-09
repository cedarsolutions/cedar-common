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
package com.cedarsolutions.client.gwt.module.presenter;

import com.cedarsolutions.client.gwt.module.ModuleEventBus;
import com.cedarsolutions.client.gwt.module.view.IModulePageView;
import com.mvp4g.client.presenter.BasePresenter;

/**
 * Abstract presenter that understands how to render a module page view.
 * @param <V> Type of the view injected into the presenter
 * @param <E> Type of the event bus used by the presenter.
 * @author Kenneth J. Pronovici <pronovic@ieee.org>
 */
public abstract class ModulePagePresenter<V extends IModulePageView, E extends ModuleEventBus> extends BasePresenter<V, E> {

    /** Render the configured view via the replaceModuleBody event. */
    protected void replaceModuleBody() {
        this.eventBus.replaceModuleBody(view.getViewWidget());
    }

}
