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
package com.cedarsolutions.server.service.impl;

import static com.cedarsolutions.shared.domain.email.EmailComponent.HTML;
import static com.cedarsolutions.shared.domain.email.EmailComponent.PLAINTEXT;
import static com.cedarsolutions.shared.domain.email.EmailComponent.SUBJECT;

import java.util.Map;

import javax.mail.Message;

import org.apache.log4j.Logger;
import org.apache.velocity.tools.generic.DateTool;

import com.cedarsolutions.exception.NotConfiguredException;
import com.cedarsolutions.server.service.IEmailService;
import com.cedarsolutions.server.service.ITemplateService;
import com.cedarsolutions.shared.domain.email.EmailMessage;
import com.cedarsolutions.shared.domain.email.EmailTemplate;
import com.cedarsolutions.util.LoggingUtils;
import com.cedarsolutions.util.gae.GaeEmailUtils;

/**
 * Email service for use with Google App Engine.
 * @author Kenneth J. Pronovici <pronovic@ieee.org>
 */
public class GaeEmailService extends AbstractService implements IEmailService {

    /** Logger instance. */
    private static Logger LOGGER = LoggingUtils.getLogger(GaeEmailService.class);

    /** GAE email utilities. */
    private GaeEmailUtils gaeEmailUtils;

    /** Template service. */
    private ITemplateService templateService;

    /**
     * Invoked by a bean factory after it has set all bean properties.
     * @throws NotConfiguredException In the event of misconfiguration.
     */
    @Override
    public void afterPropertiesSet() throws NotConfiguredException {
        super.afterPropertiesSet();
        if (this.gaeEmailUtils == null || this.templateService == null) {
            throw new NotConfiguredException("GaeEmailService is not properly configured.");
        }
    }

    /**
     * Send an email message via the GAE container infrastructure.
     * @param email   Email message to send
     */
    @Override
    public void sendEmail(EmailMessage email) {
        Message message = gaeEmailUtils.createMessage(email);
        gaeEmailUtils.sendMessage(message);
        LOGGER.debug("Sent email:\n" + email);
    }

    /**
     * Send an email message via the GAE container infrastructure.
     * @param template  Email message template to send
     */
    @Override
    public void sendEmail(EmailTemplate template) {
        EmailMessage email = generateEmail(template);
        this.sendEmail(email);
    }

    /**
     * Generate an email message from an email template.
     * @param template  Email message template to use as source
     * @return Email message that was generated from the template.
     */
    @Override
    public EmailMessage generateEmail(EmailTemplate template) {
        String group = template.getTemplateGroup();
        String name = template.getTemplateName();
        Map<String, Object> context = template.getTemplateContext();

        String subject = this.templateService.renderTemplate(group, name, SUBJECT, context);
        String plaintext = null;
        String html = null;

        switch (template.getFormat()) {
        case PLAINTEXT:
            plaintext = this.templateService.renderTemplate(group, name, PLAINTEXT, context);
            html = null;
            break;
        case MULTIPART:
            plaintext = this.templateService.renderTemplate(group, name, PLAINTEXT, context);
            html = this.templateService.renderTemplate(group, name, HTML, context);
            break;
        }

        EmailMessage email = new EmailMessage();
        email.setFormat(template.getFormat());
        email.setSender(template.getSender());
        email.setReplyTo(template.getReplyTo());
        email.setRecipients(template.getRecipients());
        email.setSubject(subject);
        email.setPlaintext(plaintext);
        email.setHtml(html);

        return email;
    }

    public GaeEmailUtils getGaeEmailUtils() {
        return this.gaeEmailUtils;
    }

    public void setGaeEmailUtils(GaeEmailUtils gaeEmailUtils) {
        this.gaeEmailUtils = gaeEmailUtils;
    }

    public ITemplateService getTemplateService() {
        return this.templateService;
    }

    public void setTemplateService(ITemplateService templateService) {
        this.templateService = templateService;
    }

}
