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
import com.cmw.entity.sys.CountersignCfgEntity;
import com.cmw.entity.sys.NodeCfgEntity;
import com.cmw.service.inter.sys.CountersignCfgService;

/**
 * 会签配置缓存器缓存
 * @author chengmingwei
 *
 */
@SuppressWarnings("serial")
public class CountersignCfgCache implements Serializable {
	static Logger logger = Logger.getLogger(CountersignCfgCache.class);
	/**
	 * 将会签配置列表放入缓存中
	 * @param ids	要放置的多个流程节点的ID
	 * @throws ServiceException 
	 */
	public static void puts(String ids) throws ServiceException{
		SHashMap<String, Object> params = new SHashMap<String, Object>();
		params.put("isenabled", SysConstant.OPTION_ENABLED);
		CountersignCfgService service = (CountersignCfgService)SpringContextUtil.getBean("countersignCfgService");
		if(StringHandler.isValidStr(ids)){
			params.put("id", SqlUtil.LOGIC_IN + SqlUtil.LOGIC + ids);
		}
		List<CountersignCfgEntity> list = service.getEntityList(params);
		GlobalCache.addElements2Cache(CacheName.countersignCfgCache,list,new ElementCreator() {
			@Override
			public <T> Element create(T obj) {
				CountersignCfgEntity entity = (CountersignCfgEntity)obj;
				return new Element(entity.getId().toString(), entity);
			}
		});
	}
	
	/**
	 * 根据ID从缓存中获取会签配置对象
	 * @param id 会签配置ID
	 * @return	返回会签配置对象
	 */
	public static CountersignCfgEntity get(Long id){
		if(null == id) return null;
		String cacheName = CacheName.countersignCfgCache.name();
		Object obj = GlobalCache.get(cacheName, id.toString());
		return (CountersignCfgEntity)obj;
	}

	/**
	 * 根据比较器从缓存中获取流程节点配置对象
	 * @param compare 元素比较器对象 
	 * @return	返回流程节点配置对象
	 */
	public static CountersignCfgEntity get(ElementCompare compare){
		String cacheName = CacheName.countersignCfgCache.name();
		Object obj = GlobalCache.get(cacheName, compare);
		return (CountersignCfgEntity)obj;
	}

	/**
	 * 获取会签配置列表
	 * @param ids	会签配置ID
	 * @return
	 * @throws ServiceException
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static List<CountersignCfgEntity> getList(String ids) throws ServiceException{
		if(!StringHandler.isValidStr(ids)) return null;
		String cacheName = CacheName.countersignCfgCache.name();
		List dataList = GlobalCache.getCacheDatas(ids, cacheName,new DataNotify() {
			@Override
			public void load(String ids) throws ServiceException {
				puts(ids);
			}
		});
		List<CountersignCfgEntity> list = (List<CountersignCfgEntity>)dataList;
		return list;
	}
	
	/**
	 * 获取会签配置列表
	 * @param compare	元素比较器对象 
	 * @return 返回符合条件的会签配置列表
	 * @throws ServiceException
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static List<CountersignCfgEntity> getList(ElementCompare compare) throws ServiceException{
		String cacheName = CacheName.countersignCfgCache.name();
		List dataList = GlobalCache.getList(cacheName, compare);
		List<CountersignCfgEntity> list = (List<CountersignCfgEntity>)dataList;
		return list;
	}
	

	/**
	 * 添加会签配置
	 * @param obj
	 */
	public static void add(CountersignCfgEntity obj){
		String cacheName = CacheName.countersignCfgCache.name();
		String key = obj.getId().toString();
		GlobalCache.put(cacheName,key,obj);
	}
	
	/**
	 * 添加会签配置列表
	 * @param list 要添加会签配置列表
	 */
	public static void add(List<CountersignCfgEntity> list){
		if(null == list || list.size() == 0) return;
		String cacheName = CacheName.countersignCfgCache.name();
		for(CountersignCfgEntity entity : list){
			String key = entity.getId().toString();
			GlobalCache.put(cacheName,key,entity);
		}
	}
	/**
	 * 更新会签配置
	 * @param entity
	 */
	public static void update(CountersignCfgEntity entity){
		String cacheName = CacheName.countersignCfgCache.name();
		String id = entity.getId().toString();
		GlobalCache.remove(cacheName, id);
		add(entity);
	}
	
	/**
	 * 删除会签配置
	 * @param user
	 */
	public static void removes(String ids){
		if(!StringHandler.isValidStr(ids)) return;
		String cacheName = CacheName.countersignCfgCache.name();
		Ehcache cache = GlobalCache.getCache(cacheName);
		String[] idArr = ids.split(",");
		for(String id : idArr){
			cache.remove(id);
		}
	}

}
