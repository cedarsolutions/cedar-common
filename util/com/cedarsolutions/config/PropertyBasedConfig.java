/* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 *
 *              C E D A R
 *          S O L U T I O N S       "Software done right."
 *           S O F T W A R E
 *
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 *
 * Copyright (c) 2011-2012 Kenneth J. Pronovici.
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
package com.cedarsolutions.config;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.InitializingBean;

import com.cedarsolutions.exception.NotConfiguredException;
import com.cedarsolutions.shared.domain.email.EmailFormat;
import com.cedarsolutions.util.StringUtils;

/**
 * Property-based configuration object.
 * @author Kenneth J. Pronovici <pronovic@ieee.org>
 */
public abstract class PropertyBasedConfig implements InitializingBean {

    /** Properties used to feed configuration. */
    private Properties properties;

    /** Indicates whether this class has been configured. */
    protected boolean isConfigured() {
        return this.properties != null;
    }

    /** String representation of this object. */
    @Override
    public String toString() {
        Class<? extends PropertyBasedConfig> clazz = this.getClass();
        Map<String, Object> fields = this.getFields();
        int maxKeyLength = getMaxKeyLength(fields);

        StringBuffer buffer = new StringBuffer();
        buffer.append("\n   " + clazz.getSimpleName() + ":\n");
        for (String key : fields.keySet()) {
            buffer.append("      ");
            buffer.append(key);
            for (int i = 0; i < maxKeyLength - key.length(); i++) {
                buffer.append(".");
            }
            buffer.append(": ");
            buffer.append(fields.get(key));
            buffer.append("\n");
        }

        return buffer.toString();
    }

    /**
     * Parse a required string from configuration.
     * @param property  Property name
     * @return Configuration value parsed from configuration.
     * @throws NotConfiguredException If there is a parsing error.
     */
    protected String parseRequiredString(String property) throws NotConfiguredException {
        String value = this.properties.getProperty(property);
        if (value == null) {
            throw new NotConfiguredException("Property " + property + " is not set.");
        }

        if (StringUtils.isEmpty(value)) {
            throw new NotConfiguredException("Property " + property + " is empty.");
        }

        return value;
    }

    /**
     * Parse an optional string from configuration.
     * @param property      Property name
     * @param defaultValue  Default value
     * @return Configuration value parsed from configuration.
     * @throws NotConfiguredException If there is a parsing error.
     */
    protected String parseOptionalString(String property, String defaultValue) throws NotConfiguredException {
        String value = this.properties.getProperty(property);
        return StringUtils.isEmpty(value) ? defaultValue : value;
    }

    /**
     * Parse a required string list from configuration.
     * @param property  Name of property
     * @return Configuration value parsed from configuration.
     * @throws NotConfiguredException If there is a parsing error.
     */
    protected List<String> parseRequiredStringList(String property) throws NotConfiguredException {
        String value = this.properties.getProperty(property);
        if (value == null) {
            throw new NotConfiguredException("Property " + property + " is not set.");
        }

        return splitString(value, ",");
    }

    /**
     * Parse a required, non-empty string list from configuration.
     * @param property  Name of property
     * @return Configuration value parsed from configuration.
     * @throws NotConfiguredException If there is a parsing error.
     */
    protected List<String> parseRequiredNonEmptyStringList(String property) throws NotConfiguredException {
        List<String> result = parseRequiredStringList(property);

        if (result.isEmpty()) {
            throw new NotConfiguredException("Property " + property + " is empty.");
        }

        return result;
    }

    /**
     * Parse an optional string list from configuration.
     * @param property      Name of property
     * @param defaultValue  Default value
     * @return Configuration value parsed from configuration.
     * @throws NotConfiguredException If there is a parsing error.
     */
    protected List<String> parseOptionalStringList(String property, List<String> defaultValue) throws NotConfiguredException {
        String value = this.properties.getProperty(property);
        if (value == null) {
            return new ArrayList<String>(defaultValue);
        } else {
            List<String> list = splitString(value, ",");
            if (list.isEmpty()) {
                return new ArrayList<String>(defaultValue);
            } else {
                return list;
            }
        }
    }

    /**
     * Parse a required integer from configuration.
     * @param property  Name of property
     * @return Configuration value parsed from configuration.
     * @throws NotConfiguredException If there is a parsing error.
     */
    protected int parseRequiredInteger(String property) throws NotConfiguredException  {
        String value = this.properties.getProperty(property);
        if (value == null) {
            throw new NotConfiguredException("Property " + property + " is not set.");
        } else {
            try {
                return Integer.parseInt(value);
            } catch (NumberFormatException e) {
                throw new NotConfiguredException("Value for property " + property + " is invalid: " + value);
            }
        }
    }

    /**
     * Parse an optional integer from configuration.
     * @param property      Name of property
     * @param defaultValue  Default value
     * @return Configuration value parsed from configuration.
     * @throws NotConfiguredException If there is a parsing error.
     */
    protected int parseOptionalInteger(String property, int defaultValue) throws NotConfiguredException  {
        String value = this.properties.getProperty(property);
        if (StringUtils.isEmpty(value)) {
            return defaultValue;
        } else {
            try {
                return Integer.parseInt(value);
            } catch (NumberFormatException e) {
                throw new NotConfiguredException("Value for property " + property + " is invalid: " + value);
            }
        }
    }

    /**
     * Parse a required boolean from configuration.
     * @param property  Name of property
     * @return Configuration value parsed from configuration.
     * @throws NotConfiguredException If there is a parsing error.
     */
    protected boolean parseRequiredBoolean(String property) throws NotConfiguredException  {
        String value = this.properties.getProperty(property);
        if (value == null) {
            throw new NotConfiguredException("Property " + property + " is not set.");
        } else {
            if ("true".equals(value)) {
                return true;
            } else if ("false".equals(value)) {
                return false;
            } else {
                throw new NotConfiguredException("Value for property " + property + " is invalid: " + value);
            }
        }
    }

    /**
     * Parse an optional boolean from configuration.
     * @param property      Name of property
     * @param defaultValue  Default value
     * @return Configuration value parsed from configuration.
     * @throws NotConfiguredException If there is a parsing error.
     */
    protected boolean parseOptionalBoolean(String property, boolean defaultValue) throws NotConfiguredException  {
        String value = this.properties.getProperty(property);
        if (StringUtils.isEmpty(value)) {
            return defaultValue;
        } else {
            if ("true".equals(value)) {
                return true;
            } else if ("false".equals(value)) {
                return false;
            } else {
                throw new NotConfiguredException("Value for property " + property + " is invalid: " + value);
            }
        }
    }

    /**
     * Parse a required email format from configuration.
     * @param property  Name of property
     * @return Email format value.
     * @throws NotConfiguredException If there is a parsing error.
     */
    protected EmailFormat parseRequiredEmailFormat(String property) throws NotConfiguredException  {
        String value = this.properties.getProperty(property);
        if (value == null) {
            throw new NotConfiguredException("Property " + property + " is not set.");
        } else {
            return parseEmailFormat(value);
        }
    }

    /**
     * Parse an optional email format from configuration.
     * @param property      Name of property
     * @param defaultValue  Default value
     * @return Email format value.
     * @throws NotConfiguredException If there is a parsing error.
     */
    protected EmailFormat parseOptionalEmailFormat(String property, EmailFormat defaultValue) throws NotConfiguredException  {
        String value = this.properties.getProperty(property);
        if (StringUtils.isEmpty(value)) {
            return defaultValue;
        } else {
            return parseEmailFormat(value);
        }
    }

    /**
     * Split a string based on a regular expression.
     * @return List of strings, always null but possibly empty.
     */
    protected static List<String> splitString(String string, String expr) {
        List<String> list = new ArrayList<String>();

        string = StringUtils.trim(string);
        if (!StringUtils.isEmpty(string)) {
            if (StringUtils.isEmpty(expr)) {
                list.add(string);
            } else {
                for (String element : string.split(expr)) {
                    String value = StringUtils.trimToNull(element);
                    if (value != null) {
                        list.add(value);
                    }
                }
            }
        }

        return list;
    }

    /**
     * Parse an email format string and turn it into an EmailFormat enumeration.
     * @param value  String value to parse
     * @return EmailFormat parsed from the string value.
     * @throws NotConfiguredException If the value cannot be parsed.
     */
    protected static EmailFormat parseEmailFormat(String value) {
        if ("PLAINTEXT".equals(value)) {
            return EmailFormat.PLAINTEXT;
        } else if ("MULTIPART".equals(value)) {
            return EmailFormat.MULTIPART;
        } else {
            throw new NotConfiguredException("Email format " + value + " is not valid.");
        }
    }

    /** Get a map of all fields on this object, excluding fields we don't want to report on. */
    @SuppressWarnings("unchecked")
    private Map<String, Object> getFields() {
        try {
            Map<String, Object> fields = (Map<String, Object>) BeanUtils.describe(this);
            fields.remove("properties");
            fields.remove("class");
            return fields;
        } catch (Exception e) {
            return new HashMap<String, Object>();
        }
    }

    /** Get the maximum length of any key in the map. */
    private static int getMaxKeyLength(Map<String, Object> map) {
        int max = 0;

        for (String key : map.keySet()) {
            if (key.length() > max) {
                max = key.length();
            }
        }

        return max;
    }

    public Properties getProperties() {
        return properties;
    }

    public void setProperties(Properties properties) {
        this.properties = properties;
    }

}
