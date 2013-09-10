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
package com.cedarsolutions.exception;

import com.cedarsolutions.shared.domain.LocalizableMessage;

/**
 * Security-related exception related to an RPC call.
 *
 * <p>
 * This class is called RpcSecurityException rather than SecurityException
 * because it's too easy for client code to accidentally reference
 * java.lang.SecurityException, which is not translatable and causes
 * RPCs to mysteriously blow up.
 * </p>
 *
 * @author Kenneth J. Pronovici <pronovic@ieee.org>
 */
public class RpcSecurityException extends ServiceException {

    private static final long serialVersionUID = 1L;

    public RpcSecurityException() {
        super();
    }

    public RpcSecurityException(String message) {
        super(message);
    }

    public RpcSecurityException(LocalizableMessage localizableMessage) {
        super(localizableMessage);
    }

    public RpcSecurityException(String message, Throwable cause) {
        super(message, cause);
    }

    public RpcSecurityException(LocalizableMessage localizableMessage, Throwable cause) {
        super(localizableMessage, cause);
    }

}
