package com.cmw.core.util;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.hibernate.Query;


/**
 * 扩展的HashMap工具类
 * @author chengmingwei
 *
 */
public class SHashMap<K extends Object,V extends Object> implements Serializable {
	private static StringHandler log = new StringHandler();
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private HashMap<K,V> map;
	public SHashMap() {
		map = new HashMap<K, V>();
	}
	
	public SHashMap(Map<K,V> map) {
		this.map =  (HashMap<K, V>) map;
	}
	
	/**
	 * 	往SHashMap 对象里面存值
	 * @param key	键
	 * @param value	值
	 */
	public void put(K key,V value)
	{
		map.put(key, value);
	}
	
	/**
	 * 	往SHashMap 对象里面存值
	 * @param key	键
	 * @param value	值
	 */
	public void put(String key,String value)
	{
		map.put((K)key, (V) value);
	}
	
	public void remove(K key){
		map.remove(key);
	}
	
//	public void remove(String key){
//		map.remove(key);
//	}
	/**
	 * 删除当前对象中指定的多个 key 的值
	 * @param keys	要删除的 keys 值 , 例："userName,passWord"
	 */
	public void removes(String keys){
		if(!StringHandler.isValidStr(keys)) return;
		String[] keyArr = keys.split(",");
		for(String key : keyArr){
			map.remove(key);
		}
	}
	/**
	 * 根据键获取值
	 * @param key 用来查找值的键
	 * @return
	 */
	public V get(K key)
	{
		return map.get(key);
	}
	
	/**
	 * 通过指定的键返回  Object 对象值
	 * @param key 键
	 * @return 返回  Object 对象值
	 */
	public Object getvalAsObj(String key){
		Object val = map.get(key);
		return val;
	}
	
	/**
	 * 通过指定的键返回 Integer 值
	 * @param key 键
	 * @return 返回  Integer 值
	 */
	public Integer getvalAsInt(String key){
		Object val = map.get(key);
		return !StringHandler.isValidObj(val) ? null :Integer.parseInt(val.toString());
	}
	
	/**
	 * 通过指定的键返回 Long 值
	 * @param key 键
	 * @return 返回  Long 值
	 */
	public Long getvalAsLng(String key){
		Object val = map.get(key);
		return !StringHandler.isValidObj(val) ? null :Long.parseLong(val.toString());
	}
	
	/**
	 * 通过指定的键返回 Boolean 值
	 * @param key 键
	 * @return 返回  Boolean 值
	 */
	public Boolean getvalAsBln(String key){
		Object val = map.get(key);
		return !StringHandler.isValidObj(val) ? null :Boolean.parseBoolean(val.toString());
	}
	
	/**
	 * 通过指定的键返回 Float 值
	 * @param key 键
	 * @return 返回  Float 值
	 */
	public Float getvalAsFlt(String key){
		Object val = map.get(key);
		return !StringHandler.isValidObj(val) ? null : Float.parseFloat(val.toString());
	}
	/**
	 * 通过指定的键返回 BigDecimal 值
	 * @param key 键
	 * @return 返回  BigDecimal 值
	 */
	public BigDecimal getvalAsBig(String key){
		Object val = map.get(key);
		BigDecimal defalut = new BigDecimal(0.00);
		return !StringHandler.isValidObj(val) ?  defalut : BigDecimal.valueOf(getvalAsDob(key));
	}
	/**
	 * 通过指定的键返回 Float 值
	 * @param key 键
	 * @return 返回  Float 值
	 */
	public Double getvalAsDob(String key){
		Object val = map.get(key);
		return !StringHandler.isValidObj(val) ? null : Double.parseDouble(val.toString());
	}
	
	/**
	 * 通过指定的键返回 String 值
	 * @param key 键
	 * @return 返回  String 值
	 */
	public String getvalAsStr(String key){
		Object val = map.get(key);
		return !StringHandler.isValidObj(val) ? null : val.toString();
	}
	
	/**
	 * 给query 参数赋值
	 * @param query	query 对象
	 * @param key	map键
	 */
	@SuppressWarnings("static-access")
	public void setParameterValue(Query query,K key)
	{
		String k = (String)key;
		Object value = map.get(k);
		if (value instanceof Integer) {
			query.setParameter(k, (Integer)value);
			log.P("Integer :"+k+"="+ value);
		}else if(value instanceof String)
		{
			query.setParameter(k, (String)value);
			log.P("String :"+key+"="+ value);
		}else if(value instanceof Float)
		{
			query.setParameter(k, (Float)value);
			log.P("Float :"+key+"="+ value);
		}else if(value instanceof Double)
		{
			query.setParameter(k, (Double)value);
			log.P("Double :"+key+"="+ value);
		}else if(value instanceof Long)
		{
			query.setParameter(k, (Long)value);
			log.P("Long :"+key+"="+ value);
		}else if(value instanceof Date)
		{
			query.setParameter(k, (Date)value);
			log.P("Date :"+key+"="+ value);
		}else
		{
			query.setParameter(k,value);
			log.P("Object :"+key+"="+ value);
		}
	}
	/**
	 * 检查指定的key 
	 */
	public boolean validKey(String key){
		return map.containsKey(key);
		
	}
	/**
	 * 返回键集合
	 * @return
	 */
	public Set<K> keySet()
	{
		return map.keySet();
	}
	/**
	 * 返回 Map 大小
	 * @return
	 */
	public long size(){
		if(null != this){
			return this.keySet().size();
		}
		return 0;
	}
	
	/**
	 * 返回 Map 对象
	 * @return
	 */
	public HashMap<K, V> getMap(){
		return this.map;
	}
	
	/**
	 * 清空SHashMap 对象中所有数据
	 */
	public void clear(){
		if(null != map) map.clear();
	}
	
	
	public static void main(String[] args)
	{
	}
}
