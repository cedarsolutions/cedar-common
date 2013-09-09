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

import com.cedarsolutions.shared.domain.email.EmailMessage;
import com.cedarsolutions.shared.domain.email.EmailTemplate;

/**
 * Provides email functionality.
 * @author Kenneth J. Pronovici <pronovic@ieee.org>
 */
public interface IEmailService {

    /**
     * Send an email message via the container infrastructure.
     * @param message   Email message to send
     */
    void sendEmail(EmailMessage message);

    /**
     * Send an email message via the container infrastructure.
     * @param template  Email message template to send
     */
    void sendEmail(EmailTemplate template);

    /**
     * Generate an email message from an email template.
     * @param template  Email message template to use as source
     * @return Email message that was generated from the template.
     */
    EmailMessage generateEmail(EmailTemplate template);

}
