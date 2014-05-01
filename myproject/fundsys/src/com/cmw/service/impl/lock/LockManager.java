package com.cmw.service.impl.lock;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.servlet.http.HttpSession;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.cmw.core.base.exception.ServiceException;
import com.cmw.core.util.DataTable;
import com.cmw.core.util.StringHandler;
import com.cmw.entity.sys.UserEntity;

/**
 * 业务锁管理
 * @author chengmingwei
 *
 */
public class LockManager {

	private static Map<Long,LockModel> lockCacheMap = new HashMap<Long, LockModel>();
	
	private LockManager() {
	}

	/**
	 * 申请锁，锁住相应用户数据
	 * @param currUser 要申请锁的用户
	 * @param dtSource	要锁住的数据源
	 * @param cmn 数据源指定的列
	 * @param key 锁钥匙
	 * @throws ServiceException
	 */
	public synchronized void applyLock(UserEntity currUser, DataTable dtSource,String cmn,String key) throws ServiceException{
		Long userId = currUser.getUserId();
		LockModel lockModel = null;
		if(lockCacheMap.containsKey(userId)){
			lockModel = lockCacheMap.get(userId);
			Map<String,List<String>> keyMap = lockModel.getLockMap();
			List<String> list = getLockDatas(dtSource, cmn);
			keyMap.put(key, list);
		}else{
			String userName = currUser.getUserName();
			lockModel = new LockModel();
			lockModel.setUserName(userName);
			Map<String,List<String>> keyMap = new HashMap<String, List<String>>();
			List<String> list = getLockDatas(dtSource, cmn);
			keyMap.put(key, list);
			lockModel.setLockMap(keyMap);
			lockCacheMap.put(userId, lockModel);
		}
	}
	/**
	 * 获取要锁定的数据
	 * @param dtSource
	 * @param cmn
	 * @return
	 */
	private List<String> getLockDatas(DataTable dtSource,String cmn){
		List<String> list = new ArrayList<String>();
		for(int i=0,count=dtSource.getRowCount(); i<count; i++){
			String val = dtSource.getString(i, cmn);
			if(!StringHandler.isValidStr(val)) continue;
			list.add(val);
		}
		return list;
	}
	
	/**
	 * 申请锁，锁住相应用户数据
	 * @param currUser 要申请锁的用户
	 * @param lockList	要锁住的List数据源
	 * @param key 锁钥匙
	 * @throws ServiceException
	 */
	public synchronized void applyLock(UserEntity currUser, List<String> lockList,String key) throws ServiceException{
		Long userId = currUser.getUserId();
		LockModel lockModel = null;
		if(lockCacheMap.containsKey(userId)){
			lockModel = lockCacheMap.get(userId);
			Map<String,List<String>> keyMap = lockModel.getLockMap();
			keyMap.put(key, lockList);
		}else{
			String userName = currUser.getUserName();
			lockModel = new LockModel();
			lockModel.setUserName(userName);
			Map<String,List<String>> keyMap = new HashMap<String, List<String>>();
			keyMap.put(key, lockList);
			lockModel.setLockMap(keyMap);
			lockCacheMap.put(userId, lockModel);
		}
	}
	
	/**
	 * 移除用户锁定的所有业务数据
	 * @param currUser 解锁的用户
	 * @throws ServiceException
	 */
	public void removeLock(UserEntity currUser) throws ServiceException{
		if(null == currUser) return;
		Long userId = currUser.getUserId();
		lockCacheMap.remove(userId);
	}
	
	/**
	 * 解锁指定用户的业务
	 * @param currUser 解锁的用户
	 * @param key 锁钥匙
	 * @throws ServiceException
	 */
	public synchronized void releaseLock(UserEntity currUser,String key) throws ServiceException{
		Long userId = currUser.getUserId();
		LockModel lockModel = lockCacheMap.get(userId);
		if(null == lockModel) return;
		Map<String, List<String>> lockMap = lockModel.getLockMap();
		if(lockMap.containsKey(key)) lockMap.remove(key);
	}
	
	/**
	 * 判断用户是否锁住了指定的客户
	 * @param currUser 解锁的用户
	 * @param key 锁钥匙
	 * @throws ServiceException
	 */
	public synchronized JSONArray isLock(String key,List<String> customers) throws ServiceException{
		StringBuilder sb = new StringBuilder();
		if(null == lockCacheMap || lockCacheMap.isEmpty()) return null;
		JSONArray lockArr = new JSONArray();
		Collection<LockModel> iterators = lockCacheMap.values();
		Iterator<LockModel> iterator = iterators.iterator();
		while(iterator.hasNext()){
			LockModel lockModel = iterator.next();
			if(null == lockModel) continue;
			Map<String, List<String>> lockMap =  lockModel.getLockMap();
			List<String> list = lockMap.get(key);
			if(null == list || list.size() == 0) continue;
			
			for(String customer : customers){
				for(String _customer : list){
					if(customer.equals(_customer)){
						sb.append(customer).append(",");
						break;
					}
				}
			}
			if(null == sb || sb.length() == 0) continue;
			String userName = lockModel.getUserName();
			JSONObject obj = new JSONObject();
			obj.put("userName", userName);
			obj.put("customers", StringHandler.RemoveStr(sb));
			lockArr.add(obj);
		}
		return lockArr;
	}
	
	/**
	 * 获取锁住了的客户数据
	 * @param key 锁钥匙
	 * @throws ServiceException
	 */
	public synchronized JSONArray getLockData(String key,DataTable dt , String cmn) throws ServiceException{
		StringBuilder sb = new StringBuilder();
		if(null == lockCacheMap || lockCacheMap.isEmpty()) return null;
		JSONArray lockArr = new JSONArray();
		Collection<LockModel> iterators = lockCacheMap.values();
		Iterator<LockModel> iterator = iterators.iterator();
		while(iterator.hasNext()){
			LockModel lockModel = iterator.next();
			if(null == lockModel) continue;
			Map<String, List<String>> lockMap =  lockModel.getLockMap();
			List<String> list = lockMap.get(key);
			if(null == list || list.size() == 0) continue;
			for(int i=0,count=dt.getRowCount(); i<count; i++){
				String customer = dt.getString(i, cmn);
				for(String _customer : list){
					if(customer.equals(_customer)){
						sb.append(customer).append(",");
						break;
					}
				}
			}
			
			if(null == sb || sb.length() == 0) continue;
			String userName = lockModel.getUserName();
			JSONObject obj = new JSONObject();
			obj.put("userName", userName);
			obj.put("customers", StringHandler.RemoveStr(sb));
			lockArr.add(obj);
		}
		return lockArr;
	}
	
	public static LockManager getInstance(){
		return LazyHodler.INSTANCE;
	}
	
	private static final class LazyHodler{
		private static final LockManager INSTANCE = new LockManager();
	}
}
