package com.cmw.core.base.dao;

import java.util.Collections;
import java.util.List;

import javax.annotation.Resource;

import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.engine.spi.SessionFactoryImplementor;
import org.hibernate.hql.internal.ast.ASTQueryTranslatorFactory;
import org.hibernate.hql.spi.QueryTranslator;
import org.hibernate.hql.spi.QueryTranslatorFactory;

import com.cmw.core.base.exception.UtilException;
import com.cmw.core.util.DataTable;

/**
 * 用来处理分页的类
 * 
 * @author chengmingwei
 * 
 */
public class PageHibernateDaoSupport<T>  {
	/**
	 * 用来标识不分页的变量
	 */
	public static final Integer NOPAGE_PARAM = -1;
	
	@Resource(name="sessionFactory")
	private SessionFactory sessionFactory;
	/**
	 * 使用hql语句进行分页查询操作
	 * 
	 * @param hql
	 *            执行分页查询的hql语句
	 * @param offset
	 *            要查询的第一条记录索引
	 * @param pageSize
	 *            每页需要显示的大小
	 * @return 要显示的记录数
	 */

//	@SuppressWarnings("unchecked")
//	public List<T> findByPage(final String hql, final int offset,
//			final int pageSize) {
//		return findByPage(hql, null, offset, pageSize);
//	}
	
	public Session getSession(){
		return sessionFactory.getCurrentSession();
	}

	/**
	 * 使用hql语句进行分页查询操作
	 * 
	 * @param hql
	 *            执行分页查询的hql语句
	 * @param value
	 *            如果hql需要参数传入,那么value就是需要传入的参数
	 * @param offset
	 *            要查询的第一条记录索引
	 * @param pageSize
	 *            每页需要显示的大小
	 * @return 要显示的记录数
	 */
	public List<T> findByPage(final String hql, final Object value,
			final int offset, final int pageSize) {
		return findByPage(hql, new Object[]{value}, offset, pageSize);

	}

	
	/**
	 * 根据 HQL语句，返回查询的数据集合
	 * @param hqlStr HQL字符串
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public List getListByHql(final String hqlStr) {
		List result = getSession().createQuery(hqlStr).list(); 
		return result;
	}
	
	/**
	 * 使用hql语句进行分页查询操作
	 * 
	 * @param hql
	 *            执行分页查询的hql语句
	 * @param values
	 *            如果hql需要多个参数传入,那么values就是需要传入的所有参数
	 * @param offset
	 *            要查询的第一条记录索引
	 * @param pageSize
	 *            每页需要显示的大小
	 * @return 要显示的记录数
	 */
	@SuppressWarnings("unchecked")
	public List<T> findByPage(final String hql, final Object[] values,
			final int offset, final int pageSize) {
		Query query = getSession().createQuery(hql);
		if(null != values){
			for (int i = 0; i < values.length; i++) {
				query.setParameter(i, values[i]);
			}
		}
		List<T> list = query.setFirstResult(offset).setMaxResults(pageSize).list();
		return list;
	}
	
	/**
	 * 使用hql语句进行分页查询操作
	 * 
	 * @param hql
	 *            执行分页查询的hql语句
	 * @param offset
	 *            要查询的第一条记录索引
	 * @param pageSize
	 *            每页需要显示的大小
	 * @return 要显示的记录数
	 */

	@SuppressWarnings("rawtypes")
	public List findByPage(String hql, final int offset,final int pageSize) {
		Session session = getSession();
		List list = (NOPAGE_PARAM==offset) ? session.createQuery(hql).list() : session.createQuery(hql).setFirstResult(offset).setMaxResults(pageSize).list();
		return list;
	}
	
	/**
	 * 使用hql语句进行分页查询操作
	 * 
	 * @param hql
	 *            执行分页查询的hql语句
	 * @param offset
	 *            要查询的第一条记录索引
	 * @param pageSize
	 *            每页需要显示的大小
	 * @return 要显示的记录数
	 */

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public DataTable findByPage(final String hql,String columnNames,final int offset,
			final int pageSize) {
		List result = findByPage(hql,offset,pageSize);
		return new DataTable(result,columnNames);
	}
	
	/**
	 * 根据指定的HQL 语句查询数据
	 * @param hql HQL语句
	 * @return 返回 DataTable 对象
	 */
	@SuppressWarnings("unchecked")
	public DataTable find(final String hql){
		List<Object> result = getSession().createQuery(hql).list();
		DataTable dt = new DataTable();
		dt.setDataSource(result);
		try {
			dt.setHqlAsColumnNames(hql);
		} catch (UtilException e) {
			e.printStackTrace();
		}
		return dt;
	}
	
	/**
	 * 根据指定的HQL 语句查询数据
	 * @param hql HQL语句
	 * @param cmns DataTable 对象的列名
	 * @return 返回 DataTable 对象
	 */
	@SuppressWarnings("unchecked")
	public DataTable find(final String hql,String cmns){
		List<Object> result = getSession().createQuery(hql).list();
		DataTable dt = new DataTable();
		dt.setDataSource(result);
		dt.setColumnNames(cmns);
		return dt;
	}
	
	
	/**
	 * 使用hql语句进行分页查询操作
	 * 
	 * @param hql
	 *            执行分页查询的hql语句
	 * @param offset
	 *            要查询的第一条记录索引
	 * @param pageSize
	 *            每页需要显示的大小
	 * @return 要显示的记录数
	 */

	@SuppressWarnings("unchecked")
	public DataTable findByPage(final String hql) {
		String strHql = hql.replaceAll("#yyyy-MM-dd", "");
		List<Object> result = findByPage(strHql,NOPAGE_PARAM,0);
			DataTable dt = new DataTable();
			dt.setDataSource(result);
			try {
				dt.setHqlAsColumnNames(hql);
			} catch (UtilException e) {
				e.printStackTrace();
			}
			return dt;
	}
	
	/**
	 * 使用hql语句进行分页查询操作
	 * 
	 * @param hql
	 *            执行分页查询的hql语句
	 * @param offset
	 *            要查询的第一条记录索引
	 * @param pageSize
	 *            每页需要显示的大小
	 * @return 要显示的记录数
	 */

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public DataTable findByPage(final String hql,final int... pageParams) {
		int offset = pageParams[0];
		int pageSize = pageParams[1];
		String strHql = hql.replaceAll("#yyyy-MM-dd", "");
		
		List result = findByPage(strHql,offset,pageSize);
			DataTable dt = new DataTable();
			dt.setDataSource(result);
			try {
				dt.setHqlAsColumnNames(hql);
			} catch (UtilException e) {
				e.printStackTrace();
			}
			return dt;
	}
	
	/**
	 * 根据SQL语句，返回查询的数据集合
	 * @param sqlStr SQL字符串
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public List getListBySql(final String sqlStr) {
		List result = getSession().createSQLQuery(sqlStr).list(); 
		return result;
	}
	
	/**
	 * 使用SQL语句进行查询操作
	 * 
	 * @param sqlStr
	 *            执行查询的sql语句
	 * @return 要显示的记录数
	 */

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public DataTable findBySql(final String sqlStr) {
		List result =getListBySql(sqlStr);
		DataTable dt = new DataTable();
		dt.setDataSource(result);
		try {
			dt.setHqlAsColumnNames(sqlStr);
		} catch (UtilException e) {
			e.printStackTrace();
		}
		return dt;
	}
	
	/**
	 * 使用SQL语句进行查询操作
	 * 
	 * @param sqlStr
	 *            执行查询的sql语句
	 * @return 要显示的记录数
	 */

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public DataTable findBySqlPage( String sqlStr,final int offset,
			final int pageSize) {
		List result = null;
		//sqlStr = SqlUtil.transactSQLInjection(sqlStr);
		SQLQuery query = getSession().createSQLQuery(sqlStr);
		if(offset == -1 || pageSize == -1){
			result = query.list();
		}else{
			result = query.setFirstResult(offset).setMaxResults(pageSize).list();
		}
		 
		DataTable dt = new DataTable();
		dt.setDataSource(result);
		try {
			dt.setHqlAsColumnNames(sqlStr);
		} catch (UtilException e) {
			e.printStackTrace();
		}
		return dt;
	}
	
	/**
	 * 使用SQL语句进行查询操作
	 * 
	 * @param sqlStr
	 *            执行查询的sql语句
	 * @param colNames
	 * 				要显示的列名
	 * @return 要显示的记录数
	 */
	public DataTable findBySqlPage(final String sqlStr,String colNames,final int offset,
			final int pageSize) {
		DataTable dt = findBySqlPage(sqlStr,offset,pageSize);
		dt.setColumnNames(colNames);
		return dt;
	}
	
	/**
	 * 使用SQL语句进行查询操作
	 * 
	 * @param sqlStr
	 *            执行查询的sql语句
	 * @param colNames
	 * 				要显示的列名
	 * @return 要显示的记录数
	 */
	public DataTable findBySqlPage(final String sqlStr,String colNames,final int offset,
			final int pageSize,long totalCount) {
		DataTable dt = findBySqlPage(sqlStr,offset,pageSize);
		if(null != dt && dt.getRowCount() > 0){
			dt.setSize(totalCount);
		}
		dt.setColumnNames(colNames);
		return dt;
	}
	
	/**
	 * 使用SQL语句进行查询操作
	 * 
	 * @param sqlStr
	 *            执行查询的sql语句
	 * @param colNames
	 * 				要显示的列名
	 * @return 要显示的记录数
	 */
	public DataTable findBySql(final String sqlStr,String colNames) {
		DataTable dt = findBySql(sqlStr);
		dt.setColumnNames(colNames);
		return dt;
	}
	
	public String toSql(String hqlQueryText){
	    if (hqlQueryText!=null && hqlQueryText.trim().length()>0){
	      final QueryTranslatorFactory translatorFactory = new ASTQueryTranslatorFactory();
	      final SessionFactoryImplementor factory = 
	        (SessionFactoryImplementor) sessionFactory;
	      final QueryTranslator translator = translatorFactory.
	        createQueryTranslator(
	          hqlQueryText, 
	          hqlQueryText, 
	          Collections.EMPTY_MAP, factory
	        );
	      translator.compile(Collections.EMPTY_MAP, false);
	      return translator.getSQLString(); 
	    }
	    return null;
	  }
}
