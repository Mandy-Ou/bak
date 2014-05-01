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
import com.cmw.entity.sys.BussTransEntity;
import com.cmw.service.inter.sys.BussTransService;

/**
 * 流转路径缓存
 * @author chengmingwei
 *
 */
@SuppressWarnings("serial")
public class BussTransCache implements Serializable {
	static Logger logger = Logger.getLogger(BussNodeCache.class);
	/**
	 * 将流转路径列表放入缓存中
	 * @param ids	要放置的多个流程节点的ID
	 * @throws ServiceException 
	 */
	public static void puts(String ids) throws ServiceException{
		SHashMap<String, Object> params = new SHashMap<String, Object>();
		params.put("isenabled", SysConstant.OPTION_ENABLED);
		params.put("actionType", SysConstant.ACTION_TYPE_TRANSCFG_1);
		BussTransService service = (BussTransService)SpringContextUtil.getBean("bussTransService");
		if(StringHandler.isValidStr(ids)){
			params.put("id", SqlUtil.LOGIC_IN + SqlUtil.LOGIC + ids);
		}
		List<BussTransEntity> list = service.getEntityList(params);
		GlobalCache.addElements2Cache(CacheName.bussTransCache,list,new ElementCreator() {
			@Override
			public <T> Element create(T obj) {
				BussTransEntity entity = (BussTransEntity)obj;
				return new Element(entity.getId().toString(), entity);
			}
		});
	}
	
	/**
	 * 根据ID从缓存中获取流转路径对象
	 * @param id 流转路径ID
	 * @return	返回流转路径对象
	 */
	public static BussTransEntity get(Long id){
		if(null == id) return null;
		String cacheName = CacheName.bussTransCache.name();
		Object obj = GlobalCache.get(cacheName, id.toString());
		return (BussTransEntity)obj;
	}

	/**
	 * 获取流转路径列表
	 * @param ids	流转路径ID
	 * @return
	 * @throws ServiceException
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static List<BussTransEntity> getList(String ids) throws ServiceException{
		if(!StringHandler.isValidStr(ids)) return null;
		String cacheName = CacheName.bussTransCache.name();
		List dataList = GlobalCache.getCacheDatas(ids, cacheName,new DataNotify() {
			@Override
			public void load(String ids) throws ServiceException {
				puts(ids);
			}
		});
		List<BussTransEntity> list = (List<BussTransEntity>)dataList;
		return list;
	}
	
	/**
	 * 获取流程节点配置列表
	 * @param compare	元素比较器对象 
	 * @return 返回符答条件的流程节点配置列表
	 * @throws ServiceException
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static List<BussTransEntity> getList(ElementCompare compare) throws ServiceException{
		String cacheName = CacheName.bussTransCache.name();
		List dataList = GlobalCache.getList(cacheName, compare);
		List<BussTransEntity> list = (List<BussTransEntity>)dataList;
		return list;
	}
	
	

	/**
	 * 添加流转路径
	 * @param obj
	 */
	public static void add(BussTransEntity obj){
		String cacheName = CacheName.bussTransCache.name();
		String key = obj.getId().toString();
		GlobalCache.put(cacheName,key,obj);
	}
	

	/**
	 * 添加流转路径列表
	 * @param list 要添加流转路径列表
	 */
	public static void add(List<BussTransEntity> list){
		if(null == list || list.size() == 0) return;
		String cacheName = CacheName.bussTransCache.name();
		int size = GlobalCache.getCache(cacheName).getSize();
		for(BussTransEntity entity : list){
			String key = entity.getId().toString();
			GlobalCache.put(cacheName,key,entity);
		}
		
		int newSize = GlobalCache.getCache(cacheName).getSize();
		logger.info("---------- Invoke BussNodeCache.add Method Info ----------\n");
		logger.info("cacheName:[添加元素之前大小："+size+"，本次添加元素数量："+list.size()+"，添加元素之后大小："+newSize+"]");
	}
	
	/**
	 * 更新流转路径
	 * @param entity
	 */
	public static void update(BussTransEntity entity){
		String cacheName = CacheName.bussTransCache.name();
		String id = entity.getId().toString();
		GlobalCache.remove(cacheName, id);
		add(entity);
	}
	
	/**
	 * 删除流转路径
	 * @param user
	 */
	public static void removes(String ids){
		if(!StringHandler.isValidStr(ids)) return;
		String cacheName = CacheName.bussTransCache.name();
		Ehcache cache = GlobalCache.getCache(cacheName);
		String[] idArr = ids.split(",");
		for(String id : idArr){
			cache.remove(id);
		}
	}
}
