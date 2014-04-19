package com.cmw.dao.impl.sys;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

import com.cmw.core.base.annotation.Description;
import com.cmw.core.base.dao.GenericDaoAbs;
import com.cmw.core.base.exception.DaoException;
import com.cmw.core.util.DataTable;
import com.cmw.dao.inter.sys.CommonDaoInter;
/**
 * 公共DAO实现类
 * @author chengmingwei
 *
 */
@Description(remark="公共DAO实现类",createDate="2013-05-30")
@Repository("commonDao")        //声明此类为数据持久层的类
public class CommonDaoImpl extends GenericDaoAbs<Object,Object> implements CommonDaoInter {

	@SuppressWarnings("unchecked")
	@Override
	public List<String> getDatasByHql(String hql) throws DaoException {
		try{
			return (List<String>)getListByHql(hql);
		}catch (DataAccessException e) {
			e.printStackTrace();
			throw new DaoException(e);
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<String> getDatasBySql(String sql) throws DaoException {
		try{
			return (List<String>)getListBySql(sql);
		}catch (DataAccessException e) {
			e.printStackTrace();
			throw new DaoException(e);
		}
		
	}

	@Override
	public void updateDatasByHql(String hql) throws DaoException {
		try{
			Query query = getSession().createQuery(hql);
			query.executeUpdate();
		}catch (DataAccessException e) {
			e.printStackTrace();
			throw new DaoException(e);
		}
	}
	
	@Override
	public void updateDatasByHql(String hql, Object[] pars) throws DaoException {
		try{
			Query query = getSession().createQuery(hql);
			if(null != pars && pars.length > 0){
				for(int i=0,count=pars.length; i<count; i++){
					Object par = pars[i];
					query.setParameter(i, par);
				}
			}
			query.executeUpdate();
		}catch (DataAccessException e) {
			e.printStackTrace();
			throw new DaoException(e);
		}
	}
	
	@Override
	public void updateDatasBySql(String sql) throws DaoException {
		try{
			SQLQuery query = getSession().createSQLQuery(sql);
			query.executeUpdate();
		}catch (DataAccessException e) {
			e.printStackTrace();
			throw new DaoException(e);
		}
	}

	@Override
	public DataTable getDatasByHql(String hql,String columnNames) throws DaoException {
		try{
			return find(hql, columnNames);
		}catch (DataAccessException e) {
			e.printStackTrace();
			throw new DaoException(e);
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public DataTable getDatasBySql(String sql, Object[] pars, String columnNames)
			throws DaoException {
		try{
			SQLQuery query = getSession().createSQLQuery(sql);
			if(null != pars && pars.length > 0){
				for(int i=0,count=pars.length; i<count; i++){
					Object val = pars[i];
					query.setParameter(i, val);
				}
			}
			List<Object> dataSource = query.list();
			DataTable dt = new DataTable(dataSource, columnNames);
			return dt;
		}catch (DataAccessException e) {
			e.printStackTrace();
			throw new DaoException(e);
		}
	}

	@Override
	@SuppressWarnings("unchecked")
	public DataTable getDatasBySql(String sql, String columnNames)
			throws DaoException {
		try{
			SQLQuery query = getSession().createSQLQuery(sql);
			List<Object> dataSource = query.list();
			DataTable dt = new DataTable(dataSource, columnNames);
			return dt;
		}catch (DataAccessException e) {
			e.printStackTrace();
			throw new DaoException(e);
		}
	}
	
	@Override
	public DataTable getDatasBySql(String sql, String columnNames,int offset, int pageSize)	throws DaoException {
		try{
			DataTable dt = findBySqlPage(sql, offset, pageSize);
			dt.setColumnNames(columnNames);
			return dt;
		}catch (DataAccessException e) {
			e.printStackTrace();
			throw new DaoException(e);
		}
	}
}
