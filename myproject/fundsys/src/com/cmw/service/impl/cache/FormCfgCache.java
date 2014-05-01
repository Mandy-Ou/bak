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
import com.cmw.entity.sys.FormCfgEntity;
import com.cmw.service.inter.sys.FormCfgService;

/**
 * 节点表单配置缓存器缓存
 * @author chengmingwei
 *
 */
@SuppressWarnings("serial")
public class FormCfgCache implements Serializable {
	static Logger logger = Logger.getLogger(FormCfgCache.class);
	/**
	 * 将节点表单配置列表放入缓存中
	 * @param ids	要放置的多个节点表单配置的ID
	 * @throws ServiceException 
	 */
	public static void puts(String ids) throws ServiceException{
		SHashMap<String, Object> params = new SHashMap<String, Object>();
		params.put("isenabled", SysConstant.OPTION_ENABLED);
		FormCfgService service = (FormCfgService)SpringContextUtil.getBean("formCfgService");
		if(StringHandler.isValidStr(ids)){
			params.put("id", SqlUtil.LOGIC_IN + SqlUtil.LOGIC + ids);
		}else{
			List<String> nodeCfgIdList = GlobalCache.getKeys(CacheName.nodeCfgCache.name());
			String nodeCfgIds = "-1";
			if(null != nodeCfgIdList && nodeCfgIdList.size() > 0){
				nodeCfgIds = StringHandler.join(nodeCfgIdList.toArray());
				params.put("nodeIds", nodeCfgIds);
			}
		}
		List<FormCfgEntity> list = service.getEntityList(params);
		GlobalCache.addElements2Cache(CacheName.formCfgCache,list,new ElementCreator() {
			@Override
			public <T> Element create(T obj) {
				FormCfgEntity entity = (FormCfgEntity)obj;
				return new Element(entity.getId().toString(), entity);
			}
		});
	}
	
	/**
	 * 根据ID从缓存中获取节点表单配置对象
	 * @param id 节点表单配置ID
	 * @return	返回节点表单配置对象
	 */
	public static FormCfgEntity get(Long id){
		if(null == id) return null;
		String cacheName = CacheName.formCfgCache.name();
		Object obj = GlobalCache.get(cacheName, id.toString());
		return (FormCfgEntity)obj;
	}

	/**
	 * 获取节点表单配置列表
	 * @param ids	节点表单配置ID
	 * @return
	 * @throws ServiceException
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static List<FormCfgEntity> getList(String ids) throws ServiceException{
		if(!StringHandler.isValidStr(ids)) return null;
		String cacheName = CacheName.formCfgCache.name();
		List dataList = GlobalCache.getCacheDatas(ids, cacheName,new DataNotify() {
			@Override
			public void load(String ids) throws ServiceException {
				puts(ids);
			}
		});
		List<FormCfgEntity> list = (List<FormCfgEntity>)dataList;
		return list;
	}

	/**
	 * 根据比较器对象，返回符合条件的表单配置
	 * @param compare 元素比较器对象 
	 * @return	返回流程节点配置对象
	 */
	public static FormCfgEntity get(ElementCompare compare){
		String cacheName = CacheName.formCfgCache.name();
		Object obj = GlobalCache.get(cacheName, compare);
		return (FormCfgEntity)obj;
	}

	/**
	 * 根据比较器对象，返回符合条件的表单配置列表
	 * @param compare	元素比较器对象 
	 * @return 返回表单配置列表
	 * @throws ServiceException
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static List<FormCfgEntity> getList(ElementCompare compare) throws ServiceException{
		String cacheName = CacheName.formCfgCache.name();
		List dataList = GlobalCache.getList(cacheName, compare);
		List<FormCfgEntity> list = (List<FormCfgEntity>)dataList;
		return list;
	}
	
	/**
	 * 添加节点表单配置
	 * @param obj
	 */
	public static void add(FormCfgEntity obj){
		String cacheName = CacheName.formCfgCache.name();
		String key = obj.getId().toString();
		GlobalCache.put(cacheName,key,obj);
	}
	
	/**
	 * 添加节点表单配置
	 * @param list 节点表单配置列表
	 */
	public static void add(List<FormCfgEntity> list){
		if(null == list || list.size() == 0) return;
		String cacheName = CacheName.formCfgCache.name();
		for(FormCfgEntity entity : list){
			String key = entity.getId().toString();
			GlobalCache.put(cacheName,key,entity);
		}
	}
	
	
	/**
	 * 更新节点表单配置
	 * @param entity
	 */
	public static void update(FormCfgEntity entity){
		String cacheName = CacheName.formCfgCache.name();
		String id = entity.getId().toString();
		GlobalCache.remove(cacheName, id);
		add(entity);
	}
	
	/**
	 * 删除节点表单配置
	 * @param user
	 */
	public static void removes(String ids){
		if(!StringHandler.isValidStr(ids)) return;
		String cacheName = CacheName.formCfgCache.name();
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
	public static void removes(List<FormCfgEntity> list){
		String cacheName = CacheName.formCfgCache.name();
		Ehcache cache = GlobalCache.getCache(cacheName);
		@SuppressWarnings("unchecked")
		List<String> keys = (List<String>)cache.getKeys();
		for(FormCfgEntity entity : list){
			Long nodeId = entity.getNodeId();
			  for(String key : keys){
					Element element = cache.get(key);
					Object value = element.getObjectValue();
					if(null == value) continue;
					FormCfgEntity cacheEntity = (FormCfgEntity)value;
					if(cacheEntity.getNodeId().equals(nodeId)){
						String id = cacheEntity.getId().toString();
						cache.remove(id);
						logger.info("remove [nodeId:"+nodeId+", formCfgId="+id+"]");
						break;
					}
				}
		}
	}
}
