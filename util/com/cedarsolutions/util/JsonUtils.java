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
package com.cedarsolutions.util;

import java.lang.reflect.Type;
import java.util.Date;

import com.cedarsolutions.exception.CedarRuntimeException;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;


/**
 * Utilities related to the JSON serialization format using the GSON library.
 *
 * <p>
 * Note that this implementation has problems deserializing generic collections.
 * You have to jump through some hoops to make it work, and I've never had the
 * patience to figure out how.  For back-end work (i.e. the GAE data store), you're
 * probably better off using a JAXB binding rather than JSON.
 * </p>
 *
 * @see <a href="http://sites.google.com/site/gson/gson-user-guide">GSON User Guide</a>
 * @see <a href="http://stackoverflow.com/questions/3874193/gson-serialization-of-date-field-in-ms-wcf-compatible-form">Stack Overflow</a>
 * @author Kenneth J. Pronovici <pronovic@ieee.org>
 */
public class JsonUtils {

    /**
     * Get the JSON representation for an object.
     * @param obj  Object to serialize
     * @return Serialized representation of the object.
     */
    public static String getJsonString(Object obj) {
        return getGson().toJson(obj);
    }

    /**
     * Parse a JSON representation, returning an instance of a class.
     * @param jsonString  JSON string to parse
     * @param clazz       Class that the string is expected to serialize
     * @return Instance of the class deserialized from the JSON string.
     */
    public static <T> T parseJsonString(String jsonString, Class<T> clazz) {
        return getGson().fromJson(jsonString, clazz);
    }

    /**
     * Parse a JSON representation, returning an instance of a class.
     * @param jsonString  JSON string to parse
     * @param className   Name of the class that the string is expected to serialize
     * @return Instance of the class deserialized from the JSON string.
     */
    public static Object parseJsonString(String jsonString, String className) {
        try {
            return getGson().fromJson(jsonString, Class.forName(className));
        } catch (ClassNotFoundException e) {
            throw new CedarRuntimeException("Could not find class: " + className);
        }
    }

    /** Get a properly configured Gson object for use in other methods. */
    private static Gson getGson() {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(Date.class, new GsonDateSerializer());
        return gsonBuilder.create();
    }

    /** JSON date serializer. */
    private static class GsonDateSerializer implements JsonSerializer<Date>, JsonDeserializer<Date> {
        private String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss.SSS Z";

        @Override
        public JsonElement serialize(Date date, Type typeOfT, JsonSerializationContext context) {
            return new JsonPrimitive(DateUtils.formatDate(date, DATE_FORMAT));
        }

        @Override
        public synchronized Date deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
            return DateUtils.parseDate(json.getAsString(), DATE_FORMAT);
        }
    }

}

