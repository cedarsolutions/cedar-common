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
 * Unit tests for EmailTemplate.
 * @author Kenneth J. Pronovici <pronovic@ieee.org>
 */
public class EmailTemplateTest {

    /** Test the constructor. */
    @Test public void testConstructor() {
        EmailTemplate template = new EmailTemplate();
        assertNotNull(template);
        assertNull(template.getFormat());
        assertNull(template.getSender());
        assertNull(template.getReplyTo());
        assertNull(template.getRecipients());
        assertNull(template.getTemplateGroup());
        assertNull(template.getTemplateName());
        assertNull(template.getTemplateContext());
    }

    /** Test the simple getters and setters. */
    @Test public void testSimpleGettersSetters() {
        EmailTemplate template = new EmailTemplate();

        template.setFormat(EmailFormat.PLAINTEXT);
        assertEquals(EmailFormat.PLAINTEXT, template.getFormat());

        template.setSender(new EmailAddress("sender-name", "sender-address"));
        assertEquals(new EmailAddress("sender-name", "sender-address"), template.getSender());

        template.setReplyTo(new EmailAddress("reply-name", "reply-address"));
        assertEquals(new EmailAddress("reply-name", "reply-address"), template.getReplyTo());

        template.setTemplateGroup("group");
        assertEquals("group", template.getTemplateGroup());

        template.setTemplateName("name");
        assertEquals("name", template.getTemplateName());

        Map<String, Object> context = new HashMap<String, Object>();
        template.setTemplateContext(context);
        assertSame(context, template.getTemplateContext());
    }

    /** Test the recipients getter and setters. */
    @Test public void testRecipients() {
        EmailTemplate message = new EmailTemplate();

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

    /** Test equals(). */
    @Test public void testEquals() {
        EmailTemplate template1;
        EmailTemplate template2;

        template1 = createEmailTemplate();
        template2 = createEmailTemplate();
        assertTrue(template1.equals(template2));
        assertTrue(template2.equals(template1));

        try {
            template1 = createEmailTemplate();
            template2 = null;
            template1.equals(template2);
            fail("Expected NullPointerException");
        } catch (NullPointerException e) { }

        try {
            template1 = createEmailTemplate();
            template2 = null;
            template1.equals("blech");
            fail("Expected ClassCastException");
        } catch (ClassCastException e) { }

        template1 = createEmailTemplate();
        template2 = createEmailTemplate();
        template2.setFormat(EmailFormat.PLAINTEXT);
        assertFalse(template1.equals(template2));
        assertFalse(template2.equals(template1));

        template1 = createEmailTemplate();
        template2 = createEmailTemplate();
        template2.setSender(new EmailAddress("X"));
        assertFalse(template1.equals(template2));
        assertFalse(template2.equals(template1));

        template1 = createEmailTemplate();
        template2 = createEmailTemplate();
        template2.setReplyTo(new EmailAddress("X"));
        assertFalse(template1.equals(template2));
        assertFalse(template2.equals(template1));

        template1 = createEmailTemplate();
        template2 = createEmailTemplate();
        template2.setRecipients(new EmailAddress("X"));
        assertFalse(template1.equals(template2));
        assertFalse(template2.equals(template1));

        template1 = createEmailTemplate();
        template2 = createEmailTemplate();
        template2.setTemplateContext(null);
        assertFalse(template1.equals(template2));
        assertFalse(template2.equals(template1));

        template1 = createEmailTemplate();
        template2 = createEmailTemplate();
        template2.setTemplateGroup("X");
        assertFalse(template1.equals(template2));
        assertFalse(template2.equals(template1));

        template1 = createEmailTemplate();
        template2 = createEmailTemplate();
        template2.setTemplateName("X");
        assertFalse(template1.equals(template2));
        assertFalse(template2.equals(template1));
    }

    /** Test hashCode(). */
    @Test public void testHashCode() {
        EmailTemplate template1 = createEmailTemplate();
        template1.setFormat(EmailFormat.PLAINTEXT);

        EmailTemplate template2 = createEmailTemplate();
        template2.setSender(new EmailAddress("X"));

        EmailTemplate template3 = createEmailTemplate();
        template2.setReplyTo(new EmailAddress("X"));

        EmailTemplate template4 = createEmailTemplate();
        template4.setRecipients(new EmailAddress("X"));

        EmailTemplate template5 = createEmailTemplate();
        template5.setTemplateContext(null);

        EmailTemplate template6 = createEmailTemplate();
        template6.setTemplateGroup("X");

        EmailTemplate template7 = createEmailTemplate();
        template7.setTemplateName("X");

        EmailTemplate template8 = createEmailTemplate();
        template8.setFormat(EmailFormat.PLAINTEXT); // same as template1

        Map<EmailTemplate, String> map = new HashMap<EmailTemplate, String>();
        map.put(template1, "ONE");
        map.put(template2, "TWO");
        map.put(template3, "THREE");
        map.put(template4, "FOUR");
        map.put(template5, "FIVE");
        map.put(template6, "SIX");
        map.put(template7, "SEVEN");

        assertEquals("ONE", map.get(template1));
        assertEquals("TWO", map.get(template2));
        assertEquals("THREE", map.get(template3));
        assertEquals("FOUR", map.get(template4));
        assertEquals("FIVE", map.get(template5));
        assertEquals("SIX", map.get(template6));
        assertEquals("SEVEN", map.get(template7));
        assertEquals("ONE", map.get(template8));
    }

    /** Create a EmailTemplate for testing. */
    private static EmailTemplate createEmailTemplate() {
        EmailTemplate template = new EmailTemplate();

        template.setFormat(EmailFormat.MULTIPART);
        template.setSender(new EmailAddress("sender@whatever"));
        template.setReplyTo(new EmailAddress("reply@whatever"));
        template.setRecipients(new EmailAddress("recipient@whatever"));
        template.setTemplateContext(new HashMap<String, Object>());
        template.setTemplateGroup("group");
        template.setTemplateName("name");

        return template;
    }
}
