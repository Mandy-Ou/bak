package com.cmw.core.base.exception;
/**
 * 工具类异常
 * @author chengmingwei
 *
 */
public class UtilException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public UtilException() {
		super();
	}

	public UtilException(String msg) {
		super(msg);
	}
	
	public final static String WHERE_IS_NULL = "query where condition is null"; 
	
	public final static String ALIAS_IS_NULL = "query  where condition alias is null";
	
	public final static String ROW_INDEX_BOUND = "DataTable.getRowData.index.bound";
	
	public final static String CELL_NAME_ISNULL = "DataTable.getCellIndex.index.bound";
}
