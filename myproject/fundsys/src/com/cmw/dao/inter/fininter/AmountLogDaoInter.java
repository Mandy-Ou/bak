package com.cmw.dao.inter.fininter;


import com.cmw.core.base.annotation.Description;
import com.cmw.core.base.dao.GenericDaoInter;
import com.cmw.core.base.exception.DaoException;
import com.cmw.core.util.DataTable;
import com.cmw.core.util.SHashMap;

import com.cmw.entity.fininter.AmountLogEntity;


/**
 * 实收金额日志  DAO接口
 * @author 程明卫
 * @date 2013-03-28T00:00:00
 */
 @Description(remark="实收金额日志Dao接口",createDate="2013-03-28T00:00:00",author="程明卫")
public interface AmountLogDaoInter  extends GenericDaoInter<AmountLogEntity, Long>{
	 /**
	  * 更新记录表的 fcnumber 为 实收金额日志ID
	  * @param tabEntity 要更新的表实体名
	  * @param params 附带参数
	  * @throws DaoException
	  */
	 void updateRecordsAmountLogId(String tabEntity, SHashMap<String, Object> params) throws DaoException;
	 /**
	  * 根据实收金额日志ID获取放款数据
	  * @param params	过滤参数
	  * @param offset	分页的起始位置
	  * @param pageSize	每页大小
	  * @return	返回 DataTable 对象
	  * @throws DaoException
	  */
	 <K, V> DataTable getDsByAmountLogId(SHashMap<K, V> params, int offset, int pageSize)throws DaoException;
	/**
	 *  根据 SQL 语句获取 DataTable 对象
	 * @param sql
	 * @param colNames
	 * @return
	 * @throws DaoException
	 */
	 DataTable getDtBySql(String sql, String colNames) throws DaoException;
	 
	 <K, V> DataTable bussTaglist(SHashMap<K, V> params, int offset, int pageSize) throws DaoException;
	 
}
