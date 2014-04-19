package com.cmw.service.impl.cache;

import java.io.Serializable;
import java.util.List;

import net.sf.ehcache.Ehcache;
import net.sf.ehcache.Element;

import com.cmw.constant.SysConstant;
import com.cmw.core.base.cache.DataNotify;
import com.cmw.core.base.cache.ElementCreator;
import com.cmw.core.base.exception.ServiceException;
import com.cmw.core.util.SHashMap;
import com.cmw.core.util.SpringContextUtil;
import com.cmw.core.util.SqlUtil;
import com.cmw.core.util.StringHandler;
import com.cmw.entity.sys.VarietyEntity;
import com.cmw.service.inter.sys.VarietyService;

/**
 * Variety (业务品种缓存)
 * @author chengmingwei
 *
 */
@SuppressWarnings("serial")
public class VarietyCache implements Serializable {
	/**********====== Variety (业务品种缓存) Cache Method Start CODE ========**********/
	/**
	 * 将业务品种列表放入缓存中
	 * @param ids	要放置的多个角色的ID
	 * @throws ServiceException 
	 */
	public static void putVarietys(String ids) throws ServiceException{
		SHashMap<String, Object> params = new SHashMap<String, Object>();
		params.put("isenabled", SysConstant.OPTION_ENABLED);
		VarietyService varietyService = (VarietyService)SpringContextUtil.getBean("varietyService");
		if(StringHandler.isValidStr(ids)){
			params.put("id", SqlUtil.LOGIC_IN + SqlUtil.LOGIC + ids);
		}
		List<VarietyEntity> list = varietyService.getEntityList(params);
			GlobalCache.addElements2Cache(CacheName.varietyCache,list,new ElementCreator() {
			@Override
			public <T> Element create(T obj) {
				VarietyEntity entity = (VarietyEntity)obj;
				return new Element(entity.getId().toString(), entity);
			}
		});
	}
	
	/**
	 * 根据ID从缓存中获取业务品种对象
	 * @param id 业务品种ID
	 * @return	返回业务品种对象
	 */
	public static VarietyEntity getVariety(Long id){
		if(null == id) return null;
		String cacheName = CacheName.varietyCache.name();
		Object obj = GlobalCache.get(cacheName, id.toString());
		return (VarietyEntity)obj;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static List<VarietyEntity> getVarietys(String ids) throws ServiceException{
		if(!StringHandler.isValidStr(ids)) return null;
		String cacheName = CacheName.varietyCache.name();
		List dataList = GlobalCache.getCacheDatas(ids, cacheName,new DataNotify() {
			@Override
			public void load(String ids) throws ServiceException {
				putVarietys(ids);
			}
		});
		List<VarietyEntity> list = (List<VarietyEntity>)dataList;
		return list;
	}

	/**
	 * 添加业务品种
	 * @param obj
	 */
	public static void addVariety(VarietyEntity obj){
		String cacheName = CacheName.varietyCache.name();
		String key = obj.getId().toString();
		GlobalCache.put(cacheName,key,obj);
	}
	
	/**
	 * 更新业务品种
	 * @param entity
	 */
	public static void updateVariety(VarietyEntity entity){
		String cacheName = CacheName.varietyCache.name();
		String id = entity.getId().toString();
		GlobalCache.remove(cacheName, id);
		addVariety(entity);
	}
	
	/**
	 * 删除业务品种
	 * @param user
	 */
	public static void removeVarietys(String ids){
		if(!StringHandler.isValidStr(ids)) return;
		String cacheName = CacheName.varietyCache.name();
		Ehcache cache = GlobalCache.getCache(cacheName);
		String[] idArr = ids.split(",");
		for(String id : idArr){
			cache.remove(id);
		}
	}/**********====== Variety(业务品种缓存) Cache Method END CODE ========**********/
}
