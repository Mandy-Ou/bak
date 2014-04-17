package com.cmw.core.base.cache;

import java.util.ArrayList;
import java.util.List;

import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Ehcache;
import net.sf.ehcache.Element;

import org.apache.log4j.Logger;

import com.cmw.core.base.exception.ServiceException;
import com.cmw.core.util.StringHandler;
import com.cmw.service.impl.cache.CacheName;
  



public abstract class FlatCache {  
    /** 
     * 日志 
     */  
    private static final Logger LOGGER = Logger.getLogger(FlatCache.class);  
  
    /** 
     * 缓存配置文件 
     */  
    public static String CACHE_CONFIG_FILE = "ehcache.xml";  
  
    /** 
     * Ehanche的缓存管理 
     */  
    private static CacheManager cacheManager = null;  
  
    /** 
     * 设置缓存配置文件，最开始设置才有效果，一旦缓存加载则不能改变 
     *  
     * @param cacheConfigFile 
     */  
    public static void setCacheConfigFile(String cacheConfigFile) {  
        CACHE_CONFIG_FILE = cacheConfigFile;
    }  
  
    /** 
     * 按缺省配置创建缓存 
     *  
     * @param cacheName 
     */  
    public static void createCache(String cacheName) {  
        getCacheManager().addCache(cacheName);  
    }  
  
    /** 
     * 添加缓存 
     *  
     * @param cacheName 
     * @param key 
     * @param value 
     */  
    public static void put(String cacheName, String key, Object value) {  
        Ehcache cache = getCacheManager().getEhcache(cacheName);  
        cache.put(new Element(key, value)); 
    }
    
    
  
    /** 
     * 根据缓存名与key获取值 
     *  
     * @param cacheName 
     * @param key 
     * @return  
     */  
    public static Object get(String cacheName, String key) {  
        Ehcache cache = getCacheManager().getEhcache(cacheName);  
        Element e = cache.get(key);  
        return e == null ? null : e.getObjectValue();  
    }  
  
    
    /** 
     * 获取缓存名 
     *  
     * @return  
     */  
    public static String[] getCacheNames() {  
        return getCacheManager().getCacheNames();  
    }  
  
    /** 
     * 获取缓存的Keys 
     *  
     * @param cacheName 
     * @return  
     */  
    @SuppressWarnings("unchecked")  
    public static List<String> getKeys(String cacheName) {  
        Ehcache cache = getCacheManager().getEhcache(cacheName);  
        return (List<String>) cache.getKeys();  
    }  
  
    /** 
     * 清除所有 
     */  
    public static void clearAll() {  
        getCacheManager().clearAll();  
    }  
  
    /** 
     * 停止Ehcache
     *  
     */  
    public static void shutdown() {  
    	getCacheManager().shutdown();
    }  
    
    /** 
     * 清空指定缓存 
     *  
     * @param cacheName 
     */  
    public static void clear(String cacheName) {  
        getCacheManager().getCache(cacheName).removeAll();  
    }  
  
    /** 
     * 删除指定对象 
     *  
     * @param cacheName 
     * @param key 
     * @return  
     */  
    public static boolean remove(String cacheName, String key) {  
        return getCacheManager().getCache(cacheName).remove(key);  
    }  
  
    /** 
     * 获取缓存大小 
     *  
     * @param cacheName 
     * @return  
     */  
    public static int getSize(String cacheName) {  
        return getCacheManager().getCache(cacheName).getSize();  
    }  
  
    /** 
     * 获取CacheManager 
     *  
     * @return  
     */  
    protected static CacheManager getCacheManager() {  
        if (cacheManager != null) {  
            return cacheManager;  
        }  
  
        try {
            String configurationFileName = StringHandler.getClassesPath(CACHE_CONFIG_FILE);
            cacheManager = CacheManager.create(configurationFileName);
        }  catch (RuntimeException e) {  
            LOGGER.error("init flat cache failed", e);  
            throw e;  
        }  
  
        return cacheManager;  
    }
    
    /**
     * 
     * @param cacheName
     * @return
     */
    public static Ehcache getCache(String cacheName){
    	return getCacheManager().getEhcache(cacheName);
    }
    
	/**
	 * 获取指定ID的缓存列表数据
	 * @param ids	要获取对象的Id列表
	 * @param cacheName	缓存名
	 * @param notifyObj	数据通知对象（当首次从缓存中找不到对象时，要从数据库中加载数据的方法）
	 * @throws ServiceException 
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static List getCacheDatas(String ids, String cacheName,DataNotify notifyObj) throws ServiceException {
	    Ehcache cache = getCacheManager().getEhcache(cacheName);
	    if(null == cache) return null;
		List<String> keys = (List<String>)cache.getKeys();
		String noExistIds = null;
		String[] idArr = ids.split(",");
		if(null == keys || keys.size() == 0){
			noExistIds = ids;
		}else{
			StringBuffer sbNoExistIds = new StringBuffer();
			for(String id : idArr){
				if(!keys.contains(id)){
					sbNoExistIds.append(id).append(",");
				}
			}
			noExistIds = StringHandler.RemoveStr(sbNoExistIds);
		}
		
		
		boolean hasLoad = false;
		if(null != noExistIds && noExistIds.length() > 0){
			notifyObj.load(noExistIds);
			hasLoad = true;
		}
		if(hasLoad) keys = getKeys(cacheName);
		
		List list = new ArrayList();
		for(String key : keys){
			for(String id : idArr){
				if(id.equals(key)){
					Element element = cache.get(key);
					Object value = element.getObjectValue();
					if(null == value) continue;
					list.add(value);
					break;
				}
			}
		}
		return list;
	}
	
	 /** 
     * 根据缓存名与key获取值 
     * @param cacheName 
     * @param compare	元素比较器对象 
     * @return  返回符合元素比较器条件的对象
     */
	@SuppressWarnings("unchecked")
    public static Object get(String cacheName, ElementCompare compare) {  
    	Ehcache cache = getCacheManager().getEhcache(cacheName);
		List<String> keys = (List<String>)cache.getKeys();
		Object obj = null;
        for(String key : keys){
			Element element = cache.get(key);
			if(element == null) continue;
			Object value = element.getObjectValue();
			if(compare.equals(value)){
				obj = value;
				break;
			}
		}
        return obj;  
    }
    
	 /** 
     * 根据缓存名与key获取值 
     * @param cacheName 
     * @param compare	元素比较器对象 
     * @return  返回符合元素比较器条件的对象
     */
	@SuppressWarnings({ "unchecked", "rawtypes" })
    public static List getList(String cacheName, ElementCompare compare) {  
    	Ehcache cache = getCacheManager().getEhcache(cacheName);
    	if(null == cache) return null;
		List<String> keys = (List<String>)cache.getKeys();
		if(null == keys || keys.size() == 0) return null;
		List dataList = new ArrayList();
        for(String key : keys){
			Element element = cache.get(key);
			if(null == element) continue;
			Object value = element.getObjectValue();
			if(!compare.equals(value)) continue;
			dataList.add(value);
		}
        return dataList;  
    }
	
	/**
	 * 添加元素到指定的缓存对象中
	 * @param cacheName	缓存名，参见 CacheName 枚举 和 ehcache.xml
	 * @param list	要缓存的列表
	 * @param callback Element 对角创建器
	 */
	public static <T> void addElements2Cache(CacheName cacheName,List<T> list,ElementCreator callback){
		addElements2Cache(cacheName, list, callback, false);
	}
	
	/**
	 * 添加元素到指定的缓存对象中
	 * @param cacheName	缓存名，参见 CacheName 枚举 和 ehcache.xml
	 * @param list	要缓存的列表
	 * @param isAppend 是否追加  [true : 将以追加形式添加, false : 清空当前缓存，再加入数据]
	 * @param callback Element 对角创建器
	 */
	public static <T> void addElements2Cache(CacheName cacheName,List<T> list,ElementCreator callback,boolean isAppend){
		if(null == list || list.size() == 0) return;
		String cacheNameStr = cacheName.toString();
		CacheManager cacheManager = getCacheManager();
		if(!isAppend) cacheManager.getCache(cacheNameStr).removeAll();
		if(!cacheManager.cacheExists(cacheNameStr)){
			cacheManager.addCache(cacheNameStr);
		}
		
		Ehcache cache = cacheManager.getEhcache(cacheNameStr);
		for(T obj : list){
		    Element ele = callback.create(obj);
	        cache.put(ele);
		}
	}
	
}  