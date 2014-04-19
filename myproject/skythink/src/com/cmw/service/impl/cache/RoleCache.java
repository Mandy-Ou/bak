package com.cmw.service.impl.cache;

import java.io.Serializable;
import java.util.ArrayList;
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
import com.cmw.entity.sys.RoleEntity;
import com.cmw.service.inter.sys.RoleService;

/**
 *  Role(角色缓存)
 * @author chengmingwei
 *
 */
@SuppressWarnings("serial")
public class RoleCache implements Serializable {
	/**********====== Role(角色缓存) Cache Method Start CODE ========**********/
	/**
	 * 将角色列表放入缓存中
	 * @param ids	要放置的多个角色的ID
	 * @throws ServiceException 
	 */
	public static void putRoles(String ids) throws ServiceException{
		SHashMap<String, Object> params = new SHashMap<String, Object>();
		params.put("isenabled", SysConstant.OPTION_ENABLED);
		RoleService roleService = (RoleService)SpringContextUtil.getBean("roleService");
		if(StringHandler.isValidStr(ids)){
			params.put("id", SqlUtil.LOGIC_IN + SqlUtil.LOGIC + ids);
		}
		List<RoleEntity> roles = roleService.getEntityList(params);
			GlobalCache.addElements2Cache(CacheName.roleCache,roles,new ElementCreator() {
			@Override
			public <T> Element create(T obj) {
				RoleEntity roleObj = (RoleEntity)obj;
				return new Element(roleObj.getId().toString(), roleObj);
			}
		});
	}
	
	public static RoleEntity getRole(Long id){
		if(null == id) return null;
		String cacheName = CacheName.roleCache.name();
		Object obj = GlobalCache.get(cacheName, id.toString());
		return (RoleEntity)obj;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static List<RoleEntity> getRoles(String ids) throws ServiceException{
		if(!StringHandler.isValidStr(ids)) return null;
		String cacheName = CacheName.roleCache.name();
		List dataList = GlobalCache.getCacheDatas(ids, cacheName,new DataNotify() {
			@Override
			public void load(String ids) throws ServiceException {
				putRoles(ids);
			}
		});
		List<RoleEntity> roles = (List<RoleEntity>)dataList;
		return roles;
	}

	/**
	 * 添加角色
	 * @param obj
	 */
	public static void addRole(RoleEntity obj){
		String cacheName = CacheName.roleCache.name();
		String key = obj.getId().toString();
		GlobalCache.put(cacheName,key,obj);
	}
	
	/**
	 * 更新角色
	 * @param role
	 */
	public static void updateRole(RoleEntity role){
		String cacheName = CacheName.roleCache.name();
		String roleId = role.getId().toString();
		GlobalCache.remove(cacheName, roleId);
		addRole(role);
	}
	
	/**
	 * 删除角色
	 * @param user
	 */
	public static void removeRoles(String roleIds){
		if(!StringHandler.isValidStr(roleIds)) return;
		String cacheName = CacheName.roleCache.name();
		Ehcache cache = GlobalCache.getCache(cacheName);
		String[] roleIdArr = roleIds.split(",");
		for(String roleId : roleIdArr){
			cache.remove(roleId);
		}
	}/**********====== Role(角色缓存) Cache Method END CODE ========**********/
	
}
