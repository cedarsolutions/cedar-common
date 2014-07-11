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
package com.tractionsoftware.gwt.user.client.util;

import com.google.gwt.dom.client.Element;
import com.google.gwt.user.client.ui.UIObject;

/**
 * DOM utilities.
 *
 * <p>
 * Created for a uniform implementation of HasEnabled, but will be
 * useful in the future for other DOM related things.
 * </p>
 *
 * <p>
 * This is a subclass of UIObject to gain access to the useful
 * setStyleName method of UIObject which is implemented as protected.
 * </p>
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
public class DomUtils extends UIObject {

    /**
     * This object is never created.
     */
    private DomUtils() {}

    /**
     * It's enough to just set the disabled attribute on the
     * element, but we want to also add a "disabled" class so that we can
     * style it.
     *
     * At some point we'll just be able to use .button:disabled,
     * but that doesn't work in IE8-
     */
    public static void setEnabled(Element element, boolean enabled) {
        element.setPropertyBoolean("disabled", !enabled);
    setStyleName(element, "disabled", !enabled);
    }

    /**
     * Returns true if the element has the disabled attribute.
     */
    public static boolean isEnabled(Element element) {
        return element.getPropertyBoolean("disabled");
    }

}
