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
package com.cedarsolutions.server.service;

import java.util.Map;

/**
 * Provides template rendering functionality.
 * @author Kenneth J. Pronovici <pronovic@ieee.org>
 */
public interface ITemplateService {

    /**
     * Render an identified template.
     * @param group          Template group, like "notifications"
     * @param name           Template name, like "register"
     * @param component      Template component, like "subject.vm"
     * @param context        Context to use when rendering template
     * @return Rendered template, as a String.
     */
    String renderTemplate(String group, String name, String component, Map<String, Object> context);

}
