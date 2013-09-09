/* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 *
 *              C E D A R
 *          S O L U T I O N S       "Software done right."
 *           S O F T W A R E
 *
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 *
 * Copyright (c) 2012-2013 Kenneth J. Pronovici.
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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;

import org.junit.Test;

import com.cedarsolutions.exception.InvalidDataException;
import com.cedarsolutions.exception.RpcSecurityException;
import com.cedarsolutions.shared.domain.ErrorDescription;
import com.cedarsolutions.web.metadata.HttpStatusCode;
import com.google.gwt.http.client.RequestTimeoutException;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.rpc.IncompatibleRemoteServiceException;
import com.google.gwt.user.client.rpc.RpcTokenException;
import com.google.gwt.user.client.rpc.StatusCodeException;


/**
 * Unit tests for AbstractRpcCaller.
 * @author Kenneth J. Pronovici <pronovic@ieee.org>
 */
@SuppressWarnings("unchecked")
public class AbstractRpcCallerTest {

    /** Test the constructor. */
    @Test public void testConstructor() {
        Caller caller = new Caller();
        assertTrue(caller.getAsync() instanceof IDummyAsync);
        assertEquals("rpc", caller.getRpc());
        assertEquals("method", caller.getMethod());
        assertEquals(0, caller.getAttempts());
        assertEquals("NextCallerId0", caller.getCallerId());
        assertEquals(1, caller.getMaxAttempts());
    }

    /** Test maxAttempts. */
    @Test public void testMaxAttempts() {
        Caller caller = new Caller();
        assertEquals(1, caller.getMaxAttempts());
        caller.setMaxAttempts(12);
        assertEquals(12, caller.getMaxAttempts());
    }

    /** Test getDescriptiveCallerId(). */
    @Test public void testGetDescriptiveCallerId() {
        Caller caller = new Caller();
        assertEquals("RPC [NextCallerId0] rpc.method()", caller.getDescriptiveCallerId());
    }

    /** Test getDescriptiveCallState(). */
    @Test public void testGetDescriptiveCallState() {
        Caller caller = new Caller();
        assertEquals("", caller.getDescriptiveCallState());

        caller.setMaxAttempts(3);
        caller.incrementAttempts();
        assertEquals(" (attempt 1 of 3)", caller.getDescriptiveCallState());
    }

    /** Test getRpcMethod(). */
    @Test public void testGetRpcMethod() {
        Caller caller = new Caller();
        assertEquals("rpc.method()", caller.getRpcMethod());
    }

    /** Test createCallback(). */
    @Test public void testCreateCallback() {
        Caller caller = new Caller();
        IRpcCallback<String> callback = caller.createCallback();
        assertNotNull(callback);
        assertTrue(callback instanceof RpcCallback);
    }

    /** Test isAnotherAttemptAllowed(). */
    @Test public void testIsAnotherAttemptAllowed() {
        Caller caller = new Caller();
        caller.setMaxAttempts(3);

        // no attempts made so far (allowed for some exceptions)
        assertFalse(caller.isAnotherAttemptAllowed(null));
        assertTrue(caller.isAnotherAttemptAllowed(new RequestTimeoutException(null, 0)));
        assertFalse(caller.isAnotherAttemptAllowed(new Exception("Hello")));

        // 1 attempt made so far (allowed for some exceptions)
        caller.incrementAttempts();
        assertFalse(caller.isAnotherAttemptAllowed(null));
        assertTrue(caller.isAnotherAttemptAllowed(new RequestTimeoutException(null, 0)));
        assertFalse(caller.isAnotherAttemptAllowed(new Exception("Hello")));

        // 2 attempts made so far (allowed for some exceptions)
        caller.incrementAttempts();
        assertFalse(caller.isAnotherAttemptAllowed(null));
        assertTrue(caller.isAnotherAttemptAllowed(new RequestTimeoutException(null, 0)));
        assertFalse(caller.isAnotherAttemptAllowed(new Exception("Hello")));

        // 3 attempts made so far (disallowed because we've already made the maximum number of retries)
        caller.incrementAttempts();
        assertFalse(caller.isAnotherAttemptAllowed(null));
        assertFalse(caller.isAnotherAttemptAllowed(new RequestTimeoutException(null, 0)));
        assertFalse(caller.isAnotherAttemptAllowed(new Exception("Hello")));
    }

    /** Test invoke(). */
    @Test public void testInvokeNoCallback() {
        Caller caller = new Caller();
        caller.invoke();
        assertSame(caller.getAsync(), caller.dummy);
        assertNotNull(caller.callback);
        assertTrue(caller.callback instanceof RpcCallback);
    }

    /** Test invoke() with a specific callback, no retries. */
    @Test public void testInvokeWithCallbackNoRetries() {
        IRpcCallback<String> callback = mock(IRpcCallback.class);
        Caller caller = new Caller();
        caller.invoke(callback);
        assertTrue(caller.applyPoliciesCalled);
        assertTrue(caller.showProgressIndicatorCalled);
        assertSame(caller.getAsync(), caller.dummy);
        assertSame(callback, caller.callback);
        assertEquals("RPC [NextCallerId0] rpc.method(): start", caller.message);
    }

    /** Test invoke() with a specific callback, with retries. */
    @Test public void testInvokeWithCallbackWithRetries() {
        IRpcCallback<String> callback1 = mock(IRpcCallback.class);
        IRpcCallback<String> callback2 = mock(IRpcCallback.class);
        IRpcCallback<String> callback3 = mock(IRpcCallback.class);

        Caller caller = new Caller();
        caller.setMaxAttempts(2);

        caller.invoke(callback1);
        assertSame(caller.getAsync(), caller.dummy);
        assertSame(callback1, caller.callback);
        assertEquals("RPC [NextCallerId0] rpc.method(): start (attempt 1 of 2)", caller.message);

        caller.invoke(callback2);
        assertSame(caller.getAsync(), caller.dummy);
        assertSame(callback2, caller.callback);
        assertEquals("RPC [NextCallerId0] rpc.method(): start (attempt 2 of 2)", caller.message);

        // no validation is done: callers are expected to check whether retries are allowed
        caller.invoke(callback3);
        assertSame(caller.getAsync(), caller.dummy);
        assertSame(callback3, caller.callback);
        assertEquals("RPC [NextCallerId0] rpc.method(): start (attempt 3 of 2)", caller.message);
    }

    /** Test onValidationError(). */
    @Test public void testOnValidationError() {
        Caller caller = new Caller();
        assertFalse(caller.onValidationError(null));
    }

    /** Test onUnhandledError() for HTTP error (FORBIDDEN). */
    @Test public void testOnUnhandledError1() {
        Throwable exception = new StatusCodeException(HttpStatusCode.FORBIDDEN.getValue(), "whatever");
        Caller caller = new Caller();
        caller.onUnhandledError(exception);

        assertEquals("generateNotAuthorized", caller.error.getMessage());
        assertEquals(HttpStatusCode.FORBIDDEN, caller.statusCode);
        assertNull(caller.invalidDataException);
        assertNull(caller.rpcSecurityException);
        assertNull(caller.rpcTokenException);
        assertNull(caller.requestTimeoutException);
        assertNull(caller.incompatibleRemoteServiceException);
        assertNull(caller.exception);
        assertNull(caller.result);
    }

    /** Test onUnhandledError() for HTTP error (not FORBIDDEN). */
    @Test public void testOnUnhandledError2() {
        Throwable exception = new StatusCodeException(HttpStatusCode.INTERNAL_SERVER_ERROR.getValue(), "whatever");
        Caller caller = new Caller();
        caller.onUnhandledError(exception);

        assertEquals("generateGeneralRpcErrorWithStatus", caller.error.getMessage());
        assertEquals(HttpStatusCode.INTERNAL_SERVER_ERROR, caller.statusCode);
        assertNull(caller.invalidDataException);
        assertNull(caller.rpcSecurityException);
        assertNull(caller.rpcTokenException);
        assertNull(caller.requestTimeoutException);
        assertNull(caller.incompatibleRemoteServiceException);
        assertSame(exception, caller.exception);
        assertNull(caller.result);
    }

    /** Test onUnhandledError() for RpcSecurityException. */
    @Test public void testOnUnhandledError3() {
        Throwable exception = new RpcSecurityException("Hello");
        Caller caller = new Caller();
        caller.onUnhandledError(exception);

        assertEquals("generateRpcSecurityExceptionError", caller.error.getMessage());
        assertNull(caller.statusCode);
        assertNull(caller.invalidDataException);
        assertSame(exception, caller.rpcSecurityException);
        assertNull(caller.rpcTokenException);
        assertNull(caller.requestTimeoutException);
        assertNull(caller.incompatibleRemoteServiceException);
        assertNull(caller.exception);
        assertNull(caller.result);
    }

    /** Test onUnhandledError() for RpcTokenException. */
    @Test public void testOnUnhandledError4() {
        Throwable exception = new RpcTokenException("Hello");
        Caller caller = new Caller();
        caller.onUnhandledError(exception);

        assertEquals("generateRpcTokenExceptionError", caller.error.getMessage());
        assertNull(caller.statusCode);
        assertNull(caller.invalidDataException);
        assertNull(caller.rpcSecurityException);
        assertSame(exception, caller.rpcTokenException);
        assertNull(caller.requestTimeoutException);
        assertNull(caller.incompatibleRemoteServiceException);
        assertNull(caller.exception);
        assertNull(caller.result);
    }

    /** Test onUnhandledError() for RequestTimeoutException. */
    @Test public void testOnUnhandledError5() {
        Throwable exception = new RequestTimeoutException(null, 0);
        Caller caller = new Caller();
        caller.onUnhandledError(exception);

        assertEquals("generateRequestTimeoutExceptionError", caller.error.getMessage());
        assertNull(caller.statusCode);
        assertNull(caller.invalidDataException);
        assertNull(caller.rpcSecurityException);
        assertNull(caller.rpcTokenException);
        assertSame(exception, caller.requestTimeoutException);
        assertNull(caller.incompatibleRemoteServiceException);
        assertNull(caller.exception);
        assertNull(caller.result);
    }

    /** Test onUnhandledError() for IncompatibleRemoteServiceException. */
    @Test public void testOnUnhandledError6() {
        Throwable exception = new IncompatibleRemoteServiceException();
        Caller caller = new Caller();
        caller.onUnhandledError(exception);

        assertEquals("generateIncompatibleRemoteServiceExceptionError", caller.error.getMessage());
        assertNull(caller.statusCode);
        assertNull(caller.invalidDataException);
        assertNull(caller.rpcSecurityException);
        assertNull(caller.rpcTokenException);
        assertNull(caller.requestTimeoutException);
        assertSame(exception, caller.incompatibleRemoteServiceException);
        assertNull(caller.exception);
        assertNull(caller.result);
    }

    /** Test onUnhandledError() for general RPC error. */
    @Test public void testOnUnhandledError7() {
        Throwable exception = new RuntimeException("Whatever");
        Caller caller = new Caller();
        caller.onUnhandledError(exception);

        assertEquals("generateGeneralRpcError", caller.error.getMessage());
        assertNull(caller.statusCode);
        assertNull(caller.invalidDataException);
        assertNull(caller.rpcSecurityException);
        assertNull(caller.rpcTokenException);
        assertNull(caller.requestTimeoutException);
        assertNull(caller.incompatibleRemoteServiceException);
        assertSame(exception, caller.exception);
        assertNull(caller.result);
    }

    /** Test onUnhandledError() for general RPC error (unhandled validation error). */
    @Test public void testOnUnhandledError8() {
        Throwable exception = new InvalidDataException("Whatever");
        Caller caller = new Caller();
        caller.onUnhandledError(exception);

        assertEquals("generateGeneralRpcError", caller.error.getMessage());
        assertNull(caller.statusCode);
        assertNull(caller.invalidDataException);
        assertNull(caller.rpcSecurityException);
        assertNull(caller.rpcTokenException);
        assertNull(caller.requestTimeoutException);
        assertNull(caller.incompatibleRemoteServiceException);
        assertSame(exception, caller.exception);
        assertNull(caller.result);
    }

    /** Dummy asynchronous interface. */
    protected interface IDummyAsync {
    }

    /** Concrete class that we can use for testing. */
    protected static class Caller extends AbstractRpcCaller<IDummyAsync, String> {

        protected ErrorDescription error;
        protected HttpStatusCode statusCode;
        protected InvalidDataException invalidDataException;
        protected RpcSecurityException rpcSecurityException;
        protected RpcTokenException rpcTokenException;
        protected RequestTimeoutException requestTimeoutException;
        protected IncompatibleRemoteServiceException incompatibleRemoteServiceException;
        protected Throwable exception;
        protected String result;
        protected String message;
        protected boolean showProgressIndicatorCalled;
        protected boolean applyPoliciesCalled;
        protected IDummyAsync dummy;
        protected AsyncCallback<String> callback;
        private int callerId = 0;

        protected Caller() {
            super(mock(IDummyAsync.class), "rpc", "method");
        }

        @Override
        public boolean onValidationError(InvalidDataException caught) {
            this.invalidDataException = caught;
            return super.onValidationError(caught);
        }

        @Override
        public void log(String message) {
            this.message = message;
        }

        @Override
        public void showProgressIndicator() {
            this.showProgressIndicatorCalled = true;
        }

        @Override
        public void hideProgressIndicator() {
            // this isn't used directly by the caller
        }

        @Override
        public void onSuccessResult(String result) {
            this.result = result;
        }

        @Override
        public void applyPolicies() {
            this.applyPoliciesCalled = true;
        }

        @Override
        public void invokeRpcMethod(IDummyAsync async, AsyncCallback<String> callback) {
            this.dummy = async;
            this.callback = callback;
        }

        @Override
        public String getNextCallerId() {
            return "NextCallerId" + (this.callerId++);
        }

        @Override
        public void showError(ErrorDescription error) {
            this.error = error;
        }

        @Override
        public ErrorDescription generateNotAuthorizedError(HttpStatusCode statusCode) {
            this.statusCode = statusCode;
            return new ErrorDescription("generateNotAuthorized");
        }

        @Override
        public ErrorDescription generateRpcSecurityExceptionError(RpcSecurityException exception) {
            this.rpcSecurityException = exception;
            return new ErrorDescription("generateRpcSecurityExceptionError");
        }

        @Override
        public ErrorDescription generateRpcTokenExceptionError(RpcTokenException exception) {
            this.rpcTokenException = exception;
            return new ErrorDescription("generateRpcTokenExceptionError");
        }

        @Override
        public ErrorDescription generateRequestTimeoutExceptionError(RequestTimeoutException exception) {
            this.requestTimeoutException = exception;
            return new ErrorDescription("generateRequestTimeoutExceptionError");
        }

        @Override
        public ErrorDescription generateIncompatibleRemoteServiceExceptionError(IncompatibleRemoteServiceException exception) {
            this.incompatibleRemoteServiceException = exception;
            return new ErrorDescription("generateIncompatibleRemoteServiceExceptionError");
        }

        @Override
        public ErrorDescription generateGeneralRpcError(Throwable exception) {
            this.exception = exception;
            return new ErrorDescription("generateGeneralRpcError");
        }

        @Override
        public ErrorDescription generateGeneralRpcError(Throwable exception, HttpStatusCode statusCode) {
            this.exception = exception;
            this.statusCode = statusCode;
            return new ErrorDescription("generateGeneralRpcErrorWithStatus");
        }
    }

}
