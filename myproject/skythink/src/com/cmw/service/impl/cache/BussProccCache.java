package com.cmw.service.impl.cache;

import java.io.Serializable;
import java.util.List;

import net.sf.ehcache.Ehcache;
import net.sf.ehcache.Element;

import com.cmw.constant.SysConstant;
import com.cmw.core.base.cache.DataNotify;
import com.cmw.core.base.cache.ElementCompare;
import com.cmw.core.base.cache.ElementCreator;
import com.cmw.core.base.exception.ServiceException;
import com.cmw.core.util.SHashMap;
import com.cmw.core.util.SpringContextUtil;
import com.cmw.core.util.SqlUtil;
import com.cmw.core.util.StringHandler;
import com.cmw.entity.sys.BussProccEntity;
import com.cmw.entity.sys.TransCfgEntity;
import com.cmw.service.inter.sys.BussProccService;

/**
 * BussProcc (子业务流程缓存)
 * @author chengmingwei
 *
 */
@SuppressWarnings("serial")
public class BussProccCache implements Serializable {

	/**********====== BussProcc (子业务流程缓存) Cache Method Start CODE ========**********/
	/**
	 * 将子业务流程列表放入缓存中
	 * @param ids	要放置的多个角色的ID
	 * @throws ServiceException 
	 */
	public static void putBussProccs(String ids) throws ServiceException{
		SHashMap<String, Object> params = new SHashMap<String, Object>();
		params.put("isenabled", SysConstant.OPTION_ENABLED);
		BussProccService service = (BussProccService)SpringContextUtil.getBean("bussProccService");
		if(StringHandler.isValidStr(ids)){
			params.put("id", SqlUtil.LOGIC_IN + SqlUtil.LOGIC + ids);
		}
		List<BussProccEntity> list = service.getEntityList(params);
			GlobalCache.addElements2Cache(CacheName.bussProccCache,list,new ElementCreator() {
			@Override
			public <T> Element create(T obj) {
				BussProccEntity entity = (BussProccEntity)obj;
				return new Element(entity.getId().toString(), entity);
			}
		});
	}
	
	/**
	 * 根据ID从缓存中获取子业务流程对象
	 * @param id 子业务流程ID
	 * @return	返回子业务流程对象
	 */
	public static BussProccEntity getBussProcc(Long id){
		if(null == id) return null;
		String cacheName = CacheName.bussProccCache.name();
		Object obj = GlobalCache.get(cacheName, id.toString());
		return (BussProccEntity)obj;
	}

	/**
	 * 获取子业务流程列表
	 * @param ids	子业务流程ID
	 * @return
	 * @throws ServiceException
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static List<BussProccEntity> getBussProccs(String ids) throws ServiceException{
		if(!StringHandler.isValidStr(ids)) return null;
		String cacheName = CacheName.bussProccCache.name();
		List dataList = GlobalCache.getCacheDatas(ids, cacheName,new DataNotify() {
			@Override
			public void load(String ids) throws ServiceException {
				putBussProccs(ids);
			}
		});
		List<BussProccEntity> list = (List<BussProccEntity>)dataList;
		return list;
	}
	
	/**
	 * 获取子业务流程列表
	 * @param compare	元素比较器对象 
	 * @return 返回符合条件的子业务流程列表
	 * @throws ServiceException
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static List<BussProccEntity> getList(ElementCompare compare) throws ServiceException{
		String cacheName = CacheName.bussProccCache.name();
		List dataList = GlobalCache.getList(cacheName, compare);
		List<BussProccEntity> list = (List<BussProccEntity>)dataList;
		return list;
	}

	/**
	 * 添加子业务流程
	 * @param obj
	 */
	public static void addBussProcc(BussProccEntity obj){
		String cacheName = CacheName.bussProccCache.name();
		String key = obj.getId().toString();
		GlobalCache.put(cacheName,key,obj);
	}
	
	/**
	 * 更新子业务流程
	 * @param entity
	 */
	public static void updatBussProcc(BussProccEntity entity){
		String cacheName = CacheName.bussProccCache.name();
		String id = entity.getId().toString();
		GlobalCache.remove(cacheName, id);
		addBussProcc(entity);
	}
	
	/**
	 * 删除子业务流程
	 * @param user
	 */
	public static void removeBussProccs(String ids){
		if(!StringHandler.isValidStr(ids)) return;
		String cacheName = CacheName.bussProccCache.name();
		Ehcache cache = GlobalCache.getCache(cacheName);
		String[] roleIdArr = ids.split(",");
		for(String roleId : roleIdArr){
			cache.remove(roleId);
		}
	}/**********====== BussProcc(子业务流程缓存) Cache Method END CODE ========**********/
	
}
