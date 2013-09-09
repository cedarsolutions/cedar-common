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
package com.cedarsolutions.wiring.gwt.rpc;

import java.lang.reflect.Method;

import org.apache.log4j.Logger;
import org.gwtwidgets.server.spring.GWTRPCServiceExporter;

import com.cedarsolutions.exception.ServiceException;
import com.cedarsolutions.server.service.IXsrfTokenService;
import com.cedarsolutions.util.LoggingUtils;
import com.google.gwt.user.client.rpc.RpcToken;
import com.google.gwt.user.client.rpc.RpcTokenException;
import com.google.gwt.user.server.Util;
import com.google.gwt.user.server.rpc.NoXsrfProtect;
import com.google.gwt.user.server.rpc.RPCRequest;
import com.google.gwt.user.server.rpc.XsrfProtect;

/**
 * Customized GWT service exporter.
 *
 * <h2>Error Handing</h2>
 *
 * <p>
 * GWT doesn't like it when service calls throw exceptions that aren't declared
 * in the method signature. If this happens, you get a 500 (server error) rather
 * than something more useful. In particular, it causes problems when Spring
 * security is involved, since we really want to see a 403 (forbidden) so we can
 * handle it explicitly.
 * </p>
 *
 * <p>
 * This class takes all unexpected failures and converts them to
 * ServiceException.  Make sure that all of your service methods declare
 * <code>throws ServiceException</code>.  If you do this, you'll get a legible
 * exception that you can catch in your service handler:
 * </p>
 *
 * <pre>
 *     public void onFailure(Throwable caught) {
 *      try {
 *          throw caught;
 *      } catch (StatusCodeException e) {
 *          HttpStatusCode statusCode = HttpStatusCode.convert(e.getStatusCode());
 *          switch(statusCode) {
 *          case FORBIDDEN:
 *              // do what's needed for a "not authorized" error
 *              break;
 *          default:
 *              // do what's needed for a generic service error
 *              break;
 *          }
 *      } catch (Throwable e) {
 *          // do what's needed for a generic service error
 *      }
 *  }
 * </pre>
 *
 * <h2>CSRF/XSRF Protection</h2>
 *
 * <p>
 * If enabled via the enableXsrfProtection flag, this code attempts to
 * implement protection against CSRF/XSRF attacks (cross site request forgery).
 * Two different approaches are taken.
 * </p>
 *
 * <p>
 * The first approach is a very limited check based on the permutation strong
 * name field that GWT automatically includes in the HTTP request header.  If
 * the permutation strong name is empty, then the request is assumed to be a
 * CRSF/XSRF attack.  The code below uses the standard check that's provided
 * with GWT, but overrides the standard GWT method to throw an RpcTokenException
 * rather than a SecurityException.  This makes it possible to handle the error
 * more explicitly on the client side.
 * </p>
 *
 * <p>
 * The second approach is more sophisticated.  Any "protected" RPC call must be
 * preceded by a call to to retrieve a cryptographic token.  This token is
 * included in the RPC call and is validated on the server side.  By validating
 * this token, we can block requests from "evil" sources, since any attacker
 * will theoretically not have the means to generate a valid token.  (They can
 * get the browser to make the request on their behalf &mdash; that's the
 * forgery part &mdash; but they can't read the session cookie's value in order
 * to generate a valid token.)
 * </p>
 *
 * <p>
 * To protect an RPC call, you annotate the service interface with
 * \@XsrfProtect.  However, remember that the mechanism depends on a session
 * being available.  Practically speaking, this means that you can only use
 * \@XsrfProtect on RPCs that are also marked with \@Secured.  That shouldn't
 * present a problem, since in the context of a CSRF/XSRF attack, you're only
 * worried about services that can actually accomplish something useful, and
 * you would normally secure those services anyway.
 * </p>
 *
 * <p>
 * The implementation below has been backported from the XsrfProtectedServiceServlet
 * in GWT 2.4.0.  There's no opportunity to inherit from that class while still
 * using GWT-SL.  However, since the GWTRPCServiceExporter and XsrfProtectedServiceServlet
 * both extend the same class, the code can be pulled out of XsrfProtectedServiceServlet
 * and implemented here.  I followed the basic outline from XsrfProtectedServiceServlet,
 * and then had to make several adjustments so that RpcTokenException would get thrown
 * back to the RPC caller in a legible way.
 * </p>
 *
 * @see <a href="http://roald.bankras.net/wordpress/?p=31">Add authentication to your GWT application</a>
 * @see <a href="http://groups.google.com/group/gwt-sl/browse_thread/thread/ee8bc5109a98f2f5">CSRF Prevention?</a>
 * @see <a href="https://developers.google.com/web-toolkit/doc/latest/DevGuideSecurityRpcXsrf">GWT RPC XSRF protection</a>
 *
 * @author Kenneth J. Pronovici <pronovic@ieee.org>
 */
public class SecuredServiceExporter extends GWTRPCServiceExporter {

    /** Logger instance. */
    private static Logger LOGGER = LoggingUtils.getLogger(SecuredServiceExporter.class);

    /** Serialization version number. */
    private static final long serialVersionUID = 1L;

    /** Whether to enable CSRF/XSRF protection. */
    private boolean enableXsrfProtection;

    /** CSRF/XSRF token service. */
    private IXsrfTokenService xsrfTokenService;

    /**
     * Create a service exporter, optionally enabling CSRF/XSRF protection.
     * @param enableXsrfProtection   Whether to enable CSRF/XSRF protection
     * @param xsrfTokenService       CSRF/XSRF token service to use
     */
    public SecuredServiceExporter(boolean enableXsrfProtection, IXsrfTokenService xsrfTokenService) {
        super();
        this.setEnableXsrfProtection(enableXsrfProtection);
        this.setShouldCheckPermutationStrongName(enableXsrfProtection);
        this.setXsrfTokenService(xsrfTokenService);
    }

    /** Wrap any unexpected exception from an RPC method call in a ServiceException. */
    @Override
    protected void doUnexpectedFailure(Throwable exception) {
        throw new ServiceException("Unexpected failure in service call: " + exception.getMessage(), exception);
    }

    /** Handle exceptions thrown by the CSRF/XSRF token checking mechanism. */
    @Override
    protected String handleExporterProcessingException(Exception exception) {
        if (exception instanceof RpcTokenException) {
            try {
                return this.encodeResponseForFailure(exception);
            } catch (Exception e) {
                return super.handleExporterProcessingException(exception);
            }
        } else {
            return super.handleExporterProcessingException(exception);
        }
    }

    /** Apply CSRF/XSRF validation to the request after it has been deserialized. */
    @Override
    protected void onAfterRequestDeserialized(RPCRequest request) {
        if (shouldValidateXsrfToken(request.getMethod())) {
            validateXsrfToken(request.getRpcToken());
        }
    }

    /**
     * Indicates whether the CSRF/XSRF token should be validated for a particular method.
     * @param method Method being invoked
     * @return True if CSRF/XSRF token should be verified, false otherwise.
     */
    protected boolean shouldValidateXsrfToken(Method method) {
        if (!this.enableXsrfProtection) {
            LOGGER.debug("CSRF/XSRF protection is disabled on the server side.");
            return false;
        } else {
            boolean enabled = Util.isMethodXsrfProtected(method, XsrfProtect.class, NoXsrfProtect.class, RpcToken.class);
            LOGGER.debug("CSRF/XSRF protection is " + (enabled ? "enabled" : "disabled") + " for method " + getMethodName(method));
            return enabled;
        }
    }

    /** Override the standard permutation strong name check to throw an RpcTokenException. */
    @Override
    protected void checkPermutationStrongName() throws SecurityException {
        try {
            super.checkPermutationStrongName();
        } catch (SecurityException e) {
            LOGGER.error("Possible CSRF/XSRF attack: permutation strong name was empty");
            throw new RpcTokenException("Request blocked: permutation strong name was invalid");
        }
    }

    /**
     * Perform CSRF/XSRF token validation.
     * @param token Token included with an RPC request
     * @throws RpcTokenException If token verification failed.
     */
    protected void validateXsrfToken(RpcToken token) throws RpcTokenException {
        this.xsrfTokenService.validateXsrfToken(token);
    }

    /** Get a legible method name based on a method argument. */
    private static String getMethodName(Method method) {
        return method.getDeclaringClass().getSimpleName() + "." + method.getName();
    }

    public boolean getShouldCheckPermutationStrongName() {
        return this.shouldCheckPermutationStrongName;
    }

    public boolean getEnableXsrfProtection() {
        return this.enableXsrfProtection;
    }

    public void setEnableXsrfProtection(boolean enableXsrfProtection) {
        this.enableXsrfProtection = enableXsrfProtection;
    }

    public IXsrfTokenService getXsrfTokenService() {
        return this.xsrfTokenService;
    }

    public void setXsrfTokenService(IXsrfTokenService xsrfTokenService) {
        this.xsrfTokenService = xsrfTokenService;
    }

}
