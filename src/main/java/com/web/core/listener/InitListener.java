package com.web.core.listener;

import javax.servlet.ServletContextEvent;

import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

/**
 * 系统初始化监听器,在系统启动时运行,进行一些初始化工作
 * 
 * @author qgl
 * 
 */
public class InitListener implements javax.servlet.ServletContextListener {

	public void contextDestroyed(ServletContextEvent arg0) {

	}

	@SuppressWarnings("unused")
	public void contextInitialized(ServletContextEvent event) {
		WebApplicationContext webApplicationContext = WebApplicationContextUtils.getWebApplicationContext(event.getServletContext());
	}

}
