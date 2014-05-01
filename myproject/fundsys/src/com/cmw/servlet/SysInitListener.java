package com.cmw.servlet;

import java.util.Date;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.log4j.Logger;

import com.cmw.service.impl.cache.GlobalCache;
import com.cmw.service.impl.job.BussJobManager;
/**
 * 系统初始化监听器
 * @author chengmingwei
 *	
 */
public class SysInitListener implements ServletContextListener {
	/** 
     * 日志 
     */  
    private static final Logger LOGGER = Logger.getLogger(SysInitListener.class);  
	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
		LOGGER.info("SysInitListener contextDestroyed start ... ");
		GlobalCache.shutdown();
		LOGGER.info("SysInitListener BussCache.shutdown ... ");
	}

	@Override
	public void contextInitialized(ServletContextEvent arg0) {
		LOGGER.info("SysInitListener init start ... ");
		LOGGER.info("SysInitListener BussCache  initCache start ... ");
		long startTime = new Date(System.currentTimeMillis()).getTime();
		GlobalCache.initCache();
		long endTime = new Date(System.currentTimeMillis()).getTime();
		long seconds = (endTime-startTime);///1000;
		LOGGER.info("SysInitListener BussCache  initCache end (加载缓存毫秒数 : "+seconds+")... ");
		
		LOGGER.info("SysInitListener Job start ... ");
		BussJobManager.startJobs();
		LOGGER.info("SysInitListener Job start finish ... ");
		
	}

}
