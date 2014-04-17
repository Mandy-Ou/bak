package com.cmw.core.util;

import java.io.Serializable;
import java.util.List;

/**
 * 查询结果集类
 * @author chengmingwei
 *
 */
public class QueryResult implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 结果记录总数
	 */
	private long recordtotal;
	/**
	 * 结果集存放对象
	 */
	private List<?> resultset;

	
	public QueryResult() {
		recordtotal = 0;
		resultset = null;
	}

	public QueryResult(List<?> resultset) {
		this.recordtotal = 0;
		this.resultset = resultset;
	}

	public long getRecordtotal() {
		return recordtotal;
	}

	public void setRecordtotal(long recordtotal) {
		this.recordtotal = recordtotal;
	}

	public List<?> getResultset() {
		return resultset;
	}

	public void setResultset(List<?> resultset) {
		this.resultset = resultset;
	}
	
}
