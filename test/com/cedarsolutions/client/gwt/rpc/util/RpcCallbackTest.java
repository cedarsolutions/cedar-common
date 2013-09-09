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

import static org.junit.Assert.assertSame;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Test;

import com.cedarsolutions.exception.InvalidDataException;

/**
 * Unit tests for RpcCallback.
 * @author Kenneth J. Pronovici <pronovic@ieee.org>
 */
@SuppressWarnings("unchecked")
public class RpcCallbackTest {

    /** Test constructor. */
    @Test public void testConstructor() {
        IRpcCaller<String> caller = mock(IRpcCaller.class);
        RpcCallback<String> callback = new RpcCallback<String>(caller);
        assertSame(caller, callback.getCaller());
    }

    /** Test onSuccess(). */
    @Test public void testOnSuccess() {
        IRpcCaller<String> caller = mock(IRpcCaller.class);
        when(caller.getDescriptiveCallerId()).thenReturn("Caller");
        when(caller.getDescriptiveCallState()).thenReturn(" (1 of 2)");
        when(caller.getElapsedTime()).thenReturn(", elapsed");

        RpcCallback<String> callback = new RpcCallback<String>(caller);
        callback.onSuccess("hello");

        verify(caller).hideProgressIndicator();
        verify(caller).log("Caller: success (1 of 2), elapsed");
        verify(caller).onSuccessResult("hello");
    }

    /** Test onFailure(), validation error, handled by child. */
    @Test public void testOnFailureValidationHandled() {
        InvalidDataException caught = new InvalidDataException("caught");

        IRpcCaller<String> caller = mock(IRpcCaller.class);
        when(caller.getDescriptiveCallerId()).thenReturn("Caller");
        when(caller.getDescriptiveCallState()).thenReturn(" (1 of 2)");
        when(caller.getElapsedTime()).thenReturn(", elapsed");
        when(caller.onValidationError(caught)).thenReturn(true);        // handled by child

        RpcCallback<String> callback = new RpcCallback<String>(caller);
        callback.onFailure(caught);

        verify(caller).hideProgressIndicator();
        verify(caller).log("Caller: validation error (1 of 2), elapsed");
    }

    /** Test onFailure(), validation error, not handled by child, no retry allowed. */
    @Test public void testOnFailureValidationNotHandledNoRetryAllowed() {
        InvalidDataException caught = new InvalidDataException("caught");

        IRpcCaller<String> caller = mock(IRpcCaller.class);
        when(caller.getDescriptiveCallerId()).thenReturn("Caller");
        when(caller.getDescriptiveCallState()).thenReturn(" (1 of 2)");
        when(caller.getElapsedTime()).thenReturn(", elapsed");
        when(caller.onValidationError(caught)).thenReturn(false);       // not handled by child
        when(caller.isAnotherAttemptAllowed(caught)).thenReturn(false); // no retry allowed

        RpcCallback<String> callback = new RpcCallback<String>(caller);
        callback.onFailure(caught);

        verify(caller).hideProgressIndicator();
        verify(caller).log("Caller: unhandled validation error (1 of 2), elapsed");
        verify(caller, times(0)).invoke(callback);
        verify(caller).onUnhandledError(caught);
    }

    /** Test onFailure(), validation error, not handled by child, retry allowed. */
    @Test public void testOnFailureValidationNotHandledRetryAllowed() {
        InvalidDataException caught = new InvalidDataException("caught");

        IRpcCaller<String> caller = mock(IRpcCaller.class);
        when(caller.getDescriptiveCallerId()).thenReturn("Caller");
        when(caller.getDescriptiveCallState()).thenReturn(" (1 of 2)");
        when(caller.getElapsedTime()).thenReturn(", elapsed");
        when(caller.onValidationError(caught)).thenReturn(false);       // not handled by child
        when(caller.isAnotherAttemptAllowed(caught)).thenReturn(true);  // retry allowed

        RpcCallback<String> callback = new RpcCallback<String>(caller);
        callback.onFailure(caught);

        verify(caller).hideProgressIndicator();
        verify(caller).log("Caller: unhandled validation error (1 of 2), elapsed");
        verify(caller).invoke(callback);
        verify(caller, times(0)).onUnhandledError(caught);
    }

    /** Test onFailure(), unhandled exception, no retry allowed. */
    @Test public void testOnFailureUnhandledNoRetryAllowed() {
        Exception caught = new Exception("caught");

        IRpcCaller<String> caller = mock(IRpcCaller.class);
        when(caller.getDescriptiveCallerId()).thenReturn("Caller");
        when(caller.getDescriptiveCallState()).thenReturn(" (1 of 2)");
        when(caller.getElapsedTime()).thenReturn(", elapsed");
        when(caller.isAnotherAttemptAllowed(caught)).thenReturn(false); // retry not allowed

        RpcCallback<String> callback = new RpcCallback<String>(caller);
        callback.onFailure(caught);

        verify(caller).hideProgressIndicator();
        verify(caller).log("Caller: unhandled exception (1 of 2), elapsed");
        verify(caller, times(0)).invoke(callback);
        verify(caller).onUnhandledError(caught);
    }

    /** Test onFailure(), unhandled exception, retry allowed. */
    @Test public void testOnFailureUnhandledRetryAllowed() {
        Exception caught = new Exception("caught");

        IRpcCaller<String> caller = mock(IRpcCaller.class);
        when(caller.getDescriptiveCallerId()).thenReturn("Caller");
        when(caller.getDescriptiveCallState()).thenReturn(" (1 of 2)");
        when(caller.getElapsedTime()).thenReturn(", elapsed");
        when(caller.isAnotherAttemptAllowed(caught)).thenReturn(true);  // retry allowed

        RpcCallback<String> callback = new RpcCallback<String>(caller);
        callback.onFailure(caught);

        verify(caller).hideProgressIndicator();
        verify(caller).log("Caller: unhandled exception (1 of 2), elapsed");
        verify(caller).invoke(callback);
        verify(caller, times(0)).onUnhandledError(caught);
    }

}
