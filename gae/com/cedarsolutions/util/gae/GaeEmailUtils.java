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
package com.cedarsolutions.util.gae;

import static com.cedarsolutions.shared.domain.email.EmailFormat.PLAINTEXT;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.mail.Address;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import com.cedarsolutions.exception.CedarRuntimeException;
import com.cedarsolutions.shared.domain.email.EmailAddress;
import com.cedarsolutions.shared.domain.email.EmailMessage;
import com.cedarsolutions.util.StringUtils;

/**
 * General email utilities.
 *
 * <p>
 * These utilities have been tested with the Google App Engine SMTP server,
 * and not any other SMTP servers.  As a result, I can't promise that the
 * multipart functionality will work properly outside of GAE.  (Based on
 * what I have read, multipart is one of those places where behavior seems
 * to vary a bit by back-end.)
 * </p>
 *
 * <p>
 * Normally, I would impelement utility code like this as static methods.
 * However, it's difficult to mock static method calls, and the methods below
 * are complicated enough that I want that option.  Instead, an instance of
 * this class should be injected into any other class that needs this
 * functionality.  It's safe to treat that instance as a singleton, but you
 * don't have to.
 * </p>
 *
 * @author Kenneth J. Pronovici <pronovic@ieee.org>
 */
public class GaeEmailUtils {

    /** Charset that will be used. */
    public static final String CHARSET = "iso-8859-1";

    /**
     * Send an email message via the Google App Engine API.
     * @param message Message to send
     * @throws CedarRuntimeException If the email cannot be sent.
     */
    public void sendMessage(Message message) {
        try {
            // This method can't really be unit tested, because we can't mock Transport.
            Transport.send(message);
        } catch (Exception e) {
            throw new CedarRuntimeException("Failed to send message: " + e.getMessage(), e);
        }
    }

    /**
     * Create a JavaMail message based on an EmailMessage.
     * @param email  EmailMessage to use as source
     * @return JavaMail message generated based on input EmailMessage.
     */
    public Message createMessage(EmailMessage email) {
        InternetAddress sender = createInternetAddress(email.getSender());
        InternetAddress replyTo = createInternetAddress(email.getReplyTo());

        List<InternetAddress> recipients = new ArrayList<InternetAddress>();
        for (EmailAddress address : email.getRecipients()) {
            InternetAddress recipient = createInternetAddress(address);
            recipients.add(recipient);
        }

        if (email.getFormat() == PLAINTEXT) {
            return createPlaintextMessage(sender, recipients, replyTo, email.getSubject(), email.getPlaintext());
        } else {
            return createMultipartMessage(sender, recipients, replyTo, email.getSubject(), email.getPlaintext(), email.getHtml());
        }
    }

    /**
     * Create an InternetAddress based on an EmailAddress.
     * @return InternetAddress for the passed-in arguments.
     * @throws CedarRuntimeException  If there's a problem creating the address.
     */
    public InternetAddress createInternetAddress(EmailAddress address) {
        try {
            return createInternetAddress(address.getAddress(), address.getName());
        } catch (NullPointerException e) {
            throw new CedarRuntimeException("Unable to create internet address: " + e.getMessage(), e);
        }
    }

    /**
     * Create an InternetAddress.
     * @param emailAddress  Email address
     * @param fullName      Full name
     * @return InternetAddress for the passed-in arguments.
     * @throws CedarRuntimeException  If there's a problem creating the address.
     */
    public InternetAddress createInternetAddress(String emailAddress, String fullName) {
        try {
            if (!StringUtils.isEmpty(emailAddress) && !StringUtils.isEmpty(fullName)) {
                return new InternetAddress(emailAddress, fullName);
            } else if (!StringUtils.isEmpty(emailAddress)) {
                return new InternetAddress(emailAddress);
            } else {
                throw new CedarRuntimeException("Must provide email address; full name is optional.");
            }
        } catch (Exception e) {
            throw new CedarRuntimeException("Unable to create internet address: " + e.getMessage(), e);
        }
    }

    /**
     * Get the plaintext part of an email message.
     * @param message   Message to introspect (must have been originally generated by this class)
     * @return Text part of the message, or null if there is no text part.
     */
    public String getTextPart(Message message) {
        try {
            if (message != null) {
                if (message.getContent() instanceof String) {
                    if ("text/plain".equals(message.getContentType())) {
                        return (String) message.getContent();
                    }
                } else if (message.getContent() instanceof Multipart) {
                    Multipart multipart = (Multipart) message.getContent();
                    if (multipart.getCount() == 2) {
                        MimeBodyPart textPart = (MimeBodyPart) multipart.getBodyPart(0);
                        if (textPart.getContent() instanceof String) {
                            if (StringUtils.startsWith(textPart.getContentType(), "text/plain")) {
                                return (String) textPart.getContent();
                            }
                        }
                    }
                }
            }
        } catch (Exception e) { }

        return null;
    }

    /**
     * Get the HTML part of an email message.
     * @param message   Message to introspect (must have been originally generated by this class)
     * @return HTML part of the message, or null if there is no HTML part.
     */
    public String getHtmlPart(Message message) {
        try {
            if (message != null) {
                if (message.getContent() instanceof Multipart) {
                    Multipart multipart = (Multipart) message.getContent();
                    if (multipart.getCount() == 2) {
                        MimeBodyPart htmlPart = (MimeBodyPart) multipart.getBodyPart(1);
                        if (htmlPart.getContent() instanceof String) {
                            if (StringUtils.startsWith(htmlPart.getContentType(), "text/html")) {
                                return (String) htmlPart.getContent();
                            }
                        }
                    }
                }
            }
        } catch (Exception e) { }

        return null;
    }

    /**
     * Create a plaintext email message.
     * @param sender     Message sender
     * @param recipients Message recipients
     * @param replyTo    Reply to email address, or null
     * @param subject    Message subject
     * @param plaintext  Plaintext message body
     * @return Plaintext message suitable for passing to sendMessage().
     * @throws CedarRuntimeException If the email cannot be sent.
     */
    public Message createPlaintextMessage(InternetAddress sender,
                                          List<InternetAddress> recipients,
                                          InternetAddress replyTo,
                                          String subject, String plaintext) {
        try {
            if (StringUtils.isEmpty(plaintext)) {
                throw new CedarRuntimeException("Must provide plaintext body.");
            }

            Message message = createBasicMessage(sender, recipients, replyTo, subject);
            message.setText(plaintext);
            return message;
        } catch (CedarRuntimeException e) {
            throw e;
        } catch (Exception e) {
            throw new CedarRuntimeException("Failed to create message: " + e.getMessage(), e);
        }
    }

    /**
     * Create a multipart/alternative email message with both plaintext and HTML parts.
     * @param sender     Message sender
     * @param recipients Message recipients
     * @param replyTo    Reply to email address, or null
     * @param subject    Message subject
     * @param plaintext  Plaintext message body
     * @param html       HTML message body, or null
     * @see <a href="http://www.thatsjava.com/java-enterprise/29158/">thatsjava.com</a>
     * @return Multipart/alternative message suitable for passing to sendMessage().
     * @throws CedarRuntimeException If the email cannot be sent.
     */
    public Message createMultipartMessage(InternetAddress sender,
                                          List<InternetAddress> recipients,
                                          InternetAddress replyTo,
                                          String subject, String plaintext, String html) {
        try {
            // Ick!  This is incredibly ugly.  I don't quite understand why there
            // is not an easier way to generate a multipart/alternative message.
            // However, this seems to work, so we'll go with it.

            if (StringUtils.isEmpty(plaintext) || StringUtils.isEmpty(html)) {
                throw new CedarRuntimeException("Must provide HTML and plaintext parts.");
            }

            MimeBodyPart plaintextPart = new MimeBodyPart();
            plaintextPart.setText(plaintext);
            plaintextPart.addHeaderLine("Content-Type: text/plain; charset=\"" + CHARSET + "\"");
            plaintextPart.addHeaderLine("Content-Transfer-Encoding: quoted-printable");

            MimeBodyPart htmlPart = new MimeBodyPart();
            htmlPart.setText(html);
            htmlPart.addHeaderLine("Content-Type: text/html; charset=\"" + CHARSET + "\"");
            htmlPart.addHeaderLine("Content-Transfer-Encoding: quoted-printable");

            Multipart content = new MimeMultipart("alternative");
            content.addBodyPart(plaintextPart);
            content.addBodyPart(htmlPart);

            Message message = createBasicMessage(sender, recipients, replyTo, subject);
            message.setContent(content);
            return message;
        } catch (CedarRuntimeException e) {
            throw e;
        } catch (Exception e) {
            throw new CedarRuntimeException("Failed to create message: " + e.getMessage(), e);
        }
    }

    /**
     * Create a basic email message, including components shared with text and multipart formats.
     * @param sender     Message sender
     * @param recipients Message recipients
     * @param replyTo    Reply to email address, or null
     * @param subject    Message subject
     * @return Basic message that can be added to for other scenarios.
     * @throws MessagingException If there is an underlying JavaMail problem.
     * @throws CedarRuntimeException If arguments are invalid.
     */
    private Message createBasicMessage(InternetAddress sender,
                                       List<InternetAddress> recipients,
                                       InternetAddress replyTo,
                                       String subject) throws MessagingException {

        if (sender == null) {
            throw new CedarRuntimeException("Must provide sender.");
        }

        if (recipients == null || recipients.isEmpty()) {
            throw new CedarRuntimeException("Must provide recipients.");
        }

        if (StringUtils.isEmpty(subject)) {
            throw new CedarRuntimeException("Must provide subject.");
        }

        Session session = Session.getDefaultInstance(new Properties(), null);
        Message message = new MimeMessage(session);
        message.setFrom(sender);
        message.setSubject(subject);

        for (InternetAddress recipient : recipients) {
            message.addRecipient(Message.RecipientType.TO, recipient);
        }

        if (replyTo != null) {
            message.setReplyTo(new Address[] { replyTo, });
        }

        return message;
    }

}
