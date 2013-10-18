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
package com.cedarsolutions.client.gwt.rpc.util;

import com.cedarsolutions.client.gwt.rpc.proxy.XsrfRpcProxyConfig;
import com.cedarsolutions.exception.InvalidDataException;
import com.cedarsolutions.exception.RpcSecurityException;
import com.cedarsolutions.shared.domain.ErrorDescription;
import com.cedarsolutions.util.gwt.GwtTimer;
import com.cedarsolutions.web.metadata.HttpStatusCode;
import com.google.gwt.http.client.RequestTimeoutException;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.rpc.IncompatibleRemoteServiceException;
import com.google.gwt.user.client.rpc.RpcTokenException;
import com.google.gwt.user.client.rpc.StatusCodeException;

/**
 * RPC caller that a callback interacts with.
 *
 * <p>
 * The goal here is to apply behavioral policies to RPCs on a system-wide
 * basis, rather than pushing those decisions out to all of the individual RPC
 * invocations.  For instance, we can apply a system-wide RPC timeout policy,
 * and we have a place to handle specific errors, retry failed requests, and
 * log information in a consistent way across all RPCs.
 * </p>
 *
 * <p>
 * The resulting RPC invocation idiom looks a little unsual compared to
 * "typical" example GWT code.  Client code never directly invokes RPCs.
 * However, since I prefer to avoid anonymous class implementations anyway for
 * testing purposes, this code already looked fairly unusual.  It's not a
 * perfect system, but it gets me enough functional benefits that I'm happy
 * with it.
 * <p>
 *
 * <p>
 * This abstract class tries to standardize behavior as much as possible, while
 * delegating application decisions (like how to actually display errors) to a
 * concrete class in the actual application.
 * </p>
 *
 * @param <A> Type of the asynchronous RPC
 * @param <T> Return type of the RPC method
 *
 * @author Kenneth J. Pronovici <pronovic@ieee.org>
 */
public abstract class AbstractRpcCaller<A, T> implements IRpcCaller<T> {

    /** The asynchronous RPC. */
    private A async;

    /** Name of the RPC that is being invoked, like "IClientSessionRpc". */
    private String rpc;

    /** Name of the method that is being invoked, like "establishClientSession". */
    private String method;

    /** Number of attempts made so far. */
    private int attempts;

    /** The caller id that's currently set. */
    private String callerId;

    /** Maximum number of attempts allowed for this RPC method. */
    private int maxAttempts;

    /** Elapsed timer. */
    private GwtTimer timer;

    /**
     * Create an RPC caller.
     * @param async        The asynchronous RPC
     * @param rpc          Name of the RPC that is being invoked, like "IClientSessionRpc"
     * @param method       Name of the method that is being invoked, like "establishClientSession"
     */
    protected AbstractRpcCaller(A async, String rpc, String method) {
        this.async = async;
        this.rpc = rpc;
        this.method = method;
        this.attempts = 0;
        this.callerId = this.getNextCallerId();
        this.maxAttempts = 1;  // by default, calls are not retried
    }

    /** Apply global policies that are required for all RPCs. */
    protected void applyGlobalPolicies() {
        // This is hideous. I'm setting a global property every single time an
        // RPC is invoked.  Unfortunately, there's no other obvious way to
        // inject configuration into the XsrfRpcProxy class that we're using
        // underneath.  On the positive side, client-side code is
        // single-threaded, so there's no real chance for thread conflict.
        // If callers set different values for different RPC invocations,
        // they'll probably get what they expect.
        XsrfRpcProxyConfig.getInstance().setTimeoutMs(this.getXsrfRpcProxyTimeoutMs());
    }

    /** Sets the global timeout to be used by the XSRF RPC proxy, or null to use the default. */
    public abstract int getXsrfRpcProxyTimeoutMs();

    /** Hook that lets child classes apply policies to an RPC. */
    public abstract void applyPolicies();

    /** Invoke the RPC method with the correct arguments, using the passed-in callback. */
    public abstract void invokeRpcMethod(A async, AsyncCallback<T> callback);

    /** Get the next caller id from some application-wide id manager, or null. */
    public abstract String getNextCallerId();

    /** Show an error to the user. */
    public abstract void showError(ErrorDescription error);

    /** Generate an error due to an authorization problem like HTTP FORBIDDEN. */
    public abstract ErrorDescription generateNotAuthorizedError(HttpStatusCode statusCode);

    /** Generate an error due to an RpcSecurityException. */
    public abstract ErrorDescription generateRpcSecurityExceptionError(RpcSecurityException exception);

    /** Generate an error due to an RpcTokenException. */
    public abstract ErrorDescription generateRpcTokenExceptionError(RpcTokenException exception);

    /** Generate an error due to a RequestTimeoutException. */
    public abstract ErrorDescription generateRequestTimeoutExceptionError(RequestTimeoutException exception);

    /** Generate an error due to no response received. */
    public abstract ErrorDescription generateNoResponseReceivedError(Throwable exception);

    /** Generate an error due to an IncompatibleRemoteServiceException. */
    public abstract ErrorDescription generateIncompatibleRemoteServiceExceptionError(IncompatibleRemoteServiceException exception);

    /** Generate an error due to a general exception. */
    public abstract ErrorDescription generateGeneralRpcError(Throwable exception);

    /** Generate an error due to a general exception that resulted in an HTTP error. */
    public abstract ErrorDescription generateGeneralRpcError(Throwable exception, HttpStatusCode statusCode);

    /** Get this caller's descriptive identifier. */
    @Override
    public String getDescriptiveCallerId() {
        return "RPC [" + this.getCallerId() + "] " + this.rpc + "." + this.method + "()";
    }

    /** Get the RPC method that is being invoked. */
    public String getRpcMethod() {
        return this.rpc + "." + this.method + "()";
    }

    /**
     * Create an RPC callback of the proper type.
     * Child classes can override this to use a specialized type of callback.
     */
    public IRpcCallback<T> createCallback() {
        return new RpcCallback<T>(this);
    }

    /**
     * Whether another RPC method call attempt should be made.
     * @param caught  Exception that caused the situation
     * @return True if retry should be made, false otherwise.
     */
    @Override
    public boolean isAnotherAttemptAllowed(Throwable caught) {
        return isExceptionRetryable(caught) ? this.attempts < this.maxAttempts : false;
    }

    /** Invoke the RPC method with the proper arguments, creating a new callback. */
    @Override
    public void invoke() {
        AsyncCallback<T> callback = this.createCallback();
        this.invoke(callback);
    }

    /**
     * Invoke the RPC method with the proper arguments, using the passed-in callback.
     * Callers are expected to check in with isAnotherAttemptAllowed() before invoking this method.
     * @param callback  Callback to use when invoking the RPC method.
     */
    @Override
    public void invoke(AsyncCallback<T> callback) {
        this.applyGlobalPolicies();
        this.applyPolicies();
        this.showProgressIndicator();
        this.incrementAttempts();
        this.startTimer();
        this.log(this.getDescriptiveCallerId() + ": start" + this.getDescriptiveCallState());
        this.invokeRpcMethod(this.async, callback);
    }

    /**
     * Method invoked when an asynchronous call results in a validation error.
     * Child classes can override this method to implement specific validation behavior.
     * @param caught  Validation error that was caught
     * @return True if the validation error was handled, false otherwise.
     */
    @Override
    public boolean onValidationError(InvalidDataException caught) {
        return false;
    }

    /**
     * Method invoked when an asynchronous call results in an unhandled error.
     * Child classes can override this method to implement specific error-handling behavior.
     * @param caught  Unhandled error that was caught
     */
    @Override
    public void onUnhandledError(Throwable caught) {
        boolean handled = this.handleSpecialErrors(caught);
        if (!handled) {
            ErrorDescription error = this.generateError(caught);
            this.showError(error);
        }
    }

    /**
     * Hook that lets child classes handle particular exceptions in a special way.
     *
     * <p>
     * By default, this is a no-op.  Child classes can override the method,
     * implement their own behavior for specific exceptions, and return true if
     * the exception was handled in a special way.  A typical use case might be
     * to handle the non-standard AUTHENTICATION_TIMEOUT status and redirect the
     * user to a login page rather than showing them an RPC error.
     * </p>
     *
     * @param caught  Unhandled error that was caught
     *
     * @return True if the exception has been handled, false to follow the existing error-handling logic.
     */
    public boolean handleSpecialErrors(Throwable caught) {
        return false; // default implementation is a no-op
    }

    /** Get descriptive call state, like "(1 of 2 attempts)", possibly empty. */
    @Override
    public String getDescriptiveCallState() {
        return this.maxAttempts > 1 ? " (attempt " + this.attempts + " of " + this.maxAttempts + ")" : "";
    }

    /** Get the elapsed time for the current call, as a string. */
    @Override
    public String getElapsedTime() {
        if (this.timer != null) {
            this.timer.stop();
            return ", elapsed: " + this.timer.getElapsedTimeString();
        } else {
            return "";
        }
    }

    /** Start the GWT timer. */
    protected void startTimer() {
        this.timer = new GwtTimer();
        this.timer.start();
    }

    /** Increment the number of of attempts that have been made. */
    protected void incrementAttempts() {
        this.attempts += 1;
    }

    /**
     * Generate the correct error for a caught exception.
     *
     * <p>
     * Child classes can override this if there are other application-specific
     * exceptions they want to handle in specific ways.  The pattern to follow
     * is something like:
     * </p>
     *
     * <pre>
     * try {
     *    throw caught;
     * } catch (MySpecialException exception) {
     *    return new MySpecialErrorDescription(exception);
     * } catch (Throwable exception) {
     *    return super.generateError(exception);
     * }
     * </pre>
     *
     * <p>
     * This probably isn't that common of a use-case, but it is safe to do it
     * if necessary.
     * </p>
     *
     * @param caught Exception to check
     * @return Error description for the passed-in exception.
     */
    public ErrorDescription generateError(Throwable caught) {
        try {
            throw caught;
        } catch (StatusCodeException exception) {
            if (exception.getStatusCode() == 0) {
                // No idea why a status code of zero means "nothing received"...?
                return this.generateNoResponseReceivedError(exception);
            } else {
                HttpStatusCode statusCode = HttpStatusCode.convert(exception.getStatusCode());
                switch(statusCode) {
                case UNAUTHORIZED:
                case FORBIDDEN:
                    return this.generateNotAuthorizedError(statusCode);
                default:
                    return this.generateGeneralRpcError(exception, statusCode);
                }
            }
        } catch (RpcSecurityException exception) {
            return this.generateRpcSecurityExceptionError(exception);
        } catch (RpcTokenException exception) {
            return this.generateRpcTokenExceptionError(exception);
        } catch (RequestTimeoutException exception) {
            return this.generateRequestTimeoutExceptionError(exception);
        } catch (IncompatibleRemoteServiceException exception) {
            return this.generateIncompatibleRemoteServiceExceptionError(exception);
        } catch (Throwable exception) {
            return this.generateGeneralRpcError(exception);
        }
    }

    /**
     * Whether an exception is retryable.
     *
     * <p>
     * Child classes can override this if there are other application-specific
     * exceptions they want to retry.  The pattern to follow is something like:
     * </p>
     *
     * <pre>
     * try {
     *    throw caught;
     * } catch (MySpecialException exception) {
     *    return true;
     * } catch (Throwable exception) {
     *    return super.isExceptionRetryable(caught);
     * }
     * </pre>
     *
     * <p>
     * Think carefully before you make other exceptions retryable. The best
     * candidates are exceptions where you can reliably tell that a retry
     * will make a difference (i.e. you can tell that the network dropped
     * but if it came back you might get a different result).
     * </p>
     *
     * <p>
     * In the default implementation, only RequestTimeoutException is retried.
     * This exception indicates a timeout invoking an RPC.  I considered handling
     * InvocationException in a special way, because it's documented to happen
     * if the RPC can't be invoked at all.  However, InvocationException can also
     * occur for unrelated reasons (mainly undeclared exceptions thrown from the
     * server-side service layer), so I've decided to ignore it for now.
     * </p>
     *
     * @param caught Exception to check
     * @return True if the exception is retryable, false otherwise.
     *
     * @see <a href="https://developers.google.com/web-toolkit/doc/latest/DevGuideServerCommunication">DevGuideServerCommunication</a>
     */
    public boolean isExceptionRetryable(Throwable caught) {
        try {
            throw caught;
        } catch (RequestTimeoutException exception) {
            return true;
        } catch (Throwable exception) {
            return false;
        }
    }

    public A getAsync() {
        return this.async;
    }

    public String getRpc() {
        return this.rpc;
    }

    public String getMethod() {
        return this.method;
    }

    public int getAttempts() {
        return this.attempts;
    }

    public String getCallerId() {
        return this.callerId;
    }

    public int getMaxAttempts() {
        return this.maxAttempts;
    }

    public void setMaxAttempts(int maxAttempts) {
        this.maxAttempts = maxAttempts;
    }

}
