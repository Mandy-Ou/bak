package com.cmw.core.kit.nosql.mongodb;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;


@SuppressWarnings("serial")
public class MgoMap extends HashMap<String, Object> implements Serializable {
	private DBObject query;
	private DBObject showCmns;
	private DBObject orders;
	
	/**
	 * 获取查询条件
	 * @return
	 */
	public DBObject getQueryCmns(){
		return query;
	}
	
	/**
	 * 设置查询条件
	 * @param params 查询条件过滤参数 
	 */
	public void putQuery(Map<String,Object> params){
		if(null == params || params.size() == 0) return;
		if(null == query) query = new BasicDBObject();
		query.putAll(params);
	}
	
	/**
	 * 设置要查询的列名
	 * @param cmns 查询的列名数组
	 */
	public void putShowCmns(String... cmns){
		if(null == showCmns) showCmns = new BasicDBObject();
		for(String cmn : cmns){
			showCmns.put(cmn, 1);
		}
	}
	
	/**
	 * 获取要查询的列名对象
	 * @return	返回要查询的 DBObject 对象
	 */
	public DBObject getShowCmns() {
		return showCmns;
	}
	
	/**
	 * 设置分页大小
	 * @param pagesize 分页大小值
	 */
	public void setPageSize(int pagesize){
		this.put(PAGESIZE_KEY, pagesize);
	}
	
	/**
	 * 设置表名
	 * @param tabName
	 */
	public void setTabName(String tabName){
		this.put(TABLE_KEY, tabName);
	}
	
	/**
	 * 获取表名
	 * @return
	 */
	public String getTabName(){
		return (String)this.get(TABLE_KEY);
	}
	
	/**
	 * 获取分页大小
	 * @return
	 */
	public Integer getPageSize(){
		return (Integer)this.get(PAGESIZE_KEY);
	}
	
	/**
	 * 设置分页偏移量
	 * @param offSet 偏移量
	 */
    public void setOffset(int offSet){
    	this.put(OFFSET_KEY, offSet);
    }
	/**
	 * 获取分页大小
	 * @return 返回 分页大小值
	 */
	public Integer getOffset(){
		return (Integer)this.get(OFFSET_KEY);
	}
	
	/**
	 * 获取排序列
	 * @return 返回排序 DBObject 对象
	 */
	public DBObject getOrderBy(){
		return orders;
	}
	
	/**
	 * 添加升序列
	 * @param ascCmns 升序列数组
	 */
	public void putAscCmn(String... ascCmns){
		if(null == ascCmns || ascCmns.length == 0) return;
		if(null == orders) orders = new BasicDBObject();
		for(String ascCmn : ascCmns){
			orders.put(ascCmn, 1);
		}
	}
	
	/**
	 * 添加降序列
	 * @param descCmns 降序列数组
	 */
	public void putDescCmn(String... descCmns){
		if(null == descCmns || descCmns.length == 0) return;
		if(null == orders) orders = new BasicDBObject();
		for(String descCmn : descCmns){
			orders.put(descCmn, -1);
		}
	}
	
	public static final String PAGESIZE_KEY = "_pagesize_";
	public static final String OFFSET_KEY = "_offset_";
	public static final String TABLE_KEY = "_tabname_";
}
