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

import com.google.gwt.user.client.ui.Composite;

/**
 * Shared implementation for UTCTimeBox.
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
public abstract class UTCDateBoxImplShared extends Composite implements UTCDateBoxImpl {

    /**
     * Sets the visible length of the date input. The HTML5
     * implementation will ignore this.
     */
    @Override
    public void setVisibleLength(int length) {}

    /**
     * Sets the date value (as milliseconds at midnight UTC since 1/1/1970)
     */
    @Override
    public final void setValue(Long value) {
        setValue(value, true);
    }

}
