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
package com.cedarsolutions.web.metadata;

import com.cedarsolutions.shared.domain.IntegerEnum;


/**
 * List of the HTTP 1.1 status codes.
 * @see <a href="http://www.w3.org/Protocols/rfc2616/rfc2616-sec10.html">Status Code Definitions</a>
 * @author Kenneth J. Pronovici <pronovic@ieee.org>
 */
public enum HttpStatusCode implements IntegerEnum {

    UNKNOWN(),
    CONTINUE(100),
    SWITCHING_PROTOCOLS(101),
    OK(200),
    CREATED(201),
    ACCEPTED(202),
    NON_AUTHORITATIVE(203),
    NO_CONTENT(204),
    RESET_CONTENT(205),
    PARTIAL_CONTENT(206),
    MULTIPLE_CHOICES(300),
    MOVED_PERMANENTLY(301),
    FOUND(302),
    SEE_OTHER(303),
    NOT_MODIFIED(304),
    USE_PROXY(305),
    TEMPORARY_REDIRECT(307),
    BAD_REQUEST(400),
    UNAUTHORIZED(401),
    PAYMENT_REQUIRED(402),
    FORBIDDEN(403),
    NOT_FOUND(404),
    METHOD_NOT_ALLOWED(405),
    NOT_ACCEPTABLE(406),
    PROXY_AUTHENTICATION_REQUIRED(407),
    REQUEST_TIMEOUT(408),
    CONFLICT(409),
    GONE(410),
    LENGTH_REQUIRED(411),
    PRECONDITION_FAILED(412),
    REQUEST_ENTITY_TOO_LARGE(413),
    REQUEST_URI_TOO_LONG(414),
    UNSUPPORTED_MEDIA_TYPE(415),
    REQUEST_RANGE_NOT_SATISFIABLE(416),
    EXPECTATION_FAILED(417),
    INTERNAL_SERVER_ERROR(500),
    NOT_IMPLEMENTED(501),
    BAD_GATEWAY(502),
    SERVICE_UNAVAILABLE(503),
    GATEWAY_TIMEOUT(504),
    HTTP_VERSION_NOT_SUPPORTED(505);

    /** Underlying value of the enumeration. */
    private Integer value;

    /** Create an enumeration with a null value. */
    private HttpStatusCode() {
        this.value = null;
    }

    /** Create an enumeration with the passed-in value. */
    private HttpStatusCode(int value) {
        this.value = value;
    }

    /** Get the value associated with an HTTP status code. */
    @Override
    public Integer getValue() {
        return this.value;
    }

    /** Convert an integer value into an HTTP status code enumeration. */
    public static HttpStatusCode convert(Integer value) {
        if (value != null) {
            switch(value) {
            case 100:
                return CONTINUE;
             case 101:
                return SWITCHING_PROTOCOLS;
             case 200:
                return OK;
             case 201:
                return CREATED;
             case 202:
                return ACCEPTED;
             case 203:
                return NON_AUTHORITATIVE;
             case 204:
                return NO_CONTENT;
             case 205:
                return RESET_CONTENT;
             case 206:
                return PARTIAL_CONTENT;
             case 300:
                return MULTIPLE_CHOICES;
             case 301:
                return MOVED_PERMANENTLY;
             case 302:
                return FOUND;
             case 303:
                return SEE_OTHER;
             case 304:
                return NOT_MODIFIED;
             case 305:
                return USE_PROXY;
             case 307:
                return TEMPORARY_REDIRECT;
             case 400:
                return BAD_REQUEST;
             case 401:
                return UNAUTHORIZED;
             case 402:
                return PAYMENT_REQUIRED;
             case 403:
                return FORBIDDEN;
             case 404:
                return NOT_FOUND;
             case 405:
                return METHOD_NOT_ALLOWED;
             case 406:
                return NOT_ACCEPTABLE;
             case 407:
                return PROXY_AUTHENTICATION_REQUIRED;
             case 408:
                return REQUEST_TIMEOUT;
             case 409:
                return CONFLICT;
             case 410:
                return GONE;
             case 411:
                return LENGTH_REQUIRED;
             case 412:
                return PRECONDITION_FAILED;
             case 413:
                return REQUEST_ENTITY_TOO_LARGE;
             case 414:
                return REQUEST_URI_TOO_LONG;
             case 415:
                return UNSUPPORTED_MEDIA_TYPE;
             case 416:
                return REQUEST_RANGE_NOT_SATISFIABLE;
             case 417:
                return EXPECTATION_FAILED;
             case 500:
                return INTERNAL_SERVER_ERROR;
             case 501:
                return NOT_IMPLEMENTED;
             case 502:
                return BAD_GATEWAY;
             case 503:
                return SERVICE_UNAVAILABLE;
             case 504:
                return GATEWAY_TIMEOUT;
             case 505:
                return HTTP_VERSION_NOT_SUPPORTED;
            }
        }

        return UNKNOWN;
    }
}
