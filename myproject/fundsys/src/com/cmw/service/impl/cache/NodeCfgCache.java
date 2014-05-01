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
import com.cmw.entity.sys.FormCfgEntity;
import com.cmw.entity.sys.NodeCfgEntity;
import com.cmw.service.inter.sys.NodeCfgService;

/**
 * 流程节点配置缓存器缓存
 * @author chengmingwei
 *
 */
@SuppressWarnings("serial")
public class NodeCfgCache implements Serializable {
	
	/**
	 * 将流程节点配置列表放入缓存中
	 * @param ids	要放置的多个流程节点的ID
	 * @throws ServiceException 
	 */
	public static void puts(String ids) throws ServiceException{
		SHashMap<String, Object> params = new SHashMap<String, Object>();
		params.put("isenabled", SysConstant.OPTION_ENABLED);
		params.put("actionType", SysConstant.ACTION_TYPE_TRANSCFG_1);
		NodeCfgService service = (NodeCfgService)SpringContextUtil.getBean("nodeCfgService");
		if(StringHandler.isValidStr(ids)){
			params.put("id", SqlUtil.LOGIC_IN + SqlUtil.LOGIC + ids);
		}
		List<NodeCfgEntity> list = service.getEntityList(params);
		GlobalCache.addElements2Cache(CacheName.nodeCfgCache,list,new ElementCreator() {
			@Override
			public <T> Element create(T obj) {
				NodeCfgEntity entity = (NodeCfgEntity)obj;
				return new Element(entity.getId().toString(), entity);
			}
		});
	}
	
	/**
	 * 根据ID从缓存中获取流程节点配置对象
	 * @param id 流程节点配置ID
	 * @return	返回流程节点配置对象
	 */
	public static NodeCfgEntity get(Long id){
		if(null == id) return null;
		String cacheName = CacheName.nodeCfgCache.name();
		Object obj = GlobalCache.get(cacheName, id.toString());
		return (NodeCfgEntity)obj;
	}

	/**
	 * 获取流程节点配置列表
	 * @param ids	流程节点配置ID
	 * @return
	 * @throws ServiceException
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static List<NodeCfgEntity> getList(String ids) throws ServiceException{
		if(!StringHandler.isValidStr(ids)) return null;
		String cacheName = CacheName.nodeCfgCache.name();
		List dataList = GlobalCache.getCacheDatas(ids, cacheName,new DataNotify() {
			@Override
			public void load(String ids) throws ServiceException {
				puts(ids);
			}
		});
		List<NodeCfgEntity> list = (List<NodeCfgEntity>)dataList;
		return list;
	}

	
	/**
	 * 根据ID从缓存中获取流程节点配置对象
	 * @param id 元素比较器对象 
	 * @return	返回流程节点配置对象
	 */
	public static NodeCfgEntity get(ElementCompare compare){
		String cacheName = CacheName.nodeCfgCache.name();
		Object obj = GlobalCache.get(cacheName, compare);
		return (NodeCfgEntity)obj;
	}

	/**
	 * 获取流程节点配置列表
	 * @param compare	元素比较器对象 
	 * @return 返回符答条件的流程节点配置列表
	 * @throws ServiceException
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static List<NodeCfgEntity> getList(ElementCompare compare) throws ServiceException{
		String cacheName = CacheName.nodeCfgCache.name();
		List dataList = GlobalCache.getList(cacheName, compare);
		List<NodeCfgEntity> list = (List<NodeCfgEntity>)dataList;
		return list;
	}
	
	/**
	 * 添加流程节点配置
	 * @param obj
	 */
	public static void add(NodeCfgEntity obj){
		String cacheName = CacheName.nodeCfgCache.name();
		String key = obj.getId().toString();
		GlobalCache.put(cacheName,key,obj);
	}
	
	
	/**
	 * 添加流程节点配置列表
	 * @param list 要添加流程节点配置列表
	 */
	public static void add(List<NodeCfgEntity> list){
		if(null == list || list.size() == 0) return;
		String cacheName = CacheName.nodeCfgCache.name();
		for(NodeCfgEntity entity : list){
			String key = entity.getId().toString();
			GlobalCache.put(cacheName,key,entity);
		}
	}
	
	/**
	 * 更新流程节点配置
	 * @param entity
	 */
	public static void update(NodeCfgEntity entity){
		String cacheName = CacheName.nodeCfgCache.name();
		String id = entity.getId().toString();
		GlobalCache.remove(cacheName, id);
		add(entity);
	}
	
	/**
	 * 删除流程节点配置
	 * @param user
	 */
	public static void removes(String ids){
		if(!StringHandler.isValidStr(ids)) return;
		String cacheName = CacheName.nodeCfgCache.name();
		Ehcache cache = GlobalCache.getCache(cacheName);
		String[] idArr = ids.split(",");
		for(String id : idArr){
			cache.remove(id);
		}
	}
}
