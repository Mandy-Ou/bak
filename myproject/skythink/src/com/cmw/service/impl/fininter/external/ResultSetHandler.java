package com.cmw.service.impl.fininter.external;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * 结果集处理器
 * @author chengmingwei
 *
 */
public interface ResultSetHandler<T> {
	/**
	 * 执行查询结果集业务逻辑
	 * @param rs 结果集对
	 * @return
	 */
	public T execute(ResultSet rs) throws SQLException;
}
