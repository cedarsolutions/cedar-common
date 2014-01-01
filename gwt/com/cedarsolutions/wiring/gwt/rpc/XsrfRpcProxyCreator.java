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

import com.cedarsolutions.client.gwt.rpc.proxy.XsrfRpcProxy;
import com.google.gwt.core.ext.typeinfo.JClassType;
import com.google.gwt.core.ext.typeinfo.JMethod;
import com.google.gwt.core.ext.typeinfo.JParameter;
import com.google.gwt.core.ext.typeinfo.JType;
import com.google.gwt.core.ext.typeinfo.TypeOracle;
import com.google.gwt.user.client.rpc.impl.RemoteServiceProxy;
import com.google.gwt.user.rebind.SourceWriter;
import com.google.gwt.user.rebind.rpc.ProxyCreator;
import com.google.gwt.user.rebind.rpc.SerializableTypeOracle;
import com.google.gwt.user.server.rpc.NoXsrfProtect;
import com.google.gwt.user.server.rpc.XsrfProtect;

/**
 * Create a customized remote service proxy that knows how to make CSRF/XSRF-protected requests.
 *
 * <p>
 * When you request an RPC interface with <code>GWT.create(MyRemoteService.class)</code>,
 * GWT normally does some magic to implement an asynchronous proxy over your
 * remote service interface.  Below, we generate customized code that knows how
 * to call the CSRF/XSRF token service before making the actual RPC call.  That
 * way, RPC clients don't need to know anything about the way the service is
 * actually invoked &mdash; it's all controlled by annotations.
 * </p>
 *
 * <p>
 * The customization is conceptually very simple: for every relevant remote procedure
 * call, we want to insert a different RPC call that happens first.  The first RPC
 * call invokes the standard GWT XSRF/CSRF token service and gets a cryptographic
 * token that's needed by the real RPC call.  Once we have the token, the real RPC
 * call is made as usual.  The implementation isn't quite that simple, but hopefully
 * it won't be too brittle as time goes on.
 * </p>
 *
 * @see <a href="https://developers.google.com/web-toolkit/doc/latest/DevGuideSecurityRpcXsrf">GWT RPC XSRF protection</a>
 * @author Kenneth J. Pronovici <pronovic@ieee.org>
 */
public class XsrfRpcProxyCreator extends ProxyCreator {

    /** Configured type. */
    protected JClassType type;

    /** Number of methods that have been XSRF protected so far. */
    protected int handled;

    /** Instantiates a new proxy creator. */
    public XsrfRpcProxyCreator(JClassType type) {
        super(type);
        this.type = type;
        this.handled = 0;
    }

    /** Return our customized proxy supertype. */
    @Override
    protected Class<? extends RemoteServiceProxy> getProxySupertype() {
        return XsrfRpcProxy.class;
    }

    /** Get the configured type. */
    public JClassType getType() {
        return this.type;
    }

    /** Generates the client's asynchronous proxy method and related utility code. */
    @Override
    protected void generateProxyMethod(SourceWriter w, SerializableTypeOracle serializableTypeOracle,
                                       TypeOracle typeOracle, JMethod syncMethod, JMethod asyncMethod) {
        if (!isMethodXsrfProtected(syncMethod)) {
            super.generateProxyMethod(w, serializableTypeOracle, typeOracle, syncMethod, asyncMethod);
        } else {
            String sequence = String.valueOf(++this.handled);
            generateInterfaceRpcMethod(w, asyncMethod, sequence);
            generateClientRpcMethod(w, serializableTypeOracle, typeOracle, syncMethod, asyncMethod);
            generateTokenCallback(w, asyncMethod, sequence);
        }
    }

    /**
     * Checks if specified method is XSRF protected at the class or method level.
     *
     * <p>
     * This is roughly equivalent to com.google.gwt.user.server.Util.isMethodXsrfProtected(),
     * but it doesn't deal with method return types.  No application-level RPC calls should
     * return RPC tokens, anyway.
     * </p>
     *
     * @param method  Method to check
     *
     * @return True if the method is protected, false otherwise.
     */
    protected static boolean isMethodXsrfProtected(JMethod method) {
        if (method.isAnnotationPresent(XsrfProtect.class)) {
            // Method is protected if it's annotated with @XsrfProtect
            return true;
        } else if (method.isAnnotationPresent(NoXsrfProtect.class)) {
            // Method is not protected if it's annotated as @NoXsrfProtect
            return false;
        } else {
            // Otherwise, method is protected if its class is annotated with @XsrfProtect
            return method.getEnclosingType().isAnnotationPresent(XsrfProtect.class);
        }
    }

    /**
     * Create the method that matches the RPC's interface.
     *
     * <p>
     * Given an RPC method "createExchange", we want to create a proxy
     * method that looks like this:
     * </p>
     *
     * <pre>
     *    public void createExchange(java.lang.String name, com.google.gwt.user.client.rpc.AsyncCallback callback) {
     *       AbstractTokenCallback tokenCallback = new _TokenCallback_createExchange(name, callback);
     *       com.cedarsolutions.client.gwt.rpc.IXsrfTokenRpcAsync xsrfTokenRpc = getXsrfTokenRpc();
     *       xsrfTokenRpc.generateXsrfToken(tokenCallback);
     *    }
     * </pre>
     *
     * <p>
     * The proxy method calls out to the token service before invoking
     * the actual RPC in the callback's onSuccess() method.
     * </p>
     *
     * <p>
     * The sequence parameter is incorporated into the generated callback
     * name so we don't have problems if a single RPC overloads a method name.
     * </p>
     */
    protected void generateInterfaceRpcMethod(SourceWriter w, JMethod asyncMethod, String sequence) {
        String asyncReturnType = asyncMethod.getReturnType().getErasedType().getQualifiedSourceName();
        String asyncMethodName =  asyncMethod.getName();
        JParameter[] asyncParams = asyncMethod.getParameters();
        String callbackClassName = "_TokenCallback_" + asyncMethodName + "_" + sequence;

        w.println();
        w.print("public ");
        w.print(asyncReturnType);
        w.print(" ");
        w.print(asyncMethodName + "(");
        writeMethodParameters(w, asyncParams);
        w.println(") {");
        w.indent();

        w.print("AbstractTokenCallback tokenCallback = new ");
        w.print(callbackClassName);
        w.print("(");
        writeMethodArguments(w, asyncParams);
        w.println(");");

        w.println("com.cedarsolutions.client.gwt.rpc.IXsrfTokenRpcAsync xsrfTokenRpc = getXsrfTokenRpc();");
        w.println("xsrfTokenRpc.generateXsrfToken(tokenCallback);");

        w.outdent();
        w.println("}");
    }

    /**
     * Create the method that invokes the client service.
     *
     * <p>
     * The goal here is to use exactly the same implementation as the parent
     * class (to hopefully future-proof this implementation as Google enhances
     * the RPC mechanism), but to change the method name so that it can be
     * called as expected from the token callback.
     * </p>
     */
    protected void generateClientRpcMethod(SourceWriter w, SerializableTypeOracle serializableTypeOracle,
                                           TypeOracle typeOracle, JMethod syncMethod, JMethod asyncMethod) {
        JType asyncReturnType = asyncMethod.getReturnType().getErasedType();
        String origSignature = "public " + asyncReturnType.getQualifiedSourceName() + " " + asyncMethod.getName();
        String newSignature = "public " + asyncReturnType.getQualifiedSourceName() + " _realRpcMethod_" + asyncMethod.getName();
        SourceWriter sourceWriter = new StringSourceWriter();
        super.generateProxyMethod(sourceWriter, serializableTypeOracle, typeOracle, syncMethod, asyncMethod);
        String method = sourceWriter.toString().replaceFirst(origSignature, newSignature);
        w.print(method);
    }

    /**
     * Generate the token callback class referenced by the interface method.
     *
     * <p>
     * Given an RPC method "createExchange", we want to create a token callback
     * that looks like this:
     * </p>
     *
     * <pre>
     *    public class _TokenCallback_createExchange extends AbstractTokenCallback {
     *       private java.lang.String name;
     *       private com.google.gwt.user.client.rpc.AsyncCallback callback;
     *
     *       public _TokenCallback_createExchange(java.lang.String name, com.google.gwt.user.client.rpc.AsyncCallback callback) {
     *          super(callback);
     *          this.name = name;
     *          this.callback = callback;
     *       }
     *
     *       @Override
     *       public void invokeRpcMethod() {
     *          _realRpcMethod_createExchange(name, callback);
     *       }
     *    }
     * </pre>
     *
     * <p>
     * The sequence parameter is incorporated into the generated callback
     * name so we don't have problems if a single RPC overloads a method name.
     * </p>
     */
    protected void generateTokenCallback(SourceWriter w, JMethod asyncMethod, String sequence) {
        String asyncMethodName =  asyncMethod.getName();
        JParameter[] asyncParams = asyncMethod.getParameters();
        JParameter callbackParam = asyncParams[asyncParams.length - 1];  // callback is assumed to be the last parameter
        String realMethodName = "_realRpcMethod_" + asyncMethodName;
        String callbackClassName = "_TokenCallback_" + asyncMethodName + "_" + sequence;

        w.println();
        w.print("public class ");
        w.print(callbackClassName);
        w.println(" extends AbstractTokenCallback {");
        w.indent();

        writePrivateVariables(w, asyncParams);
        w.println();

        w.print("public ");
        w.print(callbackClassName);
        w.print("(");
        writeMethodParameters(w, asyncParams);
        w.println(") {");
        w.indent();

        w.print("super(");
        w.print(callbackParam.getName());
        w.println(");");
        writeVariableAssignments(w, asyncParams);

        w.outdent();
        w.println("}");
        w.println();

        w.println("@Override");
        w.println("public void invokeRpcMethod() {");
        w.indent();

        w.print(realMethodName);
        w.print("(");
        writeMethodArguments(w, asyncParams);
        w.println(");");

        w.outdent();
        w.println("}");

        w.outdent();
        w.println("}");
    }

    /** Write a list of parameters as if they are in a method declaration. */
    protected void writeMethodParameters(SourceWriter w, JParameter[] params) {
        boolean needsComma = false;
        for (JParameter param : params) {
            if (needsComma) {
                w.print(", ");
            } else {
                needsComma = true;
            }

            w.print(param.getType().getErasedType().getQualifiedSourceName());
            w.print(" ");
            w.print(param.getName());
        }
    }

    /** Write a list of parameters as if they are being passed into a method call. */
    protected void writeMethodArguments(SourceWriter w, JParameter[] params) {
        boolean needsComma = false;
        for (JParameter param : params) {
            if (needsComma) {
                w.print(", ");
            } else {
                needsComma = true;
            }

            w.print(param.getName());
        }
    }

    /** Write a list of parameters as if they are private variables in a class. */
    protected void writePrivateVariables(SourceWriter w, JParameter[] params) {
        for (JParameter param : params) {
            w.print("private ");
            w.print(param.getType().getErasedType().getQualifiedSourceName());
            w.print(" ");
            w.print(param.getName());
            w.println(";");
        }
    }

    /** Write a list of parameters as if they are variable assignments. */
    protected void writeVariableAssignments(SourceWriter w, JParameter[] params) {
        for (JParameter param : params) {
            w.print("this.");
            w.print(param.getName());
            w.print(" = ");
            w.print(param.getName());
            w.println(";");
        }
    }

}
