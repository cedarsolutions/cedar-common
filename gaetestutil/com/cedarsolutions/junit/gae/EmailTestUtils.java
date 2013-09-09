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
package com.cedarsolutions.junit.gae;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.RETURNS_DEEP_STUBS;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.util.List;

import org.springframework.core.io.Resource;

import com.cedarsolutions.exception.CedarRuntimeException;
import com.cedarsolutions.server.service.IEmailService;
import com.cedarsolutions.server.service.impl.GaeEmailService;
import com.cedarsolutions.server.service.impl.GaeVelocityTemplateService;
import com.cedarsolutions.shared.domain.email.EmailAddress;
import com.cedarsolutions.shared.domain.email.EmailFormat;
import com.cedarsolutions.shared.domain.email.EmailMessage;
import com.cedarsolutions.shared.domain.email.EmailTemplate;
import com.cedarsolutions.util.FilesystemUtils;
import com.cedarsolutions.util.StringUtils;
import com.cedarsolutions.util.gae.GaeEmailUtils;
import com.cedarsolutions.util.gae.GaeVelocityUtils;

/**
 * Utility methods useful for testing with the email service.
 * @author Kenneth J. Pronovici <pronovic@ieee.org>
 */
public class EmailTestUtils {

    /**
     * Create a partially-mocked email service for testing.
     *
     * <p>
     * This is a bit difficult to follow.  However, it's the only way I can
     * come up with the validate the service behavior and test template
     * rendering at the same time.  (I really do need both to feel safe.)
     * </p>
     *
     * <p>
     * The goal is to set up the services so that the template actually
     * does get rendered via Velocity, but the email does not get sent.
     * Further, we want to be able to capture the rendered template data
     * and verify that it looks sensible.
     * </p>
     *
     * <p>
     * The Mockito folks would probably argue that using partial mocks like
     * this represents a code smell, meaning that I should find some other
     * way to do it.  I've decided to live with it.  I think of this as an
     * instance of mocking a third-party interface, something the Mockito
     * people grudgingly consider to be a legitimate use of partial mocks.
     * </p>
     *
     * @return Partially-mocked email service that can be used for testing.
     */
    public static IEmailService createPartiallyMockedEmailService(String templateDir) {
        try {
            GaeEmailUtils gaeEmailUtils = mock(GaeEmailUtils.class);
            GaeVelocityUtils gaeVelocityUtils = GaeVelocityUtils.getInstance();

            Resource templateDirResource = mock(Resource.class, RETURNS_DEEP_STUBS);
            when(templateDirResource.exists()).thenReturn(true);
            when(templateDirResource.getFile().getPath()).thenReturn(templateDir);

            GaeVelocityTemplateService templateService = new GaeVelocityTemplateService();
            templateService.setGaeVelocityUtils(gaeVelocityUtils);
            templateService.setTemplateDirResource(templateDirResource);
            templateService.afterPropertiesSet();

            GaeEmailService realEmailService = new GaeEmailService();
            realEmailService.setGaeEmailUtils(gaeEmailUtils);
            realEmailService.setTemplateService(templateService);
            realEmailService.afterPropertiesSet();

            // By creating this spy, the real underlying code will be called, but we'll
            // be able to verify the arguments that are passed in to the method calls.
            return spy(realEmailService);
        } catch (IOException e) {
            // We just have to catch IOException to satisfy File.getPath() interface
            throw new CedarRuntimeException("Error creating partial mock: " + e.getMessage(), e);
        }
    }

    /**
     * Validate email template contents.
     * @param template   Template to validate
     * @param format     Expected email format
     * @param group      Expected template group
     * @param name       Expected template name
     * @param sender     Expected sender
     * @param replyTo    Expected reply-to address
     * @param recipients Expected list of recipients
     */
    public static void validateTemplateContents(EmailTemplate template,
                                                EmailFormat format,
                                                String group,
                                                String name,
                                                EmailAddress sender,
                                                EmailAddress replyTo,
                                                List<EmailAddress> recipients) {
        assertNotNull(template);
        assertEquals(format, template.getFormat());
        assertEquals(group, template.getTemplateGroup());
        assertEquals(name, template.getTemplateName());
        assertEquals(sender, template.getSender());
        assertEquals(replyTo, template.getReplyTo());
        assertEquals(recipients, template.getRecipients());
    }

    /**
     * Validate email message contents after rendering a template.
     *
     * <p>
     * This method enforces a naming convention and standard directory hierarchy for
     * templates and expected results.  You'll end up with something like this:
     * </p>
     *
     * <pre>
     * resultsDir
     *           /templateName
     *                        /testName.html.result
     *                        /testName.plaintext.result
     *                        /testName.subject.result
     * </pre>
     *
     * <p>
     * The <code>resultsDir</code> can be anywhere, but probably lives within the unit
     * test folder, i.e. <code>test/com/cedarsolutions/santa/server/service/impl/results</code>.
     * </p>
     *
     * <p>
     * The name of the test is arbitrary.  You just need to use the same name in the
     * test case the the expected results filename.
     * </p>
     *
     * <p>
     * There are 3 expected results files, one for each component of an email.  If you
     * specify MULTIPART, then all three must be there.  If you specify PLAINTEXT, then
     * the HTML part is expected to be null.
     * </p>
     *
     * <p>
     * The result filenames <i>must</i> end in ".result".  I've standardized on this
     * because of the way I'm using AnyEdit. I set up an exclusion for "*.result", so
     * AnyEdit won't normalize the line endings in results files.
     * </p>
     *
     * @param message       Message to validate
     * @param format        Expected email format
     * @param resultsDir    Directory that expected results can be found in
     * @param templateName  Name of the template
     * @param testName      Name of the specific test
     */
    public static void validateMessageContents(EmailMessage message, EmailFormat format, String resultsDir, String templateName, String testName) {
        String subjectPath = FilesystemUtils.join(resultsDir, templateName, testName + ".subject.result");
        String plaintextPath = FilesystemUtils.join(resultsDir, templateName, testName + ".plaintext.result");
        String htmlPath = FilesystemUtils.join(resultsDir, templateName, testName + ".html.result");

        String subject = StringUtils.rtrim(FilesystemUtils.getFileContentsAsString(subjectPath));
        String plaintext = StringUtils.rtrim(FilesystemUtils.getFileContentsAsString(plaintextPath));
        String html = StringUtils.rtrim(FilesystemUtils.getFileContentsAsString(htmlPath));

        assertNotNull(message);
        assertEquals(format, message.getFormat());
        assertEquals(subject, message.getSubject());

        if (format == EmailFormat.PLAINTEXT) {
            assertEquals(plaintext, message.getPlaintext());
            assertNull(message.getHtml());
        } else {
            assertEquals(plaintext, message.getPlaintext());
            assertEquals(html, message.getHtml());
        }
    }

}
