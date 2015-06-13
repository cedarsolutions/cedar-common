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

import java.io.PrintWriter;

import com.google.gwt.core.ext.Generator;
import com.google.gwt.core.ext.GeneratorContext;
import com.google.gwt.core.ext.TreeLogger;
import com.google.gwt.core.ext.UnableToCompleteException;
import com.google.gwt.core.ext.typeinfo.JClassType;
import com.google.gwt.core.ext.typeinfo.TypeOracle;
import com.google.gwt.user.rebind.ClassSourceFileComposerFactory;
import com.google.gwt.user.rebind.SourceWriter;
import com.gwtplatform.idhandler.client.BaseElementIdHandler;

/**
 * GWT deferred binding generator that provides {@link com.gwtplatform.idhandler.client.ElementIdHandler
 * ElementIdHandler} implementations.
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
 * @see com.gwtplatform.idhandler.client.ElementIdHandler ElementIdHandler
 */
public class ElementIdHandlerGenerator extends Generator {

    static final String ElementIdHandler_generateAndSetIds_owner = "owner";

    @Override
    public String generate(TreeLogger logger, GeneratorContext context,
            String typeName) throws UnableToCompleteException {
        TypeOracle oracle = context.getTypeOracle();
        JClassType toGenerate = oracle.findType(typeName).isInterface();
        if (toGenerate == null) {
            logger.log(TreeLogger.ERROR, typeName + " is not an interface type");
            throw new UnableToCompleteException();
        }

        ElementIdTypeParser parser = new ElementIdTypeParser(logger, toGenerate);
        ElementIdStatement[] statements = parser.parseStatements();

        String packageName = toGenerate.getPackage().getName();
        String simpleSourceName = toGenerate.getName().replace('.', '_')
                + "Impl";
        PrintWriter pw = context.tryCreate(logger, packageName,
                simpleSourceName);
        if (pw == null) {
            return packageName + "." + simpleSourceName;
        }

        JClassType superclass = oracle.findType(
                BaseElementIdHandler.class.getName()).isClass();
        assert superclass != null : "No BaseElementIdHandler type";

        ClassSourceFileComposerFactory factory = new ClassSourceFileComposerFactory(
                packageName, simpleSourceName);
        factory.setSuperclass(superclass.getQualifiedSourceName() + "<"
                + parser.getOwnerType().getParameterizedQualifiedSourceName()
                + ">");
        factory.addImplementedInterface(typeName);

        SourceWriter sw = factory.createSourceWriter(context, pw);
        writeGenerateAndSetIds(sw, parser.getOwnerType(), statements);
        sw.commit(logger);

        return factory.getCreatedClassName();
    }

    void writeGenerateAndSetIds(SourceWriter sw, JClassType ownerType,
            ElementIdStatement[] statements) {
        sw.println("@Override public void generateAndSetIds(%s %s) {",
                ownerType.getQualifiedSourceName(),
                ElementIdHandler_generateAndSetIds_owner);
        sw.indent();

        for (ElementIdStatement st : statements) {
            sw.println(String.format("if (%s) %s;", st.buildGuardCondition(),
                    st.buildIdSetterStatement()));
        }

        sw.outdent();
        sw.println("}");
    }

}
