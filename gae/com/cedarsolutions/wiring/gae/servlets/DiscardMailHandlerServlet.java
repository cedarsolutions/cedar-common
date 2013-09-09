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
package com.cedarsolutions.wiring.gae.servlets;

import java.io.IOException;
import java.util.Properties;

import javax.mail.Address;
import javax.mail.Session;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMessage.RecipientType;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.cedarsolutions.util.LoggingUtils;

/**
 * Mail handler servlet, to discard incoming mail.
 * @see <a href="http://code.google.com/appengine/docs/java/mail/receiving.html">Handling Incoming Email</a>
 * @author Kenneth J. Pronovici <pronovic@ieee.org>
 */
@SuppressWarnings("serial")
public class DiscardMailHandlerServlet extends HttpServlet {

    /** Log4j logger. */
    private static final Logger LOGGER = LoggingUtils.getLogger(DiscardMailHandlerServlet.class);

    /** Handle the incoming email message and log who it was sent to. */
    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        // Note: The Session class is final, so it can't be mocked.  That
        // makes it almost impossible to test this method in any useful way.
        try {
            Properties props = new Properties();
            Session session = Session.getDefaultInstance(props, null);
            MimeMessage message = new MimeMessage(session, request.getInputStream());
            Address[] recipients = message.getRecipients(RecipientType.TO);
            if (recipients != null && recipients.length > 0) {
                LOGGER.debug("Discarding email message sent to " + recipients[0]);
            }
        } catch (Exception e) { }
    }

}
