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

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.core.ext.TreeLogger;
import com.google.gwt.core.ext.UnableToCompleteException;
import com.google.gwt.core.ext.typeinfo.JClassType;
import com.google.gwt.core.ext.typeinfo.JField;
import com.google.gwt.core.ext.typeinfo.TypeOracle;
import com.google.gwt.editor.rebind.model.ModelUtils;
import com.gwtplatform.idhandler.client.ElementIdHandler;
import com.gwtplatform.idhandler.client.WithElementId;

/**
 * Walks through fields of an owner type for the given {@link ElementIdHandler} subinterface, generating DOM element IDs
 * and collecting corresponding field statements.
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
 * @see ElementIdStatement
 */
public class ElementIdTypeParser {

    private static final String ID_ELEMENT_SEPARATOR = "_";

    private final TreeLogger logger;
    private final JClassType ownerType;

    final List<ElementIdStatement> statements = new ArrayList<ElementIdStatement>();

    public ElementIdTypeParser(TreeLogger logger, JClassType interfaceToImplement) throws UnableToCompleteException {
        assert logger != null : "logger was null";
        assert interfaceToImplement != null : "interfaceToImplement was null";

        this.logger = logger.branch(TreeLogger.DEBUG, "Creating ElementIdTypeParser for "
                + interfaceToImplement.getQualifiedSourceName());

        this.ownerType = resolveOwnerType(interfaceToImplement);
    }

    JClassType resolveOwnerType(JClassType interfaceToImplement) throws UnableToCompleteException {
        TypeOracle oracle = interfaceToImplement.getOracle();

        JClassType handlerInterface = oracle.findType(ElementIdHandler.class.getName()).isInterface();
        assert handlerInterface != null : "No ElementIdHandler type";

        if (!handlerInterface.isAssignableFrom(interfaceToImplement)) {
            die(String.format("Unexpected input type: %s is not assignable from %s",
                    handlerInterface.getQualifiedSourceName(), interfaceToImplement.getQualifiedSourceName()));
        } else if (interfaceToImplement.equals(handlerInterface)) {
            die(String.format("You must declare an interface that extends the %s type",
                    handlerInterface.getSimpleSourceName()));
        }

        JClassType[] interfaces = interfaceToImplement.getImplementedInterfaces();
        if (interfaces.length != 1) {
            die(String.format("The type %s extends more than one interface",
                    interfaceToImplement.getQualifiedSourceName()));
        }

        JClassType[] parameters = ModelUtils.findParameterizationOf(handlerInterface, interfaceToImplement);
        if (parameters.length != 1) {
            die(String.format("The type %s has unexpected number of type parameters",
                    interfaceToImplement.getQualifiedSourceName()));
        }

        return parameters[0];
    }

    public JClassType getOwnerType() {
        return ownerType;
    }

    public ElementIdStatement[] parseStatements() throws UnableToCompleteException {
        statements.clear();

        statements.add(new ElementIdStatement(
                ElementIdHandlerGenerator.ElementIdHandler_generateAndSetIds_owner,
                getOwnerTypeId()));

        doParse(ownerType, new ArrayList<JClassType>(), ".", getOwnerTypeId());

        return statements.toArray(new ElementIdStatement[0]);
    }

    void doParse(JClassType parentType, List<JClassType> grandParents, String parentFieldExpression,
            String idPrefix) throws UnableToCompleteException {
        for (JClassType type : parentType.getFlattenedSupertypeHierarchy()) {
            for (JField field : type.getFields()) {
                if (!processField(field)) {
                    continue;
                }

                JClassType fieldType = field.getType().isClass();
                String fieldName = field.getName();

                if (grandParents.contains(fieldType)) {
                    die(String.format("Field %s of type %s is already present on path from the owner type %s: %s",
                            fieldName, fieldType.getQualifiedSourceName(), ownerType.getQualifiedSourceName(),
                            grandParents.toString()));
                }

                WithElementId idAnnotation = field.getAnnotation(WithElementId.class);
                String fieldId = idAnnotation.value();
                if ("".equals(fieldId)) {
                    fieldId = fieldName;
                }

                String elementId = idPrefix + ID_ELEMENT_SEPARATOR + fieldId;
                String fieldExpression = ElementIdHandlerGenerator.ElementIdHandler_generateAndSetIds_owner
                        + parentFieldExpression + fieldName;
                ElementIdStatement statement = new ElementIdStatement(fieldExpression, elementId);

                if (statements.contains(statement)) {
                    die(String.format("Duplicate element ID %s for field %s of type %s",
                            elementId, fieldName, fieldType.getQualifiedSourceName()));
                }

                statements.add(statement);

                if (idAnnotation.processType()) {
                    List<JClassType> newGrandParents = new ArrayList<JClassType>(grandParents);
                    newGrandParents.add(fieldType);

                    doParse(fieldType, newGrandParents, parentFieldExpression + fieldName + ".", elementId);
                }
            }
        }
    }

    String getOwnerTypeId() {
        return ownerType.getName().replace(".", ID_ELEMENT_SEPARATOR);
    }

    boolean processField(JField field) {
        return !field.isPrivate() && !field.isStatic()
                && field.getType().isClass() != null
                && field.getAnnotation(WithElementId.class) != null;
    }

    void die(String lastWords) throws UnableToCompleteException {
        logger.log(TreeLogger.ERROR, lastWords);
        throw new UnableToCompleteException();
    }

}
