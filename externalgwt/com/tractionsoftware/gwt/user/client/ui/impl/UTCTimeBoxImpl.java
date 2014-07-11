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
import com.google.gwt.user.client.ui.HasText;
import com.google.gwt.user.client.ui.HasValue;
import com.google.gwt.user.client.ui.IsWidget;

/**
 * Interface for UTCTimeBox implementations that are quite different
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
public interface UTCTimeBoxImpl extends IsWidget, HasValue<Long>, HasValueChangeHandlers<Long>, HasText {

    /**
     * Sets the DateTimeFormat for this UTCTimeBox. The HTML5
     * implementation will ignore this.
     */
    public void setTimeFormat(DateTimeFormat timeFormat);

    /**
     * Sets the visible length of the time input. The HTML5
     * implementation will ignore this.
     */
    public void setVisibleLength(int length);

    /**
     * Validates the value that has been typed into the text input.
     * The HTML5 implementation will do nothing.
     */
    public void validate();

    /**
     * Sets the tab index for the control.
     */
    public void setTabIndex(int tabIndex);

}
