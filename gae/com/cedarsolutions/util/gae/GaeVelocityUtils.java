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
package com.cedarsolutions.util.gae;

import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.runtime.RuntimeConstants;

import com.cedarsolutions.exception.CedarRuntimeException;
import com.cedarsolutions.util.LoggingUtils;
import com.cedarsolutions.util.StringUtils;

/**
 * Utility class to manage access to the Velocity engine.
 * @see <a href="http://jvdkamp.wordpress.com/2010/02/12/combining-gae-apache-velocity-and-jquery/">Combining GAE, Apache Velocity and JQuery</a>
 * @author Kenneth J. Pronovici <pronovic@ieee.org>
 */
public class GaeVelocityUtils {

    /** Logger instance. */
    private static Logger LOGGER = LoggingUtils.getLogger(GaeVelocityUtils.class);

    /** Singleton instance of this class. */
    private static GaeVelocityUtils INSTANCE;

    /** Map of VelocityEngine to template directory. */
    private Map<String, VelocityEngine> engines;

    /** Default constructor is private so class cannot be instantiated. */
    private GaeVelocityUtils() {
        this.engines = new HashMap<String, VelocityEngine>();
    }

    /** Get an instance of the class. */
    public static synchronized GaeVelocityUtils getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new GaeVelocityUtils();
        }

        return INSTANCE;
    }

    /**
     * Render a named template using a passed-in context.
     * @param templateDir   Template directory
     * @param template      Name of the template
     * @param context       Velocity context as a map
     * @return Template rendered as a string.
     * @throws CedarRuntimeException If the template cannot be rendered.
     */
    public String renderTemplate(String templateDir, String template, Map<String, Object> context) {
        return renderTemplate(templateDir, template, createVelocityContext(context));
    }

    /**
     * Render a named template using a passed-in context.
     * @param templateDir   Template directory
     * @param template      Name of the template
     * @param context       Velocity context
     * @return Template rendered as a string.
     * @throws CedarRuntimeException If the template cannot be rendered.
     */
    public String renderTemplate(String templateDir, String template, VelocityContext context) {
        StringWriter writer = new StringWriter();
        getTemplate(templateDir, template).merge(context, writer);
        String result = writer.toString();
        return StringUtils.rtrim(result);
    }

    /**
     * Get a named template from Velocity.
     * @param templateDir   Template directory, for instance DEFAULT_TEMPLATE_DIR
     * @param template      Name of the template
     * @return Template retrieved from Velocity
     * @throws CedarRuntimeException If the template cannot be retrieved.
     */
    public Template getTemplate(String templateDir, String template) {
        try {
            return getEngine(templateDir).getTemplate(template);
        } catch (Exception e) {
            throw new CedarRuntimeException("Failed to find template [" + template + "] in [" + templateDir + "]: " + e.getMessage(), e);
        }
    }

    /**
     * Create a VelocityContext based on an input map.
     * @param map  Input map to use as source of context
     * @return VelocityContext created based on input map.
     */
    public VelocityContext createVelocityContext(Map<String, Object> map) {
        VelocityContext context = new VelocityContext();

        if (map != null) {
            for (String key : map.keySet()) {
                context.put(key, map.get(key));
            }
        }

        return context;
    }

    /**
     * Get a singleton reference to the Velocity engine.
     *
     * <p>
     * Within GAE, we probably never have a "true" singleton, because the application
     * might be spread among multiple engines.  However, at least this lets us minimize
     * the number of times we initialize Velocity, which is a fairly slow process.
     * </p>
     *
     * @param templateDir   Template directory
     * @return Velocity engine initialized to read the template directory.
     */
    private VelocityEngine getEngine(String templateDir) {
        if (!this.engines.containsKey(templateDir)) {
            try {
                VelocityEngine engine = new VelocityEngine();
                engine.setProperty("resource.loader", "file");
                engine.setProperty("file.resource.loader.class", GaeVelocityResourceLoader.class.getCanonicalName());
                engine.setProperty("file.resource.loader.path", templateDir);
                engine.setProperty(RuntimeConstants.RUNTIME_LOG_LOGSYSTEM_CLASS, "org.apache.velocity.runtime.log.Log4JLogChute");
                engine.setProperty("runtime.log.logsystem.log4j.logger", LOGGER.getName());
                engine.setProperty("runtime.references.strict", "true");
                engine.init();
                this.engines.put(templateDir, engine);
            } catch (Exception e) {
                throw new CedarRuntimeException("Failed to initialize Velocity engine: " + e.getMessage(), e);
            }
        }

        return this.engines.get(templateDir);
    }

}
