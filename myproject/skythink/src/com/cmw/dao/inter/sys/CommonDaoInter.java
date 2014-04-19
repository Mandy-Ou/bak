package com.cmw.dao.inter.sys;

import java.util.List;

import com.cmw.core.base.exception.DaoException;
import com.cmw.core.util.DataTable;

/**
 * 公共 Dao 接口
 * @author chengmingwei
 *
 */
public interface CommonDaoInter {
	/**
	 * 根据 HQL 语句返回List字符串数据
	 * @param hql 要查询的 HQL 语句
	 * @return
	 * @throws DaoException
	 */
	public List<String> getDatasByHql(String hql) throws DaoException;
	/**
	 * 根据 HQL 语句返回 DataTable 数据
	 * @param hql 要查询的 HQL 语句
	 * @param columnNames DataTable 对象的列名
	 * @return 返回 DataTable 对象
	 * @throws DaoException
	 */
	public DataTable getDatasByHql(String hql,String columnNames) throws DaoException;
	/**
	 * 根据 SQL 语句返回List字符串数据
	 * @param sql 要查询的  sql 语句
	 * @return
	 * @throws DaoException
	 */
	public List<String> getDatasBySql(String sql) throws DaoException;
	/**
	 * 根据 SQL 语句返回 DataTable 数据
	 * @param hql 要查询的 HQL 语句
	 * @param	pars 查询参数
	 * @param columnNames DataTable 对象的列名
	 * @return 返回 DataTable 对象
	 * @throws DaoException
	 */
	public DataTable getDatasBySql(String sql,Object[] pars,String columnNames) throws DaoException;
	/**
	 * 根据 SQL 语句返回 DataTable 数据
	 * @param sql 要查询的 HQL 语句
	 * @param columnNames DataTable 对象的列名
	 * @return 返回 DataTable 对象
	 * @throws DaoException
	 */
	public DataTable getDatasBySql(String sql,String columnNames) throws DaoException;
	/**
	 * 根据 SQL 语句返回指定分页大小的数据  DataTable 数据
	 * @param sql 要查询的 HQL 语句
	 * @param columnNames DataTable 对象的列名
	 * @param offset 页数
	 * @param pageSize 每页数量
	 * @return 返回 DataTable 对象
	 * @throws DaoException
	 */
	public DataTable getDatasBySql(String sql, String columnNames,int offset, int pageSize)	throws DaoException;
	
	/**
	 * 根据 HQL 语句更新数据
	 * @param hql 要更新时用到的 HQL 语句
	 * @throws DaoException
	 */
	public void updateDatasByHql(String hql) throws DaoException;
	/**
	 * 根据 HQL 语句更新数据
	 * @param hql 要更新时用到的 HQL 语句
	 * @param pars 更新的参数值数组
	 * @throws DaoException
	 */
	public void updateDatasByHql(String hql, Object[] pars) throws DaoException;
	/**
	 * 根据 SQL 语句更新数据
	 * @param sql 要更新时用到的  sql 语句
	 * @throws DaoException
	 */
	public void updateDatasBySql(String sql) throws DaoException;
	
}
