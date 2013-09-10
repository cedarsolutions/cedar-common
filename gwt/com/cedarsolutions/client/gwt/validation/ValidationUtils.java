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

import java.util.HashMap;
import java.util.Map;

import com.cedarsolutions.exception.InvalidDataException;
import com.cedarsolutions.shared.domain.LocalizableMessage;
import com.cedarsolutions.util.gwt.GwtStringUtils;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.UIObject;

/**
 * Validation-related utilities.
 *
 * <p>
 * Unlike a lot of other utility classes, which typically provide static
 * methods, this class is a singleton.
 * </p>
 *
 * <p>
 * It's difficult to mock static method calls, but I really want that option
 * for some of these methods.  Normally, I would fall back on dependency
 * injection to solve this problem.  However, it's not always possible to
 * inject an instance of this class into all of the places where it needs to be
 * used.  Making the class a singleton works around that problem.
 * </p>
 *
 * <p>
 * For testing, the stubbed client test framework is wired into the
 * GWT.create() call within getInstance().  The framework "automagically"
 * generates a mocked version of this class for use by unit tests.  However,
 * keep in mind that even the mock object is a singleton, so you must remember
 * to reset the mock between test cases.
 * </p>
 *
 * @author Kenneth J. Pronovici <pronovic@ieee.org>
 */
public class ValidationUtils {

    /** Message displayed as validation summary when nothing else can be found. */
    public static final String INTERNAL_ERROR_MESSAGE = "Internal error: no details provided";

    /** Empty validated field map, used if a view does not want to identify specific fields in validation. */
    public static final Map<String, UIObject> NO_SPECIFIC_FIELDS = new HashMap<String, UIObject>();

    /** Singleton instance. */
    private static ValidationUtils INSTANCE;

    /** Default constructor is private so class cannot be instantiated. */
    private ValidationUtils() {
    }

    /** Get an instance of this class to use. */
    public static synchronized ValidationUtils getInstance() {
        if (INSTANCE == null) {
            INSTANCE = GWT.create(ValidationUtils.class);
        }

        return INSTANCE;
    }

    /**
     * Show a validation error on a view that supports it.
     * @param view  View to operate on
     * @param error Validation error to show
     * @param style Name of the style applied to fields with errors
     */
    public void showValidationError(IViewWithValidation view, InvalidDataException error, String style) {
        this.clearValidationErrors(view, style);

        if (error != null) {
            boolean hasValidationData = false;

            // If there's a summary, set it
            String summary = getSummary(view, error);
            if (!GwtStringUtils.isEmpty(summary)) {
                view.getValidationErrorWidget().setErrorSummary(summary);
                hasValidationData = true;
            }

            // The context will be used to decorate the field associated with
            // a given message.  However, we will display every message, even
            // if we can't find the field associated with the message.  That
            // way, we don't lose important information (like, "hey, the developer
            // forgot to provide a field to enter that required data").
            if (error.getDetails() != null && error.getDetails().hasMessages()) {
                for (LocalizableMessage message : error.getDetails().getMessages()) {
                    String translated = message.isTranslatable() ? view.translate(message) : message.getText();
                    if (!GwtStringUtils.isEmpty(translated)) {
                        view.getValidationErrorWidget().addError(translated);
                        this.decorateFieldWithValidationError(view, message, style);
                        hasValidationData = true;
                    }
                }
            }

            // If there's neither summary nor details, try to take the message from the
            // exception itself.  If there isn't even a useful message on the exception, then
            // give up and show an internal error.  The goal here is to make sure that
            // *something* legible is shown if an error is handled.  Otherwise, it looks
            // like nothing happened, and the user won't notice the failure.
            if (!hasValidationData) {
                String message = null;

                if (error.getLocalizableMessage().isTranslatable()) {
                    message = view.translate(error.getLocalizableMessage());
                } else {
                    message = error.getLocalizableMessage().getText();
                }

                if (!GwtStringUtils.isEmpty(message)) {
                    view.getValidationErrorWidget().setErrorSummary(message);
                } else {
                    view.getValidationErrorWidget().setErrorSummary(INTERNAL_ERROR_MESSAGE);
                }
            }

            // Always show the widget
            view.getValidationErrorWidget().show();
        }
    }

    /**
     * Clear error decorations from all fields and hide any validation errors.
     * @param view  View to operate on
     * @param style Name of the style applied to fields with errors
     */
    public void clearValidationErrors(IViewWithValidation view, String style) {
        view.getValidationErrorWidget().clearErrorList();
        view.getValidationErrorWidget().clearErrorSummary();
        view.getValidationErrorWidget().hide();
        for (UIObject field : view.getValidatedFieldMap().values()) {
            field.removeStyleName(style);
        }
    }

    /**
     * Decorate a field that has a validation error.
     * @param view    View to operate on
     * @param message Localizable message whose context identifies the field
     * @param style   Name of the style applied to fields with errors
     * @return True if the field was found, false otherwise.
     */
    public boolean decorateFieldWithValidationError(IViewWithValidation view, LocalizableMessage message, String style) {
        Map<String, UIObject> map = view.getValidatedFieldMap();
        if (message.getContext() != null && map.containsKey(message.getContext())) {
            UIObject field = map.get(message.getContext());
            field.addStyleName(style);
            return true;
        } else {
            return false;
        }
    }

    /** Safely get the summary for a validation error. */
    private static String getSummary(IViewWithValidation view, InvalidDataException error) {
        if (error != null && error.getDetails() != null && error.getDetails().getSummary() != null) {
            if (error.getDetails().getSummary().isTranslatable()) {
                return view.translate(error.getDetails().getSummary());
            } else {
                return error.getDetails().getSummary().getText();
            }
        }

        return null;
    }

}
