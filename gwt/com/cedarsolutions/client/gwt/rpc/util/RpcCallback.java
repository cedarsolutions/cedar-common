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

import com.cedarsolutions.exception.InvalidDataException;

/**
 * RPC callback that standardizes error-handling behavior.
 *
 * <p>
 * This class tries to standardize error-handling behavior as much
 * as possible, by delegating application decisions (like how to actually
 * display errors) to the actual application via the passed-in IRpcCaller.
 * </p>
 *
 * @param <T> Return type of the asynchronous RPC callback.
 * @author Kenneth J. Pronovici <pronovic@ieee.org>
 */
public class RpcCallback<T> implements IRpcCallback<T> {

    /** RPC caller associated with this callback. */
    private IRpcCaller<T> caller;

    /** Create a new callback. */
    protected RpcCallback(IRpcCaller<T> caller) {
        this.caller = caller;
    }

    /** Get the RPC caller associated with the callback. */
    @Override
    public IRpcCaller<T> getCaller() {
        return this.caller;
    }

    /** Invoked when an asynchronous call completes successfully. */
    @Override
    public void onSuccess(T result) {
        this.caller.hideProgressIndicator();
        this.caller.log(this.caller.getDescriptiveCallerId() + ": success" + this.caller.getDescriptiveCallState() + this.caller.getElapsedTime());
        this.caller.onSuccessResult(result);
    }

    /**
     * Invoked when an asynchronous call fails to complete normally.
     * @param caught  Exception that was caught
     */
    @Override
    public void onFailure(Throwable caught) {
        try {
            this.caller.hideProgressIndicator();
            throw caught;
        } catch (InvalidDataException exception) {
            if (this.caller.onValidationError(exception)) {
                // We got a validation error that the caller handled explicitly
                this.caller.log(this.caller.getDescriptiveCallerId() + ": validation error" +
                                this.caller.getDescriptiveCallState() + this.caller.getElapsedTime());
            } else {
                // We got a validation error that the caller didn't handle, so treat it like any other error
                this.caller.log(this.caller.getDescriptiveCallerId() + ": unhandled validation error" +
                                this.caller.getDescriptiveCallState() + this.caller.getElapsedTime());
                if (this.caller.isAnotherAttemptAllowed(exception)) {
                    this.caller.invoke(this);
                } else {
                    this.caller.onUnhandledError(exception);
                }
            }
        } catch (Throwable exception) {
            // We got a general unhandled exception
            this.caller.log(this.caller.getDescriptiveCallerId() + ": unhandled exception" +
                            this.caller.getDescriptiveCallState() + this.caller.getElapsedTime());
            if (this.caller.isAnotherAttemptAllowed(exception)) {
                this.caller.invoke(this);
            } else {
                this.caller.onUnhandledError(exception);
            }
        }
    }

}
