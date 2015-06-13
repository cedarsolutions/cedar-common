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

import com.google.gwt.dom.client.Element;
import com.google.gwt.user.client.ui.UIObject;

/**
 * Base class for generated {@link ElementIdHandler} implementations.
 *
 * <p>
 * Provides an abstraction for handling different field types with regard to setting DOM element IDs.
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
 * @param <T>
 *            The type of an object that contains {@literal @WithElementId} fields.
 *
 * @see ElementIdHandler
 */
public abstract class BaseElementIdHandler<T> implements ElementIdHandler<T> {

    private String idExtension = "";

    @Override
    public void setIdExtension(String extension) {
        if (extension == null || extension.isEmpty()) {
            return;
        }

        this.idExtension = "_" + extension;
    }

    protected final void setElementId(Object obj, String elementId) {
        setExtendedElementId(obj, elementId + idExtension);
    }

    /**
     * Applies the generated (and possibly extended) DOM element ID to the given object.
     *
     * @param obj
     *            Object for which to set the element ID.
     * @param elementId
     *            Element ID to set.
     */
    protected void setExtendedElementId(Object obj, String elementId) {
        if (obj instanceof HasElementId) {
            ((HasElementId) obj).setElementId(elementId);
        } else if (obj instanceof UIObject) {
            ((UIObject) obj).getElement().setId(elementId);
        } else if (obj instanceof Element) {
            ((Element) obj).setId(elementId);
        }
    }

}
