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

import com.cedarsolutions.shared.domain.StringEnum;

/**
 * List of browser-supported native event types.
 * @see <a href="http://quirksmode.org/dom/events/index.html">Event Compatibility Tables</a>
 * @see <a href="http://stackoverflow.com/questions/5185078/what-is-the-list-of-valid-gwt-dom-consumed-events-for-cells">Stack Overflow</a>
 * @author Kenneth J. Pronovici <pronovic@ieee.org>
 */
public enum NativeEventType implements StringEnum {

    UNKNOWN(),
    BLUR("blur"),
    CHANGE("change"),
    CLICK("click"),
    CONTEXTMENU("contextmenu"),
    COPY("copy"),
    CUT("cut"),
    DBCLICK("dblclick"),
    ERROR("error"),
    FOCUS("focus"),
    FOCUSIN("focusin"),
    FOCUSOUT("focusout"),
    HASHCHANGE("hashchange"),
    KEYDOWN("keydown"),
    KEYPRESS("keypress"),
    KEYUP("keyup"),
    LOAD("load"),
    MOUSEDOWN("mousedown"),
    MOUSEENTER("mouseenter"),
    MOUSELEAVE("mouseleave"),
    MOUSEMOVE("mousemove"),
    MOUSEOUT("mouseout"),
    MOUSEOVER("mouseover"),
    MOUSEUP("mouseup"),
    MOUSEWHEEL("mousewheel"),
    PASTE("paste"),
    RESET("reset"),
    RESIZE("resize"),
    SCROLL("scroll"),
    SELECT("select"),
    SUBMIT("submit"),
    TEXTINPUT("textinput"),
    UNLOAD("unload"),
    WHEEL("wheel");

    /** Underlying value of the enumeration. */
    private String value;

    /** Create an enumeration with a null value. */
    private NativeEventType() {
        this.value = null;
    }

    /** Create an enumeration with the passed-in value. */
    private NativeEventType(String value) {
        this.value = value;
    }

    /** Get the value associated with an HTTP status code. */
    @Override
    public String getValue() {
        return this.value;
    }

    /** Convert a string value into a NativeEventType enumeration. */
    public static NativeEventType convert(String value) {
        if ("blur".equals(value)) {
            return NativeEventType.BLUR;
        } else if ("change".equals(value)) {
            return NativeEventType.CHANGE;
        } else if ("click".equals(value)) {
            return NativeEventType.CLICK;
        } else if ("contextmenu".equals(value)) {
            return NativeEventType.CONTEXTMENU;
        } else if ("copy".equals(value)) {
            return NativeEventType.COPY;
        } else if ("cut".equals(value)) {
            return NativeEventType.CUT;
        } else if ("dblclick".equals(value)) {
            return NativeEventType.DBCLICK;
        } else if ("error".equals(value)) {
            return NativeEventType.ERROR;
        } else if ("focus".equals(value)) {
            return NativeEventType.FOCUS;
        } else if ("focusin".equals(value)) {
            return NativeEventType.FOCUSIN;
        } else if ("focusout".equals(value)) {
            return NativeEventType.FOCUSOUT;
        } else if ("hashchange".equals(value)) {
            return NativeEventType.HASHCHANGE;
        } else if ("keydown".equals(value)) {
            return NativeEventType.KEYDOWN;
        } else if ("keypress".equals(value)) {
            return NativeEventType.KEYPRESS;
        } else if ("keyup".equals(value)) {
            return NativeEventType.KEYUP;
        } else if ("load".equals(value)) {
            return NativeEventType.LOAD;
        } else if ("mousedown".equals(value)) {
            return NativeEventType.MOUSEDOWN;
        } else if ("mouseenter".equals(value)) {
            return NativeEventType.MOUSEENTER;
        } else if ("mouseleave".equals(value)) {
            return NativeEventType.MOUSELEAVE;
        } else if ("mousemove".equals(value)) {
            return NativeEventType.MOUSEMOVE;
        } else if ("mouseout".equals(value)) {
            return NativeEventType.MOUSEOUT;
        } else if ("mouseover".equals(value)) {
            return NativeEventType.MOUSEOVER;
        } else if ("mouseup".equals(value)) {
            return NativeEventType.MOUSEUP;
        } else if ("mousewheel".equals(value)) {
            return NativeEventType.MOUSEWHEEL;
        } else if ("paste".equals(value)) {
            return NativeEventType.PASTE;
        } else if ("reset".equals(value)) {
            return NativeEventType.RESET;
        } else if ("resize".equals(value)) {
            return NativeEventType.RESIZE;
        } else if ("scroll".equals(value)) {
            return NativeEventType.SCROLL;
        } else if ("select".equals(value)) {
            return NativeEventType.SELECT;
        } else if ("submit".equals(value)) {
            return NativeEventType.SUBMIT;
        } else if ("textinput".equals(value)) {
            return NativeEventType.TEXTINPUT;
        } else if ("unload".equals(value)) {
            return NativeEventType.UNLOAD;
        } else if ("wheel".equals(value)) {
            return NativeEventType.WHEEL;
        } else {
           return NativeEventType.UNKNOWN;
        }
    }
}
