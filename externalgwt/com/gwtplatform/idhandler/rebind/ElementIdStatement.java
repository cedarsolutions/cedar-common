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

package com.gwtplatform.idhandler.rebind;

/**
 * Represents a Java statement that sets the generated DOM element ID into the corresponding field.
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
 */
public class ElementIdStatement {

    private final String fieldExpression;
    private final String elementId;

    public ElementIdStatement(String fieldExpression, String elementId) {
        this.fieldExpression = fieldExpression;
        this.elementId = elementId;
    }

    public String buildIdSetterStatement() {
        return String.format("setElementId(%s, \"%s\")", fieldExpression, elementId);
    }

    public String buildGuardCondition() {
        StringBuilder sb = new StringBuilder();

        String[] subPaths = getSubPaths(fieldExpression);
        for (int i = 0; i < subPaths.length; i++) {
            sb.append(subPaths[i]).append(" != null");

            if (i < subPaths.length - 1) {
                sb.append(" && ");
            }
        }

        return sb.toString();
    }

    String[] getSubPaths(String path) {
        String[] pathElements = path.split("\\.");
        String[] result = new String[pathElements.length];

        for (int i = 0; i < pathElements.length; i++) {
            String currentElement = pathElements[i];

            if (currentElement.isEmpty()) {
                throw new IllegalStateException("Malformed path: " + path);
            }

            if (i == 0) {
                result[i] = currentElement;
            } else {
                result[i] = result[i - 1] + "." + currentElement;
            }
        }

        return result;
    }

    @Override
    public String toString() {
        return elementId;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((elementId == null) ? 0 : elementId.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        } else if (obj == null) {
            return false;
        } else if (getClass() != obj.getClass()) {
            return false;
        }

        ElementIdStatement other = (ElementIdStatement) obj;

        if (elementId == null) {
            if (other.elementId != null) {
                return false;
            }
        } else if (!elementId.equals(other.elementId)) {
            return false;
        }

        return true;
    }

}
