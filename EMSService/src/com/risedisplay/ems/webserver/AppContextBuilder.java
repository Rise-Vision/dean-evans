// Copyright © 2010 - June 2014 Rise Vision Incorporated.
// Use of this software is governed by the GPLv3 license
// (reproduced in the LICENSE file).

package com.risedisplay.ems.webserver;

import org.eclipse.jetty.webapp.WebAppContext;

public class AppContextBuilder {

	private WebAppContext webAppContext;

	public WebAppContext buildWebAppContext(){

		String executionPath = System.getProperty("user.dir");
	
		webAppContext = new WebAppContext();
		webAppContext.setDescriptor(executionPath + "\\web");
		webAppContext.setResourceBase(executionPath + "\\web");
		webAppContext.setContextPath("/");
		return webAppContext;
	}
}  
