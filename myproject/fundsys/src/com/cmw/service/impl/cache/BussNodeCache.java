package com.cmw.service.impl.cache;

import java.io.Serializable;
import java.util.List;

import net.sf.ehcache.Ehcache;
import net.sf.ehcache.Element;

import org.apache.log4j.Logger;

import com.cmw.constant.SysConstant;
import com.cmw.core.base.cache.DataNotify;
import com.cmw.core.base.cache.ElementCompare;
import com.cmw.core.base.cache.ElementCreator;
import com.cmw.core.base.exception.ServiceException;
import com.cmw.core.util.SHashMap;
import com.cmw.core.util.SpringContextUtil;
import com.cmw.core.util.SqlUtil;
import com.cmw.core.util.StringHandler;
import com.cmw.entity.sys.BussNodeEntity;
import com.cmw.service.inter.sys.BussNodeService;

/**
 * 业务流程节点缓存
 * @author chengmingwei
 *
 */
@SuppressWarnings("serial")
public class BussNodeCache implements Serializable {
	static Logger logger = Logger.getLogger(BussNodeCache.class);
	/**********====== BussNode (业务流程节点缓存) Cache Method Start CODE ========**********/
	/**
	 * 将业务流程节点列表放入缓存中
	 * @param ids	要放置的多个流程节点的ID
	 * @throws ServiceException 
	 */
	public static void putBussNodes(String ids) throws ServiceException{
		SHashMap<String, Object> params = new SHashMap<String, Object>();
		params.put("isenabled", SysConstant.OPTION_ENABLED);
		BussNodeService service = (BussNodeService)SpringContextUtil.getBean("bussNodeService");
		if(StringHandler.isValidStr(ids)){
			params.put("id", SqlUtil.LOGIC_IN + SqlUtil.LOGIC + ids);
		}else{
			List<String> varietyIdList = GlobalCache.getKeys(CacheName.varietyCache.name());
			List<String> bussProccIdList = GlobalCache.getKeys(CacheName.bussProccCache.name());
			List<String> formIdList = null;
			if(null != varietyIdList && varietyIdList.size() > 0) formIdList = varietyIdList;
			if(null == formIdList || formIdList.size() == 0){
				formIdList = bussProccIdList;
			}else{
				formIdList.addAll(bussProccIdList);
			}
			String formIds = "-1";
			if(null != formIdList && formIdList.size() > 0){
				formIds = StringHandler.join(formIdList.toArray());
			}
			params.put("formId", SqlUtil.LOGIC_IN + SqlUtil.LOGIC + formIds);
		}
		List<BussNodeEntity> list = service.getEntityList(params);
		GlobalCache.addElements2Cache(CacheName.bussNodeCache,list,new ElementCreator() {
			@Override
			public <T> Element create(T obj) {
				BussNodeEntity entity = (BussNodeEntity)obj;
				return new Element(entity.getId().toString(), entity);
			}
		});
	}
	
	/**
	 * 根据ID从缓存中获取业务流程节点对象
	 * @param id 业务流程节点ID
	 * @return	返回业务流程节点对象
	 */
	public static BussNodeEntity getBussNode(Long id){
		if(null == id) return null;
		String cacheName = CacheName.bussNodeCache.name();
		Object obj = GlobalCache.get(cacheName, id.toString());
		return (BussNodeEntity)obj;
	}

	/**
	 * 获取业务流程节点列表
	 * @param ids	业务流程节点ID
	 * @return
	 * @throws ServiceException
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static List<BussNodeEntity> getBussNodes(String ids) throws ServiceException{
		if(!StringHandler.isValidStr(ids)) return null;
		String cacheName = CacheName.bussNodeCache.name();
		List dataList = GlobalCache.getCacheDatas(ids, cacheName,new DataNotify() {
			@Override
			public void load(String ids) throws ServiceException {
				putBussNodes(ids);
			}
		});
		List<BussNodeEntity> list = (List<BussNodeEntity>)dataList;
		return list;
	}


	/**
	 * 根据ID从缓存中获取业务流程节点对象
	 * @param id 元素比较器对象 
	 * @return	返回业务流程节点对象
	 */
	public static BussNodeEntity get(ElementCompare compare){
		String cacheName = CacheName.bussNodeCache.name();
		Object obj = GlobalCache.get(cacheName, compare);
		return (BussNodeEntity)obj;
	}

	/**
	 * 获取业务流程节点列表
	 * @param compare	元素比较器对象 
	 * @return 返回符合条件的业务流程节点列表
	 * @throws ServiceException
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static List<BussNodeEntity> getList(ElementCompare compare) throws ServiceException{
		String cacheName = CacheName.bussNodeCache.name();
		List dataList = GlobalCache.getList(cacheName, compare);
		List<BussNodeEntity> list = (List<BussNodeEntity>)dataList;
		return list;
	}
	
	/**
	 * 添加业务流程节点
	 * @param obj
	 */
	public static void addBussNode(BussNodeEntity obj){
		String cacheName = CacheName.bussNodeCache.name();
		String key = obj.getId().toString();
		GlobalCache.put(cacheName,key,obj);
	}
	
	/**
	 * 添加业务流程节点列表
	 * @param list 要添加业务流程节点列表
	 */
	public static void add(List<BussNodeEntity> list){
		if(null == list || list.size() == 0) return;
		String cacheName = CacheName.bussNodeCache.name();
		int size = GlobalCache.getCache(cacheName).getSize();
		
		for(BussNodeEntity entity : list){
			String key = entity.getId().toString();
			GlobalCache.put(cacheName,key,entity);
		}
		
		int newSize = GlobalCache.getCache(cacheName).getSize();
		logger.info("---------- Invoke BussNodeCache.add Method Info ----------\n");
		logger.info("cacheName:[添加元素之前大小："+size+"，本次添加元素数量："+list.size()+"，添加元素之后大小："+newSize+"]");
	}
	
	/**
	 * 更新业务流程节点
	 * @param entity
	 */
	public static void updateBussNode(BussNodeEntity entity){
		String cacheName = CacheName.bussNodeCache.name();
		String id = entity.getId().toString();
		GlobalCache.remove(cacheName, id);
		addBussNode(entity);
	}
	
	/**
	 * 删除业务流程节点
	 * @param user
	 */
	public static void removeBussNodes(String ids){
		if(!StringHandler.isValidStr(ids)) return;
		String cacheName = CacheName.bussNodeCache.name();
		Ehcache cache = GlobalCache.getCache(cacheName);
		String[] idArr = ids.split(",");
		for(String id : idArr){
			cache.remove(id);
		}
	}/**********====== BussNode(业务流程节点缓存) Cache Method END CODE ========**********/
}
