/**
 * Copyright 2011 ArcBees Inc.
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

package com.gwtplatform.idhandler.client;

/**
 * Interface typically implemented by custom UI objects that provides an abstract way of setting DOM element IDs.
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
 *             <td><a href="http://code.google.com/p/gwt-platform/issues/detail?id=389">GWTP Issue #389</a></td>
 *          </tr>
 *          <tr>
 *             <td><i>Date:</i></td>
 *             <td>March, 2011</td>
 *          </tr>
 *       </tbody>
 *    </table>
 * </blockquote>
 *
 * @see WithElementId
 */
public interface HasElementId {

    /**
     * Applies the given DOM element ID to this object.
     *
     * @param elementId
     *            Element ID to set.
     */
    void setElementId(String elementId);

    /** Gets the DOM element ID that has been applied to this object. */
    String getElementId();

}
