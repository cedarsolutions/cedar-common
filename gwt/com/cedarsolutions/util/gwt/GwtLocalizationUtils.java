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
package com.cedarsolutions.util.gwt;

import com.cedarsolutions.shared.domain.LocalizableMessage;
import com.google.gwt.i18n.client.ConstantsWithLookup;

/**
 * Localization utilities that are translatable to GWT client code.
 * @author Kenneth J. Pronovici <pronovic@ieee.org>
 */
public class GwtLocalizationUtils {

    /**
     * Translate a localizable message, using the passed-in configuration.
     *
     * <p>
     * Note that we have to base this functionality on the ConstantsWithLookup
     * interface.  Screen validation would be painful without lookup, so this is
     * what seems to make the most sense.  The down-side is that there's no
     * built-in argument formatting like with a real Messages interface.
     * </p>
     *
     * @param message     Message to translate
     * @param constants   GWT constants to use as translation source
     * @param prefix      Prefix to use when identifying localization constants
     *
     * @return Translated message, falling back to message.getText() if nothing else is available.
     */
    public static String translate(LocalizableMessage message, ConstantsWithLookup constants, String prefix) {
        String key = null;

        String messageKey = GwtStringUtils.trimToNull(message.getKey());
        if (messageKey != null) {
            String messageContext = GwtStringUtils.trimToNull(message.getContext());
            if (messageContext == null) {
                key = messageKey;
            } else {
                key = messageKey + "_" + messageContext;
            }
        }

        return translate(constants, prefix, key, message.getText());
    }

    /**
     * Translate a key, using the passed-in configuration.
     *
     * <p>
     * Note that we have to base this functionality on the ConstantsWithLookup
     * interface. The down-side is that there's no  built-in argument formatting
     * like with a real Messages interface.
     * </p>
     *
     * @param constants     GWT constants to use as translation source
     * @param prefix        Prefix to use when identifying localization constants
     * @param key           Key to look up
     * @param defaultValue  Default value to use if no translation is available for key
     *
     * @return Translated value for the key, falling back on the default if no translation is available.
     */
    public static String translate(ConstantsWithLookup constants, String prefix, String key, String defaultValue) {
        if (constants == null) {
            return defaultValue;
        } else {
            key = GwtStringUtils.trimToNull(key);
            if (key == null) {
                return defaultValue;
            } else {
                try {
                    String result = constants.getString(prefix + "_" + key);
                    return result != null ? result : defaultValue;
                } catch (Exception e) {
                    return defaultValue;
                }
            }
        }
    }

}
