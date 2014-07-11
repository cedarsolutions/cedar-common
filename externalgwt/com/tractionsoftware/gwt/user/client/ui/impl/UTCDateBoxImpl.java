/*
 * Copyright 2010 Traction Software, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package com.tractionsoftware.gwt.user.client.ui.impl;

import com.google.gwt.event.logical.shared.HasValueChangeHandlers;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.user.client.ui.HasEnabled;
import com.google.gwt.user.client.ui.HasText;
import com.google.gwt.user.client.ui.HasValue;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.datepicker.client.DateBox;

/**
 * Interface for UTCDateBox implementations that are quite different
 * in appearance (HTML4 vs HTML5).
 *
 * <h3>Code Source</h3>
 *
 * <p>
 * This is external code that was copied into the CedarCommon codebase under
 * the terms of its license.
 * </p>
 *
 * <blockquote>
 *    <table border="1" cellpadding="5" cellspacing="0">
 *       <tbody>
 *          <tr>
 *             <td><i>Source:</i></td>
 *             <td><a href="http://tractionsoftware.github.io/gwt-traction/">Traction Software</a></td>
 *          </tr>
 *          <tr>
 *             <td><i>Date:</i></td>
 *             <td>July, 2014</td>
 *          </tr>
 *          <tr>
 *             <td><i>Reason:</i></td>
 *             <td>The official Traction-supplied jar in Maven only supports Java 1.7+, but CedarCommon supports Java 1.6+.</td>
 *          </tr>
 *       </tbody>
 *    </table>
 * </blockquote>
 *
 * @author Andy @ Traction Software
 */
public interface UTCDateBoxImpl extends IsWidget, HasValue<Long>, HasValueChangeHandlers<Long>, HasText, HasEnabled {

    /**
     * Sets the DateTimeFormat for this UTCDateBox. The HTML5
     * implementation will ignore this.
     */
    public void setDateFormat(DateTimeFormat dateFormat);

    /**
     * Sets the visible length of the date input. The HTML5
     * implementation will ignore this.
     */
    public void setVisibleLength(int length);

    /**
     * Sets the tab index for the control.
     */
    public void setTabIndex(int tabIndex);

    /**
     * Returns the DateBox (if any) that this implementation uses. For
     * HTML5, this will return null. This was only added to make my
     * r52 tree compile and I don't intend to check it in.
     */
    public DateBox getDateBox();

}
