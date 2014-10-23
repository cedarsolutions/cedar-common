/* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 *
 *              C E D A R
 *          S O L U T I O N S       "Software done right."
 *           S O F T W A R E
 *
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 *
 * Copyright (c) 2014 Kenneth J. Pronovici.
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
package com.cedarsolutions.wiring.gwt.filter;

import java.io.IOException;
import java.util.Date;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.cedarsolutions.util.DateUtils;

/**
 * Servlet filter to manage cache control headers for GWT files.
 * @see <a href="http://stackoverflow.com/questions/3407649">Stack Overflow</a>
 * @see <a href="http://seewah.blogspot.com/2009/02/gwt-tips-2-nocachejs-getting-cached-in.html">Seewah</a>
 * @see <a href="http://www.gwtproject.org/doc/latest/DevGuideCompilingAndDebugging.html#perfect_caching">Perfect Caching</a>
 * @author Kenneth J. Pronovici <pronovic@ieee.org>
 */
public class GwtCacheControlFilter implements Filter {

    private static final long ONE_YEAR = DateUtils.MILLISECONDS_PER_DAY * 365;

    @Override
    public void destroy() {
    }

    @Override
    public void init(FilterConfig config) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        // These values are taken directly from GWT's "perfect caching" instructions (see URL above)
        String requestUri = httpRequest.getRequestURI();
        if (requestUri.contains(".nocache.")) {
            Date now = DateUtils.getCurrentDate();
            httpResponse.setDateHeader("Expires", now.getTime());
            httpResponse.setHeader("Cache-control", "public, max-age=0, must-revalidate");
        } else if (requestUri.contains(".cache.")) {
            Date now = DateUtils.getCurrentDate();
            httpResponse.setDateHeader("Expires", now.getTime() + ONE_YEAR);
        }

        filterChain.doFilter(request, response);
    }

}
