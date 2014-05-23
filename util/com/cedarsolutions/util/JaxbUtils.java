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
package com.cedarsolutions.util;

import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;

import javax.xml.XMLConstants;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.SchemaOutputResolver;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.ValidationEvent;
import javax.xml.bind.util.ValidationEventCollector;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;

import org.xml.sax.SAXException;

import com.cedarsolutions.exception.CedarRuntimeException;
import com.cedarsolutions.exception.InvalidDataException;
import com.cedarsolutions.shared.domain.ValidationErrors;

/**
 * JAXB utilities that operate on a cached JAXB context.
 *
 * <p>
 * The process of establishing a JAXB context can be pretty slow.  So, we want
 * to avoid having to do it more often than necessary.  This class keeps an
 * internal cache of contexts that have already been established.  It also
 * provides some utility methods to simplify marshalling and unmarshalling.
 * </p>
 *
 * @author Kenneth J. Pronovici <pronovic@ieee.org>
 */
@SuppressWarnings({ "unchecked", "rawtypes" })
public class JaxbUtils {

    /** Validation error key used for XML errors. */
    public static final String ERROR_KEY = "jaxb";

    /** Singleton instance. */
    private static JaxbUtils INSTANCE;

    /** Map from class name to generated JAXB context. */
    private Map<String, JAXBContext> contextMap = new HashMap<String, JAXBContext>();

    /** Default constructor is private so class cannot be instantiated. */
    private JaxbUtils() {
    }

    /** Get an instance of this class to use. */
    public static synchronized JaxbUtils getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new JaxbUtils();
        }

        return INSTANCE;
    }

    /**
     * Get a JAXB context for the indicated class.
     * @param className  Name of the class to get a context for
     * @return JAXB context for the class.
     */
    public JAXBContext getJaxbContext(String className) {
        try {
            Class clazz = Class.forName(className);
            return this.getJaxbContext(clazz);
        } catch (Exception e) {
            throw new CedarRuntimeException("Error obtaining JAXB context: " + e.getMessage(), e);
        }
    }

    /**
     * Get a JAXB context for the indicated class.
     * @param clazz  Class to get a context for.
     * @return JAXB context for the class.
     */
    public synchronized <T> JAXBContext getJaxbContext(Class<T> clazz) {
        if (!this.contextMap.containsKey(clazz.getName())) {
            try {
                JAXBContext context = JAXBContext.newInstance(clazz);
                this.contextMap.put(clazz.getName(), context);
            } catch (Exception e) {
                throw new CedarRuntimeException("Error obtaining JAXB context: " + e.getMessage(), e);
            }
        }

        return this.contextMap.get(clazz.getName());
    }

    /**
     * Marshal an object of type T, creating XML with no schema location.
     * @param <T>  Type of the object
     * @param value  Value to marshal.
     * @return Generated XML.
     */
    public <T> String marshalDocument(T value) {
        return marshalDocument(value, null);
    }

    /**
     * Marshal an object of type T, creating XML.
     * @param <T>  Type of the object
     * @param value  Value to marshal.
     * @param schema Location of the schema to be placed in the XML, or null
     * @return Generated XML.
     */
    public <T> String marshalDocument(T value, String schema) {
        try {
            JAXBContext context = this.getJaxbContext(value.getClass());

            Marshaller marshaller = context.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            marshaller.setProperty(Marshaller.JAXB_FRAGMENT, false);

            if (schema != null) {
                marshaller.setProperty(Marshaller.JAXB_SCHEMA_LOCATION, schema);
            }

            StringWriter writer = new StringWriter();
            marshaller.marshal(value, writer);
            return writer.toString();
        } catch (JAXBException e) {
            throw new CedarRuntimeException("Error marshalling XML: " + e.getMessage(), e);
        }
    }

    /**
     * Unmarshal XML, creating an object of type T.
     * @param <T>  Type of the object
     * @param type Type of the object to unmarshal
     * @param xml XML to use as source
     * @return Object of type T, unmarshalled from the XML.
     */
    public <T> T unmarshalDocument(Class<T> type, String xml) {
        return unmarshalDocument(type, xml, true);
    }

    /**
     * Unmarshal XML, creating an object of type T.
     * @param <T>  Type of the object
     * @param type Type of the object to unmarshal
     * @param xml XML to use as source
     * @param validate  Whether to validate the unmarshalling step
     * @return Object of type T, unmarshalled from the XML.
     * @throws InvalidDataException If the XML could not be parsed or if there are validation errors.
     */
    public <T> T unmarshalDocument(Class<T> type, String xml, boolean validate) {
        try {
            JAXBContext context = this.getJaxbContext(type);

            StringReader reader = new StringReader(xml);
            Source source = new StreamSource(reader);

            if (!validate) {
                Unmarshaller unmarshaller = context.createUnmarshaller();
                JAXBElement<T> element = unmarshaller.unmarshal(source, type);
                return element.getValue();
            } else {
                // If an adapter fails, we don't get an unmarshal exception.
                // Instead, we have to register and interrogate a handler.
                // See also: http://java.net/jira/browse/JAXB-537

                Unmarshaller unmarshaller = context.createUnmarshaller();

                String xsd = this.generateSchema(type);
                SchemaFactory sf = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
                Schema schema = sf.newSchema(new StreamSource(new StringReader(xsd)));

                ValidationEventCollector eventHandler = new ValidationEventCollector();
                unmarshaller.setEventHandler(eventHandler);
                unmarshaller.setSchema(schema);

                JAXBElement<T> element = unmarshaller.unmarshal(source, type);
                if (eventHandler.hasEvents()) {
                    throw generateValidationError(type, eventHandler);
                }

                return element.getValue();
            }
        } catch (JAXBException e) {
            throw translateJaxbUnmarshalError(type, e);
        } catch (SAXException e) {
            InvalidDataException invalid = getUnmarshalError(type, e);
            invalid.getDetails().addMessage(ERROR_KEY, e.getMessage());
            throw invalid;
        }
    }

    /**
     * Generate the XML schema for a JAXB type.
     * @param <T>  Type of the object
     * @param type Type of the object to unmarshal
     */
    public <T> String generateSchema(Class<T> type) {
        try {
            JAXBContext context = this.getJaxbContext(type);
            SchemaResolver resolver = new SchemaResolver();
            context.generateSchema(resolver);
            return resolver.getWriter().toString();
        } catch (IOException e) {
            throw new CedarRuntimeException("Error generating schema: " + e.getMessage(), e);
        }
    }

    /** Get a "raw" invalid data exception for a class, with empty details attached. */
    private static <T> InvalidDataException getUnmarshalError(Class<T> type, Throwable cause) {
        ValidationErrors details = new ValidationErrors(ERROR_KEY, "Error unmarshalling XML for " + type.getSimpleName());
        return new InvalidDataException("Error unmarshalling XML for " + type.getSimpleName(), details);
    }

    /** A resolver used for generating a schema from JAXB. */
    private static class SchemaResolver extends SchemaOutputResolver {
        private StringWriter writer = new StringWriter();

        public StringWriter getWriter() {
            return this.writer;
        }

        @Override
        public Result createOutput(String namespaceUri, String suggestedFileName) throws IOException {
            Result result = new StreamResult(this.writer);
            result.setSystemId("id");
            return result;
        }
    }

    /** Generate a JAXB unmarshal exception due to validation problems. */
    private static <T> InvalidDataException generateValidationError(Class<T> type, ValidationEventCollector eventHandler) {
        InvalidDataException invalid = getUnmarshalError(type, null);

        invalid.getDetails().addMessage(ERROR_KEY, "Found validation errors");
        for (ValidationEvent event : eventHandler.getEvents()) {
            invalid.getDetails().addMessage(ERROR_KEY, event.getMessage());
        }
        return invalid;
    }

    /** Translate a JAXB unmarshal exception into something legible. */
    private static <T> InvalidDataException translateJaxbUnmarshalError(Class<T> type, JAXBException e) {
        boolean added = false;
        InvalidDataException invalid = getUnmarshalError(type, e);

        // This was developed through trial-and-error.  I don't know how well it will hold up in the future.
        if (e.getCause() != null && e.getLinkedException() != null) {
            try {
                throw e.getLinkedException();
            } catch (org.xml.sax.SAXParseException sax) {
                String message = "Line " + sax.getLineNumber() + ", column " + sax.getColumnNumber() + ": " + e.getCause().getMessage();
                invalid.getDetails().addMessage(ERROR_KEY, message);
                added = true;
            } catch (Throwable other) { }
        }

        if (!added && e.getCause() != null && e.getCause().getMessage() != null) {
            invalid.getDetails().addMessage(ERROR_KEY, e.getCause().getMessage());
            added = true;
        }

        if (!added && e.getMessage() != null) {
            invalid.getDetails().addMessage(ERROR_KEY, e.getMessage());
            added = true;
        }

        return invalid;
    }

}
