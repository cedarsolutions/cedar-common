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
import com.google.gwt.user.client.rpc.AsyncCallback;

/**
 * RPC caller that a callback interacts with.
 * @param <T> Return type of the RPC method
 * @author Kenneth J. Pronovici <pronovic@ieee.org>
 */
public interface IRpcCaller<T> {

    /** Get this caller's descriptive identifier. */
    String getDescriptiveCallerId();

    /** Get descriptive call state, like "(1 of 2 attempts)", possibly empty. */
    String getDescriptiveCallState();

    /** Get the elapsed time for the current call, as a string. */
    String getElapsedTime();

    /**
     * Whether another RPC method call attempt should be made.
     * @param caught  Exception that caused the situation
     * @return True if retry should be made, false otherwise.
     */
    boolean isAnotherAttemptAllowed(Throwable caught);

    /** Invoke the RPC method with the proper arguments, creating a new callback. */
    void invoke();

    /**
     * Invoke the RPC method with the proper arguments, using the passed-in callback.
     * Callers are expected to check in with isAnotherAttemptAllowed() before invoking this method.
     * @param callback  Callback to use when invoking the RPC method.
     */
    void invoke(AsyncCallback<T> callback);

    /** Log a message to an appropriate logger mechanism, like GWT.log(). */
    void log(String message);

    /** Show the front-end progress indicator. */
    void showProgressIndicator();

    /** Hide the front-end progress indicator, if any. */
    void hideProgressIndicator();

    /**
     * Method invoked when an asynchronous call results in success.
     * @param result Result returned from the asynchronous call
     */
    void onSuccessResult(T result);

    /**
     * Method invoked when an asynchronous call results in a validation error.
     * @param caught  Validation error that was caught
     * @return True if the validation error was handled, false otherwise.
     */
    boolean onValidationError(InvalidDataException caught);

    /**
     * Method invoked when an asynchronous call results in an unhandled error.
     * @param caught  Unhandled error that was caught
     */
    void onUnhandledError(Throwable caught);

}
