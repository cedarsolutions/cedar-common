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
package com.cedarsolutions.shared.domain.email;

import java.util.ArrayList;
import java.util.List;

import com.cedarsolutions.shared.domain.TranslatableDomainObject;
import com.flipthebird.gwthashcodeequals.EqualsBuilder;
import com.flipthebird.gwthashcodeequals.HashCodeBuilder;

/**
 * Generic representation of an email message.
 * @author Kenneth J. Pronovici <pronovic@ieee.org>
 */
public class EmailMessage extends TranslatableDomainObject {

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

    /** Subject. */
    private String subject;

    /** Plaintext body. */
    private String plaintext;

    /** HTML body. */
    private String html;

    /** Default constructor. */
    public EmailMessage() {
    }

    /** String representation of the email, showing the plaintext part. */
    @Override
    public String toString() {
        StringBuffer buffer = new StringBuffer();

        buffer.append("From: ");
        if (this.sender != null) {
            buffer.append(this.sender);
        }
        buffer.append("\n");

        buffer.append("To: ");
        if (this.recipients != null) {
            for (EmailAddress recipient : this.recipients) {
                buffer.append(recipient);
                buffer.append(", ");
            }
        }
        buffer.append("\n");

        buffer.append("Subject: ");
        if (this.subject != null) {
            buffer.append(this.subject);
        }
        buffer.append("\n");

        if (this.replyTo != null) {
            buffer.append("Reply-To: ");
            buffer.append(this.replyTo);
            buffer.append("\n");
        }

        if (this.plaintext != null) {
            buffer.append("\n");
            buffer.append(this.plaintext);
        }

        buffer.append("\n");

        return buffer.toString();
    }

    /** Compare this object to another object. */
    @Override
    public boolean equals(Object obj) {
        EmailMessage other = (EmailMessage) obj;
        return new EqualsBuilder()
                    .append(this.format, other.format)
                    .append(this.sender, other.sender)
                    .append(this.replyTo, other.replyTo)
                    .append(this.recipients, other.recipients)
                    .append(this.subject, other.subject)
                    .append(this.plaintext, other.plaintext)
                    .append(this.html, other.html)
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
                    .append(this.subject)
                    .append(this.plaintext)
                    .append(this.html)
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

    public String getSubject() {
        return this.subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getPlaintext() {
        return this.plaintext;
    }

    public void setPlaintext(String plaintext) {
        this.plaintext = plaintext;
    }

    public String getHtml() {
        return this.html;
    }

    public void setHtml(String html) {
        this.html = html;
    }

}
