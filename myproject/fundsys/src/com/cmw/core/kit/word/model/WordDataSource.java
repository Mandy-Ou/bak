package com.cmw.core.kit.word.model;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import com.cmw.core.util.DataTable;

/**
 * Word 模板数据源
 * @author chengmingwei
 *
 */
public class WordDataSource {
	private Map<String,Object> que = new LinkedHashMap<String, Object>();
	private Map<String,Integer> dtType = new HashMap<String, Integer>();
	private Map<String,Object> params = null;
	public void addParams(Map<String,Object> params){
		this.params = params;
	}
	
	
	public Map<String, Object> getParams() {
		return params;
	}

	public void setParams(Map<String, Object> params) {
		this.params = params;
	}
	
	
	
	/**
	 * 有表头的Table
	 * @author chengmingwei
	 *
	 */
	public class HeadTable{
		
		private String captions;
		private DataTable dataSource;
		public String getCaptions() {
			return captions;
		}
		public void setCaptions(String captions) {
			this.captions = captions;
		}
		public DataTable getDataSource() {
			return dataSource;
		}
		public void setDataSource(DataTable dataSource) {
			this.dataSource = dataSource;
		}
	}
}
