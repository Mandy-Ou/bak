package com.cmw.dao.inter.sys;


import com.cmw.core.base.annotation.Description;
import com.cmw.core.base.dao.GenericDaoInter;
import com.cmw.core.base.exception.DaoException;
import com.cmw.core.util.DataTable;
import com.cmw.core.util.SHashMap;

import com.cmw.entity.sys.DeskModEntity;


/**
 * 桌面模块配置  DAO接口
 * @author 程明卫
 * @date 2013-03-08T00:00:00
 */
 @Description(remark="桌面模块配置Dao接口",createDate="2013-03-08T00:00:00",author="程明卫")
public interface DeskModDaoInter  extends GenericDaoInter<DeskModEntity, Long>{
	 /**
	  * 根据 HQL 语句获取 DataTable 数据
	  * @param hql hql 语句
	  * @param map
	  * @return
	  * @throws DaoException
	  */
	<K,V> DataTable getDataTableByHql(String hql, SHashMap<K, V> map)throws DaoException;
	 /**
	  * 根据  sql 语句获取 DataTable 数据
	  * @param sql
	  * @param map 条件参数
	  * @return
	  * @throws DaoException
	  */
	<K, V> DataTable getDataTableBySql(String sql, SHashMap<K, V> map)throws DaoException;
	 /**
	  * 根据  模块编号获取 DataTable 数据
	  * @param map 条件参数
	  * @return
	  * @throws DaoException
	  */
	<K, V> DataTable getDeskModByCodes(SHashMap<K, V> map) throws DaoException;
	 
}
