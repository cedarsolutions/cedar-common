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
package com.cedarsolutions.client.gwt.custom.table;

import static com.google.gwt.safehtml.shared.SafeHtmlUtils.fromSafeConstant;

import com.google.gwt.cell.client.Cell;
import com.google.gwt.safehtml.shared.SafeHtml;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;


/**
 * A {@link Cell} used to render a checkbox, which can be enabled and disabled (CUSTOMIZED).
 *
 * <h3>
 * Code Source
 * </h3>
 *
 * <p>
 * This is customized code that extends the original GWT code.  The
 * <code>render()</code> method was copied from the parent class and
 * modified.
 * </p>
 *
 * <blockquote>
 *    <table border="1" cellpadding="5" cellspacing="0">
 *       <tbody>
 *          <tr>
 *             <td><i>Source:</i></td>
 *             <td>{@link com.google.gwt.cell.client.CheckboxCell}</td>
 *          </tr>
 *          <tr>
 *             <td><i>Version:</i></td>
 *             <td>GWT 2.5.1</td>
 *          </tr>
 *          <tr>
 *             <td><i>Date:</i></td>
 *             <td>December, 2013</td>
 *          </tr>
 *          <tr>
 *             <td><i>Purpose:</i></td>
 *             <td>CheckboxCell can't be enabled or disabled.</td>
 *          </tr>
 *          <tr>
 *             <td><i>See also:</i></td>
 *             <td><a href="http://stackoverflow.com/questions/6089302/disable-checkboxcell-in-a-celltable">StackOverflow</a></td>
 *          </tr>
 *       </tbody>
 *    </table>
 * </blockquote>
 *
 * @author Kenneth J. Pronovici <pronovic@ieee.org>
 */
public class CheckboxCell extends com.google.gwt.cell.client.CheckboxCell {

    // Constants used in rendering
    private static final SafeHtml CHECKED = fromSafeConstant("<input type=\"checkbox\" tabindex=\"-1\" checked/>");
    private static final SafeHtml UNCHECKED = fromSafeConstant("<input type=\"checkbox\" tabindex=\"-1\"/>");
    private static final SafeHtml DISABLED = fromSafeConstant("<input type=\"checkbox\" tabindex=\"-1\" disabled=\"disabled\"/>");

    /** Whether the cell is enabled. */
    private boolean enabled;

    /**
     * Construct a new {@link CheckboxCell} that optionally controls selection.
     * @param dependsOnSelection true if the cell depends on the selection state
     * @param handlesSelection true if the cell modifies the selection state
     */
    public CheckboxCell(boolean dependsOnSelection, boolean handlesSelection) {
        super(dependsOnSelection, handlesSelection);
        this.enabled = true;
    }

    /** Whether the cell is enabled. */
    public boolean isEnabled() {
        return this.enabled;
    }

    /** Whether the cell is enabled. */
    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    /** Render the cell, taking into account the enabled/disabled state. */
    @Override
    public void render(Context context, Boolean value, SafeHtmlBuilder sb) {
        Object key = context.getKey();
        Boolean viewData = getViewData(key);
        if (viewData != null && viewData.equals(value)) {
            clearViewData(key);
            viewData = null;
        }

        if (!this.isEnabled()) {
            sb.append(DISABLED);
        } else {
            if (value != null && ((viewData != null) ? viewData : value)) {
                sb.append(CHECKED);
            } else {
                sb.append(UNCHECKED);
            }
        }
    }

}
