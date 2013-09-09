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
 * Interface implemented by classes that generate and set DOM element IDs into appropriate fields of an owner.
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
 * Generated element IDs are guaranteed to be unique and deterministic within the context of the given owner type.
 * <p>
 * Implementations of this interface are intended to be generated for each specific owner type, for example:
 *
 * <pre>
 * public class HelloWorld {
 *
 *     interface MyIdHandler extends ElementIdHandler&lt;HelloWorld&gt; {}
 *     private static MyIdHandler idHandler = GWT.create(MyIdHandler.class);
 *
 *     &#064;WithElementId
 *     Label label;
 *
 *     public HelloWorld() {
 *         label = new Label(&quot;Hello World&quot;);
 *         idHandler.generateAndSetIds(this);
 *     }
 *
 * }
 * </pre>
 *
 * In the example above, the label widget's DOM element will have its ID set to {@code HelloWorld_label}.
 * <p>
 * In case there are multiple instances of the same owner type displayed on the page, you can extend IDs generated for
 * those instances during runtime, for example:
 *
 * <pre>
 * public class HelloWorld {
 *
 *     ...
 *
 *     &#064;WithElementId
 *     Label label;
 *
 *     public HelloWorld(String extension) {
 *         label = new Label(&quot;Hello World&quot;);
 *         idHandler.setIdExtension(extension);
 *         idHandler.generateAndSetIds(this);
 *     }
 *
 * }
 *
 * public class MyApplication {
 *
 *     public void initialize() {
 *         new HelloWorld("One");
 *         new HelloWorld("Two");
 *     }
 *
 * }
 * </pre>
 *
 * This will cause label widgets to have following IDs set on their DOM elements:
 * <p>
 * <ul>
 * <li>{@code HelloWorld_label_One} for {@code HelloWorld} instance "One"
 * <li>{@code HelloWorld_label_Two} for {@code HelloWorld} instance "Two"
 * </ul>
 *
 * @param <T>
 *            The type of an object that contains {@literal @WithElementId} fields.
 *
 * @see WithElementId
 * @see HasElementId
 */
public interface ElementIdHandler<T> {

    /**
     * Generates and sets DOM element IDs into appropriate fields of the given root object.
     *
     * @param owner
     *            The object whose {@literal @WithElementId} fields need to be processed.
     */
    void generateAndSetIds(T owner);

    /**
     * Extends generated DOM element IDs with the given value at runtime.
     * <p>
     * This can be helpful when there are multiple instances of the same owner type displayed on the page.
     * <p>
     * Providing {@code null} or empty String has no effect.
     *
     * @param extension
     *            String value to append to DOM element IDs.
     */
    void setIdExtension(String extension);

}
