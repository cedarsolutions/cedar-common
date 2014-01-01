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
package com.cedarsolutions.wiring.gwt.rpc;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.Test;
import org.mockito.Mockito;

import com.cedarsolutions.client.gwt.rpc.proxy.XsrfRpcProxy;
import com.google.gwt.core.ext.typeinfo.JClassType;
import com.google.gwt.core.ext.typeinfo.JMethod;
import com.google.gwt.core.ext.typeinfo.JParameter;
import com.google.gwt.core.ext.typeinfo.JType;
import com.google.gwt.user.server.rpc.NoXsrfProtect;
import com.google.gwt.user.server.rpc.XsrfProtect;

/**
 * Unit tests for XsrfRpcProxyCreator.
 * @author Kenneth J. Pronovici <pronovic@ieee.org>
 */
public class XsrfRpcProxyCreatorTest {

    /** Test getProxySupertype(). */
    @Test public void testGetProxySuperType() {
        JClassType type = mock(JClassType.class);
        XsrfRpcProxyCreator creator = new XsrfRpcProxyCreator(type);
        assertEquals(XsrfRpcProxy.class, creator.getProxySupertype());
        assertSame(type, creator.getType());
    }

    /** Test isMethodXsrfProtected(). */
    @Test public void testIsMethodXsrfProtected() {
        // Declare some standard classes
        JClassType protectedType = mock(JClassType.class);
        when(protectedType.isAnnotationPresent(XsrfProtect.class)).thenReturn(true);
        when(protectedType.isAnnotationPresent(NoXsrfProtect.class)).thenReturn(false);

        JClassType notProtectedType = mock(JClassType.class);
        when(notProtectedType.isAnnotationPresent(XsrfProtect.class)).thenReturn(false);
        when(notProtectedType.isAnnotationPresent(NoXsrfProtect.class)).thenReturn(true);

        JClassType noAnnotationType = mock(JClassType.class);
        when(noAnnotationType.isAnnotationPresent(XsrfProtect.class)).thenReturn(false);
        when(noAnnotationType.isAnnotationPresent(NoXsrfProtect.class)).thenReturn(false);

        JClassType bothAnnotationType = mock(JClassType.class);
        when(bothAnnotationType.isAnnotationPresent(XsrfProtect.class)).thenReturn(true);
        when(bothAnnotationType.isAnnotationPresent(NoXsrfProtect.class)).thenReturn(true);

        // Declare some standard methods
        JMethod protectedMethod = mock(JMethod.class);
        when(protectedMethod.isAnnotationPresent(XsrfProtect.class)).thenReturn(true);
        when(protectedMethod.isAnnotationPresent(NoXsrfProtect.class)).thenReturn(false);

        JMethod notProtectedMethod = mock(JMethod.class);
        when(notProtectedMethod.isAnnotationPresent(XsrfProtect.class)).thenReturn(false);
        when(notProtectedMethod.isAnnotationPresent(NoXsrfProtect.class)).thenReturn(true);

        JMethod noAnnotationMethod = mock(JMethod.class);
        when(noAnnotationMethod.isAnnotationPresent(XsrfProtect.class)).thenReturn(false);
        when(noAnnotationMethod.isAnnotationPresent(NoXsrfProtect.class)).thenReturn(false);

        JMethod bothAnnotationMethod = mock(JMethod.class);
        when(bothAnnotationMethod.isAnnotationPresent(XsrfProtect.class)).thenReturn(true);
        when(bothAnnotationMethod.isAnnotationPresent(NoXsrfProtect.class)).thenReturn(true);

        // Test a protected method
        when(protectedMethod.getEnclosingType()).thenReturn(protectedType);
        assertTrue(XsrfRpcProxyCreator.isMethodXsrfProtected(protectedMethod));

        when(protectedMethod.getEnclosingType()).thenReturn(notProtectedType);
        assertTrue(XsrfRpcProxyCreator.isMethodXsrfProtected(protectedMethod));

        when(protectedMethod.getEnclosingType()).thenReturn(noAnnotationType);
        assertTrue(XsrfRpcProxyCreator.isMethodXsrfProtected(protectedMethod));

        when(protectedMethod.getEnclosingType()).thenReturn(bothAnnotationType);
        assertTrue(XsrfRpcProxyCreator.isMethodXsrfProtected(protectedMethod));

        // Test a method that is explicitly not protected
        when(notProtectedMethod.getEnclosingType()).thenReturn(protectedType);
        assertFalse(XsrfRpcProxyCreator.isMethodXsrfProtected(notProtectedMethod));

        when(notProtectedMethod.getEnclosingType()).thenReturn(notProtectedType);
        assertFalse(XsrfRpcProxyCreator.isMethodXsrfProtected(notProtectedMethod));

        when(notProtectedMethod.getEnclosingType()).thenReturn(noAnnotationType);
        assertFalse(XsrfRpcProxyCreator.isMethodXsrfProtected(notProtectedMethod));

        when(notProtectedMethod.getEnclosingType()).thenReturn(bothAnnotationType);
        assertFalse(XsrfRpcProxyCreator.isMethodXsrfProtected(notProtectedMethod));

        // Test a method that is explicitly not annotated at all
        when(noAnnotationMethod.getEnclosingType()).thenReturn(protectedType);
        assertTrue(XsrfRpcProxyCreator.isMethodXsrfProtected(noAnnotationMethod));

        when(noAnnotationMethod.getEnclosingType()).thenReturn(notProtectedType);
        assertFalse(XsrfRpcProxyCreator.isMethodXsrfProtected(noAnnotationMethod));

        when(noAnnotationMethod.getEnclosingType()).thenReturn(noAnnotationType);
        assertFalse(XsrfRpcProxyCreator.isMethodXsrfProtected(noAnnotationMethod));

        when(noAnnotationMethod.getEnclosingType()).thenReturn(bothAnnotationType);
        assertTrue(XsrfRpcProxyCreator.isMethodXsrfProtected(noAnnotationMethod));

        // Test a method that is annotated with both annotations
        when(bothAnnotationMethod.getEnclosingType()).thenReturn(protectedType);
        assertTrue(XsrfRpcProxyCreator.isMethodXsrfProtected(bothAnnotationMethod));

        when(bothAnnotationMethod.getEnclosingType()).thenReturn(notProtectedType);
        assertTrue(XsrfRpcProxyCreator.isMethodXsrfProtected(bothAnnotationMethod));

        when(bothAnnotationMethod.getEnclosingType()).thenReturn(noAnnotationType);
        assertTrue(XsrfRpcProxyCreator.isMethodXsrfProtected(bothAnnotationMethod));

        when(bothAnnotationMethod.getEnclosingType()).thenReturn(bothAnnotationType);
        assertTrue(XsrfRpcProxyCreator.isMethodXsrfProtected(bothAnnotationMethod));
    }

    /** Test generateInterfaceRpcMethod(). */
    @Test public void testGenerateInterfaceRpcMethod() {
        XsrfRpcProxyCreator creator = createMockedCreator();

        StringSourceWriter writer = new StringSourceWriter();
        JMethod asyncMethod = createMockedAsyncMethod();

        String sequence = "XXXX";
        creator.generateInterfaceRpcMethod(writer, asyncMethod, sequence);
        String actual = writer.toString();

        String expected = "\n" +
        "public void createExchange(java.lang.String name, com.google.gwt.user.client.rpc.AsyncCallback callback) {\n" +
        "  AbstractTokenCallback tokenCallback = new _TokenCallback_createExchange_XXXX(name, callback);\n" +
        "  com.cedarsolutions.client.gwt.rpc.IXsrfTokenRpcAsync xsrfTokenRpc = getXsrfTokenRpc();\n" +
        "  xsrfTokenRpc.generateXsrfToken(tokenCallback);\n" +
        "}\n";

        assertEquals(expected, actual);
    }

    /** Test generateTokenCallback(). */
    @Test public void testGenerateTokenCallback() {
        XsrfRpcProxyCreator creator = createMockedCreator();

        StringSourceWriter writer = new StringSourceWriter();
        JMethod asyncMethod = createMockedAsyncMethod();

        String sequence = "XXXX";
        creator.generateTokenCallback(writer, asyncMethod, sequence);
        String actual = writer.toString();

        String expected = "\n" +
        "public class _TokenCallback_createExchange_XXXX extends AbstractTokenCallback {\n" +
        "  private java.lang.String name;\n" +
        "  private com.google.gwt.user.client.rpc.AsyncCallback callback;\n" +
        "  \n" +
        "  public _TokenCallback_createExchange_XXXX(java.lang.String name, com.google.gwt.user.client.rpc.AsyncCallback callback) {\n" +
        "    super(callback);\n" +
        "    this.name = name;\n" +
        "    this.callback = callback;\n" +
        "  }\n" +
        "  \n" +
        "  @Override\n" +
        "  public void invokeRpcMethod() {\n" +
        "    _realRpcMethod_createExchange(name, callback);\n" +
        "  }\n" +
        "}\n";

        assertEquals(expected, actual);
    }

    /** Create a fully mocked XsrfRpcProxyCreator that will work for tests. */
    private static XsrfRpcProxyCreator createMockedCreator() {
        JClassType classOrInterface = mock(JClassType.class, Mockito.RETURNS_DEEP_STUBS);
        when(classOrInterface.getName()).thenReturn("IExchangeRpc");
        when(classOrInterface.getPackage().getName()).thenReturn("com.cedarsolutions.santa.client.rpc");

        JType leafType = mock(JType.class);
        when(leafType.isPrimitive()).thenReturn(null);
        when(leafType.isClassOrInterface()).thenReturn(classOrInterface);

        JClassType type = mock(JClassType.class);
        when(type.getLeafType()).thenReturn(leafType);
        when(type.getQualifiedSourceName()).thenReturn("IExchangeRpc");

        return new XsrfRpcProxyCreator(type);
    }

    /** Create a mocked asynchronous method for testing. */
    private static JMethod createMockedAsyncMethod() {
        JType stringErasedType = mock(JType.class);
        when(stringErasedType.getQualifiedSourceName()).thenReturn("java.lang.String");

        JParameter name = mock(JParameter.class, Mockito.RETURNS_DEEP_STUBS);
        when(name.getType().getErasedType()).thenReturn(stringErasedType);
        when(name.getType().getErasedType().getQualifiedSourceName()).thenReturn("java.lang.String");
        when(name.getName()).thenReturn("name");

        JType callbackErasedType = mock(JType.class);
        when(callbackErasedType.getQualifiedSourceName()).thenReturn("com.google.gwt.user.client.rpc.AsyncCallback");

        JParameter callback = mock(JParameter.class, Mockito.RETURNS_DEEP_STUBS);
        when(callback.getType().getErasedType()).thenReturn(callbackErasedType);
        when(callback.getType().getErasedType().getQualifiedSourceName()).thenReturn("com.google.gwt.user.client.rpc.AsyncCallback");
        when(callback.getName()).thenReturn("callback");

        JParameter[] parameters = new JParameter[] { name, callback, };

        JMethod asyncMethod = mock(JMethod.class, Mockito.RETURNS_DEEP_STUBS);
        when(asyncMethod.getReturnType().getErasedType().getQualifiedSourceName()).thenReturn("void");
        when(asyncMethod.getName()).thenReturn("createExchange");
        when(asyncMethod.getParameters()).thenReturn(parameters);
        return asyncMethod;
    }

}
