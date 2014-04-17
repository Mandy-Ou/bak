package com.cmw.core.kit.nosql.exception;
/**
 * NoSql 数据异常类
 * @author chengmingwei
 *
 */
@SuppressWarnings("serial")
public class NoSqlException extends Exception {
	public NoSqlException() {
		super();
	}

	public NoSqlException(String message, Throwable cause) {
		super(message, cause);
	}

	public NoSqlException(String message) {
		super(message);
	}

	public NoSqlException(Throwable cause) {
		super(cause);
	}

}
