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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.List;

import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage.RecipientType;

import org.junit.Test;

import com.cedarsolutions.exception.CedarRuntimeException;
import com.cedarsolutions.shared.domain.email.EmailAddress;
import com.cedarsolutions.shared.domain.email.EmailFormat;
import com.cedarsolutions.shared.domain.email.EmailMessage;

/**
 * Unit tests for new GaeEmailUtils().
 * @author Kenneth J. Pronovici <pronovic@ieee.org>
 */
public class GaeEmailUtilsTest {

    /** Test createInternetAddress(). */
    @Test public void testCreateInternetAddress() {
        InternetAddress result = null;

        try {
            new GaeEmailUtils().createInternetAddress(null);
            fail("Expected CedarRuntimeException");
        } catch (CedarRuntimeException e) { }

        try {
            new GaeEmailUtils().createInternetAddress(null, null);
            fail("Expected CedarRuntimeException");
        } catch (CedarRuntimeException e) { }

        try {
            new GaeEmailUtils().createInternetAddress("", null);
            fail("Expected CedarRuntimeException");
        } catch (CedarRuntimeException e) { }

        result = new GaeEmailUtils().createInternetAddress("someone@example.com", null);
        assertNotNull(result);
        assertEquals("someone@example.com", result.getAddress());
        assertNull(result.getPersonal());

        result = new GaeEmailUtils().createInternetAddress("someone@example.com", "");
        assertNotNull(result);
        assertEquals("someone@example.com", result.getAddress());
        assertNull(result.getPersonal());

        result = new GaeEmailUtils().createInternetAddress("someone@example.com", "Someone Important");
        assertNotNull(result);
        assertEquals("someone@example.com", result.getAddress());
        assertEquals("Someone Important", result.getPersonal());

        result = new GaeEmailUtils().createInternetAddress(new EmailAddress("Someone Important", "someone@example.com"));
        assertNotNull(result);
        assertEquals("someone@example.com", result.getAddress());
        assertEquals("Someone Important", result.getPersonal());
    }

    /** Test createPlaintextMessage() with no sender. */
    @Test public void testCreatePlaintextMessageNoSender() {
        try {
            InternetAddress sender = null;
            InternetAddress recipient = new GaeEmailUtils().createInternetAddress("recipient@example.com", "Example Recipient");
            List<InternetAddress> recipients = new ArrayList<InternetAddress>();
            recipients.add(recipient);
            InternetAddress replyTo = null;
            String subject = "Subject";
            String plaintext = "Plaintext";
            new GaeEmailUtils().createPlaintextMessage(sender, recipients, replyTo, subject, plaintext);
            fail("Expected CedarRuntimeException");
        } catch (CedarRuntimeException e) { }
    }

    /** Test createPlaintextMessage() with no recipient list. */
    @Test public void testCreatePlaintextMessageNoRecipientList() {
        try {
            InternetAddress sender = new GaeEmailUtils().createInternetAddress("sender@example.com", "Example Sender");
            List<InternetAddress> recipients = null;
            InternetAddress replyTo = null;
            String subject = "Subject";
            String plaintext = "Plaintext";
            new GaeEmailUtils().createPlaintextMessage(sender, recipients, replyTo, subject, plaintext);
            fail("Expected CedarRuntimeException");
        } catch (CedarRuntimeException e) { }
    }

    /** Test createPlaintextMessage() with empty recipient list. */
    @Test public void testCreatePlaintextMessageEmptyRecipientList() {
        try {
            InternetAddress sender = new GaeEmailUtils().createInternetAddress("sender@example.com", "Example Sender");
            List<InternetAddress> recipients = new ArrayList<InternetAddress>();
            InternetAddress replyTo = null;
            String subject = "Subject";
            String plaintext = "Plaintext";
            new GaeEmailUtils().createPlaintextMessage(sender, recipients, replyTo, subject, plaintext);
            fail("Expected CedarRuntimeException");
        } catch (CedarRuntimeException e) { }
    }

    /** Test createPlaintextMessage() with no subject. */
    @Test public void testCreatePlaintextMessageNoSubject() {
        try {
            InternetAddress sender = new GaeEmailUtils().createInternetAddress("sender@example.com", "Example Sender");
            InternetAddress recipient = new GaeEmailUtils().createInternetAddress("recipient@example.com", "Example Recipient");
            List<InternetAddress> recipients = new ArrayList<InternetAddress>();
            recipients.add(recipient);
            InternetAddress replyTo = null;
            String subject = null;
            String plaintext = "Plaintext";
            new GaeEmailUtils().createPlaintextMessage(sender, recipients, replyTo, subject, plaintext);
            fail("Expected CedarRuntimeException");
        } catch (CedarRuntimeException e) { }
    }

    /** Test createPlaintextMessage() with no plaintext. */
    @Test public void testCreatePlaintextMessageNoBody() {
        try {
            InternetAddress sender = new GaeEmailUtils().createInternetAddress("sender@example.com", "Example Sender");
            InternetAddress recipient = new GaeEmailUtils().createInternetAddress("recipient@example.com", "Example Recipient");
            List<InternetAddress> recipients = new ArrayList<InternetAddress>();
            recipients.add(recipient);
            InternetAddress replyTo = null;
            String subject = "Subject";
            String plaintext = null;
            new GaeEmailUtils().createPlaintextMessage(sender, recipients, replyTo, subject, plaintext);
            fail("Expected CedarRuntimeException");
        } catch (CedarRuntimeException e) { }
    }

    /** Test createPlaintextMessage() with valid arguments, no reply-to. */
    @Test public void testCreatePlaintextMessageValid() throws Exception {
        InternetAddress sender = new GaeEmailUtils().createInternetAddress("sender@example.com", "Example Sender");
        InternetAddress recipient = new GaeEmailUtils().createInternetAddress("recipient@example.com", "Example Recipient");
        List<InternetAddress> recipients = new ArrayList<InternetAddress>();
        recipients.add(recipient);
        InternetAddress replyTo = null;
        String subject = "Subject";
        String plaintext = "Plaintext";
        Message message = new GaeEmailUtils().createPlaintextMessage(sender, recipients, replyTo, subject, plaintext);
        assertNotNull(message);
        assertEquals(1, message.getReplyTo().length);
        assertEquals(sender, message.getReplyTo()[0]);
        assertEquals(1, message.getFrom().length);
        assertEquals(sender, message.getFrom()[0]);
        assertNull(message.getRecipients(RecipientType.CC));
        assertNull(message.getRecipients(RecipientType.BCC));
        assertEquals(1, message.getRecipients(RecipientType.TO).length);
        assertEquals(recipient, message.getRecipients(RecipientType.TO)[0]);
        assertEquals("text/plain", message.getContentType());
        assertEquals(plaintext, message.getContent());
    }

    /** Test createPlaintextMessage() with valid arguments, with reply-to. */
    @Test public void testCreatePlaintextMessageValidReplyTo() throws Exception {
        InternetAddress sender = new GaeEmailUtils().createInternetAddress("sender@example.com", "Example Sender");
        InternetAddress recipient = new GaeEmailUtils().createInternetAddress("recipient@example.com", "Example Recipient");
        InternetAddress replyTo = new GaeEmailUtils().createInternetAddress("reply@example.com", "Example Reply");
        List<InternetAddress> recipients = new ArrayList<InternetAddress>();
        recipients.add(recipient);
        String subject = "Subject";
        String plaintext = "Plaintext";
        Message message = new GaeEmailUtils().createPlaintextMessage(sender, recipients, replyTo, subject, plaintext);
        assertNotNull(message);
        assertEquals(1, message.getReplyTo().length);
        assertEquals(replyTo, message.getReplyTo()[0]);
        assertEquals(1, message.getFrom().length);
        assertEquals(sender, message.getFrom()[0]);
        assertNull(message.getRecipients(RecipientType.CC));
        assertNull(message.getRecipients(RecipientType.BCC));
        assertEquals(1, message.getRecipients(RecipientType.TO).length);
        assertEquals(recipient, message.getRecipients(RecipientType.TO)[0]);
        assertEquals("text/plain", message.getContentType());
        assertEquals(plaintext, message.getContent());
        assertEquals(plaintext, new GaeEmailUtils().getTextPart(message));
        assertEquals(null, new GaeEmailUtils().getHtmlPart(message));
    }

    /** Test createMultipartMessage() with no sender. */
    @Test public void testCreateMultipartMessageNoSender() {
        try {
            InternetAddress sender = null;
            InternetAddress recipient = new GaeEmailUtils().createInternetAddress("recipient@example.com", "Example Recipient");
            List<InternetAddress> recipients = new ArrayList<InternetAddress>();
            recipients.add(recipient);
            InternetAddress replyTo = null;
            String subject = "Subject";
            String plaintext = "Plaintext";
            String html = "<html><head><title>Title</title></head><body><p>Hello, world.</p></body></html>";
            new GaeEmailUtils().createMultipartMessage(sender, recipients, replyTo, subject, plaintext, html);
            fail("Expected CedarRuntimeException");
        } catch (CedarRuntimeException e) { }
    }

    /** Test createMultipartMessage() with no recipient. */
    @Test public void testCreateMultipartMessageNoRecipient() {
        try {
            InternetAddress sender = new GaeEmailUtils().createInternetAddress("sender@example.com", "Example Sender");
            List<InternetAddress> recipients = null;
            InternetAddress replyTo = null;
            String subject = "Subject";
            String plaintext = "Plaintext";
            String html = "<html><head><title>Title</title></head><body><p>Hello, world.</p></body></html>";
            new GaeEmailUtils().createMultipartMessage(sender, recipients, replyTo, subject, plaintext, html);
            fail("Expected CedarRuntimeException");
        } catch (CedarRuntimeException e) { }
    }

    /** Test createMultipartMessage() with no subject. */
    @Test public void testCreateMultipartMessageNoSubject() {
        try {
            InternetAddress sender = new GaeEmailUtils().createInternetAddress("sender@example.com", "Example Sender");
            InternetAddress recipient = new GaeEmailUtils().createInternetAddress("recipient@example.com", "Example Recipient");
            List<InternetAddress> recipients = new ArrayList<InternetAddress>();
            recipients.add(recipient);
            InternetAddress replyTo = null;
            String subject = null;
            String plaintext = "Plaintext";
            String html = "<html><head><title>Title</title></head><body><p>Hello, world.</p></body></html>";
            new GaeEmailUtils().createMultipartMessage(sender, recipients, replyTo, subject, plaintext, html);
            fail("Expected CedarRuntimeException");
        } catch (CedarRuntimeException e) { }
    }

    /** Test createMultipartMessage() with no plaintext or HTML body. */
    @Test public void testCreateMultipartMessageNoBody() {
        try {
            InternetAddress sender = new GaeEmailUtils().createInternetAddress("sender@example.com", "Example Sender");
            InternetAddress recipient = new GaeEmailUtils().createInternetAddress("recipient@example.com", "Example Recipient");
            List<InternetAddress> recipients = new ArrayList<InternetAddress>();
            recipients.add(recipient);
            InternetAddress replyTo = null;
            String subject = "Subject";
            String plaintext = null;
            String html = null;
            new GaeEmailUtils().createMultipartMessage(sender, recipients, replyTo, subject, plaintext, html);
            fail("Expected CedarRuntimeException");
        } catch (CedarRuntimeException e) { }
    }

    /** Test createMultipartMessage() with only an HTML body. */
    @Test public void testCreateMultipartMessageOnlyHtmlBody() {
        try {
            InternetAddress sender = new GaeEmailUtils().createInternetAddress("sender@example.com", "Example Sender");
            InternetAddress recipient = new GaeEmailUtils().createInternetAddress("recipient@example.com", "Example Recipient");
            List<InternetAddress> recipients = new ArrayList<InternetAddress>();
            recipients.add(recipient);
            InternetAddress replyTo = null;
            String subject = "Subject";
            String plaintext = null;
            String html = "<html><head><title>Title</title></head><body><p>Hello, world.</p></body></html>";
            new GaeEmailUtils().createMultipartMessage(sender, recipients, replyTo, subject, plaintext, html);
            fail("Expected CedarRuntimeException");
        } catch (CedarRuntimeException e) { }
    }

    /** Test createMultipartMessage() with only a plaintext body. */
    @Test public void testCreateMultipartMessageOnlyPlaintextBody() throws Exception {
        try {
            InternetAddress sender = new GaeEmailUtils().createInternetAddress("sender@example.com", "Example Sender");
            InternetAddress recipient = new GaeEmailUtils().createInternetAddress("recipient@example.com", "Example Recipient");
            List<InternetAddress> recipients = new ArrayList<InternetAddress>();
            recipients.add(recipient);
            InternetAddress replyTo = null;
            String subject = "Subject";
            String plaintext = "Plaintext";
            String html = null;
            new GaeEmailUtils().createMultipartMessage(sender, recipients, replyTo, subject, plaintext, html);
            fail("Expected CedarRuntimeException");
        } catch (CedarRuntimeException e) { }
    }

    /** Test createMultipartMessage() with valid arguments, no reply-to. */
    @Test public void testCreateMultipartMessageValid() throws Exception {
        InternetAddress sender = new GaeEmailUtils().createInternetAddress("sender@example.com", "Example Sender");
        InternetAddress recipient = new GaeEmailUtils().createInternetAddress("recipient@example.com", "Example Recipient");
        List<InternetAddress> recipients = new ArrayList<InternetAddress>();
        recipients.add(recipient);
        InternetAddress replyTo = null;
        String subject = "Subject";
        String plaintext = "Plaintext";
        String html = "<p>Hello, world.</p>";
        Message message = new GaeEmailUtils().createMultipartMessage(sender, recipients, replyTo, subject, plaintext, html);
        assertNotNull(message);
        assertEquals(1, message.getReplyTo().length);
        assertEquals(sender, message.getReplyTo()[0]);
        assertEquals(1, message.getFrom().length);
        assertEquals(sender, message.getFrom()[0]);
        assertNull(message.getRecipients(RecipientType.CC));
        assertNull(message.getRecipients(RecipientType.BCC));
        assertEquals(1, message.getRecipients(RecipientType.TO).length);
        assertEquals(recipient, message.getRecipients(RecipientType.TO)[0]);
        assertTrue(message.getContent() instanceof Multipart);
        Multipart multipart = (Multipart) message.getContent();
        assertEquals(2, multipart.getCount());
        assertEquals("text/plain; charset=\"iso-8859-1\"", multipart.getBodyPart(0).getContentType());
        assertEquals(plaintext, multipart.getBodyPart(0).getContent());
        assertEquals(plaintext, new GaeEmailUtils().getTextPart(message));
        assertEquals("text/html; charset=\"iso-8859-1\"", multipart.getBodyPart(1).getContentType());  // seems like this ought to be text/html ???
        assertEquals(html, multipart.getBodyPart(1).getContent());
        assertEquals(html, new GaeEmailUtils().getHtmlPart(message));
    }

    /** Test createMultipartMessage() with valid arguments, with a reply-to. */
    @Test public void testCreateMultipartMessageValidReplyTo() throws Exception {
        InternetAddress sender = new GaeEmailUtils().createInternetAddress("sender@example.com", "Example Sender");
        InternetAddress recipient = new GaeEmailUtils().createInternetAddress("recipient@example.com", "Example Recipient");
        InternetAddress replyTo = new GaeEmailUtils().createInternetAddress("reply@example.com", "Example Reply");
        List<InternetAddress> recipients = new ArrayList<InternetAddress>();
        recipients.add(recipient);
        String subject = "Subject";
        String plaintext = "Plaintext";
        String html = "<p>Hello, world.</p>";
        Message message = new GaeEmailUtils().createMultipartMessage(sender, recipients, replyTo, subject, plaintext, html);
        assertNotNull(message);
        assertEquals(1, message.getReplyTo().length);
        assertEquals(replyTo, message.getReplyTo()[0]);
        assertEquals(1, message.getFrom().length);
        assertEquals(sender, message.getFrom()[0]);
        assertNull(message.getRecipients(RecipientType.CC));
        assertNull(message.getRecipients(RecipientType.BCC));
        assertEquals(1, message.getRecipients(RecipientType.TO).length);
        assertEquals(recipient, message.getRecipients(RecipientType.TO)[0]);
        assertTrue(message.getContent() instanceof Multipart);
        Multipart multipart = (Multipart) message.getContent();
        assertEquals(2, multipart.getCount());
        assertEquals("text/plain; charset=\"iso-8859-1\"", multipart.getBodyPart(0).getContentType());
        assertEquals(plaintext, multipart.getBodyPart(0).getContent());
        assertEquals(plaintext, new GaeEmailUtils().getTextPart(message));
        assertEquals("text/html; charset=\"iso-8859-1\"", multipart.getBodyPart(1).getContentType());  // seems like this ought to be text/html ???
        assertEquals(html, multipart.getBodyPart(1).getContent());
        assertEquals(html, new GaeEmailUtils().getHtmlPart(message));
    }

    /** Test createMessage() for a plaintext message. */
    @Test public void testCreateMessagePlaintext() throws Exception {
        String subject = "Subject";
        String plaintext = "Plaintext";

        EmailMessage email = new EmailMessage();
        email.setFormat(EmailFormat.PLAINTEXT);
        email.setSender(new EmailAddress("Example Sender", "sender@example.com"));
        email.setRecipients(new EmailAddress("Example Recipient", "recipient@example.com"));
        email.setReplyTo(new EmailAddress("Example Reply", "reply@example.com"));
        email.setSubject(subject);
        email.setPlaintext(plaintext);

        InternetAddress sender = new GaeEmailUtils().createInternetAddress("sender@example.com", "Example Sender");
        InternetAddress recipient = new GaeEmailUtils().createInternetAddress("recipient@example.com", "Example Recipient");
        InternetAddress replyTo = new GaeEmailUtils().createInternetAddress("reply@example.com", "Example Reply");
        List<InternetAddress> recipients = new ArrayList<InternetAddress>();
        recipients.add(recipient);

        Message message = new GaeEmailUtils().createMessage(email);
        assertNotNull(message);
        assertEquals(1, message.getReplyTo().length);
        assertEquals(replyTo, message.getReplyTo()[0]);
        assertEquals(1, message.getFrom().length);
        assertEquals(sender, message.getFrom()[0]);
        assertNull(message.getRecipients(RecipientType.CC));
        assertNull(message.getRecipients(RecipientType.BCC));
        assertEquals(1, message.getRecipients(RecipientType.TO).length);
        assertEquals(recipient, message.getRecipients(RecipientType.TO)[0]);
        assertEquals("text/plain", message.getContentType());
        assertEquals(plaintext, message.getContent());
        assertEquals(plaintext, new GaeEmailUtils().getTextPart(message));
        assertEquals(null, new GaeEmailUtils().getHtmlPart(message));
    }

    /** Test createMessage() for a multipart message. */
    @Test public void testCreateMessageMultipart() throws Exception {
        String subject = "Subject";
        String plaintext = "Plaintext";
        String html = "<p>Hello, world.</p>";

        EmailMessage email = new EmailMessage();
        email.setFormat(EmailFormat.MULTIPART);
        email.setSender(new EmailAddress("Example Sender", "sender@example.com"));
        email.setRecipients(new EmailAddress("Example Recipient", "recipient@example.com"));
        email.setReplyTo(new EmailAddress("Example Reply", "reply@example.com"));
        email.setSubject(subject);
        email.setPlaintext(plaintext);
        email.setHtml(html);

        InternetAddress sender = new GaeEmailUtils().createInternetAddress("sender@example.com", "Example Sender");
        InternetAddress recipient = new GaeEmailUtils().createInternetAddress("recipient@example.com", "Example Recipient");
        InternetAddress replyTo = new GaeEmailUtils().createInternetAddress("reply@example.com", "Example Reply");
        List<InternetAddress> recipients = new ArrayList<InternetAddress>();
        recipients.add(recipient);

        Message message = new GaeEmailUtils().createMessage(email);
        assertNotNull(message);
        assertEquals(1, message.getReplyTo().length);
        assertEquals(replyTo, message.getReplyTo()[0]);
        assertEquals(1, message.getFrom().length);
        assertEquals(sender, message.getFrom()[0]);
        assertNull(message.getRecipients(RecipientType.CC));
        assertNull(message.getRecipients(RecipientType.BCC));
        assertEquals(1, message.getRecipients(RecipientType.TO).length);
        assertEquals(recipient, message.getRecipients(RecipientType.TO)[0]);
        assertTrue(message.getContent() instanceof Multipart);
        Multipart multipart = (Multipart) message.getContent();
        assertEquals(2, multipart.getCount());
        assertEquals("text/plain; charset=\"iso-8859-1\"", multipart.getBodyPart(0).getContentType());
        assertEquals(plaintext, multipart.getBodyPart(0).getContent());
        assertEquals(plaintext, new GaeEmailUtils().getTextPart(message));
        assertEquals("text/html; charset=\"iso-8859-1\"", multipart.getBodyPart(1).getContentType());  // seems like this ought to be text/html ???
        assertEquals(html, multipart.getBodyPart(1).getContent());
        assertEquals(html, new GaeEmailUtils().getHtmlPart(message));
    }
}
