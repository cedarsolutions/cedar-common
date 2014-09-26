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
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.fail;
import static org.mockito.Matchers.isA;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.HashMap;
import java.util.Map;

import javax.mail.Message;

import org.junit.Test;
import org.mockito.ArgumentCaptor;

import com.cedarsolutions.exception.NotConfiguredException;
import com.cedarsolutions.server.service.ITemplateService;
import com.cedarsolutions.shared.domain.email.EmailAddress;
import com.cedarsolutions.shared.domain.email.EmailFormat;
import com.cedarsolutions.shared.domain.email.EmailMessage;
import com.cedarsolutions.shared.domain.email.EmailTemplate;
import com.cedarsolutions.util.gae.GaeEmailUtils;

/**
 * Unit tests for GaeEmailService.
 * @author Kenneth J. Pronovici <pronovic@ieee.org>
 */
public class GaeEmailServiceTest {

    /** Test constructor, getters and setters. */
    @Test public void testConstructor() {
        GaeEmailService service = new GaeEmailService();
        assertNull(service.getGaeEmailUtils());
        assertNull(service.getTemplateService());

        GaeEmailUtils gaeEmailUtils = mock(GaeEmailUtils.class);
        service.setGaeEmailUtils(gaeEmailUtils);
        assertSame(gaeEmailUtils, service.getGaeEmailUtils());

        ITemplateService templateService = mock(ITemplateService.class);
        service.setTemplateService(templateService);
        assertSame(templateService, service.getTemplateService());
    }

    /** Test the afterPropertiesSet() method. */
    @Test public void testAfterPropertiesSet() throws Exception {
        GaeEmailService service = new GaeEmailService();
        GaeEmailUtils gaeEmailUtils = mock(GaeEmailUtils.class);
        ITemplateService templateService = mock(ITemplateService.class);

        service.setGaeEmailUtils(gaeEmailUtils);
        service.setTemplateService(templateService);
        service.afterPropertiesSet();

        try {
            service.setGaeEmailUtils(null);
            service.setTemplateService(templateService);
            service.afterPropertiesSet();
            fail("Expected NotConfiguredException");
        } catch (NotConfiguredException e) { }

        try {
            service.setGaeEmailUtils(gaeEmailUtils);
            service.setTemplateService(null);
            service.afterPropertiesSet();
            fail("Expected NotConfiguredException");
        } catch (NotConfiguredException e) { }
    }

    /** Test sendEmail() for a message. */
    @Test public void testSendEmail() {
        EmailMessage email = new EmailMessage();

        GaeEmailService service = createService();
        Message message = mock(Message.class);
        when(service.getGaeEmailUtils().createMessage(email)).thenReturn(message);

        service.sendEmail(email);
        verify(service.getGaeEmailUtils()).createMessage(email);
        verify(service.getGaeEmailUtils()).sendMessage(message);
    }

    /** Test sendEmail() for a plaintext template. */
    @Test public void testSendTemplatePlaintext() {
        Map<String, Object> context = new HashMap<String, Object>();

        EmailTemplate template = new EmailTemplate();
        template.setFormat(EmailFormat.PLAINTEXT);
        template.setSender(new EmailAddress("sender-name", "sender-address"));
        template.setReplyTo(new EmailAddress("reply-name", "reply-address"));
        template.setTemplateGroup("g");
        template.setTemplateName("n");
        template.setTemplateContext(context);

        ArgumentCaptor<EmailMessage> email = ArgumentCaptor.forClass(EmailMessage.class);

        GaeEmailService service = createService();
        Message message = mock(Message.class);
        when(service.getGaeEmailUtils().createMessage(isA(EmailMessage.class))).thenReturn(message);
        when(service.getTemplateService().renderTemplate("g", "n", SUBJECT, context)).thenReturn("subject-rendered");
        when(service.getTemplateService().renderTemplate("g", "n", PLAINTEXT, context)).thenReturn("plaintext-rendered");
        when(service.getTemplateService().renderTemplate("g", "n", HTML, context)).thenReturn("html-rendered");

        service.sendEmail(template);

        verify(service.getGaeEmailUtils()).sendMessage(message);
        verify(service.getGaeEmailUtils()).createMessage(email.capture());
        assertEquals(template.getFormat(), email.getValue().getFormat());
        assertEquals(template.getSender(), email.getValue().getSender());
        assertEquals(template.getReplyTo(), email.getValue().getReplyTo());
        assertEquals(template.getRecipients(), email.getValue().getRecipients());
        assertEquals("subject-rendered", email.getValue().getSubject());
        assertEquals("plaintext-rendered", email.getValue().getPlaintext());
        assertEquals(null, email.getValue().getHtml());
    }

    /** Test sendEmail() for a multipart template. */
    @Test public void testSendTemplateMultipart() {
        Map<String, Object> context = new HashMap<String, Object>();

        EmailTemplate template = new EmailTemplate();
        template.setFormat(EmailFormat.MULTIPART);
        template.setSender(new EmailAddress("sender-name", "sender-address"));
        template.setReplyTo(new EmailAddress("reply-name", "reply-address"));
        template.setTemplateGroup("g");
        template.setTemplateName("n");
        template.setTemplateContext(context);

        ArgumentCaptor<EmailMessage> email = ArgumentCaptor.forClass(EmailMessage.class);

        GaeEmailService service = createService();
        Message message = mock(Message.class);
        when(service.getGaeEmailUtils().createMessage(isA(EmailMessage.class))).thenReturn(message);
        when(service.getTemplateService().renderTemplate("g", "n", SUBJECT, context)).thenReturn("subject-rendered");
        when(service.getTemplateService().renderTemplate("g", "n", PLAINTEXT, context)).thenReturn("plaintext-rendered");
        when(service.getTemplateService().renderTemplate("g", "n", HTML, context)).thenReturn("html-rendered");

        service.sendEmail(template);

        verify(service.getGaeEmailUtils()).sendMessage(message);
        verify(service.getGaeEmailUtils()).createMessage(email.capture());
        assertEquals(template.getFormat(), email.getValue().getFormat());
        assertEquals(template.getSender(), email.getValue().getSender());
        assertEquals(template.getReplyTo(), email.getValue().getReplyTo());
        assertEquals(template.getRecipients(), email.getValue().getRecipients());
        assertEquals("subject-rendered", email.getValue().getSubject());
        assertEquals("plaintext-rendered", email.getValue().getPlaintext());
        assertEquals("html-rendered", email.getValue().getHtml());
    }

    /** Create a service instance, properly mocked for testing. */
    private static GaeEmailService createService() {
        GaeEmailService service = new GaeEmailService();
        GaeEmailUtils gaeEmailUtils = mock(GaeEmailUtils.class);
        ITemplateService templateService = mock(ITemplateService.class);

        service.setGaeEmailUtils(gaeEmailUtils);
        service.setTemplateService(templateService);
        service.afterPropertiesSet();

        return service;
    }

}
