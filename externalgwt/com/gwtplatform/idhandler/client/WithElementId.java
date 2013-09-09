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

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Indicates that the given field will have its DOM element ID set by an {@link ElementIdHandler} implementation.
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
 * <h3>Original Documentation</h3>
 *
 * <p>
 * Semantics of this annotation for different types are shown in the following table:
 * <p>
 *
 * <blockquote>
 * <table border="1" cellpadding="5" cellspacing="0">
 * <thead>
 * <tr>
 * <th>Field Type</th>
 * <th>Semantics</th>
 * </tr>
 * </thead> <tbody>
 * <tr>
 * <td>{@link HasElementId}</td>
 * <td>call {@link HasElementId#setElementId setElementId} (used with custom UI object types)</td>
 * </tr>
 * <tr>
 * <td>UIObject</td>
 * <td>access element through getElement and set its ID using setId</td>
 * </tr>
 * <tr>
 * <td>Element</td>
 * <td>set element ID using setId</td>
 * </tr>
 * </tbody>
 * </table>
 * </blockquote>
 *
 * <p>
 * Since {@link ElementIdHandler} implementations access field values directly through field declarations, annotated
 * fields should not be {@code private}.
 *
 * @see ElementIdHandler
 * @see HasElementId
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface WithElementId {

    /**
     * Overrides the default field ID that is part of the resulting DOM element ID.
     * <p>
     * When not specified, the name of the annotated field will be taken as the field ID value.
     *
     * @return Custom field ID or an empty string to use the default value.
     */
    String value() default "";

    /**
     * If {@code true}, declared type of the given field will be recursively processed with regard to
     * {@literal @WithElementId} fields. If {@code false}, no further action will be taken on the field type.
     *
     * @return {@code true} if the field type should be recursively processed with regard to {@literal @WithElementId}
     *         fields, {@code false} otherwise.
     */
    boolean processType() default true;

}
