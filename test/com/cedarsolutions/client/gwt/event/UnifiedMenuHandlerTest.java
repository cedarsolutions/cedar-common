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
package com.cedarsolutions.client.gwt.event;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import org.junit.Test;
import org.mockito.ArgumentCaptor;

/**
 * Unit tests for UnifiedMenuHandler.
 * @author Kenneth J. Pronovici <pronovic@ieee.org>
 */
public class UnifiedMenuHandlerTest {

    /** Test the execute() method. */
    @Test public void testExecute() {
        ArgumentCaptor<UnifiedEvent> event = ArgumentCaptor.forClass(UnifiedEvent.class);
        ViewEventHandler viewEventHandler = mock(ViewEventHandler.class);
        UnifiedMenuHandler handler = new UnifiedMenuHandler(viewEventHandler);
        handler.execute();
        verify(viewEventHandler).handleEvent(event.capture());
        assertEquals(UnifiedEventType.MENU_EVENT, event.getValue().getEventType());
        assertNull(event.getValue().getClickEvent());
    }

}
