/* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 *
 *              C E D A R
 *          S O L U T I O N S       "Software done right."
 *           S O F T W A R E
 *
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 *
 * Copyright (c) 2013-2014 Kenneth J. Pronovici.
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
package com.cedarsolutions.junit.gwt.classloader;

import static org.mockito.Mockito.mock;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import com.google.gwt.i18n.client.Constants;
import com.google.gwt.i18n.client.Constants.DefaultBooleanValue;
import com.google.gwt.i18n.client.Constants.DefaultDoubleValue;
import com.google.gwt.i18n.client.Constants.DefaultFloatValue;
import com.google.gwt.i18n.client.Constants.DefaultIntValue;
import com.google.gwt.i18n.client.Constants.DefaultStringArrayValue;
import com.google.gwt.i18n.client.Constants.DefaultStringMapValue;
import com.google.gwt.i18n.client.Constants.DefaultStringValue;
import com.google.gwt.i18n.client.ConstantsWithLookup;
import com.google.gwt.i18n.client.Messages;
import com.google.gwt.i18n.client.Messages.DefaultMessage;

/**
 * Creates mocked versions of objects for stubbed GWT client tests.
 *
 * <p>
 * This class creates all of its resources as Mockito mocks.  There is also
 * some additional logic layered on top to handle certain particular types of
 * interfaces and classes, like Constants or Messages.
 * </p>
 *
 * <p>
 * This class was derived in part from source code in gwt-test-utils.  See
 * README.credits for more information.
 * </p>
 *
 * <p>
 * This whole thing has sort of a hack-ish feel.  There's no simple way to
 * emulate GWT's behavior, and I can basically only make it work by trial-
 * and-error.
 * </p>
 *
 * @author Kenneth J. Pronovici <pronovic@ieee.org>
 */
@SuppressWarnings("unchecked")
public class GwtResourceCreator {

    /** Create a mocked resource for a particular class. */
    public static synchronized <T> T create(Class<?> clazz) {
        if (ConstantsWithLookup.class.isAssignableFrom(clazz)) {
            // ConstantsWithLookup extends Constants, so do this first
            return (T) mock(clazz, new ConstantsWithLookupAnswer(clazz));
        } else if (Constants.class.isAssignableFrom(clazz)) {
            return (T) mock(clazz, new ConstantsAnswer());
        } else if (Messages.class.isAssignableFrom(clazz)) {
            return (T) mock(clazz, new MessagesAnswer());
        } else {
            return (T) mock(clazz);
        }
    }

    /** Uses reflection to return proper values for mocks that implement ConstantsWithLookup. */
    public static class ConstantsWithLookupAnswer implements Answer<Object> {
        private Class<?> clazz;  // there's no other way to get the class that's actually being mocked

        public ConstantsWithLookupAnswer(Class<?> clazz) {
            this.clazz = clazz;
        }

        @Override
        public Object answer(InvocationOnMock invocation) throws Throwable {
            return getConstantsWithLookupAnswer(clazz, invocation);
        }
    }

    /** Uses reflection to return proper values for mocks that implement Constants. */
    public static class ConstantsAnswer implements Answer<Object> {
        @Override
        public Object answer(InvocationOnMock invocation) throws Throwable {
            return getConstantsAnswer(invocation, invocation.getMethod());
        }
    }

    /** Uses reflection to return proper values for mocks that implement Messages. */
    public static class MessagesAnswer implements Answer<Object> {
        @Override
        public Object answer(InvocationOnMock invocation) throws Throwable {
            return getMessagesAnswer(invocation);
        }
    }

    /** Generate a result for MessagesAnswer. */
    private static Object getMessagesAnswer(InvocationOnMock invocation) throws Throwable {
        Object answer = Mockito.RETURNS_DEFAULTS.answer(invocation);

        for (Annotation annotation : invocation.getMethod().getDeclaredAnnotations()) {
            if (annotation instanceof DefaultMessage) {
                String message = ((DefaultMessage) annotation).value();
                return substituteReplaceVariables(message, invocation.getArguments());
            }
        }

        return answer;
    }

    /** Generate a result for ConstantsWithLookupAnswer, which behaves similar to Constants except with possible indirection. */
    private static Object getConstantsWithLookupAnswer(Class<?> clazz, InvocationOnMock invocation) throws NoSuchMethodException, Throwable {
        if ("getBoolean".equals(invocation.getMethod().getName())) {
            return getConstantsAnswer(invocation, clazz.getMethod((String) invocation.getArguments()[0]));
        } else if ("getDouble".equals(invocation.getMethod().getName())) {
            return getConstantsAnswer(invocation, clazz.getMethod((String) invocation.getArguments()[0]));
        } else if ("getFloat".equals(invocation.getMethod().getName())) {
            return getConstantsAnswer(invocation, clazz.getMethod((String) invocation.getArguments()[0]));
        } else if ("getInt".equals(invocation.getMethod().getName())) {
            return getConstantsAnswer(invocation, clazz.getMethod((String) invocation.getArguments()[0]));
        } else if ("getString".equals(invocation.getMethod().getName())) {
            return getConstantsAnswer(invocation, clazz.getMethod((String) invocation.getArguments()[0]));
        } else if ("getStringArray".equals(invocation.getMethod().getName())) {
            return getConstantsAnswer(invocation, clazz.getMethod((String) invocation.getArguments()[0]));
        } else if ("getMap".equals(invocation.getMethod().getName())) {
            return getConstantsAnswer(invocation, clazz.getMethod((String) invocation.getArguments()[0]));
        } else {
            return getConstantsAnswer(invocation, invocation.getMethod());
        }
    }

    /** Generate a result for ConstantsAnswer. */
    private static Object getConstantsAnswer(InvocationOnMock invocation, Method method) throws Throwable {
        Object answer = Mockito.RETURNS_DEFAULTS.answer(invocation);

        for (Annotation annotation : method.getDeclaredAnnotations()) {
            if (annotation instanceof DefaultBooleanValue) {
                answer = ((DefaultBooleanValue) annotation).value();
            } else if (annotation instanceof DefaultDoubleValue) {
                answer = ((DefaultDoubleValue) annotation).value();
            } else if (annotation instanceof DefaultFloatValue) {
                answer = ((DefaultFloatValue) annotation).value();
            } else if (annotation instanceof DefaultIntValue) {
                answer = ((DefaultIntValue) annotation).value();
            } else if (annotation instanceof DefaultStringArrayValue) {
                answer = ((DefaultStringArrayValue) annotation).value();
            } else if (annotation instanceof DefaultStringMapValue) {
                answer = parseDefaultStringMapValue(((DefaultStringMapValue) annotation).value());
            } else if (annotation instanceof DefaultStringValue) {
                answer = ((DefaultStringValue) annotation).value();
            }
        }

        return answer;
    }

    /** Parse a value as from DefaultStringMapValue. */
    protected static Map<String, String> parseDefaultStringMapValue(String[] values) {
        Map<String, String> map = new HashMap<String, String>();

        if (values != null && values.length > 0 && values.length % 2 == 0) {
            for (int i = 0; i < values.length; i += 2) {
                map.put(values[i], values[i + 1]);
            }
        }

        return map;
    }

    /**
     * Substitute/replace parameters in a message.
     * @param message    Message to subsitute/replace in
     * @param arguments  List of arguments to take parameters from
     * @throws IllegalArgumentException  The parameter list doesn't match up with the arguments.
     */
    protected static String substituteReplaceVariables(String message, Object[] arguments) throws IllegalArgumentException {
        // Pre-validate so that we get out quickly
        if (message == null || arguments == null) {
            throw new IllegalArgumentException("Internal error replacing variables: null input");
        }

        // Make a list of all the argument parameters {0}, {1}, etc.
        List<Integer> argumentParameters = new ArrayList<Integer>();
        for (int i = 0; i < arguments.length; i++) {
            argumentParameters.add(i);
        }

        // Find all of the parameters in the message, like {0}, {1}, etc.
        Pattern pattern = Pattern.compile("(\\{)([0-9]+)(\\})");
        List<Integer> messageParameters = new ArrayList<Integer>();
        Matcher matcher = pattern.matcher(message);
        while (matcher.find()) {
            int messageParameter = Integer.parseInt(matcher.group(2));
            messageParameters.add(messageParameter);
        }

        // Check that every message parameter has a corresponding argument parameter
        for (Integer argumentParameter : argumentParameters) {
            if (!messageParameters.contains(argumentParameter)) {
                throw new IllegalArgumentException("Required argument " + argumentParameter + " not present: " + message);
            }
        }

        // Check that every argument parameter has a corresponding message parameter
        for (Integer messageParameter : messageParameters) {
            if (!argumentParameters.contains(messageParameter)) {
                throw new IllegalArgumentException("Argument " + messageParameter + " beyond range of arguments: " + message);
            }
        }

        // Now that we know the parameters are valid, replace them with real values.
        String result = message;
        if (arguments.length > 0) {
            for (int i = 0; i < arguments.length; i++) {
                Object argument = arguments[i];
                String value = argument == null ? "null" : String.valueOf(argument);
                String variable = "{" + i + "}";
                result = result.replace(variable, value);
            }
        }

        // Return the result, which is always non-null by this point
        return result;
    }

}
