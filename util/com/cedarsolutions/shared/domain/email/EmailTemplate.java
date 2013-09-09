/* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 *
 *              C E D A R
 *          S O L U T I O N S       "Software done right."
 *           S O F T W A R E
 *
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 *
 * Copyright (c) 2011-2013 Kenneth J. Pronovici.
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
package com.cedarsolutions.shared.domain.email;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.cedarsolutions.shared.domain.TranslatableDomainObject;
import com.flipthebird.gwthashcodeequals.EqualsBuilder;
import com.flipthebird.gwthashcodeequals.HashCodeBuilder;

/**
 * Email message with content based on templates.
 * @author Kenneth J. Pronovici <pronovic@ieee.org>
 */
public class EmailTemplate extends TranslatableDomainObject {

    /** Serialization version number, which can be important to the GAE back-end. */
    private static final long serialVersionUID = 1L;

    /** Email format. */
    private EmailFormat format;

    /** Sender email address. */
    private EmailAddress sender;

    /** Reply-to email address. */
    private EmailAddress replyTo;

    /** List of recipients. */
    private List<EmailAddress> recipients;

    /** Template group. */
    private String templateGroup;

    /** Template name. */
    private String templateName;

    /** Template context. */
    private Map<String, Object> templateContext;

    /** Default constructor. */
    public EmailTemplate() {
    }

    /** Compare this object to another object. */
    @Override
    public boolean equals(Object obj) {
        EmailTemplate other = (EmailTemplate) obj;
        return new EqualsBuilder()
                    .append(this.format, other.format)
                    .append(this.sender, other.sender)
                    .append(this.replyTo, other.replyTo)
                    .append(this.recipients, other.recipients)
                    .append(this.templateGroup, other.templateGroup)
                    .append(this.templateName, other.templateName)
                    .append(this.templateContext, other.templateContext)
                    .isEquals();
    }

    /** Generate a hash code for this object. */
    @Override
    public int hashCode() {
        return new HashCodeBuilder()
                    .append(this.format)
                    .append(this.sender)
                    .append(this.replyTo)
                    .append(this.recipients)
                    .append(this.templateGroup)
                    .append(this.templateName)
                    .append(this.templateContext)
                    .toHashCode();
    }

    public EmailFormat getFormat() {
        return this.format;
    }

    public void setFormat(EmailFormat format) {
        this.format = format;
    }

    public EmailAddress getSender() {
        return this.sender;
    }

    public void setSender(EmailAddress sender) {
        this.sender = sender;
    }

    public EmailAddress getReplyTo() {
        return this.replyTo;
    }

    public void setReplyTo(EmailAddress replyTo) {
        this.replyTo = replyTo;
    }

    public List<EmailAddress> getRecipients() {
        return this.recipients;
    }

    public void setRecipients(List<EmailAddress> recipients) {
        this.recipients = recipients;
    }

    public void setRecipients(EmailAddress ... recipients) {
        if (recipients == null) {
            this.recipients = null;
        } else {
            this.recipients = new ArrayList<EmailAddress>();
            if (recipients.length > 0) {
                for (EmailAddress recipient : recipients) {
                    this.recipients.add(recipient);
                }
            }
        }
    }

    public String getTemplateGroup() {
        return this.templateGroup;
    }

    public void setTemplateGroup(String templateGroup) {
        this.templateGroup = templateGroup;
    }

    public String getTemplateName() {
        return this.templateName;
    }

    public void setTemplateName(String templateName) {
        this.templateName = templateName;
    }

    public Map<String, Object> getTemplateContext() {
        return this.templateContext;
    }

    public void setTemplateContext(Map<String, Object> context) {
        this.templateContext = context;
    }

}
