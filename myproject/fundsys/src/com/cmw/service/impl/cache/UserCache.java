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
import com.cmw.entity.sys.UserEntity;
import com.cmw.service.inter.sys.UserService;

/**
 * User(用户缓存)
 * @author chengmingwei
 *
 */
@SuppressWarnings("serial")
public class UserCache implements Serializable {
	/**********====== User(用户缓存) Cache Method Start CODE ========**********/
	/**
	 * 将用户列表放入缓存中
	 * @param userIds	要放置的多个用户的ID
	 * @throws ServiceException 
	 */
	public static void putUsers(String userIds) throws ServiceException{
		SHashMap<String, Object> params = new SHashMap<String, Object>();
		params.put("isenabled", SysConstant.OPTION_ENABLED);
		UserService userService = (UserService)SpringContextUtil.getBean("userService");
		if(StringHandler.isValidStr(userIds)){
			params.put("userId", SqlUtil.LOGIC_IN + SqlUtil.LOGIC + userIds);
		}
		List<UserEntity> users = userService.getEntityList(params);
			GlobalCache.addElements2Cache(CacheName.userCache,users,new ElementCreator() {
			@Override
			public <T> Element create(T obj) {
				UserEntity userObj = (UserEntity)obj;
				return new Element(userObj.getUserId().toString(), userObj);
			}
		});
	}
	
	public static UserEntity getUser(Long userId) throws ServiceException{
		if(null == userId) return null;
		String cacheName = CacheName.userCache.name();
		Object obj = GlobalCache.get(cacheName, userId.toString());
		if(null == obj){
			putUsers(userId.toString());
			obj = GlobalCache.get(cacheName, userId.toString());
		}
		return (UserEntity)obj;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static List<UserEntity> getUsers(String userIds) throws ServiceException{
		if(!StringHandler.isValidStr(userIds)) return null;
		String cacheName = CacheName.userCache.name();
		List dataList = GlobalCache.getCacheDatas(userIds, cacheName,new DataNotify() {
			@Override
			public void load(String ids) throws ServiceException {
				putUsers(ids);
			}
		});
		List<UserEntity> users = (List<UserEntity>)dataList;
		return users;
	}

	/**
	 * 添加用户
	 * @param user
	 */
	public static void addUser(UserEntity user){
		String cacheName = CacheName.userCache.name();
		String key = user.getUserId().toString();
		GlobalCache.put(cacheName,key,user);
	}
	
	/**
	 * 更新用户
	 * @param user
	 */
	public static void updateUser(UserEntity user){
		String cacheName = CacheName.userCache.name();
		String userId = user.getUserId().toString();
		GlobalCache.remove(cacheName, userId);
		addUser(user);
	}
	
	/**
	 * 删除用户
	 * @param user
	 */
	public static void removeUsers(String userIds){
		if(!StringHandler.isValidStr(userIds)) return;
		String cacheName = CacheName.userCache.name();
		Ehcache cache = GlobalCache.getCache(cacheName);
		String[] userIdArr = userIds.split(",");
		for(String userId : userIdArr){
			cache.remove(userId);
		}
	}/**********====== User(用户缓存) Cache Method END CODE ========**********/
}
