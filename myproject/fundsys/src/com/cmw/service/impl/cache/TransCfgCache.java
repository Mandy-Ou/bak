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
import com.cmw.entity.sys.NodeCfgEntity;
import com.cmw.entity.sys.TransCfgEntity;
import com.cmw.service.inter.sys.TransCfgService;

/**
 * 流转路径配置缓存器缓存
 * @author chengmingwei
 *
 */
@SuppressWarnings("serial")
public class TransCfgCache implements Serializable {
	static Logger logger = Logger.getLogger(TransCfgCache.class);
	/**
	 * 将流转路径配置列表放入缓存中
	 * @param ids	要放置的多个流程节点的ID
	 * @throws ServiceException 
	 */
	public static void puts(String ids) throws ServiceException{
		SHashMap<String, Object> params = new SHashMap<String, Object>();
		params.put("isenabled", SysConstant.OPTION_ENABLED);
		params.put("actionType", SysConstant.ACTION_TYPE_TRANSCFG_1);
		TransCfgService service = (TransCfgService)SpringContextUtil.getBean("transCfgService");
		if(StringHandler.isValidStr(ids)){
			params.put("id", SqlUtil.LOGIC_IN + SqlUtil.LOGIC + ids);
		}
		List<TransCfgEntity> list = service.getEntityList(params);
		GlobalCache.addElements2Cache(CacheName.transCfgCache,list,new ElementCreator() {
			@Override
			public <T> Element create(T obj) {
				TransCfgEntity entity = (TransCfgEntity)obj;
				return new Element(entity.getId().toString(), entity);
			}
		});
	}
	
	/**
	 * 根据ID从缓存中获取流转路径配置对象
	 * @param id 流转路径配置ID
	 * @return	返回流转路径配置对象
	 */
	public static TransCfgEntity get(Long id){
		if(null == id) return null;
		String cacheName = CacheName.transCfgCache.name();
		Object obj = GlobalCache.get(cacheName, id.toString());
		return (TransCfgEntity)obj;
	}

	/**
	 * 根据ID从缓存中获取流转路径配置对象
	 * @param id 元素比较器对象 
	 * @return	返回流程节点配置对象
	 */
	public static TransCfgEntity get(ElementCompare compare){
		String cacheName = CacheName.transCfgCache.name();
		Object obj = GlobalCache.get(cacheName, compare);
		return (TransCfgEntity)obj;
	}
	
	/**
	 * 获取流转路径配置列表
	 * @param ids	流转路径配置ID
	 * @return
	 * @throws ServiceException
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static List<TransCfgEntity> getList(String ids) throws ServiceException{
		if(!StringHandler.isValidStr(ids)) return null;
		String cacheName = CacheName.transCfgCache.name();
		List dataList = GlobalCache.getCacheDatas(ids, cacheName,new DataNotify() {
			@Override
			public void load(String ids) throws ServiceException {
				puts(ids);
			}
		});
		List<TransCfgEntity> list = (List<TransCfgEntity>)dataList;
		return list;
	}
	
	/**
	 * 获取流转路径配置列表
	 * @param compare	元素比较器对象 
	 * @return 返回符合条件的流转路径配置列表
	 * @throws ServiceException
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static List<TransCfgEntity> getList(ElementCompare compare) throws ServiceException{
		String cacheName = CacheName.transCfgCache.name();
		List dataList = GlobalCache.getList(cacheName, compare);
		List<TransCfgEntity> list = (List<TransCfgEntity>)dataList;
		return list;
	}
	

	/**
	 * 添加流转路径配置
	 * @param obj
	 */
	public static void add(TransCfgEntity obj){
		String cacheName = CacheName.transCfgCache.name();
		String key = obj.getId().toString();
		GlobalCache.put(cacheName,key,obj);
	}
	
	/**
	 * 添加流转路径配置列表
	 * @param list 要添加流转路径配置列表
	 */
	public static void add(List<TransCfgEntity> list){
		if(null == list || list.size() == 0) return;
		String cacheName = CacheName.transCfgCache.name();
		for(TransCfgEntity entity : list){
			String key = entity.getId().toString();
			GlobalCache.put(cacheName,key,entity);
		}
	}
	/**
	 * 更新流转路径配置
	 * @param entity
	 */
	public static void update(TransCfgEntity entity){
		String cacheName = CacheName.transCfgCache.name();
		String id = entity.getId().toString();
		GlobalCache.remove(cacheName, id);
		add(entity);
	}
	
	/**
	 * 删除流转路径配置
	 * @param user
	 */
	public static void removes(String ids){
		if(!StringHandler.isValidStr(ids)) return;
		String cacheName = CacheName.transCfgCache.name();
		Ehcache cache = GlobalCache.getCache(cacheName);
		String[] idArr = ids.split(",");
		for(String id : idArr){
			cache.remove(id);
		}
	}
	
	/**
	 * 删除流转路径配置
	 * @param user
	 */
	public static void removes(List<TransCfgEntity> list){
		String cacheName = CacheName.transCfgCache.name();
		Ehcache cache = GlobalCache.getCache(cacheName);
		@SuppressWarnings("unchecked")
		List<String> keys = (List<String>)cache.getKeys();
		for(TransCfgEntity entity : list){
			Long transId = entity.getTransId();
			  for(String key : keys){
					Element element = cache.get(key);
					Object value = element.getObjectValue();
					if(null == value) continue;
					TransCfgEntity cacheEntity = (TransCfgEntity)value;
					Long cacheTransId = cacheEntity.getTransId();
					if(cacheTransId.equals(transId)){
						String id = cacheEntity.getId().toString();
						cache.remove(id);
						logger.info("remove [transId:"+transId+", formCfgId="+id+"]");
						break;
					}
				}
		}
	}
}
