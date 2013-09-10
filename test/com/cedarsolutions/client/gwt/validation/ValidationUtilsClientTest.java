/* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 *
 *              C E D A R
 *          S O L U T I O N S       "Software done right."
 *           S O F T W A R E
 *
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 *
 * Copyright (c) 2013 Kenneth J. Pronovici.
 * All rights reserved.
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the Apache License, Version 2.0.
 * See LICENSE for more information about the licensing terms.
 *
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 *
 * Author   : Kenneth J. Pronovici <pronovic@ieee.org>
 * Language : Java 6
 * Project  : Common Java Functionality
 *
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */
package com.cedarsolutions.client.gwt.validation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.cedarsolutions.client.gwt.junit.ClientTestCase;
import com.cedarsolutions.exception.InvalidDataException;
import com.cedarsolutions.shared.domain.LocalizableMessage;
import com.cedarsolutions.shared.domain.ValidationErrors;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.UIObject;

/**
 * Unit tests for ValidationUtils.
 * @author Kenneth J. Pronovici <pronovic@ieee.org>
 */
public class ValidationUtilsClientTest extends ClientTestCase {

    /** Test functionality to set and clear decorations. */
    public void testDecorations() {
        View view = new View();
        assertEquals("gwt-TextBox", view.field1.getStyleName());
        assertEquals("gwt-TextBox", view.field2.getStyleName());

        LocalizableMessage message1 = new LocalizableMessage("x", "field1", "x");
        LocalizableMessage message2 = new LocalizableMessage("x", "field2", "x");
        LocalizableMessage message3 = new LocalizableMessage("x", "field3", "x");

        assertEquals("gwt-TextBox", view.field1.getStyleName());
        assertEquals("gwt-TextBox", view.field2.getStyleName());

        assertFalse(ValidationUtils.getInstance().decorateFieldWithValidationError(view, message3, "style1"));
        assertEquals("gwt-TextBox", view.field1.getStyleName());
        assertEquals("gwt-TextBox", view.field2.getStyleName());

        assertTrue(ValidationUtils.getInstance().decorateFieldWithValidationError(view, message1, "style1"));
        assertEquals("gwt-TextBox style1", view.field1.getStyleName());
        assertEquals("gwt-TextBox", view.field2.getStyleName());

        assertTrue(ValidationUtils.getInstance().decorateFieldWithValidationError(view, message2, "style2"));
        assertEquals("gwt-TextBox style1", view.field1.getStyleName());
        assertEquals("gwt-TextBox style2", view.field2.getStyleName());

        assertFalse(ValidationUtils.getInstance().decorateFieldWithValidationError(view, message3, "style1"));
        assertEquals("gwt-TextBox style1", view.field1.getStyleName());
        assertEquals("gwt-TextBox style2", view.field2.getStyleName());

        ValidationUtils.getInstance().clearValidationErrors(view, "style1");
        assertEquals("gwt-TextBox", view.field1.getStyleName());
        assertEquals("gwt-TextBox style2", view.field2.getStyleName());

        ValidationUtils.getInstance().clearValidationErrors(view, "style2");
        assertEquals("gwt-TextBox", view.field1.getStyleName());
        assertEquals("gwt-TextBox", view.field2.getStyleName());

        assertTrue(ValidationUtils.getInstance().decorateFieldWithValidationError(view, message1, "style1"));
        assertTrue(ValidationUtils.getInstance().decorateFieldWithValidationError(view, message2, "style1"));
        assertEquals("gwt-TextBox style1", view.field1.getStyleName());
        assertEquals("gwt-TextBox style1", view.field2.getStyleName());

        ValidationUtils.getInstance().clearValidationErrors(view, "style1");
        assertEquals("gwt-TextBox", view.field1.getStyleName());
        assertEquals("gwt-TextBox", view.field2.getStyleName());
    }

    /** Test showValidationError(), null error. */
    public void testShowValidationError1() {
        View view = new View();
        InvalidDataException error = null;
        ValidationUtils.getInstance().showValidationError(view, error, "style");
        assertFalse(view.validationErrorWidget.isVisible());
        assertEquals("", view.validationErrorWidget.getErrorSummary());
        assertTrue(view.validationErrorWidget.getErrorList().isEmpty());
    }

    /** Test showValidationError(), empty error. */
    public void testShowValidationError2() {
        View view = new View();
        InvalidDataException error = new InvalidDataException();
        ValidationUtils.getInstance().showValidationError(view, error, "style");
        assertTrue(view.validationErrorWidget.isVisible());
        assertEquals(ValidationUtils.INTERNAL_ERROR_MESSAGE, view.validationErrorWidget.getErrorSummary());
        assertTrue(view.validationErrorWidget.getErrorList().isEmpty());
    }

    /** Test showValidationError(), with message, no details. */
    public void testShowValidationError3() {
        View view = new View();
        InvalidDataException error = new InvalidDataException("message");
        ValidationUtils.getInstance().showValidationError(view, error, "style");
        assertTrue(view.validationErrorWidget.isVisible());
        assertEquals("message", view.validationErrorWidget.getErrorSummary());
        assertTrue(view.validationErrorWidget.getErrorList().isEmpty());

        view = new View();
        error = new InvalidDataException(new LocalizableMessage("key", "text"));
        ValidationUtils.getInstance().showValidationError(view, error, "style");
        assertTrue(view.validationErrorWidget.isVisible());
        assertEquals("__key", view.validationErrorWidget.getErrorSummary());
        assertTrue(view.validationErrorWidget.getErrorList().isEmpty());
    }

    /** Test showValidationError(), with message, empty details. */
    public void testShowValidationError4() {
        View view = new View();
        ValidationErrors details = new ValidationErrors();
        InvalidDataException error = new InvalidDataException("message", details);
        ValidationUtils.getInstance().showValidationError(view, error, "style");
        assertTrue(view.validationErrorWidget.isVisible());
        assertEquals("message", view.validationErrorWidget.getErrorSummary());
        assertTrue(view.validationErrorWidget.getErrorList().isEmpty());
    }

    /** Test showValidationError(), with message, no summary and unknown field. */
    public void testShowValidationError5() {
        View view = new View();
        ValidationErrors details = new ValidationErrors();
        details.addMessage("detailKey1", "fieldX", "detailText1");
        InvalidDataException error = new InvalidDataException("message", details);
        ValidationUtils.getInstance().showValidationError(view, error, "style");
        assertTrue(view.validationErrorWidget.isVisible());
        assertEquals(1, view.validationErrorWidget.getErrorList().size());
        assertEquals("__detailKey1", view.validationErrorWidget.getErrorList().get(0));
    }

    /** Test showValidationError(), with message, no summary but valid field. */
    public void testShowValidationError6() {
        View view = new View();
        ValidationErrors details = new ValidationErrors();
        details.addMessage("detailKey1", "field1", "detailText1");
        InvalidDataException error = new InvalidDataException("message", details);
        ValidationUtils.getInstance().showValidationError(view, error, "style");
        assertTrue(view.validationErrorWidget.isVisible());
        assertEquals("", view.validationErrorWidget.getErrorSummary());
        assertEquals(1, view.validationErrorWidget.getErrorList().size());
        assertEquals("__detailKey1", view.validationErrorWidget.getErrorList().get(0));
    }

    /** Test showValidationError(), with message, with summary but no fields. */
    public void testShowValidationError7() {
        View view = new View();
        ValidationErrors details = new ValidationErrors();
        details.setSummary(new LocalizableMessage("summaryKey1", "summaryText1"));
        InvalidDataException error = new InvalidDataException("message", details);
        ValidationUtils.getInstance().showValidationError(view, error, "style");
        assertTrue(view.validationErrorWidget.isVisible());
        assertEquals("__summaryKey1", view.validationErrorWidget.getErrorSummary());
        assertTrue(view.validationErrorWidget.getErrorList().isEmpty());

        view = new View();
        details = new ValidationErrors();
        details.setSummary(new LocalizableMessage("", "summaryText1"));
        error = new InvalidDataException("message", details);
        ValidationUtils.getInstance().showValidationError(view, error, "style");
        assertTrue(view.validationErrorWidget.isVisible());
        assertEquals("summaryText1", view.validationErrorWidget.getErrorSummary());
        assertTrue(view.validationErrorWidget.getErrorList().isEmpty());
    }

    /** Test showValidationError(), with summary but unknown field. */
    public void testShowValidationError8() {
        View view = new View();
        ValidationErrors details = new ValidationErrors();
        details.setSummary(new LocalizableMessage("summaryKey1", "summaryText1"));
        details.addMessage("detailKey1", "fieldX", "detailText1");
        InvalidDataException error = new InvalidDataException("message", details);
        ValidationUtils.getInstance().showValidationError(view, error, "style");
        assertTrue(view.validationErrorWidget.isVisible());
        assertEquals("__summaryKey1", view.validationErrorWidget.getErrorSummary());
        assertEquals(1, view.validationErrorWidget.getErrorList().size());
        assertEquals("__detailKey1", view.validationErrorWidget.getErrorList().get(0));    }

    /** Test showValidationError(), with summary and one valid field. */
    public void testShowValidationError9() {
        View view = new View();
        ValidationErrors details = new ValidationErrors();
        details.setSummary(new LocalizableMessage("summaryKey1", "summaryText1"));
        details.addMessage("detailKey1", "field1", "detailText1");
        InvalidDataException error = new InvalidDataException("message", details);
        ValidationUtils.getInstance().showValidationError(view, error, "style");
        assertTrue(view.validationErrorWidget.isVisible());
        assertEquals("__summaryKey1", view.validationErrorWidget.getErrorSummary());
        assertEquals(1, view.validationErrorWidget.getErrorList().size());
        assertEquals("__detailKey1", view.validationErrorWidget.getErrorList().get(0));
    }

    /** Test showValidationError(), with summary and multiple valid fields. */
    public void testShowValidationError10() {
        View view = new View();
        ValidationErrors details = new ValidationErrors();
        details.setSummary(new LocalizableMessage("summaryKey1", "summaryText1"));
        details.addMessage("detailKey1", "field1", "detailText1");
        details.addMessage("detailKey2", "field2", "detailText2");
        InvalidDataException error = new InvalidDataException("message", details);
        ValidationUtils.getInstance().showValidationError(view, error, "style");
        assertTrue(view.validationErrorWidget.isVisible());
        assertEquals("__summaryKey1", view.validationErrorWidget.getErrorSummary());
        assertEquals(2, view.validationErrorWidget.getErrorList().size());
        assertEquals("__detailKey1", view.validationErrorWidget.getErrorList().get(0));
        assertEquals("__detailKey2", view.validationErrorWidget.getErrorList().get(1));
    }

    /** Test showValidationError(), with summary and multiple valid and invalid fields. */
    public void testShowValidationError11() {
        View view = new View();
        ValidationErrors details = new ValidationErrors();
        details.setSummary(new LocalizableMessage("summaryKey1", "summaryText1"));
        details.addMessage("detailKey1", "field1", "detailText1");
        details.addMessage("detailKey2", "field2", "detailText2");
        details.addMessage("detailKey3", "field3", "detailText3");
        InvalidDataException error = new InvalidDataException("message", details);
        ValidationUtils.getInstance().showValidationError(view, error, "style");
        assertTrue(view.validationErrorWidget.isVisible());
        assertEquals("__summaryKey1", view.validationErrorWidget.getErrorSummary());
        assertEquals(3, view.validationErrorWidget.getErrorList().size());
        assertEquals("__detailKey1", view.validationErrorWidget.getErrorList().get(0));
        assertEquals("__detailKey2", view.validationErrorWidget.getErrorList().get(1));
        assertEquals("__detailKey3", view.validationErrorWidget.getErrorList().get(2));
    }

    /** View that we can test with. */
    private static class View implements IViewWithValidation {

        protected ValidationErrorWidget validationErrorWidget = new ValidationErrorWidget();
        protected UIObject field1 = new TextBox();
        protected UIObject field2 = new TextBox();

        @Override
        public IValidationErrorWidget getValidationErrorWidget() {
            return this.validationErrorWidget;
        }

        @Override
        public String translate(LocalizableMessage message) {
            return "__" + message.getKey();  // just enough so we can tell it was called
        }

        @Override
        public Map<String, UIObject> getValidatedFieldMap() {
            Map<String, UIObject> fieldMap = new HashMap<String, UIObject>();
            fieldMap.put("field1", field1);
            fieldMap.put("field2", field2);
            return fieldMap;
        }
    }

    /** Validation error widget we can test with. */
    private static class ValidationErrorWidget implements IValidationErrorWidget {

        private boolean visible;
        private String errorSummary;
        private List<String> errorList = new ArrayList<String>();

        @Override
        public void hide() {
            this.visible = false;
        }

        @Override
        public void show() {
            this.visible = true;
        }

        @Override
        public boolean isVisible() {
            return this.visible;
        }

        @Override
        public void clearErrorSummary() {
            this.errorSummary = "";
        }

        @Override
        public void setErrorSummary(String summary) {
            this.errorSummary = summary;
        }

        @Override
        public String getErrorSummary() {
            return this.errorSummary;
        }

        @Override
        public void clearErrorList() {
            this.errorList.clear();
        }

        @Override
        public void addError(String error) {
            this.errorList.add(error);
        }

        @Override
        public List<String> getErrorList() {
            return this.errorList;
        }
    }
}
