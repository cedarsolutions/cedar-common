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

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.google.gwt.core.ext.TreeLogger;
import com.google.gwt.core.ext.TreeLogger.HelpInfo;
import com.google.gwt.core.ext.UnableToCompleteException;
import com.google.gwt.core.ext.typeinfo.JClassType;
import com.google.gwt.core.ext.typeinfo.JField;
import com.gwtplatform.idhandler.client.WithElementId;

/**
 * Unit test for {@link ElementIdTypeParser}.
 */
@RunWith(MockitoJUnitRunner.class)
public class ElementIdTypeParserTest {

    @Mock
    private TreeLogger logger;

    @Mock
    private JClassType interfaceType;

    @Mock
    private JClassType ownerType;

    @Mock
    private JField ownerTypeField1;

    @Mock
    private JField ownerTypeField2;

    @Mock
    private JField ownerTypeParentField;

    @Mock
    private JField ownerTypeParentFieldTypeSubField1;

    @Mock
    private JClassType ownerTypeParentFieldTypeSubField1Type;

    @Mock
    private JField ownerTypeParentFieldTypeSubField2;

    @Mock
    private JField field;

    private ElementIdTypeParser tested;

    @Before
    public void setUp() throws UnableToCompleteException {
        when(logger.branch(any(TreeLogger.Type.class), anyString(),
                any(Throwable.class), any(HelpInfo.class))).thenReturn(logger);

        tested = new ElementIdTypeParser(logger, interfaceType) {
            @Override
            JClassType resolveOwnerType(JClassType interfaceToImplement) throws UnableToCompleteException {
                return ownerType;
            }
        };

        stubPassingField(field, mock(JClassType.class), "field");

        JClassType ownerTypeParent = mock(JClassType.class);
        Set<? extends JClassType> ownerTypeFlattenedSupertypeHierarchy = new HashSet<JClassType>(
                Arrays.asList(ownerType, ownerTypeParent));
        doReturn(ownerTypeFlattenedSupertypeHierarchy).when(ownerType).getFlattenedSupertypeHierarchy();
        when(ownerType.getFields()).thenReturn(new JField[] { ownerTypeField1, ownerTypeField2 });
        when(ownerTypeParent.getFields()).thenReturn(new JField[] { ownerTypeParentField });
        when(ownerType.getName()).thenReturn("OwnerTypeName");

        JClassType ownerTypeParentFieldType = mock(JClassType.class, "ownerTypeParentFieldType");
        stubPassingField(ownerTypeField1, mock(JClassType.class), "ownerTypeField1");
        stubPassingField(ownerTypeField2, mock(JClassType.class), "ownerTypeField2");
        stubPassingField(ownerTypeParentField, ownerTypeParentFieldType, "ownerTypeParentField");

        Set<? extends JClassType> ownerTypeParentFieldTypeFlattenedSupertypeHierarchy = new HashSet<JClassType>(
                Arrays.asList(ownerTypeParentFieldType));
        doReturn(ownerTypeParentFieldTypeFlattenedSupertypeHierarchy).when(ownerTypeParentFieldType)
                .getFlattenedSupertypeHierarchy();
        when(ownerTypeParentFieldType.getFields()).thenReturn(new JField[] {
                ownerTypeParentFieldTypeSubField1, ownerTypeParentFieldTypeSubField2 });

        stubPassingField(ownerTypeParentFieldTypeSubField1, ownerTypeParentFieldTypeSubField1Type,
                "ownerTypeParentFieldTypeSubField1");
        stubPassingField(ownerTypeParentFieldTypeSubField2, mock(JClassType.class),
                "ownerTypeParentFieldTypeSubField2");
    }

    void stubPassingField(JField field, JClassType fieldType, String fieldName) {
        WithElementId idAnnotation = mock(WithElementId.class);
        when(field.isPrivate()).thenReturn(false);
        when(field.isStatic()).thenReturn(false);
        when(field.getType()).thenReturn(fieldType);
        when(field.getName()).thenReturn(fieldName);
        when(fieldType.isClass()).thenReturn(fieldType);
        when(field.getAnnotation(WithElementId.class)).thenReturn(idAnnotation);
        when(idAnnotation.value()).thenReturn("");
        when(idAnnotation.processType()).thenReturn(true);
    }

    @Test
    public void processFieldPrivate() {
        when(field.isPrivate()).thenReturn(true);
        verifyProcessFieldReturns(false);
    }

    @Test
    public void processFieldDefaultAccess() {
        when(field.isDefaultAccess()).thenReturn(true);
        verifyProcessFieldReturns(true);
    }

    @Test
    public void processFieldProtected() {
        when(field.isProtected()).thenReturn(true);
        verifyProcessFieldReturns(true);
    }

    @Test
    public void processFieldPublic() {
        when(field.isPublic()).thenReturn(true);
        verifyProcessFieldReturns(true);
    }

    @Test
    public void processFieldStatic() {
        when(field.isStatic()).thenReturn(true);
        verifyProcessFieldReturns(false);
    }

    @Test
    public void processFieldFinal() {
        when(field.isFinal()).thenReturn(true);
        verifyProcessFieldReturns(true);
    }

    @Test
    public void processFieldPrimitiveType() {
        JClassType fieldType = mock(JClassType.class);
        when(field.getType()).thenReturn(fieldType);
        when(fieldType.isClass()).thenReturn(null);
        verifyProcessFieldReturns(false);
    }

    @Test
    public void processFieldMissingIdAnnotation() {
        when(field.getAnnotation(WithElementId.class)).thenReturn(null);
        verifyProcessFieldReturns(false);
    }

    void verifyProcessFieldReturns(boolean expected) {
        boolean processField = tested.processField(field);
        assertThat(processField, is(equalTo(expected)));
    }

    @Test
    public void doParseDefaultBehavior() throws UnableToCompleteException {
        tested.doParse(ownerType, new ArrayList<JClassType>(), ".", "IdPrefix");

        List<ElementIdStatement> expected = Arrays.asList(
                getExpectedStatement("ownerTypeParentField", "IdPrefix_ownerTypeParentField"),
                getExpectedStatement("ownerTypeParentField.ownerTypeParentFieldTypeSubField1",
                        "IdPrefix_ownerTypeParentField_ownerTypeParentFieldTypeSubField1"),
                getExpectedStatement("ownerTypeParentField.ownerTypeParentFieldTypeSubField2",
                        "IdPrefix_ownerTypeParentField_ownerTypeParentFieldTypeSubField2"),
                getExpectedStatement("ownerTypeField1", "IdPrefix_ownerTypeField1"),
                getExpectedStatement("ownerTypeField2", "IdPrefix_ownerTypeField2"));

        assertThat(tested.statements.size(), is(equalTo(expected.size())));
        assertThat(tested.statements.containsAll(expected), is(equalTo(true)));
    }

    @Test
    public void doParseCustomFieldId() throws UnableToCompleteException {
        stubFieldIdAnnotation(ownerTypeParentField, "ownerTypeParentFieldCustomId", true);
        tested.doParse(ownerType, new ArrayList<JClassType>(), ".", "IdPrefix");

        assertThat(tested.statements.contains(getExpectedStatement(
                "ownerTypeParentField", "IdPrefix_ownerTypeParentFieldCustomId")), is(equalTo(true)));
    }

    @Test(expected = UnableToCompleteException.class)
    public void doParseDuplicateFieldIds() throws UnableToCompleteException {
        stubFieldIdAnnotation(ownerTypeParentFieldTypeSubField1, "customId", true);
        stubFieldIdAnnotation(ownerTypeParentFieldTypeSubField2, "customId", true);
        tested.doParse(ownerType, new ArrayList<JClassType>(), ".", "IdPrefix");
    }

    @Test
    public void doParseLimitedFieldTypeRecursion() throws UnableToCompleteException {
        stubFieldIdAnnotation(ownerTypeParentField, "", false);
        tested.doParse(ownerType, new ArrayList<JClassType>(), ".", "IdPrefix");

        List<ElementIdStatement> expected = Arrays.asList(
                getExpectedStatement("ownerTypeParentField", "IdPrefix_ownerTypeParentField"),
                getExpectedStatement("ownerTypeField1", "IdPrefix_ownerTypeField1"),
                getExpectedStatement("ownerTypeField2", "IdPrefix_ownerTypeField2"));

        assertThat(tested.statements.size(), is(equalTo(expected.size())));
        assertThat(tested.statements.containsAll(expected), is(equalTo(true)));
    }

    @Test(expected = UnableToCompleteException.class)
    public void doParseUnhandledFieldTypeRecursion() throws UnableToCompleteException {
        Set<? extends JClassType> ownerTypeParentFieldTypeSubField1TypeFlattenedSupertypeHierarchy =
                new HashSet<JClassType>(Arrays.asList(ownerTypeParentFieldTypeSubField1Type));
        doReturn(ownerTypeParentFieldTypeSubField1TypeFlattenedSupertypeHierarchy)
                .when(ownerTypeParentFieldTypeSubField1Type).getFlattenedSupertypeHierarchy();
        when(ownerTypeParentFieldTypeSubField1Type.getFields()).thenReturn(new JField[] { ownerTypeParentField });

        tested.doParse(ownerType, new ArrayList<JClassType>(), ".", "IdPrefix");
    }

    @Test
    public void doParseHandledFieldTypeRecursion() throws UnableToCompleteException {
        Set<? extends JClassType> ownerTypeParentFieldTypeSubField1TypeFlattenedSupertypeHierarchy =
                new HashSet<JClassType>(Arrays.asList(ownerTypeParentFieldTypeSubField1Type));
        doReturn(ownerTypeParentFieldTypeSubField1TypeFlattenedSupertypeHierarchy)
                .when(ownerTypeParentFieldTypeSubField1Type).getFlattenedSupertypeHierarchy();
        when(ownerTypeParentFieldTypeSubField1Type.getFields()).thenReturn(new JField[] { ownerTypeParentField });

        stubFieldIdAnnotation(ownerTypeParentField, "", false);
        tested.doParse(ownerType, new ArrayList<JClassType>(), ".", "IdPrefix");

        List<ElementIdStatement> expected = Arrays.asList(
                getExpectedStatement("ownerTypeParentField", "IdPrefix_ownerTypeParentField"),
                getExpectedStatement("ownerTypeField1", "IdPrefix_ownerTypeField1"),
                getExpectedStatement("ownerTypeField2", "IdPrefix_ownerTypeField2"));

        assertThat(tested.statements.size(), is(equalTo(expected.size())));
        assertThat(tested.statements.containsAll(expected), is(equalTo(true)));
    }

    @Test
    public void parseStatementsDefaultBehavior() throws UnableToCompleteException {
        tested.parseStatements();

        List<ElementIdStatement> expected = Arrays.asList(
                getExpectedStatement("ownerTypeParentField", "OwnerTypeName_ownerTypeParentField"),
                getExpectedStatement("ownerTypeParentField.ownerTypeParentFieldTypeSubField1",
                        "OwnerTypeName_ownerTypeParentField_ownerTypeParentFieldTypeSubField1"),
                getExpectedStatement("ownerTypeParentField.ownerTypeParentFieldTypeSubField2",
                        "OwnerTypeName_ownerTypeParentField_ownerTypeParentFieldTypeSubField2"),
                getExpectedStatement("ownerTypeField1", "OwnerTypeName_ownerTypeField1"),
                getExpectedStatement("ownerTypeField2", "OwnerTypeName_ownerTypeField2"),
                getExpectedStatement(null, "OwnerTypeName")
                );

        assertThat(tested.statements.size(), is(equalTo(expected.size())));
        assertThat(tested.statements.containsAll(expected), is(equalTo(true)));
    }

    void stubFieldIdAnnotation(JField field, String fieldId, boolean processType) {
        WithElementId idAnnotation = mock(WithElementId.class);
        when(field.getAnnotation(WithElementId.class)).thenReturn(idAnnotation);
        when(idAnnotation.value()).thenReturn(fieldId);
        when(idAnnotation.processType()).thenReturn(processType);
    }

    ElementIdStatement getExpectedStatement(String pathToField, String elementId) {
        String fieldExpression = ElementIdHandlerGenerator.ElementIdHandler_generateAndSetIds_owner;

        if (pathToField != null) {
            fieldExpression += "." + pathToField;
        }

        return new ElementIdStatement(fieldExpression, elementId);
    }

}
