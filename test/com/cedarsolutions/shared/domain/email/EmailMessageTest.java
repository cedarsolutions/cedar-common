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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;


/**
 * Unit tests for EmailMessage.
 * @author Kenneth J. Pronovici <pronovic@ieee.org>
 */
@SuppressWarnings("unlikely-arg-type")
public class EmailMessageTest {

    /** Test the constructor. */
    @Test public void testConstructor() {
        EmailMessage message = new EmailMessage();
        assertNotNull(message);
        assertNull(message.getFormat());
        assertNull(message.getSender());
        assertNull(message.getReplyTo());
        assertNull(message.getRecipients());
        assertNull(message.getSubject());
        assertNull(message.getPlaintext());
        assertNull(message.getHtml());
        assertNotNull(message.toString());
    }

    /** Test the simple getters and setters. */
    @Test public void testSimpleGettersSetters() {
        EmailMessage message = new EmailMessage();

        message.setFormat(EmailFormat.PLAINTEXT);
        assertEquals(EmailFormat.PLAINTEXT, message.getFormat());

        message.setSender(new EmailAddress("sender-name", "sender-address"));
        assertEquals(new EmailAddress("sender-name", "sender-address"), message.getSender());

        message.setReplyTo(new EmailAddress("reply-name", "reply-address"));
        assertEquals(new EmailAddress("reply-name", "reply-address"), message.getReplyTo());

        message.setSubject("subject");
        assertEquals("subject", message.getSubject());

        message.setPlaintext("plaintext");
        assertEquals("plaintext", message.getPlaintext());

        message.setHtml("html");
        assertEquals("html", message.getHtml());
    }

    /** Test the recipients getter and setters. */
    @Test public void testRecipients() {
        EmailMessage message = new EmailMessage();

        EmailAddress recipient1 = new EmailAddress("name1", "address1");
        EmailAddress recipient2 = new EmailAddress("name2", "address2");

        message.setRecipients((EmailAddress[]) null);
        assertNull(message.getRecipients());

        message.setRecipients((List<EmailAddress>) null);
        assertNull(message.getRecipients());

        message.setRecipients(recipient1);
        assertEquals(1, message.getRecipients().size());
        assertSame(recipient1, message.getRecipients().get(0));

        message.setRecipients((EmailAddress[]) null);
        assertNull(message.getRecipients());

        message.setRecipients(recipient1, recipient2);
        assertEquals(2, message.getRecipients().size());
        assertSame(recipient1, message.getRecipients().get(0));
        assertSame(recipient2, message.getRecipients().get(1));

        message.setRecipients((List<EmailAddress>) null);
        assertNull(message.getRecipients());

        List<EmailAddress> list = new ArrayList<EmailAddress>();
        message.setRecipients(list);
        assertSame(list, message.getRecipients());
    }

    /** Test the toString() method. */
    @Test public void testToString() {
        String result = null;
        EmailMessage email = null;

        EmailAddress recipient2 = new EmailAddress("name2", "address2");

        email = new EmailMessage();
        result = email.toString();
        assertEquals("From: \nTo: \nSubject: \n\n", result);

        email = createEmailMessage();
        result = email.toString();
        assertEquals("From: sender@whatever\nTo: recipient@whatever, \nSubject: subject\nReply-To: reply@whatever\n\nhtml\n",
                     result);

        email = createEmailMessage();
        email.getRecipients().add(recipient2);
        result = email.toString();
        assertEquals("From: sender@whatever\nTo: recipient@whatever, \"name2\" <address2>, \nSubject: subject\nReply-To: reply@whatever\n\nhtml\n",
                     result);
    }

    /** Test equals(). */
    @Test public void testEquals() {
        EmailMessage message1;
        EmailMessage message2;

        message1 = createEmailMessage();
        message2 = createEmailMessage();
        assertTrue(message1.equals(message2));
        assertTrue(message2.equals(message1));

        try {
            message1 = createEmailMessage();
            message2 = null;
            message1.equals(message2);
            fail("Expected NullPointerException");
        } catch (NullPointerException e) { }

        try {
            message1 = createEmailMessage();
            message2 = null;
            message1.equals("blech");
            fail("Expected ClassCastException");
        } catch (ClassCastException e) { }

        message1 = createEmailMessage();
        message2 = createEmailMessage();
        message2.setFormat(EmailFormat.PLAINTEXT);
        assertFalse(message1.equals(message2));
        assertFalse(message2.equals(message1));

        message1 = createEmailMessage();
        message2 = createEmailMessage();
        message2.setSender(new EmailAddress("X"));
        assertFalse(message1.equals(message2));
        assertFalse(message2.equals(message1));

        message1 = createEmailMessage();
        message2 = createEmailMessage();
        message2.setReplyTo(new EmailAddress("X"));
        assertFalse(message1.equals(message2));
        assertFalse(message2.equals(message1));

        message1 = createEmailMessage();
        message2 = createEmailMessage();
        message2.setRecipients(new EmailAddress("X"));
        assertFalse(message1.equals(message2));
        assertFalse(message2.equals(message1));

        message1 = createEmailMessage();
        message2 = createEmailMessage();
        message2.setSubject("X");
        assertFalse(message1.equals(message2));
        assertFalse(message2.equals(message1));

        message1 = createEmailMessage();
        message2 = createEmailMessage();
        message2.setPlaintext("X");
        assertFalse(message1.equals(message2));
        assertFalse(message2.equals(message1));

        message1 = createEmailMessage();
        message2 = createEmailMessage();
        message2.setHtml("X");
        assertFalse(message1.equals(message2));
        assertFalse(message2.equals(message1));
    }

    /** Test hashCode(). */
    @Test public void testHashCode() {
        EmailMessage message1 = createEmailMessage();
        message1.setFormat(EmailFormat.PLAINTEXT);

        EmailMessage message2 = createEmailMessage();
        message2.setSender(new EmailAddress("X"));

        EmailMessage message3 = createEmailMessage();
        message2.setReplyTo(new EmailAddress("X"));

        EmailMessage message4 = createEmailMessage();
        message4.setRecipients(new EmailAddress("X"));

        EmailMessage message5 = createEmailMessage();
        message5.setSubject("X");

        EmailMessage message6 = createEmailMessage();
        message6.setPlaintext("X");

        EmailMessage message7 = createEmailMessage();
        message7.setHtml("X");

        EmailMessage message8 = createEmailMessage();
        message8.setFormat(EmailFormat.PLAINTEXT); // same as message1

        Map<EmailMessage, String> map = new HashMap<EmailMessage, String>();
        map.put(message1, "ONE");
        map.put(message2, "TWO");
        map.put(message3, "THREE");
        map.put(message4, "FOUR");
        map.put(message5, "FIVE");
        map.put(message6, "SIX");
        map.put(message7, "SEVEN");

        assertEquals("ONE", map.get(message1));
        assertEquals("TWO", map.get(message2));
        assertEquals("THREE", map.get(message3));
        assertEquals("FOUR", map.get(message4));
        assertEquals("FIVE", map.get(message5));
        assertEquals("SIX", map.get(message6));
        assertEquals("SEVEN", map.get(message7));
        assertEquals("ONE", map.get(message8));
    }

    /** Create a EmailMessage for testing. */
    private static EmailMessage createEmailMessage() {
        EmailMessage message = new EmailMessage();

        message.setFormat(EmailFormat.MULTIPART);
        message.setSender(new EmailAddress("sender@whatever"));
        message.setReplyTo(new EmailAddress("reply@whatever"));
        message.setRecipients(new EmailAddress("recipient@whatever"));
        message.setSubject("subject");
        message.setPlaintext("plaintext");
        message.setPlaintext("html");

        return message;
    }
}
