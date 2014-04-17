package com.cmw.core.security.listener;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

@SuppressWarnings("serial")
public class AuthenticateListener extends javax.servlet.http.HttpServlet implements ServletContextListener,HttpSessionListener {
	private static int scount;
	@Override
	public void contextDestroyed(ServletContextEvent evt) {
		System.out.println("contextDestroyed start......");	
	}

	@Override
	public void contextInitialized(ServletContextEvent evt) {
	}

	@Override
	public void sessionCreated(HttpSessionEvent he) {
		System.out.println("session 创建了【"+scount+"】次!");
	}

	@Override
	public void sessionDestroyed(HttpSessionEvent arg0) {
		
	}

}
