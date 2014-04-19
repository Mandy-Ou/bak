package com.cmw.service.impl.cache;

import com.cmw.core.base.cache.FlatCache;
import com.cmw.core.base.exception.ServiceException;
import com.cmw.core.util.SpringContextUtil;
import com.cmw.service.impl.finance.FcFlowService;
import com.cmw.service.impl.workflow.BussProccFlowService;
/**
 * 全局业务缓存
 * @author chengmingwei
 *
 */
public class GlobalCache extends FlatCache {
	/**
	 * 设置缓存文件
	 */
	static{
		setCacheConfigFile("ehcache.xml");
	}
	
	/**
	 * 缓存初始化
	 */
	public static void initCache(){
		try {
			UserCache.putUsers(null);
			RoleCache.putRoles(null);
			VarietyCache.putVarietys(null);
			BussProccCache.putBussProccs(null);
			BussNodeCache.putBussNodes(null);
			BussTransCache.puts(null);
			NodeCfgCache.puts(null);
			CountersignCfgCache.puts(null);
			TransCfgCache.puts(null);
			FormCfgCache.puts(null);
			initWorkFlowCache();
			
		} catch (ServiceException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 初始化与工作流相关的缓存
	 * @throws ServiceException
	 */
	private static void initWorkFlowCache() throws ServiceException{
		FcFlowService service = (FcFlowService)SpringContextUtil.getBean("fcFlowService");
		service.buildCache();
		
		BussProccFlowService bussProccFlowService = (BussProccFlowService)SpringContextUtil.getBean("bussProccFlowService");
		bussProccFlowService.buildCache();
	}
}
